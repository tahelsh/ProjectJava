package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;
/**
 * class of the geometry of Cylinder
 * @author Tahel Sharon & Ayala Israeli
 *
 */
public class Cylinder extends Tube {
	/**
	 * height value
	 */
	private double height;

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
	public Vector getNormal(Point3D point) {
		if(point.equals(axisRay.getP0()))//התחתון הבסיס מרכז זה שקיבלנו הנקודה אם
			return axisRay.getDir().scale(-1).normalize();
		Point3D centerSecondBase=axisRay.getP0().add(axisRay.getDir().scale(height));
		if(point.equals(centerSecondBase))//העליון הבסיס מרכז זה שקיבלנו הנקודה אם
			return axisRay.getDir().normalize();
		if(point.subtract(axisRay.getP0()).length()<radius && isZero(point.subtract(axisRay.getP0()).dotProduct(axisRay.getDir())))//בסיס תחתון
			return axisRay.getDir().scale(-1).normalize();
		Vector v=point.subtract(axisRay.getP0().add(axisRay.getDir().scale(height)));//העליון הבסיס מרכז לבין שקיבלנו הנקודה בין ווקטור
		if(v.length()<radius && isZero(v.dotProduct(axisRay.getDir())))//בסיס עליון
			return axisRay.getDir().normalize();
		if(point.subtract(axisRay.getP0()).length()==radius && isZero(point.subtract(axisRay.getP0()).dotProduct(axisRay.getDir())))
			return point.subtract(axisRay.getP0()).normalize();
		return super.getNormal(point);
		
	}

}
