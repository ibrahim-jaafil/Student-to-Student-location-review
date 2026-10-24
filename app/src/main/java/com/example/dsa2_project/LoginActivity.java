package com.example.dsa2_project;

import static com.example.dsa2_project.MainActivity.currentUser;
import static com.example.dsa2_project.MainActivity.favoriteList;
import static com.example.dsa2_project.MainActivity.favoritesTable;
import static com.example.dsa2_project.MainActivity.lookupByName;


import android.database.Cursor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dsa2_project.API.Auth;
import com.example.dsa2_project.API.loginApi.LoginRequest;
import com.example.dsa2_project.API.loginApi.LoginResponse;
import com.example.dsa2_project.API.Retrofitinstance;
import com.example.dsa2_project.Location.Place;
import com.example.dsa2_project.Location.Reviews;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
    //without backend

  /*   public void login(View view) {
        EditText passwordInput = findViewById(R.id.etPassword);
        EditText IDInput = findViewById(R.id.etIdNumber);

        int id = Integer.parseInt(IDInput.getText().toString().trim());
        String password = passwordInput.getText().toString().trim();
        //for vulnerable version
        //Cursor c = MainActivity.userTable.loginVulnerable(id, password);
        //without the input validator for the vulnerable version
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
    }*/


        public void login(View view){

             EditText passwordInput = findViewById(R.id.etPassword);
             EditText IDInput = findViewById(R.id.etIdNumber);

              int id = Integer.parseInt(IDInput.getText().toString().trim());
              String password = passwordInput.getText().toString().trim();

              Auth api = Retrofitinstance.getservice();
            Log.d("API_TEST","now");
              api.login(new LoginRequest(id , password)).enqueue(new Callback<LoginResponse>(){

                 @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful() && response.body()!=null){
                    String message = response.body().getMessage();
                    int STUid = response.body().getId();
                    Log.d("API", "code=" + response.code());
                    //currentUser = MainActivity.users.get(STUid); have to fix the flow but for now just want to see the requestna nd respons working

                    // Intent i = new Intent(this, HomeActivity.class);
                    // startActivity(i);
                    // finish();
                }
                else{
                    Log.d("didnt work", "code=" + response.code());
                    passwordInput.setError("id or password is wrong");
                    IDInput.setError("id or password is wrong");
                }
            
            
            }

                  @Override
                  public void onFailure(Call<LoginResponse> call, Throwable t) {
                      Log.e("API_TEST", "request failed: " + t.getMessage(), t);
                  }

              });



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
