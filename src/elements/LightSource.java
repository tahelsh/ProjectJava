/**
 * 
 */
package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * interface of Light Source
 * @author Tahel Sharon and Ayala Israeli
 *
 */
public interface LightSource {
	/**
	 * get intensity in a point
	 * @param p a point
	 * @return intensity color in the point
	 */
	public Color getIntensity(Point3D p);
	
	/**
	 * ????
	 * @param p
	 * @return
	 */
	public Vector getL(Point3D p);


}
