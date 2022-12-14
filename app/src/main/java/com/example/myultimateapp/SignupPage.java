package com.example.myultimateapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SignupPage extends AppCompatActivity {

    TextView signupInstructions, signupDOB;
    Spinner signupTitle;
    EditText signupFirstName, signupLastName, signupUsername, signupPassword, signupEmail, signupPhone, signupAddress, signupPostal, signupSQ, signupSA;
    Button signupBtn;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Boolean validUsername = false;
    Boolean validPassword = false;
    Boolean validFirstName = false;
    Boolean validPhone = false;
    Boolean validEmail = false;
    Boolean validSQ = false;
    Boolean validSA = false;

    ImageView signupDisplayPic;
    FloatingActionButton signupEditPicBtn;
    Bitmap mySignupimage = null;
    ActivityResultLauncher<Intent> activityResultLauncher_capture, activityResultLauncher_choose;


    dbHandler handler = new dbHandler(SignupPage.this, "myApp", null, 1);

    @SuppressLint("MissingInflatedId")
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

        signupAddress = findViewById(R.id.signupAddress);
        signupPostal = findViewById(R.id.signupPostal);
        signupSQ = findViewById(R.id.signupSQ);
        signupSA = findViewById(R.id.signupSA);
        signupBtn = findViewById(R.id.signupButton);

        signupDisplayPic = findViewById(R.id.signupDisplayPic);
        signupEditPicBtn = findViewById(R.id.signupEditPicButton);

        signupDisplayPic.setImageResource(R.drawable.person);

        signupEditPicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getContext(), "Editing Image", Toast.LENGTH_SHORT).show();
                createOptionDialogBox();


            }
        });

        activityResultLauncher_choose = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result ->
        {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();

                if (data != null && data.getData() != null) {
                    Uri selectedImageUri = data.getData();
                    Bitmap selectedImageBitmap;
                    try {
                        selectedImageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri)
                        ;
                        mySignupimage = selectedImageBitmap;
                        signupDisplayPic.setImageBitmap(selectedImageBitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        activityResultLauncher_capture = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {

            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Bundle bundle = result.getData().getExtras();
                    Bitmap photo = (Bitmap) bundle.get("data");

                    mySignupimage = photo;
                    signupDisplayPic.setImageBitmap(photo);
                } else {
                    Log.d("capturingImage", result.toString());
                    Log.d("capturingImage", String.valueOf(result.getResultCode()));
                    Toast.makeText(SignupPage.this, "Some Error occured", Toast.LENGTH_SHORT).show();
                }

            }
        });


        List<String> titles = new ArrayList<String>();
        titles.add("Mr.");
        titles.add("Mrs.");
        titles.add("Miss");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, titles);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        signupTitle.setAdapter(dataAdapter);


        signupDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signupDOB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Calendar newCalendar = Calendar.getInstance();
                        final DatePickerDialog StartTime = new DatePickerDialog(SignupPage.this, new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                Calendar newDate = Calendar.getInstance();

                                newDate.set(year, month, day);
                                Log.d("newdata", year + "," + (month + 1) + "," + day);
                                signupDOB.setText(year + "-" + (month + 1) + "-" + day);

                            }

                        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                        StartTime.getDatePicker().setMaxDate(new Date().getTime());
                        StartTime.show();
                    }
                });
            }
        });

        signupFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String s = charSequence.toString();
                signupInstructions.setText("");
                if (s.length() < 2) {
                    signupInstructions.setText("Enter a valid Name ");
                    validFirstName = false;
                } else {
                    signupInstructions.setText("");
                    validFirstName = true;
                }

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

                Boolean unique = true;

                if (s.length() < 5) {
                    signupInstructions.setText("Username should have atleast 5 characters");
                    validUsername = false;
                } else {
                    signupInstructions.setText("");
                    for (int c = 0; c < s.length(); c++) {
                        if (!dbHandler.validUserNameCharacters.contains("" + charSequence.charAt(c))) {
                            signupInstructions.setText("Username should be alphanumeric Only");
                            validUsername = false;
                            return;
                        }

                    }


                    UserDetails user = handler.fetchUserUsingUserName(s);
                    if (user != null) {
                        signupInstructions.setText("This Username already exists");
                        validUsername = false;
                        return;
                    }


                    signupInstructions.setText("");
                    validUsername = true;
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
                signupInstructions.setText("");
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
                    validPassword = true;
                } else {
                    validPassword = false;
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
                signupInstructions.setText("");

                if (handler.fetchUsersUsingEmail(email) != null) {
                    signupInstructions.setText("An account already exists with this email account");
                    validEmail = false;
                    return;
                }

                if (email.matches(emailPattern) && email.length() > 0) {
                    signupInstructions.setText("");
                    validEmail = true;
                } else {
                    signupInstructions.setText("Enter a valid Email");
                    validEmail = false;
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
                signupInstructions.setText("");

                if (handler.fetchUserUsingPhoneNumber(s) != null) {
                    signupInstructions.setText("An account already exists with this phone number");
                    validPhone = false;
                    return;
                }

                if (s.length() != 10) {
                    signupInstructions.setText("Phone number should contain only 10 characters");
                    validPhone = false;
                } else {
                    signupInstructions.setText("");
                    validPhone = true;
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
                signupInstructions.setText("");

                if (s.length() < 20) {
                    signupInstructions.setText("Security Question should contain atleast 20 characters");
                    validSQ = false;
                } else {
                    signupInstructions.setText("");
                    validSQ = true;
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
                signupInstructions.setText("");
                if (s.length() < 5) {
                    signupInstructions.setText("Security answer should contain atleast 5 characters");
                    validSA = false;
                } else {
                    signupInstructions.setText("");
                    validSA = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    if (!validUsername) {
                        signupInstructions.setText("Enter Valid UserName");
                        return;
                    }

                    if (!validEmail) {
                        signupInstructions.setText("Enter Valid Email");
                        return;
                    }
                    if (!validPassword) {
                        signupInstructions.setText("Enter Valid Password");
                        return;
                    }
                    if (!validFirstName) {
                        signupInstructions.setText("Enter Valid firstName");
                        return;
                    }
                    if (!validPhone) {
                        signupInstructions.setText("Enter Valid Phone");
                        return;
                    }
                    if (!validSQ) {
                        signupInstructions.setText("Enter Valid Security Question");
                        return;
                    }
                    if (!validSA) {
                        signupInstructions.setText("Enter Valid Security Answer");
                        return;
                    }
                }
                if (validUsername && validPassword && validFirstName && validPhone && validEmail && validSQ && validSA) {


                    String myImageString = handler.BitMapToString(mySignupimage);

                    String gender = "Female";
                    if (signupTitle.getSelectedItem().equals("Mr."))
                        gender = "Male";

                    UserDetails user = new UserDetails(signupTitle.getSelectedItem().toString(), signupFirstName.getText().toString(), signupLastName.getText().toString(), signupUsername.getText().toString(), signupPassword.getText().toString(), signupDOB.getText().toString(), gender, signupEmail.getText().toString(), signupPhone.getText().toString(), myImageString, signupAddress.getText().toString(), signupPostal.getText().toString(), signupSQ.getText().toString(), signupSA.getText().toString());

                    if (handler.addUser(user, SignupPage.this)) {
                        Toast.makeText(SignupPage.this, "user Added", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(SignupPage.this, LoginPage.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SignupPage.this, "Some Error Occured!", Toast.LENGTH_SHORT).show();
                    }


                    return;
                }
            }
        });


    }


    public void createOptionDialogBox() {


        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_image_picker_options);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageView dialogImagePickerClickImage, dialogImagePickerPickImage;
        dialogImagePickerClickImage = dialog.findViewById(R.id.dialogImagePickerClickImage);
        dialogImagePickerPickImage = dialog.findViewById(R.id.dialogImagePickerPickImage);


        dialogImagePickerPickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Toast.makeText(SignupPage.this, "Picking From Gallery", Toast.LENGTH_SHORT).show();

                Intent choosePictureIntent = new Intent();
                choosePictureIntent.setType("image/*");
                choosePictureIntent.setAction(Intent.ACTION_GET_CONTENT);
                try {
                    activityResultLauncher_choose.launch(choosePictureIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
        dialogImagePickerClickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Toast.makeText(SignupPage.this, "Clicking Image", Toast.LENGTH_SHORT).show();

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    activityResultLauncher_capture.launch(takePictureIntent);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });

        dialog.show();

    }
}