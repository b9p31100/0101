package com.example.a0101;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
    private ArrayList<String> rellist,uculist,allergylist,taboolist,proallergy;
    private String rebarcode;
    private String jan, cop, product, material, allergy;
    private String pri, rel, all, ucu;
    private boolean flg = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);
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
        proallergy = new ArrayList<>();


        final FirebaseDatabase database2 = FirebaseDatabase.getInstance();
        DatabaseReference ref2 = database2.getReference().child("user");
        DatabaseReference ref3 =database2.getReference().child("tboo2");
        DatabaseReference ref4 =database2.getReference().child("taboo");
        ref2.orderByKey().equalTo(uid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot2, String prevChildKey) {
                pri = (String) dataSnapshot2.child("principle").getValue();
                rel = (String) dataSnapshot2.child("religion").getValue();
                all = (String) dataSnapshot2.child("allergy").getValue();
                ucu = (String) dataSnapshot2.child("ucustom").getValue();

                if(all.equals("なし")){ }else{
                    allergylist = (ArrayList<String>) Stream.of(all.split("、")).collect(Collectors.toList());
                }
                if(ucu.isEmpty()){ }else{
                    uculist =(ArrayList<String>) Stream.of(ucu.split("、")).collect(Collectors.toList());
                }
                Log.w("DEBUG_DATA", "principle = " + pri);
                Log.w("DEBUG_DATA", "religion = " + rel);
                Log.w("DEBUG_DATA", "allergy = " + all);
                Log.w("DEBUG_DATA", "ucustom = " + ucu);

                ref3.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String rel2 = "";
                        if(pri.equals("なし")) {
                        }else if(pri.equals("ビーガン")){
                            rel2 = (String) snapshot.child("ビーガン").getValue();
                        }else{
                            rel2 = (String) snapshot.child("ベジタリアン").getValue();
                        }
                        if(pri.equals("なし")){}else{
                            Log.w("DEBUG_DATA", "religion = " + rel2);
                            rellist =(ArrayList<String>) Stream.of(rel2.split("、")).collect(Collectors.toList());
                        }
                        }@Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        }
                });
                ref4.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String pri2 = "";
                        if (rel.equals("なし")) {
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
                        if(rel.equals("なし")){}else{
                            taboolist =(ArrayList<String>) Stream.of(pri2.split("、")).collect(Collectors.toList());
                            Log.w("DEBUG_DATA", "principle = " + pri2);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        }
                });


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        TextView textView = findViewById(R.id.textView2);
        TextView textView2 =findViewById(R.id.textView6);
        TextView textView3 =findViewById(R.id.textView3);
        TextView textView4 =findViewById(R.id.textView4);
        TextView textView5 =findViewById(R.id.textView10);
        TextView textView6 =findViewById(R.id.textView7);


        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();

            } else {
                rebarcode = result.getContents().toString();
                //読み取った値がresult.getContents()になる
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("read");
        ref.orderByChild("JANコード").equalTo(rebarcode).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String prevChildKey) {
                if(dataSnapshot!=null) {
                    jan = (String) dataSnapshot.child("JANコード").getValue();
                    cop = (String) dataSnapshot.child("会社名").getValue();
                    product = (String) dataSnapshot.child("商品名").getValue();
                    material = (String) dataSnapshot.child("原材料").getValue();
                    allergy = (String) dataSnapshot.child("アレルギー").getValue();
                    allergy =allergy.replaceAll("-","");
                    if(allergy.isEmpty()){}else{
                        proallergy =(ArrayList<String>) Stream.of(allergy.split("、")).collect(Collectors.toList());
                    }
                }

                    Log.w("DEBUG_DATA", "JANコード = " + jan);
                    Log.w("DEBUG_DATA", "会社名 = " + cop);
                    Log.w("DEBUG_DATA", "商品名 = " + product);
                    Log.w("DEBUG_DATA", "原材料 = " + material);
                    Log.w("DEBUG_DATA", "アレルギー = " + allergy);

                    textView3.setText("会社名"+cop);
                    textView4.setText("商品名:"+product);
                    textView5.setText("原材料:"+material);
                    textView5.setMovementMethod(new ScrollingMovementMethod());
                    textView6.setText("アレルギー物質:"+allergy);


                String cheack;
                String cheack2;
                String cheack3;
                if(CollectionUtils.isEmpty(taboolist) && CollectionUtils.isEmpty(uculist)){
                    textView.setText("食べられます");
                }else{
                    if(CollectionUtils.isEmpty(uculist)){ }else{
                        for(int j =0; j<uculist.size();j++){
                            cheack =uculist.get(j);
                            taboolist.add(cheack);
                        }
                    }
                    if(CollectionUtils.isEmpty(rellist)) {}else{
                        for (int j = 0; j < rellist.size(); j++) {
                            cheack = rellist.get(j);
                            taboolist.add(cheack);
                        }
                    }
                    if(CollectionUtils.isEmpty(taboolist)) {}else{
                        for (int i = 0; i < taboolist.size(); i++) {
                            cheack = taboolist.get(i);
                            Log.d("a", cheack);

                            if ( material.matches(cheack)) {
                                textView.setText("食べられません");

                                break;
                            } else {
                                textView.setText("食べられます");
                            }
                        }
                    }
                }
                if(CollectionUtils.isEmpty(allergylist)||CollectionUtils.isEmpty(proallergy)) {
                    textView2.setText("含まれていません");
                }else {
                    for (int a = 0; a < proallergy.size(); a++) {
                        cheack2 =proallergy.get(a);
                        for (int b = 0; b < allergylist.size(); b++) {
                            cheack3 = allergylist.get(b);
                            if(flg){
                                break;
                            }
                            if (cheack2.matches(cheack3)) {
                                textView2.setText("含まれています");
                                Log.d("a", "はい");
                                flg =true;
                                break;
                            } else {
                                textView2.setText("含まれていません");
                                Log.d("a", "ここ");
                            }
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
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}