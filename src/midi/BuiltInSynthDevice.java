package midi;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

import midi.note.Note;

/**
 * Artificial Synth Device is a device that plays notes using the computer to create sounds.
 *
 */
public class BuiltInSynthDevice implements NotePlayer {

	static BuiltInSynthDevice instance;

	private BuiltInSynthDevice() {
	}

	public static BuiltInSynthDevice get() {
		if (instance == null) {
			instance = new BuiltInSynthDevice();
		}

		return instance;
	}

	Player player = new Player();

	@Override
	public void playNote(Note note) {
		Pattern p1 = new Pattern("V0 I[Piano] " + note.toString());
		player.play(p1);
	}

	@Override
	public String toString() {
		return "Built-In Synth Device";
	}

}
