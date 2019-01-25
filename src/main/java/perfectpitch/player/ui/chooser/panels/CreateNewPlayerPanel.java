package perfectpitch.player.ui.chooser.panels;

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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import perfectpitch.player.password.PasswordStorage;
import perfectpitch.player.password.PasswordStorage.CannotPerformOperationException;
import perfectpitch.player.user.Player;
import perfectpitch.player.user.Players;

public class CreateNewPlayerPanel extends JPanel {
	private JTextField nameInputField;
	private JLabel errorLabel;

	private JButton btnCreate;
	private JPasswordField passwordField;

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

		JPanel userInfoPanel = new JPanel();
		panel.add(userInfoPanel, BorderLayout.NORTH);
		userInfoPanel.setLayout(new BoxLayout(userInfoPanel, BoxLayout.Y_AXIS));

		JPanel userPanel = new JPanel();
		userInfoPanel.add(userPanel);

		JLabel nameInputLabel = new JLabel("Enter your name : ");
		userPanel.add(nameInputLabel);
		nameInputLabel.setHorizontalAlignment(SwingConstants.CENTER);

		nameInputField = new JTextField();
		userPanel.add(nameInputField);
		nameInputField.setHorizontalAlignment(SwingConstants.CENTER);
		nameInputField.setColumns(20);

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

			void checkInputNameForValidity() {

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

		JPanel passwordPanel = new JPanel();
		userInfoPanel.add(passwordPanel);

		JLabel lblEnterYourPassword = new JLabel("Enter your password :  ");
		passwordPanel.add(lblEnterYourPassword);

		passwordField = new JPasswordField();
		passwordPanel.add(passwordField);
		passwordField.setColumns(17);

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
					Player player;
					try {
						player = new Player(playerName, PasswordStorage.createHash(passwordField.getPassword()));
						callback.accept(player);
					} catch (CannotPerformOperationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
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
