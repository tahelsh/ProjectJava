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

   /**
    * test of create a picture with all the effects
    */
	@Test
	public void allEffectsPicture() {
		Camera camera = new Camera(new Point3D(5, -10, 1000), new Vector(-0.035, 0, -1).normalized(),
				new Vector(0, 1, 0)) //
						.setViewPlaneSize(200, 200).setDistance(1000);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.3));
		scene.geometries.add(
				new Polygon(new Point3D(-150, -160, -10), new Point3D(-150, 90, -400), new Point3D(165, 90, -400),
						new Point3D(165, -160, -10)).setMaterial(new Material().setkR(0.3).setkT(0.25).setkD(0.5))
								.setEmmission(new Color(0, 0, 0)),
				new Triangle(new Point3D(49, 0, -150), new Point3D(-33, 0, -150), new Point3D(10, -70, -150))
						.setEmmission(new Color(255, 140, 190)), // orange

				new Triangle(new Point3D(49, 0, -150), new Point3D(10, -70, -150), new Point3D(59, 3, -150))
						.setEmmission(new Color(255, 140, 190)), // orange
				new Sphere(new Point3D(-8, 20, -150), 25).setEmmission(new Color(0, 30, 60))// blue icecream
						.setMaterial(new Material().setkT(1).setkD(0.5).setnShininess(30).setkS(0.5)),
				new Sphere(new Point3D(35, 20, -150), 25).setEmmission(new Color(50, 0, 0))// red icecream
						.setMaterial(new Material().setkR(1).setkD(0.5).setnShininess(30).setkS(0.5)),
				new Sphere(new Point3D(10, 50, -150), 25).setEmmission(new Color(0, 50, 0))// green icecream
						.setMaterial(new Material().setkT(1).setkD(0.5).setnShininess(30).setkS(0.5)),
				new Sphere(new Point3D(10, 75, -150), 7).setEmmission(new Color(70, 0, 0))// cherry
						.setMaterial(new Material().setkT(1).setkD(0.5).setnShininess(30).setkS(0.5)),
				// face
				new Sphere(new Point3D(35, 20, -100), 12).setEmmission(new Color(0, 0, 0)), // red icecream
				new Sphere(new Point3D(-8, 20, -100), 12).setEmmission(new Color(0, 0, 0)), // blue icecream
				new Sphere(new Point3D(40, 25, -90), 5).setEmmission(new Color(255, 255, 255)), // red icecream
				new Sphere(new Point3D(-3, 25, -90), 5).setEmmission(new Color(255, 255, 255)), // blue icecream
				// mouse
				new Sphere(new Point3D(10, -27, -100), 10).setEmmission(new Color(50, 0, 0)),
				new Sphere(new Point3D(10, -20, -100), 10).setEmmission(new Color(255, 140, 190)),
				// ice cream bar
				new Polygon(new Point3D(-80, -15, -150), new Point3D(-110, -25, -150), new Point3D(-110, 65, -150),
						new Point3D(-80, 75, -150)).setEmmission(new Color(227, 28, 36))
								.setMaterial(new Material().setkT(0.5).setkR(1).setkR(0.5)), // red
				new Polygon(new Point3D(-80, -15, -150), new Point3D(-110, -25, -150), new Point3D(-110, -35, -150),
						new Point3D(-80, -25, -150)).setEmmission(new Color(26, 240, 52))
								.setMaterial(new Material().setkT(0.5).setkR(1).setkR(0.5)), // green
				new Polygon(new Point3D(-101, -32, -150), new Point3D(-89, -28, -150), new Point3D(-89, -48, -150),
						new Point3D(-101, -52, -150)).setEmmission(new Color(202, 165, 38))
								.setMaterial(new Material().setkT(0.5).setkR(1).setkR(0.5)), // yellow

				// nucleuses
				new Sphere(new Point3D(-100, -10, -95), 2).setEmmission(new Color(0, 0, 0))
						.setMaterial(new Material().setkT(0.25).setkR(0.5)),
				new Sphere(new Point3D(-80, -12, -95), 2).setEmmission(new Color(0, 0, 0))
						.setMaterial(new Material().setkT(0.25).setkR(0.5)),
				new Sphere(new Point3D(-87, -3, -95), 2).setEmmission(new Color(0, 0, 0))
						.setMaterial(new Material().setkT(0.25).setkR(0.5)),
				new Sphere(new Point3D(-95, 7, -110), 2).setEmmission(new Color(0, 0, 0))
						.setMaterial(new Material().setkT(0.25).setkR(0.5)),
				new Sphere(new Point3D(-80, 15, -95), 2).setEmmission(new Color(0, 0, 0))
						.setMaterial(new Material().setkT(0.25).setkR(0.5)),
				new Sphere(new Point3D(-90, 20, -95), 2).setEmmission(new Color(0, 0, 0))
						.setMaterial(new Material().setkT(0.25).setkR(0.5)),
				new Sphere(new Point3D(-100, 27, -100), 2).setEmmission(new Color(0, 0, 0))
						.setMaterial(new Material().setkT(0.25).setkR(0.5)),
				new Sphere(new Point3D(-87, 33, -95), 2).setEmmission(new Color(0, 0, 0))
						.setMaterial(new Material().setkT(0.25).setkR(0.5)),
				new Sphere(new Point3D(-95, 42, -95), 2).setEmmission(new Color(0, 0, 0))
						.setMaterial(new Material().setkT(0.25).setkR(0.5)),
				new Sphere(new Point3D(-80, 47, -95), 2).setEmmission(new Color(0, 0, 0))
						.setMaterial(new Material().setkT(0.25).setkR(0.5)),
				new Sphere(new Point3D(-87, 55, -95), 2).setEmmission(new Color(0, 0, 0))
						.setMaterial(new Material().setkT(0.25).setkR(0.5)),
				new Sphere(new Point3D(-98, 56, -95), 2).setEmmission(new Color(0, 0, 0))
						.setMaterial(new Material().setkT(0.25).setkR(0.5))
		);
		// scene.background=new Color(0,0,255);
		scene.lights.add(new PointLight(new Color(100, 100, 500), new Point3D(-50, -50, 50))//
				.setkL(0.00001).setkQ(0.00001));

		ImageWriter imageWriter = new ImageWriter("allEffectsPicture", 600, 600);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));

		render.renderImage();
		render.writeToImage();
	}
}
