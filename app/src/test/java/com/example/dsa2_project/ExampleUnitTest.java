package com.example.dsa2_project;

import static com.example.dsa2_project.MainActivity.users;

import org.junit.Test;

import static org.junit.Assert.*;

import android.util.Log;

import com.example.dsa2_project.Location.Campus;
import com.example.dsa2_project.Location.Place;
import com.example.dsa2_project.Location.Reviews;
import com.example.dsa2_project.data_structures.AvlNode;
import com.example.dsa2_project.data_structures.AvlTree;
import com.example.dsa2_project.data_structures.LinkedList;
import com.example.dsa2_project.login_sys.User;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void test(){
        Campus c = new Campus("south");
        Place p1 = new Place("the Best" ,c,500);
        Place p2 = new Place("nitz",c,400);
        Place p3 = new Place("upperdeck",c,600);
        Place p6 = new Place("atara",c,700);
        Place p7 = new Place("Green Salad",c,500);
        Place p8 = new Place("cajun",c,300);
        Place p9 = new Place("idk",c,200);
        Place p10 = new Place("idkk",c,100);

        c.addLocation(p1);
        c.addLocation(p2);
        c.addLocation(p3);
        c.addLocation(p6);

        //Campus c2 = new Campus("north");
       // Place p4 = new Place("idk" ,c2);
      //  Place p5 = new Place("smth",c2);



       // c2.addLocation(p4);
//c2.addLocation(p5);
        //System.out.println(c.getPlaces().toString());
        //System.out.println(c2.getPlaces().toString());

        Reviews r4 = new Reviews("wow" , 1);
        Reviews r5 = new Reviews("So shit" , 0);
      //  p4.addReview(r4);
      //  p4.addReview(r5);

        AvlTree t = new AvlTree();

        t.insert(p1);
        t.insert(p2);
        t.insert(p3);
        t.insert(p6);
        t.insert(p7);
        t.insert(p8);
        t.insert(p9);


        //t.insert(p1);
       // t.insert(p2);
      /*  t.insert(p10);
        t.insert(p9);
        t.insert(p8);*/

        System.out.println(t.getRoot().getPrice());

        t.inorder(t.getRoot());

        System.out.println(t.getRoot().getPrice());

       // System.out.println(t.getRoot().getHeight());
      //  System.out.println(t.getRoot().getLeft().getBalanceFactor());

       //t.CalculateBalance(t.getRoot());

       // System.out.println(t.getRoot().getRight().getHeight());
       // System.out.println(t.getRoot().getLeft().getHeight());

        /*User u2 = new User("ameen","ameen@gmail.com","ameensalem",247);
        User u1 = new User("bob","bob@gmail.com","bob",2471459);
        u1.addReviewToStack(r5);
        u2.addReviewToStack(r4);*/


        System.out.println(users);
        User user = new User("john", "1234", "john@email.com",222);
        MainActivity.users.put(222, user);
        System.out.println(MainActivity.users);

        //have to add password to the login page
        //need to put safeguards for the email and password at both register and login

    }
}