package perfectpitch.challenge;

import java.util.ArrayList;
import java.util.List;

class ChallengeHandler {





    private List<Challenge> uncompletedChallenges = new ArrayList<Challenge>();

    private void generateNewChallenge() {
        Challenge challenge = new SingleNoteChallenge(e -> uncompletedChallenges.remove(e));
        uncompletedChallenges.add(challenge);
    }

}
