package com.example.dsa2_project.Profile;

import static com.example.dsa2_project.MainActivity.HomePageList;

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

import com.example.dsa2_project.Location.Place;
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
        if (MainActivity.currentUser != null) {
            // Re-load reviews from Database to ensure persistence sync
            MainActivity.currentUser.getReviews().clear();
            android.database.Cursor revCursor = MainActivity.reviewTable.getByUser(MainActivity.currentUser.getSTUid());
            while (revCursor.moveToNext()) {
                String revText = revCursor.getString(revCursor.getColumnIndexOrThrow("review"));
                int stars = revCursor.getInt(revCursor.getColumnIndexOrThrow("rating"));
                int pid = revCursor.getInt(revCursor.getColumnIndexOrThrow("pid"));
                
                Place place = null;
                for (Place p : MainActivity.HomePageList) {
                    if (p.getPid() == pid) {
                        place = p;
                        break;
                    }
                }
                
                if (place != null) {
                    new Reviews(revText, stars, MainActivity.currentUser, place);
                }
            }
            revCursor.close();

            ArrayList<Reviews> reviewsList = MainActivity.currentUser.getReviews().outputReviewInfo();

            for (Reviews review : reviewsList) {

                View reviewItem = LayoutInflater.from(this).inflate(R.layout.item_review, reviewsContainer, false);
                TextView placeName = reviewItem.findViewById(R.id.reviewerName);
                TextView reviewComment = reviewItem.findViewById(R.id.reviewComment);
                TextView reviewDate = reviewItem.findViewById(R.id.reviewDate);
                LinearLayout reviewStarsContainer = reviewItem.findViewById(R.id.reviewStarsContainer);



                if(review.getUser().getUsername().equalsIgnoreCase(MainActivity.currentUser.getUsername())){
                    ImageView trash = reviewItem.findViewById(R.id.btnDeleteReview);
                    trash.setVisibility(View.VISIBLE);
                }

                reviewItem.findViewById(R.id.btnDeleteReview).setOnClickListener(v-> {
                    // 1. Remove from Database
                    MainActivity.reviewTable.deleteReview(
                            MainActivity.currentUser.getSTUid(),
                            review.getPlace().getPid(),
                            review.getReview()
                    );

                    review.getPlace().getReviewList().remove(review);

                    for (Place p : HomePageList) {
                        if (p.getName().equalsIgnoreCase(review.getPlace().getName())) {
                            p.getReviewList().remove(review);

                        }
                    }
                    MainActivity.currentUser.getReviews().remove(review);
                    loadReviews();

                });
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