package com.example.your_contact;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        Handler h = new Handler();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), LogInPage.class));
                finish();
            }
        };
        h.postDelayed(r, 5000);
    }
}
