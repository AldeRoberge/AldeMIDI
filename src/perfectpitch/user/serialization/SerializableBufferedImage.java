package perfectpitch.user.serialization;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;

/**
 * 
 * See https://stackoverflow.com/questions/15058663/how-to-serialize-an-object-that-includes-bufferedimages
 * Transient : The transient keyword in Java is used to indicate that a field should not be part of the serialization (which means saved, like to a file) process.
 *
 */
public class SerializableBufferedImage implements Serializable {
	public transient BufferedImage bufferedImage;

	public SerializableBufferedImage(BufferedImage image) {
		this.bufferedImage = image;
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
		ImageIO.write(bufferedImage, "png", out); // png is lossless
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		bufferedImage = ImageIO.read(in);
	}

	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}

}
