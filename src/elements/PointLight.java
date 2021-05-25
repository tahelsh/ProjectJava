/**
 * 
 */
package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * class of Point Light, extends Light and implements LightSource
 * 
 * @author Tahel Sharon and Ayala Israeli
 *
 */
public class PointLight extends Light implements LightSource {

	private Point3D position;
	private double kQ = 0;
	private double kL = 0;
	private double kC = 1;

	/* ********* Constructors ***********/
	public PointLight(Color intensity, Point3D position) {
		super(intensity);
		this.position = position;
	}

	public PointLight(Color intensity, Point3D position, double radius) {
		super(intensity, radius);
		this.position = position;
	}

	/* ************* Getters/Setters *******/
	@Override
	public Color getIntensity(Point3D p) {
		double distance2 = p.distanceSquared(position);// d^2
		double distance = p.distance(position);
		return getIntensity().scale(1 / (kC + kL * distance + kQ * distance2));
	}

	@Override
	public Vector getL(Point3D p) {
		if (p.equals(position)) {// if p==position because vector zero is not good!
			return null;
		}
		return p.subtract(position).normalized();
	}

	/**
	 * set kQ
	 * 
	 * @param kQ kQ value
	 * @return the point light object
	 */
	public PointLight setkQ(double kQ) {
		this.kQ = kQ;
		return this;
	}

	/**
	 * set kL
	 * 
	 * @param kL kL value
	 * @return the point light object
	 */
	public PointLight setkL(double kL) {
		this.kL = kL;
		return this;
	}

	/**
	 * set kC
	 * 
	 * @param kC kC value
	 * @return the point light object
	 */
	public PointLight setkC(double kC) {
		this.kC = kC;
		return this;
	}

	/* ************* Administration *******/
	@Override
	public String toString() {
		return "PointLight [position=" + position + ", kQ=" + kQ + ", kL=" + kL + ", kC=" + kC + ", getIntensity()="
				+ getIntensity() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

	@Override
	public double getDistance(Point3D point) {
		return position.distance(point);
	}

}
