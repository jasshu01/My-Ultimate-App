package com.example.myultimateapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class HomePage extends AppCompatActivity {
    TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        username=findViewById(R.id.userName);
        SharedPreferences sp = getSharedPreferences("Current User", MODE_PRIVATE);

        username.setText(sp.getString("LoggedInUser","No Logged In User Exists"));

        dbHandler handler=new dbHandler(HomePage.this,"myApp",null,1);

        //        TITLE ,FIRSTNAME ,LASTNAME ,USERNAME ,PASSWORD ,DOB ,GENDER ,EMAIL ,PHONE ,IMAGEURLS ,ADDRESS ,POSTALCODE ,SECURITYQUESTION ,SECURITYANSWER
//        handler.addUser(new UserDetails("Mr","Jasshu","garg","jasshu02","jasshu@01","2001-08-01","male","jasshu.garg@gmail.com","8195850098","","tapa","148108","company","byjus"),HomePage.this);
//        handler.addUser(new UserDetails("Mr","Jasshu","garg","jasshu03","jasshu@01","2001-08-01","male","jasshu.garg@gmail.com","8195850098","","tapa","148108","company","byjus"),HomePage.this);

//        handler.updateUser(new UserDetails(2,"Mr","Jasshu","garg","jasshu266","jasshu@01","2001-08-01","male","jasshu.garg@gmail.com","8195850098","","tapa","148108","company","byjus"),HomePage.this);
//        handler.updateUser(new UserDetails(3,"Mr","Jasshu","garg","jasshu266","jasshu@01","2001-08-01","male","jasshu.garg@gmail.com","8195850098","","tapa","148108","company","byjus"),HomePage.this);


        handler.deleteUser(2);
        handler.fetchUserUsingUserName("jasshu266");



    }
}