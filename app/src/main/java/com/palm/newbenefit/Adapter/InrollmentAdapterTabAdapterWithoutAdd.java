package com.palm.newbenefit.Adapter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.palm.newbenefit.Fragment.AddCoverDynamicFragment;
import com.palm.newbenefit.Fragment.BenifitSummaryFragment;
import com.palm.newbenefit.Fragment.EmployeeDetailJava;
import com.palm.newbenefit.Fragment.MemberEnrolledFragment;


public class InrollmentAdapterTabAdapterWithoutAdd extends FragmentStatePagerAdapter {
    int tabCount;


    Bundle bundle;

    public InrollmentAdapterTabAdapterWithoutAdd(FragmentManager fm, int tabCount) {
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


            case 4:
                AddCoverDynamicFragment tab6 = new AddCoverDynamicFragment();
                return tab6;

            case 5:
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

