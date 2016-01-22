package hollow.jaymc.linesanddots.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import hollow.jaymc.linesanddots.R;
import hollow.jaymc.linesanddots.objects.World;

/**
 * Created by jaymc on
 * 12/12/2015.
 */
public class LevelFragment extends Fragment {
    private static final String TAG = LevelFragment.class.getName();
    private String title;
    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";

    public static final int NUM_COL = 5;
    public static final int NUM_ROW = 5;

    private ScrollView scrollView;

    public LevelFragment() {}

    public static final LevelFragment newInstance(String message)
    {
        LevelFragment f = new LevelFragment();
        Bundle bdl = new Bundle(1);
        bdl.putString(EXTRA_MESSAGE, message);
        f.setArguments(bdl);
        return f;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String message = getArguments().getString(EXTRA_MESSAGE);
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

            for (int row = 0; row < NUM_ROW; row++) {
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
                for (int col = 0; col < NUM_ROW; col++) {
                    Button button = new Button(v.getContext());
                    button.setText("" + row + ", " + col + "");
                    tableRow.addView(button);
                }
                tableLayout.addView(tableRow);
            }

            scrollView.addView(tableLayout);
            v.addView(scrollView);

        return v;
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
