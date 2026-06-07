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


    //without database
   /* public void GetInfo(View view){
        Button btn = (Button) view;

        EditText passwordInput = findViewById(R.id.etPassword);
        String password = passwordInput.getText().toString().trim();

        EditText idInput = findViewById(R.id.etIdNumber);
        String id = idInput.getText().toString().trim();

        TextView tvError = findViewById(R.id.tvError);
        tvError.setVisibility(View.GONE);

       passwordInput.setError(null);
        idInput.setError(null);

        if(password.isEmpty()){
            passwordInput.setError("Please enter your password");
        }
        else if(id.isEmpty()){
            idInput.setError("Please enter your id number");
        }
        else{
            try {
                int idnb = Integer.parseInt(id);

                if (users.containsKey(idnb) && password.equalsIgnoreCase(users.get(idnb).getPassword())) {
                    MainActivity.currentUser = users.get(idnb);

                    favoriteList.clear();
                    Cursor favCursor = favoritesTable.getFavoritesForUser(idnb);
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

                    // Load user's reviews from DB
                    MainActivity.currentUser.getReviews().clear();
                    Cursor revCursor = MainActivity.reviewTable.getByUser(idnb);
                    while (revCursor.moveToNext()) {
                        String revText = revCursor.getString(revCursor.getColumnIndexOrThrow("review"));
                        int stars = revCursor.getInt(revCursor.getColumnIndexOrThrow("rating"));
                        String date = revCursor.getString(revCursor.getColumnIndexOrThrow("date"));
                        int pid = revCursor.getInt(revCursor.getColumnIndexOrThrow("pid"));

                        // Find the place object. Check global lookup first.
                        Place place = null;
                        for (Place p : MainActivity.HomePageList) {
                            if (p.getPid() == pid) {
                                place = p;
                                break;
                            }
                        }
                        
                        // If not in current campus HomePageList, we might need a way to get it from DB or global cache
                        // For now, let's assume it's in the current list or create a dummy/lookup if needed.
                        if (place != null) {
                            com.example.dsa2_project.Location.Reviews r = new com.example.dsa2_project.Location.Reviews(revText, stars, MainActivity.currentUser, place);
                            // The constructor of Reviews automatically adds itself to user.getReviews()
                        }
                    }
                    revCursor.close();

                    if(MainActivity.currentUser.getSTUid()==000 && MainActivity.currentUser.getPassword().equals("000")){
                        Intent i = new Intent(this, HomeActivity.class);
                        i.putExtra("admin",true);
                        //i.putExtra("campus",campusName);
                        startActivity(i);
                        finish(); // Finish LoginActivity so user can't "go back" to a login screen with errors
                    }
                    else {
                        Intent i = new Intent(this, HomeActivity.class);
                        i.putExtra("admin",false);
                      //  i.putExtra("campus",campusName);

                        startActivity(i);
                        finish();// Finish LoginActivity so user can't "go back" to a login screen with errors
                    }
                } else {
                    passwordInput.setError("Username or id is wrong");
                    idInput.setError("Username or id is wrong");
                    tvError.setVisibility(View.VISIBLE);
                }
            }
            catch(NumberFormatException e){
                idInput.setError("Please enter a valid number");
            }
        }
    }*/

    // VULNERABLE button — for demo only
    public void loginVulnerable(View view) {
        EditText passwordInput = findViewById(R.id.etPassword);
        EditText IDInput = findViewById(R.id.etIdNumber);

        String username = IDInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        Cursor c = MainActivity.userTable.loginVulnerable(username, password);
        if (c.moveToFirst()) {

            int stuid = c.getInt(c.getColumnIndexOrThrow("STUid"));
            Log.e("DEBUG", "stuid from cursor: " + stuid);
            currentUser = MainActivity.users.get(stuid);
            Log.e("DEBUG", "currentUser: " + (MainActivity.currentUser == null ? "NULL" : MainActivity.currentUser.getUsername()));

            loadUserData(currentUser.getSTUid());
            c.close();
            Intent i = new Intent(this, HomeActivity.class);

            if(currentUser.getUsername().equalsIgnoreCase("admin")) {
                i.putExtra("admin", true);
            }
                else{
                i.putExtra("admin",false);
                }

            startActivity(i);
            finish();
        } else {
            c.close();
            passwordInput.setError("Username or id is wrong");
            IDInput.setError("Username or id is wrong");
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
