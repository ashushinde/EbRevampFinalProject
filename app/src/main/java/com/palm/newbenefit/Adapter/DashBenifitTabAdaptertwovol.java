package com.palm.newbenefit.Adapter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.palm.newbenefit.Fragment.CoverDataFragmentVol;
import com.palm.newbenefit.Fragment.FinalbenifitSummaryFragment;


public class DashBenifitTabAdaptertwovol extends FragmentStatePagerAdapter {
    int tabCount;


    Bundle bundle;

    public DashBenifitTabAdaptertwovol(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;

    }

    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                CoverDataFragmentVol tab1 = new CoverDataFragmentVol();
                return tab1;
            case 1:
                FinalbenifitSummaryFragment tab2 = new FinalbenifitSummaryFragment();
                return tab2;




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

