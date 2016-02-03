package hollow.jaymc.linesanddots.utils;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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

//    private static InputStream is;
//    private static InputStreamReader isr;
//    private static FileInputStream fis;
//    private static FileReader fr;

//    /**
//     * Creates and returns a BufferedReader
//     * @param context The context from the activity that called this method.
//     * @return BufferedReader  The BufferedReader ready to read from the level data file.
//     */
//    private static BufferedReader getReader(InputStream is, InputStreamReader isr, Context context) {
//        is = context.getResources().openRawResource(LEVEL_DATA_ID);
//        isr = new InputStreamReader(is);
//        return new BufferedReader(isr);
//    }
//
//    /**
//     *
//     * @param file
//     * @return A new BufferedReader of the specified file.
//     * @throws FileNotFoundException
//     */
//    private static BufferedReader getReader(FileReader fr, File file) {
//        try {
//            fr = new FileReader(file);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            Log.e(TAG, "Failed to find file. (getReader() method)");
//        }
//        return new BufferedReader(fr);
//    }



    /**
     * Returns Level object of levelID of worldID.
     * Reads from file LEVEL_DATA_ID.
     *
     * @param context Activity context.
     * @param tag     String denoting which level to load.
     * @return A level object constructed from
     */
    public static Level loadLevel(Context context, String tag) {
        InputStream is = context.getResources().openRawResource(LEVEL_DATA_ID);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        try {
            if(is == null)
                Log.e(TAG, "(loadLevel) InputStream is still null!");
            if(isr == null)
                Log.e(TAG, "(loadLevel) InputStreamReader is still null!");

            String line = "";
            Log.d(TAG, "Looking for \"" + tag + "\"");
            while ((line = br.readLine()) != null && !line.trim().startsWith(tag)) {

            }


            if (line != null) {
                return processLine(context, line.trim().substring(1));
            } else {
                Log.e(TAG, "[ERROR] Failed to load level. Loading default instead.");
                Log.e(TAG, "[ERROR] Attempted to load from world: " + tag);
                return Utils.getTestLevel();
            }

        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeStreams(br, is, isr);
        }
        Log.e(TAG, "[ERROR] Failed to load level. Loading default instead.");
        Log.e(TAG, "[ERROR] Attempted to load from world: " + tag);
        return Utils.getTestLevel();
    }

    public static void getLevelInformation(Context context, List<World> worlds) {
        InputStream is = context.getResources().openRawResource(LEVEL_DATA_ID);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        try {
            if(is == null)
                Log.e(TAG, "(getLevelInformation) InputStream is still null!");
            if(isr == null)
                Log.e(TAG, "(getLevelInformation) InputStreamReader is still null!");

            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                int levelCounter = 0;
                int worldCounter = 0;
                List<Integer> scores = new ArrayList<>();
                World world = new World();
                if (line.startsWith("#")) {
                    world = new World(line.substring(1, line.length()), worldCounter);
                    scores = new ArrayList<>();
                    worldCounter++;
                }

                while (line != null && !line.startsWith("#")) {
                    Log.d(TAG, "Line: " + line);
                    if (line.startsWith("$")) {
                        Log.d(TAG, "Approved");
                        levelCounter++;
                        scores.add(getScore(context, line.split(";")[0]));

                    }
                    line = br.readLine();
                }

                world.setNumberOfLevels(levelCounter);
                world.setScores(scores);
                worlds.add(world);

            }
            worlds.remove(0);

        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeStreams(br, is, isr);
        }
    }

    /**
     *
     * @param context Activity Context
     * @param line The string to process.
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

    public static List<String> getScores (Context context) {
        List<String> scores = new ArrayList<>();
        File file = Utils.getSaveFile(context);
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG, "Failed to find file. (getReader() method)");
        }
        try {
            String line;
            while ((line = br.readLine()) != null) {
                if(line.length() > 0);
                    scores.add(line.trim());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeStreams(br, fr);
        }
        return scores;
    }

    public static int getScore(Context context, String tag) {
        int score = 0;
        File saves = Utils.getSaveFile(context);
        if (saves.exists()) {
            FileReader fr = null;
            BufferedReader br = null;
            try {
                fr = new FileReader(saves);
                br = new BufferedReader(fr);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Log.e(TAG, "Failed to find file. (getReader() method)");
            }
//            FileReader fr = null;
//            BufferedReader br = getReader(fr, saves);
            try {

                String line;
                while ((line = br.readLine()) != null) {
                    if (line.startsWith(tag)) {
                        score = Integer.parseInt(line.split(";")[1]);
                        break;
                    }
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                closeStreams(br, fr);
            }
        }
        return score;
    }

    private static void closeStreams(BufferedReader br, InputStream is, InputStreamReader isr) {
        try {
            if (is != null)
                is.close();
            if (isr != null)
                isr.close();
            if(br != null)
                br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void closeStreams(BufferedReader br, FileReader fr) {
        try {
            if (fr != null)
                fr.close();
            if(br != null)
                br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}