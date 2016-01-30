package hollow.jaymc.linesanddots.activities;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import hollow.jaymc.linesanddots.R;
import hollow.jaymc.linesanddots.gameObjects.World;
import hollow.jaymc.linesanddots.utils.Reader;

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
     * Lists of all playable levels split into "worlds" / bundles.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        List<Fragment> fragments = getFragments();
        mPagerAdapter = new LevelPagerAdapter(getSupportFragmentManager(), fragments);
        ViewPager mPager = (ViewPager) findViewById(R.id.viewpager);
        mPager.setAdapter(mPagerAdapter);


    }

//    private List<String> worlds = new ArrayList<>();
    private List<World> worlds = new ArrayList<>();
    private List<Fragment> getFragments() {
//        List<Integer> levels = new ArrayList<>();
//        List<Integer> scores = new ArrayList<>();
        Reader.getLevelInformation(this, worlds);

        List<Fragment> fragments = new ArrayList<Fragment>();
        for (int i = 0; i < worlds.size(); i++) {
            World world = worlds.get(i);
            fragments.add(LevelFragment.newInstance(worlds.get(i)));
//            fragments.add(LevelFragment.newInstance(worlds.get(i), i, levels.get(i), scores.get(i)));
        }
        return fragments;
    }

    public static void levelSelected(int worldID, int levelID) {
        Log.d(TAG, "Level: " + levelID + " selected.");

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
}