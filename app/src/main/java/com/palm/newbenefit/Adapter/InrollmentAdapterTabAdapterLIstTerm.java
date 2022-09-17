package com.palm.newbenefit.Adapter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.palm.newbenefit.Fragment.AddCoverDynamicFragment;
import com.palm.newbenefit.Fragment.AddDependantFragmentFinal;
import com.palm.newbenefit.Fragment.MemberEnrolledFragment;
import com.palm.newbenefit.Fragment.NomineeEnrollFragment;


public class InrollmentAdapterTabAdapterLIstTerm extends FragmentStatePagerAdapter {
    int tabCount;


    Bundle bundle;

    public InrollmentAdapterTabAdapterLIstTerm(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount = tabCount;

    }


    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {


            case 0:
                AddDependantFragmentFinal taba = new AddDependantFragmentFinal();
                return taba;

            case 1:
                MemberEnrolledFragment tab3 = new MemberEnrolledFragment();
                return tab3;



            case 2:
                NomineeEnrollFragment tab5 = new NomineeEnrollFragment();
                return tab5;

            case 3:
                AddCoverDynamicFragment tab6 = new AddCoverDynamicFragment();
                return tab6;





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

