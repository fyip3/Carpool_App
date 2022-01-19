package com.example.carpoolbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AuthActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
    }

    public void login(View v) {
        String emaill = email.getText().toString();
        String pass = password.getText().toString();

        FirebaseUser mUser = mAuth.getCurrentUser();

        Car car = new Car();

     //   firestore.collection("cars").document("lagos");
//        firestore.collection().document().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if(task.isSuccessful()) {
//                    DocumentSnapshot ds = task.getResult();
//                      Phone myBook  = ds.toObject(Phone.class);
//                      getDescription, getModel;
//                }
//            }
//        });
    }

    public void signup(View v) {
        String emaill = email.getText().toString();
        String pass = password.getText().toString();

        mAuth.createUserWithEmailAndPassword(emaill, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Log.d("SIGN UP", "Successfully signed up the user");

                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);

                }
                else {
                    Log.w("SIGN UP", "createUserWithEmail:failure", task.getException());
//                    Toast.makeText(this, "Authentication failed.",
//                            Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });
    }

    public void updateUI(FirebaseUser user) {
        if(user != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}