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
        Campus c = new Campus("South","Besiktas");
        Campus c2 = new Campus("north","Besiktas");
        Campus c3 = new Campus("galata","Beyoglu");
        Campus c4 = new Campus("Dental","Balmumcu");


        Place p1 = new Place("the Best" ,c,"Food",500,5);
        p1.setLocation_Link("https://www.google.com/maps/place/The+Best+Kitchen/@41.0447984,29.0028072,17z/data=!3m1!4b1!4m6!3m5!1s0x14cab7a3a8e4a05f:0x620efe9ba31609e0!8m2!3d41.0447985!4d29.0076781!16s%2Fg%2F1tdj7ncy?entry=ttu&g_ep=EgoyMDI2MDUyNy4wIKXMDSoASAFQAw%3D%3D");
        Place p2 = new Place("nitz",c,"Food",500,4);
        p2.setLocation_Link("https://www.google.com/maps/place/Nitz+Schnitzel+%26+More+Be%C5%9Fikta%C5%9F/@41.0438759,28.9977981,17z/data=!3m1!4b1!4m6!3m5!1s0x14cab7a18b9ba017:0x70baeff69cb98995!8m2!3d41.043876!4d29.002669!16s%2Fg%2F11zkc7z86q?entry=ttu&g_ep=EgoyMDI2MDUyNy4wIKXMDSoASAFQAw%3D%3D");
        Place p3 = new Place("upperdeck",c,"Food",550,5);
        p3.setLocation_Link("https://www.google.com/maps/place/Upperdeck+American+Diner/@41.0431745,29.0036335,17z/data=!3m1!4b1!4m6!3m5!1s0x14cab7ee7c772355:0xedc322e05306e618!8m2!3d41.0431745!4d29.0062084!16s%2Fg%2F11t6j886ds?entry=ttu&g_ep=EgoyMDI2MDUyNy4wIKXMDSoASAFQAw%3D%3D");
        Place p6 = new Place("atara",c,"Food",700,3);
        p6.setLocation_Link("https://www.google.com/maps/place/ATARA/@41.0429639,29.0055496,17z/data=!3m1!4b1!4m6!3m5!1s0x14cab708edf1d231:0xe732127f886f0a18!8m2!3d41.0429639!4d29.0081245!16s%2Fg%2F11k55_76xs?entry=ttu&g_ep=EgoyMDI2MDUyNy4wIKXMDSoASAFQAw%3D%3D");
        Place p7 = new Place("Green Salad",c,"Food",500,3);
        Place p8 = new Place("cajun",c,"Food",300,3);
        p8.setLocation_Link("https://www.google.com/maps/place/Cajun+Corner+Be%C5%9Fikta%C5%9F/@41.0429026,28.9991897,17z/data=!3m1!4b1!4m6!3m5!1s0x14cab768ddcb1037:0xc8095de7e8727e2!8m2!3d41.0429027!4d29.0040606!16s%2Fg%2F11rhr3cwtl?entry=ttu&g_ep=EgoyMDI2MDUyNy4wIKXMDSoASAFQAw%3D%3D");

        Place p9 = new Place("Espresso Lab",c,"Cafes",250,4);
        Place p10 = new Place("Mikel Coffee",c,"Cafes",300,4);
        Place p11 = new Place("Cafe Nero",c,"Cafes",250,4);

        places.insert(p1);
        places.insert(p2);
        places.insert(p3);
        places.insert(p6);
        places.insert(p7);
        places.insert(p8);
        places.insert(p9);
        places.insert(p10);
        places.insert(p11);

        HomePageList.add(p1);
        HomePageList.add(p2);
        HomePageList.add(p3);
        HomePageList.add(p6);
        HomePageList.add(p7);
        HomePageList.add(p8);
        HomePageList.add(p9);
        HomePageList.add(p10);
        HomePageList.add(p11);


        lookupByName.put(p1.getName().toLowerCase(),p1);
        lookupByName.put(p2.getName().toLowerCase(),p2);
        lookupByName.put(p3.getName().toLowerCase(),p3);
        lookupByName.put(p6.getName().toLowerCase(),p6);
        lookupByName.put(p7.getName().toLowerCase(),p7);
        lookupByName.put(p8.getName().toLowerCase(),p8);
        lookupByName.put(p9.getName().toLowerCase(),p9);
        lookupByName.put(p10.getName().toLowerCase(),p10);
        lookupByName.put(p11.getName().toLowerCase(),p11);

        findViewById(R.id.SouthLoc).setOnClickListener(this::PickCampus);
        findViewById(R.id.NorthLoc).setOnClickListener(this::PickCampus);
        findViewById(R.id.GalataLoc).setOnClickListener(this::PickCampus);
        findViewById(R.id.DentalLoc).setOnClickListener(this::PickCampus);

    }


    public static String campus;
    public void PickCampus(View view){
        Button b = (Button) view;
         campus= b.getText().toString();

        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        //i.putExtra("campus",campus);
        startActivity(i);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }

}