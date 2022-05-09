package com.example.yalla;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class DoWorkout extends AppCompatActivity {

    private TextView countDownText;
    private Button btnGo;

    private CountDownTimer countDownTimer;
    private long countTimeLeftInMilestones;
    Spinner spinner;
    private boolean timeRunning;
    private  ArrayList<String> arrayList = new ArrayList<>();
    private  MediaPlayer mediaPlayer;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private VideoView videoView;
    private Button btnBack, btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        mediaPlayer = MediaPlayer.create(this,R.raw.claps);
        setContentView(R.layout.activity_do_workout);

        btnBack = findViewById(R.id.button3);
        btnNext = findViewById(R.id.btnNextEx);

        Bundle b = getIntent().getExtras();
        String plan = b.getString("plan");
        String name = b.getString("workoutName");
        String type = b.getString("workoutType");
        String level = b.getString("workoutLevel");


        if (plan == null)
            btnNext.setVisibility(View.GONE);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(),PlansActivty.class);
                Bundle b = new Bundle();
                b.putString("plan",plan);
                it.putExtras(b);
                startActivity(it);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(),UserMenu.class);
                startActivity(it);
            }
        });

        String workoutName = b.getString("workoutName").toString();
        videoView = findViewById(R.id.videoView);
        if (workoutName.equals("Tuck and Crunch")){
            String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.truckandcrunch;
            Uri uri = Uri.parse(videoPath);
            videoView.setVideoURI(uri);
            MediaController mediaController = new MediaController(this);
            videoView.setMediaController(mediaController);
            mediaController.setAnchorView(videoView);
        }else if (workoutName.equals("Modified V-sit")){
            String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.vsit;
            Uri uri = Uri.parse(videoPath);
            videoView.setVideoURI(uri);
            MediaController mediaController = new MediaController(this);
            videoView.setMediaController(mediaController);
            mediaController.setAnchorView(videoView);
        }else if (workoutName.equals("Crunch")){
            String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.crunches;
            Uri uri = Uri.parse(videoPath);
            videoView.setVideoURI(uri);
            MediaController mediaController = new MediaController(this);
            videoView.setMediaController(mediaController);
            mediaController.setAnchorView(videoView);
        }else if (workoutName.equals("Seated Russian Twist")){
            String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.russiantwist;
            Uri uri = Uri.parse(videoPath);
            videoView.setVideoURI(uri);
            MediaController mediaController = new MediaController(this);
            videoView.setMediaController(mediaController);
            mediaController.setAnchorView(videoView);
        }else if (workoutName.equals("Bicycle Crunches")){
            String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.bycle;
            Uri uri = Uri.parse(videoPath);
            videoView.setVideoURI(uri);
            MediaController mediaController = new MediaController(this);
            videoView.setMediaController(mediaController);
            mediaController.setAnchorView(videoView);
        }else if (workoutName.equals("Plank")){
            String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.plank;
            Uri uri = Uri.parse(videoPath);
            videoView.setVideoURI(uri);
            MediaController mediaController = new MediaController(this);
            videoView.setMediaController(mediaController);
            mediaController.setAnchorView(videoView);
        }else if (workoutName.equals("Penguins")){
            String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.peng;
            Uri uri = Uri.parse(videoPath);
            videoView.setVideoURI(uri);
            MediaController mediaController = new MediaController(this);
            videoView.setMediaController(mediaController);
            mediaController.setAnchorView(videoView);
        }

        countDownText = findViewById(R.id.textView7);
        btnGo = findViewById(R.id.btnGo);

        arrayList.add("00:30");
        arrayList.add("01:00");
        arrayList.add("01:30");
        arrayList.add("02:00");
        arrayList.add("02:30");
        arrayList.add("03:00");
        arrayList.add("03:30");
        arrayList.add("04:00");
        arrayList.add("04:30");
        arrayList.add("05:00");

        spinner = findViewById(R.id.spinnerTimes);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.times, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    countTimeLeftInMilestones = 30000;
                    countDownText.setText(arrayList.get(position));
                }
                else if (position == 1) {
                    countTimeLeftInMilestones = 60000;
                    countDownText.setText(arrayList.get(position));
                    }
                else if (position == 2) {
                    countTimeLeftInMilestones = 90000;
                    countDownText.setText(arrayList.get(position));
                }
                else if (position == 3) {
                    countTimeLeftInMilestones = 120000;
                    countDownText.setText(arrayList.get(position));
                }
                else if (position == 4) {
                    countTimeLeftInMilestones = 150000;
                    countDownText.setText(arrayList.get(position));
                }
                else if (position == 5) {
                    countTimeLeftInMilestones = 180000;
                    countDownText.setText(arrayList.get(position));
                }
                else if (position == 6) {
                    countTimeLeftInMilestones = 210000;
                    countDownText.setText(arrayList.get(position));
                }
                else if (position == 7) {
                    countTimeLeftInMilestones = 240000;
                    countDownText.setText(arrayList.get(position));
                }
                else if (position == 8) {
                    countTimeLeftInMilestones = 270000;
                    countDownText.setText(arrayList.get(position));
                }
                else {
                    countTimeLeftInMilestones = 300000;
                    countDownText.setText(arrayList.get(position));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStop();
            }
        });
    }
    private void startStop() {
        if (timeRunning){
            stopTimer();
        }else{
            startTimer();
        }
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(countTimeLeftInMilestones,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                countTimeLeftInMilestones = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    NotificationChannel channel = new NotificationChannel("My Notification","My Notification",NotificationManager.IMPORTANCE_DEFAULT);
                    NotificationManager manager = getSystemService(NotificationManager.class);
                    manager.createNotificationChannel(channel);

                }

                NotificationCompat.Builder builder = new NotificationCompat.Builder(DoWorkout.this,"My Notification");
                builder.setContentTitle("Workout Finished");
                builder.setContentText("Get into the app and do more sets!");
                builder.setSmallIcon(R.drawable.logo);
                builder.setAutoCancel(true);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(DoWorkout.this);
                managerCompat.notify(1,builder.build());

                mediaPlayer.start();

            }
        }.start();

        btnGo.setText("PAUSE");
        timeRunning = true;
    }


    private void stopTimer() {
        countDownTimer.cancel();
        btnGo.setText("START");
        timeRunning = false;
    }
    private void updateTimer() {
        int minutes = (int) countTimeLeftInMilestones / 60000;
        int seconds = (int) countTimeLeftInMilestones % 60000 / 1000;
        String timeLeftText;
        timeLeftText = "" + minutes;
        timeLeftText += ":";
        if (seconds < 10) timeLeftText+= "0";
        timeLeftText += seconds;
        countDownText.setText(timeLeftText);
    }
}