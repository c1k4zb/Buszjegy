package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class JegymodositActivity extends AppCompatActivity {
    private static final String TAG = VasarlasSzuroActivity.class.getName();
    TextView jegy;
    private int number;
    private TextView numberTextView;
    private TextView arText;
    private NotificationHandler mNotificationHandler;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    private String userID;

    private FirebaseFirestore mFirestore;
    private CollectionReference mJegyek;
    private String hely;
    private String tipus;
    private String km;
    private String id;
    Button plus;
    Button minus;
    Animation rotate;
    Intent myIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_jegymodosit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        myIntent = getIntent();
        rotate = AnimationUtils.loadAnimation(this,R.anim.rotate);


        numberTextView = findViewById(R.id.numbertext);
        arText = findViewById(R.id.arText);
        plus = findViewById(R.id.increaseButton);
        minus = findViewById(R.id.decreaseButton);
        userID = user.getUid();


        mNotificationHandler = new NotificationHandler(this);

        Intent intent = getIntent();
        hely =intent.getStringExtra("hely");

        number = Integer.parseInt(myIntent.getStringExtra("db"));
        int ar = Integer.parseInt(myIntent.getStringExtra("ar"));
        updateNumber();

        arText.setText(String.valueOf(number*ar));

        if (intent != null) {
            if(hely.equals("Helyközi járat")){
                km = intent.getStringExtra("km"); //TODO sazm null
                tipus = intent.getStringExtra("tipus");
                id = intent.getStringExtra("id");

                Log.d(TAG, "szam: "+km+" tipus: "+tipus);

                jegy = findViewById(R.id.jegy);
                jegy.setText(tipus+" - "+km+ " km");
            }else{
                hely = intent.getStringExtra("hely");
                tipus = intent.getStringExtra("tipus");

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
    public void Jegymodosítás(View view) {
        Query query = mJegyek.whereEqualTo("id",id).whereEqualTo("db",number)
                .whereEqualTo("hely",hely)
                .whereEqualTo("km",km)
                .whereEqualTo("tipus",tipus);
    }
}