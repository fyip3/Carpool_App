package com.example.carpoolbuddy;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{
    ArrayList<String> vehicle;
    ArrayList<String> vehicleType;
    ArrayList<Double> price;
    ArrayList<Integer> seats;
    int amount;
    private ItemClickListener mClickListener;

    public Adapter(ArrayList<String> vehicleType, ArrayList<String> vehicle, ArrayList<Double> price, ArrayList<Integer> seats, int amount) {
        this.vehicleType = vehicleType;
        this.vehicle = vehicle;
        this.price = price;
        this.seats = seats;
        this.amount = amount;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.vehicle.setText("Model: "+vehicle.get(position));
        holder.vehicleType.setText(vehicleType.get(position));
        holder.pprice.setText("Price: $"+price.get(position).toString());
        holder.seat.setText("Seats: "+(seats.get(position)));
    }


    @Override
    public int getItemCount() {
        return amount;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        protected TextView vehicle;
        protected TextView pprice;
        protected TextView seat;
        protected TextView vehicleType;
        protected Button book;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            vehicle = itemView.findViewById(R.id.vehicle);
            pprice = itemView.findViewById(R.id.pprice);
            seat = itemView.findViewById(R.id.seat);
            vehicleType = itemView.findViewById(R.id.vehicleType);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

    }
    String getItem(int id) {
        return vehicle.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }



}
