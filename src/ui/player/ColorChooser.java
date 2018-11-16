package ui.player;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.util.function.Consumer;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import alde.commons.util.window.UtilityJFrame;

public class ColorChooser {

	private JFrame frmColorChooser;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ColorChooser window = new ColorChooser(new Consumer<Color>() {
						@Override
						public void accept(Color t) {
							System.out.println("Color choosed : " + t);
						}
					});
					window.frmColorChooser.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ColorChooser(Consumer<Color> callback) {

		frmColorChooser = new UtilityJFrame();
		frmColorChooser.setTitle("Color chooser");
		frmColorChooser.setBounds(100, 100, 450, 300);
		frmColorChooser.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JColorChooser tcc = new JColorChooser(Color.BLACK);
		tcc.setPreviewPanel(null);
		frmColorChooser.getContentPane().add(tcc, BorderLayout.CENTER);

		tcc.getSelectionModel().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				Color newColor = tcc.getColor();
				System.out.println("Color choosed : " + newColor);
				callback.accept(newColor);
			}
		});
		frmColorChooser.setVisible(true);
	}

}
