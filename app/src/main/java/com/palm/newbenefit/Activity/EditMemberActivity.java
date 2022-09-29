package com.palm.newbenefit.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;

import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.palm.newbenefit.ApiConfig.AnalyticsApplication;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.ApiConfig.DateTimeFormater;
import com.palm.newbenefit.ApiConfig.MyDateListener;
import com.palm.newbenefit.ApiConfig.Utils;
import com.palm.newbenefit.Module.SpinnerModal;
import com.palm.newbenefit.Module.SpinnerModalFamilyData;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class EditMemberActivity extends AppCompatActivity {
    Spinner relation_type_spin,sum_insured;
    RadioButton male, female, wallet, payroll,transgender;
    LinearLayout marr_date;
    RadioGroup gendergrp ,gender_l;
    TextView saveBtn;
    String user_id;
    EditText first_name, last_name, dob,mdob, contactno, emailid;
    Calendar newCalendar;
    ProgressDialog progressDialog = null;
    private DatePickerDialog fromDatePickerDialog;
    ImageView info_text;

    String gender;
    Constants con;
    ArrayList<String> bank_city = null;
    ArrayList<SpinnerModalFamilyData> bank_cityList = null;
    ArrayAdapter<SpinnerModalFamilyData> bank_cityAdapter = null;
    private SimpleDateFormat dateFormatter;

String marraige_dat="null";
    String dateofb="null";


    String preamount;
    ArrayList<String> bank_name = null;
    ArrayList<SpinnerModal> bank_nameList = null;
    ArrayAdapter<SpinnerModal> bank_nameAdapter = null;
    LinearLayout show_all;
    String policy_id,token;
    String member_idf,first_namef,last_namef,agef,agetypef,fam_id,family_idf,dobf,relationf,mdate,
            disabled_child,is_special_child,premium,is_unmarried_child,is_adopted_child,family_member_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_member);
        con=new Constants();
        con=new Constants();
      //  AnalyticsApplication.getInstance().trackEvent("Edit Member Page", "Edit Member Page Displaying", "Edit Member Page Displaying");

        gendergrp = (RadioGroup) findViewById(R.id.gendergrp);
        dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        newCalendar = Calendar.getInstance();
        newCalendar = Calendar.getInstance();
        relation_type_spin = findViewById(R.id.relation_type_spin);
        show_all=findViewById(R.id.show_all);

        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        wallet = findViewById(R.id.wallet);
        payroll = findViewById(R.id.payroll);
        transgender =findViewById(R.id.transgender);
        marr_date= findViewById(R.id.marr_date);
        saveBtn= findViewById(R.id.saveBtn);
        gender_l= findViewById(R.id.gender_l);


        first_name =findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        dob= findViewById(R.id.dob);
        mdob= findViewById(R.id.mdob);
        contactno= findViewById(R.id.contactno);
        emailid=findViewById(R.id.emailid);
        sum_insured= findViewById(R.id.sum_insured);
        SharedPreferences prefs = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);


        policy_id = prefs.getString("policy_id", null);


        token= prefs.getString("api_token", null);
        marr_date.setVisibility(View.GONE);


        Intent intent = getIntent();
        first_namef = intent.getStringExtra("first_namef");
        first_name.setText(first_namef);



        last_namef = intent.getStringExtra("last_namef");
        last_name.setText(last_namef);

        agef = intent.getStringExtra("agef");

        if(agef.equalsIgnoreCase("null")){
            emailid.setText(null);
        }else {
            emailid.setText(agef);
        }


        agetypef = intent.getStringExtra("agetypef");

        if(agetypef.equalsIgnoreCase("null")){
            contactno.setText(null);
        }else {
            contactno.setText(agetypef);
        }


        dobf = intent.getStringExtra("dobf");


        try {

            String strCurrentDate = dobf;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date newDate = format.parse(strCurrentDate);

            format = new SimpleDateFormat("dd-MM-yyyy");

            dobf= format.format(newDate);
            dob.setText(dobf);
            dateofb=dobf;
        } catch (Exception e) {
            dobf = intent.getStringExtra("dobf");
            dob.setText(dobf);
            dateofb=dobf;

        }






        mdate = intent.getStringExtra("mdob");
        marraige_dat=mdate;

        if(mdate.equalsIgnoreCase("0000-00-00")||mdate.equalsIgnoreCase("null")){
            mdob.setText(null);
        }else {
            try {

                String strCurrentDate = mdate;
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date newDate = format.parse(strCurrentDate);

                format = new SimpleDateFormat("dd-MM-yyyy");

                mdate= format.format(newDate);
                mdob.setText(mdate);
            } catch (Exception e) {
                mdob.setText(mdate);

            }


        }



        relationf = intent.getStringExtra("relationf");

        Log.d("relationname",relationf);


        family_idf = intent.getStringExtra("family_idf");


        gender = intent.getStringExtra("genderf");

        disabled_child = intent.getStringExtra("disabled_child");
        is_special_child = intent.getStringExtra("is_special_child");
        premium = intent.getStringExtra("premium");
        is_unmarried_child = intent.getStringExtra("is_unmarried_child");
        is_adopted_child = intent.getStringExtra("is_adopted_child");
        family_member_id = intent.getStringExtra("family_member_id");








        if (relationf.equalsIgnoreCase("Father") ||
                relationf.equalsIgnoreCase("Father-in-law") ||
                relationf.equalsIgnoreCase("Son")) {


//                    female.setChecked(true);
            female.setVisibility(View.GONE);
            male.setVisibility(View.VISIBLE);
            male.setChecked(true);
            male.setSelected(true);
            gender="Male";
            marr_date.setVisibility(View.GONE);
            transgender.setVisibility(View.GONE);


            gendergrp.check(male.getId());
        }

        else if (relationf.equalsIgnoreCase("Mother") ||
                relationf.equalsIgnoreCase("Daughter") ||relationf.equalsIgnoreCase("Mother-in-law")){

            male.setVisibility(View.GONE);
            female.setVisibility(View.VISIBLE);
            female.setChecked(true);
            female.setSelected(true);
            gender="Female";
            transgender.setVisibility(View.GONE);
            marr_date.setVisibility(View.GONE);
            gendergrp.check(female.getId());

        }

        else if (relationf.equalsIgnoreCase("Spouse/Partner")){


            if(gender.equalsIgnoreCase("Male"))

            {
                female.setVisibility(View.GONE);
                male.setVisibility(View.VISIBLE);
                male.setChecked(true);
                male.setSelected(true);
                gender = "Male";
                marr_date.setVisibility(View.GONE);
                transgender.setVisibility(View.GONE);
                gendergrp.check(male.getId());

            } else if(gender.equalsIgnoreCase("Female")){
                male.setVisibility(View.GONE);
                female.setVisibility(View.VISIBLE);
                female.setChecked(true);
                female.setSelected(true);
                gender="Female";
                transgender.setVisibility(View.GONE);
                marr_date.setVisibility(View.GONE);
                gendergrp.check(female.getId());
            }else {
                male.setChecked(false);
                male.setSelected(false);
                female.setChecked(false);
                female.setSelected(false);
                transgender.setChecked(true);
                transgender.setSelected(true);
                gendergrp.check(transgender.getId());


                male.setVisibility(View.VISIBLE);
                female.setVisibility(View.VISIBLE);
                transgender.setVisibility(View.VISIBLE);
                marr_date.setVisibility(View.VISIBLE);




            }
        }



        relation_type_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerModal bank_name_modal = (SpinnerModal) adapterView.getSelectedItem();




                if (bank_name_modal.getSelValue().equalsIgnoreCase("Father") || bank_name_modal.getSelValue().equalsIgnoreCase("Father-in-law") || bank_name_modal.getSelValue().equalsIgnoreCase("Son")) {


//                    female.setChecked(true);
                    female.setVisibility(View.GONE);
                    male.setVisibility(View.VISIBLE);
                    male.setChecked(true);
                    male.setSelected(true);
                    gender="Male";
                    marr_date.setVisibility(View.GONE);
                    transgender.setVisibility(View.GONE);
                    gendergrp.check(male.getId());
                }

                else if (bank_name_modal.getSelValue().equalsIgnoreCase("Mother") || bank_name_modal.getSelValue().equalsIgnoreCase("Daughter") || bank_name_modal.getSelValue().equalsIgnoreCase("Mother-in-law")){

                    male.setVisibility(View.GONE);
                    female.setVisibility(View.VISIBLE);
                    female.setChecked(true);
                    female.setSelected(true);
                    gender="Female";
                    transgender.setVisibility(View.GONE);
                    marr_date.setVisibility(View.GONE);
                    gendergrp.check(female.getId());
                }

                else if (bank_name_modal.getSelValue().equalsIgnoreCase("Spouse/Partner")){


                    male.setVisibility(View.VISIBLE);
                    female.setVisibility(View.VISIBLE);
                    transgender.setVisibility(View.VISIBLE);
                    marr_date.setVisibility(View.VISIBLE);
                }




            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        if (male.isChecked()) {
            gender = "Male";
        } else if (female.isChecked()) {
            gender = "Female";
        }else {
            gender="Transgender";
        }
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Utils.desablePostDatePicker(getActivity(), new MyDateListener() {
//                    @Override
//                    public void onDateSelect(int selectedYear, int selectedMonth, int selectedDay) {
//                        newCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
//                        newCalendar.set(Calendar.MONTH, selectedMonth);
//                        newCalendar.set(Calendar.YEAR, selectedYear);
//                        String date = DateTimeFormater.getFormatedDate(newCalendar.getTime());
//                        dob.setText(date);
//
//
//                        //travel_request_todateshow.setText("");
//                    }
//                });

                SpinnerModal bank_branch_modal = (SpinnerModal) relation_type_spin.getSelectedItem();



                if(bank_branch_modal.getSelKey().equalsIgnoreCase("Spouse/Partner")||bank_branch_modal.getSelKey().equalsIgnoreCase("Mother") ||bank_branch_modal.getSelKey().equalsIgnoreCase("Father")||bank_branch_modal.getSelKey().equalsIgnoreCase("Mother-in-law")|| bank_branch_modal.getSelKey().equalsIgnoreCase("Father-in-law")){

                    final Calendar mcurrentDate = Calendar.getInstance();
                    int mYear = mcurrentDate.get(Calendar.YEAR);
                    int mMonth = mcurrentDate.get(Calendar.MONTH);
                    int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog mDatePicker = new DatePickerDialog(
                            EditMemberActivity.this, new DatePickerDialog.OnDateSetListener() {
                        public void onDateSet(DatePicker datepicker,
                                              int selectedyear, int selectedmonth,
                                              int selectedday) {
                            selectedyear=selectedyear;
                            mcurrentDate.set(Calendar.YEAR, selectedyear);
                            mcurrentDate.set(Calendar.MONTH, selectedmonth);
                            mcurrentDate.set(Calendar.DAY_OF_MONTH, selectedday);
                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                            SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                            dateofb=sdfs.format(mcurrentDate.getTime());
                            dob.setText(sdf.format(mcurrentDate.getTime()));


                        }
                    }, mYear-18, mMonth, mDay);
                    mcurrentDate.set(mYear-18,mMonth,mDay);
                    long value=mcurrentDate.getTimeInMillis();
                    mDatePicker.getDatePicker().setMaxDate(value);
                    mDatePicker.show();

                }else {

                    Utils.desablePostDatePicker(EditMemberActivity.this, new MyDateListener() {
                        @Override
                        public void onDateSelect(int selectedYear, int selectedMonth, int selectedDay) {
                            newCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                            newCalendar.set(Calendar.MONTH, selectedMonth);
                            newCalendar.set(Calendar.YEAR, selectedYear);
                            String date = DateTimeFormater.getFormatedDate(newCalendar.getTime());

                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                            SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                            dateofb=sdfs.format(newCalendar.getTime());
                            dob.setText(sdf.format(newCalendar.getTime()));



                            //travel_request_todateshow.setText("");
                        }
                    });

                }

            }
        });



        mdob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Utils.desablePostDatePicker(EditMemberActivity.this, new MyDateListener() {
                    @Override
                    public void onDateSelect(int selectedYear, int selectedMonth, int selectedDay) {
                        newCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                        newCalendar.set(Calendar.MONTH, selectedMonth);
                        newCalendar.set(Calendar.YEAR, selectedYear);
                        String date = DateTimeFormater.getFormatedDate(newCalendar.getTime());
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                        SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                        marraige_dat=sdfs.format(newCalendar.getTime());
                        mdob.setText(sdf.format(newCalendar.getTime()));




                        //travel_request_todateshow.setText("");
                    }
                });


            }
        });




        info_text=findViewById(R.id.info_text);

        info_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();

            }
        });



        gendergrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if(checkedId == R.id.male) {

                    gender="Male";

                } else if(checkedId == R.id.female) {
                    gender="FeMale";
                }else {
                    gender="Transgender";
                }

            }
        });








        gender_l.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if(checkedId == R.id.wallet) {

                    preamount="F";

                } else if(checkedId == R.id.payroll) {
                    preamount="S";
                }

            }
        });




        getBankName(relationf);
        getSumInsured();



        member_idf = intent.getStringExtra("member_idf");

        Log.d("member_idf",member_idf);





        if (isNetworkAvailable()) {

            getBankName(relationf);
        }else {
            new AlertDialog.Builder(EditMemberActivity.this)
                    .setTitle("Error?")
                    .setMessage("Please Check Your Internet Connection")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
        }






