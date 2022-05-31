package com.example.carpoolbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.gesture.GestureOverlayView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class VehicleInfoActivity extends AppCompatActivity implements Adapter.ItemClickListener{

    RecyclerView rec;
    ArrayList<String> vehicle;
    ArrayList<String> vehicleType;
    ArrayList<Double> price;
    ArrayList<Integer> seats;
    int amount;
    FirebaseFirestore db;
    Adapter adap;
    ArrayList<Vehicle> veh = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_info);
        rec = findViewById(R.id.rec);
        vehicle = new ArrayList<>();
        vehicleType = new ArrayList<>();
        price = new ArrayList<>();
        seats = new ArrayList<>();

        amount = 0;

        db = FirebaseFirestore.getInstance();

        db.collection("vehicles").get().addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                for (QueryDocumentSnapshot ds : Objects.requireNonNull(task.getResult())) {
                    Vehicle bob = ds.toObject(Vehicle.class);
                    veh.add(bob);
                    vehicle.add(bob.getModel());
                    vehicleType.add(bob.getVehicleType());
                    price.add(bob.getPrice());
                    seats.add(bob.getSeats());
                    amount++;

                }
                adap = new Adapter(vehicleType, vehicle, price, seats, amount);
                rec.setLayoutManager(new LinearLayoutManager(this));
                adap.setClickListener(this);
                rec.setAdapter(adap);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rec.getContext(), LinearLayoutManager.HORIZONTAL);
                rec.addItemDecoration(dividerItemDecoration);


            }
        });

    }



    public void addVehicle(View v) {
            Intent intent = new Intent(this, AddVehicleActivity.class);
            startActivity(intent);
    }

    public void logout(View v) {
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
    }

    public void freeMoney(View v) {
        Intent intent = new Intent(this, Guide.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(view.getContext(), VehicleProfileActivity.class);
        intent.putExtra("vehicle", veh.get(position));
        view.getContext().startActivity(intent);
    }
}