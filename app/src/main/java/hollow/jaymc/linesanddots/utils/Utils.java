package hollow.jaymc.linesanddots.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hollow.jaymc.linesanddots.gameObjects.Dot;
import hollow.jaymc.linesanddots.gameObjects.Level;
import hollow.jaymc.linesanddots.gameObjects.Line;

/**
 * Created by jaymc on 1/14/2016.
 */
public class Utils {

    private static final String TAG = Utils.class.getName();

    /**
     * Generates a default Level object.
     * @return
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
        return testLevel;
    }

    /**
     * Converts theString into a list of Integers.
     * Assumes " " as delimiter.
     * @return
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
     * Creates and returns a list of Dot objects from a list of Integers.
     * @param ints
     * @return
     */
    public static List<Dot> getDots(List<Integer> ints) {
        List<Dot> dots = new ArrayList<>();
        for(int i = 0 ; i < ints.size()-1; i+=2) {
            dots.add(new Dot(ints.get(i), ints.get(i+1)));
        }
        return dots;
    }

    /**
     * Creates and returns a list of Line objects from a list of Dot objects and Integers.
     * @param dots, ints
     * @return
     */
    public static List<Line> getLines(List<Dot> dots, List<Integer> ints) {
        List<Line> lines = new ArrayList<>();
        for(int i = 0 ; i < ints.size()-1; i+=2) {
            lines.add(new Line(dots.get(ints.get(i)), ints.get(i), dots.get(ints.get(i+1)), ints.get(i+1) ));
        }
        return lines;
    }
}
