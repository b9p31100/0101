package com.example.a0101;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText editText;
    private  EditText editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editText = findViewById(R.id.email);
        editText2 = findViewById(R.id.password);
        Button btn = (Button)findViewById(R.id.button);
        mAuth = FirebaseAuth.getInstance();

        btn.setOnClickListener(v->{
            loginuser();
        });
    }

    private void loginuser() {
        String email = editText.getText().toString();
        String password = editText2.getText().toString();

        if(TextUtils.isEmpty(email)){
            editText.setError("メールアドレスが未入力です");
            editText.requestFocus();
        }
        else if(TextUtils.isEmpty(password)){
            editText2.setError("パスワードが未入力です");
            editText2.requestFocus();
        }
        else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Toast.makeText(login.this, "ログイン成功.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(login.this, home.class));
                }else{
                    Toast.makeText(login.this, "ログイン失敗.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}