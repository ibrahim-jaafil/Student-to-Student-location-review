package com.example.dsa2_project.data_structures;

import com.example.dsa2_project.Location.Place;

import java.util.ArrayList;

public class AvlNode {

    private int price;
    private ArrayList<Place> places_for_price;
    private AvlNode right;
    private AvlNode left;

    private int height;
    private int balanceFactor;


    public AvlNode(){

    }
    public AvlNode(Place place){
        price=place.getPrice_range();
        places_for_price= new ArrayList<Place>();
        places_for_price.add(place);
        right=null;
        left=null;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void addToList(Place place) {
        places_for_price.add(place);
    }

    public ArrayList<Place> getPlaces_for_price() {

        return places_for_price;
    }

    public void setPlaces_for_price(ArrayList<Place> places_for_price) {
        this.places_for_price = places_for_price;

    }

    public AvlNode getLeft() {
        return left;
    }

    public void setLeft(AvlNode left) {
        this.left = left;
    }

    public AvlNode getRight() {
        return right;
    }

    public void setRight(AvlNode right) {
        this.right = right;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getBalanceFactor() {
        return balanceFactor;
    }

    public void setBalanceFactor(int balanceFactor) {
        this.balanceFactor = balanceFactor;
    }
}