//        gendergrp.clearCheck();
        marr_date.setVisibility(View.GONE);

        if (relationf.equalsIgnoreCase("Self")){

            if(gender.equalsIgnoreCase("Male")){
                female.setVisibility(View.VISIBLE);
                male.setVisibility(View.VISIBLE);
                male.setChecked(true);
                male.setSelected(true);
                female.setChecked(false);
                female.setSelected(false);
            }else {
                female.setVisibility(View.VISIBLE);
                male.setVisibility(View.VISIBLE);
                male.setChecked(false);
                male.setSelected(false);
                female.setChecked(true);
                female.setSelected(true);
            }


            transgender.setVisibility(View.GONE);

        }
       else if (relationf.equalsIgnoreCase("Father") || relationf.equalsIgnoreCase("Father in law") || relationf.equalsIgnoreCase("Son")) {


//                    female.setChecked(true);
            female.setVisibility(View.GONE);
            male.setVisibility(View.VISIBLE);
            male.setChecked(true);
            male.setSelected(true);
            gender="Male";
            transgender.setVisibility(View.GONE);
            marr_date.setVisibility(View.GONE);

            gendergrp.check(male.getId());
        }

        else if (relationf.equalsIgnoreCase("Mother") || relationf.equalsIgnoreCase("Daughter") || relationf.equalsIgnoreCase("Mother-in-law")){

            male.setVisibility(View.GONE);
            female.setVisibility(View.VISIBLE);
            female.setChecked(true);
            female.setSelected(true);
            gender="Female";
            transgender.setVisibility(View.GONE);
            marr_date.setVisibility(View.GONE);
            gendergrp.check(female.getId());
            // transgender.setVisibility(View.VISIBLE);
        }

        else if (relationf.equalsIgnoreCase("Spouse/Partner")){

            male.setVisibility(View.VISIBLE);
            female.setVisibility(View.VISIBLE);
            transgender.setVisibility(View.VISIBLE);


            if (gender.equalsIgnoreCase("Male") ) {
                male.setChecked(true);
                female.setChecked(false);

                male.setSelected(true);
                female.setSelected(false);
                gendergrp.check(male.getId());
                // transgender.setVisibility(View.GONE);
            } else if(gender.equalsIgnoreCase("Female") ){
                male.setChecked(false);
                female.setChecked(true);
                female.setSelected(true);
                male.setSelected(false);
                gendergrp.check(female.getId());
                //transgender.setVisibility(View.GONE);
            }else {
                male.setChecked(false);
                female.setChecked(false);
                transgender.setChecked(true);

                gendergrp.check(transgender.getId());
                male.setSelected(false);
                female.setSelected(false);
                transgender.setSelected(true);
              transgender.setVisibility(View.VISIBLE);
            }


            marr_date.setVisibility(View.VISIBLE);



            if (male.isChecked()) {
                gender = "Male";
            } else if(female.isChecked() ){
                gender = "Female";
            }else if(transgender.isChecked() ){
                gender = "transgender";
            }


        }



























        //newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dob.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));





        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (isNetworkAvailable()) {
                    SendData();
                }else {
                    new AlertDialog.Builder(EditMemberActivity.this)
                            .setTitle("Error?")
                            .setMessage("Please Check Your Internet Connection")
                            .setIcon(android.R.drawable.btn_dialog)
                            .setNegativeButton(android.R.string.ok, null).show();
                }



            }
        });




    }

    private void getSumInsured() {
        String url = con.base_url+"/api/employee/policy/rate/details?policy_id="+policy_id;

        RequestQueue mRequestQueue = Volley.newRequestQueue(EditMemberActivity.this,
                new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObj=new JSONObject(response);

                    String suminsured_type=jsonObj.getString("suminsured_type");


                    if(suminsured_type.equalsIgnoreCase("Flat")){
                        show_all.setVisibility(View.GONE);
                    }else {
                        show_all.setVisibility(View.GONE);
                    }


                    Log.d("suminsured_type",response);

                    bank_city = new ArrayList<>();
                    bank_cityList = new ArrayList<>();

                    bank_cityList.add(new SpinnerModalFamilyData(jsonObj.getString("suminsured"), jsonObj.getString("premium")
                            ,jsonObj.getString("suminsured_type"),jsonObj.getString("premium_type"),jsonObj.getString("opd_premium"),jsonObj.getString("has_special_child"),jsonObj.getString("premium")));
                    bank_city.add(jsonObj.getString("suminsured"));


                    bank_cityAdapter = new ArrayAdapter<SpinnerModalFamilyData>(EditMemberActivity.this, R.layout.spinner_item, bank_cityList);
                    bank_cityAdapter.setDropDownViewResource(R.layout.spinner_item);

                    sum_insured.setAdapter(bank_cityAdapter);


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
        mRequestQueue.add(mStringRequest);
    }


    private void getBankName(final String set_bank_name) {
        String url = con.base_url+"/api/admin/get/family-construct?policy_id="+policy_id;
        RequestQueue mRequestQueue = Volley.newRequestQueue(EditMemberActivity.this,
                new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);

                    Log.d("response",response);
                    JSONArray jsonArr=js.getJSONArray("data");
                    bank_name = new ArrayList<>();
                    bank_nameList = new ArrayList<>();
                    bank_nameList.add(new SpinnerModal("", "Select Family Relation"));
                    bank_name.add("");
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObj = jsonArr.getJSONObject(i);

                        if (!jsonObj.getString("name").equalsIgnoreCase("self")) {
                            bank_nameList.add(new SpinnerModal(jsonObj.getString("id"), jsonObj.getString("name")));
                            bank_name.add(jsonObj.getString("name"));
                        }
                    }
                    bank_nameAdapter = new ArrayAdapter<SpinnerModal>(EditMemberActivity.this, R.layout.spinner_item, bank_nameList);
                    bank_nameAdapter.setDropDownViewResource(R.layout.spinner_item);

                    relation_type_spin.setAdapter(bank_nameAdapter);
                    if (!set_bank_name.equalsIgnoreCase("")){
                        if(set_bank_name.equalsIgnoreCase("Mother-in-Law")){

                            relation_type_spin.setSelection(bank_nameAdapter.getPosition(bank_nameList.
                                    get(bank_name.indexOf("Mother-In-Law"))));
                        }else  if(set_bank_name.equalsIgnoreCase("Father-in-Law")){
                            relation_type_spin.setSelection(bank_nameAdapter.getPosition(bank_nameList.
                                    get(bank_name.indexOf("Father-IN-Law"))));
                        }else {
                            relation_type_spin.setSelection(bank_nameAdapter.getPosition(bank_nameList.
                                    get(bank_name.indexOf(set_bank_name))));
                        }

                    }




                } catch (Exception e) {


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
        mRequestQueue.add(mStringRequest);
    }






    public void SendData() {





        int count = 0;



        SpinnerModal patient = (SpinnerModal) relation_type_spin.getSelectedItem();
        SpinnerModalFamilyData sum = (SpinnerModalFamilyData) sum_insured.getSelectedItem();


        if (patient.getSelValue().equalsIgnoreCase("")) {
            ++count;
            new AlertDialog.Builder(EditMemberActivity.this)
                    .setTitle("Error")
                    .setMessage("Please Select Relation With Employee")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
            return;
        }

        if (sum.getSelValue().equalsIgnoreCase("")) {
            ++count;
            new AlertDialog.Builder(EditMemberActivity.this)
                    .setTitle("Error")
                    .setMessage("Please Select Suminsured")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
            return;
        }

        if (gender.equalsIgnoreCase("")) {
            ++count;
            new AlertDialog.Builder(EditMemberActivity.this)
                    .setTitle("Error")
                    .setMessage("Please Select Gender")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
            return;
        }



        if (first_name.getText().toString().trim().length() == 0) {
            ++count;
            first_name.setError("First Name is Required");
        } else {
            first_name.setError(null);
        }


        /*if (last_name.getText().toString().trim().length() == 0) {
            ++count;
            last_name.setError("Last Name is Required");
        } else {
            last_name.setError(null);
        }*/
        if(contactno.getText().toString().trim().isEmpty()||contactno.getText().toString().length()==0 ) {

        }else {
            if (contactno.getText().toString().trim().isEmpty() ||
                    !isValid(contactno.getText().toString().trim()) || contactno.getText().toString().length() < 10 ||
                    contactno.getText().toString().trim().length() > 13) {
                ++count;
                contactno.setError("Invalid Mobile");
            }else {
                contactno.setError(null);
            }
        }



        if(emailid.getText().toString().trim().length() == 0) {

        }else {

            if(Patterns.EMAIL_ADDRESS.matcher(emailid.getText().toString().trim()).matches()){
                emailid.setError(null);
            }else {
                emailid.setError("Invalid Email");
                ++count;
            }

        }

      /*  if(doc_name.getText().toString().trim().length() == 0) {
            ++count;
            doc_name.setError("Doctor Name is Required");
        }else {
            doc_name.setError(null);
        }*/



        if (dob.getText().toString().trim().length() == 0) {
            ++count;
            dob.setError("DOB is Required");
        } else {
            dob.setError(null);
        }

       /* if(marr_date.getVisibility()==View.VISIBLE){
            if (mdob.getText().toString().trim().length() == 0) {
                ++count;
                mdob.setError("DOB is Required");
            } else {
                mdob.setError(null);
            }
        }*/




       /* if(show.getVisibility()==View.VISIBLE) {


            ((RadioButton) group.getChildAt(0)).setChecked(true);

           *//* if (group.getCheckedRadioButtonId() != -1) {

                new AlertDialog.Builder(getActivity())
                        .setTitle("Error")
                        .setMessage("Please Select Atleast One Sum Insured")
                        .setIcon(android.R.drawable.btn_dialog)
                        .setNegativeButton(android.R.string.ok, null).show();
            }*//*
        }*/






        if (count == 0) {


            progressDialog = ProgressDialog.show(EditMemberActivity.this, "",
                    "Saving. Please wait...", true);
            RequestQueue rq = Volley.newRequestQueue(EditMemberActivity.this,
                    new HurlStack(null, getSocketFactory()));
            rq.getCache().clear();
           // RequestQueue rq = Volley.newRequestQueue(EditMemberActivity.this, new HurlStack(null, getSocketFactory()));

            String url = con.base_url+"/api/employee/enroll/family/members";
            StringRequest smr = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                progressDialog.dismiss();


                                Log.d("response_dependent",response);
                                JSONObject jsonObject = new JSONObject(response);
                                String status =  jsonObject.getString("status");
                                String message = jsonObject.getString("message");







                                if(status.equalsIgnoreCase("true")){
                                    new AlertDialog.Builder(EditMemberActivity.this)
                                            .setTitle("Success")
                                            .setMessage(message)
                                            .setIcon(R.drawable.checkmark)
                                            .setNegativeButton(android.R.string.ok, null).show();

                                }else {
                                    new AlertDialog.Builder(EditMemberActivity.this)
                                            .setTitle("Error")
                                            .setMessage(message)
                                            .setIcon(android.R.drawable.btn_dialog)
                                            .setNegativeButton(android.R.string.ok, null).show();
                                }







                            } catch (Exception e) {
                                new AlertDialog.Builder(EditMemberActivity.this)
                                        .setTitle("Error")
                                        .setMessage("inv")
                                        .setIcon(android.R.drawable.btn_dialog)
                                        .setNegativeButton(android.R.string.ok, null).show();

                                e.printStackTrace();
                                progressDialog.dismiss();


                            }

                        }
                    },  new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("onErrorResponse", error.toString());
                    progressDialog.dismiss();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Bearer " + token);
                    return headers;
                }
            };


            SpinnerModalFamilyData sumin = (SpinnerModalFamilyData) sum_insured.getSelectedItem();

            SpinnerModal policyd = (SpinnerModal) relation_type_spin.getSelectedItem();

            if (male.isChecked()) {
                gender = "Male";
            } else if(female.isChecked()){
                gender = "Female";
            }else  if(transgender.isChecked()){
                gender = "Trangender";
            }





            HashMap<String, String> params = new HashMap<>();


            if(marr_date.getVisibility()==View.VISIBLE){

                if(mdob.getText().toString().isEmpty()){

                }else {

                    try {

                        String strCurrentDate = mdob.getText().toString().trim();
                        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                        Date newDate = format.parse(strCurrentDate);

                        format = new SimpleDateFormat("yyyy-MM-dd");

                        params.put("member_marriage_date", format.format(newDate));
                    } catch (Exception e) {
                        params.put("member_marriage_date", mdob.getText().toString().trim());
                    }
                }

            }



            params.put("member_relation_id", policyd.getSelKey());
            params.put("member_gender", gender);
            params.put("member_firstname", first_name.getText().toString().trim());
            params.put("family_member_id", member_idf);

            if(contactno.getText().toString().isEmpty()){

            }else {
                params.put("member_contact_no", contactno.getText().toString().trim());
            }



            if(emailid.getText().toString().isEmpty()){

            }else {
                params.put("member_email", emailid.getText().toString().trim());
            }
            if(last_name.getText().toString().isEmpty()){

            }else {
                params.put("member_lastname", last_name.getText().toString().trim());
            }

            params.put("member_dob", dateofb);
            params.put("deductible", preamount);
            params.put("type", "2");
            params.put("policy_id", policy_id);





          /*  params.put("disabled_child", disabled_child);
            params.put("is_special_child", is_special_child);
            params.put("sum_insured", sumin.getSelValue());
            params.put("premium", premium);
            params.put("is_unmarried_child", is_unmarried_child);
            params.put("is_adopted_child", is_adopted_child);
            params.put("family_member_id", family_member_id);
*/





            smr.setParams(params);
            rq.add(smr);







        }


    }



    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private SSLSocketFactory getSocketFactory() {

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






           /* HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
            SSLContext context = null;
            context = SSLContext.getInstance("TLS");

            context.init(null, tmf.getTrustManagers(), null);
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
*/
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

        return  null;
    }

    public static boolean isValid(String s) {
        // The given argument to compile() method
        // is regular expression. With the help of
        // regular expression we can validate mobile
        // number.
        // 1) Begins with 0 or 91
        // 2) Then contains 7 or 8 or 9.
        // 3) Then contains 9 digits
        Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}");

        // Pattern class contains matcher() method
        // to find matching between given number
        // and regular expression
        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }

}
