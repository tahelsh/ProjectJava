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


/**
 * Unit tests for geometries.Tube class
 * @author Tahel Sharon 323125153 and Ayala Israeli 324207232
 *
 */
public class TubeTests {

	/**
	 * checks getNormal function
	 * Test method for {@link geometries.Tube#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		Tube tube=new Tube(new Ray (Point3D.ZERO, new Vector(1,0,0)), 5);
		assertEquals("Bad normal to tube", new Vector(0,0,1), tube.getNormal(new Point3D(1,0,5)));
		// =============== Boundary Values Tests ==================
		//if the point is parallel to the head of the ray
		assertEquals("bad normal to tube",new Vector(0,0,1),tube.getNormal(new Point3D(0,0,5)));
		
	}

}

