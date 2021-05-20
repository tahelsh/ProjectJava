/**
 * 
 */
package renderer;

import primitives.*;

import java.util.List;

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
	private static final double DELTA = 0.1;
	private static final int MAX_CALC_COLOR_LEVEL = 10;
	private static final double MIN_CALC_COLOR_K = 0.001;

	/**
	 * constructor that gets a scene
	 * 
	 * @param scene
	 */
	public RayTracerBasic(Scene scene) {
		super(scene);
	}

	@Override
	public Color traceRay(Ray ray) {
//		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
//		if (intersections == null)
//			return this.scene.background;
//		GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
//		return calcColor(closestPoint, ray);// calculate the color of the point
		GeoPoint closestPoint = findClosestIntersection(ray);
		if (closestPoint == null)
			return scene.background;
		return calcColor(closestPoint, ray);
	}

	/**
	 * calculate the color of a point
	 * 
	 * @param geopoint - a point of intersection
	 * @param ray      - ray of a center of pixel
	 * @return the color of the point by phong model + reflacted and refracted
	 */
	private Color calcColor(GeoPoint geopoint, Ray ray) {
		return calcColor(geopoint, ray, MAX_CALC_COLOR_LEVEL, 1.0).add(scene.ambientLight.getIntensity());
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
	private Color calcColor(GeoPoint intersection, Ray ray, int level, double k) {
		Color color = intersection.geometry.getEmmission();
		color = color.add(calcLocalEffects(intersection, ray));
		return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k));
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
	private Color calcGlobalEffects(GeoPoint geopoint, Ray ray, int level, double k) {
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
				color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
			else
				color=color.add(scene.background.scale(kr));
		}
		// calculate refracted
		double kt = material.kT;
		double kkt = k * kt;
		if (kkt > MIN_CALC_COLOR_K) {
			Ray refractedRay = constructRefractedRay(geopoint.point, ray, n);
			GeoPoint refractedPoint = findClosestIntersection(refractedRay);
			if (refractedPoint != null)
				color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
			else
				color=color.add(scene.background.scale(kt));
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
	private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
		Vector v = ray.getDir();// ray direction
		Vector n = intersection.geometry.getNormal(intersection.point);
		double nv = Util.alignZero(n.dotProduct(v));
		if (nv == 0)// there is no diffusive and Specular
			return Color.BLACK;
		int nShininess = intersection.geometry.getMaterial().nShininess;
		double kd = intersection.geometry.getMaterial().kD;
		double ks = intersection.geometry.getMaterial().kS;
		Color color = Color.BLACK;
		for (LightSource lightSource : scene.lights) {
			Vector l = lightSource.getL(intersection.point);
			double nl = Util.alignZero(n.dotProduct(l));
			if (nl * nv > 0) { // sign(nl) == sing(nv)
				if (unshaded(l, n, intersection, lightSource)) {
					Color lightIntensity = lightSource.getIntensity(intersection.point);
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
	 * @author Dan Zilberstein
	 *         <p>
	 *         Finally, the Phong model has a provision for a highlight, or
	 *         specular, component, which reflects light in a shiny way. This is
	 *         defined by [rs,gs,bs](-V.R)^p, where R is the mirror reflection
	 *         direction vector we discussed in class (and also used for ray
	 *         tracing), and where p is a specular power. The higher the value of p,
	 *         the shinier the surface.
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
	 * @author Dan Zilberstein
	 *         <p>
	 *         The diffuse component is that dot product n�L that we discussed in
	 *         class. It approximates light, originally from light source L,
	 *         reflecting from a surface which is diffuse, or non-glossy. One
	 *         example of a non-glossy surface is paper. In general, you'll also
	 *         want this to have a non-gray color value, so this term would in
	 *         general be a color defined as: [rd,gd,bd](n�L)
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
	 * @return
	 */
	private boolean unshaded(Vector l, Vector n, GeoPoint gp, LightSource lightSource) {
		Vector lightDirection = l.scale(-1); // from point to light source
		Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);// where we need to move the point
		Point3D point = gp.point.add(delta);// moving the point
		Ray lightRay = new Ray(point, lightDirection);// the new ray after the moving
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
		if (intersections == null)
			return true;// if there are not intersection points - unshadow
		double lightDistance = lightSource.getDistance(gp.point);
		for (GeoPoint g : intersections) {
			if (Util.alignZero(g.point.distance(point) - lightDistance) <= 0 && g.geometry.getMaterial().kT == 0)
				return false;
		}
		return true;

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
		// Vector r = v;
		Vector delta = n.scale(n.dotProduct(v) > 0 ? DELTA : -DELTA);// where we need to move the point
		Point3D pointDelta = pointGeo.add(delta);// moving the pointF
		return new Ray(pointDelta, inRay.getDir());
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
		Vector delta = n.scale(n.dotProduct(r) > 0 ? DELTA : -DELTA);// where we need to move the point
		Point3D pointDelta = pointGeo.add(delta);// moving the point
		return new Ray(pointDelta, r);

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

//		GeoPoint closestPoint = null;
//		double closestDistance = Double.MAX_VALUE;
//		Point3D ray_p0 = ray.getP0();

		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
		return ray.findClosestGeoPoint(intersections);
//		if (intersections == null)
//			return null;
//
//		for (GeoPoint geoPoint : intersections) {
//			double distance = ray_p0.distance(geoPoint.point);
//			if (distance < closestDistance) {
//				closestDistance = distance;
//				closestPoint = geoPoint;
//			}
//		}
//		return closestPoint;
	}
	// }

}
