package com.example.a0101;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.util.CollectionUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private final CheckBox[] checkBox = new CheckBox[21];
    //アレルギー(特定原材料の７品目+特定原材料に準ずるもの21品目）
    //https://www.food-allergy.jp/info/label_1/
    private final String[] str = {"卵","乳","小麦","そば","落花生","えび","かに","アーモンド",
            "あわび","いか","いくら","オレンジ","カシューナッツ","キウイフルーツ","牛肉","くるみ",
            "ごま","さけ","さば","大豆","鶏肉","バナナ","豚肉","まつたけ","もも","やまいも","りんご","ゼラチン"
    };
    private ArrayList<String> array = new ArrayList<>();
    private StringBuilder allergen = new StringBuilder();
    private String regular = "(?:[-,.、・^＾~～？:：；;\\\\<>＜＞「」｛｝#＃\\\"”\\[|=＝$＄%％\\s*n]|\\\\/)";

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

        checkBox[3] = findViewById(R.id.checkBox4);
        checkBox[3].setChecked(false);
        checkBox[3].setText(str[3]);
        checkBox[3].setOnClickListener( v -> {
            // チェックステータス取得
            boolean check = checkBox[3].isChecked();
            if (check) {
                array.add(str[3]);
            }else{
                array.remove(str[3]);
            }
        });

        checkBox[4] = findViewById(R.id.checkBox5);
        checkBox[4].setChecked(false);
        checkBox[4].setText(str[4]);
        checkBox[4].setOnClickListener( v -> {
            // チェックステータス取得
            boolean check = checkBox[4].isChecked();
            if (check) {
                array.add(str[4]);
            }else{
                array.remove(str[4]);
            }
        });

        checkBox[5] = findViewById(R.id.checkBox6);
        checkBox[5].setChecked(false);
        checkBox[5].setText(str[5]);
        checkBox[5].setOnClickListener( v -> {
            // チェックステータス取得
            boolean check = checkBox[5].isChecked();
            if (check) {
                array.add(str[5]);
            }else{
                array.remove(str[5]);
            }
        });

        checkBox[6] = findViewById(R.id.checkBox7);
        checkBox[6].setChecked(false);
        checkBox[6].setText(str[6]);
        checkBox[6].setOnClickListener( v -> {
            // チェックステータス取得
            boolean check = checkBox[6].isChecked();
            if (check) {
                array.add(str[6]);
            }else{
                array.remove(str[6]);
            }
        });

        checkBox[7] = findViewById(R.id.checkBox8);
        checkBox[7].setChecked(false);
        checkBox[7].setText(str[7]);
        checkBox[7].setOnClickListener( v -> {
            // チェックステータス取得
            boolean check = checkBox[7].isChecked();
            if (check) {
                array.add(str[7]);
            }else{
                array.remove(str[7]);
            }
        });
        checkBox[8] = findViewById(R.id.checkBox9);
        checkBox[8].setChecked(false);
        checkBox[8].setText(str[8]);
        checkBox[8].setOnClickListener( v -> {
            // チェックステータス取得
            boolean check = checkBox[8].isChecked();
            if (check) {
                array.add(str[8]);
            }else{
                array.remove(str[8]);
            }
        });

        checkBox[9] = findViewById(R.id.checkBox10);
        checkBox[9].setChecked(false);
        checkBox[9].setText(str[9]);
        checkBox[9].setOnClickListener( v -> {
            // チェックステータス取得
            boolean check = checkBox[9].isChecked();
            if (check) {
                array.add(str[9]);
            }else{
                array.remove(str[9]);
            }
        });

        checkBox[10] = findViewById(R.id.checkBox11);
        checkBox[10].setChecked(false);
        checkBox[10].setText(str[10]);
        checkBox[10].setOnClickListener( v -> {
            // チェックステータス取得
            boolean check = checkBox[10].isChecked();
            if (check) {
                array.add(str[10]);
            }else{
                array.remove(str[10]);
            }
        });

        checkBox[11] = findViewById(R.id.checkBox12);
        checkBox[11].setChecked(false);
        checkBox[11].setText(str[11]);
        checkBox[11].setOnClickListener( v -> {
            // チェックステータス取得
            boolean check = checkBox[11].isChecked();
            if (check) {
                array.add(str[11]);
            }else{
                array.remove(str[11]);
            }
        });

        checkBox[12] = findViewById(R.id.checkBox13);
        checkBox[12].setChecked(false);
        checkBox[12].setText(str[12]);
        checkBox[12].setOnClickListener( v -> {
            // チェックステータス取得
            boolean check = checkBox[13].isChecked();
            if (check) {
                array.add(str[13]);
            }else{
                array.remove(str[13]);
            }
        });

        checkBox[13] = findViewById(R.id.checkBox14);
        checkBox[13].setChecked(false);
        checkBox[13].setText(str[13]);
        checkBox[13].setOnClickListener( v -> {
            // チェックステータス取得
            boolean check = checkBox[13].isChecked();
            if (check) {
                array.add(str[13]);
            }else{
                array.remove(str[13]);
            }
        });

        checkBox[14] = findViewById(R.id.checkBox15);
        checkBox[14].setChecked(false);
        checkBox[14].setText(str[14]);
        checkBox[14].setOnClickListener( v -> {
            // チェックステータス取得
            boolean check = checkBox[14].isChecked();
            if (check) {
                array.add(str[14]);
            }else{
                array.remove(str[14]);
            }
        });

        checkBox[15] = findViewById(R.id.checkBox16);
        checkBox[15].setChecked(false);
        checkBox[15].setText(str[15]);
        checkBox[15].setOnClickListener( v -> {
            // チェックステータス取得
            boolean check = checkBox[15].isChecked();
            if (check) {
                array.add(str[15]);
            }else{
                array.remove(str[15]);
            }
        });

        checkBox[16] = findViewById(R.id.checkBox17);
        checkBox[16].setChecked(false);
        checkBox[16].setText(str[16]);
        checkBox[16].setOnClickListener( v -> {
            // チェックステータス取得
            boolean check = checkBox[16].isChecked();
            if (check) {
                array.add(str[16]);
            }else{
                array.remove(str[16]);
            }
        });

        checkBox[17] = findViewById(R.id.checkBox18);
        checkBox[17].setChecked(false);
        checkBox[17].setText(str[17]);
        checkBox[17].setOnClickListener( v -> {
            // チェックステータス取得
            boolean check = checkBox[17].isChecked();
            if (check) {
                array.add(str[17]);
            }else{
                array.remove(str[17]);
            }
        });

        checkBox[18] = findViewById(R.id.checkBox19);
        checkBox[18].setChecked(false);
        checkBox[18].setText(str[18]);
        checkBox[18].setOnClickListener( v -> {
            // チェックステータス取得
            boolean check = checkBox[18].isChecked();
            if (check) {
                array.add(str[18]);
            }else{
                array.remove(str[18]);
            }
        });

        checkBox[19] = findViewById(R.id.checkBox20);
        checkBox[19].setChecked(false);
        checkBox[19].setText(str[19]);
        checkBox[19].setOnClickListener( v -> {
            // チェックステータス取得
            boolean check = checkBox[19].isChecked();
            if (check) {
                array.add(str[19]);
            }else{
                array.remove(str[19]);
            }
        });

        checkBox[20] = findViewById(R.id.checkBox21);
        checkBox[20].setChecked(false);
        checkBox[20].setText(str[20]);
        checkBox[20].setOnClickListener( v -> {
            // チェックステータス取得
            boolean check = checkBox[20].isChecked();
            if (check) {
                array.add(str[20]);
            }else{
                array.remove(str[20]);
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
            String ucustom = custom.replaceAll(regular,",").replaceAll("　", ",").replaceAll(" ", ",");
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