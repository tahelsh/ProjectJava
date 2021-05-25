/**
 * 
 */
package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * class of SpotLigth, extends PointLight
 * 
 * @author Tahel Sharon and Ayala Israeli
 *
 */
public class SpotLight extends PointLight {
	private Vector direction;

	/* ********* Constructors ***********/
	/**
	 * 
	 * @param intensity
	 * @param position
	 * @param direction
	 */
	public SpotLight(Color intensity, Point3D position, Vector direction) {
		super(intensity, position);
		this.direction = direction.normalized();
	}

	/**
	 * 
	 * @param intensity
	 * @param position
	 * @param direction
	 * @param radius
	 */
	public SpotLight(Color intensity, Point3D position, Vector direction, double radius) {
		super(intensity, position, radius);
		this.direction = direction.normalized();
	}

	/* ************* Get *******/
	@Override
	public Color getIntensity(Point3D p) {
		double pl = direction.dotProduct(getL(p));// dir*L
		// if(pl<0) pl=0;
		if (pl <= 0)
			return Color.BLACK;
		return super.getIntensity(p).scale(pl);
	}

}
