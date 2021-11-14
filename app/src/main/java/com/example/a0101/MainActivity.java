package com.example.a0101;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // res/layout/screen1.xml を初期画面に
        setContentView(R.layout.activity_main);

        Button btn2 = (Button) findViewById(R.id.send_button);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        home.class );
                startActivity(intent);
            }
        });

    }
}