package com.example.a0101;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class setting extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mAuth = FirebaseAuth.getInstance();

        Button btn =(Button) findViewById(R.id.button);
        ImageButton btn3 = (ImageButton) findViewById(R.id.imageButton1);
        ImageButton btn4 = (ImageButton) findViewById(R.id.imageButton2);
        ImageButton btn5 = (ImageButton) findViewById(R.id.imageButton3);
        ImageButton btn6 = (ImageButton) findViewById(R.id.imageButton4);

        btn.setOnClickListener(v ->{
            mAuth.signOut();
            startActivity(new Intent(setting.this, MainActivity.class));
        });
        btn3.setOnClickListener(v -> {
            startActivity(new Intent(setting.this, home.class));
        });
        btn4.setOnClickListener(v -> {
            startActivity(new Intent(setting.this, setting.class));
        });
        btn5.setOnClickListener(v -> {
            startActivity(new Intent(setting.this, option.class));
        });
        btn6.setOnClickListener(v -> {
            startActivity(new Intent(setting.this, profiletregister.class));
        });
    }
}