package hollow.jaymc.linesanddots.utils;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import hollow.jaymc.linesanddots.R;
import hollow.jaymc.linesanddots.gameObjects.Level;

/**
 * Created by jaymc on 1/21/2016.
 */
public class Reader {

    private static final String TAG = Reader.class.getName();
    private static final int LEVEL_DATA_ID = R.raw.levels;

    private static BufferedReader getReader(Context context) {
        InputStream is = context.getResources().openRawResource(LEVEL_DATA_ID);
        InputStreamReader isr = new InputStreamReader(is);
        return new BufferedReader(isr);
    }

    /**
     * Returns Level object of levelID of worldID.
     * Reads from file LEVEL_DATA_ID.
     * @param context
     * @param worldID
     * @param levelID
     * @return Level
     */
    public static Level LoadLevel(Context context, int worldID, int levelID) {
//        TODO - Make this better...
        try {
            BufferedReader br = getReader(context);
            String line;

            int currentWorld = 0;
            while ((line = br.readLine()) != null || currentWorld != worldID) {
                if(line.startsWith("#")) {
                    currentWorld++;
                }
            }

            for(int i = 0; i < levelID; i++) {
                if((line = br.readLine()) == null ) {
                    break;
                }
            }

            if(line != null) {
                return processLine(line);
            } else
                return null;
//          TODO - Return default level instead of null.

        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Level processLine(String line) {
        Level level = new Level();
        String[] attributes = line.split(";");
        level.setTurns(Integer.parseInt(attributes[0]));
        level.setScore(Integer.parseInt(attributes[1]));
        level.setDots(Utils.getDots(Utils.processString(attributes[2])));
        level.setLines(Utils.getLines(level.getDots(), Utils.processString(attributes[3])));
        return level;
    }

    public static void getLevelContext(Context context, List<String> worlds, List<Integer> levels) {
        try {
            BufferedReader br = getReader(context);
            String line;
            int counter = -1;

            while ((line = br.readLine()) != null) {
                line = line.trim();

                if(line.startsWith("#")){
                    worlds.add(line.substring(1, line.length()));
                    levels.add(counter);
                    counter = 0;
                }else if (line.length() > 5){
                    counter++;
                }
            }
            levels.add(counter);


        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
