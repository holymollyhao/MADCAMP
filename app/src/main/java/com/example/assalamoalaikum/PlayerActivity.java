package com.example.assalamoalaikum;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity {
    Button btn_next, btn_previous, btn_pause;
    TextView songTextLabel;
    SeekBar songSeekbar;

    static MediaPlayer myMediaplayer;
    int position;
    String sname;
    ArrayList<File> mySongs;
    Thread updateseekBar;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        btn_next=(Button)findViewById(R.id.next);
        btn_previous=(Button)findViewById(R.id.previous);
        btn_pause=(Button)findViewById(R.id.pause);
        songTextLabel = (TextView) findViewById(R.id.songLabel);
        songSeekbar = (SeekBar) findViewById(R.id.seekBar);

        getSupportActionBar().setTitle("Now Playing");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        updateseekBar = new Thread(){

            @Override
            public void run() {
                int totalDuration = myMediaplayer.getDuration();
                int currentPosition = 0;

                while(currentPosition <totalDuration){
                    try{
                        sleep(500);
                        currentPosition = myMediaplayer.getCurrentPosition();
                        songSeekbar.setProgress(currentPosition);
                    }
                    catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        };

        if(myMediaplayer!=null){
            myMediaplayer.stop();
            myMediaplayer.release();
        }
        Intent i = getIntent();
        Bundle bundle = i.getExtras();

        mySongs=(ArrayList) bundle.getParcelableArrayList("songs");

        sname = mySongs.get(position).getName().toString();

        String songName = i.getStringExtra("songname");

        songTextLabel.setText(songName);
        songTextLabel.setSelected(true);

        position = bundle.getInt("pos", 0);

        Uri u = Uri.parse(mySongs.get(position).toString());

        myMediaplayer = MediaPlayer.create(getApplicationContext(),u);

        myMediaplayer.start();
        songSeekbar.setMax(myMediaplayer.getDuration());

        updateseekBar.start();

        songSeekbar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
        songSeekbar.getThumb().setColorFilter(getResources().getColor(R.color.colorPrimary),PorterDuff.Mode.SRC_IN);

        songSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                myMediaplayer.seekTo(seekBar.getProgress());
            }
        });

        btn_pause.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                songSeekbar.setMax(myMediaplayer.getDuration());

                if(myMediaplayer.isPlaying()){
                    btn_pause.setBackgroundResource(R.drawable.icon_play);
                    myMediaplayer.pause();
                }

                else{
                    btn_pause.setBackgroundResource(R.drawable.icon_pause);
                    myMediaplayer.start();
                }

            }

       });

       btn_next.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view){

               myMediaplayer.stop();
               myMediaplayer.release();
               position = ((position+1)%mySongs.size());

               Uri u = Uri.parse(mySongs.get(position).toString());

               myMediaplayer = MediaPlayer.create(getApplicationContext(), u);

               sname = mySongs.get(position).getName().toString();
               songTextLabel.setText(sname);

               myMediaplayer.start();

           }

       });

       btn_previous.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view){
               myMediaplayer.stop();
               myMediaplayer.release();

               position = ((position-1)<0)?(mySongs.size()-1):(position-1);
               Uri u = Uri.parse(mySongs.get(position).toString());
               myMediaplayer = MediaPlayer.create(getApplicationContext(), u);

               sname = mySongs.get(position).getName().toString();
               songTextLabel.setText(sname);

               myMediaplayer.start();
           }
       });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
