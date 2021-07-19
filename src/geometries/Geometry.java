package geometries;

import primitives.Vector;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Ray;

/**
 * interface of Geometry
 * 
 * @author Tahel Sharon 323125153 && Ayala Israeli 324207232
 *
 */

public abstract class Geometry extends Intersectable {
	private Color emmission = Color.BLACK;
	private Material material=new Material();

	/**
	 * C-TOR that gets a box
	 * @param box a box
	 */
	public Geometry(Box box)
	{
		super(box);
	}
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
	 * @return emmision
	 */
	public Color getEmmission() {
		return emmission;
	}

	/**
	 * Set emmission 
	 * @param emmission
	 * @return the geometry
	 */
	public Geometry setEmmission(Color emmission) {
		 this.emmission = emmission;
		 return this;
	}

	/**
	 * get material
	 * @return the material
	 */
	public Material getMaterial() {
		return material;
	}

	/**
	 * set material
	 * @param material
	 * @return the geometry
	 */
	public Geometry setMaterial(Material material) {
		this.material = material;
		return this;
	}
	/**
	 * get box
	 * @return the box
	 */
	public Box getBox() {
		return box;
	}

	/**
	 * set box
	 * @param box
	 * @return the geometry
	 */
	public Geometry setBox(Box box) {
		this.box = box;
		return this;
	}

}
