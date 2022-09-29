package com.palm.newbenefit.Adapter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.palm.newbenefit.Fragment.CoverDataFragmentGroup;
import com.palm.newbenefit.Fragment.CoverDataFragmentVol;
import com.palm.newbenefit.Fragment.FinalbenifitSummaryFragment;
import com.palm.newbenefit.Fragment.FinalbenifitSummaryFragmentDemo;


public class DashBenifitTabAdapter extends FragmentStatePagerAdapter {
    int tabCount;


    Bundle bundle;

    public DashBenifitTabAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;

    }

    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                CoverDataFragmentGroup tab1 = new CoverDataFragmentGroup();
                return tab1;
            case 1:
                CoverDataFragmentVol tab2 = new CoverDataFragmentVol();
                return tab2;

            case 2:
                FinalbenifitSummaryFragmentDemo tab3 = new FinalbenifitSummaryFragmentDemo();
                return tab3;


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

