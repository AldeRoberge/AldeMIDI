package perfectpitch.challenge;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class Challenge {

	// If it's completed
	boolean isCompleted = false;

	float timeStart;

	// Time taken to complete
	float timeEnd;

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
	List<Consumer<Challenge>> completedChallengeListeners = new ArrayList<>();

	/**
	 * Add a consumer that accepts the CompletedChallenge when the Challenge is completed
	 * @param consumer
	 */
	public void addListener(Consumer<Challenge> completedChallengeListener) {
		this.completedChallengeListeners.add(completedChallengeListener);
	}

	public void tellListeners() {
		for (Consumer<Challenge> listener : completedChallengeListeners) {
			listener.accept(this);
		}
	}

	public Challenge(int EXP) {
		isCompleted = false;
	}

}
