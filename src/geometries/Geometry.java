package geometries;
import primitives.Vector;

import primitives.Point3D;
/**
 * interface of Geometry
 * @author Tahel Sharon 323125153 && Ayala Israeli 324207232
 *
 */

public interface Geometry {

	/**
	 * return vector normal of the geometry
	 * @param p
	 * @return Vector normal for point
	 */
	public Vector get_Normal(Point3D p);
}
