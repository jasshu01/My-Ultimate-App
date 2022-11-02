package com.example.myultimateapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class HomePage extends AppCompatActivity {
    TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        username=findViewById(R.id.userName);
        SharedPreferences sp = getSharedPreferences("Current User", MODE_PRIVATE);

        username.setText(sp.getString("LoggedInUser","No Logged In User Exists"));
    }
}