package com.example.myultimateapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainPageActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager2 viewPager2;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openNavigation, R.string.closeNavigation);

        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();


        tabLayout = drawerLayout.findViewById(R.id.container).findViewById(R.id.tabLayoutMainPage);
        viewPager2 = drawerLayout.findViewById(R.id.container).findViewById(R.id.viewPager2MainPage);

        tabLayout.setVisibility(View.VISIBLE);
        viewPager2.setVisibility(View.VISIBLE);


        ViewPager2Adapter adapter = new ViewPager2Adapter(this, MainPageActivity.this);
        viewPager2.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(ViewPager2Adapter.getPageTitle(position));

            }
        }).attach();


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                switch (id) {
                    case R.id.navigationItemHomePage:

                        tabLayout.setVisibility(View.VISIBLE);
                        viewPager2.setVisibility(View.VISIBLE);


                        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                            FragmentManager fm = getSupportFragmentManager();
                            FragmentTransaction ft = fm.beginTransaction();
                            ft.remove(fragment).commit();
                        }

                        break;
                    case R.id.navigationItemLogOut: {


                        logOutClicked();

                    }
                    break;
                    case R.id.navigationItemChangePassword: {
                        Toast.makeText(MainPageActivity.this, "Change Password", Toast.LENGTH_SHORT).show();


//                        Intent intent = new Intent(MainPageActivity.this, ChangePasswordActivity.class);
                        Intent intent = new Intent(MainPageActivity.this, AskSecurityQuestionActivity.class);
                        String username = getSharedPreferences("Current User", MODE_PRIVATE).getString("LoggedInUser", "");
                        Log.d("Checking", username);
                        intent.putExtra("username", username);
                        startActivity(intent);

                    }
                    break;
                    case R.id.navigationItemYourProfile:
                        loadFragment(new YourProfileFragment());
                        break;
                    case R.id.navigationItemJokesPage: {
                        loadFragment(new JokesFragment());
                        break;
                    }
                    case R.id.navigationItemActivities: {
                        loadFragment(new ActivitiesFragment());
                        break;
                    }
                    case R.id.navigationItemDogImages: {
                        loadFragment(new DogImagesFragment());
                        break;
                    }
                    case R.id.navigationItemEditProfile: {
                        loadFragment(new EditProfileFragment());
                        break;
                    }
                    case R.id.navigationItemSendBroadcastMessage: {
                        loadFragment(new SendBroadCastFragment());
                        break;
                    }
                    case R.id.navigationItemReceiveBroadcastMessage: {
                        loadFragment(new ReceiveBroadCastFragment());
                        break;
                    }
                    case R.id.navigationItemWebView: {
                        loadFragment(new WebViewFragment());
                        break;
                    }
                    case R.id.navigationItemNewActivity: {
                        Intent intent=new Intent(getApplicationContext(),OneActivity.class);
                        startActivity(intent);
                        break;
                    }
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });


    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();

    }


    private void loadFragment(Fragment fragment) {
        findViewById(R.id.tabLayoutMainPage).setVisibility(View.GONE);
        findViewById(R.id.viewPager2MainPage).setVisibility(View.GONE);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();


        ft.replace(R.id.container, fragment);
        ft.commit();
    }

//    AirplaneModeChangeReceiver airplaneModeChangeReceiver = new AirplaneModeChangeReceiver();
//    NetworkChangeBroadcast networkChangeBroadcast = new NetworkChangeBroadcast();
//
//    @Override
//    protected void onStart() {
//        super.onStart();
////        IntentFilter filter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
////        IntentFilter filter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
////
////        registerReceiver(airplaneModeChangeReceiver, filter);
//        IntentFilter intent = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
//        registerReceiver(networkChangeBroadcast, intent);
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        unregisterReceiver(networkChangeBroadcast);
////        unregisterReceiver(airplaneModeChangeReceiver);
//    }


    public void logOutClicked() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainPageActivity.this);


        builder.setMessage("Are you sure  you want to Logout ?");
        builder.setCancelable(false);

        SharedPreferences sp = getSharedPreferences("Current User", MODE_PRIVATE);
        Log.d("Checking", "logout clicked " + sp.getString("LoggedInUser", ""));

        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {

            SharedPreferences.Editor ed = sp.edit();
            ed.putString("LoggedInUser", "");
            ed.apply();

            LoginPage.mGoogleSignInClient.signOut();

            Intent intent = new Intent(MainPageActivity.this, LoginPage.class);
            startActivity(intent);
        });

        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {

            dialog.cancel();
        });


        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }


}