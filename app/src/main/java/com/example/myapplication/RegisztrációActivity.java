package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class RegisztrációActivity extends AppCompatActivity {
    private static final String LOG_TAG = RegisztrációActivity.class.getName();
    EditText emailEdittext;
    EditText usernameEdittext;
    EditText passwordEdittext;
    EditText passwordAgainEdittext;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        emailEdittext = findViewById(R.id.email);
        usernameEdittext = findViewById(R.id.username);
        passwordEdittext = findViewById(R.id.password);
        passwordAgainEdittext = findViewById(R.id.passwordAgain);

        auth = FirebaseAuth.getInstance();
    }

    public void registration(View view) {
        String username = usernameEdittext.getText().toString();
        String password = passwordEdittext.getText().toString();
        String passwordAgain = passwordAgainEdittext.getText().toString();
        String email = emailEdittext.getText().toString();

        if(!password.equals(passwordAgain)) {
            return;
        }



        Log.d(LOG_TAG, "username: " + username + ", password: " + password + " again: " + passwordAgain + ", email: " + email);
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(LOG_TAG, "sikeres regisztrálás: ");
                    Log.d(LOG_TAG, "username: " + username +", password: "+password+" again: " + passwordAgain +", email: "+email);
                    jegyeim();
                } else {
                    Log.d(LOG_TAG, "Nem sikeres regisztrálás" +task.getException().getMessage());
                    Toast.makeText(RegisztrációActivity.this,"Nem sikeres regisztrálás"+task.getException().getMessage(),Toast.LENGTH_LONG).show();;
                }
            }
        });
        //TODO: ellenőrzésre hibát kiírni
    }

    public void cancel(View view) {
        finish();
    }

    public void jegyeim(){
        Intent intent = new Intent(this,JegyeimActivity.class);
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