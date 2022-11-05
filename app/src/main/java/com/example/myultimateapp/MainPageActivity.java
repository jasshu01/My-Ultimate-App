package com.example.myultimateapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);

        @SuppressLint("ResourceType") ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openNavigation, R.string.closeNavigation);

        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();


        tabLayout = drawerLayout.findViewById(R.id.container).findViewById(R.id.tabLayoutMainPage);
        viewPager2 = drawerLayout.findViewById(R.id.container).findViewById(R.id.viewPager2MainPage);


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

                        findViewById(R.id.tabLayoutMainPage).setVisibility(View.VISIBLE);
                        findViewById(R.id.viewPager2MainPage).setVisibility(View.VISIBLE);




                        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                            FragmentManager fm = getSupportFragmentManager();
                            FragmentTransaction ft = fm.beginTransaction();
                            ft.remove(fragment).commit();
                        }

                        break;
                    case R.id.navigationItemLogOut:
                        Toast.makeText(MainPageActivity.this, "Logging out TODO", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigationItemYourProfile:
                        loadFragment(new YourProfileFragment(),1);
                        break;
                    case R.id.navigationItemJokesPage: {
                        loadFragment(new JokesFragment(MainPageActivity.this), 1);
                        break;
                    }
                    case R.id.navigationItemActivities: {
                        loadFragment(new ActivitiesFragment(MainPageActivity.this), 1);
                        break;
                    }
                    case R.id.navigationItemDogImages: {
                        loadFragment(new DogImagesFragment(MainPageActivity.this), 1);
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


    private void loadFragment(Fragment fragment, int flag) {
        findViewById(R.id.tabLayoutMainPage).setVisibility(View.GONE);
        findViewById(R.id.viewPager2MainPage).setVisibility(View.GONE);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if (flag == 0)
            ft.add(R.id.container, fragment);
        else
            ft.replace(R.id.container, fragment);
        ft.commit();
    }
}