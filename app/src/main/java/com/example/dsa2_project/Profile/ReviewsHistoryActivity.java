package com.example.dsa2_project.Profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dsa2_project.Location.Reviews;
import com.example.dsa2_project.MainActivity;
import com.example.dsa2_project.R;

import java.util.ArrayList;

public class ReviewsHistoryActivity extends AppCompatActivity {

    private LinearLayout reviewsContainer;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reviews_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        reviewsContainer = findViewById(R.id.reviewsContainer);
        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> {
            Intent i = new Intent(this, ProfileActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(i);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
                );

        loadReviews();
    }

    private void loadReviews() {
        reviewsContainer.removeAllViews();
        if (MainActivity.currentUser != null && !MainActivity.currentUser.getReviews().isEmpty()) {


            ArrayList<Reviews> reviewsList = MainActivity.currentUser.getReviews().outputStack();

            for (Reviews review : reviewsList) {

                View reviewItem = LayoutInflater.from(this).inflate(R.layout.item_review, reviewsContainer, false);
                TextView placeName = reviewItem.findViewById(R.id.reviewerName);
                TextView reviewComment = reviewItem.findViewById(R.id.reviewComment);
                TextView reviewDate = reviewItem.findViewById(R.id.reviewDate);
                LinearLayout reviewStarsContainer = reviewItem.findViewById(R.id.reviewStarsContainer);

                if (review.getPlace() != null) {
                    placeName.setText(review.getPlace().getName());
                } else {
                    placeName.setText("Unknown Location");
                }
                reviewDate.setText(review.getDate());
                reviewComment.setText(review.getReview());
                starsFunc(review.getStars(), reviewStarsContainer);

                android.util.Log.d("Reviews", "Found review: " + review.getReview());
                reviewsContainer.addView(reviewItem);
            }
        }

    }

    public void starsFunc(int starsCount, LinearLayout reviewStarsContainer){
        for (int i = 0; i < starsCount; i++) {
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
    }


}