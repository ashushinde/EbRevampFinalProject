package com.palm.newbenefit.Adapter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.palm.newbenefit.Fragment.FinalbenifitSummaryFragment;
import com.palm.newbenefit.Fragment.FinalbenifitSummaryFragmentDemo;


public class DashBenifitTabAdaptersingle extends FragmentStatePagerAdapter {
    int tabCount;


    Bundle bundle;

    public DashBenifitTabAdaptersingle(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;

    }

    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                FinalbenifitSummaryFragmentDemo tab1 = new FinalbenifitSummaryFragmentDemo();
                return tab1;


                default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }

}

