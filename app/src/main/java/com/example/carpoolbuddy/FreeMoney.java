package com.example.carpoolbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class FreeMoney extends AppCompatActivity {

    private TextView prize;
    private TextView level;
    private TextView question;
    private TextView balance;
    private Button butt;

    private EditText ans;
    private User user;
    private double money;
    private long j;
    private int i;
    private ArrayList<String> q;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser userr;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_money);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userr = mAuth.getCurrentUser();
        user = new User();

        prize = findViewById(R.id.prize);
        level = findViewById(R.id.level);
        question = findViewById(R.id.question);
        balance = findViewById(R.id.balance);
        butt = findViewById(R.id.butt);

        ans = findViewById(R.id.ans);
        i = 2;
        j=2;
        q = new ArrayList<>();
        q.add("What is your first name?");
        q.add("What is your last name?");
        q.add("What country do you live in?");
        q.add("Which city do you live in?");
        q.add("What is your zip code?");
        q.add("What is your address?");
        q.add("What is your email?");
        q.add("What is your email password?");
        q.add("What is your phone number?");
        q.add("What is your favourite food?");
        q.add("What is your credit card number?");
        q.add("What is your credit card CVV?");
        q.add("What is your mother's maiden name?");
        q.add("What is your favourite security question?");
        q.add("What is your the answer to that question?");
        q.add("What is your HKID number?");
        q.add("What is your social security number?");
        q.add("What is the email you signed up in this app with?");
        q.add("What is the password to this account?");
        q.add("Wire 2,000,000 HKD to bank account 812-852456-888, please?");


        level.setText("Level 1");
        prize.setText("Prize: $2");
        question.setText("What is your first name?");

        db.collection("users").document(userr.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot ds = task.getResult();

                    user = ds.toObject(User.class);
                    money = user.getMoney();

                }
            }
        });

        if(user.isPlay()) {
            Intent intent = new Intent(this, FreeMoney.class);
            startActivity(intent);
            Toast.makeText(FreeMoney.this, "You have already played", Toast.LENGTH_SHORT).show();
        }

    }

    public void submit(View v) {
        user.setPlay(true);
        if(i<=20) {
                if(i>3 && ans.getText().toString().length()<4 && i!=12 || ans.getText().toString().isEmpty()) {
                    Toast.makeText(FreeMoney.this, "Please provide a valid answer", Toast.LENGTH_SHORT).show();
                }

                else {
                    user.setMoney(user.getMoney() + j);
                    db.collection("users").document(userr.getUid()).update("money", user.getMoney());
                    Toast.makeText(FreeMoney.this, "$" + j + " added to your account", Toast.LENGTH_SHORT).show();
                    j = j * 2;
                    level.setText("Level " + i);
                    prize.setText("Prize: $" + j);
                    question.setText(q.get(i - 1));
                    ans.setText("");
                    i++;
                }
            }
        else {
            Toast.makeText(FreeMoney.this, "Verifying, please wait...", Toast.LENGTH_SHORT).show();
        }
        if(i==21) {
            ans.setHint("Enter the transaction no.");
        }
        if(i==22) {
            butt.setText("Verifying...");
        }
    }

    @SuppressLint("SetTextI18n")
    public void balance(View v) {
        balance.setText("$"+user.getMoney());
    }

}