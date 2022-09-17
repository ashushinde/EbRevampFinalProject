package com.palm.newbenefit.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.palm.newbenefit.Activity.MainActivity;
import com.palm.newbenefit.Adapter.IntimateClaimTabAdapter;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class IntimateClaimFragment extends Fragment {
    private ViewPager viewPager;
    Button plan, summary;
    Bundle bundle;
    boolean respnce = false;
    IntimateClaimTabAdapter adapter;
    Constants con;
    LinearLayout group_cover,form_center_two;



    public IntimateClaimFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_intimate_claim, container, false);

        con = new Constants();


        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).setTitle("");




        group_cover= (LinearLayout) v.findViewById(R.id.group_cover);
        form_center_two= (LinearLayout) v.findViewById(R.id.form_center_two);


        viewPager = (ViewPager) v.findViewById(R.id.pager);

        //Creating our pager adapter
        IntimateClaimTabAdapter adapter = new IntimateClaimTabAdapter(getFragmentManager(), 2);

        //Adding adapter to pager
        viewPager.setAdapter(adapter);


        group_cover.setBackgroundResource(R.drawable.click_on_tab);
        form_center_two.setBackgroundResource(R.drawable.click_tab);


        group_cover.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                viewPager.setCurrentItem(0);
                group_cover.setBackgroundResource(R.drawable.click_on_tab);
                form_center_two.setBackgroundResource(R.drawable.click_tab);


            }
        });

        form_center_two.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                viewPager.setCurrentItem(1);
                form_center_two.setBackgroundResource(R.drawable.click_on_tab);
                group_cover.setBackgroundResource(R.drawable.click_tab);


            }
        });





        return v;


    }






}