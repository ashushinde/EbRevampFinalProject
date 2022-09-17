package com.palm.newbenefit.Adapter;

import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.palm.newbenefit.Fragment.CashLessFragment;
import com.palm.newbenefit.Fragment.ReimbursmentClaimFragment;


public class IntimateClaimTabAdapter extends FragmentStatePagerAdapter {
    int tabCount;


    Bundle bundle;

    public IntimateClaimTabAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount = tabCount;

    }

    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                ReimbursmentClaimFragment tab1 = new ReimbursmentClaimFragment();
                return tab1;
            case 1:
                CashLessFragment tab2 = new CashLessFragment();
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

