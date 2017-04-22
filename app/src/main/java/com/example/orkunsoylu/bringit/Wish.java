package com.example.orkunsoylu.bringit;

/**
 * Created by orkunsoylu on 05/03/2017.
 */

public class Wish {
    private String name;
    private double price;
    private String info;
    private String address;

    public Wish(String name,double price,String info,String address){
        setName(name);
        setPrice(price);
        setInfo(info);
        setAddress(address);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
