package com.ittx.netapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MusicActivity.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MusicActivity#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MusicActivity extends Activity {
    public static void  actionToMusicFragment(Context context) {
        Intent intent = new Intent(context,MusicActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_music_layout);
    }

}
