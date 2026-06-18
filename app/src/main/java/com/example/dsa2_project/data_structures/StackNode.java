package com.example.dsa2_project.data_structures;

import androidx.annotation.NonNull;

import com.example.dsa2_project.Location.Place;
import com.example.dsa2_project.Location.Reviews;

import java.io.Serializable;

public class StackNode implements Serializable {

    private Place lastViewed;
    private StackNode next;

    public StackNode(){
        next=null;

    }
    public StackNode(Place lastviewed ){
        next=null;
        this.lastViewed=lastviewed;

    }

    public Place getLastViewed() {
        return lastViewed;
    }

    public void setLastViewed(Place lastViewed) {
        this.lastViewed = lastViewed;
    }

    public StackNode getNext() {
        return next;
    }

    public void setNext(StackNode next) {
        this.next = next;
    }
}
