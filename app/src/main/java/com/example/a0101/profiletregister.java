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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class profiletregister extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiletregister);
        Button btn1 = (Button) findViewById(R.id.button7);
        ImageButton btn6 = (ImageButton) findViewById(R.id.imageButton1);
        ImageButton btn7 = (ImageButton) findViewById(R.id.imageButton2);
        ImageButton btn8 = (ImageButton) findViewById(R.id.imageButton3);
        ImageButton btn9 = (ImageButton) findViewById(R.id.imageButton4);
        mAuth = FirebaseAuth.getInstance();
        //Button btn = (Button) findViewById(R.id.homebutton1);//return_buttonのところをボタンのid名に変える
        btn6.setOnClickListener(v -> {
            startActivity(new Intent(profiletregister.this, home.class));
        });
        btn7.setOnClickListener(v -> {
            startActivity(new Intent(profiletregister.this, setting.class));
        });
        btn8.setOnClickListener(v -> {
            startActivity(new Intent(profiletregister.this, option.class));
        });
        btn9.setOnClickListener(v -> {
            startActivity(new Intent(profiletregister.this, profiletregister.class));
        });

        btn1.setOnClickListener(v ->{
            startActivity(new Intent(profiletregister.this, register.class));
        });
        TextView textView =(TextView) findViewById(R.id.textView4);
        TextView textView2 = (TextView) findViewById(R.id.textView7);
        TextView textView3 = (TextView) findViewById(R.id.textView5);
        TextView textView4 = (TextView) findViewById(R.id.textView9);

        FirebaseUser user = mAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("user");
        ref.orderByKey().equalTo(uid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot datasnapshot, @Nullable String previousChildName) {
                String pri = (String) datasnapshot.child("principle").getValue();
                String rel = (String) datasnapshot.child("religion").getValue();
                String all = (String) datasnapshot.child("allergy").getValue();
                String ucu = (String) datasnapshot.child("ucustom").getValue();

                Log.w("DEBUG_DATA", "principle = " + pri);
                Log.w("DEBUG_DATA", "religion = " + rel);
                Log.w("DEBUG_DATA", "allergy = " + all);
                Log.w("DEBUG_DATA", "ucustom = " + ucu);

                if(ucu.isEmpty()){
                    ucu ="なし";
                }
                textView.setText(pri);
                textView2.setText(rel);
                textView3.setText(all);
                textView4.setText(ucu);
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