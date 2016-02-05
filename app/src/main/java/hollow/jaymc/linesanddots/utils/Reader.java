package hollow.jaymc.linesanddots.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import hollow.jaymc.linesanddots.R;
import hollow.jaymc.linesanddots.gameObjects.Level;
import hollow.jaymc.linesanddots.gameObjects.World;

/**
 * Created by jaymc
 * 1/21/2016.
 */
public class Reader {
    public static final int TAG_POS = 0;
    public static final int DOTS_POS = 1;
    public static final int LINES_POS = 2;

    private static final String TAG = Reader.class.getName();
    private static final int LEVEL_DATA_ID = R.raw.levels;

    /**
     * Returns Level object of levelID of worldID.
     * Reads from file LEVEL_DATA_ID.
     *
     * @param context Activity context.
     * @param tag     String denoting which level to load.
     * @return A level object constructed from
     */
    public static Level getLevel(Context context, String tag) {
        InputStream is = context.getResources().openRawResource(LEVEL_DATA_ID);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        try {
            String line = "";
            while ((line = br.readLine()) != null) {
                if (line.startsWith(tag))
                    return processLine(context, line.trim());
            }
            Log.e(TAG, "[ERROR] Failed to load level. Loading default instead.");
            Log.e(TAG, "[ERROR] Attempted to load from world: " + tag);
            return Utils.getTestLevel();
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeStreams(br, is, isr);
        }
        return Utils.getTestLevel();
    }

    /**
     * Loads all playable levels into their corresponding worlds.
     * @param context Context from parent activity.
     * @param worlds List of Worlds containing all playable levels.
     */
    public static void getLevelInformation(Context context, List<World> worlds) {
        InputStream is = context.getResources().openRawResource(LEVEL_DATA_ID);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        try {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#"))
                    lineWorld(line, worlds);
                else if (line.startsWith("$"))
                    lineLevel(context, line, worlds);
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeStreams(br, is, isr);
        }
    }

    /**
     * Process a line consisting of world information.
     * @param line String to process.
     * @param worlds List of Worlds in which to create a new world.
     */
    private static void lineWorld(String line, List<World> worlds) {
        worlds.add(new World(line.substring(1)));
    }

    /**
     * Process a line consisting of level information.
     * @param context Context from parent activity.
     * @param line Line to process.
     * @param worlds World to save level to.
     */
    private static void lineLevel(Context context, String line, List<World> worlds) {
        World world = worlds.get(worlds.size() - 1);
        world.increaseLevelCount(1);
        world.addScore(getScore(context, line.split(";")[0]));
    }

    /**
     * @param context Activity Context
     * @param line    The string to process.
     * @return A load level.
     */
    private static Level processLine(Context context, String line) {
        Level level = new Level();
        String[] attributes = line.split(";");
        level.setTag(attributes[TAG_POS]);
//        level.setTurns(Integer.parseInt(attributes[TURNS_POS]));
        level.setScore(getScore(context, level.getTag()));
        level.setDots(Utils.getDots(Utils.processString(attributes[DOTS_POS])));
        level.setLines(Utils.getLines(level.getDots(), Utils.processString(attributes[LINES_POS])));
        return level;
    }

    /**
     *
     * @param context Any activity context.
     * @return  Returns a Map<String, Integer> containing all saved scores.
     */
    public static Map<String, Integer> getScores(Context context) {
        SharedPreferences saves = context.getSharedPreferences(context.getString(R.string.saves), Context.MODE_PRIVATE);
        return (Map<String, Integer>)saves.getAll();
    }

    /**
     *
     * @param context Any activity context.
     * @param tag Level tag of score to load
     * @return Returns an integer score associated with tag. Returns 0 if one has not yet been set.
     */
    public static int getScore(Context context, String tag) {
        SharedPreferences scores = context.getSharedPreferences(context.getString(R.string.saves), Context.MODE_PRIVATE);
        return scores.getInt(tag, 0);
    }

    /**
     * @param context Context from the activity calling this method.
     * @return Returns the last level played.
     */
    public static Level getLastLevel(Context context) {
        SharedPreferences settings = context.getSharedPreferences(context.getResources().getString(R.string.preferences), 0);
        return getLevel(context, settings.getString(context.getResources().getString(R.string.lastLevelPlayed), Utils.createTag(0, 0)));
    }

    /**
     *
     * @param context Context from parent activity.
     * @param tag The level tag of the current level.
     * @return Returns the level following tag.
     */
    public static Level getNextLevel(Context context, String tag) {
        int score = getScore(context, tag);
        if (score > 0) {
            InputStream is = context.getResources().openRawResource(LEVEL_DATA_ID);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            try {
                String line = "";
                while ((line = br.readLine()) != null) {
                    if (line.startsWith(tag)) {
                        if ((line = br.readLine()) != null && line.startsWith("$"))
                            return processLine(context, line);
                        else
                            return null;
                    }
                }
                return null;
            } catch (FileNotFoundException e) {
                Log.e("login activity", "File not found: " + e.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                closeStreams(br, is, isr);
            }
        } else {
            return getLevel(context, tag);
        }
        return null;
    }

    /**
     * @param br  The BufferedReader to be closed.
     * @param is  The InputStream to be closed.
     * @param isr The InputStreamReader to be closed.
     */
    private static void closeStreams(BufferedReader br, InputStream is, InputStreamReader isr) {
        try {
            if (is != null)
                is.close();
            if (isr != null)
                isr.close();
            if (br != null)
                br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param br The BufferedReader to be closed.
     * @param fr The FileReader to be closed.
     */
    private static void closeStreams(BufferedReader br, FileReader fr) {
        try {
            if (fr != null)
                fr.close();
            if (br != null)
                br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}