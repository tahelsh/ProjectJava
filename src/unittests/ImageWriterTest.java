package unittests;

import renderer.ImageWriter;
import primitives.*;
import org.junit.Test;

public class ImageWriterTest {

	@Test
	public void WriteToImageTest() {
		ImageWriter imageWriter = new ImageWriter("image_1", 800, 500);
        int Nx = imageWriter.getNx();
        int Ny = imageWriter.getNy();
        Color b=new Color(0, 0, 255);
        Color r=new Color(255, 0, 0);
        for (int i = 0; i < Ny; i++) {
            for (int j = 0; j < Nx; j++) {
                if (i % 50 == 0 || j % 50 == 0)
                {
                    imageWriter.writePixel(j, i, r);
                }
                else
                {
                    imageWriter.writePixel(j, i,b);
                }
            }
        }
        imageWriter.writeToImage();	}

}
