package com.example.dsa2_project.API.load;

import androidx.annotation.NonNull;

public class loadReviewsResponse {
    private int rid;
    private int pid;
    private int STUid;
    private String review;
    private int rating;
    private String date;

    public int getRating() {
        return rating;
    }

    public String getReview() {
        return review;
    }

    public int getSTUid() {
        return STUid;
    }

    public int getPid() {
        return pid;
    }

    public int getRid() {
        return rid;
    }

    public String getDate() {
        return date;
    }


}
