package perfectpitch.player.ui.chooser;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.function.Consumer;

import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.slf4j.LoggerFactory;

import alde.commons.util.window.UtilityJFrame;
import perfectpitch.player.ui.EditPlayerImage;
import perfectpitch.player.ui.chooser.panels.ChooseExistingOrNewPlayerPanel;
import perfectpitch.player.ui.chooser.panels.CreateNewPlayerPanel;
import perfectpitch.player.ui.chooser.panels.SelectExistingPlayerPanel;
import perfectpitch.player.Player;
import perfectpitch.util.GetResource;

public class PlayerChooserUI {

	private static org.slf4j.Logger log = LoggerFactory.getLogger(PlayerChooserUI.class);

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
							log.info("Received player : " + p.getName());
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
					log.info("Existing player...");

					JPanel selectExistingPanel = new SelectExistingPlayerPanel(new Consumer<Player>() {
						@Override
						public void accept(Player t) {
							callback.accept(t);
						}
					}, () -> setMainMenu());

					setView(selectExistingPanel);

				} else { //New player
					log.info("New player...");
					frame.setTitle("Player Creation");
					JPanel createNewPlayerPanel = new CreateNewPlayerPanel(t -> {
						setView(new EditPlayerImage(t, () -> callback.accept(t)));
						callback.accept(t);
					}, () -> setMainMenu());
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
