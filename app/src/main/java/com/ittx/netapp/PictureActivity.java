package com.ittx.netapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PictureActivity.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PictureActivity#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PictureActivity extends Activity {
    private ImageView mBackImg;

    public static void  actionToPictureActivity(Context context) {
        Intent intent = new Intent(context,PictureActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_picture_layout);
        mBackImg = (ImageView) findViewById(R.id.title_imageview);

        mBackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
