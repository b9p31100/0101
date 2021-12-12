package com.example.a0101;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
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
        Button button =(Button)findViewById(R.id.button);
        ImageButton imageButton =(ImageButton)findViewById(R.id.imagebutton1);


        imageButton.setOnClickListener(v ->{
            Intent intent = new Intent(getApplication(), post.class);
            startActivity(intent);
        });

        button.setOnClickListener(v ->{
            Intent intent = new Intent(getApplication(), hanteiocr.class);
            intent.putExtra("ocr", result);
            startActivity(intent);
        });

        Intent intentMain = getIntent();
        result = intentMain.getStringExtra("data");
        editText.setText(result);

    }

}