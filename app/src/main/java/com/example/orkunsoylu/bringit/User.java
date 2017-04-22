package com.example.orkunsoylu.bringit;

/**
 * Created by orkunsoylu on 05/03/2017.
 */

public class User {
    private String username;
    private String password;
    private String email;
    private String address;
    private Wish[] activeWishes;
    private Wish[] oldWishes;
    private membershipType membershipType;
    private double rating;
    private double balance;

    public User(String username,String password,String email,String address,membershipType membershipType){
        setUsername(username);
        setPassword(password);
        setEmail(email);
        setAddress(address);
        setMembershipType(membershipType);
        setActiveWishes(null);
        setOldWishes(null);
        setRating(0.0);
        setBalance(0.0);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Wish[] getActiveWishes() {
        return activeWishes;
    }

    public void setActiveWishes(Wish[] activeWishes) {
        this.activeWishes = activeWishes;
    }

    public Wish[] getOldWishes() {
        return oldWishes;
    }

    public void setOldWishes(Wish[] oldWishes) {
        this.oldWishes = oldWishes;
    }

    public membershipType getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(membershipType membershipType) {
        this.membershipType = membershipType;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
