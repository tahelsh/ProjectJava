package scene;

import primitives.*;

import java.util.LinkedList;
import java.util.List;

import elements.AmbientLight;
import elements.LightSource;
import geometries.Geometries;

/**
 * class of Scene-PDS
 * @author Tahel Sharon & Ayala Israeli
 *
 */
public class Scene {

	public String name;
	public Color background = Color.BLACK;
	public AmbientLight ambientLight = new AmbientLight(Color.BLACK, 1);
	public Geometries geometries;
	public List<LightSource> lights=new LinkedList<LightSource>();

	/**
	 * CTOR that gets the name of the scene
	 * @param name
	 */
	public Scene(String name) {
		this.name = name;
		this.geometries = new Geometries();

	}
	 /**
     * set scene background
     * @param background the background color
     * @return the scene
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * set scene light
     * @param light the ambient light
     * @return the scene
     */
    public Scene setAmbientLight(AmbientLight light) {
        this.ambientLight = light;
        return this;

    }
    /**
     * set scene geometries
     * @param light the geometries
     * @return the scene
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;

    }
    /**
     * set scene lights
     * @param lights list of lights
     * @return the scene
     */
	public Scene setLights(List<LightSource> lights) {
		this.lights = lights;
		return this;
	}

}
