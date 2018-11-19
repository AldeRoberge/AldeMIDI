package perfectpitch.player.ui.chooser.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import alde.commons.util.WrapLayout;
import perfectpitch.player.ui.PlayerImageViewer;
import perfectpitch.player.ui.PlayerInfoPanel;
import perfectpitch.player.user.Player;
import perfectpitch.player.user.Players;

public class SelectExistingPlayerPanel extends JPanel {

	Player selected;

	/**
	 * Create the panel.
	 */
	public SelectExistingPlayerPanel(Consumer<Player> callback, Runnable goBack) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel selectPanel = new JPanel();
		add(selectPanel);
		selectPanel.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		selectPanel.add(scrollPane, BorderLayout.CENTER);

		JPanel warpLayout = new JPanel();
		warpLayout.setLayout(new WrapLayout());
		scrollPane.setViewportView(warpLayout);

		for (Player p : Players.getPlayers()) {

			warpLayout.add(new SelectablePanel<Player>(new PlayerImageViewer(p, false), p, new Consumer<Player>() {
				@Override
				public void accept(Player player) {
					callback.accept(player);
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

}
