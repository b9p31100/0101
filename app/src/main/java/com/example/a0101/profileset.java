package com.example.a0101;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class profileset extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileset);

        TextView textView =(TextView) findViewById(R.id.textView12);
        TextView textView2 = (TextView) findViewById(R.id.textView13);
        TextView textView3 = (TextView) findViewById(R.id.textView14);
        TextView textView4 = (TextView) findViewById(R.id.textView15);

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