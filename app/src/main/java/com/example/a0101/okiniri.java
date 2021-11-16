package com.example.a0101;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class okiniri extends AppCompatActivity {
    //お気に入り画面
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okiniri);  //activity_homeを遷移先のページの名前に変える(xml)

        Button btn = (Button) findViewById(R.id.homebutton1);//return_buttonのところをボタンのid名に変える

        btn.setOnClickListener(v-> {
                Intent intent = new Intent(okiniri.this, //homeを遷移先のページの名前に変える(java)
                        MainActivity.class );
                startActivity(intent);
                finish(); // アクティビティ終了
        });
    }
}