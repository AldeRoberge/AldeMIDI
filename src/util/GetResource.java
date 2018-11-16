package util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GetResource {

	public static BufferedImage getBufferedImage(String name) {
		return (BufferedImage) getImage(name);
	}

	public static Image getImage(String name) {

		try {
			return ImageIO.read(GetResource.class.getResourceAsStream(name));
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.err.println("Could not get image file '" + name + "'.");

		return null;
	}

	public static Image getDefaultUserImage() {
		return getImage("images/player/defaultUserImage.jpg");
	}

}
