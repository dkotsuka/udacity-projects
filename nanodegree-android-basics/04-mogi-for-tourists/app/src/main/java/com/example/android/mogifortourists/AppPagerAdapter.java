package com.example.android.mogifortourists;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by dkots on 05/11/2017.
 */

public class AppPagerAdapter extends FragmentPagerAdapter {


    public AppPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new HistoryFragment();
        } else if (position == 1){
            return new SpotsFragment();
        } else if (position == 2){
            return new RestaurantsFragment();
        } else if (position == 3){
            return new ToBuyFragment();
        } else {
            return new FestivalsFragment();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }

}
