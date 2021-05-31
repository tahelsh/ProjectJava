/**
 * 
 */
package renderer;

import primitives.*;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import elements.LightSource;
import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;
import scene.Scene;

/**
 * class of RayTracerBasic
 * 
 * @author Tahel Sharon and Ayala Israeli
 *
 */
public class RayTracerBasic extends RayTracerBase {
	private static final int MAX_CALC_COLOR_LEVEL = 10;
	private static final double MIN_CALC_COLOR_K = 0.001;
	private int numberOfRays=1;//number of rays to the light 

	/**
	 * constructor that gets a scene
	 * 
	 * @param scene
	 */
	public RayTracerBasic(Scene scene) {
		super(scene);
	}
	/**
	 * set number of rays
	 * @param numberOfRays
	 * @return the object
	 */
	public RayTracerBasic setNumberOfRays(int numberOfRays)
	{
		this.numberOfRays=numberOfRays;
		return this;
	}

	@Override
	public Color traceRay(Ray ray/*, int numberOfRays*/) {
		GeoPoint closestPoint = findClosestIntersection(ray);
		if (closestPoint == null)
			return scene.background;
		return calcColor(closestPoint, ray/*, numberOfRays*/);
	}

	/**
	 * calculate the color of a point
	 * 
	 * @param geopoint - a point of intersection
	 * @param ray      - ray of a center of pixel
	 * @return the color of the point by phong model + reflacted and refracted
	 */
	private Color calcColor(GeoPoint geopoint, Ray ray/*, int numberOfRays*/) {
		return calcColor(geopoint, ray, MAX_CALC_COLOR_LEVEL, 1.0/*, numberOfRays*/).add(scene.ambientLight.getIntensity());
	}

