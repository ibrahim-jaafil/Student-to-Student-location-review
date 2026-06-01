package com.example.dsa2_project.login_sys;

import com.example.dsa2_project.Location.Reviews;
import com.example.dsa2_project.data_structures.LinkedList;
import com.example.dsa2_project.data_structures.Stack;

import java.io.Serializable;


public class User implements Serializable {

    private String username;

    private int STUid;

    private String email;

    private String password;

    private Stack lastViewedPlaces;

    private LinkedList reviews;

public User(String username,String email, String password, int STUid){
    this.email=email;
    this.password=password;
    this.username=username;
    this.STUid=STUid;
    reviews= new LinkedList();
    lastViewedPlaces = new Stack();

}


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getSTUid() {
        return STUid;
    }

    public void setSTUid(int STUid) {
        this.STUid = STUid;
    }



    public LinkedList getReviews() {
        return reviews;
    }

    public void setReviews(LinkedList reviews) {
        this.reviews = reviews;
    }

    public Stack getLastViewedPlaces() {
        return lastViewedPlaces;
    }

    public void setLastViewedPlaces(Stack lastViewedPlaces) {
        this.lastViewedPlaces = lastViewedPlaces;
    }
}
