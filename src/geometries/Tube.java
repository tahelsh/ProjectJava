package geometries;

import primitives.Point3D;
import static primitives.Util.*;

import java.util.List;

import primitives.Ray;
import primitives.Vector;
//גליל אינסופי

/**
 * class of Tube, implements from Geometry interface
 * 
 * @author Tahel Sharon & Ayala Israeli
 *
 */
public class Tube extends Geometry {
	/**
	 * the ray
	 */
	protected Ray axisRay;
	/**
	 * radius value
	 */
	protected double radius;

	/**
	 * constructor from ray and radius value
	 * 
	 * @param axisRay
	 * @param radius
	 */
	public Tube(Ray axisRay, double radius) {
		super(null);
		this.axisRay = axisRay;
		this.radius = radius;
	}

	/**
	 * 
	 * @return axisRay
	 */
	public Ray getAxisRay() {
		return axisRay;
	}

	/**
	 * 
	 * @return radius
	 */
	public double getRadius() {
		return radius;
	}

	@Override
	public String toString() {
		return "Tube [axisRay=" + axisRay + ", radius=" + radius + "]";
	}

	@Override
	public Vector getNormal(Point3D point) {
		Point3D o = axisRay.getP0();
		Vector v = axisRay.getDir();
		double t = point.subtract(o).dotProduct(v);
		if (!isZero(t)) {
			// o=o.add(v.scale(t))
			o = axisRay.getPoint(t);
			return point.subtract(o).normalize();
		}
		// if the point is on the same level then return normal
		return point.subtract(axisRay.getP0()).normalize();
	}

	@Override
	public List<GeoPoint> findGeoIntersections(Ray r) {
		// TODO Auto-generated method stub
		return null;
	}
}
