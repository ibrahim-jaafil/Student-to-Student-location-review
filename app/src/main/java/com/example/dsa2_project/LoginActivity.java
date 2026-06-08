package com.example.dsa2_project;

import static com.example.dsa2_project.MainActivity.campus;
import static com.example.dsa2_project.MainActivity.currentUser;
import static com.example.dsa2_project.MainActivity.favoriteList;
import static com.example.dsa2_project.MainActivity.favoritesTable;
import static com.example.dsa2_project.MainActivity.lookupByName;
import static com.example.dsa2_project.MainActivity.users;

import android.database.Cursor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dsa2_project.Home.HomeActivity;
import com.example.dsa2_project.Location.Place;
import com.example.dsa2_project.Location.Reviews;
import com.example.dsa2_project.data_structures.InputValidator;
import com.example.dsa2_project.login_sys.User;

public class LoginActivity extends AppCompatActivity {

   // private String campusName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

       // campusName= getIntent().getStringExtra("campus");
    }


    // login button
    public void login(View view) {
        EditText passwordInput = findViewById(R.id.etPassword);
        EditText IDInput = findViewById(R.id.etIdNumber);

        int id = Integer.parseInt(IDInput.getText().toString().trim());
        String password = passwordInput.getText().toString().trim();

        //for vulnerable version
        //Cursor c = MainActivity.userTable.loginVulnerable(id, password);

        InputValidator validator = new InputValidator();
        if(validator.isSafe(password) && validator.isValidLength(password, 10) && validator.isValidPassword(password)) {
            Cursor c = MainActivity.userTable.loginSecure(id, password);
            if (c.moveToFirst()) {

                int stuid = c.getInt(c.getColumnIndexOrThrow("STUid"));
                currentUser = MainActivity.users.get(stuid);

                loadUserData(currentUser.getSTUid());
                c.close();
                Intent i = new Intent(this, HomeActivity.class);

                if (currentUser.getUsername().equalsIgnoreCase("admin")) {
                    i.putExtra("admin", true);
                } else {
                    i.putExtra("admin", false);
                }

                startActivity(i);
                finish();
            }
            c.close();

        }
        else {
            passwordInput.setError("id or password is wrong");
            IDInput.setError("id or password is wrong");
        }
    }

    private void loadUserData(int stuid) {
        // load favorites
        favoriteList.clear();
        Cursor favCursor = favoritesTable.getFavoritesForUser(stuid);
        while (favCursor.moveToNext()) {
            int pid = favCursor.getInt(favCursor.getColumnIndexOrThrow("pid"));
            for (Place p : lookupByName.values()) {
                if (p.getPid() == pid) {
                    favoriteList.add(p);
                    break;
                }
            }
        }
        favCursor.close();

        // load reviews
        currentUser.getReviews().clear();
        Cursor revCursor = MainActivity.reviewTable.getByUser(stuid);
        while (revCursor.moveToNext()) {
            String revText = revCursor.getString(revCursor.getColumnIndexOrThrow("review"));
            int    stars   = revCursor.getInt(revCursor.getColumnIndexOrThrow("rating"));
            String date    = revCursor.getString(revCursor.getColumnIndexOrThrow("date"));
            int    pid     = revCursor.getInt(revCursor.getColumnIndexOrThrow("pid"));

            Place place = null;
            for (Place p : MainActivity.HomePageList) {
                if (p.getPid() == pid) {
                    place = p;
                    break;
                }
            }

            if (place != null) {
                new Reviews(revText, stars, currentUser, place);
            }
        }
        revCursor.close();
    }
    public void OpenRegister(View view) {
        // Clear errors before leaving the screen
        EditText passwordInput = findViewById(R.id.etPassword);
        EditText idInput = findViewById(R.id.etIdNumber);
        TextView tvError = findViewById(R.id.tvError);
        
        passwordInput.setError(null);
        idInput.setError(null);
        tvError.setVisibility(View.GONE);

        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
