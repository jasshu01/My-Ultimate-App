package com.example.myultimateapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPassword_GetUsername extends AppCompatActivity {
    EditText username;
    Button confirm;
    Button cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_get_username);

        dbHandler handler = new dbHandler(this, "myApp", null, 1);
        username=findViewById(R.id.ForgotPassword_username);
        confirm=findViewById(R.id.ForgotPassword_usernameCheck);
        cancel=findViewById(R.id.ForgotPassword_cancel);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str= String.valueOf(username.getText());
                UserDetails user=handler.fetchUserUsingUserName(str);
                if(user!=null)
                {
                    Intent intent=new Intent(ForgotPassword_GetUsername.this,AskSecurityQuestionActivity.class);
                    intent.putExtra("username",str);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(ForgotPassword_GetUsername.this, "Invalid Username", Toast.LENGTH_SHORT).show();

                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}