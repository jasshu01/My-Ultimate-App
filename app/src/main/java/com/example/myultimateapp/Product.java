package com.example.myultimateapp;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Product {

//    "id": 1,
//            "title": "iPhone 9",
//            "description": "An apple mobile which is nothing like apple",
//            "price": 549,
//            "discountPercentage": 12.96,
//            "rating": 4.69,
//            "stock": 94,
//            "brand": "Apple",
//            "category": "smartphones",
//            "thumbnail": "https://dummyjson.com/image/i/products/1/thumbnail.jpg",
//            "images": [
//            "https://dummyjson.com/image/i/products/1/1.jpg",
//            "https://dummyjson.com/image/i/products/1/2.jpg",
//            "https://dummyjson.com/image/i/products/1/3.jpg",
//            "https://dummyjson.com/image/i/products/1/4.jpg",
//            "https://dummyjson.com/image/i/products/1/thumbnail.jpg"
//            ]


    int id, price;
    String title, description, brand, category;
    Bitmap thumbnail;
    double rating;
    ArrayList<Bitmap> images;

    public Product(int id, int price, String title, String description, String brand, String category, Bitmap thumbnail, double rating, ArrayList<Bitmap> images) {
        this.id = id;
        this.price = price;
        this.title = title;
        this.description = description;
        this.brand = brand;
        this.category = category;
        this.thumbnail = thumbnail;
        this.rating = rating;
        this.images = images;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Product() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", price=" + price +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", brand='" + brand + '\'' +
                ", category='" + category + '\'' +
                ", thumbnail=" + thumbnail +
                ", rating=" + rating +
                ", images=" + images +
                '}';
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public ArrayList<Bitmap> getImages() {
        return images;
    }

    public void setImages(ArrayList<Bitmap> images) {
        this.images = images;
    }
}
