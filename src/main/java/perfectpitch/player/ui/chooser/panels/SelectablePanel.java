package perfectpitch.player.ui.chooser.panels;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.function.Consumer;

import javax.swing.JPanel;

public class SelectablePanel<T> extends JPanel {

	T value;

	public SelectablePanel(JPanel panel, T value, Consumer<T> callback) {
		this.value = value;

		setLayout(new BorderLayout(0, 0));

		add(panel, BorderLayout.CENTER);

		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				callback.accept(value);
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		});

	}

}
