package geometries;

import java.util.List;
import java.util.stream.Collectors;

import primitives.*;

/**
 * interface of intersectable
 * 
 * @author Tahel Sharon & Ayala Israeli
 *
 */
public interface Intersectable {
	

	/**
	 * Class of GeoPoint - contain geometry and point
	 * 
	 * @author Tahel Sharon & Ayala Israeli
	 *
	 */
	public static class GeoPoint {
		public Geometry geometry;
		public Point3D point;

		/* ********* Constructors ***********/
		/**
		 * CTOR that gets geomerty and point
		 * 
		 * @param geometry
		 * @param point
		 */
		public GeoPoint(Geometry geometry, Point3D point) {
			this.geometry = geometry;
			this.point = point;
		}
		/* ************** Admin *****************/

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			GeoPoint geoPoint = (GeoPoint) o;
			return geometry==geoPoint.geometry && point.equals(geoPoint.point);
		}

		@Override
		public String toString() {
			return "GP{" + "G=" + geometry + ", P=" + point + '}';
		}
	}

	/**
	 * function that finds intersections points
	 * 
	 * @param r ray
	 * @return list of all intersections points
	 */
	default List<Point3D> findIntersections(Ray ray) {
		List<GeoPoint> geoList = findGeoIntersections(ray);
		return geoList == null ? null
		: geoList .stream()
		.map(gp -> gp.point)
		.collect(Collectors.toList());
		}
	 //List<Point3D> findIntersections(Ray ray); //{
	//    var geoList = findGeoIntersections(ray);
	//    return geoList == null ? null: geoList.stream().map(gp -> gp.point).collect(Collectors.toList());
	//}


	/**
	 * function that finds intersections geo points
	 * 
	 * @param r ray
	 * @return list of all intersections geo points
	 */
	public List<GeoPoint> findGeoIntersections(Ray r);

}
