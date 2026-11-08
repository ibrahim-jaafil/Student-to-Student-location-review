package com.example.dsa2_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.dsa2_project.API.Auth;
import com.example.dsa2_project.API.Retrofitinstance;
import com.example.dsa2_project.API.load.loadCampusesResponse;
import com.example.dsa2_project.API.load.loadPlacesRequest;
import com.example.dsa2_project.API.load.loadPlacesResponse;
import com.example.dsa2_project.API.load.loadUsersResponse;
import com.example.dsa2_project.Location.Campus;
import com.example.dsa2_project.Location.Place;
import com.example.dsa2_project.data_structures.AvlTree;
import com.example.dsa2_project.login_sys.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static HashMap<Integer, User> users = new HashMap<>();
    public static User currentUser;
    public static ArrayList<Place> favoriteList = new ArrayList<>();

    public static AvlTree places = new AvlTree();

    public static ArrayList<Place> HomePageList = new ArrayList<>();
    public static HashMap<String, Place> lookupByName = new HashMap<>();

    //public static List<Campus> campuseslist = new ArrayList<>();
    public static HashMap<Integer , Campus> campuseslist = new HashMap<>();

    public static HashMap<Integer , Place> placelistbypid= new HashMap<>();




    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


   /*     dbHelper       = new DatabaseHelper(this);
        campusTable    = new CampusTable(dbHelper);
        userTable      = new UsersTable(dbHelper);
        placeTable     = new PlaceTable(dbHelper);
        reviewTable    = new ReviewTable(dbHelper);
        favoritesTablefavoritesTable = new FavoritesTable(dbHelper);

        if (campusTable.getCount() == 0) {

            campusTable.insert("South",  "Besiktas");
            campusTable.insert("North",  "Besiktas");
            campusTable.insert("Galata", "Beyoglu");
            campusTable.insert("Dental", "Balmumcu");

            int southid  = campusTable.getCidByName("South");
            int northid  = campusTable.getCidByName("North");
            int galataid = campusTable.getCidByName("Galata");
            int dentalid = campusTable.getCidByName("Dental");

            userTable.insert("admin",   "admin",   "111", 111);
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
*/
        //  EVERY LAUNCH — load campuses and users



        Auth api = Retrofitinstance.getservice();
        api.getCampuses().enqueue(new Callback <List<loadCampusesResponse>>(){
                @Override
            public void onResponse(Call<List<loadCampusesResponse>> call, Response<List<loadCampusesResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<loadCampusesResponse> campuses = response.body();
                    for (loadCampusesResponse c : campuses) {
                        Campus campus = new Campus(c.getCid(),c.getCName(), c.getLocation());
                        campuseslist.put(c.getCid(),campus);
                    }

                    Log.d("", ""+response.code());
                    System.out.println(campuseslist);
                }
                else{
                    Log.d("didnt load campuses", "code=" + response.code());

                }
            }
                @Override
            public void onFailure(Call<List<loadCampusesResponse>> call, Throwable t) {
                    Log.d("didnt load campuses", "code=" + t.getMessage());
            }
        });

        api.loadUsers().enqueue(new Callback <List<loadUsersResponse>>(){
            @Override
            public void onResponse(Call <List<loadUsersResponse>> call, Response <List<loadUsersResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<loadUsersResponse> usersresponse = response.body();
                    for (loadUsersResponse u: usersresponse) {
                        User user = new User(u.getUsername(),u.getEmail(),u.getSTUid());
                        users.put(u.getSTUid(),user);
                    }
                    Log.d("users loaded", ""+response.code()+" "+users.size());
                }
                else{
                    Log.d("didnt load users", "code=" + response.code());

                                       }
            }
            @Override
            public void onFailure(Call<List<loadUsersResponse>> call, Throwable t) {
                Log.d("didnt load users", "code=" + t.getMessage());
            }

        });



       findViewById(R.id.SouthLoc).setOnClickListener(this::PickCampus);
        findViewById(R.id.NorthLoc).setOnClickListener(this::PickCampus);
        findViewById(R.id.GalataLoc).setOnClickListener(this::PickCampus);
       findViewById(R.id.DentalLoc).setOnClickListener(this::PickCampus);

    }

    public static String campus;
    public static int campusid;

    //next is this

    public void PickCampus(View view) {
        Button b = (Button) view;
            campus = b.getText().toString();  // e.g. "South"

           HomePageList.clear();
           places     = new AvlTree();  // have to change it so an avl tree isnt made each time you press a button
         lookupByName.clear();

        // match campus name case-insensitively


             campusid = -1;

             campuseslist.forEach((key, value)->{
                 if (value.getName().equalsIgnoreCase(campus)) {
                     campusid= value.getCid();
                     return;
                 }


             });
             Log.d("id",""+campusid);

        if (campusid == -1) {
            Log.e("PickCampus", "Campus not found: " + campus);
            return; // stop here so app doesn't crash
        }


        Auth api = Retrofitinstance.getservice();
        api.loadPlaces(new loadPlacesRequest(campusid)).enqueue(new Callback <List<loadPlacesResponse>>(){

            @Override
            public void onResponse(Call <List<loadPlacesResponse>> call, Response <List<loadPlacesResponse>> response){
                if (response.isSuccessful() && response.body() != null) {
                    List<loadPlacesResponse> place = response.body();
                    for (loadPlacesResponse r : place){
                        Place p = new Place(r.getName(), campuseslist.get(r.getCid()), r.getPid(),r.getLocation_type(), r.getPrice_range(), r.getStarsRating());
                        places.insert(p);
                        HomePageList.add(p);
                        lookupByName.put(p.getName().toLowerCase(), p);
                        placelistbypid.put(r.getPid(),p);
                    }
                    Log.d("",""+placelistbypid.size());
                    Log.d("places are loaded", ""+response.code());
                }
                else{
                    Log.d("didnt load places", "code=" + response.code());
                }

            }
            @Override
            public void onFailure(Call <List<loadPlacesResponse>> call, Throwable t) {
                Log.d("didnt load places , error", "code=" + t.getMessage());
            }

        });



// i dont want to load reviews at launch
// because i would have to load all users and assign each review to the users and that a security problem
     /*   api.loadReviews().enqueue(new Callback <List<loadReviewsResponse>>() {

            @Override
            public void onResponse(Call <List<loadReviewsResponse>> call, Response <List<loadReviewsResponse>> response){
                if(response.isSuccessful() && response.body()!=null){
                    List<loadReviewsResponse> reviews = response.body();
                    for (loadReviewsResponse r : reviews){
                      //  Log.d("",""+r.getSTUid());
                        Reviews review = new Reviews(r.getReview(), r.getRating(), users.get(r.getSTUid()), placelistbypid.get(r.getPid()));
                        placelistbypid.get(r.getPid()).addReview(review);
                    }

                    Log.d("reviews are loaded", ""+response.code());

                }
                else{
                    Log.d("didnt load reviews , unsuccessfull response", "code=" + response.code());
                }
            }
            public void onFailure( Call <List<loadReviewsResponse>> call, Throwable t){
                Log.d("didnt load reviews , error", "code=" + t.getMessage());
            }


                                  });*/

         Intent i = new Intent(MainActivity.this, LoginActivity.class);
           startActivity(i);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
           finish();

    }
}
