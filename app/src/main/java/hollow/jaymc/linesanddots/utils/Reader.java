package hollow.jaymc.linesanddots.utils;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
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
    public static final int SCORE_POS = 1;
    public static final int TURNS_POS = 2;
    public static final int DOTS_POS = 3;
    public static final int LINES_POS = 4;


    private static final String TAG = Reader.class.getName();
    private static final int LEVEL_DATA_ID = R.raw.levels;

    private static InputStream is;
    private static InputStreamReader isr;

    /**
     * Creates and returns a BufferedReader
     * @param context   The context from the activity that called this method.
     * @return  BufferedReader  The BufferedReader ready to read from the level data file.
     */
    private static BufferedReader getReader(Context context) {
        is = context.getResources().openRawResource(LEVEL_DATA_ID);
        isr = new InputStreamReader(is);
        return new BufferedReader(isr);
    }

    public static List<String> loadSaves(InputStream inputStream){
        List<String> saves = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        try {
            while((line = br.readLine()) != null) {
                Log.d(TAG, line);
                saves.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return saves;
    }

    /**
     * Returns Level object of levelID of worldID.
     * Reads from file LEVEL_DATA_ID.
     * @param context
     * @param worldID   An Integeer denoting which world to load.
     * @param levelID   An Integer denoting which level to load.
     * @return          A level object constructed from
     */
    public static Level LoadLevel(Context context, int worldID, int levelID) {
        try {
            BufferedReader br = getReader(context);
            String line;
            String levelTag = "W" + worldID + "L" + levelID;
            Log.d(TAG, "Looking for \"" + "$" + levelTag + "\"");
            while ((line = br.readLine()) != null && !line.trim().startsWith(levelTag)) {

            }
            br.close();
            closeStreams();

            if(line != null) {
                return processLine(line.trim().substring(1));
            } else {
                Log.e(TAG, "[ERROR] Failed to load level. Loading default instead.");
                Log.e(TAG, "[ERROR] Attempted to load from world: " + worldID + ", Level: " + levelID);
                return Utils.getTestLevel();
            }

        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "[ERROR] Failed to load level. Loading default instead.");
        Log.e(TAG, "[ERROR] Attempted to load from world: " + worldID + ", Level: " + levelID);
        return Utils.getTestLevel();
    }

    private static Level processLine(String line) {
        Level level = new Level();
        String[] attributes = line.split(";");
        level.setTag(attributes[TAG_POS]);
        level.setTurns(Integer.parseInt(attributes[TURNS_POS]));
        level.setScore(Integer.parseInt(attributes[SCORE_POS]));
        level.setDots(Utils.getDots(Utils.processString(attributes[DOTS_POS])));
        level.setLines(Utils.getLines(level.getDots(), Utils.processString(attributes[LINES_POS])));
        return level;
    }

    public static void getLevelCount(Context context, List<World> worlds) {
        try {
            BufferedReader br = getReader(context);
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                int levelCounter = 0;
                int worldCounter = 0;
                List<Integer> scores = new ArrayList<>();
                World world = new World();
//                boolean newWorld = false;

                if(line.startsWith("#")) {
//                    newWorld = true;
                    world = new World(line.substring(1, line.length()), worldCounter);
                    worldCounter++;
                }

                while (line != null && !line.startsWith("#")) {
                    if(line.startsWith("$")) {
                        levelCounter++;
                        scores.add(Integer.parseInt(line.split(";")[1]));

                    }
                    line = br.readLine();
                }

//                if ( !newWorld ) {
                    world.setNumberOfLevels(levelCounter);
                    world.setScores(scores);
                    worlds.add(world);
//                }





//                if(line.startsWith("#")){
//                    world = new World(line.substring(1, line.length()), worldCounter);
//
//                    levels.add(levelCounter);
//                    worldCounter++;
//                    levelCounter = 0;
//                }else if (line.startsWith("$")){
//                    scores.add(Integer.parseInt(line.split(";")[1]));
//                    levelCounter++;
//                }
            }
//            levels.add(levelCounter);
//            levels.remove(0);
            worlds.remove(0);
            br.close();
            closeStreams();
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void closeStreams() {
        try {
            if(is != null)
                is.close();
            if(isr != null)
                isr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

/*

public static Level LoadLevel(Context context, int worldID, int levelID) {
//        TODO - Make this better...
        try {
            BufferedReader br = getReader(context);
            String line;
            Log.d(TAG, "w:" + worldID + ", l:" + levelID);

            int currentWorld = 0;
            while ((line = br.readLine()) != null && currentWorld != worldID) {
                Log.d(TAG, line);
                if(line.trim().startsWith("#")) {
                    currentWorld++;
                }
            }

            int currentLevel = 0;
            while ((line = br.readLine()) != null && currentLevel != levelID) {
                Log.d(TAG, line);
                if(line.trim().startsWith("$")) {
                    currentLevel++;
                }
                if(line.startsWith("#")) {
                    line = null;
                    Log.e(TAG, "[ERROR] Failed to load level.\nLevel Not Found");
                }
            }

//            for(int i = 0; i < levelID; i++) {
//                while(line.length() < 4 && line != null) {
//                    line = br.readLine();
//                }
//                if((line = br.readLine()) == null ) {
//                    break;
//                }
//            }

            br.close();
            closeStreams();
            if(line != null) {
                return processLine(line.trim().substring(1));
            } else {
                Log.e(TAG, "[ERROR] Failed to load level. Loading default instead.");
                Log.e(TAG, "[ERROR] Attempted to load from world: " + worldID + ", Level: " + levelID);
                return Utils.getTestLevel();

            }

        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "[ERROR] Failed to load level. Loading default instead.");
        Log.e(TAG, "[ERROR] Attempted to load from world: " + worldID + ", Level: " + levelID);
        return Utils.getTestLevel();
    }


public static void getLevelCount(Context context, List<String> worlds, List<Integer> levels) {
        try {
            BufferedReader br = getReader(context);
            String line;
            int counter = 0;
            while ((line = br.readLine()) != null) {
                line = line.trim();

                if(line.startsWith("#")){
                    worlds.add(line.substring(1, line.length()));
                    levels.add(counter);
                    counter = 0;
                }else if (line.startsWith("$")){
                    counter++;
                }
            }
            levels.add(counter);
            levels.remove(0);
            br.close();
            closeStreams();
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


 */