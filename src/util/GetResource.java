package util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GetResource {

	public static BufferedImage getBufferedImage(String name) {
		return (BufferedImage) getImage(name);
	}

	public static Image getImage(String path) {

		String currentPath = null;
		try {
			currentPath = new File(".").getCanonicalPath();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		File file = new File(currentPath + path);

		System.out.println("currentPath : " + currentPath);

		try {
			return ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.err.println("Could not get image file '" + currentPath + path + "'.");

		return null;
	}

	public static Image getDefaultUserImage() {
		return getImage("\\res\\images\\player\\defaultUserImage.jpg");
	}

}
