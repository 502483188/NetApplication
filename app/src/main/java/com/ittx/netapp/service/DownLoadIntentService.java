package com.ittx.netapp.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class DownLoadIntentService extends IntentService {
    public DownLoadIntentService() {
        super("DownLoadIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //下载文件
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.e("tag", "下载 " + intent.getStringExtra("FILE_NAME") + " 成功!");
    }

}
