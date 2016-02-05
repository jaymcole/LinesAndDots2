package hollow.jaymc.linesanddots.gameObjects;

import java.util.ArrayList;
import java.util.List;

import hollow.jaymc.linesanddots.activities.LevelActivity;

/**
 * Created by jaymc on 12/10/2015.
 */
public class World {

    private String name;
    private int numberOfLevels;
    private int worldID;
    private List<Integer> scores;

    public World() {

    }

    public World (String name) {
        this.name = name;
    }

    public World (String name, int worldID) {
        this.name = name;
        this.worldID = worldID;
    }

    public World(String name, int worldID, int totalLevels, List<Integer> scores) {
        this.name = name;
        this.worldID = worldID;
        this.numberOfLevels = totalLevels;
        this.scores = scores;
    }

    public void setWorldName(String name) {
        this.name = name;
    }
    public String getWorldName() {
        return name;
    }

    public void setNumberOfLevels(int levels) {
        numberOfLevels = levels;
    }
    public int getNumberOfLevels() {
        return numberOfLevels;
    }

    public void increaseLevelCount(int n) {
        setNumberOfLevels(n + getNumberOfLevels());
    }

    public void setScores(List<Integer> scores) {
        this.scores = scores;
    }
    public void addScore(int score) {
        if(scores == null)
            scores = new ArrayList<Integer>();
        scores.add(score);
    }
    public int[] getScores() {
        int[] arr = new int[scores.size()];
        for(int i = 0; i < arr.length; i++) {
            arr[i] = scores.get(i);
        }
        return arr;
    }

    public void setWorldID(int id) {
        worldID = id;
    }
    public int getWorldID() {
        return worldID;
    }

}
