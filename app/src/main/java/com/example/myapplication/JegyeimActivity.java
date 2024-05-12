package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class JegyeimActivity extends AppCompatActivity {
    private static final String TAG = JegyeimActivity.class.getName();
    ChipGroup chipgroup;
    FirebaseFirestore db;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    private String userID;
    CollectionReference jegyekCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_jegyeim);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        chipgroup = findViewById(R.id.jegyekChipGroup);
        db = FirebaseFirestore.getInstance();
        jegyekCollection = db.collection("megvett");
        userID = user.getUid();

        if(jegyekCollection == null){
            Log.d(TAG, "NULLLLLLLLLLLLL ");
        }

    }

    public void kiir(){
        jegyekCollection.get().addOnSuccessListener(queryDocumentSnapshots  -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                String userid = document.getString("id");
                if(userID.equals(userid)){
                    Activity thiss = this;
                    String hely = document.getString("hely");
                    String tipus = document.getString("tipus");
                    String km = document.getString("km");
                    String ar = document.getString("ar");
                    String db = document.getString("db");

                    Chip chip = new Chip(this);

                    if(hely.equals("Helyközi járat")){
                        chip.setText(tipus+" - "+km+" km");
                    }else {
                        chip.setText(tipus+" - "+hely);
                    }
                    chip.setLayoutParams(new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            300));

                    chip.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(thiss, JegymodositActivity.class);
                            intent.putExtra("hely", hely);
                            intent.putExtra("tipus", tipus);
                            intent.putExtra("km", km);
                            intent.putExtra("ar", ar);
                            intent.putExtra("db", db);

                            startActivity(intent);
                        }
                    });
                    chipgroup.addView(chip);

                    chip.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    chip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                }
            }
        });
    }

    public void jegyvasarlas(View view) {
        Intent intent = new Intent(this, VasarlasSzuroActivity.class);
        startActivity(intent);
    }

    public void bejelentes(View view) {
        Intent intent = new Intent(this,HibaBejelentesActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        chipgroup.removeAllViews();
        kiir();
    }
}