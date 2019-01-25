package perfectpitch.player.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import perfectpitch.player.Player;

public class PlayerImageViewer extends JPanel {

	private static final long serialVersionUID = 2378457583L;

	private static Logger log = LoggerFactory.getLogger(PlayerImageViewer.class);

	private Player player;

	JPanel editImagePanel;
	private JPanel playerImagePanel;

	JLabel playerImage;

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

		log.info("Name : " + player.getName());

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

		playerImage = new JLabel(new ImageIcon(player.getImage()));
		playerImagePanel.add(playerImage, BorderLayout.CENTER);

	}

	void setPlayerColor(Color color) {
		player.setColor(color);
		playerImagePanel.setBorder(new LineBorder(color, 3, true));
	}

	public void setImage(BufferedImage read) {
		player.setImage(read);
		log.info("Set new user image.");
		playerImage.setIcon(new ImageIcon(player.getImage()));

	}

}
