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
	private double kQ;
	private double kL;
	private double kC;

	/* ********* Constructors ***********/
	public PointLight(Color intensity, Point3D position, double kC, double kL, double kQ ) {
		super(intensity);
		this.position = position;
		this.kQ = kQ;
		this.kL = kL;
		this.kC = kC;
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
		if (p.equals(position)) {//if p==position because vector zero is not good!
			return null;
		}
		return p.subtract(position).normalized();
	}

	/* ************* Administration *******/
	@Override
	public String toString() {
		return "PointLight [position=" + position + ", kQ=" + kQ + ", kL=" + kL + ", kC=" + kC + ", getIntensity()="
				+ getIntensity() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

}
