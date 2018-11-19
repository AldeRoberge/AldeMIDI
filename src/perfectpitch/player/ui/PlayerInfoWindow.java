package perfectpitch.player.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.function.Consumer;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import alde.commons.fileImporter.FileImporter;
import alde.commons.util.file.extensions.ExtensionFilter;
import alde.commons.util.window.UtilityJFrame;
import perfectpitch.player.user.Player;
import perfectpitch.player.user.Players;
import perfectpitch.util.GetResource;

public class PlayerInfoWindow extends UtilityJFrame {

	private JPanel editImagePanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlayerInfoWindow window = new PlayerInfoWindow(Players.getPlayers().get(0), new Runnable() {
						@Override
						public void run() {
							System.out.println("Closed.");
						}
					}, true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PlayerInfoWindow(Player player, Runnable runAfter, boolean isEditable) {
		setTitle("Player Edit");
		setBounds(100, 100, 270, 270);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setIconImage(GetResource.getSoftwareIcon());

		getContentPane().add(new PlayerImageViewer(player, isEditable), BorderLayout.CENTER);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				runAfter.run();
			}
		});

		setVisible(true);

	}

}

