package perfectpitch.player.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.border.LineBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import alde.commons.fileImporter.FileImporter;
import alde.commons.util.file.extensions.ExtensionFilter;
import perfectpitch.player.user.Player;

public class PlayerImageViewer extends JPanel {

	private static final long serialVersionUID = 2378457583L;

	private static Logger log = LoggerFactory.getLogger(PlayerImageViewer.class);

	private Player player;

	JPanel editImagePanel;
	private JPanel playerImagePanel;

	public PlayerImageViewer(Player player, boolean isEditable) {

		this.player = player;
		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		this.add(panel);

		panel.setLayout(new BorderLayout(0, 0));

		Component horizontalStrut = Box.createHorizontalStrut(20);
		panel.add(horizontalStrut, BorderLayout.WEST);

		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		panel.add(horizontalStrut_1, BorderLayout.EAST);

		Component verticalStrut = Box.createVerticalStrut(20);
		panel.add(verticalStrut, BorderLayout.NORTH);

		JPanel playerNamePanel = new JPanel();
		panel.add(playerNamePanel, BorderLayout.SOUTH);
		playerNamePanel.setLayout(new BorderLayout(0, 0));

		System.out.println("Name : " + player.getName());

		JLabel lblNewLabel = new JLabel(player.getName());
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		playerNamePanel.add(lblNewLabel, BorderLayout.CENTER);

		Component verticalStrut_1 = Box.createVerticalStrut(5);
		playerNamePanel.add(verticalStrut_1, BorderLayout.NORTH);

		Component verticalStrut_2 = Box.createVerticalStrut(5);
		playerNamePanel.add(verticalStrut_2, BorderLayout.SOUTH);

		playerImagePanel = new JPanel();
		panel.add(playerImagePanel, BorderLayout.CENTER);
		playerImagePanel.setLayout(new BorderLayout(0, 0));

		setPlayerColor(player.getColor());

		JLabel playerImage = new JLabel(new ImageIcon(player.getImage()));
		playerImagePanel.add(playerImage, BorderLayout.CENTER);

		// build poup menu
		final JPopupMenu popup = new JPopupMenu();
		// New project menu item
		JMenuItem menuItem = new JMenuItem("Edit color...", new ImageIcon("res/images/popup/colorPicker.png"));
		menuItem.setMnemonic(KeyEvent.VK_P);
		menuItem.getAccessibleContext().setAccessibleDescription("New Project");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ColorChooser(new Consumer<Color>() {
					@Override
					public void accept(Color t) {
						setPlayerColor(t);
					}
				});
			}
		});
		popup.add(menuItem);

		// New File menu item
		menuItem = new JMenuItem("Edit image...", new ImageIcon("res/images/popup/perfectpitch.player.user.png"));
		menuItem.setMnemonic(KeyEvent.VK_F);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FileImporter(k -> {
					try {
						player.setImage(ImageIO.read(k.get(0)));
						log.info("Set new perfectpitch.player.user image.");
						playerImage.setIcon(new ImageIcon(player.getImage()));
					} catch (IOException e1) {
						log.info("Could not read image.");
						e1.printStackTrace();
					}
				}, false, ExtensionFilter.PICTURE_FILES);
			}
		});
		popup.add(menuItem);

		// add mouse listener
		playerImagePanel.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				showPopup(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				showPopup(e);
			}

			private void showPopup(MouseEvent e) {
				if (e.isPopupTrigger()) {
					popup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});

	}

	private void setPlayerColor(Color color) {
		player.setColor(color);
		playerImagePanel.setBorder(new LineBorder(color, 3, true));
	}

}
