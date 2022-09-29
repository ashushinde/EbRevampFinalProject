package com.palm.newbenefit.Fragment;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.palm.newbenefit.Activity.MainActivity;
import com.palm.newbenefit.Adapter.FormAdapter;
import com.palm.newbenefit.Adapter.InrollmentAdapterTabAdapterLIst;
import com.palm.newbenefit.Adapter.InrollmentAdapterTabAdapterLIstTerm;
import com.palm.newbenefit.Adapter.InrollmentAdapterTabAdapternm;
import com.palm.newbenefit.Adapter.InrollmentAdapterTabAdapternomwithoutnm;
import com.palm.newbenefit.ApiConfig.Constants;
import com.kmd.newbenefit.R;
import com.palm.tatarewamp.SslData.NullHostNameVerifier;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class EnrollMentFragmentJavaWithoutAdd extends Fragment {
    private ViewPager viewPager;
    Button plan, summary;
    Bundle bundle;
    boolean respnce = false;
    FormAdapter adapter;
    Constants con;
    LinearLayout employee,add_member,member_enroll,add_nominee,nominee_enroll,add_cover,show_all;
    TextView employee_text,add_dependent_text,member_enroll_txt,add_nominee_txt
            ,nominne_enroll_txt,add_cover_txt
            ,my_summary_txt;

    String nominne="show";
    String allow="0";

    public EnrollMentFragmentJavaWithoutAdd() {
        // Required empty public constructor
    }
    View[] views = new View[7];

    HorizontalScrollView horizontalScrollView;
    String shown,start_date,end_date,token,policy_id;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_enroll_ment_fragment_java, container, false);
        con = new Constants();


        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).setTitle("");

        SharedPreferences prefs = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);

        token = prefs.getString("api_token", null);
        shown = prefs.getString("shown", null);
        start_date = prefs.getString("start_date", null);
        end_date = prefs.getString("end_date", null);
        policy_id = prefs.getString("policy_id", null);


        if(start_date.equalsIgnoreCase("0")&&end_date.equalsIgnoreCase("0")){

        }else  if(start_date.equalsIgnoreCase("null")&&end_date.equalsIgnoreCase("null")){


        }else if(start_date.equalsIgnoreCase("null")||end_date.equalsIgnoreCase("null")) {

        }else {
            if(shown.equalsIgnoreCase("all")) {
                showDialog();
            }else {

            }

        }




            horizontalScrollView= (HorizontalScrollView) v.findViewById(R.id.horizontalScrollView);
        employee= (LinearLayout) v.findViewById(R.id.employee);
        add_member= (LinearLayout) v.findViewById(R.id.add_member);
        member_enroll= (LinearLayout) v.findViewById(R.id.member_enroll);
        add_nominee= (LinearLayout) v.findViewById(R.id.add_nominee);
        nominee_enroll= (LinearLayout) v.findViewById(R.id.nominee_enroll);
        add_cover= (LinearLayout) v.findViewById(R.id.add_cover);
        show_all= (LinearLayout) v.findViewById(R.id.show_all);

        employee_text= (TextView) v.findViewById(R.id.employee_text);
        add_dependent_text= (TextView) v.findViewById(R.id.add_dependent_text);
        member_enroll_txt= (TextView) v.findViewById(R.id.member_enroll_txt);
        add_nominee_txt= (TextView) v.findViewById(R.id.add_nominee_txt);
        nominne_enroll_txt= (TextView) v.findViewById(R.id.nominne_enroll_txt);
        add_cover_txt= (TextView) v.findViewById(R.id.add_cover_txt);
        my_summary_txt= (TextView) v.findViewById(R.id.my_summary_txt);

        viewPager = (ViewPager) v.findViewById(R.id.pager);
        viewPager.beginFakeDrag();

        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (viewPager.getCurrentItem() == 0) {
                    viewPager.setCurrentItem(-1, false);
                    return true;
                }
                else if (viewPager.getCurrentItem() == 1) {
                    viewPager.setCurrentItem(1, false);
                    return true;
                }
                else if (viewPager.getCurrentItem() == 2) {
                    viewPager.setCurrentItem(2, false);
                    return true;
                }
                else if (viewPager.getCurrentItem() == 3) {
                    viewPager.setCurrentItem(3, false);
                    return true;
                }
                else if (viewPager.getCurrentItem() == 4) {
                    viewPager.setCurrentItem(4, false);
                    return true;
                }
                else if (viewPager.getCurrentItem() == 5) {
                    viewPager.setCurrentItem(5, false);
                    return true;
                }
                else if (viewPager.getCurrentItem() == 6) {
                    viewPager.setCurrentItem(6, false);
                    return true;
                }
                else if (viewPager.getCurrentItem() == 7) {
                    viewPager.setCurrentItem(7, false);
                    return true;
                }
                return true;
            }
        });

        //Creating our pager adapter

        NomineeConfig();


        employee.setBackgroundResource(R.drawable.click_on_tab);
        add_member.setBackgroundResource(R.drawable.click_tab);
        add_nominee.setBackgroundResource(R.drawable.click_tab);
        nominee_enroll.setBackgroundResource(R.drawable.click_tab);
        add_cover.setBackgroundResource(R.drawable.click_tab);
        show_all.setBackgroundResource(R.drawable.click_tab);
        member_enroll.setBackgroundResource(R.drawable.click_tab);

        employee.setOnClickListener(new View.OnClickListener()
        {

            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v)
            {

                viewPager.setCurrentItem(0);
                employee.setBackgroundResource(R.drawable.click_on_tab);
                add_member.setBackgroundResource(R.drawable.click_tab);
                add_nominee.setBackgroundResource(R.drawable.click_tab);
                nominee_enroll.setBackgroundResource(R.drawable.click_tab);
                add_cover.setBackgroundResource(R.drawable.click_tab);
                show_all.setBackgroundResource(R.drawable.click_tab);
                member_enroll.setBackgroundResource(R.drawable.click_tab);


            }
        });



        member_enroll.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                if(shown.equalsIgnoreCase("all")){

                    viewPager.setCurrentItem(1);
                    member_enroll.setBackgroundResource(R.drawable.click_on_tab);
                    employee.setBackgroundResource(R.drawable.click_tab);
                    add_member.setBackgroundResource(R.drawable.click_tab);
                    add_nominee.setBackgroundResource(R.drawable.click_tab);
                    nominee_enroll.setBackgroundResource(R.drawable.click_tab);
                    add_cover.setBackgroundResource(R.drawable.click_tab);
                    show_all.setBackgroundResource(R.drawable.click_tab);


                    int[] loc = new int[2];
                    member_enroll.getLocationInWindow(loc);
                    horizontalScrollView.scrollTo(loc[0], 0);

                }else {
                    if(allow.equalsIgnoreCase("1")){
                        viewPager.setCurrentItem(1);
                    }else {
                        viewPager.setCurrentItem(0);
                    }

                    member_enroll.setBackgroundResource(R.drawable.click_on_tab);
                    employee.setBackgroundResource(R.drawable.click_tab);
                    add_member.setBackgroundResource(R.drawable.click_tab);
                    add_nominee.setBackgroundResource(R.drawable.click_tab);
                    nominee_enroll.setBackgroundResource(R.drawable.click_tab);
                    add_cover.setBackgroundResource(R.drawable.click_tab);
                    show_all.setBackgroundResource(R.drawable.click_tab);



                }




            }
        });

        add_nominee.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                    viewPager.setCurrentItem(2);


                add_nominee.setBackgroundResource(R.drawable.click_on_tab);
                employee.setBackgroundResource(R.drawable.click_tab);
                add_member.setBackgroundResource(R.drawable.click_tab);
                member_enroll.setBackgroundResource(R.drawable.click_tab);
                nominee_enroll.setBackgroundResource(R.drawable.click_tab);
                add_cover.setBackgroundResource(R.drawable.click_tab);
                show_all.setBackgroundResource(R.drawable.click_tab);


            }
        });


        nominee_enroll.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)


            {

                if(shown.equalsIgnoreCase("all")) {
                    viewPager.setCurrentItem(3);
                    nominee_enroll.setBackgroundResource(R.drawable.click_on_tab);
                    employee.setBackgroundResource(R.drawable.click_tab);
                    add_member.setBackgroundResource(R.drawable.click_tab);
                    add_nominee.setBackgroundResource(R.drawable.click_tab);
                    member_enroll.setBackgroundResource(R.drawable.click_tab);
                    add_cover.setBackgroundResource(R.drawable.click_tab);
                    show_all.setBackgroundResource(R.drawable.click_tab);

                }else {
                    nominee_enroll.setBackgroundResource(R.drawable.click_on_tab);
                    employee.setBackgroundResource(R.drawable.click_tab);
                    add_member.setBackgroundResource(R.drawable.click_tab);
                    add_nominee.setBackgroundResource(R.drawable.click_tab);
                    member_enroll.setBackgroundResource(R.drawable.click_tab);
                    add_cover.setBackgroundResource(R.drawable.click_tab);
                    show_all.setBackgroundResource(R.drawable.click_tab);
                    if(allow.equalsIgnoreCase("1")){
                        viewPager.setCurrentItem(2);
                    }else {
                        viewPager.setCurrentItem(1);
                    }

                }


            }
        });


        add_cover.setOnClickListener(new View.OnClickListener()
        {

            @SuppressLint("Range")
            @Override
            public void onClick(View v)
            {


                if(shown.equalsIgnoreCase("all")) {


                    if(nominne.equalsIgnoreCase("show")){
                        viewPager.setCurrentItem(4);
                    }else {
                        viewPager.setCurrentItem(2);
                    }

                    add_cover.setBackgroundResource(R.drawable.click_on_tab);
                    employee.setBackgroundResource(R.drawable.click_tab);
                    add_member.setBackgroundResource(R.drawable.click_tab);
                    add_nominee.setBackgroundResource(R.drawable.click_tab);
                    nominee_enroll.setBackgroundResource(R.drawable.click_tab);
                    member_enroll.setBackgroundResource(R.drawable.click_tab);
                    show_all.setBackgroundResource(R.drawable.click_tab);


                }else {
                    add_cover.setBackgroundResource(R.drawable.click_on_tab);
                    employee.setBackgroundResource(R.drawable.click_tab);
                    add_member.setBackgroundResource(R.drawable.click_tab);
                    add_nominee.setBackgroundResource(R.drawable.click_tab);
                    nominee_enroll.setBackgroundResource(R.drawable.click_tab);
                    member_enroll.setBackgroundResource(R.drawable.click_tab);
                    show_all.setBackgroundResource(R.drawable.click_tab);

                    if(allow.equalsIgnoreCase("1")){

                        viewPager.setCurrentItem(3);
                    }else {
                        int[] loc = new int[2];
                        member_enroll.getLocationInWindow(loc);
                        horizontalScrollView.scrollTo(loc[0], 0);
                        viewPager.setCurrentItem(2);
                    }



                }


            }
        });

        show_all.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                try{
                    if(nominne.equalsIgnoreCase("show")){
                        viewPager.setCurrentItem(5);
                    }else {
                        viewPager.setCurrentItem(3);
                    }

                    show_all.setBackgroundResource(R.drawable.click_on_tab);
                    employee.setBackgroundResource(R.drawable.click_tab);
                    add_member.setBackgroundResource(R.drawable.click_tab);
                    add_nominee.setBackgroundResource(R.drawable.click_tab);
                    nominee_enroll.setBackgroundResource(R.drawable.click_tab);
                    add_cover.setBackgroundResource(R.drawable.click_tab);
                    member_enroll.setBackgroundResource(R.drawable.click_tab);


                }catch (Exception e){
                    Log.d("myisue",e.toString());
                }



            }
        });



        return v;


    }




    void NomineeConfig(){
        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();

        String url = con.base_url+"/api/broker/get/nominee-config?configurable_id="+policy_id+"&"+"configurable_type=policy";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {



                            Log.d("response_dependent",response);
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array =jsonObject.getJSONArray("data");


                            JSONObject object = array.getJSONObject(0);
                            String allowed_relations_type =object.getString("allowed_relations_type");

                            if(allowed_relations_type.equalsIgnoreCase("null")){
                                nominne="hide";
                            }else {
                                nominne="show";
                            }



                            if(shown.equalsIgnoreCase("all")){

                                if(nominne.equalsIgnoreCase("show")){

                                    InrollmentAdapterTabAdapternm adapter = new InrollmentAdapterTabAdapternm(getFragmentManager(), 6);
                                    viewPager.setAdapter(adapter);

                                }else {
                                    InrollmentAdapterTabAdapternomwithoutnm adapter = new InrollmentAdapterTabAdapternomwithoutnm(getFragmentManager(), 4);
                                    viewPager.setAdapter(adapter);
                                }






                            }else {


                                frag();

                            }

                            //Adding adapter to pager





                            if(shown.equalsIgnoreCase("all")){


                                if(nominne.equalsIgnoreCase("show")){

                                    employee.setVisibility(View.VISIBLE);
                                    add_member.setVisibility(View.GONE);
                                    add_nominee.setVisibility(View.VISIBLE);
                                    nominee_enroll.setVisibility(View.VISIBLE);
                                    add_cover.setVisibility(View.VISIBLE);
                                    show_all.setVisibility(View.VISIBLE);
                                    member_enroll.setVisibility(View.VISIBLE);

                                    employee.setBackgroundResource(R.drawable.click_on_tab);
                                    add_member.setBackgroundResource(R.drawable.click_tab);
                                    add_nominee.setBackgroundResource(R.drawable.click_tab);
                                    nominee_enroll.setBackgroundResource(R.drawable.click_tab);
                                    add_cover.setBackgroundResource(R.drawable.click_tab);
                                    show_all.setBackgroundResource(R.drawable.click_tab);
                                    member_enroll.setBackgroundResource(R.drawable.click_tab);

                                }else {


                                    employee.setVisibility(View.VISIBLE);
                                    add_member.setVisibility(View.GONE);
                                    add_nominee.setVisibility(View.GONE);
                                    nominee_enroll.setVisibility(View.GONE);
                                    add_cover.setVisibility(View.VISIBLE);
                                    show_all.setVisibility(View.VISIBLE);
                                    member_enroll.setVisibility(View.VISIBLE);

                                    employee.setBackgroundResource(R.drawable.click_on_tab);
                                    add_member.setBackgroundResource(R.drawable.click_tab);
                                    add_nominee.setBackgroundResource(R.drawable.click_tab);
                                    nominee_enroll.setBackgroundResource(R.drawable.click_tab);
                                    add_cover.setBackgroundResource(R.drawable.click_tab);
                                    show_all.setBackgroundResource(R.drawable.click_tab);
                                    member_enroll.setBackgroundResource(R.drawable.click_tab);
                                }


                            }else {

                               frag();

                            }


                        } catch (Exception e) {

                            nominne="show";
                            if(shown.equalsIgnoreCase("all")){

                                if(nominne.equalsIgnoreCase("show")){

                                    InrollmentAdapterTabAdapternm adapter = new InrollmentAdapterTabAdapternm(getFragmentManager(), 7);
                                    viewPager.setAdapter(adapter);

                                }else {
                                    InrollmentAdapterTabAdapternomwithoutnm adapter = new InrollmentAdapterTabAdapternomwithoutnm(getFragmentManager(), 5);
                                    viewPager.setAdapter(adapter);
                                }






                            }else {
                                frag();
                            }

                            //Adding adapter to pager





                            if(shown.equalsIgnoreCase("all")){


                                if(nominne.equalsIgnoreCase("show")){

                                    employee.setVisibility(View.VISIBLE);
                                    add_member.setVisibility(View.GONE);
                                    add_nominee.setVisibility(View.VISIBLE);
                                    nominee_enroll.setVisibility(View.VISIBLE);
                                    add_cover.setVisibility(View.VISIBLE);
                                    show_all.setVisibility(View.VISIBLE);
                                    member_enroll.setVisibility(View.VISIBLE);

                                    employee.setBackgroundResource(R.drawable.click_on_tab);
                                    add_member.setBackgroundResource(R.drawable.click_tab);
                                    add_nominee.setBackgroundResource(R.drawable.click_tab);
                                    nominee_enroll.setBackgroundResource(R.drawable.click_tab);
                                    add_cover.setBackgroundResource(R.drawable.click_tab);
                                    show_all.setBackgroundResource(R.drawable.click_tab);
                                    member_enroll.setBackgroundResource(R.drawable.click_tab);

                                }else {


                                    employee.setVisibility(View.VISIBLE);
                                    add_member.setVisibility(View.GONE);
                                    add_nominee.setVisibility(View.GONE);
                                    nominee_enroll.setVisibility(View.GONE);
                                    add_cover.setVisibility(View.VISIBLE);
                                    show_all.setVisibility(View.VISIBLE);
                                    member_enroll.setVisibility(View.VISIBLE);

                                    employee.setBackgroundResource(R.drawable.click_on_tab);
                                    add_member.setBackgroundResource(R.drawable.click_tab);
                                    add_nominee.setBackgroundResource(R.drawable.click_tab);
                                    nominee_enroll.setBackgroundResource(R.drawable.click_tab);
                                    add_cover.setBackgroundResource(R.drawable.click_tab);
                                    show_all.setBackgroundResource(R.drawable.click_tab);
                                    member_enroll.setBackgroundResource(R.drawable.click_tab);
                                }


                            }else {

                              frag();

                            }


                        }

                    }
                },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                nominne="show";
                Log.e("onErrorResponse", error.toString());

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };





        HashMap<String, String> params = new HashMap<>();

        params.put("configurable_type", "policy");
        params.put("configurable_id", policy_id);
        smr.setParams(params);
        rq.add(smr);

    }


    void frag(){


        String urls = con.base_url+ "/api/employee/policy/rate/details?policy_id="+policy_id;
        RequestQueue rqs = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rqs.getCache().clear();
        StringRequest mStringRequests = new StringRequest(Request.Method.GET, urls, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try{




                    JSONObject resp= new JSONObject(response);



                    String is_midterm_enrollement_allowed =resp.getString("is_midterm_enrollement_allowed");
                    String is_midterm_enrollement_allowed_for_kids =resp.getString("is_midterm_enrollement_allowed_for_kids");
                    String is_midterm_enrollement_allowed_for_spouse =resp.getString("is_midterm_enrollement_allowed_for_spouse");

                    if(is_midterm_enrollement_allowed.equalsIgnoreCase("1")){


                        SharedPreferences prefs = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("allowedter", "1");
                        if(is_midterm_enrollement_allowed_for_kids.equalsIgnoreCase("1")){
                            editor.putString("is_midterm_enrollement_allowed_for_kids", "1");
                        }else {
                            editor.putString("is_midterm_enrollement_allowed_for_kids", "0");
                        }

                        if(is_midterm_enrollement_allowed_for_spouse.equalsIgnoreCase("1")){
                            editor.putString("is_midterm_enrollement_allowed_for_spouse", "1");
                        }else {
                            editor.putString("is_midterm_enrollement_allowed_for_spouse", "0");
                        }

                        editor.putString("allowedter", "1");
                        editor.apply();
                        allow="1";
                        InrollmentAdapterTabAdapterLIstTerm adapter1 = new InrollmentAdapterTabAdapterLIstTerm(getFragmentManager(), 4);
                        viewPager.setAdapter(adapter1);


                        employee.setBackgroundResource(R.drawable.click_tab);
                        add_member.setBackgroundResource(R.drawable.click_on_tab);
                        add_nominee.setBackgroundResource(R.drawable.click_tab);
                        nominee_enroll.setBackgroundResource(R.drawable.click_tab);
                        add_cover.setBackgroundResource(R.drawable.click_tab);
                        show_all.setBackgroundResource(R.drawable.click_tab);
                        member_enroll.setBackgroundResource(R.drawable.click_tab);



                        employee.setVisibility(View.GONE);
                        add_member.setVisibility(View.VISIBLE);
                        add_nominee.setVisibility(View.GONE);
                        nominee_enroll.setVisibility(View.VISIBLE);
                        add_cover.setVisibility(View.VISIBLE);
                        show_all.setVisibility(View.GONE);
                        member_enroll.setVisibility(View.VISIBLE);
                    }else {

                        SharedPreferences prefs = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("allowedter", "0");
                        editor.apply();
                        allow="0";
                        InrollmentAdapterTabAdapterLIst adapter1 = new InrollmentAdapterTabAdapterLIst(getFragmentManager(), 3);
                        viewPager.setAdapter(adapter1);


                        employee.setBackgroundResource(R.drawable.click_tab);
                        add_member.setBackgroundResource(R.drawable.click_tab);
                        add_nominee.setBackgroundResource(R.drawable.click_tab);
                        nominee_enroll.setBackgroundResource(R.drawable.click_tab);
                        add_cover.setBackgroundResource(R.drawable.click_tab);
                        show_all.setBackgroundResource(R.drawable.click_tab);
                        member_enroll.setBackgroundResource(R.drawable.click_on_tab);



                        employee.setVisibility(View.GONE);
                        add_member.setVisibility(View.GONE);
                        add_nominee.setVisibility(View.GONE);
                        nominee_enroll.setVisibility(View.VISIBLE);
                        add_cover.setVisibility(View.VISIBLE);
                        show_all.setVisibility(View.GONE);
                        member_enroll.setVisibility(View.VISIBLE);
                    }




                } catch (Exception e) {

                    Log.e("onErrorResponse", e.toString());
                }

            }
        },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("onErrorResponse", error.toString());

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };




        HashMap<String, String> paramss = new HashMap<>();


        rqs.add(mStringRequests);
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    private void showDialog() {
        Dialog myDialog;

        myDialog = new Dialog(getActivity());

        myDialog.setContentView(R.layout.enrollment_window);


        TextView start_dates= myDialog.findViewById(R.id.start_date);
        TextView end_dates= myDialog.findViewById(R.id.end_date);
        Button send= myDialog.findViewById(R.id.send);


        try{

            String strCurrentDate = start_date;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date newDate = format.parse(strCurrentDate);

            format = new SimpleDateFormat("dd-MM-yyyy");

            start_dates.setText(format.format(newDate));
        }catch (Exception e){
            start_dates.setText(start_date);
        }

        try{

            String strCurrentDate = end_date;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date newDate = format.parse(strCurrentDate);

            format = new SimpleDateFormat("dd-MM-yyyy");

            end_dates.setText(format.format(newDate));
        }catch (Exception e){
            end_dates.setText(end_date);
        }








        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        myDialog.show();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public SSLSocketFactory getSocketFactory() {

        CertificateFactory cf = null;
        try {
            cf = CertificateFactory.getInstance("X.509");
            InputStream caInput = getResources().openRawResource(R.raw.cert);
            Certificate ca;
            try {
                ca = cf.generateCertificate(caInput);
                Log.e("CERT", "ca=" + ((X509Certificate) ca).getSubjectDN());
            } finally {
                caInput.close();
            }


            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);


            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);


            HttpsURLConnection.setDefaultHostnameVerifier(new NullHostNameVerifier());
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, tmf.getTrustManagers(), null);
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
            SSLSocketFactory sf = context.getSocketFactory();


            return sf;

        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        return null;
    }



}