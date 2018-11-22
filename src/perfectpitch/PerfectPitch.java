package perfectpitch;

import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URISyntaxException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import alde.commons.ExampleConsole;
import alde.commons.util.SplashScreen;
import midi.device.NotePlayer;
import midi.device.impl.Device;
import perfectpitch.player.ui.chooser.PlayerChooserUI;
import perfectpitch.player.user.Player;
import perfectpitch.properties.Properties;
import perfectpitch.util.GetResource;

class PerfectPitch {

	ConfigDeviceUI configDevice;

	private Device performDevice;
	private NotePlayer audioDevice;

	private Player player;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new PerfectPitch();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private PerfectPitch() {
		setLookAndFeel();

		setupDebuggerConsole();

		showSplashScreen(new Runnable() {
			@Override
			public void run() {
				loadConfigDeviceUI();
			}
		});
	}

	private void setupDebuggerConsole() {
		int option = JOptionPane.showConfirmDialog(null, "Show debugger console?", "Show debugger console?", JOptionPane.YES_NO_OPTION);

		if (option == 0) {
			ExampleConsole e = new ExampleConsole();
		}
	}

	private void loadConfigDeviceUI() {
		new ConfigDeviceUI(new BiConsumer<Device, NotePlayer>() {
			@Override
			public void accept(Device t, NotePlayer u) {
				performDevice = t;
				audioDevice = u;
				loadPlayerChooserUI();
			}
		});
	}

	private void loadPlayerChooserUI() {

		new PlayerChooserUI(new Consumer<Player>() {
			@Override
			public void accept(Player p) {
				player = p;
				System.out.println("Received player, " + p);
				Runnable r = new Runnable() {
					@Override
					public void run() {
						System.out.println("Run game.");
					}
				};

				if (!p.isConfigured()) {

				} else {
					r.run();
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
				BufferedImage inImage = GetResource.getBufferedImage("/res/splashScreen/splashScreen_in.png");
				BufferedImage outImage = GetResource.getBufferedImage("/res/splashScreen/splashScreen_out.png");
				BufferedImage textImage = GetResource.getBufferedImage("/res/splashScreen/splashScreen_title.png");

				SplashScreen s = new SplashScreen(inImage, outImage, textImage);

				s.setAutomaticClose(10);
				s.setRunnableAfterClose(r);

				try {
					s.setSound(new File(ConfigDeviceUI.class.getResource("/splashScreen/boot.wav").toURI()));
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
