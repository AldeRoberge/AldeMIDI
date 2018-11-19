package ui.player.chooser;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.function.Consumer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import alde.commons.util.window.UtilityJFrame;
import ui.player.chooser.panels.ChooseExistingOrNewPlayerPanel;
import ui.player.chooser.panels.CreateNewPlayerPanel;
import ui.player.chooser.panels.SelectExistingPlayerPanel;
import user.Player;
import util.GetResource;

public class PlayerChooserUI {

	JPanel existingOrReturning;

	private UtilityJFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlayerChooserUI window = new PlayerChooserUI(new Consumer<Player>() {
						@Override
						public void accept(Player p) {
							System.out.println("Received player : " + p.getName());
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PlayerChooserUI(Consumer<Player> callback) {
		frame = new UtilityJFrame();
		frame.setTitle("Player Selection");
		frame.setBounds(100, 100, 500, 180);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setIconImage(GetResource.getSoftwareIcon());

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
					}, new Runnable() {
						@Override
						public void run() {
							setMainMenu();
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
					}, new Runnable() {
						@Override
						public void run() {
							setMainMenu();
						}

					});

					setView(createNewPlayerPanel);

				}
			}

		});

		setView(existingOrReturning);
		frame.setVisible(true);
	}

	private void setMainMenu() {
		setView(existingOrReturning);
	}

	private void setView(JPanel jpanel) {

		frame.getContentPane().removeAll();
		frame.getContentPane().add(jpanel, BorderLayout.CENTER);

		frame.revalidate();
		frame.repaint();

	}

}