package com.example.a0101;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class okiniri extends AppCompatActivity {
    private FirebaseAuth mAuth;
    //お気に入り画面
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okiniri);  //activity_homeを遷移先のページの名前に変える(xml)
        mAuth = FirebaseAuth.getInstance();
        //Button btn = (Button) findViewById(R.id.homebutton1);//return_buttonのところをボタンのid名に変える
    }

}