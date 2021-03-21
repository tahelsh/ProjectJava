package geometries;
import java.util.List;
import  primitives.*;

/**
 * interface of intersectable 
 * @author  Tahel Sharon & Ayala Israeli
 *
 */
public interface Intersectable {

	/**
	 * function that finds intersections points
	 * @param r ray
	 * @return list of all intersections points
	 */
	public List<Point3D> findIntersections (Ray r);
		
	
}
