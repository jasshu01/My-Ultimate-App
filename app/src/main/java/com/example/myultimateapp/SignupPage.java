package com.example.myultimateapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SignupPage extends AppCompatActivity {

    TextView signupInstructions;
    Spinner signupTitle;
    EditText signupFirstName, signupLastName, signupUsername, signupPassword, signupDOB, signupEmail, signupPhone, signupImage, signupAddress, signupPostal, signupSQ, signupSA;
    Button signupBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);

        signupInstructions = findViewById(R.id.singupInstuctions);
        signupTitle = findViewById(R.id.signupTitle);
        signupFirstName = findViewById(R.id.signupFirstName);
        signupLastName = findViewById(R.id.signupLastName);
        signupUsername = findViewById(R.id.signupUserName);
        signupPassword = findViewById(R.id.signupPassword);
        signupDOB = findViewById(R.id.signupDOB);
        signupEmail = findViewById(R.id.signupEmail);
        signupPhone = findViewById(R.id.signupPhone);
        signupImage = findViewById(R.id.signupImage);
        signupAddress = findViewById(R.id.signupAddress);
        signupPostal = findViewById(R.id.signupPostal);
        signupSQ = findViewById(R.id.signupSQ);
        signupSA = findViewById(R.id.signupSA);
        signupBtn = findViewById(R.id.signupButton);


        List<String> titles = new ArrayList<String>();
        titles.add("Mr.");
        titles.add("Mrs.");
        titles.add("Miss");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, titles);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        signupTitle.setAdapter(dataAdapter);


        signupFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String s = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        signupLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String s = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        signupUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String s = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        signupPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String s = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        signupDOB.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String s = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        signupEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String s = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        signupPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String s = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        signupSQ.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String s = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        signupSA.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String s = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });




    }
}