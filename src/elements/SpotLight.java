/**
 * 
 */
package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**class of SpotLigth, extends PointLight
 * @author Tahel Sharon and Ayala Israeli
 *
 */
public class SpotLight extends PointLight {
	private Vector direction;
	
	/* ********* Constructors ***********/
	/**
	 * C-TOR that gets the value of all the fields
	 * @param intensity intensity color
	 * @param position position point
	 * @param kQ kQ value
	 * @param kL kL value
	 * @param kC kC value
	 * @param direction direction vector of the spot
	 */
	public SpotLight(Color intensity, Point3D position, double kQ, double kL, double kC, Vector direction) {
		super(intensity, position, kQ, kL, kC);
		this.direction = direction.normalized();
	}

	/* ************* Get *******/
	@Override
    public Color getIntensity(Point3D p) {
    	double pl =direction.dotProduct(getL(p));//dir*L
    	//if(pl<0) pl=0;     
        if (pl <= 0)
            return Color.BLACK;
        return super.getIntensity(p).scale(pl);
    }
	
	

}
