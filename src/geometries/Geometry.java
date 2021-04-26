package geometries;

import primitives.Vector;
import primitives.Color;
import primitives.Point3D;

/**
 * interface of Geometry
 * 
 * @author Tahel Sharon 323125153 && Ayala Israeli 324207232
 *
 */

public abstract class Geometry implements Intersectable {
	Color emmission = Color.BLACK;

	/**
	 * calculate a vector normal of a geometry
	 * 
	 * @param p
	 * @return Vector normal for point
	 */
	public abstract Vector getNormal(Point3D p);
	
	
	// ***************** Getters/Setters ********************** //

	/**
	 * Get emmission
	 * 
	 * @return
	 */
	public Color getEmmission() {
		return emmission;
	}

	/**
	 * Set emmission
	 * 
	 * @param emmission
	 */
	public Geometry setEmmission(Color emmission) {
		 this.emmission = emmission;
		 return this;
	}
}
