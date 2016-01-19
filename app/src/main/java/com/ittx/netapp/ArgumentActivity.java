package com.ittx.netapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ArgumentActivity extends AppCompatActivity implements ArgumentFragment.onArgumentFragmentInterface{
    private Button mArugmentBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_argument_layout);

        mArugmentBtn = (Button) findViewById(R.id.argment_activity_btn);
    }

    public void onToFragmentClick(View view) {
        String p = "Fragment传参学习Activity->Fragment";
        getSupportFragmentManager().beginTransaction().
                add(R.id.argument_fragment_layout, ArgumentFragment.newInstance(p)).commit();
    }

    @Override
    public void doArguemnt(String parameter1) {
        mArugmentBtn.setText(parameter1);
    }
}
