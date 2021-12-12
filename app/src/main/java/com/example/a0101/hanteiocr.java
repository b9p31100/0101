package com.example.a0101;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class hanteiocr extends AppCompatActivity {
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hanteiocr);

        ImageButton imageButton =(ImageButton)findViewById(R.id.imagebutton1);
        imageButton.setOnClickListener(v ->{
            Intent intent = new Intent(getApplication(), qcrkeka.class);
            startActivity(intent);
        });


    }
}