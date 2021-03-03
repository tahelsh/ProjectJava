package geometries;
import primitives.Point3D;
import primitives.Vector;

public class Plane implements Geometry {
	
	Point3D q0;
	Vector normal;

	public Plane(Point3D q0, Vector normal) {
		super();
		this.q0 = q0;
		this.normal = normal;
	}
	public Plane(Point3D q0,Point3D q1,Point3D q2 ) {
		super();
		this.q0 = q0;
		this.normal = null;
	}
	public Point3D getQ0() {
		return q0;
	}
	
	public Vector getNormal() {
		return normal;
	}
	
	@Override 
	public Vector get_Normal(Point3D point) {
		return null;
	}
	
	@Override
	public String toString() {
		return "Plane [" + (q0 != null ? "q0=" + q0 + ", " : "") + (normal != null ? "normal=" + normal : "") + "]";
	}

}
