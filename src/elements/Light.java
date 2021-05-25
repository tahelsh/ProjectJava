/**
 * 
 */
package elements;

import primitives.Color;

/**
 * class of light
 * 
 * @author Tahel Sharon and Ayala Israeli
 *
 */
abstract class Light {

	private Color intensity;
	private double radius=0;

	/**
	 * C-TOR that gets intensity color
	 * 
	 * @param intensity intensity color
	 */
	protected Light(Color intensity) {
		this.intensity = intensity;
	}

	/**
	 * C-TOR that gets intensity color and radius
	 * 
	 * @param intensity intensity color
	 * @param radius    radius value
	 */
	protected Light(Color intensity, double radius) {
		this.intensity = intensity;
		this.radius = radius;
	}

	/**
	 * get intensity
	 * 
	 * @return intensity color
	 */
	public Color getIntensity() {
		return intensity;
	}

	/**
	 * get radius
	 * 
	 * @return radius value
	 */
	public double getRadius() {
		return radius;
	}

}
