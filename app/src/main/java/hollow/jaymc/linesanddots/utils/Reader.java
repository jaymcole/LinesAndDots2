package hollow.jaymc.linesanddots.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import hollow.jaymc.linesanddots.R;
import hollow.jaymc.linesanddots.gameObjects.Level;

/**
 * Created by jaymc on 1/21/2016.
 */
public class Reader {

    private static final String TAG = Reader.class.getName();

    public static Level LoadLevel(InputStream is, int world, int level) {
        try {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                Log.d(TAG, line);
            }

        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
