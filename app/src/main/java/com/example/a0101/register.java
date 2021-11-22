package com.example.a0101;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.util.CollectionUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private final CheckBox[] checkBox = new CheckBox[3];
    private final String[] str = {"卵","乳","小麦","そば","落花生","えび","かに","アーモンド",
            "あわび","いか","いくら","オレンジ","カシューナッツ","キウイフルーツ","牛肉","くるみ",
            "ごま","さけ","さば","大豆","鶏肉","バナナ","豚肉","まつたけ","もも","やまいも","りんご","ゼラチン"
    };
    private ArrayList<String> array = new ArrayList<>();
    private StringBuilder allergy = new StringBuilder();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView textView =findViewById(R.id.textView4);
        TextView textView2 =findViewById(R.id.textView5);
        TextView textView3 =findViewById(R.id.textView6);
        TextView textView4 =findViewById(R.id.textView7);
        Button btn =(Button)findViewById(R.id.button);
        FirebaseUser user = mAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        //宗教のプルダウンリスト作成
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(register.this, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.add("なし");
        adapter.add("正教会");
        adapter.add("カトリック");
        adapter.add("イスラム教");
        adapter.add("大乗仏教");
        adapter.add("観音信仰");
        adapter.add("ユダヤ教");
        adapter.add("ヒンドゥー教");
        adapter.add("モルモン教");
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        //主義(ビーガンなど)のプルダウンリスト作成
        ArrayAdapter<String> adapter2 =new ArrayAdapter<String>(register.this, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.add("なし");
        adapter2.add("ビーガン");
        adapter2.add("ベジタリアン");
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner2.setAdapter(adapter2);

        checkBox[0] = findViewById(R.id.checkBox);
        checkBox[0].setChecked(false);
        checkBox[0].setText(str[0]);

        checkBox[0].setOnClickListener( v -> {
                    // チェックステータス取得
                    boolean check = checkBox[0].isChecked();
                    if (check) {
                        array.add(str[0]);
                    }else{
                        array.remove(str[0]);
                    }
        });

        checkBox[1] = findViewById(R.id.checkBox2);
        checkBox[1].setChecked(false);
        checkBox[1].setText(str[1]);
        checkBox[1].setOnClickListener( v -> {
            // チェックステータス取得
            boolean check = checkBox[1].isChecked();
            if (check) {
                array.add(str[1]);
            }else{
                array.remove(str[1]);
            }
        });

        checkBox[2] = findViewById(R.id.checkBox3);
        checkBox[2].setChecked(false);
        checkBox[2].setText(str[2]);
        checkBox[2].setOnClickListener( v -> {
            // チェックステータス取得
            boolean check = checkBox[2].isChecked();
            if (check) {
                array.add(str[2]);
            }else{
                array.remove(str[2]);
            }
        });

        btn.setOnClickListener(v ->{
            String select = (String) spinner.getSelectedItem();
            textView.setText(select);
            String select2 = (String) spinner2.getSelectedItem();
            textView2.setText(select2);
            if (CollectionUtils.isEmpty(array)) {
                String none ="ないよ";
                textView3.setText(none);
            }else {
                for (int i = 0; i < array.size(); i++) {
                    allergy.append(array.get(i));
                    allergy.append(",");
                }
                    textView3.setText(allergy);
                    allergy.setLength(0);
            }
            textView4.setText(uid);

        });


    }
}