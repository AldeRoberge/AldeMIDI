import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import javax.sound.midi.MidiDevice.Info;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import alde.commons.util.window.UtilityJFrame;
import midi.ArtificialSynthDevice;
import midi.Device;
import midi.NotePlayer;
import midi.TestKeyboard;
import util.GetResource;

public class ConfigDeviceUI {

	UtilityJFrame frame;

	private boolean deviceIsASynth;

	private Device performDevice;
	private NotePlayer audioDevice;

	/**
	 * Create the application.
	 */
	public ConfigDeviceUI(BiConsumer<Device, NotePlayer> callBack) {
		frame = new UtilityJFrame();
		frame.setTitle("Configuration");
		frame.setBounds(100, 100, 531, 207);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setIconImage(GetResource.getBufferedImage("/res/logo/logo.png"));

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(0, 0));
		frame.getContentPane().add(panel, BorderLayout.CENTER);

		JPanel nextPanel = new JPanel();
		panel.add(nextPanel, BorderLayout.SOUTH);

		JButton btnHelp = new JButton("Help");
		btnHelp.setFont(new Font("Segoe UI Light", Font.PLAIN, 15));
		nextPanel.add(btnHelp);
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String message;

				if (deviceIsASynth) {
					message = "If your synth does not correctly receive input, \n" + "make sure it is turned on and plugged in to the computer via MIDI.\n"
							+ "Try turning it on and off again.\n\n If everything fails, use the built-in synth '" + ArtificialSynthDevice.get() + "'.";
				} else {
					message = "Perfect Pitch has a built in Synthetiser. To hear it, your computer must have speakers or headphones plugged in.";
				}
				showMessage(message);
			}
		});
		btnHelp.setBackground(Color.RED);

		JButton btnNext = new JButton("Next");
		btnNext.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		nextPanel.add(btnNext);
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showMessage("Awesome! Let's do it.");
				callBack.accept(performDevice, audioDevice);
			}
		});
		btnNext.setBackground(Color.GREEN);
		btnNext.setForeground(Color.BLACK);

		JPanel optionsPanel = new JPanel();
		panel.add(optionsPanel, BorderLayout.CENTER);
		optionsPanel.setLayout(null);

		JLabel soundLabel = new JLabel("Output to this device : ");
		soundLabel.setBounds(50, 83, 143, 14);
		optionsPanel.add(soundLabel);

		JComboBox<Device> performDeviceSelector = new JComboBox<Device>();

		for (Device d : findMidiDevices()) {
			performDeviceSelector.addItem(d);
		}

		performDeviceSelector.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {

				Device notePlayer = (Device) performDeviceSelector.getSelectedItem();
				System.out.println("Perform (input) device selected : '" + notePlayer + "'.");
				performDevice = notePlayer;

			}
		});

		JComboBox<NotePlayer> soundDeviceSelector = new JComboBox<NotePlayer>();
		soundDeviceSelector.setBounds(178, 76, 168, 28);
		optionsPanel.add(soundDeviceSelector);

		soundDeviceSelector.addItem(ArtificialSynthDevice.get());

		soundDeviceSelector.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {

				NotePlayer notePlayer = (NotePlayer) soundDeviceSelector.getSelectedItem();
				System.out.println("Output (sound) selected : '" + notePlayer + "'.");
				audioDevice = notePlayer;

			}
		});

		audioDevice = ArtificialSynthDevice.get();

		JLabel performLabel = new JLabel("Perform on this device : ");
		performLabel.setBounds(50, 18, 143, 14);
		optionsPanel.add(performLabel);

		performDeviceSelector.setBounds(178, 11, 168, 28);
		optionsPanel.add(performDeviceSelector);

		JRadioButton rdbtnDeviceIsASynth = new JRadioButton("This device also produces sound");

		rdbtnDeviceIsASynth.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (rdbtnDeviceIsASynth.isSelected()) {

					deviceIsASynth = true;

					soundLabel.setVisible(false);
					soundDeviceSelector.setVisible(false);

					audioDevice = performDevice;
				} else {

					deviceIsASynth = false;

					soundLabel.setVisible(true);
					soundDeviceSelector.setVisible(true);

				}
			}

		});

		rdbtnDeviceIsASynth.setToolTipText("Select this if your device is a synthesiser.");
		rdbtnDeviceIsASynth.setBounds(104, 46, 191, 23);
		optionsPanel.add(rdbtnDeviceIsASynth);

		JButton btnTestAudio = new JButton("Test audio");
		btnTestAudio.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnTestAudio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (audioDevice != null) {

					System.out.println("Playing note 'C5' on '" + audioDevice + "'.");

					audioDevice.playNote("C5");

				} else {
					System.err.println("Audio device is null!");
				}

			}
		});

		btnTestAudio.setBackground(Color.BLUE);
		btnTestAudio.setBounds(366, 79, 114, 23);
		optionsPanel.add(btnTestAudio);

		JButton btnTestInput = new JButton("Test input");
		btnTestInput.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnTestInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TestKeyboard t = new TestKeyboard(performDevice);

			}
		});
		btnTestInput.setBackground(Color.BLUE);
		btnTestInput.setBounds(366, 14, 114, 23);
		optionsPanel.add(btnTestInput);

		for (NotePlayer d : findMidiDevices()) {
			soundDeviceSelector.addItem(d);
		}

		frame.setVisible(true);

	}

	public static List<Device> findMidiDevices() {
		List<Device> midiDevices = new ArrayList<Device>();

		for (Info info : MidiSystem.getMidiDeviceInfo()) {

			try {
				midiDevices.add(new Device(MidiSystem.getMidiDevice(info)));
				System.out.println("Added device : " + info);
			} catch (MidiUnavailableException e) {
				System.out.println("Error with loading device : " + info);
				e.printStackTrace();
			}
		}

		return midiDevices;

	}

	private void showMessage(String message) {
		JOptionPane.showMessageDialog(new JFrame(), message, "Information", JOptionPane.INFORMATION_MESSAGE);
	}
}
