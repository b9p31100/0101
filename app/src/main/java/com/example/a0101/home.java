package com.example.a0101;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class home extends AppCompatActivity {
    private FirebaseAuth mAuth;
    //ホーム画面
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);  //activity_homeを遷移先のページの名前に変える(xml)
        //変数はこのファイルだけなので、「btn」と置いても、別のjavaファイルで「btn」でも問題ないです
        Button btn = (Button) findViewById(R.id.return_button);//return_buttonのところをボタンのid名に変える
        Button btn2 =(Button)findViewById(R.id.imageButton4);
        Button btn3 =(Button)findViewById(R.id.button3);
        mAuth = FirebaseAuth.getInstance();

        //ラムダ式の方が簡単に書けます
        btn.setOnClickListener(v -> {
            mAuth.signOut();
            startActivity(new Intent(home.this, MainActivity.class));
        });

        btn2.setOnClickListener(v ->{
            Intent intent = new Intent(home.this, //homeを遷移先のページの名前に変える(java)
                    okiniri.class );
            //MainActivity(メイン画面に遷移)
            startActivity(intent);
        });
        btn3.setOnClickListener(v ->{
            Intent intent = new Intent(home.this, //homeを遷移先のページの名前に変える(java)
                    register.class );
            //MainActivity(メイン画面に遷移)
            startActivity(intent);
        });
    }
}