package perfectpitch;

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
import javax.swing.*;

import alde.commons.util.window.UtilityJFrame;
import midi.device.NotePlayer;
import midi.device.impl.BuiltInSynthDevice;
import midi.device.impl.Device;
import midi.device.test.TestKeyboardInput;
import midi.protocol.Note;
import perfectpitch.util.GetResource;
import java.awt.FlowLayout;
import java.awt.Component;

class ConfigDeviceUI {

	private UtilityJFrame frame;

	private boolean deviceIsASynth;

	private Device performDevice;
	private NotePlayer audioDevice;

	private JComboBox<NotePlayer> soundDeviceSelector;
	private JComboBox<Device> performDeviceSelector;
	private JPanel selectOutputDevice;

	/**
	 * Create the application.
	 */
	public ConfigDeviceUI(BiConsumer<Device, NotePlayer> callBack) {
		frame = new UtilityJFrame();
		frame.setResizable(false);
		frame.setTitle("Configuration");
		frame.setBounds(100, 100, 520, 185);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setIconImage(GetResource.getSoftwareIcon());

		audioDevice = BuiltInSynthDevice.get();

		JPanel mainpanel = new JPanel();
		frame.getContentPane().add(mainpanel, BorderLayout.CENTER);
		mainpanel.setLayout(new BoxLayout(mainpanel, BoxLayout.Y_AXIS));

		JPanel perfomDevicePanel = new JPanel();
		mainpanel.add(perfomDevicePanel);

		JLabel performLabel = new JLabel("Input from this device : ");
		perfomDevicePanel.add(performLabel);

		performDeviceSelector = new JComboBox<Device>();
		performDeviceSelector.setForeground(Color.BLACK);
		perfomDevicePanel.add(performDeviceSelector);
		
		Component horizontalStrut = Box.createHorizontalStrut(0);
		perfomDevicePanel.add(horizontalStrut);

		JButton btnTestInput = new JButton("  Test input  ");
		perfomDevicePanel.add(btnTestInput);
		btnTestInput.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnTestInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TestKeyboardInput(performDevice);
			}
		});

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

		JPanel deviceAlsoProducesSoundPanel = new JPanel();
		mainpanel.add(deviceAlsoProducesSoundPanel);

		JRadioButton btnDeviceIsASynth = new JRadioButton("This device also produces sound");
		deviceAlsoProducesSoundPanel.add(btnDeviceIsASynth);

		btnDeviceIsASynth.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (btnDeviceIsASynth.isSelected()) {
					deviceIsASynth = true;
					selectOutputDevice.setVisible(false);
					audioDevice = performDevice;
				} else {
					deviceIsASynth = false;
					selectOutputDevice.setVisible(true);
					updateSelectedSoundDevice();
				}
			}
		});

		btnDeviceIsASynth.setToolTipText("Select this if your device is a synthesiser.");

		JPanel outputDevicePanel = new JPanel();
		mainpanel.add(outputDevicePanel);

		selectOutputDevice = new JPanel();
		outputDevicePanel.add(selectOutputDevice);

		JLabel soundLabel = new JLabel("Output to this device : ");
		selectOutputDevice.add(soundLabel);

		soundDeviceSelector = new JComboBox<NotePlayer>();
		selectOutputDevice.add(soundDeviceSelector);
		soundDeviceSelector.setForeground(Color.BLACK);

		soundDeviceSelector.addItem(BuiltInSynthDevice.get());

		soundDeviceSelector.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {

				updateSelectedSoundDevice();

			}
		});

		JButton btnTestAudio = new JButton("Test output");
		outputDevicePanel.add(btnTestAudio);
		btnTestAudio.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnTestAudio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (audioDevice != null) {

					Note debugNote = Note.C;
					System.out.println("Playing note '" + debugNote + "' on '" + audioDevice + "'.");
					audioDevice.playNote(debugNote);

				} else {

					System.err.println("Audio device is null!");

				}

			}
		});

		JPanel buttonPanel = new JPanel();
		mainpanel.add(buttonPanel);

		JButton btnHelp = new JButton("Help");
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String message;

				if (deviceIsASynth) {
					message = "If your synth does not correctly receive input, \n" + "make sure it is turned on and plugged in to the computer via MIDI.\n"
							+ "Try turning it on and off again.\n\n If everything fails, use the built-in synth '" + BuiltInSynthDevice.get() + "'.";
				} else {
					message = "Perfect Pitch has a built in Synthetiser. To hear it, your computer must have speakers or headphones plugged in.";
				}

				showMessage(message);

			}
		});
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		buttonPanel.add(btnHelp);

		btnTestInput = new JButton("Next");
		btnTestInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showMessage("Awesome! Let's do it.");
				callBack.accept(performDevice, audioDevice);
				frame.dispose();
			}
		});
		buttonPanel.add(btnTestInput);

		for (NotePlayer d : findMidiDevices()) {
			soundDeviceSelector.addItem(d);
		}

		frame.setVisible(true);

	}

	private void updateSelectedSoundDevice() {
		NotePlayer notePlayer = (NotePlayer) soundDeviceSelector.getSelectedItem();
		System.out.println("Output (sound) selected : '" + notePlayer + "'.");
		audioDevice = notePlayer;
	}

	private static List<Device> findMidiDevices() {
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
