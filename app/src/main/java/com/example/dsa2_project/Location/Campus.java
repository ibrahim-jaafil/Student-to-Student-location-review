package com.example.dsa2_project.Logic;

import java.util.ArrayList;

public class Campus {

    private String name;
    private String Location;
    private ArrayList<Place> places;

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

    public void setPlaces(ArrayList<Place> places) {
        this.places = places;
    }

    public boolean addLocation(Place place){
        places.add(place);
        return true;
    }
}
