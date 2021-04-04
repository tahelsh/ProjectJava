/**
 * 
 */
package unittests;

import primitives.*;

import static org.junit.Assert.*;
import static primitives.Util.isZero;
import java.util.List;

import org.junit.Test;

import geometries.Plane;

/**
 * Unit tests for geometries.Plane class
 * 
 * @author Tahel Sharon 323125153 and Ayala Israeli 324207232
 *
 */
public class PlaneTests {

	/**
	 * checks getNormal function Test method for plane
	 * {@link geometries.Plane#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		Point3D p1 = new Point3D(1, 2, 0);
		Point3D p2 = new Point3D(-4, 7, 0);
		Point3D p3 = new Point3D(1, 0, 5);
		Plane p = new Plane(p1, p2, p3);
		Vector v1 = new Vector((p1.subtract(p2)).getHead());
		Vector v2 = new Vector((p2.subtract(p3)).getHead());
		Vector v3 = new Vector((p3.subtract(p1)).getHead());
		Vector n = p.getNormal(p1);
		assertTrue("ERROR: Bad normal to plane", isZero(v1.dotProduct(n)));
		assertTrue("ERROR: Bad normal to plane", isZero(v2.dotProduct(n)));
		assertTrue("ERROR: Bad normal to plane", isZero(v3.dotProduct(n)));
		// =============== Boundary Values Tests ==================
		// if there are 2 vectors that are parallel
		try {
			new Plane(new Point3D(1, 2, 3), new Point3D(2, 4, 6), new Point3D(4, 8, 12)).getNormal(p1);
			fail("GetNormal() should throw an exception, but it failed");
		} catch (Exception e) {
		}
	}

	/**
	 * checks findIntersections Test method for plane
	 * {@link geometries.Plane#findIntersections(primitives.Ray)}.
	 */
	public void testFindIntersections() {
		Plane pl = new Plane(new Point3D(0, 0, -3), new Vector(0, 0, -1));
		Ray r;

		// ============ Equivalence Partitions Tests ==============
		// EP: The Ray must be neither orthogonal nor parallel to the plane
		// TC01: Ray intersects the plane
		r = new Ray(new Point3D(1, 1, 0), new Vector(2, 1, -1));
		List<Point3D> result = pl.findIntersections(r);
		assertEquals("the ray intersects the plane", List.of(new Point3D(7, 4, -3)), result);

		// TC02:Ray does not intersect the plane
		r = new Ray(new Point3D(1, 1, 0), new Vector(2, 1, 1));
		result = pl.findIntersections(r);
		assertEquals("the ray does not intersect the plane", null, result);

		// =============== Boundary Values Tests ==================

		// Group:Ray is parallel to the plane
		// TC11:Ray is parallel to the plane,the ray included in the plan
		r = new Ray(new Point3D(1, 2, -3), new Vector(2, 1, 0));
		assertEquals("the ray is parallel and included in the plane", null, pl.findIntersections(r));
		// TC12: Ray is parallel to the plane and ray is not included in the plane
		r = new Ray(new Point3D(1, 2, -2), new Vector(2, 1, 0));
		assertEquals("the ray is parallel and not included in the plane", null, pl.findIntersections(r));

		// Group: Ray is orthogonal to the plane
		// TC13:Ray starts before the plane
		r = new Ray(new Point3D(1, 1, 0), new Vector(0, 0, -1));
		assertEquals("the ray is orthogonal and starts before the plane", List.of(new Point3D(1, 1, -3)),
				pl.findIntersections(r));
		// TC14:Ray starts in the plane
		r = new Ray(new Point3D(1, 1, -3), new Vector(0, 0, -1));
		assertEquals("the ray is orthogonal and starts in the plane", null, pl.findIntersections(r));
		// TC15:Ray starts after the plane
		r = new Ray(new Point3D(1, 1, -4), new Vector(0, 0, -1));
		assertEquals("the ray is orthogonal and starts after the plane", null, pl.findIntersections(r));

		// TC16: Starting point on the plane, but the rest of the ray is not
		r = new Ray(new Point3D(1, 1, -3), new Vector(2, 1, -1));
		assertEquals("Starting point on the plane, but the rest of the ray is not", null, pl.findIntersections(r));

		// TC17: Starting point is equal to the plane point
		r = new Ray(new Point3D(0, 0, -3), new Vector(2, 1, -1));
		assertEquals("Starting point is equal to the plane point", null, pl.findIntersections(r));
	}

}
