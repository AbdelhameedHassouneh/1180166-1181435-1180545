package com.example.finalproject.model;

import com.example.finalproject.R;

public class Product {
    String productName;
    double price;
    String type;

    int id;
    int imageId;

    public Product() {
    }
    public Product(String productName, double price, int id,String type) {
        this.productName = productName;
        this.price = price;

        this.id = id;

        this.type=type;
        if(this.type.equals("fishFood")){
            imageId= R.drawable.fish;
        }else if(this.type.equals("drink")){
            imageId=R.drawable.drinks;
        }else if(this.type.equals("normalFood")){
            imageId=R.drawable.normal;
        }else {
            imageId=R.drawable.fastfood;
        }

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
