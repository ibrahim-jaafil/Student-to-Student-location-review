package com.example.dsa2_project.Location;

import androidx.annotation.NonNull;

import com.example.dsa2_project.data_structures.LinkedList;

import java.io.Serializable;

public class Place implements Serializable {
    private String name;
    private  Campus campus;

    private String Location_Link;
    private int stars;

    private int price_range;

    private String location_type;

    private LinkedList reviewList; //make a method to calc stars from reviews

    public Place(){

    }

    public Place(String name,Campus campus,String location_type, int price_range,int stars){
       this.location_type=location_type;
        this.stars=stars;
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



    public int getPrice_range() {
        return price_range;
    }



    public int getStars() {
        return stars;
    }


    public void addReview(Reviews reviews) {
        reviewList.insertAtFront(reviews);
        reviewList.outputList();

    }

    public LinkedList getReviewList() {
        return reviewList;
    }

    public String getLocation_Link() {
        return Location_Link;
    }

    public void setLocation_Link(String location_Link) {
        Location_Link = location_Link;
    }

    public Campus getCampus() {
        return campus;
    }


    @NonNull
    @Override
    public String toString() {
        return name;
    }

}
