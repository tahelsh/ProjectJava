package geometries;

import primitives.Vector;
import java.util.List;
import static primitives.Util.*;
import primitives.Point3D;
import primitives.Ray;

/**
 * class of the geometry of Sphere
 * 
 * @author Tahel Sharon & Ayala Israeli
 *
 */
public class Sphere extends Geometry {

	/**
	 * center point
	 */
	private Point3D center;
	/**
	 * radius value
	 */
	private double radius;

	public Sphere(Point3D center, double radius) {
		super(new Box(center.getX() - radius, center.getX() + radius, center.getY() - radius, center.getY() + radius,
				center.getZ() - radius, center.getZ() + radius));
		this.center = center;
		this.radius = radius;
	}

	/**
	 * getCenter
	 * 
	 * @return center point
	 */
	public Point3D getCenter() {
		return center;
	}

	/**
	 * getRadius
	 * 
	 * @return radius value
	 */
	public double getRadius() {
		return radius;
	}

	@Override
	public String toString() {
		return "Sphere [center=" + center + ", radius=" + radius + "]";
	}

	@Override
	public Vector getNormal(Point3D point) {
		Vector v = point.subtract(center).normalize();
		return v;
	}

	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray) {
		if (this.getBox().IntersectionBox(ray) == false)
			return null;
//		if (!IsIntersectionBox(ray))
//			return null;
		// get ray point and vector
		Point3D rayP = ray.getP0();
		Vector rayV = ray.getDir();

		// vector between p0 start and sphere center-O
		Vector l = null;
		try {
			l = center.subtract(rayP);
		} catch (Exception ex)// p0=center
		{
			// if Sphere is on ray start point then return p0 + r*V
			return List.of(new GeoPoint(this, (ray.getPoint(radius))));
		}

		// the scale for the ray in order to get parallel to the sphere center
		double tm = l.dotProduct(rayV);

		double lengthL = l.length();

		// get the distance between the ray and the sphere center
		// pitagoras
		// להוריד ערך מוחלט?
		double d2 = Math.abs(lengthL * lengthL - tm * tm);// pitagoras, d^2
		double d = Math.sqrt(d2);// d

		// the ray doesn't cross the sphere
		if (d > radius)
			return null;

		// the ray tangent the sphere
		if (isZero(d - radius)) // if d==radius
		{
			return null;
		}
		// the ray crosses the sphere in two places
		// למחוק מוחלט?
		double th = Math.sqrt(Math.abs(radius * radius - d2));
		// get the distance to the two points
		double t1 = tm - th;
		double t2 = tm + th;

		// return the points that are after the ray
		if (t1 <= 0 && t2 <= 0)
			return null;
		if (t1 > 0 && t2 > 0)
			return List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2))); // P1 , P2
		if (t1 > 0)
			return List.of(new GeoPoint(this, ray.getPoint(t1)));
		else
			return List.of(new GeoPoint(this, ray.getPoint(t2)));
	}
	@Override
	public Point3D getPositionPoint() {return center;}
}
