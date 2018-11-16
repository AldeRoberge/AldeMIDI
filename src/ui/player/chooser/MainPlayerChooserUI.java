package ui.player.chooser;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.function.Consumer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import alde.commons.util.window.UtilityJFrame;
import user.Player;

public class MainPlayerChooserUI {

	JPanel existingOrReturning;

	private UtilityJFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainPlayerChooserUI window = new MainPlayerChooserUI(new Consumer<Player>() {
						@Override
						public void accept(Player p) {
							System.out.println("Received player : " + p.getName());
						}
					});
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainPlayerChooserUI(Consumer<Player> callback) {
		frame = new UtilityJFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		existingOrReturning = new ChooseExistingOrNewPlayerPanel(new Consumer<Boolean>() {

			@Override
			public void accept(Boolean existingPlayer) {
				if (existingPlayer) { //Existing player
					System.out.println("Existing player...");

					JPanel selectExistingPanel = new SelectExistingPlayerPanel(new Consumer<Player>() {
						@Override
						public void accept(Player t) {
							callback.accept(t);
						}
					});

					setView(selectExistingPanel);

				} else { //New player
					System.out.println("New player...");

					JPanel createNewPlayerPanel = new CreateNewPlayerPanel(new Consumer<Player>() {
						@Override
						public void accept(Player t) {
							callback.accept(t);
						}
					});

					setView(createNewPlayerPanel);

				}
			}

		});

		setView(existingOrReturning);

	}

	private void setView(JPanel jpanel) {

		frame.getContentPane().removeAll();
		frame.getContentPane().add(jpanel, BorderLayout.CENTER);

		frame.revalidate();
		frame.repaint();

	}

}
