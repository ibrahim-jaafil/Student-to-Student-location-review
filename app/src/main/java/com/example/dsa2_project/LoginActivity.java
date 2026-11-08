package com.example.dsa2_project;

import static com.example.dsa2_project.MainActivity.currentUser;
import static com.example.dsa2_project.MainActivity.favoriteList;
import static com.example.dsa2_project.MainActivity.lookupByName;
import static com.example.dsa2_project.MainActivity.placelistbypid;


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
import com.example.dsa2_project.API.load.loadFavsRequest;
import com.example.dsa2_project.API.load.loadFavsResponse;
import com.example.dsa2_project.API.loginApi.LoginRequest;
import com.example.dsa2_project.API.loginApi.LoginResponse;
import com.example.dsa2_project.API.Retrofitinstance;
import com.example.dsa2_project.Home.HomeActivity;
import com.example.dsa2_project.Location.Place;
import com.example.dsa2_project.Location.Reviews;
import com.example.dsa2_project.login_sys.User;

import java.util.List;

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
                    LoginResponse r = response.body();
                    currentUser = new User(r.getUsername(),r.getEmail(),r.getSTUid());


                    Log.d("API", "code=" + response.code());
                    //currentUser = MainActivity.users.get(STUid); have to fix the flow but for now just want to see the requestna nd respons working
                     loadFavorites();
                     Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                     startActivity(i);
                     finish();
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

        public void loadFavorites() {
           Auth api = Retrofitinstance.getservice();
            api.loadfavs(new loadFavsRequest(currentUser.getSTUid())).enqueue(new Callback<List<loadFavsResponse>>() {
                @Override
                public void onResponse(Call<List<loadFavsResponse>> call, Response<List<loadFavsResponse>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<loadFavsResponse> favs = response.body();

                        for (loadFavsResponse r : favs) {
                            Log.d(" loaded favs", "code=" + ""+placelistbypid.get(r.getPid()));
                            placelistbypid.get(r.getPid()).setFavorite(true);
                        }


                    }
                }

                @Override
                public void onFailure(Call<List<loadFavsResponse>> call, Throwable t) {
                    Log.d("didnt load favs", "code=" + t.getMessage());
                }

            });
        }
        //have to do this also
/*    private void loadUserData(int stuid) {
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


    public void OpenRegister(View view) {
        // Clear errors before leaving the screen
        EditText passwordInput = findViewById(R.id.etPassword);
        EditText idInput = findViewById(R.id.etIdNumber);
        TextView tvError = findViewById(R.id.tvError);
        
        passwordInput.setError(null);
        idInput.setError(null);
        tvError.setVisibility(View.GONE);

        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);*/
    
}
