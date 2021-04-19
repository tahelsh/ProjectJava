package elements;

import primitives.*;

/**
 * class of Ambient light
 * @author Tahel Sharon and Ayala Israeli
 *
 */
public class AmbientLight {
	private Color intensity;

	/**
	 * CTOR that gets IA color parameter and scale KA- discount factor
	 * 
	 * @param IA color
	 * @param KA a discount factor
	 */
	public AmbientLight(Color IA, double KA) {
		intensity = IA.scale(KA);

	}

	/**
	 * get intensity
	 * @return intensity value
	 */
	public Color getIntensity() {
		return intensity;
	}

}
