package com.example.carpoolbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AddVehicleActivity extends AppCompatActivity {

    TextView model;
    TextView seats;
    TextView price;
    TextView mode;
    TextView vehicl;

    EditText seat;
    EditText pric;
    Spinner vehic;

    ArrayList<Vehicle> vehicles = new ArrayList<>();
    ArrayList<String> modelss = new ArrayList<>();

    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);
        mAuth = FirebaseAuth.getInstance();

        vehicl = findViewById(R.id.ve);
        model = findViewById(R.id.mo);
        seats = findViewById(R.id.se);
        price = findViewById(R.id.pr);
        mode = findViewById(R.id.mode);
        seat = findViewById(R.id.seat);
        pric = findViewById(R.id.pric);
        vehic = findViewById(R.id.typee);

        db = FirebaseFirestore.getInstance();

        modelss.add("Car");
        modelss.add("Jetpack");
        modelss.add("Camel");
        modelss.add("Shopping Cart");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, modelss);
        vehic.setAdapter(adapter);

    }

    public void addVehicle(View v) {
        String vehicleType = vehic.getSelectedItem().toString();
        String modell = mode.getText().toString();
        String seatss = seat.getText().toString();
        String pricee = pric.getText().toString();
        String own = mAuth.getCurrentUser().getEmail();

        if(isValid()) {
            if(vehicleType.equals("Car")) {
                Vehicle vehicle = new Car(vehicleType, modell, Integer.parseInt(seatss), Double.parseDouble(pricee), own);
                vehicles.add(vehicle);
                db.collection("vehicles").document(vehicle.getVehicleID()).set(vehicle);
                Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();

            }
            if(vehicleType.equals("Jetpack")) {
                Vehicle vehicle = new Jetpack(vehicleType, modell, Integer.parseInt(seatss), Double.parseDouble(pricee), own);
                vehicles.add(vehicle);
                db.collection("vehicles").document(vehicle.getVehicleID()).set(vehicle);
                Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
            }
            if(vehicleType.equals("Camel")) {
                Vehicle vehicle = new Camel(vehicleType, modell, Integer.parseInt(seatss), Double.parseDouble(pricee), own);
                vehicles.add(vehicle);
                db.collection("vehicles").document(vehicle.getVehicleID()).set(vehicle);
                Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
            }
            if(vehicleType.equals("Shopping Cart")) {
                Vehicle vehicle = new ShoppingCart(vehicleType, modell, Integer.parseInt(seatss), Double.parseDouble(pricee), own);
                vehicles.add(vehicle);
                db.collection("vehicles").document(vehicle.getVehicleID()).set(vehicle);
                Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
            }


            }
        else {
            Toast.makeText(getApplicationContext(), "Fields incomplete", Toast.LENGTH_SHORT).show();
        }
        
        }


    public boolean isValid() {
        return mode != null && seat != null && pric != null;
    }



}