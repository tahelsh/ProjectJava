/**
 * 
 */
package renderer;

import primitives.Color;
import primitives.Ray;
import geometries.Intersectable.GeoPoint;
import java.util.MissingResourceException;

import elements.Camera;
import scene.Scene;

/**
 * class of render
 * 
 * @author Tahel Sharon and Ayala Israeli
 *
 */
public class Render {
	//private Scene scene;// scene
	private Camera camera;// camera
	private ImageWriter imageWriter;// imagewriter
	private RayTracerBase rayTracer;// rayTracer

	// ***************** Setters ********************** //
	/**
	 * set value of scene
	 * 
	 * @param scene
	 * @return the render
	 */
	//public Render setScene(Scene scene) {
	//	this.scene = scene;
	//	return this;
	//}

	/**
	 * set value of Camera
	 * 
	 * @param camera
	 * @return the render
	 */
	public Render setCamera(Camera camera) {
		this.camera = camera;
		return this;
	}

	/**
	 * set value of imageWriter
	 * 
	 * @param imageWriter
	 * @return the render
	 */
	public Render setImageWriter(ImageWriter imageWriter) {
		this.imageWriter = imageWriter;
		return this;
	}

	/**
	 * set value of rayTracer
	 * 
	 * @param rayTracer
	 * @return the render
	 */
	public Render setRayTracer(RayTracerBase rayTracer) {
		this.rayTracer = rayTracer;
		return this;
	}

	/**
	 * render image-paint the pixels
	 * 
	 * @throws MissingResourceException
	 */
	public void renderImage() throws MissingResourceException {
		//if (scene == null)
		//	throw new MissingResourceException("The value of scene is null", "Render", null);
		if (camera == null)
			throw new MissingResourceException("The value of camera is null", "Render", null);
		if (imageWriter == null)
			throw new MissingResourceException("The value of imageWriter is null", "Render", null);
		if (rayTracer == null)
			throw new MissingResourceException("The value of rayTracer is null", "Render", null);

		int Nx = imageWriter.getNx();// Nx= how many columns
		int Ny = imageWriter.getNy();// Ny= how many rows
		for (int i = 0; i < Ny; i++) {
			for (int j = 0; j < Nx; j++) {
				Ray ray = camera.constructRayThroughPixel(Nx, Ny, j, i); // create the ray from camera to view plane
				imageWriter.writePixel(j, i, rayTracer.traceRay(ray)); // write this pixels
			}
		}
	}

	/**
	 * prints grid
	 * 
	 * @param interval the interval of the grid (
	 * @param color color of the grid
	 * @throws MissingResourceException
	 */
	public void printGrid(int interval, Color color) throws MissingResourceException {
		if (imageWriter == null)
			throw new MissingResourceException("The value of imageWriter is null", "Render", null);
		int Nx = imageWriter.getNx();// Nx= how many columns
		int Ny = imageWriter.getNy();// Ny= how many rows
		for (int i = 0; i < Ny; i++) {
			for (int j = 0; j < Nx; j++) {
				if (i % interval == 0 || j % interval == 0) {
					imageWriter.writePixel(j, i, color);// the grid
				}
			}
		}
	}

	/**
	 * calling the image building from the image writer
	 */
	public void writeToImage() {
		imageWriter.writeToImage();
	}

}
