package com.example.a0101;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

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

        btn.setOnClickListener(v ->{
            mAuth.signOut();
            startActivity(new Intent(setting.this, MainActivity.class));
        });
    }
}