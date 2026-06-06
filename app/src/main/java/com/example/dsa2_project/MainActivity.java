package com.example.dsa2_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.dsa2_project.Location.Campus;
import com.example.dsa2_project.Location.Place;
import com.example.dsa2_project.Location.Reviews;
import com.example.dsa2_project.data_structures.AvlTree;
import com.example.dsa2_project.data_structures.Database.DatabaseHelper;
import com.example.dsa2_project.data_structures.Database.Tables.CampusTable;
import com.example.dsa2_project.data_structures.Database.Tables.FavoritesTable;
import com.example.dsa2_project.data_structures.Database.Tables.PlaceTable;
import com.example.dsa2_project.data_structures.Database.Tables.ReviewTable;
import com.example.dsa2_project.data_structures.Database.Tables.UsersTable;
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

    public static ArrayList<Campus> campuses = new ArrayList<>();



    public static DatabaseHelper dbHelper;
    public static UsersTable   userTable;
    public static PlaceTable  placeTable;
    public static ReviewTable reviewTable;
    public static CampusTable campusTable;
    public static FavoritesTable favoritesTable;









    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dbHelper       = new DatabaseHelper(this);
        campusTable    = new CampusTable(dbHelper);
        userTable      = new UsersTable(dbHelper);
        placeTable     = new PlaceTable(dbHelper);
        reviewTable    = new ReviewTable(dbHelper);
        favoritesTable = new FavoritesTable(dbHelper);

        if (campusTable.getCount() == 0) {

            campusTable.insert("South",  "Besiktas");
            campusTable.insert("North",  "Besiktas");
            campusTable.insert("Galata", "Beyoglu");
            campusTable.insert("Dental", "Balmumcu");

            int southid  = campusTable.getCidByName("South");
            int northid  = campusTable.getCidByName("North");
            int galataid = campusTable.getCidByName("Galata");
            int dentalid = campusTable.getCidByName("Dental");

            userTable.insert("admin",   "admin",   "000", 000);
            userTable.insert("bob",     "user",    "123", 123);
            userTable.insert("ameen",   "ameen",   "456", 456);
            userTable.insert("ragheed", "ragheed", "789", 789);

            placeTable.insert("The Best",    southid, "Food", 500, 5, "https://www.google.com/maps/place/The+Best+Kitchen/@41.0447984,29.0028072,17z");
            placeTable.insert("nitz",        southid, "Food", 500, 4, "https://www.google.com/maps/place/Nitz+Schnitzel");
            placeTable.insert("upperdeck",   southid, "Food", 550, 5, "https://www.google.com/maps/place/Upperdeck+American+Diner");
            placeTable.insert("atara",       southid, "Food", 700, 3, "https://www.google.com/maps/place/ATARA");
            placeTable.insert("Green Salad", southid, "Food", 500, 3, "https://www.google.com/maps/place/Green+Salad");
            placeTable.insert("cajun",       southid, "Food", 300, 3, "https://www.google.com/maps/place/Cajun+Corner");

            int p1 = placeTable.getPidByName("The Best");
            int p2 = placeTable.getPidByName("nitz");
            int p3 = placeTable.getPidByName("upperdeck");
            int p4 = placeTable.getPidByName("atara");

            reviewTable.insert(p1, 789, "so good",           5, "05/05/2026");
            reviewTable.insert(p2, 789, "very good",          4, "05/05/2026");
            reviewTable.insert(p3, 123, "love this place",    5, "20/05/2026");
            reviewTable.insert(p4, 456, "I didn't like this", 2, "17/05/2026");
        }

        //  EVERY LAUNCH — load campuses and users

        campuses.clear();
        Cursor campusCursor = campusTable.getAll();
        while (campusCursor.moveToNext()) {
            int    cid      = campusCursor.getInt(campusCursor.getColumnIndexOrThrow("cid"));
            String cname    = campusCursor.getString(campusCursor.getColumnIndexOrThrow("Cname"));
            String location = campusCursor.getString(campusCursor.getColumnIndexOrThrow("Location"));
            campuses.add(new Campus(cname, location));
        }
        campusCursor.close();

        users.clear();
        Cursor userCursor = userTable.getAll();
        while (userCursor.moveToNext()) {
            int    id    = userCursor.getInt(userCursor.getColumnIndexOrThrow("STUid"));
            String name  = userCursor.getString(userCursor.getColumnIndexOrThrow("username"));
            String email = userCursor.getString(userCursor.getColumnIndexOrThrow("email"));
            String pass  = userCursor.getString(userCursor.getColumnIndexOrThrow("password"));
            users.put(id, new User(name, email, pass, id));
        }
        userCursor.close();


        findViewById(R.id.SouthLoc).setOnClickListener(this::PickCampus);
        findViewById(R.id.NorthLoc).setOnClickListener(this::PickCampus);
        findViewById(R.id.GalataLoc).setOnClickListener(this::PickCampus);
        findViewById(R.id.DentalLoc).setOnClickListener(this::PickCampus);

    }

    public static String campus;
    public static int campusid;

    public void PickCampus(View view) {
        Button b = (Button) view;
        campus = b.getText().toString();  // e.g. "South"

        HomePageList.clear();
        places     = new AvlTree();
        lookupByName.clear();

        // match campus name case-insensitively
        campusid = -1;
        for (Campus c : campuses) {
            if (c.getName().equalsIgnoreCase(campus)) {
                campusid = campusTable.getCidByName(c.getName());
                break;
            }
        }

        if (campusid == -1) {
            Log.e("PickCampus", "Campus not found: " + campus);
            return; // stop here so app doesn't crash
        }

        Cursor c = placeTable.getAll();
        while (c.moveToNext()) {
            int cid = c.getInt(c.getColumnIndexOrThrow("cid"));
            if (cid != campusid) continue;  // skip other campuses

            String name  = c.getString(c.getColumnIndexOrThrow("Pname"));
            int    price = c.getInt(c.getColumnIndexOrThrow("price_range"));
            String type  = c.getString(c.getColumnIndexOrThrow("location_type"));
            int    stars = c.getInt(c.getColumnIndexOrThrow("starsRating"));
            String link  = c.getString(c.getColumnIndexOrThrow("Location_Link"));
            int    pid   = c.getInt(c.getColumnIndexOrThrow("pid"));

            Campus camp = campusTable.getByCid(cid);
            if (camp == null) continue;

            Place p = new Place(name, camp, type, price, stars);
            p.setPid(pid);
            p.setLocation_Link(link);

            // load reviews for this place
            Cursor revCursor = reviewTable.getByPid(pid);
            while (revCursor.moveToNext()) {
                int    stuid   = revCursor.getInt(revCursor.getColumnIndexOrThrow("STUid"));
                String revText = revCursor.getString(revCursor.getColumnIndexOrThrow("review"));
                int    rating  = revCursor.getInt(revCursor.getColumnIndexOrThrow("rating"));

                User reviewer = users.get(stuid);
                if (reviewer == null) {
                    reviewer = userTable.getByUid(stuid);
                    if (reviewer != null) users.put(stuid, reviewer);
                }

                if (reviewer != null) {
                    Reviews r = new Reviews(revText, rating, reviewer, p);
                    p.addReview(r);
                }
            }
            revCursor.close();

            places.insert(p);
            HomePageList.add(p);
            lookupByName.put(name.toLowerCase(), p);
        }
        c.close();

        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(i);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

}