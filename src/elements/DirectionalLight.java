/**
 * 
 */
package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * class of Directional Light, extends Light and implements LightSource
 * @author Tahel Sharon and Ayala Israeli
 *
 */
public class DirectionalLight extends Light implements LightSource {
	
	private Vector direction;
	
	/* ********* Constructors ***********/
	
	/**
	 * C-TOR that gets intensity color and direction vector
	 * @param intensity intensity color
	 * @param direction direction vector
	 */
	public DirectionalLight(Color intensity, Vector direction) {
		super(intensity);
		this.direction = direction.normalized();
	}


	/* ************* Getters/Setters *******/
	@Override
	public Color getIntensity(Point3D p) {
		return super.getIntensity(); //return the intensity
	}

	@Override
	public Vector getL(Point3D p) { 
		return direction;//return the direction vector of the light
	}

	 /* ************* Administration *******/
	@Override
	public String toString() {
		return "DirectionalLight [direction=" + direction + ", getIntensity()=" + getIntensity() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	@Override
	public double getDistance(Point3D point) {
		return  Double.POSITIVE_INFINITY;
	}
	
	@Override 
	public double getRadius() {
		return 0;
	}
	
	

}
