package ui.player.chooser;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import user.Player;
import user.Players;

public class SelectExistingPlayerPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public SelectExistingPlayerPanel(Consumer<Player> callback) {
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

		JButton btnOkay = new JButton("Okay");
		btnOkay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				callback.accept((Player) playersComboBox.getSelectedItem());
			}
		});
		acceptPanel.add(btnOkay);

	}

}
