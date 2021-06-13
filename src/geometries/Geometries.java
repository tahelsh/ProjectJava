/**
 * 
 */
package geometries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import primitives.Point3D;
import primitives.Ray;


/**Geometries class of collection of intersectables
 * @author Tahel Sharon & Ayala Israeli
 *
 */
public class Geometries implements Intersectable {
	private List<Intersectable> geometries = new LinkedList<>();
	private Box box=null;
 ;

	   /* ********* Constructors **************/

 /**
  * A new Container
  * @param geometries the geometries
  * @see Intersectable
  */
 public Geometries(Intersectable ... geometries)
 {
     this.geometries.addAll(Arrays.asList(geometries));
     createBox();
 }

	/* ************* Operations ***************/

 /**
  * get geometries
  * @return geometries list
  */
 public List<Intersectable> getGeometries(){
     return geometries;
 }
 

 /**
  * add geometry
  * @param geometries the geometry
  */
 public void add(Intersectable ... geometries){
     this.geometries.addAll(Arrays.asList(geometries));
     createBox();
 }
	
//	  /**
//  * 
//  *
//  * @param ray the ray that intersect the geometries
//  * @return list of Point3D that intersect the collection
//  */
// @Override
// public List<Point3D> findIntersections(Ray ray) {
//     List<Point3D> intersections = null;
//
//     for (Intersectable geo : geometries) 
//     {
//         List<Point3D> tempIntersections = geo.findIntersections(ray);//list of single geometry
//         if (tempIntersections != null) 
//         {
//             if (intersections == null)//for the first time
//                 intersections = new ArrayList<>();
//             intersections.addAll(tempIntersections);
//         }
//     }
//     return intersections;
//
// }
 
 @Override
 public List<GeoPoint> findGeoIntersections (Ray ray) {
     List<GeoPoint> intersections = null;
     if (box!=null && box.IntersectionBox(ray)==false)
			return null;

     for (Intersectable geo : geometries) 
     {
         List<GeoPoint> tempIntersections = geo.findGeoIntersections(ray);//list of single geometry
         if (tempIntersections != null) 
         {
             if (intersections == null)//for the first time
                 intersections = new ArrayList<>();
             intersections.addAll(tempIntersections);
         }
     }
     return intersections;

 }
	/**
	 * Creating a box containing all the geometries little boxes
	 */
	private void createBox() {
		//Restart
     double x1=Double.NEGATIVE_INFINITY;
		double x0=Double.POSITIVE_INFINITY;
		double y1=Double.NEGATIVE_INFINITY;
		double y0=Double.POSITIVE_INFINITY;
		double z1=Double.NEGATIVE_INFINITY;
		double z0=Double.POSITIVE_INFINITY;
		//Adjust the size of the box to contain exactly all the small boxes
     for(Intersectable geo: geometries) {        	
     	if(geo.getBox().getX0()<x0) x0=geo.getBox().getX0();
     	if(geo.getBox().getX1()>x1) x1=geo.getBox().getX1();
     	if(geo.getBox().getY0()<y0) y0=geo.getBox().getY0();
     	if(geo.getBox().getY1()>y1) y1=geo.getBox().getY1();
     	if(geo.getBox().getZ0()<z0) z0=geo.getBox().getZ0();
     	if(geo.getBox().getZ1()>z1) z1=geo.getBox().getZ1();
     }
     this.box=new Box(x0,x1,y0,y1,z0,z1);
 }
	public Box getBox()
	{
		return box;
	}
}
