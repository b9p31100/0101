package com.example.a0101;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

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

public class hanteiocr extends AppCompatActivity {
    private String result;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hanteiocr);

        Intent intentMain = getIntent();
        result = intentMain.getStringExtra("ocrre");
        FirebaseUser user = mAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        ImageButton imageButton =(ImageButton)findViewById(R.id.imagebutton1);
        imageButton.setOnClickListener(v ->{
            String reture =result;
            Intent intentre = new Intent();
            intentre.putExtra("rest",reture);
            setResult(RESULT_OK, intentre);
            finish();
        });
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