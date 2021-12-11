package com.example.a0101;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class qcrkeka extends AppCompatActivity {
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qcrkeka);
        EditText editText = (EditText)findViewById(R.id.Edittext);
        ImageButton imageButton =(ImageButton)findViewById(R.id.imagebutton1);

        imageButton.setOnClickListener(v ->{
            startActivity(new Intent(qcrkeka.this,post.class));
        });

        Intent intentMain = getIntent();
        result = intentMain.getStringExtra("data");
        editText.setText(result);

    }
}