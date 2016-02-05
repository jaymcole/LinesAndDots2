package hollow.jaymc.linesanddots.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hollow.jaymc.linesanddots.R;

/**
 * Created by jaymc
 * 1/21/2016.
 */
public class Writer {

    private static final String TAG = Writer.class.getName();

    public static void saveLevel(Context context, String tag, int score, int turns) {
        File save = Utils.getSaveFile(context);
        Log.d(TAG, "Tag: " + tag);
        String saveEntry = tag + ";" + score + ";" + turns;
        if (!save.exists()) {
            Log.d(TAG, "File does not exist. Attempting to create file.");
            try {
                save.createNewFile();
                if (save.exists())
                    Log.d(TAG, "File now exists!");
                else
                    Log.d(TAG, "File STILL does not exist.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.d(TAG, "File already exists.");
        }

        List<String> saves = new ArrayList<>();
        int saveIndex = -1;
        if (save.exists()) {

            saves = Reader.getScores(context);
            for (int i = 0; i < saves.size(); i++) {
                if (saves.get(i).startsWith(tag)) {
                    saveIndex = i;
                    break;
                }
            }

        } else {
            Log.e(TAG, "[ERROR] Can't find save file.");
        }

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(save));
            if (saveIndex == -1) {
                saves.add(saveEntry);
            } else if (Utils.compareScore(saveEntry, saves.get(saveIndex))) {
                saves.set(saveIndex, saveEntry);
            }
            for (int i = 0; i < saves.size(); i++) {
                writer.write(saves.get(i) + "\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Deletes the save file.
     * @param context Context from activity calling this method.
     */
    public static void deleteSaves(Context context) {
        File file =  Utils.getSaveFile(context);
        if(file.exists())
            file.delete();
    }

    public static void saveSharedPreference(Context context, String key, int value) {
        SharedPreferences settings = context.getSharedPreferences(context.getResources().getString(R.string.preferences), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void saveScore(Context context, String tag, int score)
    {
        SharedPreferences scores = context.getSharedPreferences(context.getString(R.string.saves), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = scores.edit();
        editor.putInt(tag, score);
        editor.commit();
    }

    public static void saveSharedPreference(Context context, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(context.getString(R.string.preferences), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.commit();
    }
}
