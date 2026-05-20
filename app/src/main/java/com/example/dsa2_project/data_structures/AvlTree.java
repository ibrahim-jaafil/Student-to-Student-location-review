package com.example.dsa2_project.data_structures;

import com.example.dsa2_project.Location.Place;

import java.util.ArrayList;

public class AvlTree {

    private AvlNode root;

    public AvlTree() {
        root = null;
    }


    public AvlNode getRoot(){
        return root;
    }

    // NEED TO MAKE THE INSERT A RECURSIVE METHOD

   /* public boolean insert(Place place) {
        AvlNode n = new AvlNode(place);
        if (root == null) {
            root = n;

        }
        else{
            AvlNode parent = null;
            AvlNode curr = root;
            while (curr != null) {
                if (n.getPrice()> curr.getPrice()) {
                    parent = curr;
                    curr = curr.getRight();
                } else if (n.getPrice() < curr.getPrice()) {
                    parent = curr;
                    curr = curr.getLeft();
                } else {
                    curr.getPlaces_for_price().addAll(n.getPlaces_for_price());
                    return true;
                }
            }
            if (n.getPrice() < parent.getPrice()) {
                parent.setLeft(n);
            } else {
                parent.setRight(n);
            }

        }
        NodeHeight(root);
        CalculateBalance(root);
        return true;
    }*/

    public boolean insert(Place p) {
        root = insertRecursive(root, p);
        return true;
    }


    //need to also add in when it is duplicate
    public AvlNode insertRecursive(AvlNode n,Place p){
        if(n==null){
            return n = new AvlNode(p);
        }
        if(p.getPrice_range()>n.getPrice()){
            n.setRight(insertRecursive(n.getRight(),p));
            NodeHeight(n);

           int balance = CalculateBalance(n);
            if(balance>=2){
                if (CalculateBalance(n.getLeft())==-1){
                    // there a problem in the double rotation
                   n.setLeft(rotateLeft(n.getLeft()));
                   n= rotateRight(n);
                    return n ;

                }

                   n= rotateRight(n);
                    return n ;
            }
            else if(balance<=-2){
                if (CalculateBalance(n.getRight())==1){
                  n.setRight(rotateRight(n.getRight()));
                   n= rotateLeft(n);
                    return n ;
                }
                else {
                   n= rotateLeft(n);
                    return n ;
                }
            }
        }
        else if(p.getPrice_range()<n.getPrice()){
            n.setLeft(insertRecursive(n.getLeft(),p));
            NodeHeight(n);

            int balance = CalculateBalance(n);
            if(balance>=2){
                //smth wrong in double about what we are checking balance factor(fixed i think)

                if (CalculateBalance(n.getLeft())==-1){
                   n.setLeft(rotateLeft(n.getLeft()));
                    n =rotateRight(n);
                    return n ;
                }
                else {
                    n=rotateRight(n);
                    return n ;
                }
            }
            else if(balance<=-2){
                if (CalculateBalance(n.getRight())==1){
                   n.setRight(rotateRight(n.getRight()));
                   n=rotateLeft(n);
                   return n ;
                }
                else {
                   n = rotateLeft(n);
                    return n ;
                }
            }
        }
        else{
            n.getPlaces_for_price().add(p);
            return n;
        }
        return n;
    }



    public int NodeHeight(AvlNode root){
            if(root==null){
                return -1;
            }
            if (root.getRight() == null && root.getLeft() == null) {
                return 0;
            }
            root.setHeight(1+Math.max(NodeHeight(root.getLeft()), NodeHeight(root.getRight())));

        return root.getHeight();
    }
    public int CalculateBalance(AvlNode node){
       node.setBalanceFactor(NodeHeight(node.getLeft())-NodeHeight(node.getRight()));
       return node.getBalanceFactor();
       //System.out.println(node.getBalanceFactor());
    }
    

    //gotta change the rotations method there problems(fixed)
    public AvlNode rotateRight(AvlNode n){

        AvlNode t =n.getLeft();
        n.setLeft(t.getRight());// works now
        t.setRight(n);


return t;
    }
    public AvlNode rotateLeft(AvlNode n) {
        AvlNode t = n.getRight();
        n.setRight(t.getLeft());// okk this should be here before the rotation
       t.setLeft(n);
        //n.setRight(null);

        return t;
    }


public void inorder(AvlNode root){
        if(root==null) {
            return;
        }
            inorder(root.getLeft());
            System.out.print(root.getPrice()+" "+root.getPlaces_for_price()+" ");
            inorder(root.getRight());
        }

        public ArrayList<Place> search(int price){

        AvlNode n = root;
        while(n!=null){
            if(n.getPrice()>price){
                n=n.getLeft();
            }
            else if(n.getPrice()<price){
                n=n.getRight();
            }
            else{
                return n.getPlaces_for_price();
            }

        }
        return null;
        }
}