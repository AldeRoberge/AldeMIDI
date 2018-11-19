package ui.player.chooser.panels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ChooseExistingOrNewPlayerPanel extends JPanel {

	/**
	 * Create the panel.
	 * 
	 * Returns true if existing player
	 * False if new player
	 * 
	 */
	public ChooseExistingOrNewPlayerPanel(Consumer<Boolean> callback) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel labelPanel = new JPanel();
		add(labelPanel);
		labelPanel.setLayout(new BorderLayout(0, 0));

		JLabel label = new JLabel("Who are you?");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		labelPanel.add(label, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		add(buttonPanel);

		JButton existingPlayerButton = new JButton("Existing player");
		existingPlayerButton.setFocusable(false);
		existingPlayerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				callback.accept(true);
			}
		});
		buttonPanel.add(existingPlayerButton);

		JButton newPlayerButton = new JButton("New player");
		newPlayerButton.requestFocus();
		newPlayerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				callback.accept(false);
			}
		});
		buttonPanel.add(newPlayerButton);

		JPanel bottomPanel = new JPanel();
		add(bottomPanel);
		bottomPanel.setLayout(new BorderLayout(0, 0));

	}

}
