package com.soumayaguenaguen.climategazer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 3000; // in milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView logo = findViewById(R.id.splash_image);
        Animation logoAnimation = AnimationUtils.loadAnimation(this, R.anim.splash_logo_anim);
        logo.startAnimation(logoAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start your next activity
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);

                // Close the splash screen activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
