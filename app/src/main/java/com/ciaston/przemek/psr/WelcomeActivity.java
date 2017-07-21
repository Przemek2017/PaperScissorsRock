package com.ciaston.przemek.psr;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WelcomeActivity extends AppCompatActivity {
    final static int SPLASH_TIME = 3000;
    @BindView(R.id.zoomImage) ImageView zoomImage;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        animation = AnimationUtils.loadAnimation(this, R.anim.zoom);
        zoomImage.startAnimation(animation);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent intent = new Intent(WelcomeActivity.this, PaperScissorsRockActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME);
    }
}