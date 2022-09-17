package com.palm.newbenefit.Adapter;

import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


import com.palm.newbenefit.Fragment.GroupClaimFragment;
import com.palm.newbenefit.Fragment.PersonalAccFragment;
import com.palm.newbenefit.Fragment.TermLifeFragment;


public class FormAdapter extends FragmentStatePagerAdapter {
    int tabCount;


    Bundle bundle;

    public FormAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount = tabCount;

    }

    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                GroupClaimFragment tab1 = new GroupClaimFragment();
                return tab1;
            case 1:
                PersonalAccFragment tab2 = new PersonalAccFragment();
                return tab2;

            case 2:
                TermLifeFragment tab3 = new TermLifeFragment();

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

