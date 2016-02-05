package hollow.jaymc.linesanddots.activities;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import hollow.jaymc.linesanddots.R;
import hollow.jaymc.linesanddots.gameObjects.World;
import hollow.jaymc.linesanddots.utils.Reader;

public class LevelActivity extends FragmentActivity {

    private static final String TAG = LevelActivity.class.getName();
    public static int lastPage = 0;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private LevelPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        List<Fragment> fragments = getFragments();
        mPagerAdapter = new LevelPagerAdapter(getSupportFragmentManager(), fragments);
        ViewPager mPager = (ViewPager) findViewById(R.id.viewpager);
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(lastPage);
    }

    @Override
    public void onPause() {
        super.onPause();
        finish();
    }

    private List<World> worlds = new ArrayList<>();

    private List<Fragment> getFragments() {
        Reader.getLevelInformation(this, worlds);

        List<Fragment> fragments = new ArrayList<Fragment>();
        for (int i = 0; i < worlds.size(); i++) {
            worlds.get(i).setWorldID(i);
            fragments.add(LevelFragment.newInstance(worlds.get(i)));
        }
        return fragments;
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