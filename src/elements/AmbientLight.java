package elements;

import primitives.*;

public class AmbientLight {
	private Color intensity;

	/**
	 * CTOR that gets IA color parameter and scale KA- discount factor
	 * 
	 * @param IA
	 * @param KA
	 */
	public AmbientLight(Color IA, double KA) {
		intensity = IA.scale(KA);

	}

	/**
	 * get intensity
	 * 
	 * @return
	 */
	public Color getIntensity() {
		return intensity;
	}

}
