package com.example.a0101;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
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
    private ArrayList<String> rellist,uculist,taboolist;
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
        rellist =new ArrayList<>();
        uculist =new ArrayList<>();

        Button btn =(Button)findViewById(R.id.button5);
        Button btn2 =(Button)findViewById(R.id.button6);
        ImageButton btn3 =(ImageButton) findViewById(R.id.imageButton1);
        ImageButton btn4 =(ImageButton)findViewById(R.id.imageButton2);
        ImageButton btn5 =(ImageButton)findViewById(R.id.imageButton3);
        ImageButton btn6 =(ImageButton)findViewById(R.id.imageButton4);

        ImageButton imageButton =(ImageButton)findViewById(R.id.imagebutton1);
        imageButton.setOnClickListener(v ->{
            String reture =resul;
            Intent intentre = new Intent();
            intentre.putExtra("rest",reture);
            setResult(RESULT_OK, intentre);
            finish();
        });
        btn.setOnClickListener(v ->{
            startActivity(new Intent(hanteiocr.this,post.class));
        });
        btn2.setOnClickListener(v ->{
            startActivity(new Intent(hanteiocr.this,home.class));
        });

        btn3.setOnClickListener(v -> {
            startActivity(new Intent(hanteiocr.this, home.class));
        });
        btn4.setOnClickListener(v -> {
            startActivity(new Intent(hanteiocr.this, setting.class));
        });
        btn5.setOnClickListener(v -> {
            startActivity(new Intent(hanteiocr.this, option.class));
        });
        btn6.setOnClickListener(v -> {
            startActivity(new Intent(hanteiocr.this, profiletregister.class));
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

                all =all.replaceAll(",","???");
                if(all.equals("??????")){
                }else{
                    allergylist = (ArrayList<String>) Stream.of(all.split("???")).collect(Collectors.toList());
                }
                if(ucu.isEmpty()){

                }else{
                    uculist =(ArrayList<String>) Stream.of(ucu.split(",")).collect(Collectors.toList());
                }
                Log.w("DEBUG_DATA", "principle = " + pri);
                Log.w("DEBUG_DATA", "religion = " + rel);
                Log.w("DEBUG_DATA", "allergy = " + all);
                Log.w("DEBUG_DATA", "ucustom = " + ucu);

                ref2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String rel2 = "";
                        if (pri.equals("??????")){ }else if(pri.equals("????????????")){
                            rel2 = (String) snapshot.child("????????????").getValue();
                        }else{
                            rel2 = (String) snapshot.child("??????????????????").getValue();
                        }
                        if(pri.isEmpty()){}else{
                            Log.w("DEBUG_DATA", "religion = " + rel2);
                            rellist =(ArrayList<String>) Stream.of(rel2.split("???")).collect(Collectors.toList());
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                ref3.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String pri2 = "";
                        if(rel.equals("??????")){
                        }else if(rel.equals("???????????????")){
                            pri2 =(String)snapshot.child("???????????????").getValue();
                        }else if(rel.equals("???????????????")){
                            pri2 =(String) snapshot.child("???????????????").getValue();
                        }else if(rel.equals("??????????????????")){
                            pri2 =(String) snapshot.child("??????????????????").getValue();
                        }else if(rel.equals("???????????????")){
                            pri2 =(String) snapshot.child("???????????????").getValue();
                        }else if(rel.equals("????????????")){
                            pri2 =(String) snapshot.child("????????????").getValue();
                        }else if(rel.equals("????????????")){
                            pri2=(String) snapshot.child("????????????").getValue();
                        }else if (rel.equals("?????????")){
                            pri2=(String) snapshot.child("?????????").getValue();
                        }else{
                            pri2=(String) snapshot.child("????????????").getValue();
                        }
                        if(rel.isEmpty()){}else{
                            taboolist =(ArrayList<String>) Stream.of(pri2.split("???")).collect(Collectors.toList());
                            Log.w("DEBUG_DATA", "principle = " + pri2);
                        }

                        if(CollectionUtils.isEmpty(taboolist) && CollectionUtils.isEmpty(uculist)){
                            textView.setText("??????????????????");
                        }else{
                            String cheack;
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
                                    if (resul.matches("^.*" + cheack + ".*$")) {
                                        textView.setText("?????????????????????");
                                        break;
                                    } else {
                                        textView.setText("??????????????????");
                                    }
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