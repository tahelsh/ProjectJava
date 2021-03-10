package geometries;
import primitives.Point3D;
import primitives.Vector;

public class Sphere implements Geometry {

		Point3D center;
		double radius;
		
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
		public Vector get_Normal(Point3D point) {
			Vector v=point.subtract(center).normalize();
			return v;
		}
	}



