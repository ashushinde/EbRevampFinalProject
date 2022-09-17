package com.palm.newbenefit.Adapter;

import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.palm.newbenefit.Fragment.FaqFragment;
import com.palm.newbenefit.Fragment.FeedBackFragment;
import com.palm.newbenefit.Fragment.QueriesComplaintsFragment;


public class FaqTabAdapter extends FragmentStatePagerAdapter {
    int tabCount;


    Bundle bundle;

    public FaqTabAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;

    }

    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                FaqFragment tab1 = new FaqFragment();
                return tab1;
            case 1:
                QueriesComplaintsFragment tab2 = new QueriesComplaintsFragment();
                return tab2;

            case 2:
                FeedBackFragment tab3 = new FeedBackFragment();
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

