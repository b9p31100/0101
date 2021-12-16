package com.example.a0101;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.util.CollectionUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class barcode extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private ArrayList<String> rellist, uculist, taboolist;
    private ArrayList<String> allergylist;
    private String rebarcode;
    private String jan, cop, product, material, allergy;
    private String pri, rel, all, ucu;
    private ArrayList<String> tab_li = taboolist;
    private ArrayList<String> all_li = allergylist;
    private ArrayList<String> ucu_li = uculist;
    private ArrayList<String> rel_li = rellist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hanteikextuka);
        //ZXingのインスタンス作成
        IntentIntegrator integrator = new IntentIntegrator(this);
        //横から縦向きに,ここからZxingの設定が行える
        // 詳細は→https://github.com/journeyapps/zxing-android-embedded
        integrator.setOrientationLocked(false);
        //ZXingの下部に書かれた文字の変更
        integrator.setPrompt("バーコードを読み込んでください");
        integrator.initiateScan();

        Button btn =(Button)findViewById(R.id.button5);
        btn.setOnClickListener(v ->{
            IntentIntegrator integrator2 = new IntentIntegrator(this);
            //横から縦向きに,ここからZxingの設定が行える
            // 詳細は→https://github.com/journeyapps/zxing-android-embedded
            integrator2.setOrientationLocked(false);
            //ZXingの下部に書かれた文字の変更
            integrator2.setPrompt("バーコードを読み込んでください");
            integrator2.initiateScan();
        });

        FirebaseUser user = mAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        allergylist = new ArrayList<>();
        taboolist =new ArrayList<>();
        uculist =new ArrayList<>();
        rellist =new ArrayList<>();


        final FirebaseDatabase database2 = FirebaseDatabase.getInstance();
        DatabaseReference ref2 = database2.getReference().child("user");
        DatabaseReference ref3 =database2.getReference().child("tboo2");
        DatabaseReference ref4 =database2.getReference().child("taboo");
        ref2.orderByKey().equalTo(uid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot2, String prevChildKey) {
                if(dataSnapshot2 != null) {
                    pri = (String) dataSnapshot2.child("principle").getValue();
                    rel = (String) dataSnapshot2.child("religion").getValue();
                    all = (String) dataSnapshot2.child("allergy").getValue();
                    ucu = (String) dataSnapshot2.child("ucustom").getValue();

                    all =all.replaceAll("-","");
                    if(all.isEmpty()){
                    }else{
                        allergylist = (ArrayList<String>) Stream.of(all.split("、")).collect(Collectors.toList());
                    }
                    if(ucu.isEmpty()){

                    }else{
                        uculist =(ArrayList<String>) Stream.of(ucu.split("、")).collect(Collectors.toList());
                    }
                    Log.w("DEBUG_DATA", "principle = " + pri);
                    Log.w("DEBUG_DATA", "religion = " + rel);
                    Log.w("DEBUG_DATA", "allergy = " + all);
                    Log.w("DEBUG_DATA", "ucustom = " + ucu);
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(barcode.this);
                    builder.setMessage("OCR判定画面に移動しますか？")
                            .setTitle("読み込んだバーコードはありませんでした")
                            .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    startActivity(new Intent(barcode.this, post.class));
                                }
                            });
                    builder.show();

                }

            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        ref3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String rel2 = "";
                if(pri.isEmpty()) {
                }else if(pri.equals("ビーガン")){
                    rel2 = (String) snapshot.child("ビーガン").getValue();
                }else{
                    rel2 = (String) snapshot.child("ベジタリアン").getValue();
                }
                Log.w("DEBUG_DATA", "religion = " + rel2);
                rellist =(ArrayList<String>) Stream.of(rel2.split("、")).collect(Collectors.toList());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ref4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String pri2 = "";
                if (rel.isEmpty()) {
                }else if(rel.equals("イスラム教")){
                    pri2 =(String)snapshot.child("イスラム教").getValue();
                }else if(rel.equals("カトリック")){
                    pri2 =(String) snapshot.child("カトリック").getValue();
                }else if(rel.equals("ヒンドゥー教")){
                    pri2 =(String) snapshot.child("ヒンドゥー教").getValue();
                }else if(rel.equals("モルモン教")){
                    pri2 =(String) snapshot.child("モルモン教").getValue();
                }else if(rel.equals("ユダヤ教")){
                    pri2 =(String) snapshot.child("ユダヤ教").getValue();
                }else if(rel.equals("大乗仏教")){
                    pri2=(String) snapshot.child("大乗仏教").getValue();
                }else if (rel.equals("正教会")){
                    pri2=(String) snapshot.child("正教会").getValue();
                }else{
                    pri2=(String) snapshot.child("観音信仰").getValue();
                }
                taboolist =(ArrayList<String>) Stream.of(pri2.split("、")).collect(Collectors.toList());
                Log.w("DEBUG_DATA", "principle = " + pri2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        TextView textView = findViewById(R.id.code);
        TextView textView2 =(TextView)findViewById(R.id.textView10);
        TextView textView3 =(TextView)findViewById(R.id.textView11);


        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();

            } else {
                rebarcode = result.getContents().toString();
                Toast.makeText(this, "Scanned: " + rebarcode, Toast.LENGTH_LONG).show();
                //読み取った値がresult.getContents()になる
                textView.setText(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("read");
        ref.orderByChild("JANコード").equalTo(rebarcode).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String prevChildKey) {
                if(dataSnapshot != null) {
                    jan = (String) dataSnapshot.child("JANコード").getValue();
                    cop = (String) dataSnapshot.child("会社名").getValue();
                    product = (String) dataSnapshot.child("商品名").getValue();
                    material = (String) dataSnapshot.child("原材料").getValue();
                    allergy = (String) dataSnapshot.child("アレルギー").getValue();

                    Log.w("DEBUG_DATA", "JANコード = " + jan);
                    Log.w("DEBUG_DATA", "会社名 = " + cop);
                    Log.w("DEBUG_DATA", "商品名 = " + product);
                    Log.w("DEBUG_DATA", "原材料 = " + material);
                    Log.w("DEBUG_DATA", "アレルギー = " + allergy);
                }else{
                    //OCRに飛ぶような処理
                }

                if(CollectionUtils.isEmpty(tab_li)){
                    textView2.setText("食べられます");
                }else {
                    String cheack;
                    if (ucu.isEmpty()) {
                    } else {
                        for (int j = 0; j < ucu_li.size(); j++) {
                            cheack = ucu_li.get(j);
                            tab_li.add(cheack);
                        }
                    }

                    for (int j = 0; j < rel_li.size(); j++) {
                        cheack = rel_li.get(j);
                        tab_li.add(cheack);
                    }
                    for (int i = 0; i < tab_li.size(); i++) {
                        cheack = tab_li.get(i);
                        Log.d("a", cheack);
                        if (cheack.matches(cheack)) {
                            textView2.setText("食べられません");
                            break;
                        } else {
                            textView2.setText("食べられます");
                        }
                    }
                }

                if(CollectionUtils.isEmpty(all_li)) {
                    textView3.setText("ないです");
                }else {
                    for (int i = 0; i < all_li.size(); i++) {
                        String cheack2 = all_li.get(i);
                        if (cheack2.matches(cheack2)) {
                            textView3.setText("ないです");
                            break;
                        } else {
                            textView3.setText("あります");
                        }
                    }
                }


            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }
}