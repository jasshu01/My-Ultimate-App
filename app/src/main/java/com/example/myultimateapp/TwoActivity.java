package com.example.myultimateapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;

public class TwoActivity extends AppCompatActivity {
    EditText editTextActivityTwo;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        editTextActivityTwo = findViewById(R.id.editTextActivityTwo);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();

        Log.d("dataFromTwo", String.valueOf(editTextActivityTwo.getText()));
        intent.putExtra("stringFromActivityTwo", String.valueOf(editTextActivityTwo.getText()));
        setResult(RESULT_OK, intent);
        finish();


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

}
