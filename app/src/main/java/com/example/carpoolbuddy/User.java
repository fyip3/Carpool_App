package com.example.carpoolbuddy;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class User {
        String uid;
        String email;
        String name;
        String userType;
        double priceMultiplier;
        ArrayList<String> owned;
        double money;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        boolean play = false;


        public User(String email, String userType) {
                this.email = email;
                this.userType = userType;
                money = 999;
                uid=user.getUid();
        }

        public User() {
        }

        public boolean isPlay() {
                return play;
        }

        public void setPlay(boolean play) {
                this.play = play;
        }

        public String getUid() {
                return uid;
        }

        public double getMoney() {
                return money;
        }

        public void setMoney(double money) {
                this.money = money;
        }

        public void setUid(String uid) {
                this.uid = uid;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getUserType() {
                return userType;
        }

        public void setUserType(String userType) {
                this.userType = userType;
        }

        public double getPriceMultiplier() {
                return priceMultiplier;
        }

        public void setPriceMultiplier(double priceMultiplier) {
                this.priceMultiplier = priceMultiplier;
        }

        public ArrayList<String> getOwned() {
                return owned;
        }

        public void setOwned(ArrayList<String> owned) {
                this.owned = owned;
        }
}



