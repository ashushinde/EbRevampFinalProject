package com.palm.newbenefit.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class InrollmentAdapterTabAdapterFour extends FragmentStatePagerAdapter {
    int tabCount;


    Bundle bundle;

    public InrollmentAdapterTabAdapterFour(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount = tabCount;

    }


    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                EmployeeDetailJava tab1 = new EmployeeDetailJava();
                return tab1;




            case 1:
                MemberEnrolledFragment tab3 = new MemberEnrolledFragment();
                return tab3;






            case 2:
                AddCoverDynamicFragment tab6 = new AddCoverDynamicFragment();
                return tab6;

            case 3:
                BenifitSummaryFragment tab7 = new BenifitSummaryFragment();
                return tab7;

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

