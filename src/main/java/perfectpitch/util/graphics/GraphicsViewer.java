package perfectpitch.util.graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JComponent;
import javax.swing.JFrame;

import org.slf4j.LoggerFactory;

class GraphicsViewer extends JComponent {

	private static org.slf4j.Logger log = LoggerFactory.getLogger(GraphicsViewer.class);

	private static final long serialVersionUID = 1L;

	private GraphicsViewer() {

		setPreferredSize(new Dimension(500, 100));

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				repaint();
			}

		}, 0, 250);

		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
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

	private int mouseX;
	private int mouseY;

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		int height = this.getHeight();
		int width = this.getWidth();

		log.info("Size : Width : " + width + ", Height " + height);

		g.setColor(Color.GRAY);
		g.fillRect(0, 0, width, height); //Draw background

		g.setColor(Color.DARK_GRAY);

		for (int x = 0; x < width; x++) {
			if ((x % 10 == 0)) {
				g.drawLine(x, 0, x, height);
			}
		}

		for (int y = 0; y < height; y++) {
			if ((y % 10 == 0)) {
				g.drawLine(0, y, width, y);
			}
		}

		g.setColor(Color.WHITE);
		g.drawString(mouseX + " " + mouseY, 20, 20);

		drawGraph(g, height, width);

	}

	private void drawGraph(Graphics g, int height, int width) {

		for (int x = 0; x < width; x++) {
			int y = (x ^ 2) * 50;

			g.drawLine(-x, -y, -x, -y);

		}

	}

	private void createGUI() {
		final JFrame frame = new JFrame();
		frame.add(new GraphicsViewer(), BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				GraphicsViewer g = new GraphicsViewer();
				g.createGUI();
			}
		});

	}

}

abstract class Rule {

	public abstract boolean accepts(int x, int y);

}