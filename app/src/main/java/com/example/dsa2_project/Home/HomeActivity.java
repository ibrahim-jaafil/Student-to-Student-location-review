package com.example.dsa2_project.Home;

import static android.view.View.VISIBLE;
import static com.example.dsa2_project.MainActivity.HomePageList;
import static com.example.dsa2_project.MainActivity.places;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dsa2_project.Location.Campus;
import com.example.dsa2_project.Location.Place;
import com.example.dsa2_project.MainActivity;
import com.example.dsa2_project.Profile.ProfileActivity;
import com.example.dsa2_project.R;
import com.example.dsa2_project.Search.SearchActivity;
import com.example.dsa2_project.login_sys.User;

import java.util.ArrayList;
import java.util.Collections;

public class HomeActivity extends AppCompatActivity {

    private LinearLayout itemsContainer;

    public static ArrayList<Place> favoriteList = new ArrayList<>();

    private boolean isAdmin;
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


        User bob = MainActivity.users.get(123);



        isAdmin= getIntent().getBooleanExtra("admin",false);
        ifAdmin(isAdmin);
        loadPlaces("All");

        findViewById(R.id.navSearch).setOnClickListener(v -> {
            Intent i = new Intent(HomeActivity.this, SearchActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(i);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });

        findViewById(R.id.navProfile).setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });

        TextView tvUserName = findViewById(R.id.tvUserName);
        tvUserName.setText(MainActivity.currentUser.getUsername());


        findViewById(R.id.btnFood).setOnClickListener(v->{ loadPlaces("Food");
        });
        findViewById(R.id.btnCafes).setOnClickListener(v->{ loadPlaces("Cafes");
        });
        findViewById(R.id.btnAll).setOnClickListener(v->{ loadPlaces("All");
        });


    }

    private void ifAdmin(boolean isAdmin){
        if(isAdmin){
            findViewById(R.id.addPlace).setVisibility(VISIBLE);
            findViewById(R.id.addPlace).setOnClickListener(v->{
                showAddPlaceDialog();
            });


        }

    }


    private void loadPlaces(String orderByType) {

        itemsContainer.removeAllViews();

        Collections.sort(HomePageList, (a, b) -> Double.compare(b.getStars(), a.getStars()));

        View topSpacer = new View(this);
        int topHeight = (int) (320 * getResources().getDisplayMetrics().density);
        topSpacer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, topHeight));
        itemsContainer.addView(topSpacer);

        if(orderByType.equalsIgnoreCase("Food")){

            for (Place p: HomePageList) {
                if (p.getLocation_type().equalsIgnoreCase("Food")){
                addPlaceItem(p);
                }
            }

        }
        else if(orderByType.equalsIgnoreCase("Cafes")){

            for (Place p: HomePageList) {
                if(p.getLocation_type().equalsIgnoreCase("Cafes")) {
                    addPlaceItem(p);
                }
            }
        }
        else if(orderByType.equalsIgnoreCase("All")){

            for (Place p: HomePageList) {
                addPlaceItem(p);
            }
        }
        View bottomSpacer = new View(this);
        int bottomHeight = (int) (120 * getResources().getDisplayMetrics().density);
        bottomSpacer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, bottomHeight));
        itemsContainer.addView(bottomSpacer);

    }


    //want to add also stars(done)
    private void addPlaceItem(Place p) {
        // Inflate the item layout
        View itemView = LayoutInflater.from(this).inflate(R.layout.item_place, itemsContainer, false);
        
        TextView placeName = itemView.findViewById(R.id.placeName);
        TextView placeLocation = itemView.findViewById(R.id.placeLocation);
        LinearLayout starsContainer = itemView.findViewById(R.id.itemStarsContainer);
        ImageButton btnFavorite = itemView.findViewById(R.id.btnFavorite);
        
        placeName.setText(p.getName());

        btnFavorite.setOnClickListener(v -> {
            v.setSelected(!v.isSelected());
            if(v.isSelected()) {
                if (!favoriteList.contains(p)) {
                    favoriteList.add(p);
                }
            }
            else if(!v.isSelected()){
             favoriteList.remove(p);
            }
        });

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
      /*  int count = itemsContainer.getChildCount();
        if (count > 0) {
            // count - 1 is the index of the bottom spacer
            itemsContainer.addView(itemView, count - 1);
        } else {
            itemsContainer.addView(itemView);
        }*/

        itemsContainer.addView(itemView);

    }

    private void showAddPlaceDialog() {
        // Create the dialog
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_place);

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

        EditText placeName = dialog.findViewById(R.id.etPlaceName);
        EditText campus = dialog.findViewById(R.id.etCampus);
        EditText locationType = dialog.findViewById(R.id.etLocationType);
        EditText priceRange = dialog.findViewById(R.id.etPriceRange);
        RatingBar rating = dialog.findViewById(R.id.dialogRatingBar);

        Button btnSubmit = dialog.findViewById(R.id.btnSubmitPlace);
        btnSubmit.setOnClickListener(v -> {
            String Name = placeName.getText().toString();
            //String campusName = campus.getText().toString();
            String type = campus.getText().toString();
            int price = Integer.parseInt(priceRange.getText().toString());

            Place p = new Place(Name,South,type,price,(int)rating.getRating());
            HomePageList.add(p);
            places.insert(p);
            MainActivity.lookupByName.put(p.getName().trim().toLowerCase(),p);

            dialog.dismiss();
            loadPlaces("All");
                // have to make safeguards for the add if like no comment or no stars
        });

        dialog.show();
    }

}