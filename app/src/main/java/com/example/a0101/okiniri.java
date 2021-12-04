package com.example.a0101;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class okiniri extends AppCompatActivity {
    //お気に入り画面
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okiniri);  //activity_homeを遷移先のページの名前に変える(xml)

        //Button btn = (Button) findViewById(R.id.homebutton1);//return_buttonのところをボタンのid名に変える
    }

}