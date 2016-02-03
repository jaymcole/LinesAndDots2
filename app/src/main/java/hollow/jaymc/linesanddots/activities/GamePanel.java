package hollow.jaymc.linesanddots.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

import hollow.jaymc.linesanddots.utils.Point2D;
import hollow.jaymc.linesanddots.gameObjects.Dot;
import hollow.jaymc.linesanddots.gameObjects.Level;
import hollow.jaymc.linesanddots.gameObjects.Line;
import hollow.jaymc.linesanddots.utils.Writer;

/**
 * Created by jaymc
 * 1/14/2016.
 */
public class GamePanel {
//public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
//
//    private static final String TAG = GamePanel.class.getName();
//
//    private List<Dot> dots;
//    private List<Line> lines;
//    private List<Point2D> intersections;
//
//    private Rect gameBounds;
//    private Level level;
//    private Activity parent;
//
//    private static int TOUCH_DISTANCE = 60;
//    private static final int BUFFER_FROM_LEFT = 100;
//    private static final int BUFFER_FROM_RIGHT = 100;
//    private static final int BUFFER_FROM_TOP = 100;
//    private static final int BUFFER_FROM_BOTTOM = 100;
//
//    public GamePanel(Activity parent, Context context, Level level) {
//        super(context);
//        this.level = level;
//        this.parent = parent;
//        getHolder().addCallback(this);
//        setFocusable(true);
//        createLevel(parent);
//    }
//
//    private void createLevel(Activity parent) {
//        // Create bounds -------------------
//        DisplayMetrics dm = new DisplayMetrics();
//        parent.getWindowManager().getDefaultDisplay().getMetrics(dm);
//        int width = dm.widthPixels;
//        int height = dm.heightPixels;
//
//        gameBounds = new Rect(
//                BUFFER_FROM_LEFT,
//                BUFFER_FROM_TOP,
//                width - BUFFER_FROM_RIGHT,
//                height - BUFFER_FROM_BOTTOM
//        );
//
//        scaleDots();
//    }
//
//    private void scaleDots() {
//        dots = level.getDots();
//        Dot minX = dots.get(0);
//        Dot minY = dots.get(0);
//        Dot maxX = dots.get(0);
//        Dot maxY = dots.get(0);
//
//        for (int i = 0; i < dots.size(); i++) {
//            Dot dot = dots.get(i);
//            if (dot.getX() > maxX.getX()) {
//                maxX = dot;
//            } else if (dot.getX() < minX.getX()) {
//                minX = dot;
//            }
//            if (dot.getY() > maxY.getY()) {
//                maxY = dot;
//            } else if (dot.getY() < minY.getY()) {
//                minY = dot;
//            }
//        }
//
//        int offsetX = Math.abs((int) (minX.getX() - BUFFER_FROM_LEFT));
//        int offsetY = Math.abs((int) (minY.getY() - BUFFER_FROM_TOP));
//        float scaleX = gameBounds.width() / maxX.getX();
//        float scaleY = gameBounds.height() / maxY.getY();
//
//        for (int i = 0; i < dots.size(); i++) {
//            dots.get(i).setScale(scaleX, scaleY);
//            dots.get(i).setPos(dots.get(i).getX() + offsetX, dots.get(i).getY() + offsetY);
//        }
//        lines = level.getLines();
//        intersections = new ArrayList<>();
//        checkLines();
//    }
//
//    @Override
//    public void surfaceCreated(SurfaceHolder holder) {
//        this.draw();
//    }
//
//    @Override
//    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//        this.draw();
//    }
//
//    @Override
//    public void surfaceDestroyed(SurfaceHolder holder) {
//        this.draw();
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            mousePressed(event.getX(), event.getY());
//        }
//        // GESTURES!
//        if (event.getAction() == MotionEvent.ACTION_MOVE) {
//            mouseDragged(event.getX(), event.getY());
//        }
//        if (event.getAction() == MotionEvent.ACTION_UP) {
//            mouseReleased();
//        }
//        return true;
//    }
//
//    private static Dot activeDot;
//    private static int index;
//
//    private void mousePressed(float x, float y) {
//        Log.d("GamePanel", "Screen pressed");
//        double lowest = TOUCH_DISTANCE + 1;
//        index = 0;
//        boolean suitable = false;
//        Point2D cursLoc = new Point2D(x, y);
//        Point2D dotLoc = null;
//        for (int i = 0; i < dots.size(); i++) {
//            dotLoc = new Point2D(dots.get(i).getX(), dots.get(i).getY());
//            double distance = cursLoc.distance(dotLoc);
//
//            if (distance <= TOUCH_DISTANCE) {
//                if (distance <= TOUCH_DISTANCE) {
//                    index = i;
//                    lowest = distance;
//                    suitable = true;
//                }
//            }
//        }
//        if (suitable) {
//            activeDot = dots.get(index);
//            activeDot.setActive(true);
//        }
//        draw();
//    }
//
//    private void mouseDragged(float x, float y) {
//        if (activeDot != null) {
//            activeDot.setPos(x, y);
//            checkLines();
//        }
//        draw();
//    }
//
//    private void mouseReleased() {
//        if (activeDot != null) {
//            activeDot.setActive(false);
//            checkLines();
//            activeDot = null;
//            if (intersections.size() <= 0 && activeDot == null) {
//                win();
//            }
//        }
//        draw();
//    }
//
//    private void checkLines() {
//        intersections = new ArrayList<>();
//        for (int i = 0; i < lines.size(); i++) {
//            lines.get(i).setCrossing(false);
//        }
//
//        for (int i = 0; i < lines.size() - 1; i++) {
//            for (int j = i + 1; j < lines.size(); j++) {
//                Point2D point = lines.get(i).getIntersection(lines.get(j));
//                if (point != null) {
//                    intersections.add(point);
//                    lines.get(i).setCrossing(true);
//                    lines.get(j).setCrossing(true);
//                }
//            }
//        }
//    }
//
//    private void win() {
//        Log.d(TAG, "Level complete!");
//        Writer.saveLevel(parent, level.getTag(), 3, 0);
//    }
//
//    private void draw() {
//        invalidate();
//    }
//
//    @Override
//    public void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        Paint paint;
//        paint = new Paint();
//        canvas.drawColor(Color.BLUE);
//        paint.setColor(Color.YELLOW);
//        Log.d("s", "Painting (ON DRAW)");
//        canvas.drawRect(new Rect(5, 5, 20, 35), paint);
//        render(canvas, paint);
//    }
//
//    @Override
//    public void draw(Canvas canvas) {
//        super.draw(canvas);
//        Paint paint;
//        paint = new Paint();
//        canvas.drawColor(Color.BLUE);
//        paint.setColor(Color.YELLOW);
//        Log.d("s", "Painting");
//        canvas.drawRect(new Rect(5, 5, 20, 35), paint);
//        render(canvas, paint);
//    }
//
//    private void render(Canvas canvas, Paint paint) {
//        paint.setColor(Color.MAGENTA);
//        for (int i = 0; i < lines.size(); i++) {
//            lines.get(i).render(canvas, paint);
//        }
//
//        if (intersections.size() < 5) {
//            Point2D[] points = new Point2D[intersections.size()];
//            points = intersections.toArray(points);
//            paint.setColor(Color.RED);
//            for (int i = 0; i < points.length; i++) {
//                canvas.drawCircle(points[i].getX(), points[i].getY(), 20, paint);
//            }
//        }
//
//        for (int i = 0; i < dots.size(); i++) {
//            dots.get(i).render(canvas, paint);
//        }
//    }
}
