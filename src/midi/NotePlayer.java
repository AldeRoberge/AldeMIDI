package midi;

import midi.note.Note;

public interface NotePlayer {

	/**
	 * Plays note on device
	 */
	void playNote(Note note);

	/**
	 * Gives the name of the device
	 */
	public String toString();

}
