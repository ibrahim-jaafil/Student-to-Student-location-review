package com.example.dsa2_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

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
    }

    public void GetInfo(View view){
        Button btn = (Button) view;

        EditText nameInput = findViewById(R.id.etName);
        String name = nameInput.getText().toString();

        EditText idInput = findViewById(R.id.etIdNumber);
        String id = idInput.getText().toString();

        if(name.isEmpty()){
            nameInput.setError("Please enter your name");
        }
        else if(id.isEmpty()){
            idInput.setError("Please enter your id number");
        }
        else{
            Intent i = new Intent(this, HomeActivity.class);
            startActivity(i);

        }
    }
}