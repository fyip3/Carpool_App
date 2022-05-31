package com.example.carpoolbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class VehicleProfileActivity extends AppCompatActivity{

    private TextView v;
    private TextView p;
    private TextView s;
    private TextView o;
    private TextView m;
    private Button button;
    private FirebaseAuth mAuth;
    private FirebaseUser userr;
    private FirebaseFirestore db;
    private Vehicle vehicle;
    private User user;
    private double money;
    private double price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_profile);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userr = mAuth.getCurrentUser();
        user = new User();

        v = findViewById(R.id.v);
        p = findViewById(R.id.p);
        m = findViewById(R.id.m);
        o = findViewById(R.id.o);
        s = findViewById(R.id.s);
        button = findViewById(R.id.button);

        Intent intent = getIntent();
        vehicle = (Vehicle) intent.getSerializableExtra("vehicle");

        v.setText(vehicle.getVehicleType());
        p.setText(String.valueOf(vehicle.getPrice()));
        m.setText(vehicle.getModel());
        o.setText(vehicle.getOwner());
        s.setText(String.valueOf(vehicle.getSeats()));

        db.collection("users").document(userr.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot ds = task.getResult();

                    user = ds.toObject(User.class);
                    money = user.getMoney();

                    if(user.getUserType().equals("Student") || user.getUserType().equals("Teacher")) {
                        price = vehicle.getPrice()/2;
                    }
                    else {
                        price = vehicle.getPrice();
                    }
                    setUpButtons();
                } else {
                    Toast.makeText(VehicleProfileActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
    
    public void setUpButtons() {

                if(userr.getEmail().equals(vehicle.getOwner())) {
                    if(vehicle.isOpen()) {
                        button.setText("Close");
                    } else {
                        button.setText("Open");
                    }
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(vehicle.isOpen()) {
                                vehicle.setOpen(false);
                                db.collection("vehicles").document(vehicle.getVehicleID()).update("open", false);
                                button.setText("Open");
                                Toast.makeText(VehicleProfileActivity.this, "Closed ride", Toast.LENGTH_SHORT).show();

                            } else {
                                vehicle.setOpen(true);
                                db.collection("vehicles").document(vehicle.getVehicleID()).update("open", true);
                                button.setText("Close");
                                Toast.makeText(VehicleProfileActivity.this, "Opened ride", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });


                } else {
                    if(!vehicle.isOpen()) {
                        button.setText("Ride is closed");
                    }
                    else if(vehicle.getRidersUIDs().contains(userr.getUid())) {
                        button.setText("Leave");
                    } else {
                        button.setText("Join");
                    }
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(vehicle.isOpen() && vehicle.getRidersUIDs().contains(userr.getUid())) {
                                vehicle.removeRider(userr.getUid());
                                db.collection("vehicles").document(vehicle.getVehicleID()).update("ridersUIDs", vehicle.getRidersUIDs());
                                Toast.makeText(VehicleProfileActivity.this, "Left", Toast.LENGTH_SHORT).show();
                                button.setText("Join");

                            } else if(vehicle.isOpen() && user.getMoney() >= price) {
                                vehicle.addRider(userr.getUid());
                                db.collection("vehicles").document(vehicle.getVehicleID()).update("ridersUIDs", vehicle.getRidersUIDs());
                                Toast.makeText(VehicleProfileActivity.this, "Joined", Toast.LENGTH_SHORT).show();
                                user.setMoney(money-price);
                                db.collection("users").document(userr.getUid()).update("money", user.getMoney());
                                button.setText("Leave");

                            }
                            else if(vehicle.isOpen()){
                                Toast.makeText(VehicleProfileActivity.this, "You broke", Toast.LENGTH_SHORT).show();

                            }
                            else {
                                Toast.makeText(VehicleProfileActivity.this, "Ride is closed", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }


    }

}