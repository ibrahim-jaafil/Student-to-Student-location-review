package com.example.dsa2_project.Location;

import androidx.annotation.NonNull;

import com.example.dsa2_project.login_sys.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;



public class Reviews implements Serializable {

     private String review;
     private int stars;
     private LocalDate date;

     //private LocalDate past;
     private User user;

     private   Place place;


     public Reviews(String review,int stars,User user , Place place){
         this.place=place;
         this.review=review;
         this.stars=stars;
         this.user=user;
         user.getReviews().insertAtFront(this);
         date=LocalDate.now();
        // past = LocalDate.of(2026, 5, 22);
     }
     public String getReview() {
         return review;
     }
     /*public void setReview(String review) {

         this.review = review;
     }*/

    public int getStars() {
        return stars;
    }

    /*public void setStars(int stars) {
        this.stars = stars;
    }*/

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Place getPlace() {
        return place;
    }

    public String getDate() {
        //long days   = ChronoUnit.DAYS.between(past, date);
        DateTimeFormatter format =  DateTimeFormatter.ofPattern("dd/MM/yyyy");
       //return days+" days ago";
        return date.format(format);
    }



    @NonNull
    @Override
    public String toString() {
        return review;
    }
}

