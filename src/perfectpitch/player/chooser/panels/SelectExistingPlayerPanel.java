package perfectpitch.player.chooser.panels;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import perfectpitch.user.Player;
import perfectpitch.user.Players;

public class SelectExistingPlayerPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public SelectExistingPlayerPanel(Consumer<Player> callback, Runnable goBack) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel selectPanel = new JPanel();
		add(selectPanel);
		selectPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblSelectAPlayer = new JLabel("Select a player : ");
		selectPanel.add(lblSelectAPlayer);

		JComboBox<Player> playersComboBox = new JComboBox<Player>();

		for (Player p : Players.getPlayers()) {
			playersComboBox.addItem(p);
		}

		selectPanel.add(playersComboBox);

		JPanel acceptPanel = new JPanel();
		add(acceptPanel);
		acceptPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnOkay = new JButton("Next");
		btnOkay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				callback.accept((Player) playersComboBox.getSelectedItem());
			}
		});

		JButton btnBack = new JButton("Cancel");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goBack.run();
			}
		});
		acceptPanel.add(btnBack);
		acceptPanel.add(btnOkay);

	}

}
