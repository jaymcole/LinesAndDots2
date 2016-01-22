package hollow.jaymc.linesanddots.gameObjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jay Cole on 11/17/2015.
 */
public class Dot implements Cloneable{

    //	private List<Dot> dots;
    private List<Line> lines;
    private float x;
    private float xScale;
    private float y;
    private float yScale;
    private int offsetX;
    private int offsetY;
    private int r;
    private boolean active;

    private int lineC = Color.CYAN;
    private int bodyC = Color.BLUE;

    public Dot(float x, float y) {
        lines = new ArrayList<Line>();
        this.x = x;
        this.y = y;
        xScale = 1;
        yScale = 1;
        r = 30;
        active = false;
    }

    public void tick() {

    }

    public void render(Canvas canvas, Paint paint) {
        paint.setColor(lineC);
        if(active)
            paint.setColor(Color.GREEN);
        else
            paint.setColor(bodyC);
        canvas.drawCircle(getX(), getY(), r, paint);
        debug(canvas, paint);

    }

    private void debug(Canvas canvas, Paint paint) {
//        if(MainActivity.DEBUG) {
//            if(!active)
//                paint.setColor(Color.YELLOW);
//
//            Paint.FontMetrics fontMetrics = new Paint.FontMetrics();
//            Rect rect = new Rect();
//            paint.setTextSize(30);
////            paint.getTextBounds(toString(), 0, toString().length(), rect);
////            canvas.drawText(toString(), 0, toString().length(), (x  * xScale) - rect.width()/2, (y * yScale) - r * 3, paint);
//
//        }
    }


    public List<Line> getLines () {
        return lines;
    }

    public void addLine(Line theLine) {
        lines.add(theLine);
    }

    public void removeLine(Line theLine) {
        lines.remove(theLine);
    }

    public void setPos(float x, float y) {
        this.x = (x / xScale);
        this.y = (y / yScale);
    }

    public void setScale(float xScale, float yScale) {
        this.xScale = xScale;
        this.yScale = yScale;
    }

    public void setOffset(int offsetX, int offsetY) {
        x += offsetX;
        y += offsetY;
    }

    public float getX() {
//        return (x  * xScale) + offsetX;
        return (x  * xScale);
    }

    public float getY() {
//        return (y * yScale) + offsetY;
        return (y * yScale);
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int)x;
        result = prime * result + (int)y;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Dot))
            return false;
        Dot other = (Dot) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }
    @Override
    public Object clone() throws CloneNotSupportedException{
        return new Dot(x, y);
    }

}