package com.example.dsa2_project.data_structures;

import com.example.dsa2_project.Location.Reviews;

import java.io.Serializable;

public class Stack implements Serializable {

    private StackNode front;


    public Stack(){
        front=null;
    }

    public boolean isEmpty(){
        return (front==null);
    }

    public void add(Reviews r){
        StackNode n = new StackNode(r);
        if(isEmpty() ) {
            front=n;
        }
        else {
            n.setNext(front);
            front=n;

        }

    }

    public void outputStack(){
        StackNode curr=front;
        while(curr!=null){
            System.out.println(curr.getReviewInStack().getReview());
            curr=curr.getNext();
        }
    }

}
