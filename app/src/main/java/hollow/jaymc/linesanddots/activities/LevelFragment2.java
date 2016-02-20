package hollow.jaymc.linesanddots.activities;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListAdapter;

import hollow.jaymc.linesanddots.R;
import hollow.jaymc.linesanddots.gameObjects.World;
import hollow.jaymc.linesanddots.utils.Reader;
import hollow.jaymc.linesanddots.utils.Utils;
import hollow.jaymc.linesanddots.utils.Writer;

public class LevelFragment2 extends Fragment {

    private static final String TAG = LevelFragment2.class.getName();
    private String title;
    public static final String NUMBER_OF_LEVELS = "NUMBER_OF_LEVELS";
    public static final String WORLD_ID = "WORLD_ID";
    public static final String TITLE = "TITLE";
    public static final String SCORES = "SCORES";

    private ImageButton[] buttons;

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
        buttons = new ImageButton[getArguments().getInt(NUMBER_OF_LEVELS)];
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.fragment_level, container, false);
        createButtons(buttons, v);

        GridView gv = (GridView) v.findViewById(R.id.gridview);
        Adapter adapter = new Adapter();
        gv.setAdapter(adapter);
        return v;
    }

    private void createButtons(ImageButton[] buttons, View v) {
        final int worldID = getArguments().getInt(WORLD_ID);
        for(int i = 0; i < buttons.length; i++) {
            ImageButton btn = new ImageButton(v.getContext());
            final int levelId = i;

            int score = Reader.getScore(getContext(), Utils.createTag(worldID, levelId));
            if(score == 0)
                btn.setImageResource(R.drawable.stars0);
            else if(score == 1)
                btn.setImageResource(R.drawable.stars1);
            else if(score == 2)
                btn.setImageResource(R.drawable.stars2);
            else if(score == 3)
                btn.setImageResource(R.drawable.stars3);


            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String levelTag = Utils.createTag(worldID, levelId);

                    startGame(v.getContext(), levelTag);
                }
            });

            buttons[i] = btn;
        }
    }

    private void startGame(Context context, String tag) {
        LevelActivity.lastPage = getArguments().getInt(WORLD_ID);
        Intent intent = new Intent(context, GameActivity.class);
        Writer.saveSharedPreference(context, getResources().getString(R.string.lastLevelPlayed), tag);
        startActivity(intent);
    }

    private class Adapter  extends BaseAdapter{

        public  Adapter() {
            super();
        }


        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }

}
