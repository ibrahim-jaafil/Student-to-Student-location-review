package com.example.dsa2_project;

import org.junit.Test;

import static org.junit.Assert.*;

import android.util.Log;

import com.example.dsa2_project.Location.Campus;
import com.example.dsa2_project.Location.Place;
import com.example.dsa2_project.Location.Reviews;
import com.example.dsa2_project.data_structures.LinkedList;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void test(){
        Campus c = new Campus("south");
        Place p1 = new Place("the Best" ,c);
        Place p2 = new Place("nitz",c);
        Place p3 = new Place("upperdeck",c);

        c.addLocation(p1);
        c.addLocation(p2);
        c.addLocation(p3);

        Campus c2 = new Campus("north");
        Place p4 = new Place("idk" ,c2);
        Place p5 = new Place("smth",c2);



        c2.addLocation(p4);
        c2.addLocation(p5);
        System.out.println(c.getPlaces().toString());
        System.out.println(c2.getPlaces().toString());

        Reviews r4 = new Reviews("nigga" , 1);
        Reviews r5 = new Reviews("So shit" , 0);
        p4.addReview(r4);
        p4.addReview(r5);


    }
}