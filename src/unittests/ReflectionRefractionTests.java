/**
 * 
 */
package unittests;

import org.junit.Test;

import elements.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 * 
 * @author dzilb
 */
public class ReflectionRefractionTests {
	private Scene scene = new Scene("Test scene");

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void twoSpheres() {
		Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setViewPlaneSize(150, 150).setDistance(1000);

		scene.geometries.add( //
				new Sphere(new Point3D(0, 0, -50), 50) //
						.setEmmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
				new Sphere(new Point3D(0, 0, -50), 25) //
						.setEmmission(new Color(java.awt.Color.RED)) //
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)));
		scene.lights.add( //
				new SpotLight(new Color(1000, 600, 0), new Point3D(-100, -100, 500), new Vector(-1, -1, -2)) //
						.setkL(0.0004).setkQ(0.0000006));

		Render render = new Render() //
				.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void twoSpheresOnMirrors() {
		Camera camera = new Camera(new Point3D(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setViewPlaneSize(2500, 2500).setDistance(10000); //

		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

		scene.geometries.add( //
				new Sphere(new Point3D(-950, -900, -1000), 400) //
						.setEmmission(new Color(0, 0, 100)) //
						.setMaterial(new Material().setkD(0.25).setkS(0.25).setnShininess(20).setkT(0.5)),
				new Sphere(new Point3D(-950, -900, -1000), 200) //
						.setEmmission(new Color(100, 20, 20)) //
						.setMaterial(new Material().setkD(0.25).setkS(0.25).setnShininess(20)),
				new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500),
						new Point3D(670, 670, 3000)) //
								.setEmmission(new Color(20, 20, 20)) //
								.setMaterial(new Material().setkR(1)),
				new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500),
						new Point3D(-1500, -1500, -2000)) //
								.setEmmission(new Color(20, 20, 20)) ///
								.setMaterial(new Material().setkR(0.5)));

		scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point3D(-750, -750, -150), new Vector(-1, -1, -4)) //
				.setkL(0.00001).setkQ(0.000005));

		ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));

		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a spot light with a partially
	 * transparent Sphere producing partial shadow
	 */
	@Test
	public void trianglesTransparentSphere() {
		Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setViewPlaneSize(200, 200).setDistance(1000);

		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.geometries.add( //
				new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -135), new Point3D(75, 75, -150)) //
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)), //
				new Triangle(new Point3D(-150, -150, -115), new Point3D(-70, 70, -140), new Point3D(75, 75, -150)) //
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)), //
				new Sphere(new Point3D(60, 50, -50), 30) //
						.setEmmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setkT(0.6)));

		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point3D(60, 50, 0), new Vector(0, 0, -1)) //
				.setkL(4E-5).setkQ(2E-7));

		ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));

		render.renderImage();
		render.writeToImage();
	}

