package com.example.dsa2_project.Location;

public class Reviews {

     private String review;
     private int stars;
     private String date;


     public Reviews(String review,int stars){
         this.review=review;
         this.stars=stars;
     }
     public String getReview() {
         return review;
     }
     public void setReview(String review) {
         this.review = review;
     }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