	/**
	 * calculate the color of a point, recursion
	 * 
	 * @param intersection - a point of intersection
	 * @param ray          - ray of a center of pixel
	 * @param level        - level of the recursion
	 * @param k            - The multiplication factor of kR and kT
	 * @return local effect + global effect
	 */
	private Color calcColor(GeoPoint intersection, Ray ray, int level, double k/*, int numberOfRays*/) {
		Color color = intersection.geometry.getEmmission();
		color = color.add(calcLocalEffects(intersection, ray, k/*, numberOfRays*/));
		return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k/*, numberOfRays*/));
	}

	/**
	 * calc Global Effects - reflected and refracted
	 * 
	 * @param geopoint - geopoint
	 * @param ray      - Ray of a center of pixel
	 * @param level    - level of the recursion
	 * @param k        - The multiplication factor of kR and kT
	 * @return color after global effect
	 */
	private Color calcGlobalEffects(GeoPoint geopoint, Ray ray, int level, double k/*, int numberOfRays*/) {
		if (level == 1 || k < MIN_CALC_COLOR_K) {
			return Color.BLACK;
		}
		Color color = Color.BLACK;
		Vector n = geopoint.geometry.getNormal(geopoint.point);
		Material material = geopoint.geometry.getMaterial();
		// calculate reflectedd
		double kr = material.kR;
		double kkr = k * kr;
		if (kkr > MIN_CALC_COLOR_K) {
			Ray reflectedRay = constructReflectedRay(geopoint.point, ray, n);
			GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
			if (reflectedPoint != null)
				color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr/*, numberOfRays*/).scale(kr));
			else
				color = color.add(scene.background.scale(kr));
		}
		// calculate refracted
		double kt = material.kT;
		double kkt = k * kt;
		if (kkt > MIN_CALC_COLOR_K) {
			Ray refractedRay = constructRefractedRay(geopoint.point, ray, n);
			GeoPoint refractedPoint = findClosestIntersection(refractedRay);
			if (refractedPoint != null)
				color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt/*, numberOfRays*/).scale(kt));
			else
				color = color.add(scene.background.scale(kt));
		}
		return color;
	}

	/**
	 * calc Local Effects - diffusive and Specular
	 * 
	 * @param intersection - GeoPoint intersection
	 * @param ray          - Ray of a center of pixel
	 * @return calculate the part of phong model :( kd*|l*n| +ks*(-v*r)^nsh)*IL
	 */
	private Color calcLocalEffects(GeoPoint intersection, Ray ray, double k/*, int numberOfRays*/) {
		Vector v = ray.getDir();// ray direction
		Vector n = intersection.geometry.getNormal(intersection.point);
		double nv = Util.alignZero(n.dotProduct(v));
		if (Util.isZero(nv))// there is no diffusive and Specular
			return Color.BLACK;
		int nShininess = intersection.geometry.getMaterial().nShininess;
		double kd = intersection.geometry.getMaterial().kD;
		double ks = intersection.geometry.getMaterial().kS;
		Color color = Color.BLACK;
		for (LightSource lightSource : scene.lights) {
			Vector l = lightSource.getL(intersection.point);
			double nl = Util.alignZero(n.dotProduct(l));
			if (nl * nv > 0) { // sign(nl) == sing(nv)
				double ktr = transparency(l, n, intersection, lightSource/*, numberOfRays*/);
				if (ktr * k > MIN_CALC_COLOR_K) {
					Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
					color = color.add(calcDiffusive(kd, nl, lightIntensity),
							calcSpecular(ks, l, n, nl, v, nShininess, lightIntensity));
				}
			}
		}
		return color;
	}

	/**
	 * Calculate Specular component of light reflection.
	 *
	 * @param ks         specular component coef
	 * @param l          direction from light to point
	 * @param n          normal to surface at the point
	 * @param nl         dot-product n*l
	 * @param v          direction from point of view to point
	 * @param nShininess shininess level
	 * @param ip         light intensity at the point
	 * @return specular component light effect at the point
	 */
	private Color calcSpecular(double ks, Vector l, Vector n, double nl, Vector v, int nShininess, Color ip) {
		double p = nShininess;

		Vector R = l.add(n.scale(-2 * nl)); // (r= l-2 *(l*n)*n) nl must not be zero!
		double minusVR = -Util.alignZero(R.dotProduct(v));
		if (minusVR <= 0) {
			return Color.BLACK; // view from direction opposite to r vector
		}
		return ip.scale(ks * Math.pow(minusVR, p));
	}

	/**
	 * Calculate Diffusive component of light reflection.
	 *
	 * @param kd diffusive component coef
	 * @param nl dot-product n*l
	 * @param ip light intensity at the point
	 * @return diffusive component of light reflection
	 */
	private Color calcDiffusive(double kd, double nl, Color ip) {
		if (nl < 0)
			nl = -nl;
		return ip.scale(nl * kd);
	}

	/**
	 * check if there is shadow
	 * 
	 * @param l  vector l - light direction
	 * @param n  normal
	 * @param gp geo point
	 * @return if the point is unshaded or not
	 */
	private boolean unshaded(Vector l, Vector n, GeoPoint gp, LightSource lightSource) {
		Vector lightDirection = l.scale(-1); // from point to light source
		Ray lightRay = new Ray(gp.point, lightDirection, n);
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
		if (intersections == null)
			return true;// if there are not intersection points - unshadow
		double lightDistance = lightSource.getDistance(gp.point);
		for (GeoPoint g : intersections) {
			if (Util.alignZero(g.point.distance(lightRay.getP0()) - lightDistance) <= 0
					&& g.geometry.getMaterial().kT == 0)
				return false;
		}
		return true;

	}

	/**
	 * 
	 * function that creates Partial shading in case the body or bodies that block
	 * the light source from the point have transparency at some level or another
	 * 
	 * @param light
	 * @param l         -the vector from the light to the point
	 * @param n-        normal vector to the point at the geometry
	 * @param geopoint- the point in the geometry
	 * @return double number represent the shadow
	 */
	private double transparency(Vector l, Vector n, GeoPoint geopoint, LightSource lightSource/*, int numberOfRays*/) {
		double sum = 0;// sum of ktr - Coefficients
		List<Ray> rays = constructRaysToLight(lightSource, l, n, geopoint, numberOfRays);// create numberOfRays rays
		for (Ray ray : rays) { // for each ray
			List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);// calculate Intersections
			if (intersections != null)// there are intersections
			{
				double lightDistance = lightSource.getDistance(geopoint.point);
				double ktr = 1.0;
				for (GeoPoint gp : intersections) {
					if (Util.alignZero(gp.point.distance(geopoint.point) - lightDistance) <= 0) {
						//negative when the intersection point is before(from the object view) the light source
						ktr *= gp.geometry.getMaterial().kT;
						if (ktr < MIN_CALC_COLOR_K)// if we got to the minimum value of k- stop the recursion
						{
							ktr = 0.0;
							break;// stop the checking for current point
						}
					}
					// else -> the intersection point is after(from the object view) the light
					// source, there is no shadow
				}
				sum += ktr;
			} else// no intersections
			{
				sum += 1;
			}
		}
		return sum / rays.size();// Average of Coefficients
	}

	/**
	 * construct refracted ray
	 * 
	 * @param pointGeo - intersection point
	 * @param inRay    - ray v from the camera
	 * @return refracted ray
	 */
	private Ray constructRefractedRay(Point3D pointGeo, Ray inRay, Vector n) {
		Vector v = inRay.getDir();
		return new Ray(pointGeo, v, n);
	}

	/**
	 * construct reflected ray
	 * 
	 * @param pointGeo - intersection point
	 * @param inRay    - ray v from the camera
	 * @param n        - normal from the geometry in the intersection point
	 * @return reflected ray
	 */
	private Ray constructReflectedRay(Point3D pointGeo, Ray inRay, Vector n) {

		Vector v = inRay.getDir();
		double vn = v.dotProduct(n);

		if (Util.isZero(vn)) {
			return null;
		}
		Vector r = (v.subtract(n.scale(2 * vn))).normalized();
		return new Ray(pointGeo, r, n);

	}

	/**
	 * calculate intersection points with the ray and return the closest point
	 * 
	 * @param ray - ray
	 * @return Closest intersection point
	 */
	private GeoPoint findClosestIntersection(Ray ray) {

		if (ray == null) {
			return null;
		}

		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
		return ray.findClosestGeoPoint(intersections);

	}

	/**
	 * construct Rays To the Light
	 * 
	 * @param light    the light source
	 * @param l        from light source to point
	 * @param n        the normal
	 * @param geopoint the point
	 * @param number   Of Rays
	 * @return list of rays to the light
	 */
	private List<Ray> constructRaysToLight(LightSource light, Vector l, Vector n, GeoPoint geopoint, int numberOfRays) {
		Vector lightDirection = l.scale(-1); // from point to light source
		Ray lightRay = new Ray(geopoint.point, lightDirection, n);
		List<Ray> beam = new LinkedList<>();
		beam.add(lightRay);
		if (light.getRadius() == 0)
			return beam;
		Point3D p0 = lightRay.getP0();
		Vector v = lightRay.getDir();// האור של הוקטור
		//Vector vx = (new Vector(-v.getHead().getY(), v.getHead().getX(), 0)).normalized();// (-y,x,0)
		Vector vx=v.OrthogonalVector();
		Vector vy = (v.crossProduct(vx)).normalized();// v ו vx מאונך ווקטור
		double r = light.getRadius();
		Point3D pC = lightRay.getPoint(light.getDistance(p0));
		for (int i = 0; i < numberOfRays - 1; i++) {
			// create random polar system coordinates of a point in circle of radius r
			double cosTeta = ThreadLocalRandom.current().nextDouble(-1, 1);
			double sinTeta = Math.sqrt(1 - cosTeta * cosTeta);
			double d = ThreadLocalRandom.current().nextDouble(0, r);
			// Convert polar coordinates to Cartesian ones
			double x = d * cosTeta;
			double y = d * sinTeta;
			// pC - center of the circle
			// p0 - start of central ray, v - its direction, distance - from p0 to pC
			Point3D point = pC;
			if (!Util.isZero(x))
				point = point.add(vx.scale(x));
			if (!Util.isZero(y))
				point = point.add(vy.scale(y));
			beam.add(new Ray(p0, point.subtract(p0))); // normalized inside Ray ctor
		}
		return beam;

	}

}
