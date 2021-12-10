package com.example.a0101;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class hanteikextuka extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hanteikextuka);

        Animation animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.class)

    }
}