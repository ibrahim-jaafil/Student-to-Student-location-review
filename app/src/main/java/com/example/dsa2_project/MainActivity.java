package com.example.dsa2_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.dsa2_project.Location.Campus;
import com.example.dsa2_project.Location.Place;
import com.example.dsa2_project.data_structures.AvlTree;
import com.example.dsa2_project.login_sys.User;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    public static HashMap<Integer, User> users = new HashMap<>();
    public static User currentUser;
    public static ArrayList<Place> favoriteList = new ArrayList<>();

    public static AvlTree places = new AvlTree();

    public static ArrayList<Place> HomePageList = new ArrayList<>();
    public static HashMap<String, Place> lookupByName = new HashMap<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("Places", "App started");
        Campus c = new Campus("besiktas","south");

        Place p1 = new Place("the Best" ,c,"Food",500,5);
        Place p2 = new Place("nitz",c,"Food",500,4);
        Place p3 = new Place("upperdeck",c,"Cafes",600,5);
        Place p6 = new Place("atara",c,"Food",700,3);
        Place p7 = new Place("Green Salad",c,"Food",500,3);
        Place p8 = new Place("cajun",c,"Food",300,3);
        Place p9 = new Place("idk",c,"Food",200,2);
        places.insert(p1);
        places.insert(p2);
        places.insert(p3);
        places.insert(p6);
        places.insert(p7);
        places.insert(p8);
        places.insert(p9);

        HomePageList.add(p1);
        HomePageList.add(p2);
        HomePageList.add(p3);
        HomePageList.add(p6);
        HomePageList.add(p7);
        HomePageList.add(p8);
        HomePageList.add(p9);


        lookupByName.put(p1.getName().toLowerCase(),p1);
        lookupByName.put(p2.getName().toLowerCase(),p2);
        lookupByName.put(p3.getName().toLowerCase(),p3);
        lookupByName.put(p6.getName().toLowerCase(),p6);
        lookupByName.put(p7.getName().toLowerCase(),p7);
        lookupByName.put(p8.getName().toLowerCase(),p8);
        lookupByName.put(p9.getName().toLowerCase(),p9);



    }


    public static String campus;
    public void PickCampus(View view){
        Button b = (Button) view;
         campus= b.getText().toString();

        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(i);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }

}