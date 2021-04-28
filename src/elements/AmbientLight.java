package elements;

import primitives.*;

/**
 * class of Ambient light
 * @author Tahel Sharon and Ayala Israeli
 *
 */
public class AmbientLight extends Light{
	
	/**
	 * CTOR that gets IA color parameter and scale KA- discount factor
	 * @param IA color
	 * @param KA a discount factor
	 */
	public AmbientLight(Color IA, double KA) {
		super(IA.scale(KA));
	}


}
