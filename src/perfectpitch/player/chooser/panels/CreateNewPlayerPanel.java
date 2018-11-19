package perfectpitch.player.chooser.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import perfectpitch.user.Player;
import perfectpitch.user.Players;

public class CreateNewPlayerPanel extends JPanel {
	private JTextField nameInputField;
	private JLabel errorLabel;

	private JButton btnCreate;

	/**
	 * Create the panel.
	 */
	public CreateNewPlayerPanel(Consumer<Player> callback, Runnable goBack) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel topPanel = new JPanel();
		add(topPanel);
		topPanel.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		errorLabel = new JLabel("Error label");
		errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		errorLabel.setForeground(Color.RED);
		panel.add(errorLabel, BorderLayout.SOUTH);

		JPanel namePanel = new JPanel();
		panel.add(namePanel, BorderLayout.CENTER);

		JLabel nameInputLabel = new JLabel("Enter your name : ");
		nameInputLabel.setHorizontalAlignment(SwingConstants.CENTER);
		namePanel.add(nameInputLabel);

		nameInputField = new JTextField();
		nameInputField.setHorizontalAlignment(SwingConstants.CENTER);
		namePanel.add(nameInputField);
		nameInputField.setColumns(15);

		nameInputField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				checkInputNameForValidity();
			}

			public void removeUpdate(DocumentEvent e) {
				checkInputNameForValidity();
			}

			public void insertUpdate(DocumentEvent e) {
				checkInputNameForValidity();
			}

			public void checkInputNameForValidity() {

				String name = nameInputField.getText();

				if (name.length() == 0) {
					setError("");
					btnCreate.setEnabled(false);
				} else if (!name.matches("^[a-zA-Z0-9]+$")) {
					setError("Only letters and numbers allowed.");
					btnCreate.setEnabled(false);
				} else {
					setError("");
					btnCreate.setEnabled(true);
				}

			}
		});

		JPanel acceptPanel = new JPanel();
		add(acceptPanel);
		acceptPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String playerName = nameInputField.getText();

				boolean alreadyExists = false;

				for (Player p : Players.getPlayers()) {
					if (p.getName().equalsIgnoreCase(playerName)) {
						setError("Player already exists");
						alreadyExists = true;
					}
				}

				if (!alreadyExists) {
					Player player = new Player(playerName);
					callback.accept(player);
				}

			}
		});

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goBack.run();
			}
		});
		acceptPanel.add(btnCancel);
		acceptPanel.add(btnCreate);

		btnCreate.setEnabled(false);

		setError("");
		setVisible(true);

	}

	private void setError(String error) {
		if (error.equals("")) {
			errorLabel.setVisible(false);
			btnCreate.setEnabled(true);
		} else {
			errorLabel.setVisible(true);
			errorLabel.setText(error);
			btnCreate.setEnabled(false);
		}
	}

}
