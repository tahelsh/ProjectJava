package geometries;

import primitives.Point3D;
import java.util.List;
import static primitives.Util.*;
import primitives.Vector;
import primitives.Ray;

/**
 * class of Triangle, exstends from Polygon
 * 
 * @author Tahel Sharon & Ayala Israeli
 *
 */
public class Triangle extends Polygon {

	/**
	 * constructor that gets 3 point3D
	 * 
	 * @param p0
	 * @param p1
	 * @param p2
	 * @throws IllegalArgumentException
	 */
	public Triangle(Point3D p0, Point3D p1, Point3D p2) throws IllegalArgumentException {
		super(p0, p1, p2);
	}

	@Override
	public String toString() {
		return "Triangle [vertices=" + vertices + ", plane=" + plane + "]";
	}

	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray) {
		if (this.getBox().IntersectionBox(ray) == false)
			return null;
		List<GeoPoint> intersections = plane.findGeoIntersections(ray);
		if (intersections == null)
			return null;
		Point3D p0 = ray.getP0();
		Vector v = ray.getDir();
		Vector v1 = vertices.get(0).subtract(p0);
		Vector v2 = vertices.get(1).subtract(p0);
		Vector v3 = vertices.get(2).subtract(p0);
		// v1.crossProduct(v2)-> N1: normal to the plane of v1,v2
		double s1 = v.dotProduct(v1.crossProduct(v2));
		// if v.dotProduct(N1)==0 -> v contain in plane of v1, v2 ,so we dont have
		// Intsersections points
		if (isZero(s1))
			return null;
		double s2 = v.dotProduct(v2.crossProduct(v3));
		if (isZero(s2))
			return null;
		double s3 = v.dotProduct(v3.crossProduct(v1));
		if (isZero(s3))
			return null;
		// The point is inside if all v*Ni have the same sign (+/-)
		if ((s1 > 0 && s2 > 0 && s3 > 0) || (s1 < 0 && s2 < 0 && s3 < 0)) {
			for (GeoPoint geo : intersections) {
				geo.geometry = this;
			}
			return intersections;
		}
		return null;
	}

}
