package com.example.a0101;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class hanteiocr extends AppCompatActivity {
    private String resul,rehan;
    private FirebaseAuth mAuth;
    private ArrayList<String> taboolist;
    private ArrayList<String> allergylist ;
    private FirebaseDatabase database;
    private String bubun ="^.";
    private String bubun2=".$";
    private String hantei="";
    private int c=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hanteiocr);

        TextView textView =(TextView)findViewById(R.id.textView10);

        Intent intentMain = getIntent();
        resul = intentMain.getStringExtra("ocrre");
        Log.d("a",resul);
        FirebaseUser user = mAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        allergylist = new ArrayList<>();
        taboolist =new ArrayList<>();

        ImageButton imageButton =(ImageButton)findViewById(R.id.imagebutton1);
        imageButton.setOnClickListener(v ->{
            String reture =resul;
            Intent intentre = new Intent();
            intentre.putExtra("rest",reture);
            setResult(RESULT_OK, intentre);
            finish();
        });

        database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("user");
        DatabaseReference ref2 =database.getReference().child("tboo2");
        DatabaseReference ref3 =database.getReference().child("taboo");
        ref.orderByKey().equalTo(uid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot datasnapshot, @Nullable String previousChildName) {
                 String pri = (String) datasnapshot.child("principle").getValue();
                 String rel = (String) datasnapshot.child("religion").getValue();
                 String all = (String) datasnapshot.child("allergy").getValue();
                 String ucu = (String) datasnapshot.child("ucustom").getValue();

                all =all.replaceAll("-","");
                if(all.isEmpty()){
                }else{
                    allergylist = (ArrayList<String>) Stream.of(all.split("、")).collect(Collectors.toList());
                }
                if(ucu.isEmpty()){

                }else{
                    taboolist =(ArrayList<String>) Stream.of(ucu.split("、")).collect(Collectors.toList());
                }
                Log.w("DEBUG_DATA", "principle = " + pri);
                Log.w("DEBUG_DATA", "religion = " + rel);
                Log.w("DEBUG_DATA", "allergy = " + all);
                Log.w("DEBUG_DATA", "ucustom = " + ucu);

                ref2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String rel2 = "";
                        if (pri.isEmpty()){
                        }else if(pri.equals("ビーガン")){
                            rel2 = (String) snapshot.child("ビーガン").getValue();
                        }else{
                            rel2 = (String) snapshot.child("ベジタリアン").getValue();
                        }
                        Log.w("DEBUG_DATA", "religion = " + rel2);
                        taboolist =(ArrayList<String>) Stream.of(rel2.split("、")).collect(Collectors.toList());
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                ref3.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String pri2 = "";
                        if(rel.isEmpty()){
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

                        if(CollectionUtils.isEmpty(taboolist)){
                            textView.setText("食べられます");
                        }else{
                            for (int i = 0; i < taboolist.size(); i++) {
                                String cheack = taboolist.get(i);
                                if(resul.matches(cheack)){
                                    textView.setText("食べられません");
                                    break;
                                }else{
                                    textView.setText("食べられます");
                                }
                            }
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });



    }
}