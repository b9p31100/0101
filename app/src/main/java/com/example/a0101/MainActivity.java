package com.example.a0101;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    //ログイン画面　
    //MainActivityはhomeではないので注意(自分(増田)への戒めなので、デザイン班はスルーしてください)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //res/layout/screen1.xml を初期画面に
        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.send_button);
        Button btn2 =(Button)findViewById(R.id.send_button2);
        btn.setOnClickListener(v ->{
                Intent intent = new Intent(MainActivity.this,
                        home.class );
                startActivity(intent);
        });

        btn2.setOnClickListener(v ->{
            Intent intent = new Intent(MainActivity.this,
                    userregister.class);
            startActivity(intent);
        });

    }
}