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
    }

    private void performSearch() {
        String query = etSearch.getText().toString().trim();
        if (!query.isEmpty()) {
            addRecentSearch(query);
            etSearch.setText(""); // Clear search bar after adding
        }
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

    public void HomeButton(View view){
        Intent i = new Intent(SearchActivity.this, HomeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(i);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}