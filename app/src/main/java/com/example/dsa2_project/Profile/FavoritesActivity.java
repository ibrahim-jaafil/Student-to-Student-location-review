package com.example.dsa2_project.Profile;

import static com.example.dsa2_project.Home.HomeActivity.favoriteList;

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

import com.example.dsa2_project.Location.Place;
import com.example.dsa2_project.Home.PlaceDetailsActivity;
import com.example.dsa2_project.R;

public class FavoritesActivity extends AppCompatActivity {
    private LinearLayout favoritesContainer;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_favorites);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        favoritesContainer = findViewById(R.id.favoritesContainer);
        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> {
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });

        loadFavorites();
    }

    public void loadFavorites() {
        favoritesContainer.removeAllViews();
        if (favoriteList != null) {
            for (Place favorite : favoriteList) {
                addPlaceFavorite(favorite);
            }
        }
    }

    private void addPlaceFavorite(Place p) {
        View itemView = LayoutInflater.from(this).inflate(R.layout.item_place, favoritesContainer, false);

        TextView placeName = itemView.findViewById(R.id.placeName);
        TextView placeLocation = itemView.findViewById(R.id.placeLocation);
        LinearLayout starsContainer = itemView.findViewById(R.id.itemStarsContainer);
        ImageButton btnFavorite = itemView.findViewById(R.id.btnFavorite);

        placeName.setText(p.getName());
        btnFavorite.setSelected(true); // Since it's in the favorites list

        btnFavorite.setOnClickListener(v -> {
            v.setSelected(!v.isSelected());
            if (!v.isSelected()) {
                favoriteList.remove(p);
                favoritesContainer.removeView(itemView);
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

        itemView.setOnClickListener(v -> {
            Intent intent = new Intent(FavoritesActivity.this, PlaceDetailsActivity.class);
            intent.putExtra("PLACE_NAME", p.getName());
            intent.putExtra("PLACE_STARS", p.getStars());
            intent.putExtra("PLACE", p);
            startActivity(intent);
        });

        favoritesContainer.addView(itemView);
    }
}