package com.example.dsa2_project;

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

public class SearchResults extends AppCompatActivity {

    private TextView tvSearchHint;
    private LinearLayout ResultsContainer;
    private ImageButton backButton;

    private LinearLayout filterChipsContainer;

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
        //String query = getIntent().getStringExtra("QUERY");
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

        ArrayList<Place> list = places.search(priceRange);

        for (Place item:list) {
            additems(item);

        }
        // This method would be used to dynamically populate the resultsContainer
        // based on the search query. For now, the layout uses placeholder Views.
    }

    private void additems(Place p){

        View resultItem = LayoutInflater.from(this).inflate(R.layout.item_review, ResultsContainer, false);
        TextView placeName = resultItem.findViewById(R.id.reviewerName);
        TextView price = resultItem.findViewById(R.id.reviewComment);
       // LinearLayout reviewStarsContainer = resultItem.findViewById(R.id.reviewStarsContainer);


        placeName.setText(p.getName());
        price.setText(String.valueOf(p.getPrice_range()));

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
