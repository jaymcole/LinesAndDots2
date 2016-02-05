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

    /**
     * Deletes the save file.
     * @param context Context from any Activity.
     */
    public static void deleteSaves(Context context) {
        SharedPreferences saves = context.getSharedPreferences(context.getString(R.string.saves), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = saves.edit();
        editor.clear();
        editor.commit();
    }

    /**
     *
     * @param context Any Activity Context.
     * @param key Some String value :/
     * @param value Value to save.
     */
    public static void saveSharedPreference(Context context, String key, int value) {
        SharedPreferences settings = context.getSharedPreferences(context.getResources().getString(R.string.preferences), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * Saves a score to SharedPreferences.
     * TODO - Save turns / time / score
     *
     * @param context
     * @param tag Level tag.
     * @param score The Score ot save.
     */
    public static void saveScore(Context context, String tag, int score)
    {
        SharedPreferences scores = context.getSharedPreferences(context.getString(R.string.saves), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = scores.edit();
        editor.putInt(tag, score);
        editor.commit();
    }

    /**
     *
     * @param context Any Activity Context.
     * @param key String value
     * @param value String value
     */
    public static void saveSharedPreference(Context context, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(context.getString(R.string.preferences), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.commit();
    }
}
