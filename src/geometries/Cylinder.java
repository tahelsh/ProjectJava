package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
/**
 * class of the geometry of Cylinder
 * @author Tahel Sharon & Ayala Israeli
 *
 */
public class Cylinder extends Tube {
	/**
	 * height value
	 */
	double height;

	/**
	 * constructor of cylinder, gets ray, radius and heighth
	 * @param axisRay
	 * @param radius
	 * @param height
	 */
	public Cylinder(Ray axisRay, double radius, double height) {
		super(axisRay, radius);
		this.height = height;
	}

	/**
	 * 
	 * @return height
	 */
	public double getHeight() {
		return height;
	}

	@Override
	public String toString() {
		return "Cylinder [height=" + height + "]";
	}
	
	@Override
	public Vector get_Normal(Point3D point) {
		return null;
	}

}
