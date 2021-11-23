package com.example.a0101;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.util.CollectionUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private final CheckBox[] checkBox = new CheckBox[3];
    //アレルギー(特定原材料の７品目+特定原材料に準ずるもの21品目）
    //https://www.food-allergy.jp/info/label_1/
    private final String[] str = {"卵","乳","小麦","そば","落花生","えび","かに","アーモンド",
            "あわび","いか","いくら","オレンジ","カシューナッツ","キウイフルーツ","牛肉","くるみ",
            "ごま","さけ","さば","大豆","鶏肉","バナナ","豚肉","まつたけ","もも","やまいも","りんご","ゼラチン"
    };
    private ArrayList<String> array = new ArrayList<>();
    private StringBuilder allergen = new StringBuilder();
    private String regular = "(?:[-,.、・^\\n]|\\/)";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText editText =(EditText)findViewById(R.id.EditText);
        Button btn =(Button)findViewById(R.id.button);

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
                //品目を追加する
                array.add(str[1]);
            }else{
                //品目を削除する
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
            String allergy ="";
            FirebaseUser user = mAuth.getInstance().getCurrentUser();
            String uid = user.getUid();

            //宗教から選択されたテキスト
            String religion = (String) spinner.getSelectedItem();
            //主義から選択されたテキスト
            String principle = (String) spinner2.getSelectedItem();

            String custom = editText.getText().toString();
            String ucustom = custom.replaceAll(regular,",");
            //チェックリストのアレルギーを選択したどうかの判定(これが無いとエラーに)
            if (CollectionUtils.isEmpty(array)) {
                allergy = "なし";
            }else {
                for (int i = 0; i < array.size(); i++) {
                    allergen.append(array.get(i));
                    allergen.append(",");
                }
                //末尾に付いたカンマ(,)を消す処理
                allergen.deleteCharAt(allergen.length()-1);
                allergy = allergen.toString();
                //何度も選択するように、初期化する
                allergen.setLength(0);
            }
            mDatabase = FirebaseDatabase.getInstance().getReference("user");
            UserHelper helper = new UserHelper(religion,principle,ucustom,allergy,uid);
            mDatabase.child(uid).setValue(helper);
            Toast.makeText(register.this, "登録完了", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(register.this, home.class));

        });


    }

}