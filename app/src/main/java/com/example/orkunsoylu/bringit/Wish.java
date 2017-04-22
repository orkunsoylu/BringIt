package com.example.orkunsoylu.bringit;

/**
 * Created by orkunsoylu on 22/04/2017.
 */

public class Wish {
    private String name;
    private String description;
    private double price;
    private String country;
    private String owner;

    public Wish(String name,String description,double price,String country,String owner){
        setName(name);
        setDescription(description);
        setPrice(price);
        setCountry(country);
        setOwner(owner);
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
