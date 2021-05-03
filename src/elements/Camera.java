package elements;

import primitives.Vector;
import static primitives.Util.*;
import primitives.Point3D;
import primitives.Ray;

/**
 * class of Camera
 * @author Tahel Sharon & Ayala Israeli
 *
 */

public class Camera {

	private Point3D p0;
	private Vector vUp;
	private Vector vTo;
	private Vector vRight;
	private double width;
	private double height;
	private double distance; 

	/* ********* C-TOR ********/
	/**
	 * C_TOR
	 * 
	 * @param p0
	 * @param vUp
	 * @param vTo
	 * @param width
	 * @param height
	 * @param distance
	 */
	public Camera(Point3D p0, Vector vTo , Vector vUp ) throws IllegalArgumentException {
		super();
		if (!isZero(vUp.dotProduct(vTo))) {
			throw new IllegalArgumentException("Error, cannot create Camera, vUp and vTo are not vertical");
		}
		this.p0 = p0;
		this.vUp = vUp.normalized();
		this.vTo = vTo.normalized();
		this.vRight = vTo.crossProduct(vUp).normalize();

	}

	/* ********* Getters ********/
	/**
	 * get P0
	 * @return
	 */
	public Point3D getP0() {
		return p0;
	}

	/**
	 * get vUp
	 * @return
	 */
	public Vector getvUp() {
		return vUp;
	}

	/**
	 * get vTo
	 * @return
	 */
	public Vector getvTo() {
		return vTo;
	}

	/**
	 * get vRight
	 * @return
	 */
	public Vector getvRight() {
		return vRight;
	}

	/**
	 * get Width
	 * @return
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * get Height 
	 * @return
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * get Distance
	 * @return
	 */
	public double getDistance() {
		return distance;
	}

	/* ********* Setters ********/
	/**
	 * set View Plane Size
	 * 
	 * @param width
	 * @param height
	 * @return Camera
	 */
	public Camera setViewPlaneSize(double width, double height) {
		this.width = width;
		this.height = height;
		return this;
	}

	/**
	 * set Distance
	 * 
	 * @param distance
	 * @return Camera
	 */
	public Camera setDistance(double distance) {
		this.distance = distance;
		return this;
	}

	/**
	 * construct ray through Pixel
	 * @param nX How many pixels in row
	 * @param nY How many pixels in column
	 * @param j  column
	 * @param i  row
	 * @return
	 */
	public Ray constructRayThroughPixel(int nX, int nY, int j, int i) throws IllegalArgumentException {
		if (isZero(distance)) {
			throw new IllegalArgumentException("distance cannot be 0");
		}

		Point3D Pc = p0.add(vTo.scale(distance));

		double Ry = height / nY;// height of each pixel
		double Rx = width / nX;// width of each pixel
		// the middle of the pixel (xj,yi)
		double yi = (i - (nY - 1) / 2d) * Ry;
		double xj = (j - (nX - 1) / 2d) * Rx;

		Point3D Pij = Pc;

		if (!isZero(xj)) {
			Pij = Pij.add(vRight.scale(xj));
		}
		if (!isZero(yi)) {
			Pij = Pij.add(vUp.scale(-yi));
		}

		Vector Vij = Pij.subtract(p0);

		return new Ray(p0, Vij);

	}
}
