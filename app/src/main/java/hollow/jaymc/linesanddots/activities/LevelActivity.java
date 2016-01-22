package hollow.jaymc.linesanddots.activities;

import android.app.Activity;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import hollow.jaymc.linesanddots.R;
import hollow.jaymc.linesanddots.objects.World;

public class LevelActivity extends FragmentActivity {

    private static final String TAG = LevelActivity.class.getName();

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
//    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private LevelPagerAdapter mPagerAdapter;


    private int num_pages = 5;
    /**
     *  Lists of all playable levels split into "worlds" / bundles.
     */
    private List<World> worlds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        List<Fragment> fragments = getFragments();
        mPagerAdapter = new LevelPagerAdapter(getSupportFragmentManager(), fragments);
        ViewPager mPager = (ViewPager)findViewById(R.id.viewpager);
        mPager.setAdapter(mPagerAdapter);



    }

//    private List<Fragment> getFragments() {
//        worlds = new ArrayList<World>();
//        worlds.add(new World("World: 1"));
//        worlds.add(new World("World: 2"));
//        worlds.add(new World("World: 3"));
//        worlds.add(new World("World: 4"));
//        worlds.add(new World("World: 5"));
//        worlds.add(new World("World: 6"));
//        num_pages = worlds.size();
//
//        List<Fragment> fragments = new ArrayList<Fragment>();
//        for(int i = 0; i < worlds.size(); i++) {
//            LevelFragment level = new LevelFragment();
//            level.setArguments(getIntent().getExtras());
//            Bundle b = new Bundle();
//            b.putInt("levels", worlds.get(i).getNumberOfLevels());
//            b.putString("title", worlds.get(i).getWorldName());
//            level.setArguments(b);
//
//            fragments.add(level);
//        }
//        return fragments;
//    }

    private List<Fragment> getFragments() {
        worlds = new ArrayList<World>();
        worlds.add(new World("World: 1"));
        worlds.add(new World("World: 2"));
        worlds.add(new World("World: 3"));
        worlds.add(new World("World: 4"));
        worlds.add(new World("World: 5"));
        worlds.add(new World("World: 6"));
        num_pages = worlds.size();

        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(LevelFragment.newInstance("Fragment 1"));
        fragments.add(LevelFragment.newInstance("Fragment 2"));
        fragments.add(LevelFragment.newInstance("Fragment 3"));
        fragments.add(LevelFragment.newInstance("Fragment 4"));
        fragments.add(LevelFragment.newInstance("Fragment 5"));
        fragments.add(LevelFragment.newInstance("Fragment 6"));
        return fragments;
    }

    public static void levelSelected(int levelIndex) {
        Log.d(TAG, "Level: " + levelIndex + " selected.");
    }

    private class LevelPagerAdapter extends FragmentStatePagerAdapter {

        private List<Fragment> fragments;

        public LevelPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }


        @Override
        public Fragment getItem(int position) {
            return this.fragments.get(position);
        }

        @Override
        public int getCount() {
            return this.fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return worlds.get(position).getWorldName();
        }
    }

//    LevelFragment level = new LevelFragment();
//    level.setArguments(getIntent().getExtras());
//    Bundle b = new Bundle();
//    b.putInt("levels", 40);
//    level.setArguments(b);
//
//
//    getSupportFragmentManager().beginTransaction().add(R.id.level_pages, level).commit();
//    ViewPager pager = (ViewPager)findViewById(R.id.level_pages);
//    pager.addView(level.onCreateView(this.getLayoutInflater(), pager, savedInstanceState));



    // * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
// * one of the sections/tabs/pages.
//    public class SectionsPagerAdapter extends FragmentPagerAdapter {
//
//        public SectionsPagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            // getItem is called to instantiate the fragment for the given page.
//            // Return a PlaceholderFragment (defined as a static inner class below).
//            return LevelFragment.newInstance(position+1);
//        }
//
//        @Override
//        public int getCount() {
//            if(worlds != null && worlds.size() > 0)
//                return worlds.size();
//            else
//                return 10;
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            position--;
//            if(worlds == null || worlds.size() <= 0) {
//                return "Levels FAILED to load.";
//            } else {
//                if (position < worlds.size() && position >= 0)
//                    return worlds.get(position).getWorldName();
//                else
//                    return worlds.get(worlds.size()-1).getWorldName();
//            }
//        }
//    }
}






//public static class PlaceholderFragment extends Fragment {
//    /**
//     * The fragment argument representing the section number for this
//     * fragment.
//     */
//    private static final String ARG_SECTION_NUMBER = "section_number";
//
//    /**
//     * Returns a new instance of this fragment for the given section
//     * number.
//     */
//    public static PlaceholderFragment newInstance(int sectionNumber) {
//        PlaceholderFragment fragment = new PlaceholderFragment();
//        Bundle args = new Bundle();
//        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    public PlaceholderFragment() {
//    }
//
//
//
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.fragment_level, container, false);
//        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
////            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
//        textView.setText(mSectionsPagerAdapter.getPageTitle(getArguments().getInt(ARG_SECTION_NUMBER)));
//        inflater.inflate(R.layout.fragment_level, container, false);
//        populateButtons(this.getActivity());
//        return rootView;
//    }
//
//    private static final int NUM_COLS = 5;
//    private static final int NUM_ROWS = 5;
//    private Button buttons[][] = new Button[NUM_ROWS][NUM_COLS];
//
//    private void populateButtons(Activity view) {
//        Log.d(TAG, "Populating buttons.");
//        Log.d(TAG, "Activity Title: " + view.getTitle());
//
//        TableLayout table = (TableLayout) this.getView().findViewById(R.id.tableForButtons);
////            TableLayout table = (TableLayout) view.findViewById(R.id.tableForButtons);
//        if(table == null)
//            Log.d(TAG, "Table is null");
//        else
//            Log.d(TAG, "Table NOT null");
//        for(int row = 0; row < NUM_ROWS; row++) {
////                TableRow tableRow = new TableRow(view);
//            TableRow tableRow = new TableRow(this.getView().getContext());
//            tableRow.setLayoutParams(new TableLayout.LayoutParams(
//                    TableLayout.LayoutParams.MATCH_PARENT,
//                    TableLayout.LayoutParams.MATCH_PARENT,
//                    1.0f));
//            table.addView(tableRow);
//            for(int col = 0; col < NUM_COLS; col++) {
//                final int FINAL_COL = col;
//                final int FINAL_ROW = row;
//
////                    Button button = new Button(view);
//                Button button = new Button(this.getView().getContext());
//                button.setLayoutParams(new TableLayout.LayoutParams(
//                        TableLayout.LayoutParams.MATCH_PARENT,
//                        TableLayout.LayoutParams.MATCH_PARENT,
//                        1.0f));
//                button.setText("" + col + ", " + row);
//                button.setPadding(0, 0, 0, 0);
//                button.setOnClickListener(new View.OnClickListener(){
//                    @Override
//                    public void onClick(View v) {
//                        gridButtonClicked(FINAL_COL, FINAL_ROW);
//                    }
//                });
//
//                tableRow.addView(button);
//                buttons[row][col] = button;
//            }
//        }
//    }
//    private void gridButtonClicked(int col, int row) {
//        Log.d(TAG, "Clicked: " + col + ", " + row);
//    }
//}
