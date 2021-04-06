/**
 * 
 */
package geometries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import primitives.Point3D;
import primitives.Ray;


/**Geometries class of collection of intersectables
 * @author Tahel Sharon & Ayala Israeli
 *
 */
public class Geometries implements Intersectable {
	private List<Intersectable> geometries = new ArrayList<>();
 ;

	   /* ********* Constructors ***********/

 /**
  * A new Container
  * @param geometries the geometries
  * @see Intersectable
  */
 public Geometries(Intersectable ... geometries)
 {
     this.geometries.addAll(Arrays.asList(geometries));
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
 }
	
	  /**
  * 
  *
  * @param ray the ray that intersect the geometries
  * @return list of Point3D that intersect the collection
  */
 @Override
 public List<Point3D> findIntersections(Ray ray) {
     List<Point3D> intersections = null;

     for (Intersectable geo : geometries) 
     {
         List<Point3D> tempIntersections = geo.findIntersections(ray);//list of single geometry
         if (tempIntersections != null) 
         {
             if (intersections == null)//for the first time
                 intersections = new ArrayList<>();
             intersections.addAll(tempIntersections);
         }
     }
     return intersections;

 }

}
