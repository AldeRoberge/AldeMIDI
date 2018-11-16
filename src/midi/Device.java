package midi;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

/**
 * MidiDevice wrapper with a few utilities
 *
 */
public class Device implements NotePlayer {

	public MidiDevice midiDevice;
	public Receiver receiver;

	boolean isOpened = false;

	public Device(MidiDevice midiDevice) {
		System.out.println("MidiDevice is a " + midiDevice.getClass().getName());

		this.midiDevice = midiDevice;

	}

	List<Consumer<ShortMessage>> listeners = new ArrayList<>();

	public void addListener(Consumer<ShortMessage> consumer) {
		listeners.add(consumer);
	}

	private void receivedMessage(MidiMessage message, long timeStamp) {
		for (Consumer<ShortMessage> listener : listeners) {
			listener.accept((ShortMessage) message);
		}
	}

	public void open() {

		if (!isOpened) {
			try {
				midiDevice.open();
			} catch (MidiUnavailableException e) {
				e.printStackTrace();
			}

			try {
				receiver = midiDevice.getReceiver();
			} catch (MidiUnavailableException e) {
				e.printStackTrace();
			}

			try {
				this.midiDevice.getTransmitter().setReceiver(new Receiver() {
					@Override
					public void send(MidiMessage message, long timeStamp) {
						receivedMessage(message, timeStamp);
					}

					@Override
					public void close() {
					}
				});
			} catch (MidiUnavailableException e) {
				e.printStackTrace();
			}

			isOpened = true;
		} else {
			System.err.println("This device is already opened.");
		}

	}

	public void close() {

		if (isOpened) {
			receiver.close();
			midiDevice.close();

			isOpened = false;
		} else {
			System.err.println("This device is already closed.");
		}

	}

	private void send(ShortMessage msg) {

		long timeStamp = -1;
		receiver.send(msg, timeStamp);
	}

	public String toString() {
		return midiDevice.getDeviceInfo().getName();
	}

	public void playNote(int noteCode, int velocity) {

		if (!isOpened) {
			open();
		}

		if (receiver == null) {
			throw new NullPointerException("Null receiver.");
		} else {
			ShortMessage myMsg = new ShortMessage();
			try {
				myMsg.setMessage(ShortMessage.NOTE_ON, 0, noteCode, velocity); // C = 90, velocity = 93
				send(myMsg);
			} catch (InvalidMidiDataException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void playNote(String note) { //TODO implement this
		playNote(60, 90);
	}

}
