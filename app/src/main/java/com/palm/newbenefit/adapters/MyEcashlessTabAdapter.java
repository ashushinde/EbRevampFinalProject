package com.palm.newbenefit.adapters;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.palm.newbenefit.Fragment.PlanHospitalizationFragment;
import com.palm.newbenefit.Fragment.e_cashless_claim;


public class MyEcashlessTabAdapter extends FragmentStatePagerAdapter {
    int tabCount;


    Bundle bundle;

    public MyEcashlessTabAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount = tabCount;

    }

    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                PlanHospitalizationFragment tab1 = new PlanHospitalizationFragment();
                return tab1;
            case 1:
                e_cashless_claim tab2 = new e_cashless_claim();
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

