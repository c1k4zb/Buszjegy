package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class JegyvasarlasActivity extends AppCompatActivity {
    private static final String TAG = VasarlasSzuroActivity.class.getName();
    TextView jegy;
    private int number;
    private TextView numberTextView;
    private NotificationHandler mNotificationHandler;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    private String userID;

    private FirebaseFirestore mFirestore;
    private CollectionReference mJegyek;
    private String hely;
    private String tipus;
    private String km;
    Button plus;
    Button minus;
    Animation rotate;
    Intent myIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_jegyvasarlas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        myIntent = getIntent();
        rotate = AnimationUtils.loadAnimation(this,R.anim.rotate);


        numberTextView = findViewById(R.id.numberTextView);
        plus = findViewById(R.id.increaseButton);
        minus = findViewById(R.id.decreaseButton);
        userID = user.getUid();


        mNotificationHandler = new NotificationHandler(this);

        hely =myIntent.getStringExtra("hely");

        number = Integer.parseInt(myIntent.getStringExtra("number"));
        updateNumber();

        if (myIntent != null) {
            if(hely.equals("Helyközi járat")){
                km = myIntent.getStringExtra("km");
                tipus = myIntent.getStringExtra("tipus");

                Log.d(TAG, "szam: "+km+" tipus: "+tipus);

                jegy = findViewById(R.id.jegy);
                jegy.setText(tipus+" - "+km+ " km");
            }else{
                hely = myIntent.getStringExtra("hely");
                tipus = myIntent.getStringExtra("tipus");

                Log.d(TAG, "szam: "+hely+" tipus: "+tipus);

                jegy = findViewById(R.id.jegy);
                jegy.setText(tipus+" - "+hely);
            }
        }

        mFirestore = FirebaseFirestore.getInstance();
        mJegyek = mFirestore.collection("megvett");
    }


    public void cancel(View view) {
        finish();
    }

    public void increaseNumber(View view) {
        numberTextView.startAnimation(rotate);
        number++;
        updateNumber();
    }

    public void decreaseNumber(View view) {
        numberTextView.startAnimation(rotate);
        if (number > 0) {
            number--;
            updateNumber();
        }
    }
    private void updateNumber(){
        numberTextView.setText(String.valueOf(number));
    }

    public void jegyvasarlas(View view) {
        create();
        Log.d(TAG, "jegyvasarlas: üzenet");
        mNotificationHandler.send("sikeres vásárlás");
    }
    
    public void create(){
        String ar = myIntent.getStringExtra("ar");
        String km = myIntent.getStringExtra("km");
        if(!hely.equals("Helyközi járat")){
            km = "0";
            ar = "300";
        }

        if(number == 0){
            Toast.makeText(JegyvasarlasActivity.this,"Nem tudsz 0 darabot venni",Toast.LENGTH_LONG).show();;
        }else{
            mJegyek.add(new Jegy(userID,hely,tipus,km,ar,String.valueOf(number)));
        }


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