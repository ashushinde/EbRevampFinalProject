package com.palm.newbenefit.Adapter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.palm.newbenefit.Fragment.BuyInsuranceCoverFragment;
import com.palm.newbenefit.Fragment.InsuranceCoverFragment;
import com.palm.newbenefit.Fragment.RenuewalFragment;


public class MyPolicyTabAdapter extends FragmentStatePagerAdapter {
    int tabCount;


    Bundle bundle;

    public MyPolicyTabAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount = tabCount;

    }

    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                RenuewalFragment tab1 = new RenuewalFragment();
                return tab1;
            case 1:
                 InsuranceCoverFragment tab2 = new InsuranceCoverFragment();
                return tab2;

            case 2:
                BuyInsuranceCoverFragment tab3 = new BuyInsuranceCoverFragment();
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

