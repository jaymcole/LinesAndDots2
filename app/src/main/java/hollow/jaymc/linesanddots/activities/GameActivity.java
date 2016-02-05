package hollow.jaymc.linesanddots.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import hollow.jaymc.linesanddots.R;
import hollow.jaymc.linesanddots.gameObjects.Dot;
import hollow.jaymc.linesanddots.gameObjects.Level;
import hollow.jaymc.linesanddots.gameObjects.Line;
import hollow.jaymc.linesanddots.utils.Point2D;
import hollow.jaymc.linesanddots.utils.Reader;
import hollow.jaymc.linesanddots.utils.Writer;

public class GameActivity extends Activity {

    private static final String TAG = GameActivity.class.getName();
    private MyView gamePanel;

    public static final String LEVEL_TAG = "TAG";


    public static final int BACKGROUND = Color.BLACK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        level = Reader.getLastLevel(this);
        createLevel();
        gamePanel = new MyView(this);
        setContentView(gamePanel);

    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "Destroying...");
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "Stopping...");
        super.onStop();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "Pausing...");
        super.onPause();
        finish();
    }

    private Rect gameBounds;
    private Level level;

    private void createLevel() {
        // Create bounds -------------------
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        gameBounds = new Rect(
                BUFFER_FROM_LEFT,
                BUFFER_FROM_TOP,
                width - BUFFER_FROM_RIGHT,
                height - BUFFER_FROM_BOTTOM
        );

        scaleDots();
    }

    private void scaleDots() {
        dots = level.getDots();
        Dot minX = dots.get(0);
        Dot minY = dots.get(0);
        Dot maxX = dots.get(0);
        Dot maxY = dots.get(0);

        for (int i = 0; i < dots.size(); i++) {
            Dot dot = dots.get(i);
            if (dot.getX() > maxX.getX()) {
                maxX = dot;
            } else if (dot.getX() < minX.getX()) {
                minX = dot;
            }
            if (dot.getY() > maxY.getY()) {
                maxY = dot;
            } else if (dot.getY() < minY.getY()) {
                minY = dot;
            }
        }

        int offsetX = Math.abs((int) (minX.getX() - BUFFER_FROM_LEFT));
        int offsetY = Math.abs((int) (minY.getY() - BUFFER_FROM_TOP));
        float scaleX = gameBounds.width() / maxX.getX();
        float scaleY = gameBounds.height() / maxY.getY();

        for (int i = 0; i < dots.size(); i++) {
            dots.get(i).setScale(scaleX, scaleY);
            dots.get(i).setPos(dots.get(i).getX() + offsetX, dots.get(i).getY() + offsetY);
        }
        lines = level.getLines();
        intersections = new ArrayList<>();
        checkLines();
    }

    private void checkLines() {
        intersections = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            lines.get(i).setCrossing(false);
        }

        for (int i = 0; i < lines.size() - 1; i++) {
            for (int j = i + 1; j < lines.size(); j++) {
                Point2D point = lines.get(i).getIntersection(lines.get(j));
                if (point != null) {
                    intersections.add(point);
                    lines.get(i).setCrossing(true);
                    lines.get(j).setCrossing(true);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX(), y = event.getY() + Y_OFFSET;
        point = new Point2D(x, y);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mousePressed(x, y);
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            mouseDragged(x, y);
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            mouseReleased();
        }

        gamePanel.invalidate();
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startLevelSelect();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void startLevelSelect() {
        Log.d(TAG, "Starting LevelActivity");
        Intent intent = new Intent(this, LevelActivity.class);
        startActivity(intent);
    }

    private static int Y_OFFSET = -85;
    private static int TOUCH_DISTANCE = 120;
    private static final int BUFFER_FROM_LEFT = 100;
    private static final int BUFFER_FROM_RIGHT = 100;
    private static final int BUFFER_FROM_TOP = 100;
    private static final int CONTROL_BAR_HEIGHT = 85;
    private static final int BUFFER_FROM_BOTTOM = 100 + CONTROL_BAR_HEIGHT;


    private List<Dot> dots;
    private List<Line> lines;
    private List<Point2D> intersections;
    private Point2D point;

    private static Dot activeDot;
    private static int index;

    private void mousePressed(float x, float y) {

        double lowest = TOUCH_DISTANCE + 1;
        index = 0;
        boolean suitable = false;
        Point2D cursLoc = new Point2D(x, y);
        Point2D dotLoc = null;
        for (int i = 0; i < dots.size(); i++) {
            dotLoc = new Point2D(dots.get(i).getX(), dots.get(i).getY());
            double distance = cursLoc.distance(dotLoc);

            if (distance <= TOUCH_DISTANCE) {
                if (distance <= TOUCH_DISTANCE) {
                    index = i;
                    lowest = distance;
                    suitable = true;
                }
            }
        }
        if (suitable) {
            activeDot = dots.get(index);
            activeDot.setActive(true);
        }
    }

    private void mouseDragged(float x, float y) {
        if (activeDot != null) {
            activeDot.setPos(x, y);
            checkLines();
        }
    }

    private void mouseReleased() {
        if (activeDot != null) {
            activeDot.setActive(false);
            checkLines();
            activeDot = null;
            if (intersections.size() <= 0 && activeDot == null) {
                win();
            }
        }
    }

    private void win() {
        Log.d(TAG, "Level complete!");
//        Writer.saveLevel(this, level.getTag(), 3, 0);
        Writer.saveScore(this, level.getTag(), 3);
        level = Reader.getNextLevel(this, level.getTag());
        if (level != null) {
            createLevel();
            Writer.saveSharedPreference(this, getString(R.string.lastLevelPlayed), level.getTag());
        } else
            startLevelSelect();

    }

    //----------------------------------------------------------------------------------------------

    public class MyView extends View {
        public MyView(Context context) {
            super(context);
            // TODO Auto-generated constructor stub
        }

        @Override
        protected void onDraw(Canvas canvas) {
            // TODO Auto-generated method stub
            super.onDraw(canvas);
            int x = getWidth();
            int y = getHeight();
            int radius;
            radius = 100;
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(BACKGROUND);
            canvas.drawPaint(paint);

            paint.setColor(Color.RED);
            paint.setTextSize(25f);
            canvas.drawText("Lines: " + lines.size(), 20, 20, paint);
            canvas.drawText("Dots: " + dots.size(), 20, 40, paint);
            canvas.drawText("Intersections: " + intersections.size(), 20, 60, paint);
            if (level != null)
                canvas.drawText("Current Level: " + level.getTag(), 20, 80, paint);
            for (int i = 0; i < lines.size(); i++) {
                lines.get(i).render(canvas, paint);
            }

            for (int i = 0; i < dots.size(); i++) {
                dots.get(i).render(canvas, paint);
            }

            paint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(gameBounds, paint);


            paint.setColor(Color.RED);
            if (point != null) {
                canvas.drawLine(0, point.getY(), point.getX(), point.getY(), paint);
                canvas.drawLine(point.getX(), point.getY(), getWidth(), point.getY(), paint);
                canvas.drawLine(point.getX(), 0, point.getX(), point.getY(), paint);
                canvas.drawLine(point.getX(), point.getY(), point.getX(), getHeight(), paint);
                canvas.drawCircle(point.getX(), point.getY(), 25, paint);
            }
        }
    }
}