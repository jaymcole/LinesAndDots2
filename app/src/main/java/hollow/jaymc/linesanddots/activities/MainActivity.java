package hollow.jaymc.linesanddots.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

import hollow.jaymc.linesanddots.R;
import hollow.jaymc.linesanddots.utils.Reader;

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
        Reader.LoadLevel(getResources().openRawResource(R.raw.levels), 0, 0);
    }
}
