package com.ittx.netapp.mp3;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ittx.netapp.R;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MP3Activity extends AppCompatActivity implements View.OnClickListener {
    private Button mPlayerMusicBtn, mStopMusicBtn, mPrceMusicBtn, mNextMusicBtn;
    private SeekBar mSeekBar;
    private TextView mTotalTimeTxt, mCurrentTimeTxt,mMusicNameTxt;
    private MediaPlayer mediaPlayer = new MediaPlayer();  //IDLE

    private static class MyHandler extends Handler {
        private WeakReference<MP3Activity> reference;

        public MyHandler(MP3Activity activity) {
            reference = new WeakReference<MP3Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MP3Activity activity = reference.get();
            if (activity != null) {
                int currentTime = activity.mediaPlayer.getCurrentPosition();
                activity.mCurrentTimeTxt.setText(activity.format.format(new Date(currentTime)));
                activity.mSeekBar.setProgress(currentTime);
            }
        }
    }

    private MyHandler mHandler = new MyHandler(this);

    private SimpleDateFormat format = new SimpleDateFormat("mm:ss");
    private String mMusicPath;
    private List<MusicBean> musicBeanList = new ArrayList<>();
    private int mCurrentPostion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mp3_layout);
        mPlayerMusicBtn = (Button) findViewById(R.id.mp3_playter_music_btn);
        mStopMusicBtn = (Button) findViewById(R.id.mp3_stop_music_btn);
        mPrceMusicBtn = (Button) findViewById(R.id.mp3_prce_music_btn);
        mNextMusicBtn = (Button) findViewById(R.id.mp3_next_music_btn);
        mSeekBar = (SeekBar) findViewById(R.id.mp3_player_seekbar);
        mTotalTimeTxt = (TextView) findViewById(R.id.mp3_total_time_txt);
        mCurrentTimeTxt = (TextView) findViewById(R.id.mp3_current_time_txt);
        mMusicNameTxt = (TextView) findViewById(R.id.mp3_musicname_txt);
        mPlayerMusicBtn.setOnClickListener(this);
        mStopMusicBtn.setOnClickListener(this);
        mPrceMusicBtn.setOnClickListener(this);
        mNextMusicBtn.setOnClickListener(this);

        //获取参数据---------------
//        MusicBean musicBean = getIntent().getParcelableExtra("MUSIC_BENA");
//        mMusicPath = musicBean.getMusicPath();


        musicBeanList = getIntent().getParcelableArrayListExtra("MUSIC_LIST");
        mCurrentPostion = getIntent().getIntExtra("CURRENT_POSTION", 0);

        MusicBean musicBean = musicBeanList.get(mCurrentPostion);
        mMusicPath = musicBean.getMusicPath();
        mMusicNameTxt.setText(musicBean.getMusicName());
        //------------------
        inintMusic();

        mediaPlayer.start();

        Log.e("tag", "mediaPlayer.getDuration()  :" + mediaPlayer.getDuration());
        int totalTime = mediaPlayer.getDuration();
        mSeekBar.setMax(totalTime);

        mTotalTimeTxt.setText(format.format(new Date(totalTime)));

        updateMusicPlayer();

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int mProgress;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.e("tag", "onProgressChanged progress :" + progress);
                mProgress = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.e("tag", "onStartTrackingTouch >>>>> ");
                mediaPlayer.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.e("tag", "onStopTrackingTouch >>>>> ");
                mediaPlayer.seekTo(mProgress);
                mediaPlayer.start();
            }
        });

    }

    public void inintMusic() {
        //String mp3path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath() + "/qlyh.mp3";
        try {
            mediaPlayer.setDataSource(mMusicPath);
            mediaPlayer.prepare();

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    //播放完成，下一首
                    mPlayerMusicBtn.setText("播放");
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private boolean mFlag = true;
    public void updateMusicPlayer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mFlag) {
                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        /*Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                int currentTime = mediaPlayer.getCurrentPosition();
                                mCurrentTimeTxt.setText(format.format(new Date(currentTime)));
                                mSeekBar.setProgress(currentTime);
                            }
                        };

                        mHandler.post(runnable);*/

                        mHandler.sendMessage(Message.obtain());

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Log.e("tag", "mediaPlayer.getCurrentPosition()  :" + mediaPlayer.getCurrentPosition());
                    }
                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mp3_playter_music_btn:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    mPlayerMusicBtn.setText("播放");
                } else {
                    mediaPlayer.start();
                    mPlayerMusicBtn.setText("暂停");
                }
                break;

            case R.id.mp3_stop_music_btn:
               /* mediaPlayer.stop();
                try {
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/

                mediaPlayer.reset();
                inintMusic();
                mPlayerMusicBtn.setText("播放");
                break;

            case R.id.mp3_prce_music_btn:
                if(--mCurrentPostion < 0 ){
                    mCurrentPostion = 0;
                }
                playMusic();
                break;
            case R.id.mp3_next_music_btn:
                if(++mCurrentPostion > (musicBeanList.size()-1)){
                     mCurrentPostion = musicBeanList.size()-1;
                }
                playMusic();
                break;
        }
    }

    public void playMusic(){
        MusicBean musicBean = musicBeanList.get(mCurrentPostion);
        mMusicPath = musicBean.getMusicPath();
        mMusicNameTxt.setText(musicBean.getMusicName());
        mediaPlayer.reset();
        inintMusic();
        mediaPlayer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFlag = false;
        mediaPlayer.release();
        mediaPlayer = null;
    }
}
