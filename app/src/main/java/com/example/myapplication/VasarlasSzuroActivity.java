package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class VasarlasSzuroActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = VasarlasSzuroActivity.class.getName();

    Spinner helyekSpinner;
    RadioGroup fajtaRadioGroup;

    private FirebaseFirestore mFirestore;
    private CollectionReference mJegyek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vasarlasszuro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        helyekSpinner = findViewById(R.id.HelyekSpinner);
        fajtaRadioGroup = findViewById(R.id.fajtaRadioGroup);

        fajtaRadioGroup.check(R.id.fajtaRadioButton);

        helyekSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.helyek, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        helyekSpinner.setAdapter(adapter);

        mFirestore = FirebaseFirestore.getInstance();
        mJegyek = mFirestore.collection("Jegyek");

        queryData();
    }
    private void queryData() {
        mJegyek.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult() != null && !task.getResult().isEmpty()) {
                    Log.d(TAG, "A 'Jegyek' nevű Collection már létezik.");
                    // Itt folytathatod az alkalmazás normál működését
                } else {
                    Log.d(TAG, "A 'Jegyek' nevű Collection még nem létezik.");
                    // Ha nincs ilyen Collection, akkor most hozzuk létre
                    create();
                }
            } else {
                Log.e(TAG, "Firestore hiba: ", task.getException());
            }
        });
    }


    public void create(){
        String[] km = getResources().getStringArray(R.array.km);
        String[] helyek = getResources().getStringArray(R.array.helyek);
        String[] fajta = getResources().getStringArray(R.array.fajta);
        String[] ar = getResources().getStringArray(R.array.ar);


        for (int i = 0; i < helyek.length; i++) {
            for (int j = 0; j < fajta.length; j++) {
                if(!helyek[i].equals("Helyközi járat")){
                    if(j == 0){
                        mJegyek.add(new Jegy(helyek[i],fajta[j],"0","300"));
                    }else{
                        mJegyek.add(new Jegy(helyek[i],fajta[j],"0","150"));
                    }
                }
            }
        }
        for (int i = 0; i < km.length; i++) {
            for (int j = 0; j < fajta.length; j++) {
                if(j == 0){
                    mJegyek.add(new Jegy("Helyközi járat",fajta[j],km[i],ar[i]));
                }else{
                    mJegyek.add(new Jegy("Helyközi járat",fajta[j],km[i],String.valueOf(Integer.parseInt(ar[i])/2)));
                }

            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selected = parent.getItemAtPosition(position).toString();
        Log.d(TAG, selected);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void kereses(View view) {
        String helyek = helyekSpinner.getSelectedItem().toString();


        int id = fajtaRadioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = fajtaRadioGroup.findViewById(id);
        String tipus = radioButton.getText().toString();

        Log.d(TAG, "kereses: "+helyek+" tipus: "+tipus);

        Intent intent = new Intent(this,KeresesActivity.class);
        intent.putExtra("hely",helyek);
        intent.putExtra("tipus",tipus);
        startActivity(intent);
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