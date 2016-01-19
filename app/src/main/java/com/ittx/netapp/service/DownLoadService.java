package com.ittx.netapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/**
 * 通过服务service后台下载文件
 */
public class DownLoadService extends Service {
    private MyHandler mHandler;
    private Looper mLooper;
    public class MyHandler extends Handler {
        public MyHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            //下载文件
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.e("tag","下载 "+msg.obj +" 成功!");
            stopSelf();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("tag","onCreate>>>>");
        HandlerThread handlerThread = new HandlerThread("DownLoadThread");
        handlerThread.start();

        mLooper = handlerThread.getLooper();
        mHandler = new MyHandler(mLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String fileName = intent.getStringExtra("FILE_NAME");
        Log.e("tag",fileName+"开始下载"+ " startId："+startId);
        Message message = Message.obtain();
        message.what = startId;
        message.obj = fileName;
        mHandler.sendMessage(message);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLooper.quit();
        Log.e("tag","onDestroy >>>");
    }
}
