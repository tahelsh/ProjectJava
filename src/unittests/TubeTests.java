/**
 * 
 */
package unittests;

import static org.junit.Assert.*;
import static primitives.Util.isZero;

import org.junit.Test;

import geometries.Tube;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

//import org.junit.Test;

/**
 * Unit tests for geometries.Tube class
 * @author Tahel Sharon 323125153 and Ayala Israeli 324207232
 *
 */
public class TubeTests {

	/**
	 * Test method for {@link geometries.Tube#get_Normal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormal() {
		Ray r= new Ray( new Point3D(0,0,0),new Vector(0,1,0));
		Tube t= new Tube(r,1);
		Point3D p = new Point3D(1,0,1);
		Vector n= t.getNormal(p);
		assertTrue("bad normal to tube",isZero(r.getDir().dotProduct(n)));
	}

}

