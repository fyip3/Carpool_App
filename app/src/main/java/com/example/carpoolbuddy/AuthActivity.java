package com.example.carpoolbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AuthActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private EditText email;
    private EditText password;
    private Spinner typee;
    ArrayList<String> types = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        typee = findViewById(R.id.typee);
        types.add("Student");
        types.add("Alumni");
        types.add("Teacher");
        types.add("Parent");


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, types);
        typee.setAdapter(adapter);

    }

    @SuppressLint("SetTextI18n")
    public void login(View v) {

        String emaill = email.getText().toString();
        String pass = password.getText().toString();

        try {
            mAuth.signInWithEmailAndPassword(emaill, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.d("SIGN IN", "Successfully signed in the user");

                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);

                    } else {
                        Toast.makeText(AuthActivity.this, "Invalid Username/Password", Toast.LENGTH_SHORT).show();
                        Log.w("SIGN IN", "signIn:failure", task.getException());
                        updateUI(null);
                    }
                }
            });
        } catch (Exception e) {
            Toast.makeText(AuthActivity.this, "Invalid Username/Password", Toast.LENGTH_SHORT).show();
        }

    }


    public void signup(View v) {
        String emaill = email.getText().toString();
        String pass = password.getText().toString();
        String userType = typee.getSelectedItem().toString();
        User userrr = new User(emaill, userType);
        if(!emaill.isEmpty() && !pass.isEmpty()) {
            if (emaill.contains("cis.edu.hk")) {
                mAuth.createUserWithEmailAndPassword(emaill, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("SIGN UP", "Successfully signed up the user");

                            FirebaseUser user = mAuth.getCurrentUser();
                            db.collection("users").document(user.getUid()).set(userrr);
                            updateUI(user);

                        } else {
                            Log.w("SIGN UP", "createUserWithEmail:failure", task.getException());
//                    Toast.makeText(this, "Authentication failed.",
//                            Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
            }
            else {
                Toast.makeText(AuthActivity.this, "Sorry, you are not a CIS member", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(AuthActivity.this, "Please enter your email and password", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateUI(FirebaseUser user) {
        if(user != null) {
            Intent intent = new Intent(this, VehicleInfoActivity.class);
            startActivity(intent);
        }
    }
}