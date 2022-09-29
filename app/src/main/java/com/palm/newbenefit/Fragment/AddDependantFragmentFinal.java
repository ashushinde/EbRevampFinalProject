package com.palm.newbenefit.Fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;


public class AddDependantFragmentFinal extends Fragment {
    Spinner relation_type_spin, sum_insured;
    RadioButton male, female, wallet, payroll, transgender;
    LinearLayout marr_date;
    RadioGroup gendergrp, gender_l;
    TextView saveBtn;
    EditText first_name, last_name, dob, mdob, contactno, emailid;
    Calendar newCalendar;
    ProgressDialog progressDialog = null;
    private DatePickerDialog fromDatePickerDialog;


    public AddDependantFragmentFinal() {
        // Required empty public constructor
    }

    String gender;
    Constants con;
    ArrayList<String> bank_city = null;
    ArrayList<SpinnerModalFamilyData> bank_cityList = null;
    ArrayAdapter<SpinnerModalFamilyData> bank_cityAdapter = null;
    private SimpleDateFormat dateFormatter;


    String preamount;
    ArrayList<String> bank_name = null;
    ArrayList<SpinnerModal> bank_nameList = null;
    ArrayAdapter<SpinnerModal> bank_nameAdapter = null;
    LinearLayout show_all;
    String policy_id, token,allowedter,is_midterm_enrollement_allowed_for_kids,is_midterm_enrollement_allowed_for_spouse;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_dependant_final, container, false);


        con = new Constants();
        gendergrp = (RadioGroup) v.findViewById(R.id.gendergrp);
        dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        newCalendar = Calendar.getInstance();
        newCalendar = Calendar.getInstance();
        relation_type_spin = v.findViewById(R.id.relation_type_spin);
        show_all = v.findViewById(R.id.show_all);

        male = v.findViewById(R.id.male);
        female = v.findViewById(R.id.female);
        wallet = v.findViewById(R.id.wallet);
        payroll = v.findViewById(R.id.payroll);
        transgender = v.findViewById(R.id.transgender);
        marr_date = v.findViewById(R.id.marr_date);
        saveBtn = v.findViewById(R.id.saveBtn);
        gender_l = v.findViewById(R.id.gender_l);


        first_name = v.findViewById(R.id.first_name);
        last_name = v.findViewById(R.id.last_name);
        dob = v.findViewById(R.id.dob);
        mdob = v.findViewById(R.id.mdob);
        contactno = v.findViewById(R.id.contactno);
        emailid = v.findViewById(R.id.emailid);
        sum_insured = v.findViewById(R.id.sum_insured);
        SharedPreferences prefs = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);


        policy_id = prefs.getString("policy_id", null);

        allowedter= prefs.getString("allowedter", null);
        token = prefs.getString("api_token", null);
        is_midterm_enrollement_allowed_for_kids= prefs.getString("is_midterm_enrollement_allowed_for_kids", null);
        is_midterm_enrollement_allowed_for_spouse= prefs.getString("is_midterm_enrollement_allowed_for_spouse", null);
        marr_date.setVisibility(View.GONE);
        relation_type_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerModal bank_name_modal = (SpinnerModal) adapterView.getSelectedItem();


                gendergrp.clearCheck();
                if (bank_name_modal.getSelValue().equalsIgnoreCase("Father") ||
                        bank_name_modal.getSelValue().equalsIgnoreCase("Father-in-law") ||
                        bank_name_modal.getSelValue().equalsIgnoreCase("Son")) {


//                    female.setChecked(true);
                    female.setVisibility(View.GONE);
                    male.setVisibility(View.VISIBLE);
                    male.setChecked(true);
                    male.setSelected(true);
                    gender = "Male";
                    marr_date.setVisibility(View.GONE);
                    transgender.setVisibility(View.GONE);
                } else if (bank_name_modal.getSelValue().equalsIgnoreCase("Mother") ||
                        bank_name_modal.getSelValue().equalsIgnoreCase("Daughter") ||
                        bank_name_modal.getSelValue().equalsIgnoreCase("Mother-in-law")) {

                    male.setVisibility(View.GONE);
                    female.setVisibility(View.VISIBLE);
                    female.setChecked(true);
                    female.setSelected(true);
                    gender = "Female";
                    transgender.setVisibility(View.GONE);
                    marr_date.setVisibility(View.GONE);
                } else if (bank_name_modal.getSelValue().equalsIgnoreCase("Spouse/Partner")) {
                    male.setChecked(false);
                    male.setSelected(false);
                    female.setChecked(false);
                    female.setSelected(false);
                    transgender.setChecked(false);
                    transgender.setSelected(false);
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
        } else {
            gender = "Female";
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


                if (bank_branch_modal.getSelKey().equalsIgnoreCase("Spouse/Partner") ||
                        bank_branch_modal.getSelKey().equalsIgnoreCase("Mother") ||
                        bank_branch_modal.getSelKey().equalsIgnoreCase("Father") ||
                        bank_branch_modal.getSelKey().equalsIgnoreCase("Mother-in-law") ||
                        bank_branch_modal.getSelKey().equalsIgnoreCase("Father-in-law")) {

                    final Calendar mcurrentDate = Calendar.getInstance();
                    int mYear = mcurrentDate.get(Calendar.YEAR);
                    int mMonth = mcurrentDate.get(Calendar.MONTH);
                    int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog mDatePicker = new DatePickerDialog(
                            getContext(), new DatePickerDialog.OnDateSetListener() {
                        public void onDateSet(DatePicker datepicker,
                                              int selectedyear, int selectedmonth,
                                              int selectedday) {
                            selectedyear = selectedyear;
                            mcurrentDate.set(Calendar.YEAR, selectedyear);
                            mcurrentDate.set(Calendar.MONTH, selectedmonth);
                            mcurrentDate.set(Calendar.DAY_OF_MONTH, selectedday);
                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                            dob.setText(sdf.format(mcurrentDate.getTime()));


                        }
                    }, mYear - 18, mMonth, mDay);
                    mcurrentDate.set(mYear - 18, mMonth, mDay);
                    long value = mcurrentDate.getTimeInMillis();
                    mDatePicker.getDatePicker().setMaxDate(value);
                    mDatePicker.show();

                } else {

                    Utils.desablePostDatePicker(getContext(), new MyDateListener() {
                        @Override
                        public void onDateSelect(int selectedYear, int selectedMonth, int selectedDay) {
                            newCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                            newCalendar.set(Calendar.MONTH, selectedMonth);
                            newCalendar.set(Calendar.YEAR, selectedYear);

                            String date = DateTimeFormater.getFormatedDate(newCalendar.getTime());
                            dob.setText(date);
                            //travel_request_todateshow.setText("");
                        }
                    });

                }

            }
        });


        mdob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Utils.desablePostDatePicker(getContext(), new MyDateListener() {
                    @Override
                    public void onDateSelect(int selectedYear, int selectedMonth, int selectedDay) {
                        newCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                        newCalendar.set(Calendar.MONTH, selectedMonth);
                        newCalendar.set(Calendar.YEAR, selectedYear);
                        String date = DateTimeFormater.getFormatedDate(newCalendar.getTime());
                        mdob.setText(date);


                        //travel_request_todateshow.setText("");
                    }
                });


            }
        });


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (isNetworkAvailable()) {

                    SendData();


                } else {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Error?")
                            .setMessage("Please Check Your Internet Connection")
                            .setIcon(android.R.drawable.btn_dialog)
                            .setNegativeButton(android.R.string.ok, null).show();
                }


            }
        });


        gender_l.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (checkedId == R.id.wallet) {

                    preamount = "F";

                } else if (checkedId == R.id.payroll) {
                    preamount = "S";
                }

            }
        });

        if(allowedter.equalsIgnoreCase("1")){
            getBankName();
        }else {
            getBankNameDemo();
        }

        getSumInsured();
        return v;
    }





    private void getBankName() {
        String url = con.base_url + "/api/admin/get/family-construct?policy_id=" + policy_id;



        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js = new JSONObject(response);

                    Log.d("response", response);
                    JSONArray jsonArr = js.getJSONArray("data");
                    bank_name = new ArrayList<>();
                    bank_nameList = new ArrayList<>();
                    bank_nameList.add(new SpinnerModal("", "Select Family Relation"));
                    bank_name.add("");
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObj = jsonArr.getJSONObject(i);


                        if (!jsonObj.getString("name").equalsIgnoreCase("self")) {
                            if(is_midterm_enrollement_allowed_for_spouse.equalsIgnoreCase("1")){
                                if (jsonObj.getString("name").equalsIgnoreCase("Spouse/Partner")) {
                                    bank_nameList.add(new SpinnerModal(jsonObj.getString("id"), jsonObj.getString("name")));
                                    bank_name.add(jsonObj.getString("name"));
                                }
                            }
                            if(is_midterm_enrollement_allowed_for_kids.equalsIgnoreCase("1")){
                                if (jsonObj.getString("name").equalsIgnoreCase("Daughter")||
                                        (jsonObj.getString("name").equalsIgnoreCase("Son"))) {

                                    bank_nameList.add(new SpinnerModal(jsonObj.getString("id"), jsonObj.getString("name")));
                                    bank_name.add(jsonObj.getString("name"));
                                }

                            }

                        }



                    }
                    bank_nameAdapter = new ArrayAdapter<SpinnerModal>(getActivity(), R.layout.spinner_item, bank_nameList);
                    bank_nameAdapter.setDropDownViewResource(R.layout.spinner_item);

                    relation_type_spin.setAdapter(bank_nameAdapter);


                } catch (Exception e) {


                }

            }
        }, new Response.ErrorListener() {
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


    private void getBankNameDemo() {
        String url = con.base_url + "/api/admin/get/family-construct?policy_id=" + policy_id;



      RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js = new JSONObject(response);

                    Log.d("response", response);
                    JSONArray jsonArr = js.getJSONArray("data");
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
                    bank_nameAdapter = new ArrayAdapter<SpinnerModal>(getActivity(), R.layout.spinner_item, bank_nameList);
                    bank_nameAdapter.setDropDownViewResource(R.layout.spinner_item);

                    relation_type_spin.setAdapter(bank_nameAdapter);


                } catch (Exception e) {


                }

            }
        }, new Response.ErrorListener() {
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

    private void getSumInsured() {
        String url = con.base_url + "/api/employee/policy/rate/details?policy_id=" + policy_id;

       // RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());


     RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObj = new JSONObject(response);

                    String suminsured_type = jsonObj.getString("main_suminsured_type");

                    int suminsured_type_id = jsonObj.getInt("suminsured_type_id");
                    int main_suminsured_type_id = jsonObj.getInt("main_suminsured_type_id");


                    if ((main_suminsured_type_id == 1) &&
                            ((suminsured_type_id == 1) || (suminsured_type_id == 5))) {
                        show_all.setVisibility(View.VISIBLE);
                    } else {
                        show_all.setVisibility(View.GONE);
                    }


                    Log.d("suminsured_type", response);

                    bank_city = new ArrayList<>();
                    bank_cityList = new ArrayList<>();

                    List<String> suminsured = new ArrayList<String>(Arrays.asList(jsonObj.getString("suminsured").split(",")));

                    List<String> premium = new ArrayList<String>(Arrays.asList(jsonObj.getString("premium").split(",")));


                    for (int i = 0; i < suminsured.size(); i++) {

                        bank_cityList.add(new SpinnerModalFamilyData(suminsured.get(i).toString(), premium.get(i).toString()
                                , jsonObj.getString("suminsured_type"), jsonObj.getString("premium_type"), jsonObj.getString("opd_premium"), jsonObj.getString("has_special_child"), premium.get(i).toString()));
                        bank_city.add(suminsured.get(i).toString());

                    }


                    bank_cityAdapter = new ArrayAdapter<SpinnerModalFamilyData>(getActivity(), R.layout.spinner_item, bank_cityList);
                    bank_cityAdapter.setDropDownViewResource(R.layout.spinner_item);

                    sum_insured.setAdapter(bank_cityAdapter);


                } catch (Exception e) {

                    Log.e("onErrorResponse", e.toString());
                }

            }
        }, new Response.ErrorListener() {
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



        if (patient.getSelKey().equalsIgnoreCase("")) {
            ++count;
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error")
                    .setMessage("Please Select Relation With Employee")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
            return;
        }

        try{
            SpinnerModalFamilyData sum = (SpinnerModalFamilyData) sum_insured.getSelectedItem();
            if (sum.getSelValue().equalsIgnoreCase("")) {
                ++count;
                new AlertDialog.Builder(getActivity())
                        .setTitle("Error")
                        .setMessage("Please Select Suminsured")
                        .setIcon(android.R.drawable.btn_dialog)
                        .setNegativeButton(android.R.string.ok, null).show();
                return;
            }
        }catch (Exception e){

        }


        if (gender.equalsIgnoreCase("")) {
            ++count;
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error")
                    .setMessage("Please Select Gender")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
            return;
        }


        if (first_name.getText().toString().trim().length() < 2) {
            ++count;
            first_name.setError("First Name is Required");
        } else {
            first_name.setError(null);
        }


        if (last_name.getText().toString().trim().length() == 0) {
            last_name.setError(null);
        }else {
            if (last_name.getText().toString().trim().length() < 2) {
                ++count;
                last_name.setError("Last Name is Required");
            } else {
                last_name.setError(null);
            }
        }


        if (contactno.getText().toString().trim().isEmpty() || contactno.getText().toString().length() == 0) {

        } else {
            if (contactno.getText().toString().trim().isEmpty() ||
                    !isValid(contactno.getText().toString().trim()) || contactno.getText().toString().length() < 10 ||
                    contactno.getText().toString().trim().length() > 13) {
                ++count;
                contactno.setError("Invalid Mobile");
            } else {
                contactno.setError(null);
            }
        }


        if (emailid.getText().toString().trim().length() == 0) {

        } else {
            if (Patterns.EMAIL_ADDRESS.matcher(emailid.getText().toString().trim()).matches()) {
                emailid.setError(null);
            } else {
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

    /*  if(marr_date.getVisibility()==View.VISIBLE){
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


            progressDialog = ProgressDialog.show(getActivity(), "",
                    "Saving. Please wait...", true);

            RequestQueue rq = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));

            rq.getCache().clear();



            String url = con.base_url + "/api/employee/enroll/family/members";
            StringRequest smr = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                progressDialog.dismiss();


                                Log.d("response_dependent", response);
                                JSONObject jsonObject = new JSONObject(response);
                                String status = String.valueOf(jsonObject.getString("status"));
                                String message = jsonObject.getString("message");


                                if (status.equalsIgnoreCase("true")) {
                                    new AlertDialog.Builder(getActivity())
                                            .setTitle("Success")
                                            .setMessage(message)
                                            .setIcon(R.drawable.checkmark)
                                            .setNegativeButton(android.R.string.ok, null).show();

                                } else {
                                    new AlertDialog.Builder(getActivity())
                                            .setTitle("Error")
                                            .setMessage(message)
                                            .setIcon(android.R.drawable.btn_dialog)
                                            .setNegativeButton(android.R.string.ok, null).show();
                                }


                                //setBankDet();

                                SpinnerModal bank_branch_modal = (SpinnerModal) relation_type_spin.getSelectedItem();


                                if(allowedter.equalsIgnoreCase("1")){
                                    getBankName();
                                }else {
                                    getBankNameDemo();
                                }

                                getSumInsured();

                                dob.setText("");
                                emailid.setText("");
                                contactno.setText("");
                                first_name.setText("");
                                last_name.setText("");

                                female.setVisibility(View.VISIBLE);
                                male.setVisibility(View.VISIBLE);

                                // hide_data.setVisibility(View.GONE);
                                marr_date.setVisibility(View.GONE);


                               /* } else {

                                    new AlertDialog.Builder(getActivity())
                                            .setTitle("Alert")
                                            .setMessage(status)
                                            .setIcon(android.R.drawable.btn_dialog)
                                            .setNegativeButton(android.R.string.ok, null).show();
                                }*/


                            } catch (Exception e) {

                                e.printStackTrace();
                                progressDialog.dismiss();


                            }

                        }
                    }, new Response.ErrorListener() {
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
            } else if (female.isChecked()) {
                gender = "Female";
            } else if (transgender.isChecked()) {
                gender = "TransGender";
            }





            HashMap<String, String> params = new HashMap<>();
            if (marr_date.getVisibility() == View.VISIBLE) {

                if (mdob.getText().toString().isEmpty()) {

                } else {

                    try {

                        String strCurrentDate = mdob.getText().toString().trim();
                        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                        Date newDate = format.parse(strCurrentDate);

                        format = new SimpleDateFormat("yyyy-MM-dd");

                        params.put("member_marriage_date", format.format(newDate));
                    } catch (Exception e) {
                        if(mdob.getText().toString().trim().equalsIgnoreCase("null")){
                            params.put("member_marriage_date", "");
                        }else {
                            params.put("member_marriage_date", mdob.getText().toString().trim());
                        }


                    }


                }

            }

            if(show_all.getVisibility()==View.VISIBLE){
                params.put("sum_insured",sumin.getSelKey());
            }else {

            }



            params.put("member_relation_id", policyd.getSelKey());
            params.put("member_gender", gender);
            params.put("member_firstname", first_name.getText().toString().trim());

         if (contactno.getText().toString().isEmpty()) {

            } else {
                params.put("member_contact_no", contactno.getText().toString().trim());
            }


            if (emailid.getText().toString().isEmpty()) {

            } else {
                params.put("member_email", emailid.getText().toString().trim());
            }
            if (last_name.getText().toString().isEmpty()) {

            } else {
                params.put("member_lastname", last_name.getText().toString().trim());
            }

            try {

                String strCurrentDate = dob.getText().toString().trim();
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                Date newDate = format.parse(strCurrentDate);

                format = new SimpleDateFormat("yyyy-MM-dd");

                params.put("member_dob", format.format(newDate));
            } catch (Exception e) {
                params.put("member_dob", dob.getText().toString().trim());
            }


            params.put("deductible", preamount);
            params.put("type", "1");
            params.put("policy_id", policy_id);






          /*  "disabled_child": "0",
                    "is_special_child": "0",
                    "member_relation_id": "2",
                    "member_gender": "Female",
                    "member_firstname": "sapna daughter",
                    "member_dob": "1993-02-17",
                    "is_unmarried_child": "0",
                    "is_adopted_child": "0",
                    "policy_id": "1983",
                    "type": "1"
*/
            smr.setParams(params);
            rq.add(smr);


        }


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

        return null;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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