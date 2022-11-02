package com.example.myultimateapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity {

    EditText userName, password;
    TextView passwordInstructions, userNameInstructions, signupOption, ForgotPassword;
    Button loginButton;
    Boolean ValidPassword = false;
    Boolean ValidUserName = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        userName = findViewById(R.id.login_userName);
        password = findViewById(R.id.login_password);
        passwordInstructions = findViewById(R.id.passwordInstructions);
        userNameInstructions = findViewById(R.id.userNameInstructions);
        signupOption = findViewById(R.id.signupOption);
        ForgotPassword = findViewById(R.id.forgotPassword);
        loginButton = findViewById(R.id.loginButton);


        SharedPreferences sp = getSharedPreferences("Current User", MODE_PRIVATE);

        String CheckingIfAlreadyLoggedIn=sp.getString("LoggedInUser","No Logged In User Exists");
        if(!CheckingIfAlreadyLoggedIn.equals("No Logged In User Exists"))
        {
            Intent intent = new Intent(LoginPage.this, HomePage.class);
            startActivity(intent);
        }

        userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                String username = s.toString();
                if (username.length() >= 5) {
                    userNameInstructions.setText("");
                    ValidUserName = true;


                } else {
                    userNameInstructions.setTextColor(Color.argb(255, 255, 0, 0));

                    ValidUserName = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                boolean numericPresent = false;
                ValidPassword = false;
                passwordInstructions.setTextColor(Color.argb(255, 255, 0, 0));
                String password = s.toString();
                if (password.length() == 0) {
                    passwordInstructions.setText("");
                } else {
                    if (password.length() < 8)
                        passwordInstructions.setText("Password Length Should be minimum 8 Characters");
                    else if (password.contains("@") || password.contains("#") || password.contains("$")) {


                        for (int k = 0; k <= 9; k++) {
                            if (password.contains(String.valueOf(k)))
                                numericPresent = true;

                        }

                        if (numericPresent == false) {
                            passwordInstructions.setText("Add atleast one digit");
                        } else {
                            passwordInstructions.setText("");
                        }

                    } else {
                        passwordInstructions.setText("Add atleast one Special Chatacter @,#,$");
                    }

                }

                if (numericPresent && password.length() >= 8 && (password.contains("@") || password.contains("#") || password.contains("$"))) {
                    passwordInstructions.setText("");
                    passwordInstructions.setTextColor(Color.argb(255, 0, 255, 0));
                    ValidPassword = true;
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });




        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!ValidPassword)
                {
                    Toast.makeText(LoginPage.this, "Password is not Valid", Toast.LENGTH_SHORT).show();
                }
                if(!ValidUserName)
                {
                    Toast.makeText(LoginPage.this, "UserName is not Valid", Toast.LENGTH_SHORT).show();
                }


                String final_username, final_password;
                final_username = String.valueOf(userName.getText());

                final_password = String.valueOf(password.getText());




                SharedPreferences sp = getSharedPreferences("Current User", MODE_PRIVATE);
                SharedPreferences.Editor ed = sp.edit();
                ed.putString("LoggedInUser", final_username);
                ed.apply();


                Intent intent = new Intent(LoginPage.this, HomePage.class);
                startActivity(intent);


            }
        });

    }
}