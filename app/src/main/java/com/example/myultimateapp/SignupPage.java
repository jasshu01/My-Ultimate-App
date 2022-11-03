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
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


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
                if (s.length() < 2)
                    signupInstructions.setText("Enter a valid Name ");
                else
                    signupInstructions.setText("");
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
                signupInstructions.setText("");
                if (s.length() < 5) {
                    signupInstructions.setText("Username should have atleast 5 characters");
                } else {
                    signupInstructions.setText("");
                    for (int c = 0; c < s.length(); c++) {
                        if (!dbHandler.validUserNameCharacters.contains("" + charSequence.charAt(c))) {
                            signupInstructions.setText("Username should be alphanumeric Only");
                            return;
                        }

                    }
                    signupInstructions.setText("");
                }

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
                String password = charSequence.toString();

                boolean numericPresent = false;
                boolean smallPresent = false;
                boolean capitalPresent = false;
                boolean sufficientLength = false;
                boolean specialCharacters = false;
                signupInstructions.setText("");

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
                    signupInstructions.setText("Password should have atleast one Special Chatacter @,#,$");
                }
                if (sufficientLength == false) {

                    signupInstructions.setText("Password should have atleast 8 characters");
                }
                if (numericPresent == false) {
                    signupInstructions.setText("Password should have atleast one digit");
                }

                if (smallPresent == false) {
                    signupInstructions.setText("Password should have atleast one small alphabet");
                }


                if (capitalPresent == false) {
                    signupInstructions.setText("Password should have atleast one capital alphabet");
                }

                if (specialCharacters && sufficientLength && numericPresent && smallPresent && capitalPresent) {
                    signupInstructions.setText("");
                }

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
                String[] str = s.split("-");

                if(str.length!=3 || (str[0].length()!=4 || str[1].length()!=2 || str[2].length()!=2))
                {
                    signupInstructions.setText("Enter DOB in format (YYYY-MM-DD)");
                }
                else{
                    signupInstructions.setText("");
                }
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
                String email = charSequence.toString();
                if (email.matches(emailPattern) && email.length() > 0)
                {
                    signupInstructions.setText("");
                }
                else
                {
                    signupInstructions.setText("Enter a valid Email");
                }

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
                if(s.length()!=10)
                {
                    signupInstructions.setText("Phone number should contain only 10 characters");
                }
                else {
                    signupInstructions.setText("");
                }
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

                if(s.length()<20)
                {
                    signupInstructions.setText("Security Question should contain atleast 20 characters");
                }
                else {
                    signupInstructions.setText("");
                }
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
                if(s.length()<5)
                {
                    signupInstructions.setText("Security answer should contain atleast 5 characters");
                }
                else {
                    signupInstructions.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });






    }
}