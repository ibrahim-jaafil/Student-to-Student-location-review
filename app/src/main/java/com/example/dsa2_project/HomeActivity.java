package com.example.dsa2_project;

import static com.example.dsa2_project.MainActivity.campus;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dsa2_project.Location.Campus;
import com.example.dsa2_project.Location.Place;
import com.example.dsa2_project.Location.Reviews;
import com.example.dsa2_project.login_sys.User;

import java.util.Collections;

public class HomeActivity extends AppCompatActivity {

    private LinearLayout itemsContainer;

    Campus South;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        itemsContainer = findViewById(R.id.itemsContainer);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // To add items manually, call a method like this.
        // If there are no places, simply don't call it or pass an empty list.
      South = new Campus("south",campus);
        South.addLocation(new Place("the Best" ,South,500,4));
        South.addLocation( new Place("nitz",South,400,3));
        Place p1 = new Place("upperdeck",South,600,5);
        p1.addReview(new Reviews("so good",5,new User("bob","bob","123",123)));
       South.addLocation(p1);
        South.addLocation( new Place("atara",South,700,2));

        loadPlaces();

    }


    private void loadPlaces() {
       Collections.sort(South.getPlaces(), (a, b) -> Double.compare(b.getStars(), a.getStars()));

        for (Place p: South.getPlaces()) {
            addPlaceItem(p);
        }


        // Here you would typically loop through your data
        // For example:
        // for (Place place : myPlacesList) { addPlaceItem(place.getName(), place.getLocation()); }

        // Manually adding some items as an example:
       /* addPlaceItem("Central Park", "New York, NY");
        addPlaceItem("Times Square", "Manhattan, NY");
        addPlaceItem("Empire State Building", "Midtown Manhattan, NY");*/
    }


    //want to add also stars(done)
    private void addPlaceItem(Place p) {
        // Inflate the item layout
        View itemView = LayoutInflater.from(this).inflate(R.layout.item_place, itemsContainer, false);
        
        // Find the TextViews and set the content
        TextView placeName = itemView.findViewById(R.id.placeName);
        TextView placeLocation = itemView.findViewById(R.id.placeLocation);
        LinearLayout starsContainer = itemView.findViewById(R.id.itemStarsContainer);
        
        placeName.setText(p.getName());
        for (int i = 0; i < p.getStars(); i++) {
            ImageView star = new ImageView(this);
            star.setImageResource(R.drawable.ic_star);

            int sizeInPx = (int) (20 * getResources().getDisplayMetrics().density);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(sizeInPx, sizeInPx);

            if (i > 0) {
                params.setMarginStart((int) (4 * getResources().getDisplayMetrics().density));
            }

            star.setLayoutParams(params);
            starsContainer.addView(star);
        }

        // Make the item clickable and open Details page
        itemView.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, PlaceDetailsActivity.class);
            intent.putExtra("PLACE_NAME", p.getName());
            intent.putExtra("PLACE_STARS", p.getStars());
            intent.putExtra("PLACE", p);


            startActivity(intent);
        });

        // We add the item before the last view (the bottom spacer)
        int count = itemsContainer.getChildCount();
        if (count > 0) {
            itemsContainer.addView(itemView, count - 1);
        } else {
            itemsContainer.addView(itemView);
        }
    }

    public void SearchButton(View view){
        Intent i = new Intent(HomeActivity.this, SearchActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(i);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}