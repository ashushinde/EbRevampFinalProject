package com.palm.newbenefit.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.palm.newbenefit.Activity.MainActivity;

import com.palm.newbenefit.ApiConfig.Constants;
import com.kmd.newbenefit.R;
import com.palm.newbenefit.adapters.MyEcashlessTabAdapter;


public class ECashlessFragment extends Fragment {
    private ViewPager viewPager;
    Button plan, summary;
    Bundle bundle;
    boolean respnce = false;
    MyEcashlessTabAdapter adapter;
    Constants con;
    LinearLayout group_cover,form_center_two,form_center_three;



    public ECashlessFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_e_cashless, container, false);

        con = new Constants();


        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).setTitle("");




        group_cover= (LinearLayout) v.findViewById(R.id.group_cover);
        form_center_two= (LinearLayout) v.findViewById(R.id.form_center_two);

        viewPager = (ViewPager) v.findViewById(R.id.pager);

        //Creating our pager adapter
        MyEcashlessTabAdapter adapter = new MyEcashlessTabAdapter(getFragmentManager(), 2);

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
                //form_center_three.setBackgroundResource(R.drawable.click_tab);

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
               // form_center_three.setBackgroundResource(R.drawable.click_tab);

            }
        });





        return v;


    }






}
