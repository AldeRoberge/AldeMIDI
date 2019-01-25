package perfectpitch;

import alde.commons.properties.Property;
import alde.commons.properties.PropertyFileManager;
import alde.commons.util.window.UtilityJFrame;
import midi.device.NotePlayer;
import midi.device.impl.BuiltInSynthDevice;
import midi.device.impl.Device;
import midi.device.test.TestKeyboardInput;
import midi.protocol.Note;
import org.slf4j.LoggerFactory;
import perfectpitch.util.GetResource;

import javax.sound.midi.MidiDevice.Info;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * Config device UI allows the user to change and test the input (perform) and output device
 */
class ConfigDeviceUI {

    private static org.slf4j.Logger log = LoggerFactory.getLogger(ConfigDeviceUI.class);

    private UtilityJFrame frame;

    private Device performDevice; //Input device
    private NotePlayer outputDevice; //Output device

    private JComboBox<NotePlayer> outputDeviceSelector;
    private JComboBox<Device> performDeviceSelector;
    private JPanel selectOutputDevice;

    private static PropertyFileManager configDevice = new PropertyFileManager("midi-device-config.properties");
    private static Property selectedPerformDevice = new Property("PERFORM_DEVICE", "<none>", configDevice);
    private static Property selectedOutputDevice = new Property("OUTPUT_DEVICE", "<none>", configDevice);

    private static Property hasBeenConfiguredOnce = new Property("HAS_BEEN_CONFIGURED_ONCE", Property.FALSE, configDevice);

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

        outputDevice = BuiltInSynthDevice.get();

        JPanel mainpanel = new JPanel();
        frame.getContentPane().add(mainpanel, BorderLayout.CENTER);
        mainpanel.setLayout(new BoxLayout(mainpanel, BoxLayout.Y_AXIS));

        JPanel perfomDevicePanel = new JPanel();
        mainpanel.add(perfomDevicePanel);

        JLabel performLabel = new JLabel("Input from this device : ");
        performLabel.setToolTipText("The program will receive MIDI from this device.");
        perfomDevicePanel.add(performLabel);

        performDeviceSelector = new JComboBox<Device>();
        performDeviceSelector.setForeground(Color.BLACK);
        perfomDevicePanel.add(performDeviceSelector);

        Component horizontalStrut = Box.createHorizontalStrut(0);
        perfomDevicePanel.add(horizontalStrut);

        JButton testInputBtn = new JButton("  Test input  ");
        perfomDevicePanel.add(testInputBtn);
        testInputBtn.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        testInputBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new TestKeyboardInput(performDevice);
            }
        });

        for (Device d : findMidiDevices()) {
            performDeviceSelector.addItem(d);

            if (d.toString().equals(selectedPerformDevice.getValue())) {
                performDeviceSelector.setSelectedItem(d);
            }
        }

        performDeviceSelector.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                updateSelectedPerformDevice();
            }
        });

        JPanel deviceAlsoProducesSoundPanel = new JPanel();
        mainpanel.add(deviceAlsoProducesSoundPanel);

        JRadioButton deviceProducesSoundBtn = new JRadioButton("This device also produces sound");
        deviceAlsoProducesSoundPanel.add(deviceProducesSoundBtn);

        deviceProducesSoundBtn.addActionListener(e -> {
            if (deviceProducesSoundBtn.isSelected()) {
                selectOutputDevice.setVisible(false);
                outputDevice = performDevice;
            } else {
                selectOutputDevice.setVisible(true);
                updateSelectedSoundDevice();
            }
        });

        deviceProducesSoundBtn.setToolTipText("Select this if your device is a synthesiser.");

        JPanel outputDevicePanel = new JPanel();
        mainpanel.add(outputDevicePanel);

        selectOutputDevice = new JPanel();
        outputDevicePanel.add(selectOutputDevice);

        JLabel soundLabel = new JLabel("Output to this device : ");
        soundLabel.setToolTipText("The program will send MIDI to this device.");
        selectOutputDevice.add(soundLabel);

        outputDeviceSelector = new JComboBox<>();
        selectOutputDevice.add(outputDeviceSelector);
        outputDeviceSelector.setForeground(Color.BLACK);

        outputDeviceSelector.addItem(BuiltInSynthDevice.get());

        outputDeviceSelector.addItemListener(e -> updateSelectedSoundDevice());

        JButton btnTestAudio = new JButton("Test output");
        outputDevicePanel.add(btnTestAudio);
        btnTestAudio.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        btnTestAudio.addActionListener(e -> {

            if (outputDevice != null) {
                Note debugNote = Note.C;
                log.info("Playing note '" + debugNote + "' on '" + outputDevice + "'.");
                outputDevice.playNote(debugNote);

            } else {
                log.error("Audio device is null!");
            }

        });

        JPanel buttonPanel = new JPanel();
        mainpanel.add(buttonPanel);

        JButton btnHelp = new JButton("Help");
        btnHelp.addActionListener(e -> {
            String message = "If your MIDI keyboard does not correctly receive or send input, \n"
                    + "make sure it is turned on and plugged in to the computer via MIDI USB.\n"
                    + "Try turning it on and off again.\n\n "
                    + "If sending MIDI fails, use the built-in synth '" + BuiltInSynthDevice.get() + "' to play audio.\n"
                    + "Make sure you have speakers or headphones plugged in.";
            showMessage(message);

        });
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        buttonPanel.add(btnHelp);

        JButton nextBtn = new JButton("Next");
        nextBtn.addActionListener(e -> {
            showMessage("Awesome! Let's do it.");
            hasBeenConfiguredOnce.setValue(Property.TRUE);
            callBack.accept(performDevice, outputDevice);
            frame.dispose();
        });
        buttonPanel.add(nextBtn);

        for (NotePlayer d : findMidiDevices()) {
            outputDeviceSelector.addItem(d);

            if (d.toString().equals(selectedOutputDevice.getValue())) {
                outputDeviceSelector.setSelectedItem(d);
            }
        }

        if (hasBeenConfiguredOnce.getValueAsBoolean()) {
            nextBtn.doClick();
        } else {
            frame.setVisible(true);
        }

    }

    private void updateSelectedPerformDevice() {
        Device newPerformDevice = (Device) performDeviceSelector.getSelectedItem();
        log.info("Perform (input) device selected : '" + newPerformDevice + "'.");
        performDevice = newPerformDevice;

        //Update Property file
        if (performDevice != null) {
            selectedPerformDevice.setValue(performDevice.toString());
        }
    }

    private void updateSelectedSoundDevice() {
        NotePlayer newOutputDevice = (NotePlayer) outputDeviceSelector.getSelectedItem();
        log.info("Output (sound) selected : '" + newOutputDevice + "'.");
        outputDevice = newOutputDevice;

        // Update property file
        if (outputDevice != null) {
            selectedOutputDevice.setValue(outputDevice.toString());
        }
    }

    private static List<Device> findMidiDevices() {
        List<Device> midiDevices = new ArrayList<Device>();

        for (Info info : MidiSystem.getMidiDeviceInfo()) {

            try {
                midiDevices.add(new Device(MidiSystem.getMidiDevice(info)));
                log.info("Added device : " + info);
            } catch (MidiUnavailableException e) {
                log.info("Error with loading device : " + info);
                e.printStackTrace();
            }
        }

        return midiDevices;

    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(new JFrame(), message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }
}
