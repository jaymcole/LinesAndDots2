package hollow.jaymc.linesanddots.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import hollow.jaymc.linesanddots.R;
import hollow.jaymc.linesanddots.gameObjects.World;
import hollow.jaymc.linesanddots.utils.Reader;
import hollow.jaymc.linesanddots.utils.Writer;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void startGameActivity(View view) {
        Log.d(TAG, "Starting game activity");
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void startLevelActivity(View view) {
        Log.d(TAG, "Starting game activity");
        Intent intent = new Intent(this, LevelActivity.class);
        startActivity(intent);
    }

    public void test(View view) {
        Log.d(TAG, "Save Test.");
        List<String> scores = Reader.getScores(this);
        for(int i = 0; i < scores.size(); i++) {
            Log.d(TAG, scores.get(i));
        }
    }

    public void delete(View view) {
        Log.d(TAG, "Deleting saves.");
        Writer.deleteSaves(this);
    }
}
