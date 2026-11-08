package com.example.dsa2_project.Location;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

public class Campus implements Serializable {

    private int cid;

    private String name;
    private String Location;
    private ArrayList<Place> places;

    public Campus(int cid,String name, String Location){
        this.cid =cid;
        this.Location=Location;
        this.name=name;
        this.places= new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public ArrayList<Place> getPlaces() {
        return places;
    }

    public int getCid() {
        return cid;
    }

    public void addLocation(Place place){
        places.add(place);

    }

    @NonNull
    @Override
    public String toString() {
        return name + " " + Location;
    }
}
