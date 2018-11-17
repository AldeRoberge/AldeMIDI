package midi;

import midi.note.Note;

public interface NotePlayer {

	void playNote(Note note);

	public String toString();

}
