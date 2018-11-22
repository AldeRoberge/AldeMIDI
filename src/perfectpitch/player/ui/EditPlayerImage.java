package perfectpitch.player.ui;

import javax.swing.JPanel;

import alde.commons.fileImporter.FileImporter;
import alde.commons.util.file.extensions.ExtensionFilter;
import perfectpitch.player.user.Player;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.function.Consumer;
import java.awt.event.ActionEvent;

public class EditPlayerImage extends JPanel {

	public EditPlayerImage(Player player, Runnable runAfter) {
		setLayout(new BorderLayout(0, 0));

		JPanel actionPanel = new JPanel();
		add(actionPanel, BorderLayout.SOUTH);

		PlayerImageViewer playerImagePanel = new PlayerImageViewer(player, true);

		add(playerImagePanel, BorderLayout.CENTER);

		JButton changeImageButton = new JButton("Change image", new ImageIcon("res/images/popup/user.png"));
		changeImageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FileImporter(k -> {
					BufferedImage b;
					try {
						b = ImageIO.read(k.get(0));
						playerImagePanel.setImage(b);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}, false, ExtensionFilter.PICTURE_FILES);
			}
		});
		actionPanel.add(changeImageButton);

		JButton changeColorButton = new JButton("Change color", new ImageIcon("res/images/popup/colorPicker.png"));
		changeColorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ColorChooser(new Consumer<Color>() {
					@Override
					public void accept(Color t) {
						playerImagePanel.setPlayerColor(t);
					}
				});
			}
		});
		actionPanel.add(changeColorButton);

		JButton nextButton = new JButton("Next");
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				runAfter.run();
			}
		});
		actionPanel.add(nextButton);

	}

}
