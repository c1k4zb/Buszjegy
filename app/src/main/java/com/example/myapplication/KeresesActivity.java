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

public class KeresesActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    ChipGroup chipgroup;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_kereses);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent myIntent = getIntent();
        String  hely = "";
        String tipus = "";
        int number = 0;

        if (myIntent != null && myIntent.hasExtra("hely")) {
            hely = myIntent.getStringExtra("hely");
            tipus = myIntent.getStringExtra("tipus");
        }
        chipgroup = findViewById(R.id.jegyekChipGroup);


        if (hely.equals("Helyközi járat")) {
            String[] helyközi = getResources().getStringArray(R.array.helyközi);
            String[] km = getResources().getStringArray(R.array.km);
            String[] ar = getResources().getStringArray(R.array.ar);

            for (int i = 0; i < helyközi.length; i++) {
                Chip chip = new Chip(this);
                chip.setText(helyközi[i]);

                chip.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        300));

                Activity thiss = this;
                String finalHely = "Helyközi járat";
                String finalTipus = tipus;
                String finalNumber = String.valueOf(number);
                int finali = i;

                chip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(thiss, JegyvasarlasActivity.class);
                        intent.putExtra("hely", finalHely);
                        intent.putExtra("tipus", finalTipus);
                        intent.putExtra("szam", finalNumber);
                        intent.putExtra("km", km[finali]);
                        intent.putExtra("ar", ar[finali]);
                        intent.putExtra("number", "0");

                        Log.d(TAG, "szam: " + finalNumber);
                        startActivity(intent);
                    }
                });
                number++;

                chipgroup.addView(chip);

                chip.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                chip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
            }
        } else {
            Chip chip = new Chip(this);
            chip.setText(tipus + " - " + hely);

            chip.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    300));

            Activity thiss = this;
            String finalHely = hely;
            String finalTipus = tipus;
            String finalNumber1 = String.valueOf(number);

            chip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(thiss, JegyvasarlasActivity.class);
                    intent.putExtra("hely", finalHely);
                    intent.putExtra("tipus", finalTipus);
                    intent.putExtra("szam", finalNumber1);
                    intent.putExtra("number", "0");
                    Log.d(TAG, " hely "+ finalHely +" tipus "+ finalTipus+" szam:  " + finalNumber1);
                    startActivity(intent);
                }
            });
            number++;

            chipgroup.addView(chip);

            chip.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            chip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        }
    }


    public void cancel(View view) {
        finish();
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
    }
}
