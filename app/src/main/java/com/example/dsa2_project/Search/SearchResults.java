package com.example.dsa2_project.Search;

import static com.example.dsa2_project.MainActivity.favoriteList;
import static com.example.dsa2_project.MainActivity.campus;
import static com.example.dsa2_project.MainActivity.lookupByName;
import static com.example.dsa2_project.MainActivity.places;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dsa2_project.Home.PlaceDetailsActivity;
import com.example.dsa2_project.Location.Place;
import com.example.dsa2_project.R;

import java.util.ArrayList;
import java.util.Collections;

public class SearchResults extends AppCompatActivity {

    private LinearLayout ResultsContainer;
    private ImageButton backButton;

    private LinearLayout filterChipsContainer;

    private String query;
    int priceRange ;
    private String category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_results);
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //tvSearchHint = findViewById(R.id.tvSearchHint);
        ResultsContainer = findViewById(R.id.ResultsContainer);
        backButton = findViewById(R.id.backButton);

        filterChipsContainer = findViewById(R.id.filterChipsContainer);

        backButton.setOnClickListener(v -> finish());

        category= getIntent().getStringExtra("category");
         query = getIntent().getStringExtra("QUERY");


        priceRange=getIntent().getIntExtra("price",0);
if(priceRange != 0 ) {
    addFilterChip(priceRange);
}

        loadResults();

    }


    private void loadResults() {
        ResultsContainer.removeAllViews();
        TextView noResults = findViewById(R.id.NoResults);
        boolean foundAny = false; // The safest way to track if we added anything

        View topSpacer = new View(this);
        int topHeight = (int) (20 * getResources().getDisplayMetrics().density);
        topSpacer.setLayoutParams(new LinearLayout.LayoutParams(1, topHeight));
        ResultsContainer.addView(topSpacer);

        String cleanQuery="";
        if(query!=null){
            cleanQuery=query.toLowerCase().trim();
        }
        // CASE 1: Both Price and Name Search
        if (priceRange != 0 && !cleanQuery.isEmpty()) {
            ArrayList<Place> priceList = places.search(priceRange);
            Place namedPlace = lookupByName.get(cleanQuery);

            if (namedPlace != null && priceList.contains(namedPlace)) {
                if (namedPlace.getCampus().getName().equals(campus)) {
                    additems(namedPlace);
                    foundAny = true;
                }
            }
        }
        // CASE 2: Price Range (and optional Category)
        else if (priceRange != 0) {
            ArrayList<Place> list = places.search(priceRange);
            if (!list.isEmpty()) {
                Collections.sort(list, (a, b) -> Double.compare(b.getPrice_range(), a.getPrice_range()));
                for (Place item : list) {
                    if (item.getCampus().getName().equals(campus)) {
                        if (category == null || category.equalsIgnoreCase("All") || item.getLocation_type().equalsIgnoreCase(category)) {
                            additems(item);
                            foundAny = true;
                        }
                    }
                }
            }
        }
        // CASE 3: Name Search only
        else if (!cleanQuery.isEmpty()) {
            Place p = lookupByName.get(cleanQuery);
            if (p != null && p.getCampus().getName().equals(campus)) {
                additems(p);
                foundAny = true;
            }
        }

        // FINAL CHECK: Toggle "No Results" based on our flag
        if (foundAny) {
            noResults.setVisibility(View.GONE);
        } else {
            noResults.setVisibility(View.VISIBLE);
        }
    }

    private void additems(Place p){

        View resultItem = LayoutInflater.from(this).inflate(R.layout.item_place, ResultsContainer, false);
        TextView placeName = resultItem.findViewById(R.id.placeName);
        TextView price = resultItem.findViewById(R.id.placeLocation);
        LinearLayout placeStarsContainer = resultItem.findViewById(R.id.itemStarsContainer);


        placeName.setText(p.getName());
        price.setText(String.valueOf(p.getPrice_range()));

        for (int i = 0; i < p.getStars(); i++) {
            ImageView star = new ImageView(this);
            star.setImageResource(R.drawable.ic_star);

            int sizeInPx = (int) (20 * getResources().getDisplayMetrics().density);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(sizeInPx, sizeInPx);

            if (i > 0) {
                params.setMarginStart((int) (4 * getResources().getDisplayMetrics().density));
            }

            star.setLayoutParams(params);
            placeStarsContainer.addView(star);
        }

        ImageButton btnFavorite = resultItem.findViewById(R.id.btnFavorite);

        placeName.setText(p.getName());

        if (favoriteList.contains(p)) {
            btnFavorite.setSelected(true);
        }
        else {
            btnFavorite.setSelected(false);
        }
        btnFavorite.setOnClickListener(v -> {
            boolean newState = !v.isSelected();
            v.setSelected(newState);

            if(newState) {
                if (!favoriteList.contains(p)) {
                    favoriteList.add(p);
                }
            }
            else {
                favoriteList.remove(p);
            }
        });

        resultItem.setOnClickListener(v -> {
            Intent intent = new Intent(this, PlaceDetailsActivity.class);
            intent.putExtra("PLACE_NAME", p.getName());
            intent.putExtra("PLACE_STARS", p.getStars());
            intent.putExtra("PLACE", p);


            startActivity(intent);
        });

        ResultsContainer.addView(resultItem);
    }

    private void addFilterChip(int text) {

        View chipView = getLayoutInflater().inflate(R.layout.item_filter_chip, filterChipsContainer, false);
        TextView chipText = chipView.findViewById(R.id.chipText);
        View chipClose = chipView.findViewById(R.id.chipClose);

        chipText.setText(String.valueOf(text));
        chipClose.setOnClickListener(v -> {
            filterChipsContainer.removeView(chipView);
            // Here you would also update your search results
        });

        filterChipsContainer.addView(chipView);
    }
}
