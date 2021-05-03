package primitives;

/**
 * class of Material-PDS
 * @author Tahel Sharon and Ayala Israeli
 *
 */
public class Material {
	public double kD=0;
	public double kS=0;
	public int nShininess=0;

	/**
	 * set kD value
	 * @param kD kD value
	 * @return the Material
	 */
	public Material setkD(double kD) {
		this.kD = kD;
		return this;
	}
	/**
	 * set kS value
	 * @param kS kS value
	 * @return the Material
	 */
	public Material setkS(double kS) {
		this.kS = kS;
		return this;
	}
	/**
	 * set nShininess
	 * @param nShininess nShininess value
	 * @return the Material
	 */
	public Material setnShininess(int nShininess) {
		this.nShininess = nShininess;
		return this;
	}
	

}
