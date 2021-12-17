package com.example.a0101;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    //FirebaseAuth のプライベートメンバー変数を宣言
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
        mAuth = FirebaseAuth.getInstance(); //mAuth　インスタンスを初期化

        btn.setOnClickListener(v->{
            //loginuserメソッドへ
            loginuser();
        });
    }

    //ログイン時の処理
    private void loginuser() {
        //メアドとパスワードを取得
        String email = editText.getText().toString();
        String password = editText2.getText().toString();
        //メアドとパスワードが空白でないか確認
        if(TextUtils.isEmpty(email)){
            editText.setError("メールアドレスが未入力です");
            editText.requestFocus();
        }
        else if(TextUtils.isEmpty(password)){
            editText2.setError("パスワードが未入力です");
            editText2.requestFocus();
        }
        else{
            //入力されていたら、Authenticationにメアドとパスワードが登録されているか確認
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(login.this, "ログイン成功.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(login.this, home.class));
                    } else {
                        Toast.makeText(login.this, "ログイン失敗.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}