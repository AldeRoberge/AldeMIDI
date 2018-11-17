package midi.note;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum Octave {

	//@formatter:off

	MinusOne(-1),
	Zero(0),
	One(1),
	Two(2),
	Three(3),
	Four(4),
	Five(5),
	Six(6),
	Seven(7),
	Eight(8),
	Nine(9);

	//@formatter:on

	static Logger log = LoggerFactory.getLogger(Octave.class);

	int octave;

	Octave(int octave) {
		this.octave = octave;
	}

	public static Octave getOctave(int octave) {
		switch (octave) {
			case -1:
				return Octave.MinusOne;
			case 0:
				return Octave.Zero;
			case 1:
				return Octave.One;
			case 2:
				return Octave.Two;
			case 3:
				return Octave.Three;
			case 4:
				return Octave.Four;
			case 5:
				return Octave.Five;
			case 6:
				return Octave.Six;
			case 7:
				return Octave.Seven;
			case 8:
				return Octave.Eight;
			case 9:
				return Octave.Nine;
			default :
				log.error("Invalid Octave number : '" + octave + "'.");
				return Octave.Five;
		}

	}

}
