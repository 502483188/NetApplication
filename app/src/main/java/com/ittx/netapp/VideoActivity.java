package com.ittx.netapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VideoActivity.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VideoActivity#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoActivity extends Activity {


    public static void  actionToVideoActivity(Context context) {
        Intent intent = new Intent(context,VideoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_video_layout);
    }

}
