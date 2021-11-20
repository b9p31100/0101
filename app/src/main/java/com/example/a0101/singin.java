package com.example.a0101;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//ユーザー登録画面
public class singin extends AppCompatActivity {
    //FirebaseAuth のプライベートメンバー変数を宣言
    private FirebaseAuth mAuth;
    private  EditText editText;
    private  EditText editText2;

    //ユーザー登録画面
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        editText = findViewById(R.id.email);
        editText2 = findViewById(R.id.password);
        Button btn = (Button)findViewById(R.id.button);
        mAuth = FirebaseAuth.getInstance(); //mAuth　インスタンスを初期化

        btn.setOnClickListener(v ->{
            createuser();
        });
    }

    private void createuser() {
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
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "createUserWithEmail:success");
                        Toast.makeText(singin.this, "Authentication 成功.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(singin.this, MainActivity.class));
                    }else{
                        Toast.makeText(singin.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }
}