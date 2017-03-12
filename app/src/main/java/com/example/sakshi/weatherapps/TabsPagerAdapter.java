package com.example.sakshi.weatherapps;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by sakshi on 12/3/17.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {
    Fragment fragment;
    static String city;

    public TabsPagerAdapter(FragmentManager fm, String city) {
        super(fm);
        this.city = city;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TopRatedFragment();

            case 1:
                return new TueRatedFragment();

            case 2:
                return new WedRatedFragment();

            default:
                return null;

        }


    }

    @Override
    public int getCount() {
        return 3;
    }
}