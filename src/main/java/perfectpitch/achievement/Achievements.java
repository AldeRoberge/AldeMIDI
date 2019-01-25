package perfectpitch.achievement;

import java.util.ArrayList;
import java.util.List;

public class Achievements {

    public static final Achievement changeUserPicture = new Achievement("Personaliation l", "You changed your picture");

    public static List<Achievement> getAchievements() {
        List<Achievement> achievements = new ArrayList<Achievement>();
        achievements.add(changeUserPicture);
        return achievements;
    }

}
