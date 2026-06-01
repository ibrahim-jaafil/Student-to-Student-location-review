package com.example.dsa2_project.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dsa2_project.Home.HomeActivity;
import com.example.dsa2_project.MainActivity;
import com.example.dsa2_project.R;
import com.example.dsa2_project.Search.SearchActivity;
import com.example.dsa2_project.login_sys.User;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupUserProfile();
        setupNavigation();
    }

    private void setupUserProfile() {
        User currentUser = MainActivity.currentUser;
        if (currentUser != null) {
            TextView profileName = findViewById(R.id.profileName);
            TextView profileId = findViewById(R.id.profileId);
            
            profileName.setText(currentUser.getUsername());
            profileId.setText(String.valueOf(currentUser.getSTUid()));
        }
    }

    private void setupNavigation() {
        findViewById(R.id.navHome).setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });

        findViewById(R.id.navSearch).setOnClickListener(v -> {
            Intent intent = new Intent(this, SearchActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
    }

    public void ReviewsButton(View view){
        Intent i = new Intent(this, ReviewsHistoryActivity.class);
        startActivity(i);
       //need to add animation
    }
    public void FavoritesButton(View view){
        Intent i = new Intent(this, FavoritesActivity.class);
        startActivity(i);
    }

}
