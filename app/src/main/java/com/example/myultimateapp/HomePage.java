package com.example.myultimateapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class HomePage extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager2;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        SharedPreferences sp = getSharedPreferences("Current User", MODE_PRIVATE);


        dbHandler handler = new dbHandler(HomePage.this, "myApp", null, 1);

        //        TITLE ,FIRSTNAME ,LASTNAME ,USERNAME ,PASSWORD ,DOB ,GENDER ,EMAIL ,PHONE ,IMAGEURLS ,ADDRESS ,POSTALCODE ,SECURITYQUESTION ,SECURITYANSWER
//        handler.addUser(new UserDetails("Mr","Jasshu","garg","jasshu02","jasshu@01","2001-08-01","male","jasshu.garg@gmail.com","8195850098","","tapa","148108","company","byjus"),HomePage.this);
//        handler.addUser(new UserDetails("Mr","Jasshu","garg","jasshu03","jasshu@01","2001-08-01","male","jasshu.garg@gmail.com","8195850098","","tapa","148108","company","byjus"),HomePage.this);

//        handler.updateUser(new UserDetails(2,"Mr","Jasshu","garg","jasshu266","jasshu@01","2001-08-01","male","jasshu.garg@gmail.com","8195850098","","tapa","148108","company","byjus"),HomePage.this);
//        handler.updateUser(new UserDetails(3,"Mr","Jasshu","garg","jasshu266","jasshu@01","2001-08-01","male","jasshu.garg@gmail.com","8195850098","","tapa","148108","company","byjus"),HomePage.this);


        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.view_pager2);



        ViewPager2Adapter adapter = new ViewPager2Adapter(this,HomePage.this);
        viewPager2.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        tab.setText(ViewPager2Adapter.getPageTitle(position));

            }

        }).attach();
    }


}
