package unittests;

import static org.junit.Assert.*;

import org.junit.Test;
import geometries.Cylinder;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * Unit tests for geometries.Cylinder class
 * 
 * @author Tahel Sharon 323125153 and Ayala Israeli 324207232
 *
 */
public class CylinderTests {

	@Test
	/**
	 * checks getNormal function Test method for
	 * {@link geometries.Cylinder#getNormal(primitives.Point3D)}.
	 */
	public void testGetNormal() {
		Cylinder cyl = new Cylinder(new Ray(Point3D.ZERO, new Vector(0, 0, 1)), 5, 3);
		// ============ Equivalence Partitions Tests =============
		// TC01: test for points on the cylinder's side
		assertEquals("Bad normal to cylinder-for points to the cylinder's side", new Vector(1, 0, 0),
				cyl.getNormal(new Point3D(5, 0, 2.5)));
		// TC02: test for points on the FIRST base(where the point P0)
		assertEquals("Bad normal to cylinder-for points to the cylinder's first base", new Vector(0, 0, -1),
				cyl.getNormal(new Point3D(1, 2, 0)));
		assertEquals("Bad normal to cylinder-for points to the cylinder's first base", new Vector(0, 0, -1),
				cyl.getNormal(Point3D.ZERO));
		// TC03: test for points on the SECOND base
		assertEquals("Bad normal to cylinder-for points to the cylinder's second base", new Vector(0, 0, 1),
				cyl.getNormal(new Point3D(1, 2, 3)));
		assertEquals("Bad normal to cylinder-for points to the cylinder's second base", new Vector(0, 0, 1),
				cyl.getNormal(new Point3D(0, 0, 3)));
		// =============== Boundary Values Tests ==================
		// TC11: test for points on the border between the cylinde's side and the first
		// base
		assertEquals("Bad normal to cylinder-for points to the cylinder's first base", new Vector(0, 1, 0),
				cyl.getNormal(new Point3D(0, 5, 0)));
		// TC12: test for points on the border between the cylinde's side and the second
		// base
		assertEquals("Bad normal to cylinder-for points to the cylinder's first base", new Vector(0, 1, 0),
				cyl.getNormal(new Point3D(0, 5, 3)));
	}

}
