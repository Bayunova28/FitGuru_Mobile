package com.uas.fitguru;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class WorkoutDetailActivity extends AppCompatActivity {
    TextView title;
    String position, judul;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_workout_detail);

        //Hooks
        title = findViewById(R.id.title);
        VideoView videoView = (VideoView)findViewById(R.id.videoView1);

        //Mendapatkan Variabel Position
        Intent intent = getIntent();
        position = intent.getStringExtra("position");

        //Buat objek untuk class Media Controller
        MediaController mediaController = new MediaController(this);
        switch (position){
            case "0":
                judul = intent.getStringExtra("title");
                title.setText(judul);
                videoView.setVideoPath("http://192.168.2.5/FitGuru/video/Pushup.mp4");
                mediaController.setAnchorView(videoView);
                videoView.setMediaController(mediaController);
                videoView.start();
              break;
            case "1":
                judul = intent.getStringExtra("title");
                title.setText(judul);
                videoView.setVideoPath("http://192.168.2.5/FitGuru/video/BodyBuilding.mp4");
                mediaController.setAnchorView(videoView);
                videoView.setMediaController(mediaController);
                videoView.start();
                break;
            case "2":
                judul = intent.getStringExtra("title");
                title.setText(judul);
                videoView.setVideoPath("http://192.168.2.5/FitGuru/video/Yoga.mp4");
                mediaController.setAnchorView(videoView);
                videoView.setMediaController(mediaController);
                videoView.start();
                break;
            case "3":
                judul = intent.getStringExtra("title");
                title.setText(judul);
                videoView.setVideoPath("http://192.168.2.5/FitGuru/video/PowerLifting.mp4");
                mediaController.setAnchorView(videoView);
                videoView.setMediaController(mediaController);
                videoView.start();
                break;
        }
    }
}