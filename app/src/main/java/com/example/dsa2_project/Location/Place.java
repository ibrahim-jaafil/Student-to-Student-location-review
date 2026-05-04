package com.example.dsa2_project.Location;

import androidx.annotation.NonNull;

import com.example.dsa2_project.data_structures.LinkedList;

public class Place {
    private String name;
    private Campus campus;
    private Reviews review;

    private int price_range;

    private String location_type;

    private LinkedList reviewList;

    public Place(String name,Campus campus, int price_range){
        this.campus=campus;
        this.name=name;
        this.price_range=price_range;
        reviewList= new LinkedList();

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation_type() {
        return location_type;
    }

    public void setLocation_type(String location_type) {
        this.location_type = location_type;
    }


    public int getPrice_range() {
        return price_range;
    }

    public void setPrice_range(int price_range) {
        this.price_range = price_range;
    }



    public void addReview(Reviews reviews) {
        reviewList.insertAtFront(reviews);
        reviewList.outputList();

    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }

}
