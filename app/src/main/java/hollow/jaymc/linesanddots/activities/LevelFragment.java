package hollow.jaymc.linesanddots.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;

import hollow.jaymc.linesanddots.R;
import hollow.jaymc.linesanddots.gameObjects.World;
import hollow.jaymc.linesanddots.utils.Utils;

/**
 * Created by jaymc on
 * 12/12/2015.
 */
public class LevelFragment extends Fragment {
    private static final String TAG = LevelFragment.class.getName();
    private String title;
    public static final String NUMBER_OF_LEVELS = "NUMBER_OF_LEVELS";
    public static final String WORLD_ID = "WORLD_ID";
    public static final String TITLE = "TITLE";
    public static final String SCORES = "SCORES";

    public static final int NUM_COL = 5;

    private ScrollView scrollView;

    public LevelFragment() {
    }

    public static final LevelFragment newInstance(World world) {
        LevelFragment f = new LevelFragment();
        Bundle bdl = new Bundle(1);
        bdl.putString(TITLE, world.getWorldName());
        bdl.putInt(NUMBER_OF_LEVELS, world.getNumberOfLevels());
        bdl.putInt(WORLD_ID, world.getWorldID());
        bdl.putIntArray(SCORES, world.getScores());
        f.setArguments(bdl);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String message = getArguments().getString(TITLE);
        int[] scores = getArguments().getIntArray(SCORES);
        int totalLevels = getArguments().getInt(NUMBER_OF_LEVELS);

        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.fragment_level_2, container, false);

        scrollView = new ScrollView(v.getContext());
        final TableLayout tableLayout = new TableLayout(v.getContext());
        tableLayout.setShrinkAllColumns(true);
        tableLayout.setPadding(50, 0, 50, 0);
        tableLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                tableLayout.setRotation(tableLayout.getRotation() + 15);
                Log.d(TAG, "Long Click");
                return false;
            }
        });

        TableLayout.LayoutParams params = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.MATCH_PARENT,
                1.0f);

        int num_row = totalLevels / NUM_COL;
        Log.e(TAG, "Rows: " + num_row);
        int counter = 0;
        for (int row = 0; row <= num_row; row++) {
            TableRow tableRow = new TableRow(v.getContext());
            tableRow.setLayoutParams(params);
            tableRow.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    tableLayout.setRotation(tableLayout.getRotation() + 15);
                    Log.d(TAG, "Long Click ( via row )");
                    return false;
                }
            });
            for (int col = 0; col < NUM_COL; col++) {
                if (row * NUM_COL + col < totalLevels) {
                    Button button = new Button(v.getContext());


                    final int worldID = getArguments().getInt(WORLD_ID);

                    final int levelId = row * NUM_COL + col;
//                    button.setText("" + row + ", " + col + " (" + scores[levelId] + ")");
                    button.setText("" + row + ", " + col + " (" + scores[counter] + ") " + counter);

                    Log.d(TAG, "--------------------");
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String levelTag = Utils.createTag(worldID, levelId);
                            startGame(v.getContext(), levelTag);
                        }
                    });
                    tableRow.addView(button);
                }
                counter++;
            }
            tableLayout.addView(tableRow);
            counter++;
        }
        for(int i : scores) {
            Log.d(TAG, "Scores for world" + getArguments().getInt(WORLD_ID) + ": " + i);
        }
        scrollView.addView(tableLayout);
        v.addView(scrollView);
        return v;
    }

    private void startGame(Context context, String levelTag) {
        Log.d(TAG, "Starting game activity");
        Intent intent = new Intent(context, GameActivity.class);
        Bundle extras = new Bundle();
        extras.putString(GameActivity.LEVE_TAG, levelTag);
        intent.putExtras(extras);
        startActivity(intent);
    }
}