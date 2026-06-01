package com.example.dsa2_project.Search;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dsa2_project.R;
import com.google.android.material.slider.Slider;

public class FiltersActivity extends AppCompatActivity {

    private TextView tvAll, tvFood, tvCafes;
    private String selectedCategory = "All";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_filters);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvAll = findViewById(R.id.tvAll);
        tvFood = findViewById(R.id.tvFood);
        tvCafes = findViewById(R.id.tvCafes);

        tvAll.setOnClickListener(v -> selectCategory("All"));
        tvFood.setOnClickListener(v -> selectCategory("Food"));
        tvCafes.setOnClickListener(v -> selectCategory("Cafes"));
    }

    private void selectCategory(String category) {
        selectedCategory = category;

        // Reset all to unselected style
        tvAll.setBackgroundResource(R.drawable.filter_chip_bg);
        tvAll.setTextColor(Color.parseColor("#49454F"));

        tvFood.setBackgroundResource(R.drawable.filter_chip_bg);
        tvFood.setTextColor(Color.parseColor("#49454F"));

        tvCafes.setBackgroundResource(R.drawable.filter_chip_bg);
        tvCafes.setTextColor(Color.parseColor("#49454F"));

        // Set selected style
        if (category.equals("All")) {
            tvAll.setBackgroundResource(R.drawable.dark_pill_bg);
            tvAll.setTextColor(Color.WHITE);
        } else if (category.equals("Food")) {
            tvFood.setBackgroundResource(R.drawable.dark_pill_bg);
            tvFood.setTextColor(Color.WHITE);
        } else if (category.equals("Cafes")) {
            tvCafes.setBackgroundResource(R.drawable.dark_pill_bg);
            tvCafes.setTextColor(Color.WHITE);
        }
    }

    public void Done(View view){
        Slider priceSlider = findViewById(R.id.priceSlider);

        int value = (int) priceSlider.getValue();


        Intent i = new Intent(this, SearchActivity.class);
        i.putExtra("price", value);
        i.putExtra("category", selectedCategory);
        i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(i);
        finish();
    }

    public void Cancel(View view){
        Intent i = new Intent(this, SearchActivity.class);
        i.putExtra("price", 0);
        i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(i);
        finish();
    }



}