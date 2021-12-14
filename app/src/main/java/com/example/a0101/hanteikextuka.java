package com.example.a0101;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class hanteikextuka extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_hanteikextuka);
            ImageButton btn1 = (ImageButton) findViewById(R.id.imageButton1);
            ImageButton btn2 = (ImageButton) findViewById(R.id.imageButton3);
            ImageButton btn3 = (ImageButton) findViewById(R.id.imageButton2);
            ImageButton btn4 = (ImageButton) findViewById(R.id.imageButton4);

            btn1.setOnClickListener(v -> {
                startActivity(new Intent(hanteikextuka.this, home.class));
            });
            btn2.setOnClickListener(v -> {
                startActivity(new Intent(hanteikextuka.this, option.class));
            });
            btn3.setOnClickListener(v -> {
                startActivity(new Intent(hanteikextuka.this, setting.class));
            });
            btn4.setOnClickListener(v -> {
                startActivity(new Intent(hanteikextuka.this, profiletregister.class));
            });
        }


        }

