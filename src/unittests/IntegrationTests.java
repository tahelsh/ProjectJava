package unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import elements.*;
import primitives.*;
import geometries.*;

import java.util.List;

/**
 * Integration Tests
 * @author Tahel Sharon & Ayala Israeli
 *
 */
public class IntegrationTests {

	Camera cam1 = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)).setDistance(1).setViewPlaneSize(3, 3);
	Camera cam2 = new Camera(new Point3D(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0)).setDistance(1).setViewPlaneSize(3, 3);
	
/**
 * Calculates how many intersections a view plane has with an intersectable
 * @param Nx  How many pixels in row
 * @param Ny How many pixels in column
 * @param inter intersectable
 * @param cam camera
 * @return
 */
	private int countIntersection(int Nx, int Ny, Intersectable inter, Camera cam) {
		List<Point3D> results;//list of intersections points of one intersectable
		int count = 0;//counter of intersections points
		for (int i = 0; i < Nx; ++i) {
			for (int j = 0; j < Ny; ++j) {
				Ray ray = cam.constructRayThroughPixel(3, 3, j, i);
				results = inter.findIntersections(ray);
				if (results != null)
					count += results.size();
			}
		}
		return count;
	}

	/**
	 * Test for Sphere
	 */
	@Test
	public void constructRayThroughPixelWithSphere() {

		// ***** 2 intersection points*****
		Sphere sph = new Sphere(new Point3D(0, 0, -3), 1);
		int Nx = 3;// How many pixels in row
		int Ny = 3;// How many pixels in column
		assertEquals("too bad", 2, countIntersection(Nx,Ny,sph,cam1));

		// ***** 18 intersection points*****
		sph = new Sphere(new Point3D(0, 0, -2.5), 2.5);
		assertEquals("too bad", 18, countIntersection(Nx,Ny,sph,cam2));

		// ***** 10 intersection points*****
		sph = new Sphere(new Point3D(0, 0, -2), 2);
		assertEquals("too bad", 10, countIntersection(Nx,Ny,sph,cam2));

		// ***** 9 intersection points*****
		sph = new Sphere(new Point3D(0, 0, 1), 4);
		assertEquals("too bad", 9, countIntersection(Nx,Ny,sph,cam2));

		// ***** 0 intersection points*****
		sph = new Sphere(new Point3D(0, 0, 1), 0.5);
		assertEquals("too bad", 0, countIntersection(Nx,Ny,sph,cam2));
	}

	/**
	 * Test for Plane
	 */
	@Test
	public void constructRayThroughPixelWithPlane() {
		// ***** 9 intersection points*****
		Plane pl = new Plane(new Point3D(0, 0, -7), new Vector(0, 0, 1));
		int Nx = 3;// How many pixels in row
		int Ny = 3;// How many pixels in column
		assertEquals("too bad", 9, countIntersection(Nx,Ny,pl,cam2));

		// ***** 9 intersection points*****
		pl = new Plane(new Point3D(0, 0, -2), new Vector(0, -1, 3));
		assertEquals("too bad", 9, countIntersection(Nx,Ny,pl,cam2));

		// ***** 6 intersection points*****
		pl = new Plane(new Point3D(0, 0, -2), new Vector(0, -3, 1));
		assertEquals("too bad", 6, countIntersection(Nx,Ny,pl,cam2));
	}

	/**
	 * Test for triangle
	 */
	@Test
	public void constructRayThroughPixelWithTriangle() {
		// ***** One intersection point*****
		Triangle tr = new Triangle(new Point3D(1, -1, -2), new Point3D(-1, -1, -2), new Point3D(0, 1, -2));
		int Nx = 3;// How many pixels in row
		int Ny = 3;// How many pixels in column
		assertEquals("too bad", 1, countIntersection(Nx,Ny,tr,cam2));

		// ***** Two intersection point*****
		tr = new Triangle(new Point3D(1, -1, -2), new Point3D(-1, -1, -2), new Point3D(0, 20, -2));
		assertEquals("too bad", 2, countIntersection(Nx,Ny,tr,cam2));
	}

}
