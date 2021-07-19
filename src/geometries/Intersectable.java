package geometries;

import java.util.List;
import java.util.stream.Collectors;

import geometries.Intersectable.Box;
import primitives.*;

/**
 * interface of intersectable
 * 
 * @author Tahel Sharon & Ayala Israeli
 *
 */
public abstract class Intersectable {

	protected Box box=null;//a box- for BVH
	
	/**
	 * C-TOR that gets a box
	 * @param box a box
	 */
	public Intersectable(Box box)
	{
		this.box=box;
	}
	
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
			return geometry == geoPoint.geometry && point.equals(geoPoint.point);
		}

		@Override
		public String toString() {
			return "GP{" + "G=" + geometry + ", P=" + point + '}';
		}
	}
    /**
     * Class of Box for BVH - contains 6 double values of x,y,z minimum and maximum
     * @author Tahel Sharon & Ayala Israeli
     *
     */
	public static class Box {// for MP2

		protected final double x0;// x minimum
		protected final double x1;// x max

		protected final double y0;// y minimum
		protected final double y1;// y max

		protected final double z0;// z minimum
		protected final double z1;// z max

		/**
		 * Constructor, build a box around among of shapes
		 * 
		 * @param x0 x minimum
		 * @param x1 x max
		 * @param y0 y minimum
		 * @param y1 y max
		 * @param z0 z minimum
		 * @param z1 z max
		 */
		public Box(double x0, double x1, double y0, double y1, double z0, double z1) {
			super();
			this.x0 = x0;
			this.x1 = x1;
			this.y0 = y0;
			this.y1 = y1;
			this.z0 = z0;
			this.z1 = z1;
		}

		// Getters
		/**
		 * Get x0
		 * @return x0 value
		 */
		public double getX0() {
			return x0;
		}

		/**
		 * Get x1
		 * @return x1 value
		 */
		public double getX1() {
			return x1;
		}

		/**
		 * Get y0
		 * @return y0 value
		 */
		public double getY0() {
			return y0;
		}

		/**
		 * Get y1
		 * @return y1 value
		 */
		public double getY1() {
			return y1;
		}

		/**
		 * Get z0
		 * @return z0 value
		 */
		public double getZ0() {
			return z0;
		}

		/**
		 * Get z1
		 * @return z1 value
		 */
		public double getZ1() {
			return z1;
		}

		/**
		 * Returns true if the ray intersects the box
		 * 
		 * @param r ray
		 * @return True/False
		 */
		public boolean IntersectionBox(Ray r) {

			double txmin = (x0 - r.getP0().getX()) / r.getDir().getHead().getX();
			double txmax = (x1 - r.getP0().getX()) / r.getDir().getHead().getX();
			if (txmin > txmax) {
				double temp = txmin;
				txmin = txmax;
				txmax = temp;
			}
			double tymin = (y0 - r.getP0().getY()) / r.getDir().getHead().getY();
			double tymax = (y1 - r.getP0().getY()) / r.getDir().getHead().getY();
			if (tymin > tymax) {
				double temp = tymin;
				tymin = tymax;
				tymax = temp;
			}
			if ((txmin > tymax) || (tymin > txmax))
				return false;
			if (tymin > txmin)
				txmin = tymin;
			if (tymax < txmax)
				txmax = tymax;
			double tzmin = (z0 - r.getP0().getZ()) / r.getDir().getHead().getZ();
			double tzmax = (z1 - r.getP0().getZ()) / r.getDir().getHead().getZ();
			if (tzmin > tzmax) {
				double temp = tzmin;
				tzmin = tzmax;
				tzmax = temp;
			}
			if ((txmin > tzmax) || (tzmin > txmax))
				return false;
			if (tzmin > txmin)
				txmin = tzmin;
			if (tzmax < txmax)
				txmax = tzmax;
			return true;
		}

	}
	
	//*************Getters and Setters**************//
		/**
		 * Get box
		 * @return the box
		 */
		public Box getBox() {
			return box;
		}

		/**
		 * set box
		 * @param box
		 * @return the geometry
		 */
		public Intersectable setBox(Box box) {
			this.box = box;
			return this;
		}
	
	//********************* functions*****************//

	/**
	 * function that finds intersections points
	 * 
	 * @param r ray
	 * @return list of all intersections points
	 */
	public List<Point3D> findIntersections(Ray ray) {
		List<GeoPoint> geoList = findGeoIntersections(ray);
		return geoList == null ? null : geoList.stream().map(gp -> gp.point).collect(Collectors.toList());
	}

	/**
	 * function that finds intersections geo points
	 * 
	 * @param r ray
	 * @return list of all intersections geo points
	 */
	public abstract List<GeoPoint> findGeoIntersections(Ray r);

}
