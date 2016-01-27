package hollow.jaymc.linesanddots.utils;

import android.content.Context;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import hollow.jaymc.linesanddots.gameObjects.Level;

/**
 * Created by jaymc
 * 1/21/2016.
 */
public class Writer {

    private static final String TAG = Writer.class.getName();

    public static void saveLevel(Context context, String tag, int score, int turns) {
        File file = context.getFilesDir();
        File save = new File(file.getPath() + "/saves.txt");

        if (!save.exists()) {
            try {
                save.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        List<String> saves = new ArrayList<>();
        if (save.exists()) {
            try {
                saves = Reader.loadSaves(new FileInputStream(save));
            } catch (FileNotFoundException e) {
                Log.e(TAG, "[ERROR] save.exists()");
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "[ERROR] Can't find save file.");
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(save));
            int lines = saves.size();
            for(int i = 0; i < lines; i++) {
                String line = saves.get(i);
                if (line.startsWith(tag)) {
                    writer.write(compareScores(line, tag, score, turns));
                    break;
                } else {
                    writer.write(lines);
                }
            }
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            Log.e(TAG, "[ERROR] FileNotFoundException");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, "[ERROR] IOException");
            e.printStackTrace();
        }
    }

    private static String compareScores(String line, String tag, int score, int turns){
        String[] attributes = line.split(";");
        if (score > Integer.parseInt(attributes[2])) {
            return tag + ";" + score + ";" + turns;
        } else {
            return line;
        }
    }
}
