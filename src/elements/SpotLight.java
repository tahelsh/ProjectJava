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
	 * C-TOR that gets intensity light, position point and direction vector
	 * @param intensity intensity color
	 * @param position position point
	 * @param direction direction vector
	 */
	public SpotLight(Color intensity, Point3D position, Vector direction) {
		super(intensity, position);
		this.direction = direction.normalized();
	}

	/**
	 * C-TOR that gets intensity light, position point, direction vector and radius value
	 * @param intensity intensity color
	 * @param position position point
	 * @param direction direction vector
	 * @param radius radius value
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
