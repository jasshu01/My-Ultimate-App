package com.example.myultimateapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText p1, p2;
    TextView passwordInstructions;
    Button cancel, confirm;
    Boolean validPassword = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        p1 = findViewById(R.id.ChangePasswordActivity_Password1);
        p2 = findViewById(R.id.ChangePasswordActivity_Password2);
        passwordInstructions = findViewById(R.id.ChangePasswordActivity_passwordInstructions);
        cancel = findViewById(R.id.ChangePasswordActivity_cancel);
        confirm = findViewById(R.id.ChangePasswordActivity_ChangePassword);


        String username = getIntent().getStringExtra("username");
        dbHandler handler = new dbHandler(this, "myApp", null, 1);
        UserDetails user = handler.fetchUserUsingUserName(username);


        p1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String password = charSequence.toString();
                String password2 = String.valueOf(p2.getText());
                validatePassword(password, password2);

            }


            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        p2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String password = charSequence.toString();
                String password2 = String.valueOf(p1.getText());
                validatePassword(password, password2);

            }


            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validPassword == true) {
                    user.setPassword(String.valueOf(p1.getText()));
                    if (handler.updateUser(user, ChangePasswordActivity.this)) {
                            Intent intent=new Intent(ChangePasswordActivity.this,LoginPage.class);
                            startActivity(intent);
                    }
                    else{
                        Toast.makeText(ChangePasswordActivity.this, "Some error occured", Toast.LENGTH_SHORT).show();

                    }

                } else {

                    Toast.makeText(ChangePasswordActivity.this, "Enter a Valid Password", Toast.LENGTH_SHORT).show();


                }
            }
        });

    }

    public void validatePassword(String password, String password2) {
        passwordInstructions.setText("");
        boolean numericPresent = false;
        boolean smallPresent = false;
        boolean capitalPresent = false;
        boolean sufficientLength = false;
        boolean specialCharacters = false;


        if (password2.length() == 0) {
            if (password.length() < 8) {
                sufficientLength = false;

            } else {
                sufficientLength = true;
            }


            if (password.contains("@") || password.contains("#") || password.contains("$")) {

                specialCharacters = true;
            } else {
                specialCharacters = false;
            }

            for (int k = 0; k <= 9; k++) {
                if (password.contains(String.valueOf(k))) {
                    numericPresent = true;
                    break;
                } else {
                    numericPresent = false;
                }
            }


            for (char ch = 'a'; ch <= 'z'; ch++) {
                if (password.contains("" + ch)) {
                    smallPresent = true;
                    break;
                } else {
                    smallPresent = false;
                }
            }

            for (char ch = 'A'; ch <= 'Z'; ch++) {
                if (password.contains("" + ch)) {
                    capitalPresent = true;
                    break;
                } else {
                    capitalPresent = false;
                }
            }

            if (specialCharacters == false) {
                passwordInstructions.setText("Password should have atleast one Special Chatacter @,#,$");
            }
            if (sufficientLength == false) {

                passwordInstructions.setText("Password should have atleast 8 characters");
            }
            if (numericPresent == false) {
                passwordInstructions.setText("Password should have atleast one digit");
            }

            if (smallPresent == false) {
                passwordInstructions.setText("Password should have atleast one small alphabet");
            }


            if (capitalPresent == false) {
                passwordInstructions.setText("Password should have atleast one capital alphabet");
            }

            if (specialCharacters && sufficientLength && numericPresent && smallPresent && capitalPresent) {
                passwordInstructions.setText("");
                validPassword = true;
            } else {
                validPassword = false;
            }

        } else {
            if (password.equals(password2)) {
                passwordInstructions.setText("");
                validPassword = true;
            } else {
                passwordInstructions.setText("Passwords doesnt match");
                validPassword = false;
            }

        }


    }
}