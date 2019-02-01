package perfectpitch.challenge;

import midi.protocol.Note;
import perfectpitch.util.RandomUtil;

import java.util.Random;
import java.util.function.Consumer;

public class SingleNoteChallenge extends Challenge {

    Note noteToPlay;

    public SingleNoteChallenge(Consumer<Challenge> completedChallengeListener) {
        super(completedChallengeListener);

        noteToPlay = Note.getNote(RandomUtil.getRandomNumberInRange(0, 11));

        System.out.println("Play " + noteToPlay);
    }

    public void receiveNote(Note note) {
        if (noteToPlay.equals(note)) {
            System.out.println("Correct!");
        }
    }


}
