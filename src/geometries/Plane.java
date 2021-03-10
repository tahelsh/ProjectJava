package geometries;
//import primitives.Point3D;
//import primitives.Vector;
import  primitives.*;
/**
 * class of Plane, implements from Geometry interface
 * @author Tahel Sharon & Ayala Israeli
 *
 */
public class Plane implements Geometry {
	
	/**
	 * point p0
	 */
	Point3D q0;
	/**
	 * vector normal
	 */
	Vector normal;

	/**
	 * constructor that get point3D and vector normal
	 * @param q0
	 * @param normal
	 */
	public Plane(Point3D q0, Vector normal) {
		super();
		this.q0 = q0;
		this.normal = normal;
	}
	/**
	 * constructor that gets 3 points3D, the first is the q0 and constuct the normal from the others points
	 * @param q0
	 * @param q1
	 * @param q2
	 */
	public Plane(Point3D p1,Point3D p2,Point3D p3) {
		try {
			this.q0=p1;
			Vector v1=p1.subtract(p2);
			Vector v2=p1.subtract(p3);		
			normal=v1.crossProduct(v2).normalized();
			}
	        catch (IllegalArgumentException exc)
			{
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
	public Vector get_Normal(Point3D point) {
		return normal;
	}
	
	@Override
	public String toString() {
		return "Plane [" + (q0 != null ? "q0=" + q0 + ", " : "") + (normal != null ? "normal=" + normal : "") + "]";
	}

}
