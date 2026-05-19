package com.example.dsa2_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dsa2_project.Location.Place;
import com.example.dsa2_project.Location.Reviews;
import com.example.dsa2_project.login_sys.User;


import java.util.ArrayList;

public class PlaceDetailsActivity extends AppCompatActivity {

    private LinearLayout detailItemsContainer;
    private Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);

        place = (Place) getIntent().getSerializableExtra("PLACE");
        String placeName = getIntent().getStringExtra("PLACE_NAME");
        int stars = getIntent().getIntExtra("PLACE_STARS", 0);

        TextView nameTextView = findViewById(R.id.detailPlaceName);
        LinearLayout starsContainer = findViewById(R.id.starsContainer);
        detailItemsContainer = findViewById(R.id.detailItemsContainer);

        if (placeName != null) {
            nameTextView.setText(placeName);
        }

        // Add star icons based on the rating
        for (int i = 0; i < stars; i++) {
            ImageView star = new ImageView(this);
            star.setImageResource(R.drawable.ic_star);
            
            int sizeInPx = (int) (24 * getResources().getDisplayMetrics().density);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(sizeInPx, sizeInPx);
            
            if (i > 0) {
                params.setMarginStart((int) (8 * getResources().getDisplayMetrics().density));
            }

            star.setLayoutParams(params);
            starsContainer.addView(star);
        }

        findViewById(R.id.backButton).setOnClickListener(v -> finish());
            if(!place.getReviewList().isEmpty()) {
            loadDummyReviews();
}

    }


    private void loadDummyReviews() {
        addReviewItem(place.getReviewList().outputReviewInfo());

    }

    private void addReviewItem(ArrayList<String> a) {
        View reviewItem = LayoutInflater.from(this).inflate(R.layout.item_review, detailItemsContainer, false);

        TextView reviewerName = reviewItem.findViewById(R.id.reviewerName);
        TextView reviewComment = reviewItem.findViewById(R.id.reviewComment);
        LinearLayout reviewStarsContainer = reviewItem.findViewById(R.id.reviewStarsContainer);

        reviewerName.setText(a.get(0));
        reviewComment.setText(a.get(1));


        // Add stars to the review item
        for (int i = 0; i < Integer.parseInt(a.get(2)); i++) {
            ImageView star = new ImageView(this);
            star.setImageResource(R.drawable.ic_star);

            int sizeInPx = (int) (16 * getResources().getDisplayMetrics().density);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(sizeInPx, sizeInPx);

            if (i > 0) {
                params.setMarginStart((int) (4 * getResources().getDisplayMetrics().density));
            }

            star.setLayoutParams(params);
            reviewStarsContainer.addView(star);
        }
        detailItemsContainer.addView(reviewItem);
    }




}