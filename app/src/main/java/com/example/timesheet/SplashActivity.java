package com.example.timesheet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.transition.AutoTransition;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.animation.AccelerateDecelerateInterpolator;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler preTransitionHandler = new Handler();
        preTransitionHandler.postDelayed(new Runnable() {
            public void run() {
                ConstraintLayout SplashScreen = findViewById(R.id.SplashScreen);
                ConstraintSet constraintSet = new ConstraintSet();

                constraintSet.clone(SplashScreen);
                constraintSet.setGuidelinePercent(R.id.guidelineSplashImageTop, 0.0f);
                constraintSet.setGuidelinePercent(R.id.guidelineSplashImageBottom, 0.25f);

                Transition transition = new AutoTransition();
                transition.setDuration(1500);
                transition.setInterpolator(new AccelerateDecelerateInterpolator());

                TransitionManager.beginDelayedTransition(SplashScreen, transition);
                constraintSet.applyTo(SplashScreen);
                Handler postTransitionHandler = new Handler();
                postTransitionHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(SplashActivity.this, SignInActivity.class);
                        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(SplashActivity.this, findViewById(R.id.SplashImage), "Splash");
                        startActivity(intent, options.toBundle());
                    }
                }, 2000);
            }
        }, 1500);

    }
}
