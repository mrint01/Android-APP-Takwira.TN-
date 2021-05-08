package com.example.takwiratn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

public class SplachActivity extends AppCompatActivity {

    Handler handler;
    ProgressBar progressBar5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splach);

        progressBar5 = findViewById(R.id.progressBar5);
        progressBar5.setVisibility(View.VISIBLE);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplachActivity.this,MainActivity.class));
                progressBar5.setVisibility(View.GONE);
                finish();
            }
        }, 10000);
    }
}