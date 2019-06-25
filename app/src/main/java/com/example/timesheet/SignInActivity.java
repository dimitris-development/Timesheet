package com.example.timesheet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }


    public void SignIn(View v){
        Intent intent = new Intent(SignInActivity.this, TimesheetSubmissionFormActivity.class);
        startActivity(intent);
    }

    public void SignUp(View v){
        Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(SignInActivity.this, findViewById(R.id.SplashImage), "Splash");
        startActivity(intent, options.toBundle());
    }
}
