package com.example.a0101;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class home extends AppCompatActivity {
    private FirebaseAuth mAuth;
    //ホーム画面
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);  //activity_homeを遷移先のページの名前に変える(xml)
        //変数はこのファイルだけなので、「btn」と置いても、別のjavaファイルで「btn」でも問題ないです
        //Button btn = (Button) findViewById(R.id.return_button);//return_buttonのところをボタンのid名に変える
        Button btn2 =(Button) findViewById(R.id.button_okini);
        Button btn3 =(Button)findViewById(R.id.button_puro);
        Button btn4 =(Button)findViewById(R.id.button_ocr);
        mAuth = FirebaseAuth.getInstance();
        Button btn5 =(Button) findViewById(R.id.button2);

        //ラムダ式の方が簡単に書けます

        btn2.setOnClickListener(v ->{
            startActivity(new Intent(home.this, //homeを遷移先のページの名前に変える(java)
                    okiniri.class) );
            //お気に入り画面に遷移
        });
        btn3.setOnClickListener(v ->{
            startActivity(new Intent(home.this, //homeを遷移先のページの名前に変える(java)
                    register.class) );
            //ユーザー登録に遷移
        });
        //OCRに遷移
        btn4.setOnClickListener(v ->{
            startActivity(new Intent(home.this, post.class));

        });
        btn5.setOnClickListener(v ->{
            startActivity(new Intent(home.this, hanteikextuka.class));
                }
                );
    }
}