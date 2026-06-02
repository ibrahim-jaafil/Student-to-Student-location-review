package com.example.dsa2_project;

import static com.example.dsa2_project.MainActivity.users;

import android.content.Intent;
import android.os.Bundle;
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
        users.put(123,new User("bob","bob","123",123));
        users.put(000,new User("admin","admin","000",000));

       // campusName= getIntent().getStringExtra("campus");
    }

    public void GetInfo(View view){
        Button btn = (Button) view;

        EditText nameInput = findViewById(R.id.etName);
        String name = nameInput.getText().toString().trim();

        EditText idInput = findViewById(R.id.etIdNumber);
        String id = idInput.getText().toString().trim();

        TextView tvError = findViewById(R.id.tvError);
        tvError.setVisibility(View.GONE);

        nameInput.setError(null);
        idInput.setError(null);

        if(name.isEmpty()){
            nameInput.setError("Please enter your name");
        }
        else if(id.isEmpty()){
            idInput.setError("Please enter your id number");
        }
        else{
            try {
                int idnb = Integer.parseInt(id);

                if (users.containsKey(idnb) && name.equalsIgnoreCase(users.get(idnb).getUsername())) {
                    MainActivity.currentUser = users.get(idnb);

                    if(users.get(idnb).getSTUid()==000 && users.get(idnb).getUsername().equals("admin")){
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
                    nameInput.setError("Username or id is wrong");
                    idInput.setError("Username or id is wrong");


                    tvError.setVisibility(View.VISIBLE);
                }
            }
            catch(NumberFormatException e){
                idInput.setError("Please enter a valid number", null);
            }
        }
    }

    public void OpenRegister(View view) {
        // Clear errors before leaving the screen
        EditText nameInput = findViewById(R.id.etName);
        EditText idInput = findViewById(R.id.etIdNumber);
        TextView tvError = findViewById(R.id.tvError);
        
        nameInput.setError(null);
        idInput.setError(null);
        tvError.setVisibility(View.GONE);

        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}