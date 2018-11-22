package perfectpitch.challenge;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

class ChallengeHandler {

	private List<Challenge> uncompletedChallenges = new ArrayList<Challenge>();

	public void addChallenge(Challenge challenge) {
		uncompletedChallenges.add(challenge);

		challenge.addListener(new Consumer<Challenge>() {
			@Override
			public void accept(Challenge t) {
				uncompletedChallenges.remove(challenge);
			}
		});
	}
}
