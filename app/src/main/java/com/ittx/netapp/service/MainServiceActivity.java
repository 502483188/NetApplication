package com.ittx.netapp.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ittx.netapp.R;

public class MainServiceActivity extends AppCompatActivity {
    private Button mStartBtn, mStopBtn, mBindBtn, mGetCountBtn;
    private MyService.IBinderMyService mIBinderMyService;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("tag", "onServiceConnected >>>>>");
            mIBinderMyService = (MyService.IBinderMyService) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("tag", "onServiceDisconnected >>>>>");
            mIBinderMyService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_service_layout);

        mStartBtn = (Button) findViewById(R.id.service_start_btn);
        mStopBtn = (Button) findViewById(R.id.service_stop_btn);
        mBindBtn = (Button) findViewById(R.id.service_bindservice_btn);
        mGetCountBtn = (Button) findViewById(R.id.service_getcount_btn);

        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainServiceActivity.this, MyService.class);
                startService(intent);
            }
        });

        mStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(MainServiceActivity.this, MyService.class));
            }
        });

        mBindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainServiceActivity.this, MyService.class);
                bindService(intent, connection, BIND_AUTO_CREATE);
            }
        });

        mGetCountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetCountBtn.setText("" + mIBinderMyService.getCount());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (connection != null)
            unbindService(connection);
    }
}
