package com.wazimatariq.i190439;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class songTitle1 extends AppCompatActivity {

    TextView title,time;
    SeekBar seekbar;
    ImageView pause,next,previous,icon;
    ArrayList<AudioModel> songlist;
    AudioModel currentSong;
    MediaPlayer mediaPlayer=MyMediaPlayer.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_title1);
        title=findViewById(R.id.song_title);
        time=findViewById(R.id.timeS);
        seekbar=findViewById(R.id.seekbar);
        pause=findViewById(R.id.play5);
        previous=findViewById(R.id.playPrev4);
        next=findViewById(R.id.playNext4);
        icon=findViewById(R.id.musicIcon);

        title.setSelected(true);

        songlist=(ArrayList<AudioModel>) getIntent().getSerializableExtra("LIST");

        setResourcesWithMusic();
    }

    void setResourcesWithMusic(){
        currentSong=songlist.get(MyMediaPlayer.currentIndex);
        title.setText(currentSong.getTitle());

        time.setText(convertToMMS(currentSong.getDuration()));

        pause.setOnClickListener(v-> pausePlay());
        next.setOnClickListener(v-> playNextSong());
        previous.setOnClickListener(v-> playPreviousSong());

        playMusic();

    }

    private void playMusic(){
        mediaPlayer.reset();

        try {
            mediaPlayer.setDataSource(currentSong.getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            seekbar.setProgress(0);
            seekbar.setMax(mediaPlayer.getDuration());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void playNextSong(){
        if(MyMediaPlayer.currentIndex==songlist.size()-1)
            return;
        MyMediaPlayer.currentIndex+=1;
        mediaPlayer.reset();
        setResourcesWithMusic();
    }

    private void playPreviousSong(){
        if(MyMediaPlayer.currentIndex==0)
            return;
        MyMediaPlayer.currentIndex-=1;
        mediaPlayer.reset();
        setResourcesWithMusic();

        songTitle1.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer!=null){
                    seekbar.setProgress(mediaPlayer.getCurrentPosition());
                    time.setText(convertToMMS(mediaPlayer.getCurrentPosition()+""));

                    if(mediaPlayer.isPlaying()){
                        pause.setImageResource(R.drawable.wrapper_play);

                    }
                    else{
                        pause.setImageResource(R.drawable.wrapper_pausebutton);

                    }
                }
                new Handler().postDelayed(this,100);
            }
        });
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(mediaPlayer!=null && b){
                    mediaPlayer.seekTo(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void pausePlay(){
        if(mediaPlayer.isPlaying())
            mediaPlayer.pause();
        else
            mediaPlayer.start();

    }

    public static String convertToMMS(String duration){
        Long millis= Long.parseLong(duration);
        return String.format("%02d", TimeUnit.MILLISECONDS.toMinutes(millis)%TimeUnit.HOURS.toMinutes(1),
        TimeUnit.MILLISECONDS.toMinutes(millis)%TimeUnit.MINUTES.toSeconds(1));

    }
}