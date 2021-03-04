package geometries;
import primitives.Point3D;

/**
 * class of Triangle, exstends from Polygon
 * @author Tahel Sharon & Ayala Israeli
 *
 */
public class Triangle extends Polygon {

	/**
	 * constructor that gets 3 point3D
	 * @param p0
	 * @param p1
	 * @param p2
	 * @throws IllegalArgumentException
	 */
	public Triangle (Point3D p0,Point3D p1,Point3D p2) throws IllegalArgumentException {
		super(p0,p1,p2);
	}

	@Override
	public String toString() {
		return "Triangle [vertices=" + vertices + ", plane=" + plane + "]";
	}	
}
