package com.palm.newbenefit.Adapter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.palm.newbenefit.Fragment.AddCoverDynamicFragment;
import com.palm.newbenefit.Fragment.AddDependantFragmentFinal;
import com.palm.newbenefit.Fragment.BenifitsumFragment;
import com.palm.newbenefit.Fragment.EmployeeDetailJava;
import com.palm.newbenefit.Fragment.MemberEnrolledFragment;


public class InrollmentAdapterTabAdapternomwithout extends FragmentStatePagerAdapter {
    int tabCount;


    Bundle bundle;

    public InrollmentAdapterTabAdapternomwithout(FragmentManager fm, int tabCount) {
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
                 AddDependantFragmentFinal tab2 = new AddDependantFragmentFinal();
                return tab2;

            case 2:
                MemberEnrolledFragment tab3 = new MemberEnrolledFragment();
                return tab3;



            case 3:
                AddCoverDynamicFragment tab4 = new AddCoverDynamicFragment();
                return tab4;

            case 4:
                BenifitsumFragment tab5 = new BenifitsumFragment();
                return tab5;

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

