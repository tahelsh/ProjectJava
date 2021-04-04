/**
 * 
 */
package unittests;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.List;
import java.util.LinkedList;

import geometries.*;
import primitives.*;

/**
 * Testing Polygons
 * 
 * @author Dan
 *
 */
public class PolygonTests {

	/**
	 * Test method for Polygon
	 * {@link geometries.Polygon#Polygon(primitives.Point3D, primitives.Point3D, primitives.Point3D, primitives.Point3D)}.
	 */
	@Test
	public void testConstructor() {
		// ============ Equivalence Partitions Tests ==============

		// TC01: Correct concave quadrangular with vertices in correct order
		try {
			new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(-1, 1, 1));
		} catch (IllegalArgumentException e) {
			fail("Failed constructing a correct polygon");
		}

		// TC02: Wrong vertices order
		try {
			new Polygon(new Point3D(0, 0, 1), new Point3D(0, 1, 0), new Point3D(1, 0, 0), new Point3D(-1, 1, 1));
			fail("Constructed a polygon with wrong order of vertices");
		} catch (IllegalArgumentException e) {
		}

		// TC03: Not in the same plane
		try {
			new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(0, 2, 2));
			fail("Constructed a polygon with vertices that are not in the same plane");
		} catch (IllegalArgumentException e) {
		}

		// TC04: Concave quadrangular
		try {
			new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(0.5, 0.25, 0.5));
			fail("Constructed a concave polygon");
		} catch (IllegalArgumentException e) {
		}

		// =============== Boundary Values Tests ==================

		// TC10: Vertex on a side of a quadrangular
		try {
			new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(0, 0.5, 0.5));
			fail("Constructed a polygon with vertix on a side");
		} catch (IllegalArgumentException e) {
		}

		// TC11: Last point = first point
		try {
			new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(0, 0, 1));
			fail("Constructed a polygon with vertice on a side");
		} catch (IllegalArgumentException e) {
		}

		// TC12: Colocated points
		try {
			new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(0, 1, 0));
			fail("Constructed a polygon with vertice on a side");
		} catch (IllegalArgumentException e) {
		}

	}

	/**
	 * checks getNormal Test method for polygon
	 * Test method for {@link geometries.Polygon#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: There is a simple single test here
		Polygon pl = new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0),
				new Point3D(-1, 1, 1));
		double sqrt3 = Math.sqrt(1d / 3);
		assertEquals("Bad normal to trinagle", new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point3D(0, 0, 1)));
	}
    
	/**
	 * checks findIntersections Test method for polygon
	 * {@link geometries.Polygon#findIntersections(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersections() {
		Polygon polygon = new Polygon(new Point3D(0, 3, -3), new Point3D(3, 0, -3), new Point3D(-3, 0, -3));
		Ray r;
		// ============ Equivalence Partitions Tests ==============
		// TC01: the ray goes through the polygon
		r = new Ray(new Point3D(1, 1, -2), new Vector(-2, 0.5, -1));
		assertEquals("the ray goes through the triangle", List.of(new Point3D(-1, 1.5, -3)),
				polygon.findIntersections(r));
		// TC02: the ray is outside the polygon between 2 far sides
		r = new Ray(new Point3D(4, 4, -2), new Vector(1, 1, -4));
		assertEquals("the ray is outside the triangle between 2 far sides", null, polygon.findIntersections(r));
		// TC03: the ray is outside the polygon between 2 close sides
		r = new Ray(new Point3D(-4, -1, -2), new Vector(-1, -1, -1));
		assertEquals("the ray is outside the triangle between 2 close sides", null, polygon.findIntersections(r));

		// =============== Boundary Values Tests ==================
		// TC11: ray goes through the continuation of side 1
		r = new Ray(new Point3D(-1, 4, -2), new Vector(0, 0, -1));
		assertEquals("ray goes through the continuation of side 1", null, polygon.findIntersections(r));
		// TC12: ray through edge
		r = new Ray(new Point3D(-2, 1, -1), new Vector(0, 0, -1));
		assertEquals("ray through edge", null, polygon.findIntersections(r));
		// TC13: ray through vertex kodkod ]][[[
		r = new Ray(new Point3D(0, 3, -2), new Vector(0, 0, -1));
		assertEquals("ray through vertex", null, polygon.findIntersections(r));

	}

}
