package perfectpitch;

import alde.commons.ExampleConsole;
import alde.commons.util.SplashScreen;
import midi.device.NotePlayer;
import midi.device.impl.Device;
import org.slf4j.LoggerFactory;
import perfectpitch.player.ui.chooser.PlayerChooserUI;
import perfectpitch.player.Player;
import perfectpitch.properties.Properties;
import perfectpitch.util.GetResource;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URISyntaxException;

class PerfectPitch {

    private static org.slf4j.Logger log = LoggerFactory.getLogger(PerfectPitch.class);

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

        //setupDebuggerConsole();

        showSplashScreen(() -> loadConfigDeviceUI());
    }

    private void setupDebuggerConsole() {

        int option = JOptionPane.showConfirmDialog(null, "Show debugger console?", "Show debugger console?", JOptionPane.YES_NO_OPTION);

        if (option == 0) {
            new ExampleConsole();
        }
    }

    private void loadConfigDeviceUI() {
        new ConfigDeviceUI((t, u) -> {
            performDevice = t;
            audioDevice = u;
            loadPlayerChooserUI();
        });
    }

    private void loadPlayerChooserUI() {

        new PlayerChooserUI(p -> {
            player = p;
            log.info("Received player, " + p);
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    log.info("Run game.");
                }
            };

            if (!p.isConfigured()) {

            } else {
                r.run();
            }
        });
    }

    private static void setLookAndFeel() {
        log.info("Setting look and feel...");

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
            e1.printStackTrace();
            log.error("Error with setting look and feel");
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
                    s.setSound(new File(ConfigDeviceUI.class.getResource("res/splashScreen/boot.wav").toURI()));
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
