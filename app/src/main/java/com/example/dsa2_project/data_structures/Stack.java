package com.example.dsa2_project.data_structures;

import com.example.dsa2_project.Location.Place;

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

    public void add(Place p){
        // Remove duplicate if it exists to keep the list unique
        remove(p);

        StackNode n = new StackNode(p);
        if(isEmpty()) {
            front = n;
        } else {
            n.setNext(front);
            front = n;
        }
    }

    public void remove(Place p) {
        if (isEmpty()) return;

        if (front.getLastViewed().getName().equalsIgnoreCase(p.getName())) {
            front = front.getNext();
            return;
        }

        StackNode curr = front;
        while (curr.getNext() != null) {
            if (curr.getNext().getLastViewed().getName().equalsIgnoreCase(p.getName())) {
                curr.setNext(curr.getNext().getNext());
                return;
            }
            curr = curr.getNext();
        }
    }

    public ArrayList<Place> outputStack(){
        StackNode curr=front;
        ArrayList<Place> r =new ArrayList<>();
        int counter =0;
        while(curr!=null && counter<=4){
            r.add(curr.getLastViewed());
            curr=curr.getNext();
            counter++;
        }

        return r;
    }

}
