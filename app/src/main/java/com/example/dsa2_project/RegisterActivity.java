package com.example.dsa2_project;

import static com.example.dsa2_project.MainActivity.users;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dsa2_project.API.Auth;
import com.example.dsa2_project.API.Retrofitinstance;
import com.example.dsa2_project.API.registerApi.registerRequest;
import com.example.dsa2_project.API.registerApi.registerResponse;
import com.example.dsa2_project.Home.HomeActivity;
import com.example.dsa2_project.login_sys.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText etEmail, etPassword, etName, etSTUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etName = findViewById(R.id.etName);
        etSTUid = findViewById(R.id.etSTUid);
    }

    public void RegisterUser(View view) {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String stuId = etSTUid.getText().toString().trim();


        if (email.isEmpty() || password.isEmpty() || name.isEmpty() || stuId.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }


try {
    int stuIdnb= Integer.parseInt(stuId);

        // Logic for creating account would go here (e.g., adding to your User HashMap)

            Auth api = Retrofitinstance.getservice();
        api.register(new registerRequest(stuIdnb,name,email,password)).enqueue(new Callback<registerResponse>(){
            @Override
            public void onResponse(Call < registerResponse > call, Response< registerResponse > response){
                if(response.isSuccessful() && response.body()!=null){
                    String message = response.body().getMessage();
                    int STUid = response.body().getId();
                    Log.d("code="," "+response.code());


                    users.put(stuIdnb,new User(name,email,password,stuIdnb));

                    MainActivity.userTable.insert(name, email, password, stuIdnb);
                    MainActivity.currentUser= users.get(stuIdnb);
                    //Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_SHORT).show();

                    //Intent intent = new Intent(this, HomeActivity.class);
                    //startActivity(intent);
                   // finish();
                }
                else{
                    Log.d("didnt work", "code=" + response.code());
                }



    }

    @Override
    public void onFailure(Call<registerResponse> call, Throwable t) {
        Toast.makeText(RegisterActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
    }
    });





    }
catch (NumberFormatException e){
    etSTUid.setError("Please enter a valid number");
}
    }

    public void BackToLogin(View view) {
        finish();
    }
}