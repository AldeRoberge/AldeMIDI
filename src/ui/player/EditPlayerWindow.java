package ui.player;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import alde.commons.util.window.UtilityJFrame;
import user.Player;
import user.Players;

public class EditPlayerWindow {

	private JFrame frmEditPlayer;
	private JPanel editImagePanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditPlayerWindow window = new EditPlayerWindow(Players.getPlayers().get(0));
					window.frmEditPlayer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EditPlayerWindow(Player player) {
		frmEditPlayer = new UtilityJFrame();
		frmEditPlayer.setTitle("Player Info");
		frmEditPlayer.setBounds(100, 100, 639, 270);
		frmEditPlayer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setEnabled(false);
		splitPane.setDividerLocation(200);
		splitPane.setResizeWeight(0.5);
		frmEditPlayer.getContentPane().add(splitPane, BorderLayout.CENTER);

		JPanel panel = new PlayerImageViewer(player);
		splitPane.setLeftComponent(panel);

		JPanel panel_1 = new PlayerDetailsPanel(player);
		splitPane.setRightComponent(panel_1);

	}

}
