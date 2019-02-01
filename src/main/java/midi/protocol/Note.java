package midi.protocol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum Note {

	//@formatter:off

	C("C", 0),
	CSharp("C#", 1),
	D("D", 2),
	DSharp("D#", 3),
	E("E", 4),
	F("F", 5),
	FSharp("F#", 6),
	G("G", 7),
	GSharp("G#", 8),
	A("A", 9),
	ASharp("A#", 10),
	B("B", 11);

	//@formatter:on

	private static Logger log = LoggerFactory.getLogger(Note.class);

	private String name;
	private int rank;
	private Octave octave;

	Note(String name, int rank) {
		this.name = name;
		this.rank = rank;
		setOctave(Octave.Five);
	}

	private Note setOctave(int octave) {
		setOctave(Octave.getOctave(octave));
		return this;
	}

	private void setOctave(Octave octave) {
		this.octave = octave;
	}

	/* MIDI octaves starts at octave -1.
	 * For future reference :  https://cdn.instructables.com/FWX/NBXG/H4AFZWE7/FWXNBXGH4AFZWE7.LARGE.jpg
	 */
	public int getMidi() {
		return (12 * (octave.octave + 1)) + rank;
	}

	public static Note fromMidi(int midiNote) {
		int octave = (midiNote / 12) - 1; //If you explicitly cast double to int, the decimal part will be truncated.
		int rank = midiNote - ((octave + 1) * 12); //0 = C

		Note n = getNote(rank).setOctave(octave);

		log.debug("MIDI NOTE : [" + midiNote + "] is (" + n + ")");

		return n;

		//return Note.valueOf(rank).set
	}

	// Returns note from rank (0 to 11) with octave 5
	public static Note getNote(int rank) {
		switch (rank) {
			case 0:
				return C;
			case 1:
				return CSharp;
			case 2:
				return D;
			case 3:
				return DSharp;
			case 4:
				return E;
			case 5:
				return F;
			case 6:
				return FSharp;
			case 7:
				return G;
			case 8:
				return GSharp;
			case 9:
				return A;
			case 10:
				return ASharp;
			case 11:
				return B;
			default:
				log.error("Invalid rank number : '" + rank + "'.");
				return Note.C;
		}
	}

	public static void main(String[] args) {
		/*for (Note n : Note.values()) {
			log.info("Value of midi.note : " + n.name + " for octave 5 is : " + n.getMidi(Octave.Five));
		}*/

		for (int i = 0; i < 127; i++) {
			fromMidi(i); //C0
		}

	}

	@Override
	public String toString() {
		return name + octave.octave;
	}

}
