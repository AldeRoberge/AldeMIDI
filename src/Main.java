import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URISyntaxException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import alde.commons.util.SplashScreen;
import properties.Properties;
import util.GetResource;

public class Main {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					setLookAndFeel();

					ConfigUI window = new ConfigUI();

					showSplashScreen(new Runnable() {
						@Override
						public void run() {
							window.frame.setVisible(true);
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});
	}

	private static void setLookAndFeel() {
		System.out.println("Setting look and feel...");

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
			System.err.println("Error with setting look and feel");
		}
	}

	private static void showSplashScreen(Runnable r) {

		if (Properties.SHOW_SPLASHSCREEN_ON_STARTUP.getValueAsBoolean()) {

			//log.info("Opening splash screen...");

			try {
				BufferedImage inImage = GetResource.getBufferedImage("/splashScreen/splashScreen_in.png");
				BufferedImage outImage = GetResource.getBufferedImage("/splashScreen/splashScreen_out.png");
				BufferedImage textImage = GetResource.getBufferedImage("/splashScreen/splashScreen_title.png");

				SplashScreen s = new SplashScreen(inImage, outImage, textImage);

				s.setAutomaticClose(10);
				s.setRunnableAfterClose(r);

				try {
					s.setSound(new File(ConfigUI.class.getResource("/splashScreen/boot.wav").toURI()));
				} catch (URISyntaxException e1) {
					e1.printStackTrace();
				}

				s.setVisible(true);

			} catch (Exception e) {
				//log.error("Error with opening splash screen.");
				e.printStackTrace();

				r.run();
			}
		} else {
			r.run();
		}

	}

}
