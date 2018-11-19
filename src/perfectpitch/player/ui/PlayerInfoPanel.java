package perfectpitch.player.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import perfectpitch.player.user.Player;

public class PlayerInfoPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public PlayerInfoPanel(Player player) {
		setLayout(new BorderLayout(0, 0));

		JPanel fileMainDetails = new JPanel(new BorderLayout(4, 2));
		fileMainDetails.setBorder(new EmptyBorder(0, 6, 0, 6));

		JPanel fileDetailsLabels = new JPanel(new GridLayout(0, 1, 2, 2));
		fileMainDetails.add(fileDetailsLabels, BorderLayout.WEST);

		JPanel fileDetailsValues = new JPanel(new GridLayout(0, 1, 2, 2));
		fileMainDetails.add(fileDetailsValues, BorderLayout.CENTER);

		JLabel levelLabel = new JLabel("Level 9001");
		fileDetailsValues.add(levelLabel);
		fileDetailsLabels.add(new JLabel("Level", JLabel.TRAILING));

		JLabel path = new JLabel("Nibba in Chief");
		fileDetailsValues.add(path);
		fileDetailsLabels.add(new JLabel("Rank", JLabel.TRAILING));

		JLabel date = new JLabel();
		date.setText("Stops asteroids");
		fileDetailsValues.add(date);
		fileDetailsLabels.add(new JLabel("Other", JLabel.TRAILING));

		JLabel size = new JLabel();
		size.setText("Takes steroids");
		fileDetailsValues.add(size);
		fileDetailsLabels.add(new JLabel("Other", JLabel.TRAILING));

		add(fileMainDetails);

	}

}
