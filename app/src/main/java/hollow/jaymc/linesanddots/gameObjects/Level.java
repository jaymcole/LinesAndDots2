package hollow.jaymc.linesanddots.gameObjects;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Jay Cole
 * 11/19/2015.
 */
public class Level implements Cloneable{

    private static final String TAG = Level.class.getSimpleName();

    private List<Dot> dots;
    private List<Line> lines;
    private String tag;
    private int score;
    private int time;
    private int stars;
    private int turns;
    private int width;
    private int height;

    public Level() {
        this.dots = new ArrayList<Dot>();
        this.lines = new ArrayList<Line>();
        this.score = -1;
        this.time = -1;
        this.turns = -1;
        this.width = 100;
        this.height = 100;
        this.stars = 0;
    }

    public Level(List<Dot> dots, List<Line> lines) {
        this.dots = dots;
        this.lines = lines;
        this.score = -1;
        this.time = -1;
        this.turns = -1;
        this.width = 100;
        this.height = 100;
        this.stars = 0;
    }

    public Level(List<Dot> dots, List<Line> lines, int score, int time, int turns) {
        this.dots = dots;
        this.lines = lines;
        this.score = score;
        this.time = time;
        this.turns = turns;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
    public String getTag () {
        return tag;
    }

    public List<Dot> getDots() {
        return dots;
    }
    public void setDots(List<Dot> dots) {
        this.dots = dots;
    }

    public List<Line> getLines() {
        return lines;
    }
    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }

    public int getTime() {
        return time;
    }
    public void setTime(int time) {
        this.time = time;
    }

    public int getTurns() {
        return turns;
    }
    public void setTurns(int turns) {
        this.turns = turns;
    }

    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }

    public int getStart() {
        return stars;
    }
    public void setStars(int stars) {
        this.stars = stars;
    }

    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }

    @Override
     public Object clone() throws CloneNotSupportedException{
        List<Dot> dots = new ArrayList<>();
        List<Line> lines = new ArrayList<>();

        for (int i = 0; i < this.dots.size(); i++) {
            dots.add((Dot) this.dots.get(i).clone());
        }
        for (int i = 0; i < this.lines.size(); i++) {
            Line line = this.lines.get(i);
            lines.add(new Line(dots.get(line.getD1Index()), line.getD1Index(), dots.get(line.getD2Index()), line.getD2Index()));
//            Log.d(TAG, dots.get(line.getD1Index()) + " " + line.getD1Index()+ " " + dots.get(line.getD2Index()) + " " + line.getD2Index());

        }
        Level clonedLevel = new Level(dots, lines);
        clonedLevel.setStars(stars);
        return clonedLevel;
    }


}






































