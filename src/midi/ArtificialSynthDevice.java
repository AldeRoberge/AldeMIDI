package midi;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

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
	public void playNote(String note) {
		Pattern p1 = new Pattern("V0 I[Piano] C");
		player.play(p1);
	}


	@Override
	public String toString() {
		return "Built-In Synth Device";
	}

}
