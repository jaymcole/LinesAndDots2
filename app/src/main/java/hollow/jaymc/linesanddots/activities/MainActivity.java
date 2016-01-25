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

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void startGameActivity(View view, int worldID, int levelID) {
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
//        Reader.LoadLevel(this, 0, 0);
//        Log.d(TAG, "-----------------");
//        List<String> worlds = new ArrayList<>();
//        List<Integer> levels = new ArrayList<>();
//        List<World> worlds = new ArrayList<>();
//        Reader.getLevelCount(this, worlds);
//        Log.d(TAG, "Worlds: " + worlds.size() + ", Levels: " + worlds.get());
//        for(int i = 0; i < worlds.size(); i++ ) {
//            Log.d(TAG, "World: " + worlds.get(i) + ", " + levels.get(i+1));
//        }

    }
}
