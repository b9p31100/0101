package com.example.a0101;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class qcrkeka extends AppCompatActivity {
    private String result,result2;
    private Uri reUri;
    static final int RESULT_SUBACTIVITY = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qcrkeka);

        EditText editText = (EditText)findViewById(R.id.Edittext);
        Button button =(Button)findViewById(R.id.button);
        ImageButton imageButton =(ImageButton)findViewById(R.id.imagebutton1);


        Intent intentMain = getIntent();
        result = intentMain.getStringExtra("data");
        editText.setText(result);
        reUri =intentMain.getParcelableExtra("uri");

        imageButton.setOnClickListener(v ->{
            Intent intentre = new Intent();
            intentre.putExtra("reuri",reUri);
            setResult(RESULT_OK, intentre);
            finish();
        });

        button.setOnClickListener(v ->{
            Intent intent = new Intent(getApplication(), hanteiocr.class);
            intent.putExtra("ocrre", result);
            startActivity(intent);
        });

    }

    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if(resultCode == RESULT_OK &&  requestCode == RESULT_SUBACTIVITY &&     null != intent) {
            result =intent.getStringExtra("rest");
        }
    }

}