package geometries;

import java.util.List;
import static primitives.Util.*;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * class of Plane, implements from Geometry interface
 * 
 * @author Tahel Sharon & Ayala Israeli
 *
 */
public class Plane extends Geometry {

	/**
	 * point p0
	 */
	protected Point3D q0;
	/**
	 * vector normal
	 */
	protected Vector normal;

	/**
	 * constructor that get point3D and vector normal
	 * 
	 * @param q0
	 * @param normal
	 */
	public Plane(Point3D q0, Vector normal) {
		super(new Box(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY,
				Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY));
		this.q0 = q0;
		this.normal = normal.normalize();
	}

	/**
	 * constructor that gets 3 points3D, the first is the q0 and constuct the normal
	 * from the others points
	 * 
	 * @param q0
	 * @param q1
	 * @param q2
	 */
	public Plane(Point3D p1, Point3D p2, Point3D p3) {
		super(new Box(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY,
				Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY));
		try {
			this.q0 = p1;
			Vector v1 = p1.subtract(p2);// ������ 1
			Vector v2 = p1.subtract(p3);// ������ 2
			normal = v1.crossProduct(v2).normalized();// ������ �� ���� �������� 2 ��� �������� �����
		} catch (IllegalArgumentException exc) {
			throw new IllegalArgumentException("This is not a Plane/Triangle");
		}
	}

	/**
	 * 
	 * @return q0
	 */
	public Point3D getQ0() {
		return q0;
	}

	/**
	 * 
	 * @return normal
	 */
	public Vector getNormal() {
		return normal;
	}

	@Override
	public Vector getNormal(Point3D point) {
		return normal;
	}

	@Override
	public String toString() {
		return "Plane [" + (q0 != null ? "q0=" + q0 + ", " : "") + (normal != null ? "normal=" + normal : "") + "]";
	}

	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray) {
		if (this.getBox().IntersectionBox(ray) == false)
			return null;

		// get ray point and vector
		Point3D rayP = ray.getP0();
		Vector rayV = ray.getDir();

		// check if the ray is parallel to the plane
		if (isZero(normal.dotProduct(rayV))) // dotProduct = 0 => parallel
			return null;

		try {
			double t = (normal.dotProduct(q0.subtract(rayP))) / (normal.dotProduct(rayV));

			if (isZero(t))
				// the ray starts on the plane
				return null;
			else if (t > 0.0) // the ray crosses the plane
				return List.of(new GeoPoint(this, (ray.getPoint(t))));
			else // the ray doesn't cross the plane
				return null;

		} catch (IllegalArgumentException ex) {
			// _p.subtract(rayP) is vector zero, which means the ray point is equal to the
			// plane point (ray start on plane)
			return null;
		}

	}
	
}
