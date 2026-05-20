package com.example.dsa2_project;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity {

    private EditText etSearch;
    private LinearLayout recentSearchesContainer;
    private LinearLayout filterChipsContainer;

    private int selectedPrice = 0;

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
        findViewById(R.id.ivSearchIcon).setOnClickListener(v -> performSearch());

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

    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent); // This updates the activity intent to the new one

        if (intent.hasExtra("price")) {
            selectedPrice = intent.getIntExtra("price",0);
            checkFilters();
        }
    }

    private void performSearch() {

        String priceRange = getIntent().getStringExtra("price");


       // String query = etSearch.getText().toString().trim();
      //  if (!query.isEmpty()) {
         //   addRecentSearch(query);
          //  etSearch.setText("");
            Intent i = new Intent(this, SearchResults.class);
            i.putExtra("price",selectedPrice);
         //   i.putExtra("QUERY", query);
            startActivity(i);

       // }
    }


    @Override
    protected void onResume() {
        super.onResume();
        checkFilters();
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

    private void addRecentSearch(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTextColor(getResources().getColor(android.R.color.black));
        textView.setTextSize(16);
        textView.setPadding(0, 8, 0, 8);
        
        // Add at the top of the list
        recentSearchesContainer.addView(textView, 0);
    }

}