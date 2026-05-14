package com.example.dsa2_project.login_sys;

import com.example.dsa2_project.Location.Reviews;
import com.example.dsa2_project.data_structures.Stack;



public class User {

    private String username;

    private int STUid;

    private Stack reviews;

public User(String username, int STUid){

    this.username=username;
    this.STUid=STUid;
    reviews= new Stack();
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


    public void addReviewToStack(Reviews r){
    reviews.add(r);

    }

}
