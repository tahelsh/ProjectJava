package geometries;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Tube implements Geometry {
	Ray axisRay;
	double radius;
	
	public Tube(Ray axisRay, double radius) {
		super();
		this.axisRay = axisRay;
		this.radius = radius;
	}

	public Ray getAxisRay() {
		return axisRay;
	}

	public double getRadius() {
		return radius;
	}

	@Override
	public String toString() {
		return "Tube [axisRay=" + axisRay + ", radius=" + radius + "]";
	}
	
	@Override
	public Vector get_Normal(Point3D point) {
		return null;
	}
}
