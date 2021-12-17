package com.example.a0101;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class qcrkeka extends AppCompatActivity {
    private String result,result2;
    private Uri reUri;
    private FirebaseAuth mAuth;
    static final int RESULT_SUBACTIVITY = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qcrkeka);

        EditText editText = (EditText)findViewById(R.id.Edittext);
        Button button =(Button)findViewById(R.id.button);
        ImageButton imageButton =(ImageButton)findViewById(R.id.imagebutton1);
        ImageButton btn8 = (ImageButton) findViewById(R.id.imageButton5);
        ImageButton btn9 =(ImageButton)findViewById(R.id.imageButton2);
        ImageButton btn10 = (ImageButton) findViewById(R.id.imageButton3);
        ImageButton btn11 =(ImageButton)findViewById(R.id.imageButton4);


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
        btn8.setOnClickListener(v -> {
            startActivity(new Intent(qcrkeka.this, //homeを遷移先のページの名前に変える(java)
                    home.class));
        });
        btn9.setOnClickListener(v -> {
            startActivity(new Intent(qcrkeka.this, //homを遷移先のページの名前に変える(java)
                    setting.class));
        });
        btn10.setOnClickListener(v -> {
            startActivity(new Intent(qcrkeka.this, //homeを遷移先のページの名前に変える(java)
                    option.class));
        });
        btn11.setOnClickListener(v -> {
            startActivity(new Intent(qcrkeka.this, //homeを遷移先のページの名前に変える(java)
                    profiletregister.class));
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