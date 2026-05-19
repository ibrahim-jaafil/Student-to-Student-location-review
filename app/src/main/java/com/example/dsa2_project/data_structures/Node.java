package com.example.dsa2_project.data_structures;

import com.example.dsa2_project.Location.Reviews;

import java.io.Serializable;

public class Node implements Serializable {

    private Reviews reviews;
    private Node next;

    public Node() {
        next=null;

    }

    public Node(Reviews reviews ) {
        next=null;
        this.reviews=reviews;
    }

    public Reviews getReviews() {
        return reviews;
    }

    public void setReviews(Reviews reviews) {
        this.reviews = reviews;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}


