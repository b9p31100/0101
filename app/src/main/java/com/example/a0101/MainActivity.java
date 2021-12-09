package com.example.a0101;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    //ログイン画面　
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        Button btn = (Button) findViewById(R.id.send_button);

        Button btn2 = (Button)findViewById(R.id.send_button2);
        btn.setOnClickListener(v ->{
                Intent intent = new Intent(MainActivity.this,
                        login.class );
                startActivity(intent);
        });

        btn2.setOnClickListener(v ->{
            Intent intent = new Intent(MainActivity.this,
                    singin.class);
            startActivity(intent);
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user == null){
            Toast.makeText(MainActivity.this, "ログインしてください", Toast.LENGTH_SHORT).show();
        }else{
            startActivity(new Intent(MainActivity.this, home.class));
            finish();
        }
    }
}
//a