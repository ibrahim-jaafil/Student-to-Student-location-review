package com.example.dsa2_project.API.load;

import androidx.annotation.NonNull;

public class loadPlacesResponse {

    private int pid;
    private String Pname;
    private int price_range;
    private String location_type;
    private int starsRating;
    private String Location_Link;
    private int cid;

    public int getPid() {
        return pid;
    }

    public String getName() {
        return Pname;
    }

    public int getPrice_range() {
        return price_range;
    }

    public String getLocation_type() {
        return location_type;
    }

    public int getStarsRating() {
        return starsRating;
    }

    public String getLocation_Link() {
        return Location_Link;
    }


    public int getCid() {
        return cid;
    }


}
