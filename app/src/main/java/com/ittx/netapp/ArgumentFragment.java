package com.ittx.netapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class ArgumentFragment extends Fragment {
    public static final String PARAMTETER = "PARAMTETER1";
    public String parmeter = "初化值";

    private onArgumentFragmentInterface mArgumentFragmentInterface;
    interface onArgumentFragmentInterface{
        void doArguemnt(String parameter1);
    }

    public static ArgumentFragment newInstance(String p) {
        ArgumentFragment fragment = new ArgumentFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PARAMTETER, p);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mArgumentFragmentInterface = (onArgumentFragmentInterface)activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        parmeter = bundle.getString(PARAMTETER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_argument_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView texview = (TextView) getView().findViewById(R.id.fragment_txt);
        Button backBtn = (Button) getView().findViewById(R.id.fragment_btn);
        texview.setText(parmeter);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mArgumentFragmentInterface.doArguemnt("Fragment传参给Activity 接口实现");
            }
        });
    }
}
