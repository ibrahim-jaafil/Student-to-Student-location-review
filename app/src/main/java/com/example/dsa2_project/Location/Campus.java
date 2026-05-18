package com.example.dsa2_project.Location;

import java.io.Serializable;
import java.util.ArrayList;

public class Campus implements Serializable {

    private String name;
    private String Location;
    private ArrayList<Place> places;

    public Campus(String name, String Location){
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


    public void addLocation(Place place){
        places.add(place);

    }
}
