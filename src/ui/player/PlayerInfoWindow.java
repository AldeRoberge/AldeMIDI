package ui.player;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import user.Player;
import user.Players;
import util.GetResource;

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
		setTitle("Player Info");
		setBounds(100, 100, 639, 270);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(GetResource.getSoftwareIcon());

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		panel.add(tabbedPane, BorderLayout.CENTER);

		JPanel playerPanel = new JPanel();
		tabbedPane.addTab("Me", null, playerPanel, null);
		playerPanel.setLayout(new BorderLayout(0, 0));

		playerPanel.add(getPlayerPanel(player), BorderLayout.CENTER);

		JPanel game = new JPanel();
		tabbedPane.addTab("Game", null, game, null);

	}

	JComponent getPlayerPanel(Player player) {
		JSplitPane splitPane = new JSplitPane();
		splitPane.setEnabled(false);
		splitPane.setDividerLocation(200);
		splitPane.setResizeWeight(0.5);

		JPanel panel = new PlayerImageViewer(player);
		splitPane.setLeftComponent(panel);

		JPanel panel_1 = new PlayerDetailsPanel(player);
		splitPane.setRightComponent(panel_1);

		setVisible(true);

		return splitPane;
	}

}
