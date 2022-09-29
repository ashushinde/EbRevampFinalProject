package com.palm.newbenefit.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kmd.newbenefit.R;


public class NoMemberCoverdFragment extends Fragment {


    public NoMemberCoverdFragment() {
        // Required empty public constructor
    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_no_member_coverd, container, false);
        return  v;
    }
}