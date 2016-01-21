package com.ittx.netapp.mp3;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ittx.netapp.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MP3Activity extends AppCompatActivity implements View.OnClickListener {
    private Button mPlayerMusicBtn, mStopMusicBtn;
    private SeekBar mSeekBar;
    private TextView mTotalTimeTxt, mCurrentTimeTxt;
    private MediaPlayer mediaPlayer = new MediaPlayer();  //IDLE

    private Handler mHandler = new Handler();
    private SimpleDateFormat format = new SimpleDateFormat("mm:ss");
    private String mMusicPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mp3_layout);
        mPlayerMusicBtn = (Button) findViewById(R.id.mp3_playter_music_btn);
        mStopMusicBtn = (Button) findViewById(R.id.mp3_stop_music_btn);
        mSeekBar = (SeekBar) findViewById(R.id.mp3_player_seekbar);
        mTotalTimeTxt = (TextView) findViewById(R.id.mp3_total_time_txt);
        mCurrentTimeTxt = (TextView) findViewById(R.id.mp3_current_time_txt);
        mPlayerMusicBtn.setOnClickListener(this);
        mStopMusicBtn.setOnClickListener(this);

        MusicBean musicBean = getIntent().getParcelableExtra("MUSIC_BENA");
        mMusicPath = musicBean.getMusicPath();
        inintMusic();

        Log.e("tag", "mediaPlayer.getDuration()  :" + mediaPlayer.getDuration());
        int totalTime = mediaPlayer.getDuration();
        mSeekBar.setMax(totalTime);

        /*int time = totalTime / 1000;
        int min = time / 60;
        int sec = time % 60;

        String totalStr = min + ":" + sec;
        mTotalTimeTxt.setText(totalStr);*/

        mTotalTimeTxt.setText(format.format(new Date(totalTime)));

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                int currentTime = mediaPlayer.getCurrentPosition();

                               /* int time = currentTime / 1000;
                                int min = time / 60;
                                int sec = time % 60;

                                String currentStr = "";
                                if (sec < 10) {
                                    currentStr = min + ":0" + sec;
                                } else {
                                    currentStr = min + ":" + sec;
                                }
                                mCurrentTimeTxt.setText(currentStr);*/


                                mCurrentTimeTxt.setText(format.format(new Date(currentTime)));

                                mSeekBar.setProgress(currentTime);
                            }
                        });

                        Log.e("tag", "mediaPlayer.getCurrentPosition()  :" + mediaPlayer.getCurrentPosition());
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int mProgress;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.e("tag","onProgressChanged progress :"+progress);
                mProgress = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.e("tag","onStartTrackingTouch >>>>> ");
                mediaPlayer.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.e("tag","onStopTrackingTouch >>>>> ");
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
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
        mediaPlayer = null;
    }
}
