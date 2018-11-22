package perfectpitch.player.ui.chooser.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import alde.commons.util.WrapLayout;
import crypto.PasswordStorage;
import crypto.PasswordStorage.CannotPerformOperationException;
import crypto.PasswordStorage.InvalidHashException;
import perfectpitch.player.ui.PlayerImageViewer;
import perfectpitch.player.ui.PlayerInfoPanel;
import perfectpitch.player.user.Player;
import perfectpitch.player.user.Players;

public class SelectExistingPlayerPanel extends JPanel {

	Player selected;

	/**
	 * Create the panel.
	 */
	public SelectExistingPlayerPanel(final Consumer<Player> callback, final Runnable goBack) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel selectPanel = new JPanel();
		selectPanel.setBorder(null);
		add(selectPanel);
		selectPanel.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(null);
		selectPanel.add(scrollPane, BorderLayout.CENTER);

		JPanel warpLayout = new JPanel();
		warpLayout.setBorder(null);
		warpLayout.setLayout(new WrapLayout());
		scrollPane.setViewportView(warpLayout);

		for (Player p : Players.getPlayers()) {

			warpLayout.add(new SelectablePanel<Player>(new PlayerImageViewer(p, false), p, new Consumer<Player>() {
				@Override
				public void accept(Player player) {

					try {

						String password = getPassword();

						if (password == null || password.isEmpty()) {
							System.out.println("Password is null or empty");
						} else {
							if (PasswordStorage.verifyPassword(password, player.getHashedPassword())) {
								System.out.println("Verified!");
								callback.accept(player);
							} else {
								System.out.println("Not verified...");
							}
						}

					} catch (CannotPerformOperationException e) {
						System.out.println("Error...");
						e.printStackTrace();
					} catch (InvalidHashException e) {
						System.out.println("Error..");
						e.printStackTrace();
					}

				}
			}));
			//playersComboBox.addItem(p);
		}

		JPanel acceptPanel = new JPanel();
		add(acceptPanel);
		acceptPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnBack = new JButton("Cancel");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goBack.run();
			}
		});
		acceptPanel.add(btnBack);

	}

	public String getPassword() {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Password : ");
		JPasswordField pass = new JPasswordField(10);
		panel.add(label);
		panel.add(pass);
		String[] options = new String[] { "OK", "Cancel" };
		int option = JOptionPane.showOptionDialog(null, panel, "Enter your password", JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
		if (option == 0) {
			return new String(pass.getPassword());
		}
		return null;
	}

}
