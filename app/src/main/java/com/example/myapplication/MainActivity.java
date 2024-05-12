package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    private FirebaseAuth auth;
    EditText email;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Animation pulseAnimation = AnimationUtils.loadAnimation(this, R.anim.pulse);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        Button login = findViewById(R.id.loginBtn);
        Button register = findViewById(R.id.registrationBtn);

        login.startAnimation(pulseAnimation);

        auth = FirebaseAuth.getInstance();
    }

    public void login(View view) {
        String usernameSTR = email.getText().toString();
        String passwordSTR = password.getText().toString();

       // Log.d(TAG, "login: " + usernameSTR +", jelszo: "+passwordSTR);

        auth.signInWithEmailAndPassword(usernameSTR,passwordSTR).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d(TAG, "Sikeres belépés");
                    jegyeim();
                }else {
                    Log.d(TAG, "Sikertelen belépés");
                    Toast.makeText(MainActivity.this,"Sikertelen belépés: "+task.getException(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void jegyeim(){
        Intent intent = new Intent(this,JegyeimActivity.class);
        startActivity(intent);
    }

    public void registration(View view) {
        Intent intent = new Intent(this, RegisztrációActivity.class);

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
    }
}