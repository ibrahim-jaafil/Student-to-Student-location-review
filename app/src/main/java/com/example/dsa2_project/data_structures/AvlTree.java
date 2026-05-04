package com.example.dsa2_project.data_structures;

import androidx.annotation.NonNull;

import com.example.dsa2_project.Location.Place;

public class AvlTree {

    private AvlNode root;

    public AvlTree() {
        root = null;
    }


    public AvlNode getRoot(){
        return root;
    }
    public boolean insert(Place place) {
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
        return true;
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
    public void CalculateBalance(AvlNode node){
       node.setBalanceFactor(NodeHeight(node.getLeft())-NodeHeight(node.getRight()));
       System.out.println(node.getBalanceFactor());
    }

    public void rotations(){

    }


public void inorder(AvlNode root){
        if(root==null) {
            return;
        }
            inorder(root.getLeft());
            System.out.print(root.getPrice()+" ");
            inorder(root.getRight());
        }
}







