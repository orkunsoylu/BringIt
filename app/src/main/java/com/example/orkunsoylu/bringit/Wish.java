package com.example.orkunsoylu.bringit;

/**
 * Created by orkunsoylu on 22/04/2017.
 */

public class Wish {
    private String name;
    private String description;
    private double price;

    public Wish(String name,String description,double price){
        setName(name);
        setDescription(description);
        setPrice(price);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
