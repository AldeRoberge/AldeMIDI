package perfectpitch.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.LoggerFactory;

public class GetResource {

	private static org.slf4j.Logger log = LoggerFactory.getLogger(GetResource.class);

	public static BufferedImage getBufferedImage(String name) {
		return (BufferedImage) getImage(name);
	}

	private static Image getImage(String path) {

		String currentPath = null;
		try {
			currentPath = new File(".").getCanonicalPath();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		File file = new File(currentPath + path);

		log.info("currentPath : " + currentPath);

		try {
			return ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		log.error("Could not get image file '" + currentPath + path + "'.");

		return null;
	}

	public static Image getDefaultUserImage() {
		return getImage("\\res\\images\\player\\defaultUserImage.jpg");
	}

	public static Image getSoftwareIcon() {
		return getImage("\\res\\logo\\logo.png");
	}

}
