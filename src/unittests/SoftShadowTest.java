package unittests;

//import static org.junit.Assert..*;

import org.junit.Test;

import elements.AmbientLight;
import elements.Camera;
import elements.PointLight;
//import elements.SpotLight;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import renderer.Render;
import scene.Scene;
/**
 * tests for soft shadow- mini project 1
 * @author Tahel Sharon & Ayala Israeli
 *
 */
public class SoftShadowTest {
	private Scene scene = new Scene("Test scene");
	
	/**
	 * test with soft shadow
	 */
	@Test
	public void PictureWithSoftShadows() {
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
		scene.lights.add(new PointLight(new Color(100, 100, 500), new Point3D(-50, -50, 50),5)//
				.setkL(0.00001).setkQ(0.00001));

		ImageWriter imageWriter = new ImageWriter("softShadowPicture", 600, 600);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene).setNumberOfRays(100));

		render.renderImage(/*100*/);
		render.writeToImage();
	}
	
	/**
	 * test without soft shadow
	 */
	@Test
	public void PictureWithoutSoftShadows() {
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
		scene.lights.add(new PointLight(new Color(100, 100, 500), new Point3D(-50, -50, 50),5)//
				.setkL(0.00001).setkQ(0.00001));

		ImageWriter imageWriter = new ImageWriter("WithoutsoftShadowPicture", 600, 600);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));

		render.renderImage();
		render.writeToImage();
	}

}
