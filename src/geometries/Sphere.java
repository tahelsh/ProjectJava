package geometries;
import java.util.List;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Sphere implements Geometry {

	private	Point3D center;
	private	double radius;
		
	public Sphere(Point3D center, double radius) {
		super();
		this.center = center;
		this.radius = radius;
	}
		public Point3D getCenter() {
			return center;
		}

		public double getRadius() {
			return radius;
		}
		
		@Override
		public String toString() {
			return "Sphere [center=" + center + ", radius=" + radius + "]";
		}
		
		@Override
		public Vector getNormal(Point3D point) {
			Vector v=point.subtract(center).normalize();
			return v;
		}
		@Override
		public List<Point3D> findIntersections(Ray r) {
			// TODO Auto-generated method stub
			return null;
		}
	}



