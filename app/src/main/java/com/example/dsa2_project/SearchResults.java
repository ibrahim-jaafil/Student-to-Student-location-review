package com.example.dsa2_project;

import static com.example.dsa2_project.MainActivity.lookupByName;
import static com.example.dsa2_project.MainActivity.places;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dsa2_project.Location.Place;

import java.util.ArrayList;
import java.util.Collections;

public class SearchResults extends AppCompatActivity {

    private TextView tvSearchHint;
    private LinearLayout ResultsContainer;
    private ImageButton backButton;

    private LinearLayout filterChipsContainer;

    private String query;
    int priceRange ;
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

        tvSearchHint = findViewById(R.id.tvSearchHint);
        ResultsContainer = findViewById(R.id.ResultsContainer);
        backButton = findViewById(R.id.backButton);

        filterChipsContainer = findViewById(R.id.filterChipsContainer);

        backButton.setOnClickListener(v -> finish());

        // Get the search query from the Intent
         query = getIntent().getStringExtra("QUERY");
     /*   if (query != null && !query.isEmpty()) {
            tvSearchHint.setText(query);
        }*/

        priceRange=getIntent().getIntExtra("price",0);
if(priceRange != 0 ) {
    addFilterChip(priceRange);
}

        loadResults();
    }

    private void loadResults() {
        ResultsContainer.removeAllViews();

        ArrayList<Place> list = places.search(priceRange);

          if (priceRange!=0 && query!=null &&!query.isEmpty() ) {
            Collections.sort(list, (a, b) -> Double.compare(b.getPrice_range(), a.getPrice_range()));

            if(list.contains(lookupByName.get((query).toLowerCase()))){
                additems(lookupByName.get((query).toLowerCase()));
            }
            else{
                ResultsContainer.removeAllViews();
                TextView NoResults = findViewById(R.id.NoResults);
                NoResults.setVisibility(View.VISIBLE);
            }

        }

        else if(priceRange!=0) {


            if(places.SimpleSearch(priceRange)!=null) {
                Collections.sort(list, (a, b) -> Double.compare(b.getPrice_range(), a.getPrice_range()));

// if the filter is applied but no value for it in the tree put no ressults
                for (Place item : list) {
                    additems(item);
                }
            }
            else{
                ResultsContainer.removeAllViews();
                TextView NoResults = findViewById(R.id.NoResults);
                NoResults.setVisibility(View.VISIBLE);


            }
        }
       else if(query!=null &&!query.isEmpty() ) {
                additems(lookupByName.get(query.toLowerCase()));

            }

       else {
            ResultsContainer.removeAllViews();
            TextView NoResults = findViewById(R.id.NoResults);
            NoResults.setVisibility(View.VISIBLE);


        }
    }

    private void additems(Place p){

        View resultItem = LayoutInflater.from(this).inflate(R.layout.item_review, ResultsContainer, false);
        TextView placeName = resultItem.findViewById(R.id.reviewerName);
        TextView price = resultItem.findViewById(R.id.reviewComment);
        TextView reviewDate = resultItem.findViewById(R.id.reviewDate);
       // LinearLayout reviewStarsContainer = resultItem.findViewById(R.id.reviewStarsContainer);


        placeName.setText(p.getName());
        price.setText(String.valueOf(p.getPrice_range()));
        reviewDate.setText("");

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
