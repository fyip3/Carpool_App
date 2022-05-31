package com.example.carpoolbuddy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;


public class Vehicle implements Serializable {

    String owner;
    String model;
    int seats;
    String vehicleID;
    ArrayList<String> ridersUIDs;
    boolean open;
    String vehicleType;
    double price;

    public Vehicle(String vehicleType, String model, int seats, double price, String owner) {
        this.vehicleType = vehicleType;
        this.model = model;
        this.seats = seats;
        this.price = price;
        this.owner = owner;
        UUID uid = UUID.randomUUID();
        vehicleID = uid.toString();
        open = true;
        ridersUIDs = new ArrayList<>();
    }

    public Vehicle() {
    }

    public void addRider(String uid) {
        ridersUIDs.add(uid);
    }
    public void removeRider(String uid) {
        ridersUIDs.remove(uid);
    }

    public String getOwner() {
        return owner;
    }

    public String getModel() {
        return model;
    }

    public int getSeats() {
        return seats;
    }

    public String getVehicleID() {
        return vehicleID;
    }

    public ArrayList<String> getRidersUIDs() {
        return ridersUIDs;
    }

    public boolean isOpen() {
        return open;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public double getPrice() {
        return price;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public void setVehicleID(String vehicleID) {
        this.vehicleID = vehicleID;
    }

    public void setRidersUIDs(ArrayList<String> ridersUIDs) {
        this.ridersUIDs = ridersUIDs;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
