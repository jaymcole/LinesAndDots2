package hollow.jaymc.linesanddots.objects;

import hollow.jaymc.linesanddots.activities.LevelActivity;

/**
 * Created by jaymc on 12/10/2015.
 */
public class World {

    private String name;

    public World(String name) {
        this.name = name;
    }

    public String getWorldName() {
        return name;
    }

    public int getNumberOfLevels() {
        return 50;
    }
}
