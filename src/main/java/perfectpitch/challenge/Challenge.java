package perfectpitch.challenge;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

abstract class Challenge {

	// If it's completed
	private boolean isCompleted;

	private float timeStart;

	// Time taken to complete
	private float timeEnd;

	// Experience points
	int EXP;

	public void completed() throws Exception {
		if (isCompleted) {
			throw new Exception("Is aleady completed.");
		} else {
			isCompleted = true;
			timeEnd = System.currentTimeMillis() - timeStart;
			tellListeners();
		}
	}

	/**
	 * List of Consumers that accept the CompletedChallenge
	 */
	private List<Consumer<Challenge>> completedChallengeListeners = new ArrayList<>();

	/**
	 * Add a consumer that accepts the CompletedChallenge when the Challenge is completed
	 * @param completedChallengeListener
	 */
	public void addListener(Consumer<Challenge> completedChallengeListener) {
		this.completedChallengeListeners.add(completedChallengeListener);
	}

	private void tellListeners() {
		for (Consumer<Challenge> listener : completedChallengeListeners) {
			listener.accept(this);
		}
	}

	public Challenge(int EXP) {
		isCompleted = false;
	}

}
