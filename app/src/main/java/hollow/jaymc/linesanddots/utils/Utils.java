package hollow.jaymc.linesanddots.utils;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import hollow.jaymc.linesanddots.gameObjects.Dot;
import hollow.jaymc.linesanddots.gameObjects.Level;
import hollow.jaymc.linesanddots.gameObjects.Line;

/**
 * Created by jaymc
 * 1/14/2016.
 */
public class Utils {

    private static final String TAG = Utils.class.getName();
    public static final String SAVE_FILE = "saves.txt";
    /**
     * @return A default level.
     */
    public static Level getTestLevel() {
        Log.i(TAG, "Loading default level.");
        Level testLevel;
        List<Dot> dots = new ArrayList<>();
        List<Line> lines = new ArrayList<>();
//        Random rand = new Random();
//        dots.add(new Dot(rand.nextInt(100), rand.nextInt(100)));
//        dots.add(new Dot(rand.nextInt(100), rand.nextInt(100)));
//        dots.add(new Dot(rand.nextInt(100), rand.nextInt(100)));
//        dots.add(new Dot(rand.nextInt(100), rand.nextInt(100)));
//        dots.add(new Dot(rand.nextInt(100), rand.nextInt(100)));
//        dots.add(new Dot(rand.nextInt(100), rand.nextInt(100)));
//
//        for(int i = 0; i < dots.size()-2; i++) {
//            lines.add(new Line(dots.get(i), i, dots.get(i+2),  i+2));
//        }
        dots.add(new Dot(10, 10));
        dots.add(new Dot(90, 10));
        dots.add(new Dot(10, 90));
        dots.add(new Dot(90, 90));
        testLevel = new Level(dots, lines);
        testLevel.setHeight(100);
        testLevel.setWidth(100);
        testLevel.setTag("W-1L-1");
        return testLevel;
    }

    /**
     * Assumes " " as delimiter.
     * @return Integers converted from theString.
     */
    public static List<Integer> processString(String theString) {
        theString = theString.trim();
        String[] stringInts = theString.split(" ");
        List<Integer> ints = new ArrayList<>();

        for(int i = 0; i < stringInts.length; i++) {
            ints.add(Integer.parseInt(stringInts[i].trim()));
        }

        return ints;
    }

    /**
     *
     * @param ints Integers used to creates dots.
     * @return Creates and returns a list of Dot objects from a list of Integers.
     */
    public static List<Dot> getDots(List<Integer> ints) {
        List<Dot> dots = new ArrayList<>();
        for(int i = 0 ; i < ints.size()-1; i+=2) {
            dots.add(new Dot(ints.get(i), ints.get(i+1)));
        }
        return dots;
    }

    /**
     *
     * @param dots, ints
     * @return Creates and returns a list of Line objects from a list of Dot objects and Integers.
     */
    public static List<Line> getLines(List<Dot> dots, List<Integer> ints) {
        List<Line> lines = new ArrayList<>();
        for(int i = 0 ; i < ints.size()-1; i+=2) {
            lines.add(new Line(dots.get(ints.get(i)), ints.get(i), dots.get(ints.get(i+1)), ints.get(i+1) ));
        }
        return lines;
    }

    /**
     *
     * @param context Context for getFilesDir.
     * @return A file of saved scores.
     */
    public static File getSaveFile(Context context) {
        return new File(context.getFilesDir() + "/" + SAVE_FILE);
    }

    /**
     *
     * @param worldID Index of the world.
     * @param levelID Index of the level.
     * @return A level tag.
     */
    public static String createTag(int worldID, int levelID) {
        return "$W" + worldID + "L" +levelID;
    }

    /**
     *
     * @param newScore The new score to compare.
     * @param old The old score.
     * @return True if new score is higher.
     */
    public static boolean compareScore(String newScore, String old) {
        // TODO - Factor in turns and time.
        int scoreNew = Integer.parseInt(newScore.split(";")[1]);
        int scoreOld = Integer.parseInt(old.split(";")[1]);
        return scoreNew > scoreOld;
    }
}
