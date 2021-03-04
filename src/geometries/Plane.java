package geometries;
import primitives.Point3D;
import primitives.Vector;
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
	public Plane(Point3D q0,Point3D q1,Point3D q2 ) {
		super();
		this.q0 = q0;
		this.normal = null;
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
		return null;
	}
	
	@Override
	public String toString() {
		return "Plane [" + (q0 != null ? "q0=" + q0 + ", " : "") + (normal != null ? "normal=" + normal : "") + "]";
	}

}
