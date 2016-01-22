package hollow.jaymc.linesanddots.utils;

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

    public static Level getTestLevel() {
        Level testLevel;
        List<Dot> dots = new ArrayList<>();
        List<Line> lines = new ArrayList<>();
        Random rand = new Random();
        dots.add(new Dot(rand.nextInt(100), rand.nextInt(100)));
        dots.add(new Dot(rand.nextInt(100), rand.nextInt(100)));
        dots.add(new Dot(rand.nextInt(100), rand.nextInt(100)));
        dots.add(new Dot(rand.nextInt(100), rand.nextInt(100)));
        dots.add(new Dot(rand.nextInt(100), rand.nextInt(100)));
        dots.add(new Dot(rand.nextInt(100), rand.nextInt(100)));

        for(int i = 0; i < dots.size()-2; i++) {
            lines.add(new Line(dots.get(i), i, dots.get(i+2),  i+2));
        }
        testLevel = new Level(dots, lines);
        testLevel.setHeight(100);
        testLevel.setWidth(100);
        return testLevel;
    }

}
