package hollow.jaymc.linesanddots.utils;

/**
 * Created by Jay Cole on 11/17/2015.
 */
public class Point2D {
    private float x, y;

    public Point2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float distance(Point2D d) {
        return (float)Math.sqrt(Math.pow((d.getX() - x), 2) + Math.pow((d.getY() - y), 2));
    }

}
