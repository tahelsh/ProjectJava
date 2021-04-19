package scene;

import primitives.*;
import elements.AmbientLight;
import geometries.Geometries;

/**
 * class of Scene
 * @author Tahel Sharon & Ayala Israeli
 *
 */
public class Scene {

	public String name;
	public Color background;
	public AmbientLight ambientLight = new AmbientLight(Color.BLACK, 1);
	public Geometries geometries;

	/**
	 * CTOR
	 * @param name
	 */
	public Scene(String name) {
		this.name = name;
		this.geometries = new Geometries();

	}
	 /**
     * set scene background
     * @param background the background color
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * set scene light
     * @param light the light
     */
    public Scene setAmbientLight(AmbientLight light) {
        this.ambientLight = light;
        return this;

    }
    /**
     * set scene geometries
     * @param light the geometries
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;

    }

}
