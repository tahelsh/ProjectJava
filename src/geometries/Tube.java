package geometries;
import primitives.Point3D;
import static primitives.Util.*;
import primitives.Ray;
import primitives.Vector; 

/**
 * class of Tube, implements from Geometry interface
 * @author Tahel Sharon & Ayala Israeli
 *
 */
public class Tube implements Geometry {
	/**
	 * the ray
	 */
	Ray axisRay;
	/**
	 * radius value
	 */
	double radius;
	
	/**
	 * constructor from ray and radius value
	 * @param axisRay
	 * @param radius
	 */
	public Tube(Ray axisRay, double radius) {
		super();
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
	public Vector get_Normal(Point3D point) {
		Point3D o=axisRay.getp0();
	    Vector v=axisRay.getDir();
	    double t=point.subtract(o).dotProduct(v);
	    if(!isZero(t))
	    {
	       o=o.add(v.scale(t));
	       return point.subtract(o).normalize();
	    }
	  //if the point is on the same level then return normal
        return point.subtract(axisRay.getp0()).normalized();
	}
}
