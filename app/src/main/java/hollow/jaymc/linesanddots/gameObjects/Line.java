package hollow.jaymc.linesanddots.gameObjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import hollow.jaymc.linesanddots.utils.Point2D;


/**
 * Created by Jay Cole on 11/17/2015.
 */
public class Line implements Cloneable{

    public static final double THRESHOLD = 0.1;

    private Dot d1;
    private Dot d2;
    private int d1Index;
    private int d2Index;
    private boolean weGood = false;
    private boolean isCrossing = false;
    private int c = Color.CYAN;

    public Line(Dot d1, int d1Index, Dot d2, int d2Index) {
        this.d1 = d1;
        this.d2 = d2;
        d1.addLine(this);
        d2.addLine(this);
        this.d1Index = d1Index;
        this.d2Index = d2Index;
    }

    public int getD1Index() {
        return d1Index;
    }
    public int getD2Index() {
        return d2Index;
    }

    public void render(Canvas canvas, Paint paint) {
        if(isCrossing)
           paint.setColor(Color.RED);
        else
            paint.setColor(c);
        canvas.drawLine(d1.getX(), d1.getY(), d2.getX(), d2.getY(), paint);
    }

    public Point2D getIntersection(Line theLine) {
        // Ax + Bx = C
        if (!this.equals(theLine)) {
            float A1 = (d2.getY() - d1.getY()); // A = y_2 - y_1
            float B1 = (d1.getX() - d2.getX()); // B = x_1 - x_2
            float C1 = ((A1 * d1.getX()) + (B1 * d1.getY())); // C = A * x_1 + B * y_1

            float A2 = (theLine.d2.getY() - theLine.d1.getY());
            float B2 = (theLine.d1.getX() - theLine.d2.getX());
            float C2 = ((A2 * theLine.d1.getX()) + (B2 * theLine.d1.getY()));

            float det = (A1 * B2) - (A2 * B1);
            if (det == 0) {
                if(A1 == A2 || B1 == B2) {
                    return null;
                } else {
                    return new Point2D(-10,-10);
                }
            } else {
                float x = (B2 * C1 - B1 * C2) / det;
                float y = (A1 * C2 - A2 * C1) / det;
                Point2D point = new Point2D(x, y);

                if(checkRange(point, d1, d2) && checkRange(point, theLine.d1, theLine.d2)) {
                    return point;
                }else {
                    return null;
                }
            }
        } else {
            return null;
        }
    }

    public void setCrossing (boolean isCrossing) {
        this.isCrossing = isCrossing;
    }

    private boolean checkRange(Point2D value, Dot d1, Dot d2) {
        double xMin = d2.getX();
        double yMin = d2.getY();
        double xMax = d1.getX();
        if(d2.getX() > xMax) {
            xMax = d2.getX();
            xMin = d1.getX();
        }
        double yMax = d1.getY();
        if(d2.getY() > yMax) {
            yMax = d2.getY();
            yMin = d1.getY();
        }

        if(xMin + THRESHOLD > value.getX() || xMax - THRESHOLD < value.getX()) {
            return false;
        } else if (yMin + THRESHOLD > value.getY() || yMax - THRESHOLD < value.getY()) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result += ((d1 == null) ? 0 : d1.hashCode());
        result += ((d2 == null) ? 0 : d2.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Line))
            return false;
        Line other = (Line) obj;
        if (d1 == null) {
            if (other.d1 != null)
                return false;
        } else if (!d1.equals(other.d1) && !d1.equals(other.d2))
            return false;
        if (d2 == null) {
            if (other.d2 != null)
                return false;
        } else if (!d2.equals(other.d2) && !d2.equals(other.d1))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "[" + d1.toString() + ", " + d2.toString() + "]";
    }
}
