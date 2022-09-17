package com.palm.newbenefit.Adapter;

import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.palm.newbenefit.Fragment.MyHosClaimFragment;
import com.palm.newbenefit.Fragment.MyIntimateClaimFragment;


public class MyCliamTabAdapter extends FragmentStatePagerAdapter {
    int tabCount;


    Bundle bundle;

    public MyCliamTabAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount = tabCount;

    }

    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                MyHosClaimFragment tab1 = new MyHosClaimFragment();
                return tab1;
            case 1:
                MyIntimateClaimFragment tab2 = new MyIntimateClaimFragment();
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

