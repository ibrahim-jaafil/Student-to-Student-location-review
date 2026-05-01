package com.example.dsa2_project.data_structures;

import com.example.dsa2_project.Location.Reviews;

public class LinkedList {

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










}
