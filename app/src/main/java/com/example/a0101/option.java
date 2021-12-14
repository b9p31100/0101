package com.example.a0101;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class option extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        Button btn =(Button) findViewById(R.id.button_bako);
        Button btn2 =(Button)findViewById(R.id.button_ocr);

        btn.setOnClickListener(v->{
            startActivity(new Intent(option.this,barcode.class));
        });
        btn2.setOnClickListener(v->{
            startActivity(new Intent(option.this,post.class));
        });
    }
}