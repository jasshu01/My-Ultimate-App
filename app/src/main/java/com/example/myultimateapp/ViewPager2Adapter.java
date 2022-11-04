package com.example.myultimateapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPager2Adapter extends FragmentStateAdapter {

    Context context;

    public ViewPager2Adapter(@NonNull FragmentActivity fragmentActivity, Context context) {
        super(fragmentActivity);
        this.context=context;
    }

    public Fragment createFragment(int position) {
        if (position == 0)
            return new JokesFragment(context);
        if (position == 1)
            return new DogImages( context);
        return null;
    }





    public static CharSequence getPageTitle(int position) {
        if (position == 0)
            return "Jokes";
        if (position == 1)
            return "Dogs Images";
        return null;
    }





    @Override
    public int getItemCount() {
        return 2;
    }
}
