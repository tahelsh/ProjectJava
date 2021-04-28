/**
 * 
 */
package renderer;

import java.util.List;

import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

/**
 * class of RayTracerBasic
 * @author Tahel Sharon and Ayala Israeli
 *
 */
public class RayTracerBasic extends RayTracerBase {
	
	/**
	 * constructor that gets a scene
	 * @param scene
	 */
	public RayTracerBasic(Scene scene)
	{
		super(scene);
	}

	@Override
	public Color traceRay(Ray ray) {
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
		if (intersections == null) 
			return scene.background;
		GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
		return calcColor(closestPoint);
	}
	/**
	 * calculate the color of a point
	 * @param point a point
	 * @return the color of the point
	 */
	private Color calcColor(GeoPoint point) {
		return scene.ambientLight.getIntensity().add(point.geometry.getEmmission());
	}
	
	//@Override
	//public Color traceRay(Ray ray) {
	//	List<Point3D> intersections = scene.geometries.findIntersections(ray);
	//	if (intersections == null) 
	//		return scene.background;
	//	Point3D closestPoint = ray.findClosestPoint(intersections);
	//	return calcColor(closestPoint);
	//}
	
	///**
	// * calculate the color of a point
	// * @param point a point
	// * @return the color of the point
	 //*/
	//private Color calcColor(Point3D point) {
	//	return scene.ambientLight.getIntensity();
	//}


}
