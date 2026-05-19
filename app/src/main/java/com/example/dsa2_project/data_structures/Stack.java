package com.example.dsa2_project.data_structures;

import com.example.dsa2_project.Location.Reviews;

import java.io.Serializable;
import java.util.ArrayList;

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

    public ArrayList<Reviews> outputStack(){
        StackNode curr=front;
        ArrayList<Reviews> r =new ArrayList<>();
        while(curr!=null){
            r.add(curr.getReviewInStack());
            curr=curr.getNext();
        }

        return r;
    }

}
