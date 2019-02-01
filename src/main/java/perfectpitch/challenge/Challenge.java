package perfectpitch.challenge;

import java.util.function.Consumer;

abstract class Challenge {

    public float timeStart;
    public float timeEnd;

    private boolean isCompleted = false;

    private final Consumer<Challenge> completedChallengeListener;

    public Challenge(Consumer<Challenge> completedChallengeListener) {
        this.completedChallengeListener = completedChallengeListener;
    }

    public void begin() {
        timeStart = System.currentTimeMillis();
    }

    public void completed() throws Exception {
        if (isCompleted) {
            throw new Exception("Is already completed.");
        } else {
            isCompleted = true;
            timeEnd = System.currentTimeMillis() - timeStart;
            completedChallengeListener.accept(this);
        }
    }

}
