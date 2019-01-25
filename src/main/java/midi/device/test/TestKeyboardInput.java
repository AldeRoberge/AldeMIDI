package midi.device.test;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import alde.commons.util.window.UtilityJFrame;
import midi.device.impl.Device;
import midi.protocol.Note;

public class TestKeyboardInput {

	private UtilityJFrame frmTestingInputOn;
	private JTextArea debugInputTextArea;
	private JScrollPane scrollPane;
	private JButton btnItWorks;

	private boolean hasReceivedInfo = false;

	private boolean showDebugInfo = true;

	/**
	 * Create the application.
	 */
	public TestKeyboardInput(Device d) {

		d.open();

		initialize(d);
		frmTestingInputOn.setVisible(true);

		d.addListener(new Consumer<Note>() {
			@Override
			public void accept(Note note) {

				if (showDebugInfo) {

					printToDebug("Received note : '" + note + "'\n");

				}

				if (!hasReceivedInfo) {
					hasReceivedInfo = true;
					btnItWorks.setVisible(true);
				}

			}

		});

	}

	private void printToDebug(String string) {
		debugInputTextArea.append(string);

		JScrollBar vertical = scrollPane.getVerticalScrollBar();
		vertical.setValue(vertical.getMaximum());
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Device d) {
		frmTestingInputOn = new UtilityJFrame();
		frmTestingInputOn.setTitle("Testing input from " + d);
		frmTestingInputOn.setBounds(100, 100, 450, 300);
		frmTestingInputOn.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmTestingInputOn.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		frmTestingInputOn.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel.add(panel_2, BorderLayout.NORTH);

		JCheckBox chckbxShowDebugInfo = new JCheckBox("Show debug info");
		chckbxShowDebugInfo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showDebugInfo = chckbxShowDebugInfo.isSelected();
				scrollPane.setVisible(showDebugInfo);
			}

		});
		chckbxShowDebugInfo.setHorizontalAlignment(SwingConstants.LEFT);
		chckbxShowDebugInfo.setSelected(true);
		panel_2.add(chckbxShowDebugInfo);

		scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);

		debugInputTextArea = new JTextArea();
		debugInputTextArea.setAutoscrolls(true);
		debugInputTextArea.setLineWrap(true);
		debugInputTextArea.setEditable(false);
		scrollPane.setViewportView(debugInputTextArea);

		JPanel panel_1 = new JPanel();
		frmTestingInputOn.getContentPane().add(panel_1, BorderLayout.NORTH);

		Component verticalStrut = Box.createVerticalStrut(30);
		panel_1.add(verticalStrut);

		JLabel lblPressAnyKey = new JLabel("Press any key on your piano keyboard...");
		panel_1.add(lblPressAnyKey);

		btnItWorks = new JButton("It works!");
		btnItWorks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmTestingInputOn.setVisible(false);
				frmTestingInputOn.dispose();
			}
		});
		btnItWorks.setVisible(false);
		btnItWorks.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_1.add(btnItWorks);
	}

}
