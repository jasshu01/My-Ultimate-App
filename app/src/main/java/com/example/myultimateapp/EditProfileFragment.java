package com.example.myultimateapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class EditProfileFragment extends Fragment {

    TextView editProfileInstructions;
    Spinner editProfileTitle;
    EditText editProfileFirstName, editProfileLastName, editProfileUsername, editProfilePassword, editProfileDOB, editProfileEmail, editProfilePhone, editProfileImage, editProfileAddress, editProfilePostal, editProfileSQ, editProfileSA;
    Button editProfileBtn;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Boolean validUsername = false;
    Boolean validPassword = false;
    Boolean validFirstName = false;
    Boolean validPhone = false;
    Boolean validEmail = false;
    Boolean validDOB = false;
    Boolean validSQ = false;
    Boolean validSA = false;

    dbHandler handler = new dbHandler(getContext(), "myApp", null, 1);


    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        editProfileInstructions = view.findViewById(R.id.instructions);
        editProfileTitle = view.findViewById(R.id.editProfileTitle);
        editProfileFirstName = view.findViewById(R.id.editProfileFirstName);
        editProfileLastName = view.findViewById(R.id.editProfileLastName);
        editProfileUsername = view.findViewById(R.id.editProfileUserName);
        editProfileDOB = view.findViewById(R.id.editProfileDOB);
        editProfileEmail = view.findViewById(R.id.editProfileEmail);
        editProfilePhone = view.findViewById(R.id.editProfilePhone);
        editProfileImage = view.findViewById(R.id.editProfileImage);
        editProfileAddress = view.findViewById(R.id.editProfileAddress);
        editProfilePostal = view.findViewById(R.id.editProfilePostal);
        editProfileSQ = view.findViewById(R.id.editProfileSQ);
        editProfileSA = view.findViewById(R.id.editProfileSA);
        editProfileBtn = view.findViewById(R.id.editProfileButton);


        List<String> titles = new ArrayList<String>();
        titles.add("Mr.");
        titles.add("Mrs.");
        titles.add("Miss");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, titles);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editProfileTitle.setAdapter(dataAdapter);


        editProfileFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String s = charSequence.toString();
                editProfileInstructions.setText("");
                if (s.length() < 2) {
                    editProfileInstructions.setText("Enter a valid Name ");
                    validFirstName = false;
                } else {
                    editProfileInstructions.setText("");
                    validFirstName = true;
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editProfileLastName.addTextChangedListener(new TextWatcher() {
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
        editProfileUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String s = charSequence.toString();
                editProfileInstructions.setText("");

                Boolean unique = true;

                if (s.length() < 5) {
                    editProfileInstructions.setText("Username should have atleast 5 characters");
                    validUsername = false;
                } else {
                    editProfileInstructions.setText("");
                    for (int c = 0; c < s.length(); c++) {
                        if (!dbHandler.validUserNameCharacters.contains("" + charSequence.charAt(c))) {
                            editProfileInstructions.setText("Username should be alphanumeric Only");
                            validUsername = false;
                            return;
                        }

                    }


                    UserDetails user = handler.fetchUserUsingUserName(s);
                    if (user != null) {
                        editProfileInstructions.setText("This Username already exists");
                        validUsername = false;
                        return;
                    }


                    editProfileInstructions.setText("");
                    validUsername = true;
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editProfilePassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String password = charSequence.toString();
                editProfileInstructions.setText("");
                boolean numericPresent = false;
                boolean smallPresent = false;
                boolean capitalPresent = false;
                boolean sufficientLength = false;
                boolean specialCharacters = false;
                editProfileInstructions.setText("");

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
                    editProfileInstructions.setText("Password should have atleast one Special Chatacter @,#,$");
                }
                if (sufficientLength == false) {

                    editProfileInstructions.setText("Password should have atleast 8 characters");
                }
                if (numericPresent == false) {
                    editProfileInstructions.setText("Password should have atleast one digit");
                }

                if (smallPresent == false) {
                    editProfileInstructions.setText("Password should have atleast one small alphabet");
                }


                if (capitalPresent == false) {
                    editProfileInstructions.setText("Password should have atleast one capital alphabet");
                }

                if (specialCharacters && sufficientLength && numericPresent && smallPresent && capitalPresent) {
                    editProfileInstructions.setText("");
                    validPassword = true;
                } else {
                    validPassword = false;
                }

            }


            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editProfileDOB.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String s = charSequence.toString();
                editProfileInstructions.setText("");
                String[] str = s.split("-");

                if (str.length != 3 || (str[0].length() != 4 || str[1].length() != 2 || str[2].length() != 2)) {
                    editProfileInstructions.setText("Enter DOB in format (YYYY-MM-DD)");
                    validDOB = false;
                } else {
                    editProfileInstructions.setText("");
                    validDOB = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editProfileEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String email = charSequence.toString();
                editProfileInstructions.setText("");
                if (email.matches(emailPattern) && email.length() > 0) {
                    editProfileInstructions.setText("");
                    validEmail = true;
                } else {
                    editProfileInstructions.setText("Enter a valid Email");
                    validEmail = false;
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editProfilePhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String s = charSequence.toString();
                editProfileInstructions.setText("");
                if (s.length() != 10) {
                    editProfileInstructions.setText("Phone number should contain only 10 characters");
                    validPhone = false;
                } else {
                    editProfileInstructions.setText("");
                    validPhone = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editProfileSQ.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String s = charSequence.toString();
                editProfileInstructions.setText("");

                if (s.length() < 20) {
                    editProfileInstructions.setText("Security Question should contain atleast 20 characters");
                    validSQ = false;
                } else {
                    editProfileInstructions.setText("");
                    validSQ = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editProfileSA.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String s = charSequence.toString();
                editProfileInstructions.setText("");
                if (s.length() < 5) {
                    editProfileInstructions.setText("Security answer should contain atleast 5 characters");
                    validSA = false;
                } else {
                    editProfileInstructions.setText("");
                    validSA = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    if (!validUsername) {
                        editProfileInstructions.setText("Enter Valid UserName");
                        return;
                    }
                    if (!validDOB) {
                        editProfileInstructions.setText("Enter Valid DOB");
                        return;
                    }
                    if (!validEmail) {
                        editProfileInstructions.setText("Enter Valid Email");
                        return;
                    }
                    if (!validPassword) {
                        editProfileInstructions.setText("Enter Valid Password");
                        return;
                    }
                    if (!validFirstName) {
                        editProfileInstructions.setText("Enter Valid firstName");
                        return;
                    }
                    if (!validPhone) {
                        editProfileInstructions.setText("Enter Valid Phone");
                        return;
                    }
                    if (!validSQ) {
                        editProfileInstructions.setText("Enter Valid Security Question");
                        return;
                    }
                    if (!validSA) {
                        editProfileInstructions.setText("Enter Valid Security Answer");
                        return;
                    }
                }
                if (validUsername && validPassword && validFirstName && validPhone && validEmail && validDOB && validSQ && validSA) {


//                    Log.d("selectedTitle", "onClick: i am here");
//                    Log.d("selectedTitle", "onClick: "+editProfileTitle.getSelectedItem());

                    String gender = "Female";
                    if (editProfileTitle.getSelectedItem().equals("Mr."))
                        gender = "Male";

                    UserDetails user = new UserDetails(editProfileTitle.getSelectedItem().toString(), editProfileFirstName.getText().toString(), editProfileLastName.getText().toString(), editProfileUsername.getText().toString(), editProfilePassword.getText().toString(), editProfileDOB.getText().toString(), gender, editProfileEmail.getText().toString(), editProfilePhone.getText().toString(), editProfileImage.getText().toString(), editProfileAddress.getText().toString(), editProfilePostal.getText().toString(), editProfileSQ.getText().toString(), editProfileSA.getText().toString());

                    if (handler.addUser(user, getContext())) {
                        Toast.makeText(getContext(), "user Added", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getContext(), LoginPage.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getContext(), "Some Error Occured!", Toast.LENGTH_SHORT).show();
                    }


                    return;
                }
            }
        });
        return view;


    }


}
