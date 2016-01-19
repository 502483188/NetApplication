package com.ittx.netapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    private int mCount = 1;
    private boolean mFlag = true;

    /**
     * 第一步:定义交互接口
     */
    interface IBinderMyService {
        int getCount();
    }

    /**
     * 第二步: 继承Binder实现交互接口
     */
    public class MyBinderService extends Binder implements IBinderMyService {
        @Override
        public int getCount() {
            return mCount;
        }
    }

    /**
     * 第三步:实例类并在onBinder方法中返回
     */
    public MyBinderService binderService = new MyBinderService();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("tag", "onCreate>>>>>>>>>> ");

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mFlag) {
                    mCount++;
                    Log.e("tag", "count :" + mCount);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("tag", "onStartCommand>>>>>>>>>> ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e("tag", "onBind>>>>>>>>>> ");
        return binderService;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("tag", "onUnbind>>>>>>>>>> ");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.e("tag", "onDestroy>>>>>>>>>> ");
        super.onDestroy();
        mFlag = false;
    }
}
