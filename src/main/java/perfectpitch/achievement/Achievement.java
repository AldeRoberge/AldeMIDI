package perfectpitch.achievement;

import java.io.Serializable;

public class Achievement implements Serializable {

    final String name;

    final String description;

    public Achievement(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
