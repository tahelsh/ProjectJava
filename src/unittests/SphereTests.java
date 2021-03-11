/**
 * 
 */
package unittests;

import static org.junit.Assert.*;
import primitives.*;
import org.junit.Test;
import geometries.*;

//import org.junit.Test;

/**
 * Unit tests for geometries.Sphere class
 * @author Tahel Sharon 323125153 and Ayala Israeli 324207232
 *
 */
public class SphereTests {

	/**
	 * Test method for {@link geometries.Sphere#get_Normal(primitives.Point3D)}.
	 */
	@Test
	public void testGet_Normal() {
		// ============ Equivalence Partitions Tests ==============
		Point3D p= new Point3D(1, 1, 8);
		Point3D o=new Point3D(1,1,2);
		Sphere s=new Sphere(o,6);
		Vector v= p.subtract(o).normalize();
		assertEquals("Bad normal to sphere",v,s.get_Normal(p));
	}

}
