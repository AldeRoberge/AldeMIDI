package perfectpitch.player;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import perfectpitch.user.Player;
import perfectpitch.user.Players;
import perfectpitch.util.GetResource;

public class PlayerInfoWindow extends JFrame {

	private JPanel editImagePanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlayerInfoWindow window = new PlayerInfoWindow(Players.getPlayers().get(0));

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PlayerInfoWindow(Player player) {
		setTitle("Player Edit");
		setBounds(100, 100, 270, 270);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(GetResource.getSoftwareIcon());

		JPanel panel = new PlayerImageViewer(player);
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		setVisible(true);

	}

}
