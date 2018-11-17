package midi;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

import midi.note.Note;

public class ArtificialSynthDevice implements NotePlayer {

	static ArtificialSynthDevice instance;

	private ArtificialSynthDevice() {
	}

	public static ArtificialSynthDevice get() {
		if (instance == null) {
			instance = new ArtificialSynthDevice();
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
