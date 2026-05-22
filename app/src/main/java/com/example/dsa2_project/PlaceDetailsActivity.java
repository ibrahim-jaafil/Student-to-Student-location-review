package com.example.dsa2_project;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
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

        if (place == null) {
            finish();
            return;
        }

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
            loadReviews();
}
            else{
               /* TextView NoReviews = findViewById(R.id.NoReviews);
                NoReviews.setVisibility(View.VISIBLE);*/

                //Have to fix it since the scoll cant have two children
            }

    }

    private void loadReviews() {
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

    public void addReviewButton(View v){
        showAddReviewDialog();
    }

    private void showAddReviewDialog() {
        // Create the dialog
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_review);

        Window window = dialog.getWindow();
        if (window != null) {
            // This makes the dialog background transparent so the rounded corners of your layout show
            window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.gravity = Gravity.CENTER;


            // This dims everything behind the dialog
            lp.dimAmount = 0.7f;
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

            window.setAttributes(lp);
        }

        RatingBar rating = dialog.findViewById(R.id.dialogRatingBar);
        EditText reviewComment= dialog.findViewById(R.id.etReviewComment);
        Button btnSubmit = dialog.findViewById(R.id.btnSubmitReview);
        btnSubmit.setOnClickListener(v -> {
            //Dialog dialog = new Dialog(this);

            ArrayList<User> users = place.getReviewList().outputUsers();
            if(users.contains(MainActivity.currentUser)){
                btnSubmit.setError("You have already reviewed this place!");
            }
            else {

                double ratingPutIn = rating.getRating();
                String comment = reviewComment.getText().toString();
                Reviews r = new Reviews(comment, (int) ratingPutIn, MainActivity.currentUser, place);
                place.addReview(r);
                MainActivity.currentUser.addReviewToStack(r);


                // have to make safeguards for the add if like no comment or no stars


                dialog.dismiss();
                loadReviews();
            }
        });

        dialog.show();
    }




}