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

    public void remove(Reviews review) {
        if (isEmpty() || review == null) {
            return;
        }

        Node curr = root;
        Node prev = null;

        while (curr != null) {
            Reviews r = curr.getReviews();
            // Compare fields because serialization creates copies with different memory addresses
            boolean match = r.getReview().equals(review.getReview()) &&
                    r.getStars() == review.getStars() &&
                    r.getUser().getUsername().equals(review.getUser().getUsername())
                    && r.getPlace().getName().equals(review.getPlace().getName());

            if (match) {
                if (prev == null) {
                    root = curr.getNext();
                } else {
                    prev.setNext(curr.getNext());
                }
                return;
            }
            prev = curr;
            curr = curr.getNext();
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

        public ArrayList<Reviews> outputReviewInfo(){
        ArrayList<Reviews> a = new ArrayList<>();
        Node curr =root;
        while(curr!=null){
           /* a.add(curr.getReviews().getUser().getUsername());
            a.add(curr.getReviews().getReview());
            String stars = String.valueOf(curr.getReviews().getStars());
            a.add(stars);
            a.add(curr.getReviews().getDate());
            */
            a.add(curr.getReviews());
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
