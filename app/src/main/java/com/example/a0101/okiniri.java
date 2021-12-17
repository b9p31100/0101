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
        ImageButton btn6 = (ImageButton) findViewById(R.id.imageButton1);
        ImageButton btn7 = (ImageButton) findViewById(R.id.imageButton2);
        ImageButton btn8 = (ImageButton) findViewById(R.id.imageButton3);
        ImageButton btn9 = (ImageButton) findViewById(R.id.imageButton4);
        mAuth = FirebaseAuth.getInstance();
        //Button btn = (Button) findViewById(R.id.homebutton1);//return_buttonのところをボタンのid名に変える
        btn6.setOnClickListener(v -> {
            startActivity(new Intent(okiniri.this, home.class));
        });
        btn7.setOnClickListener(v -> {
            startActivity(new Intent(okiniri.this, setting.class));
        });
        btn8.setOnClickListener(v -> {
            startActivity(new Intent(okiniri.this, option.class));
        });
        btn9.setOnClickListener(v -> {
            startActivity(new Intent(okiniri.this, profiletregister.class));
        });
    }

}