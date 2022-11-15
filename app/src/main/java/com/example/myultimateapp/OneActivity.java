package com.example.myultimateapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OneActivity extends AppCompatActivity {

    TextView textViewInOneActivity;
    Button goToActivityTwo;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);

        textViewInOneActivity = findViewById(R.id.textViewInOneActivity);
        goToActivityTwo = findViewById(R.id.goToActivityTwo);

        goToActivityTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OneActivity.this, TwoActivity.class);
                someActivityResultLauncher.launch(intent);
            }
        });

    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        try{
                            Log.d("dataFromTwo", data.getStringExtra("stringFromActivityTwo"));
                            textViewInOneActivity.setText(data.getStringExtra("stringFromActivityTwo"));
                        }catch (Exception e)
                        {
                            Log.d("dataFromTwo",e.toString());
                        }

                    }
                }
            });

}