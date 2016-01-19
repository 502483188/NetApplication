package com.ittx.netapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class MenuFragment extends Fragment {
    private Button mPictureBtn, mVideoBtn, mMusicBtn;

    private OnMenuFragmentInteractionListener mOnMenuFragmentInteractionListener;

    /**
     * 第一步定义接口
     */
    public interface OnMenuFragmentInteractionListener {
        void toPicture();

        void toVideo();

        void toMusic();
    }

    public static MenuFragment newInstance() {
        MenuFragment fragment = new MenuFragment();
        return fragment;
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        if (context instanceof OnMenuFragmentInteractionListener) {
            mOnMenuFragmentInteractionListener = (OnMenuFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMenuFragmentInteractionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mPictureBtn = (Button) getView().findViewById(R.id.framgment_merchant_picture_btn);
        mVideoBtn = (Button) getView().findViewById(R.id.framgment_merchant_video_btn);
        mMusicBtn = (Button) getView().findViewById(R.id.framgment_merchant_music_btn);

        mPictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnMenuFragmentInteractionListener.toPicture();
            }
        });
        mVideoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnMenuFragmentInteractionListener.toVideo();
            }
        });
        mMusicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mOnMenuFragmentInteractionListener.toMusic();
            }
        });
    }
}
