package com.example.dsa2_project.data_structures;

import com.example.dsa2_project.Location.Reviews;
import com.example.dsa2_project.login_sys.User;

import java.io.Serializable;
import java.util.ArrayList;

public class LinkedList implements Serializable {

    private Node root;

    public LinkedList() {
        root=null;
    }

    public boolean isEmpty() {

        return (root==null);

    }

    public void insertAtFront(Reviews reviews) {
        Node n = new Node(reviews);
        if (isEmpty()) {
            root = n;
        } else {
            n.setNext(root);
            root = n;
        }
    }
        public boolean removeAtFront() {
            if (isEmpty()) {
                return false;
            } else {
                root = root.getNext();
                return true;
            }
        }




    public void outputList() {
        Node current = new Node();
        current=root;
        while(current!=null) {
            System.out.println(current.getReviews().getReview());
            current=current.getNext();
        }


        }

        public ArrayList<String> outputReviewInfo(){
        ArrayList<String> a = new ArrayList<>();
        Node curr =root;
        while(curr!=null){
            a.add(curr.getReviews().getUser().getUsername());
            a.add(curr.getReviews().getReview());
            String stars = String.valueOf(curr.getReviews().getStars());
            a.add(stars);
            curr=curr.getNext();
        }
        return a;
        }

        public ArrayList<User> outputUsers(){
            ArrayList<User> a = new ArrayList<>();
            Node curr =root;
            while(curr!=null){
               a.add(curr.getReviews().getUser());
                curr=curr.getNext();
            }
            return a;
        }


}
