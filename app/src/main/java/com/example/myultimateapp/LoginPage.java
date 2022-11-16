package com.example.myultimateapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Size;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class LoginPage extends AppCompatActivity {

    private static final int RC_SIGN_IN = 266;
    EditText userName, password;
    TextView passwordInstructions, userNameInstructions, signupOption, ForgotPassword, signinwithgoogle;
    Button loginButton;
    Boolean ValidPassword = false;
    Boolean ValidUserName = false;
    dbHandler handler;
    public static GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onStart() {
        super.onStart();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        SharedPreferences sp = getSharedPreferences("Current User", MODE_PRIVATE);

        String CheckingIfAlreadyLoggedIn = sp.getString("LoggedInUser", "");

        if (!CheckingIfAlreadyLoggedIn.equals("")) {
            Intent intent = new Intent(LoginPage.this, MainPageActivity.class);
            startActivity(intent);
        }


    }

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
        signinwithgoogle = findViewById(R.id.signinwithgoogle);

        userNameInstructions.setVisibility(View.GONE);
        passwordInstructions.setVisibility(View.GONE);
        handler = new dbHandler(LoginPage.this, "myApp", null, 1);


//        userName.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
//                String username = s.toString();
//                if (username.length() >= 5) {
//                    userNameInstructions.setText("");
//                    ValidUserName = true;
//
//
//                } else {
//                    userNameInstructions.setTextColor(Color.argb(255, 255, 0, 0));
//
//                    ValidUserName = false;
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//
//        password.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
//                boolean numericPresent = false;
//                ValidPassword = false;
//                passwordInstructions.setTextColor(Color.argb(255, 255, 0, 0));
//                String password = s.toString();
//                if (password.length() == 0) {
//                    passwordInstructions.setText("");
//                } else {
//                    if (password.length() < 8)
//                        passwordInstructions.setText("Password Length Should be minimum 8 Characters");
//                    else if (password.contains("@") || password.contains("#") || password.contains("$")) {
//
//
//                        for (int k = 0; k <= 9; k++) {
//                            if (password.contains(String.valueOf(k)))
//                                numericPresent = true;
//
//                        }
//
//                        if (numericPresent == false) {
//                            passwordInstructions.setText("Add atleast one digit");
//                        } else {
//                            passwordInstructions.setText("");
//                        }
//
//                    } else {
//                        passwordInstructions.setText("Add atleast one Special Chatacter @,#,$");
//                    }
//
//                }
//
//                if (numericPresent && password.length() >= 8 && (password.contains("@") || password.contains("#") || password.contains("$"))) {
//                    passwordInstructions.setText("");
//                    passwordInstructions.setTextColor(Color.argb(255, 0, 255, 0));
//                    ValidPassword = true;
//                }
//
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if(!ValidPassword)
//                {
//                    Toast.makeText(LoginPage.this, "Password is not Valid", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if(!ValidUserName)
//                {
//                    Toast.makeText(LoginPage.this, "UserName is not Valid", Toast.LENGTH_SHORT).show();
//                    return;
//                }


                String final_username, final_password;
                final_username = String.valueOf(userName.getText());

                final_password = String.valueOf(password.getText());


                UserDetails user = handler.fetchUserUsingUserName(final_username);
                if (user == null) {
                    Toast.makeText(LoginPage.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (!user.getPassword().equals(final_password)) {
                    Toast.makeText(LoginPage.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    return;
                }

                LoginSuccessful(final_username);


            }
        });


        signupOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginPage.this, SignupPage.class);
                startActivity(intent);
            }
        });

        ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginPage.this, ForgotPassword_GetUsername.class);
                startActivity(intent);
            }
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        signinwithgoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("GOOGLE_ERROR", "signing in");
                signIn();

            }
        });

    }

    private void signIn() {


        Log.d("GOOGLE_ERROR", " in sign in");
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        try {

            googleSignInResultLauncher.launch(signInIntent);
        } catch (Exception e) {
            Log.d("GOOGLE_ERROR", "signin " + e.toString());
        }
    }


    ActivityResultLauncher<Intent> googleSignInResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    Log.d("GOOGLE_ERROR", " in  launcher 1");
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();


                        Log.d("GOOGLE_ERROR", " in  launcher");
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        handleSignInResult(task);
                    }
                }
            });


    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {

        Log.d("GOOGLE_ERROR", " in handle");
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            String personEmail = account.getEmail();

            ArrayList<String> associatedAccounts = handler.fetchUserUsingEmail(personEmail);

            CreateDialogBox(associatedAccounts);


//            LoginSuccessful("username");
            Log.d("GOOGLE_DATA", " in handle" + personEmail + " ");

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.d("GOOGLE_ERROR", "signInResult: =" + e.toString());
        }
    }

    private void LoginSuccessful(String username) {

        SharedPreferences sp = getSharedPreferences("Current User", MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString("LoggedInUser", username);
        ed.apply();

        mGoogleSignInClient.signOut();

        Intent intent = new Intent(LoginPage.this, MainPageActivity.class);
        startActivity(intent);

    }


    private void CreateDialogBox(ArrayList<String> usernames) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_associated_usernames_layout);
        dialog.setTitle("Customer Info");

        LinearLayout linearLayout = dialog.findViewById(R.id.ll_details);


        for (int i = 0; i < usernames.size(); i++) {
            String username = usernames.get(i);

            TextView textView1 = new TextView(this);
            textView1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            textView1.setGravity(Gravity.CENTER);
            textView1.setText(username);
            textView1.setPadding(20, 20, 20, 20);

            textView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LoginSuccessful(username);
                    return;
                }
            });



            linearLayout.addView(textView1);

        }

        if(usernames.size()==0)
        {
            TextView textView1 = new TextView(this);
            textView1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            textView1.setGravity(Gravity.CENTER);
            textView1.setText("NO ACCOUNT ASSOCIATED WITH THIS GMAIL ACCOUNT");
            textView1.setPadding(20, 20, 20, 20);

        }


        Button dialogUsernamesCancel=dialog.findViewById(R.id.dialogUsernamesCancel);
        dialogUsernamesCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
        dialog.show();

    }

}