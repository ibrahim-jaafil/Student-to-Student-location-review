package com.example.dsa2_project.Search;

import static com.example.dsa2_project.MainActivity.currentUser;
import static com.example.dsa2_project.MainActivity.favoriteList;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dsa2_project.Home.HomeActivity;
import com.example.dsa2_project.Home.PlaceDetailsActivity;
import com.example.dsa2_project.Location.Place;
import com.example.dsa2_project.Profile.ProfileActivity;
import com.example.dsa2_project.R;

public class SearchActivity extends AppCompatActivity {

    private EditText etSearch;
    private LinearLayout recentSearchesContainer;
    private LinearLayout filterChipsContainer;

    private int selectedPrice = 0;
    private String category ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        etSearch = findViewById(R.id.etSearch);
        recentSearchesContainer = findViewById(R.id.recentSearchesContainer);
        filterChipsContainer = findViewById(R.id.filterChipsContainer);

        checkFilters();

        // Listen for "Search" action on keyboard
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                performSearch();
                return true;
            }
            return false;
        });

        // Listen for search icon click
        findViewById(R.id.ivSearchIcon).setOnClickListener(v ->
        {
            if(!etSearch.getText().toString().isEmpty() || selectedPrice!=0){
            performSearch();
        }


        });

        // Listen for filter icon click
        findViewById(R.id.ivFilterIcon).setOnClickListener(v -> {
            Intent i = new Intent(SearchActivity.this, FiltersActivity.class);
            startActivity(i);
        });

        findViewById(R.id.navHome).setOnClickListener(v -> {
            Intent i = new Intent(SearchActivity.this, HomeActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(i);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });

        findViewById(R.id.navProfile).setOnClickListener(v-> {
            Intent i = new Intent(this, ProfileActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(i);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        });

        loadRecentPlaces();
    }



    private void loadRecentPlaces() {
        if (currentUser == null || currentUser.getLastViewedPlaces() == null) return;

        recentSearchesContainer.removeAllViews();

        if(!currentUser.getLastViewedPlaces().isEmpty()){
            for (Place p : currentUser.getLastViewedPlaces().outputStack()) {
                View placeItem = LayoutInflater.from(this).inflate(R.layout.item_place, recentSearchesContainer, false);

                TextView placeName = placeItem.findViewById(R.id.placeName);
                LinearLayout starsContainer = placeItem.findViewById(R.id.itemStarsContainer);
                ImageButton btnFavorite = placeItem.findViewById(R.id.btnFavorite);

                placeName.setText(p.getName());
                if (isPlaceFavorited(p)) {
                    btnFavorite.setSelected(true);
                }
                else {
                    btnFavorite.setSelected(false);
                }
                btnFavorite.setOnClickListener(v -> {
                    boolean newState = !v.isSelected();
                    v.setSelected(newState);

                    if(newState) {
                        if (!isPlaceFavorited(p)) {
                            favoriteList.add(p);
                        }
                    }
                    else {
                        favoriteList.removeIf(fav -> fav.getName().equalsIgnoreCase(p.getName()));                    }
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

                placeItem.setOnClickListener(v -> {
                    Intent intent = new Intent(this, PlaceDetailsActivity.class);
                    intent.putExtra("PLACE_NAME", p.getName());
                    intent.putExtra("PLACE_STARS", p.getStars());
                    intent.putExtra("PLACE", p);
                    startActivity(intent);
                });
                recentSearchesContainer.addView(placeItem);
            }
        }
    }

    // due to Intent serialization
    private boolean isPlaceFavorited(Place p) {
        if (favoriteList == null) return false;
        for (Place fav : favoriteList) {
            // Compare by name because the objects might be different instances
            if (fav.getName().equalsIgnoreCase(p.getName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent); // This updates the activity intent to the new one

        if (intent.hasExtra("price")) {
            selectedPrice = intent.getIntExtra("price",0);
            checkFilters();
        }
        if (intent.hasExtra("category")) {
            category = intent.getStringExtra("category");

        }

    }

    private void performSearch() {

       String query = etSearch.getText().toString().trim();
           etSearch.setText("");
            Intent i = new Intent(this, SearchResults.class);
            i.putExtra("price",selectedPrice);
            i.putExtra("QUERY", query);
            i.putExtra("category",category);
            startActivity(i);


    }


    @Override
    protected void onResume() {
        super.onResume();
        checkFilters();
        loadRecentPlaces();
    }

    private void checkFilters() {
        filterChipsContainer.removeAllViews();
        if (selectedPrice != 0) {
            addFilterChip(selectedPrice);
        }
    }

    private void addFilterChip(int text) {
        View chipView = getLayoutInflater().inflate(R.layout.item_filter_chip, filterChipsContainer, false);
        TextView chipText = chipView.findViewById(R.id.chipText);
        View chipClose = chipView.findViewById(R.id.chipClose);

        chipText.setText(String.valueOf(text));
        chipClose.setOnClickListener(v -> {
            filterChipsContainer.removeView(chipView);
            selectedPrice = 0;
        });

        filterChipsContainer.addView(chipView);
    }


}