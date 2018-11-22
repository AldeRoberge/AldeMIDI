package perfectpitch.player.ui.chooser;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.function.Consumer;

import javax.swing.*;

import alde.commons.util.window.UtilityJFrame;
import perfectpitch.player.ui.EditPlayerImage;
import perfectpitch.player.ui.PlayerImageViewer;
import perfectpitch.player.ui.chooser.panels.ChooseExistingOrNewPlayerPanel;
import perfectpitch.player.ui.chooser.panels.CreateNewPlayerPanel;
import perfectpitch.player.ui.chooser.panels.SelectExistingPlayerPanel;
import perfectpitch.player.user.Player;
import perfectpitch.util.GetResource;

public class PlayerChooserUI {

	private JPanel existingOrReturning;

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
		frame.setBounds(100, 100, 500, 275);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
					frame.setTitle("Player Creation");
					JPanel createNewPlayerPanel = new CreateNewPlayerPanel(new Consumer<Player>() {
						@Override
						public void accept(Player t) {
							setView(new EditPlayerImage(t, new Runnable() {
								@Override
								public void run() {
									callback.accept(t);
								}
							}));
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
