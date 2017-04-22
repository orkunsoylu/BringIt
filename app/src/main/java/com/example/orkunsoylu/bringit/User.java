package com.example.orkunsoylu.bringit;

/**
 * Created by orkunsoylu on 22/04/2017.
 */

public class User {
    private String username;
    private String password;
    private Wish[] wishes;

    public User(String username,String password,Wish[] wishes){
        setUsername(username);
        setPassword(password);
        setWishes(wishes);
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

    public Wish[] getWishes() {
        return wishes;
    }

    public void setWishes(Wish[] wishes) {
        this.wishes = wishes;
    }
}