//	@Test
//	public void ourPicture() {
//		Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
//				.setViewPlaneSize(200, 200).setDistance(1000);
//		Geometry triangle1 = new Triangle( //
//				new Point3D(-150, -150, -150), new Point3D(150, -150, -150), new Point3D(75, 75, -150));
////						.setEmmission(new Color(java.awt.Color.BLUE));
//		Geometry triangle2 = new Triangle( //
//				new Point3D(-150, -150, -150), new Point3D(-70, 70, -50), new Point3D(75, 75, -150));
////						.setEmmission(new Color(java.awt.Color.BLUE));
//
//		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.3));
//		scene.geometries.add(
////				new Polygon(new Point3D(-150, -150, -150), new Point3D(75, 75, -150),
////						new Point3D(75, 75, 50), new Point3D(-150, -150, 50)) //
////								.setEmmission(new Color(20, 20, 20)) //
////								.setMaterial(new Material().setkR(1)).setEmmission(new Color(java.awt.Color.RED)),
//				// triangle1.setMaterial(new
//				// Material().setkD(0.5).setkS(0.5).setnShininess(300).setkR(0.5)),
//				// triangle2.setMaterial(new
//				// Material().setkD(0.5).setkS(0.5).setnShininess(300).setkR(0.5)),
//				new Sphere(new Point3D(-33, -53, -150), 30).setEmmission(new Color(java.awt.Color.CYAN))
//						.setMaterial(new Material().setkR(0.5).setkD(0.5).setnShininess(30).setkS(0.5)),
//				new Sphere(new Point3D(50, 33, -150), 30).setEmmission(new Color(java.awt.Color.RED))
//						.setMaterial(new Material().setkR(0.5).setkD(0.5).setnShininess(30).setkS(0.5)),
//				new Cylinder(new Ray(new Point3D(50, 33, -150), new Vector(-83, -86, 0)), 100, 200)
//						.setEmmission(new Color(java.awt.Color.PINK)),
//				new Polygon(new Point3D(18, 1, 200), new Point3D(18, 65, -500), new Point3D(95, 65, -500),
//						new Point3D(75, 1, 200)).setEmmission(new Color(java.awt.Color.GREEN))
//								.setMaterial(new Material().setkT(0.75)),
//				new Polygon(new Point3D(-62, -102, -500), new Point3D(-62, -20, 100), new Point3D(-2, -20, 100),
//						new Point3D(-2, -102, -500)).setEmmission(new Color(java.awt.Color.BLUE))
//								.setMaterial(new Material().setkT(0.75)));
//		scene.lights.add(new PointLight(new Color(500, 250, 250), new Point3D(10, -10, -130)) //
//				.setkL(0.0001).setkQ(0.000005));
//		ImageWriter imageWriter = new ImageWriter("trilili", 600, 600);
//		Render render = new Render() //
//				.setImageWriter(imageWriter) //
//				.setCamera(camera) //
//				.setRayTracer(new RayTracerBasic(scene));
//
//		render.renderImage();
//		render.writeToImage();
//	}

	@Test
	public void ourPicture2() {
		Camera camera = new Camera(new Point3D(0, 0, 1500), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setViewPlaneSize(200, 200).setDistance(1000);
		Geometry triangle1 = new Triangle( //
				new Point3D(-150, -150, -300), new Point3D(150, -150, -300), new Point3D(75, 75, -300));
//						.setEmmission(new Color(java.awt.Color.BLUE));
		Geometry triangle2 = new Triangle( //
				new Point3D(-150, -150, -500), new Point3D(-70, 70, -500), new Point3D(75, 75, -500));
//						.setEmmission(new Color(java.awt.Color.BLUE));

		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.3));
		scene.geometries.add(
				new Polygon(new Point3D(-150, -160, -10), new Point3D(-150, 90, -400), new Point3D(165, 90, -400),
						new Point3D(165, -160, -10)).setMaterial(new Material().setkR(1).setkD(0.5))
								.setEmmission(new Color(0, 0, 0)),
				// triangle1.setMaterial(new
				// Material().setkD(0.5).setkS(0.5).setnShininess(300).setkR(0.5)),
				//triangle2.setMaterial(new Material().setkR(1)),
				new Triangle(new Point3D(50, -10, -150), new Point3D(-30, -10, -150), new Point3D(10, -70, -150))
						.setEmmission(new Color(200, 70, 50))// orange
						.setMaterial(new Material().setkR(1).setkD(1).setnShininess(300).setkS(0.5)),
				new Sphere(new Point3D(-8, 10, -150), 25).setEmmission(new Color(0, 30, 60))// blue icecream
						.setMaterial(new Material().setkT(1).setkD(0.5).setnShininess(30).setkS(0.5)),
				new Sphere(new Point3D(35, 10, -150), 25).setEmmission(new Color(50, 0, 0))// red icecream
						.setMaterial(new Material().setkR(1).setkD(0.5).setnShininess(30).setkS(0.5)),
				new Sphere(new Point3D(10, 40, -150), 25).setEmmission(new Color(0, 50, 0))// green icecream
						.setMaterial(new Material().setkT(1).setkD(0.5).setnShininess(30).setkS(0.5)),
				new Sphere(new Point3D(-140, 0, -150), 10).setEmmission(new Color(150, 0, 150))// blue
						.setMaterial(new Material().setkT(0.5).setkD(0.75).setnShininess(30).setkS(1)),
				new Sphere(new Point3D(150, 0, -150), 10).setEmmission(new Color(80, 80, 18))// blue
						.setMaterial(new Material().setkR(1).setkD(0.75).setnShininess(30).setkS(1)),
				new Sphere(new Point3D(-60, 80, -160), 10).setEmmission(new Color(80, 80, 18))// blue
						.setMaterial(new Material().setkR(1).setkD(0.75).setnShininess(30).setkS(1)),
				new Sphere(new Point3D(80, 80, -160), 10).setEmmission(new Color(150, 0, 150))// blue
						.setMaterial(new Material().setkT(0.5).setkD(0.75).setnShininess(30).setkS(1)));
		scene.lights.add(new PointLight(new Color(100, 100, 500), new Point3D(-50, -50, 50))//
				.setkL(0.00001).setkQ(0.00001));

		ImageWriter imageWriter = new ImageWriter("trilili2", 600, 600);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));

		render.renderImage();
		render.writeToImage();
	}
}
