package com.example.a0101;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class profiletregister extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiletregister);
        Button btn1 = (Button) findViewById(R.id.button7);

        btn1.setOnClickListener(v ->{
            startActivity(new Intent(profiletregister.this, register.class));
        });
    }
}