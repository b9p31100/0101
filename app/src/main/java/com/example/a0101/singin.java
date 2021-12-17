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
import android.widget.ImageButton;
import android.widget.Toast;

//ユーザー登録画面
public class singin extends AppCompatActivity {
    //FirebaseAuth のプライベートメンバー変数を宣言
    private FirebaseAuth mAuth;
    private  EditText editText;
    private  EditText editText2;

    //ユーザー登録画面(メアドとパスワード)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        editText = findViewById(R.id.email);
        editText2 = findViewById(R.id.password);
        Button btn = (Button)findViewById(R.id.button);
        ImageButton btn6 = (ImageButton) findViewById(R.id.imageButton1);
        ImageButton btn7 = (ImageButton) findViewById(R.id.imageButton2);
        ImageButton btn8 = (ImageButton) findViewById(R.id.imageButton3);
        ImageButton btn9 = (ImageButton) findViewById(R.id.imageButton4);
        mAuth = FirebaseAuth.getInstance(); //mAuth　インスタンスを初期化


            //ログインボタンを押した時の処理

            //Button btn = (Button) findViewById(R.id.homebutton1);//return_buttonのところをボタンのid名に変える
            btn6.setOnClickListener(v -> {
                startActivity(new Intent(singin.this, home.class));
            });
            btn7.setOnClickListener(v -> {
                startActivity(new Intent(singin.this, setting.class));
            });
            btn8.setOnClickListener(v -> {
                startActivity(new Intent(singin.this, option.class));
            });
            btn9.setOnClickListener(v -> {
                startActivity(new Intent(singin.this, profiletregister.class));
            });
            createuser();
        };


    private void createuser() {
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
            //入力されていたら、Authenticationにメアドとパスワードを追加す処理
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        //taskが成功=追加できた時の処理
                        Toast.makeText(singin.this, "Authentication 成功.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(singin.this, register.class));
                    }else{
                        //追加できなかった時の処理
                        Toast.makeText(singin.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }
}