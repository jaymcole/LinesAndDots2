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
    public LevelFragment() {}

    public static final LevelFragment newInstance(World world)
    {
        LevelFragment f = new LevelFragment();
        Bundle bdl = new Bundle(1);
        bdl.putString(TITLE, world.getWorldName());
        bdl.putInt(NUMBER_OF_LEVELS, world.getNumberOfLevels());
        bdl.putInt(WORLD_ID, world.getWorldID());
        bdl.putIntArray(SCORES, world.getScores());
        f.setArguments(bdl);
        return f;
    }

    public static final LevelFragment newInstance(String worldName, int worldIndex, int levels)
    {
        LevelFragment f = new LevelFragment();
        Bundle bdl = new Bundle(1);
        bdl.putString(TITLE, worldName);
        bdl.putInt(NUMBER_OF_LEVELS, levels);
        bdl.putInt(WORLD_ID, worldIndex);
        f.setArguments(bdl);
        return f;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String message = getArguments().getString(TITLE);
        int totalLevels = getArguments().getInt(NUMBER_OF_LEVELS);

        ViewGroup v = (ViewGroup)inflater.inflate(R.layout.fragment_level_2, container, false);

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

        int num_row = totalLevels / NUM_COL ;
        Log.e(TAG, "Rows: " + num_row);
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
                    if(row * NUM_COL + col < totalLevels) {
                        Button button = new Button(v.getContext());
                        button.setText("" + row + ", " + col + "");

                        final int worldID = getArguments().getInt(WORLD_ID);
                        final int levelId = row * NUM_COL + col;

                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final int w = worldID;
                                final int l = levelId;
                                startGame(v.getContext(), w, l);
                            }
                        });
                        tableRow.addView(button);
                    }
                }
                tableLayout.addView(tableRow);
            }

            scrollView.addView(tableLayout);
            v.addView(scrollView);

        return v;
    }

    private void startGame(Context context, int worldID, int levelID) {
        Log.d(TAG, "Starting game activity");
        Intent intent = new Intent(context, GameActivity.class);
//        Bundle extras = intent.getExtras();
        Bundle extras = new Bundle();
        extras.putInt(GameActivity.WORLD_ID, worldID);
        extras.putInt(GameActivity.LEVEL_ID, levelID);
        intent.putExtras(extras);
        startActivity(intent);
    }
}

//private static final String TAG = LevelFragment.class.getName();
//
//private World world;
//private String title;
//private static final int NUM_COLS = 5;
//private static final int NUM_ROWS = 5;
//private Button buttons[][] = new Button[NUM_ROWS][NUM_COLS];
//
//    public LevelFragment() {
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        ViewGroup rootView = (ViewGroup) inflater.inflate(
//                R.layout.fragment_level, container, false);
//
//        populateButtons(rootView);
//        return rootView;
//    }
//
//    private ViewGroup populateButtons(ViewGroup view) {
//        Log.d(TAG, "Populating buttons for: " + getArguments().get("title"));
//
//        TableLayout table = (TableLayout) view.findViewById(R.id.tableForButtons);
//
//        for (int row = 0; row < NUM_ROWS; row++) {
//            TableRow tableRow = new TableRow(table.getContext());
//            tableRow.setLayoutParams(new TableLayout.LayoutParams(
//                    TableLayout.LayoutParams.MATCH_PARENT,
//                    TableLayout.LayoutParams.MATCH_PARENT,
//                    1.0f));
//
//
//            for (int col = 0; col < NUM_COLS; col++) {
//                Log.d(TAG, "row=" + row + " col=" + col);
//                final int FINAL_COL = col;
//                final int FINAL_ROW = row;
//
//                Button button = new Button(view.getContext());
//                button.setLayoutParams(new TableLayout.LayoutParams(
//                        TableLayout.LayoutParams.MATCH_PARENT,
//                        TableLayout.LayoutParams.MATCH_PARENT,
//                        1.0f));
//                button.setText("" + col + ", " + row);
//                button.setPadding(1, 1, 1, 1);
//                button.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        LevelActivity.levelSelected((FINAL_ROW * NUM_COLS) + FINAL_COL);
//                    }
//                });
//                tableRow.addView(button);
//                buttons[row][col] = button;
//            }
//            table.addView(tableRow);
//
//        }
//
//        view.addView(table);
//        return view;
//    }
//
//
//    public void setWorld(World world) {
//        this.world = world;
//    }
//
//    public String getTitle() {
//        return title;
//    }
