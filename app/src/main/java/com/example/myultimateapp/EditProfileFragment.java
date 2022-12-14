package com.example.myultimateapp;

import static android.content.Context.MODE_PRIVATE;

import static androidx.media.MediaBrowserServiceCompat.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class EditProfileFragment extends Fragment {

    TextView editProfileInstructions, editProfileDOB;
    Spinner editProfileTitle;
    EditText editProfileFirstName, editProfileLastName, editProfileUsername, editProfileEmail, editProfilePhone, editProfileAddress, editProfilePostal, editProfileSQ, editProfileSA;
    Button editProfileBtn;

    Bitmap myEditedImage = null;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Boolean validUsername = true;

    Boolean validFirstName = true;
    Boolean validPhone = true;
    Boolean validEmail = true;

    Boolean validSQ = true;
    Boolean validSA = true;

    ImageView editProfileDisplayPic;
    FloatingActionButton editProfileEditPicBtn;

    ActivityResultLauncher<Intent> activityResultLauncher_capture, activityResultLauncher_choose;


    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);


        activityResultLauncher_choose = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result ->
        {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();

                if (data != null && data.getData() != null) {
                    Uri selectedImageUri = data.getData();
                    Bitmap selectedImageBitmap;
                    try {
                        selectedImageBitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectedImageUri);
                        myEditedImage = selectedImageBitmap;
                        editProfileDisplayPic.setImageBitmap(selectedImageBitmap);
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

                    myEditedImage = photo;
                    editProfileDisplayPic.setImageBitmap(photo);
                } else {
                    Log.d("capturingImage", result.toString());
                    Log.d("capturingImage", String.valueOf(result.getResultCode()));
                    Toast.makeText(getContext(), "Some Error occured", Toast.LENGTH_SHORT).show();
                }

            }
        });


        dbHandler handler = new dbHandler(getContext(), "myApp", null, 1);

        editProfileDisplayPic = view.findViewById(R.id.editProfileDisplayPic);
        editProfileEditPicBtn = view.findViewById(R.id.editProfileEditPicButton);

        editProfileInstructions = view.findViewById(R.id.instructions);
        editProfileTitle = view.findViewById(R.id.editProfileTitle);
        editProfileFirstName = view.findViewById(R.id.editProfileFirstName);
        editProfileLastName = view.findViewById(R.id.editProfileLastName);
        editProfileUsername = view.findViewById(R.id.editProfileUserName);
        editProfileDOB = view.findViewById(R.id.editProfileDOB);
        editProfileEmail = view.findViewById(R.id.editProfileEmail);
        editProfilePhone = view.findViewById(R.id.editProfilePhone);

        editProfileAddress = view.findViewById(R.id.editProfileAddress);
        editProfilePostal = view.findViewById(R.id.editProfilePostal);
        editProfileSQ = view.findViewById(R.id.editProfileSQ);
        editProfileSA = view.findViewById(R.id.editProfileSA);
        editProfileBtn = view.findViewById(R.id.editProfileButton);


        SharedPreferences sp = getActivity().getSharedPreferences("Current User", MODE_PRIVATE);
        String username = sp.getString("LoggedInUser", "");

        Log.d("editprofile", "onCreateView: " + username);


        UserDetails user = handler.fetchUserUsingUserName(username);

        myEditedImage = handler.StringToBitMap(user.getImageurls());
        if (myEditedImage != null)
            editProfileDisplayPic.setImageBitmap(myEditedImage);
        else
            editProfileDisplayPic.setImageResource(R.drawable.person);


        Log.d("editprofile", "onCreateView: " + user);

        List<String> titles = new ArrayList<String>();
        titles.add("Mr.");
        titles.add("Mrs.");
        titles.add("Miss");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, titles);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editProfileTitle.setAdapter(dataAdapter);

        int spinnerPosition = dataAdapter.getPosition(user.getTitle());
        editProfileTitle.setSelection(spinnerPosition);

        editProfileFirstName.setText(user.getFirstName());
        editProfileLastName.setText(user.getLastName());
        editProfileUsername.setText(user.getUsername());
        editProfileDOB.setText(user.getDob());
        editProfileEmail.setText(user.getEmail());
        editProfilePhone.setText(user.getPhone());

        editProfileAddress.setText(user.getAddress());
        editProfilePostal.setText(user.getPostalcode());
        editProfileSQ.setText(user.getSecurityquestion());
        editProfileSA.setText(user.getSecurityanswer());


        editProfileDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar newCalendar = Calendar.getInstance();
                final DatePickerDialog StartTime = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        Calendar newDate = Calendar.getInstance();

                        newDate.set(year, month, day);
                        Log.d("newdata", year + "," + (month + 1) + "," + day);
                        editProfileDOB.setText(year + "-" + (month + 1) + "-" + day);

                    }

                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                StartTime.getDatePicker().setMaxDate(new Date().getTime());
                StartTime.show();
            }
        });
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


                    UserDetails fetcheduser = handler.fetchUserUsingUserName(s);
                    Log.d("editing", "onTextChanged: " + user);
                    Log.d("editing", "onTextChanged: " + fetcheduser);
                    if (fetcheduser != null) {
                        if (user.getSno() != fetcheduser.getSno()) {
                            editProfileInstructions.setText("This Username already exists");
                            validUsername = false;
                            return;
                        }


                    }


                    editProfileInstructions.setText("");
                    validUsername = true;
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

                Log.d("emailCheck", email);


                if (handler.fetchUserUsingEmail(email) == user) {
                    validEmail = true;
                    editProfileInstructions.setText("");
                    return;
                }

                if (handler.fetchUserUsingEmail(email) != null) {

                    editProfileInstructions.setText("An account already exists with this email account");
                    validEmail = false;
                    return;
                }

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
                Log.d("editPhone", s + " " + user.getUsername());

                if (handler.fetchUserUsingPhoneNumber(s) == user) {
                    validPhone = true;
                    editProfileInstructions.setText("");
                    return;
                }

                if (handler.fetchUserUsingPhoneNumber(s) != null) {
                    editProfileInstructions.setText("An account already exists with this phone number");
                    validPhone = false;
                    Log.d("editPhone", s + " " + handler.fetchUserUsingPhoneNumber(s).getUsername() + " " + user.getUsername());

                    return;
                }

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


        editProfileEditPicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getContext(), "Editing Image", Toast.LENGTH_SHORT).show();
                createOptionDialogBox();


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

                    if (!validEmail) {
                        editProfileInstructions.setText("Enter Valid Email");
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
                if (validUsername && validFirstName && validPhone && validEmail && validSQ && validSA) {


                    String editProfileImageString = handler.BitMapToString(myEditedImage);


                    String gender = "Female";
                    if (editProfileTitle.getSelectedItem().equals("Mr."))
                        gender = "Male";

                    UserDetails userUpdated = new UserDetails(user.getSno(), editProfileTitle.getSelectedItem().toString(), editProfileFirstName.getText().toString(), editProfileLastName.getText().toString(), editProfileUsername.getText().toString(), user.getPassword(), editProfileDOB.getText().toString(), gender, editProfileEmail.getText().toString(), editProfilePhone.getText().toString(), editProfileImageString, editProfileAddress.getText().toString(), editProfilePostal.getText().toString(), editProfileSQ.getText().toString(), editProfileSA.getText().toString());
//                    UserDetails userUpdated = new UserDetails(user.getSno(), editProfileTitle.getSelectedItem().toString(), editProfileFirstName.getText().toString(), editProfileLastName.getText().toString(), editProfileUsername.getText().toString(), user.getPassword(), editProfileDOB.getText().toString(), gender, editProfileEmail.getText().toString(), editProfilePhone.getText().toString(), editProfileImage.getText().toString(), editProfileAddress.getText().toString(), editProfilePostal.getText().toString(), editProfileSQ.getText().toString(), editProfileSA.getText().toString());

                    if (handler.updateUser(userUpdated, getContext())) {
                        Toast.makeText(getContext(), "user updated", Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor ed = sp.edit();
                        ed.putString("LoggedInUser", userUpdated.getUsername());
                        ed.apply();

                        Intent intent = new Intent(getContext(), MainPageActivity.class);
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


    public void createOptionDialogBox() {


        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_image_picker_options);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageView dialogImagePickerClickImage, dialogImagePickerPickImage;
        dialogImagePickerClickImage = dialog.findViewById(R.id.dialogImagePickerClickImage);
        dialogImagePickerPickImage = dialog.findViewById(R.id.dialogImagePickerPickImage);


        dialogImagePickerPickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Toast.makeText(getContext(), "Picking From Gallery", Toast.LENGTH_SHORT).show();

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
                Toast.makeText(getContext(), "Clicking Image", Toast.LENGTH_SHORT).show();

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
