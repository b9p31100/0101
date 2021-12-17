package com.example.a0101;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class option extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        Button btn =(Button) findViewById(R.id.button_bako);
        Button btn2 =(Button)findViewById(R.id.button_ocr);
        ImageButton btn3 =(ImageButton) findViewById(R.id.imageButton1);
        ImageButton btn4 =(ImageButton)findViewById(R.id.imageButton2);
        ImageButton btn5 =(ImageButton)findViewById(R.id.imageButton3);
        ImageButton btn6 =(ImageButton)findViewById(R.id.imageButton4);

        btn.setOnClickListener(v->{
            startActivity(new Intent(option.this,barcode.class));
        });
        btn2.setOnClickListener(v->{
            startActivity(new Intent(option.this,post.class));
        });
        btn3.setOnClickListener(v -> {
            startActivity(new Intent(option.this, home.class));
        });
        btn4.setOnClickListener(v -> {
            startActivity(new Intent(option.this, setting.class));
        });
        btn5.setOnClickListener(v -> {
            startActivity(new Intent(option.this, option.class));
        });
        btn6.setOnClickListener(v -> {
            startActivity(new Intent(option.this, profiletregister.class));
        });
    }
}