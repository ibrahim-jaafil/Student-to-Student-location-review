package com.example.dsa2_project.data_structures;

import androidx.annotation.NonNull;

import com.example.dsa2_project.Location.Reviews;

public class StackNode {

    private Reviews reviewInStack;
    private StackNode next;

    public StackNode(){
        next=null;

    }
    public StackNode(Reviews reviewInStack ){
        next=null;
        this.reviewInStack=reviewInStack;

    }

    public Reviews getReviewInStack() {
        return reviewInStack;
    }

    public void setReviewInStack(Reviews reviewInStack) {
        this.reviewInStack = reviewInStack;
    }

    public StackNode getNext() {
        return next;
    }

    public void setNext(StackNode next) {
        this.next = next;
    }

    @NonNull
    @Override
    public String toString() {
        return reviewInStack.getReview();
    }
}
