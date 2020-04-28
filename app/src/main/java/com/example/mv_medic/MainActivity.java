package com.example.mv_medic;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ImageView img, img1;
    private TextView txt;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        img = (ImageView) findViewById(R.id.logo);
        img1 = (ImageView) findViewById(R.id.imageView3);
        txt = (TextView) findViewById(R.id.textmain);
        Animation vecl = (Animation) AnimationUtils.loadAnimation(MainActivity.this, R.anim.mytransition);
        img.startAnimation(vecl);
        img1.startAnimation(vecl);
        txt.startAnimation(vecl);
        final Intent in = new Intent(MainActivity.this, Home_Activity.class);

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(in);
                    finish();
                }

            }
        };
        timer.start();


    }
}
