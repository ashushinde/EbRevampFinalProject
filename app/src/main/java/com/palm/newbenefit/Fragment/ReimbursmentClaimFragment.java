package com.palm.newbenefit.Fragment;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
import com.palm.newbenefit.R;
import com.palm.tatarewamp.SslData.NullHostNameVerifier;

import org.json.JSONArray;
import org.json.JSONException;
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
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReimbursmentClaimFragment extends Fragment {
    TextView emp_name;
    String  name;
    Spinner policy_type_spin,policy_type_spin_no;
    int day = 0;
    int month = 0;
    int year = 0;
    String _year;
    String _month, _day;
    Spinner patient_name_spin;
    TextView clear_user_btn,add_user_btn;
    EditText email_id,contact,discharge_date,dte_of_adminsiion,doc_name,hos_add,hos_name,admitted_for,claim_amount_reimurse,remark_reimburse;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog ToDatePickerDialog;
    ProgressDialog progressDialog = null;
    private SimpleDateFormat dateFormatter;
    Constants con;
    String user_id;
    ArrayList<SpinnerModal> bank_nameList = null;
    ArrayAdapter<SpinnerModal> bank_nameAdapter = null;
    ArrayList<String> bank_name = null;
    String bank_name_value = "";
    ArrayList<String> bank_city = null;
    ArrayList<SpinnerModalFamilyData> bank_cityList = null;
    ArrayAdapter<SpinnerModalFamilyData> bank_cityAdapter = null;
    String bank_city_value = "";
    String over="NO";

    String token;
    String employee_id=null;
    Calendar newCalendar = Calendar.getInstance();
    public ReimbursmentClaimFragment() {
        // Required empty public constructor
    }

String emp_id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_reimbursment_claim, container, false);
        con=new Constants();
        SharedPreferences prefs = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);
        // newCalendar = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        newCalendar = Calendar.getInstance();
        token = prefs.getString("api_token", null);
        user_id = prefs.getString("user_id", null);

        name= prefs.getString("full_name", null);
        emp_name=v.findViewById(R.id.emp_name);
        hos_add=v.findViewById(R.id.hos_add);
     EmployeeDetail();
         GetEmployeeId();
         setProfileDet();
        policy_type_spin=v.findViewById(R.id.policy_type_spin);
        policy_type_spin_no=v.findViewById(R.id.policy_type_spin_no);
        patient_name_spin=v.findViewById(R.id.patient_name_spin);
        clear_user_btn=v.findViewById(R.id.clear_user_btn);

        email_id=v.findViewById(R.id.email_id);
        contact=v.findViewById(R.id.contact);
        discharge_date=v.findViewById(R.id.discharge_date);
        dte_of_adminsiion=v.findViewById(R.id.dte_of_adminsiion);
        doc_name=v.findViewById(R.id.doc_name);
        hos_name=v.findViewById(R.id.hos_name);
        admitted_for=v.findViewById(R.id.admitted_for);
        claim_amount_reimurse=v.findViewById(R.id.claim_amount_reimurse);
        remark_reimburse=v.findViewById(R.id.remark_reimburse);
        clear_user_btn=v.findViewById(R.id.clear_user_btn);
        add_user_btn=v.findViewById(R.id.add_user_btn);


        clear_user_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                discharge_date.setText("");
                email_id.setText("");
                dte_of_adminsiion.setText("");
                contact.setText("");
                doc_name.setText("");
                hos_name.setText("");
                claim_amount_reimurse.setText("");
                remark_reimburse.setText("");





                admitted_for.setText("");








                if (isNetworkAvailable()) {
                    getBankName(bank_name_value);
                    SpinnerModal bank_branch_modal = (SpinnerModal) policy_type_spin.getSelectedItem();
                    getBankCity(bank_branch_modal.getSelValue(),"Select Patient Name");

                }else {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Error?")
                            .setMessage("Please Check Your Internet Connection")
                            .setIcon(android.R.drawable.btn_dialog)
                            .setNegativeButton(android.R.string.ok, null).show();
                }






            }

        });

        add_user_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (isNetworkAvailable()) {

                    SendData();

                }else {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Error?")
                            .setMessage("Please Check Your Internet Connection")
                            .setIcon(android.R.drawable.btn_dialog)
                            .setNegativeButton(android.R.string.ok, null).show();
                }







            }

        });


        dte_of_adminsiion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.desablePreDatePicker(getActivity(), new MyDateListener() {
                    @Override
                    public void onDateSelect(int selectedYear, int selectedMonth, int selectedDay) {
                        newCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                        newCalendar.set(Calendar.MONTH, selectedMonth);
                        newCalendar.set(Calendar.YEAR, selectedYear);

                        day = selectedDay;
                        month = selectedMonth;
                        year = selectedYear;

                        _day = String.valueOf(selectedDay);
                        _month = String.valueOf(selectedMonth);
                        _year = String.valueOf(selectedYear);


                        String date = DateTimeFormater.getFormatedDate(newCalendar.getTime());
                        dte_of_adminsiion.setText(date);


                        //travel_request_todateshow.setText("");
                    }
                });

            }
        });

        discharge_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.maxdate(getActivity(), day, month, year, new MyDateListener() {
                    @Override
                    public void onDateSelect(int selectedYear, int selectedMonth, int selectedDay) {
                        newCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                        newCalendar.set(Calendar.MONTH, selectedMonth);
                        newCalendar.set(Calendar.YEAR, selectedYear);

                        String date = DateTimeFormater.getFormatedDate(newCalendar.getTime());
                        discharge_date.setText(date);

                        //travel_request_todateshow.setText("");
                    }
                });

            }
        });












        policy_type_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerModal bank_name_modal = (SpinnerModal) adapterView.getSelectedItem();
                if (!bank_name_modal.selValue.equals("")){










                    if (isNetworkAvailable()) {
                        getBankNamenumber(bank_name_modal.getSelKey());


                    }else {
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Error?")
                                .setMessage("Please Check Your Internet Connection")
                                .setIcon(android.R.drawable.btn_dialog)
                                .setNegativeButton(android.R.string.ok, null).show();
                    }





                }






            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        policy_type_spin_no.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerModal bank_name_modal = (SpinnerModal) adapterView.getSelectedItem();
                if (!bank_name_modal.selValue.equals("")){










                    if (isNetworkAvailable()) {
                        getBankCity(bank_name_modal.getSelKey(), bank_city_value);

                    }else {
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Error?")
                                .setMessage("Please Check Your Internet Connection")
                                .setIcon(android.R.drawable.btn_dialog)
                                .setNegativeButton(android.R.string.ok, null).show();
                    }





                }






            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        patient_name_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerModalFamilyData bank_city_modal = (SpinnerModalFamilyData) adapterView.getSelectedItem();
                if (!bank_city_modal.selValue.equals("")) {



                    if((( bank_city_modal.getBank_id().equalsIgnoreCase(""))
                            ||(bank_city_modal.getBank_id().equalsIgnoreCase("null")
                            ||(bank_city_modal.getBank_id().equalsIgnoreCase("false")
                            ||(bank_city_modal.getBank_id().equalsIgnoreCase("0")))))){


                        email_id.setText(null);
                    }else {
                        email_id.setText(bank_city_modal.getBank_id());
                    }

                    if((( bank_city_modal.getFamily_dob().equalsIgnoreCase(""))
                            ||(bank_city_modal.getFamily_dob().equalsIgnoreCase("null")
                            ||(bank_city_modal.getFamily_dob().equalsIgnoreCase("false")
                            ||(bank_city_modal.getFamily_dob().equalsIgnoreCase("0")))))){


                        contact.setText(null);
                    }else {
                        contact.setText(bank_city_modal.getFamily_dob());
                    }








                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        return  v;
    }
    private void getBankName(final String set_bank_name) {
        //String url =con.base_url+"/api/employee/policies";
        String url =con.base_url+"/api/admin/get/policy/subtype";

        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {


                            JSONObject data=new JSONObject(response);
                            JSONArray jsonArr = data.getJSONArray("data");





                            bank_name = new ArrayList<>();
                            bank_nameList = new ArrayList<>();

                            for (int i = 0; i < jsonArr.length(); i++) {
                                JSONObject jsonObj = jsonArr.getJSONObject(i);

                                if(jsonObj.getString("policy_sub_type_name").equalsIgnoreCase("Group Mediclaim")){
                                    bank_nameList.add(new SpinnerModal(jsonObj.getString("policy_sub_type_id"),
                                            jsonObj.getString("policy_sub_type_name")));
                                    bank_name.add(jsonObj.getString("policy_sub_type_name"));
                                }
                            }





                            bank_nameAdapter = new ArrayAdapter<SpinnerModal>(getActivity(), R.layout.spinner_item, bank_nameList);
                            bank_nameAdapter.setDropDownViewResource(R.layout.spinner_item);


                            policy_type_spin.setAdapter(bank_nameAdapter);
                            // policy_type_spin_no.setAdapter(bank_nameAdapter);



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



        HashMap<String, String> params = new HashMap<>();
        params.put("employer_id", employee_id);

        smr.setParams(params);
        rq.add(smr);
    }

    void GetEmployeeId(){
    String url = con.base_url+"/api/admin/user";

        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);

                    Log.d("mydata",response);
                    JSONArray jsonObj=js.getJSONArray("data");

                    for (int i = 0; i < jsonObj.length(); i++) {
                        JSONObject explrObject = jsonObj.getJSONObject(0);





                        String employer_id = explrObject.getString("employer_id");





                        if (employer_id != "null" && !employer_id.isEmpty()) {

                            employee_id=employer_id;
                            getBankName(bank_name_value);

                        }





                    }



                } catch (JSONException e) {
                    e.printStackTrace();
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
        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", user_id);
        params.put("master_user_type_id", "5");


        mStringRequest.setParams(params);
        rq.add(mStringRequest);

    }

    private void getBankNamenumber(final String set_bank_name) {
        String url =con.base_url+"/api/admin/get/policyno";



        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {



                            JSONObject data=new JSONObject(response);
                            JSONArray jsonArr = data.getJSONArray("data");





                            bank_name = new ArrayList<>();
                            bank_nameList = new ArrayList<>();

                            for (int i = 0; i < jsonArr.length(); i++) {
                                JSONObject jsonObj = jsonArr.getJSONObject(i);


                                bank_nameList.add(new SpinnerModal(jsonObj.getString("id"),
                                        jsonObj.getString("policy_no")));
                                bank_name.add(jsonObj.getString("policy_no"));

                            }





                            bank_nameAdapter = new ArrayAdapter<SpinnerModal>(getActivity(), R.layout.spinner_item, bank_nameList);
                            bank_nameAdapter.setDropDownViewResource(R.layout.spinner_item);



                            policy_type_spin_no.setAdapter(bank_nameAdapter);



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



        HashMap<String, String> params = new HashMap<>();
        params.put("employer_id", employee_id);
        params.put("policy_sub_type_id", set_bank_name);
        params.put("user_type_name", "Employee");

        smr.setParams(params);
        rq.add(smr);
    }



    public static boolean isValid(String s)
    {
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

    public void SendData(){

        int count = 0;


        SpinnerModal policy = (SpinnerModal) policy_type_spin.getSelectedItem();
        SpinnerModalFamilyData patient = (SpinnerModalFamilyData) patient_name_spin.getSelectedItem();


        if (policy.getSelValue().trim().length() == 0) {
            ++count;
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error")
                    .setMessage("Please Select Policy Type")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
            return;
        }

        if (patient.getSelValue().trim().length() == 0) {
            ++count;
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error")
                    .setMessage("Please Select Patient Name")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
            return;
        }


        if (patient.getSelValue().equalsIgnoreCase("0")) {
            ++count;
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error")
                    .setMessage("Please Select Patient Name")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
            return;
        }


       /* if(discharge_date.getText().toString().trim().length() == 0) {
            ++count;
            discharge_date.setError("Select Discharge Date");
        }else {
            discharge_date.setError(null);
        }*/

        if(contact.getText().toString().trim().isEmpty()||contact.getText().toString().length()<10 ) {
            ++count;
            contact.setError("Invalid Mobile");
        }else {
            contact.setError(null);
        }

        if(dte_of_adminsiion.getText().toString().trim().length() == 0) {
            ++count;
            dte_of_adminsiion.setError("Select Date Of Admission");
        }else {
            dte_of_adminsiion.setError(null);
        }

        if(hos_name.getText().toString().trim().length() == 0) {
            ++count;
            hos_name.setError("Enter Hospital Name");
        }else {
            hos_name.setError(null);
        }

        if(email_id.getText().toString().trim().length() == 0) {
            ++count;
            email_id.setError("Enter Email Id");
        }else {
            email_id.setError(null);
        }

        if(doc_name.getText().toString().trim().length() == 0) {
            ++count;
            doc_name.setError("Doctor Name is Required");
        }else {
            doc_name.setError(null);
        }

        if(!con.isValidEmail(email_id.getText().toString().toLowerCase().trim())) {
            email_id.setError("Invalid Email");
            ++count;
        }else {
            email_id.setError(null);
        }

       /* if(claim_amount_reimurse.getText().toString().trim().length() == 0) {
            ++count;
            claim_amount_reimurse.setError("Enter Claim Amount");
        }else {
            claim_amount_reimurse.setError(null);
        }*/



        if (claim_amount_reimurse.getText().toString().trim().length() == 0) {
            ++count;
            claim_amount_reimurse.setError("Enter Claim Amount");
        } else {
            if(claim_amount_reimurse.getText().toString().trim().startsWith("0"))
            {
                ++count;
                claim_amount_reimurse.setError("Enter Valid Claim Amount");
            }
            else {
                claim_amount_reimurse.setError(null);
            }
        }







        if(admitted_for.getText().toString().trim().length() == 0) {
            ++count;
            admitted_for.setError("Admitted For?");
        }else {
            admitted_for.setError(null);
        }


        if(hos_add.getText().toString().trim().length() == 0) {
            ++count;
            hos_add.setError("Enter Hospital Address");
        }else {
            hos_add.setError(null);
        }




        if(count == 0) {

            progressDialog = ProgressDialog.show(getActivity(), "",
                    "Saving. Please wait...", true);


            RequestQueue rq = Volley.newRequestQueue(getActivity(),
                    new HurlStack(null, getSocketFactory()));

            rq.getCache().clear();
            String url = con.base_url+"/api/admin/create/claim/intimate";
            StringRequest smr = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                progressDialog.dismiss();


                                Log.e("cc",response);


                                JSONObject jsonObject = new JSONObject(response);

                                String errorCode = jsonObject.getString("message");
                                String errorCoded = String.valueOf(jsonObject.getBoolean("status"));

                                if(errorCoded.equalsIgnoreCase("false")){

                                    new AlertDialog.Builder(getActivity())
                                            .setTitle("Error")
                                            .setMessage(errorCode)
                                            .setIcon(android.R.drawable.btn_dialog)
                                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                                public void onClick(DialogInterface dialog, int whichButton) {

                                                }
                                            }) .show();
                                }else {

                                    new AlertDialog.Builder(getActivity())
                                            .setTitle("Success")
                                            .setMessage(errorCode)
                                            .setIcon(R.drawable.checkmark)
                                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                                public void onClick(DialogInterface dialog, int whichButton) {
                                                    DashboardBenifitFragment travel = new DashboardBenifitFragment();
                                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout_for_fragement, travel, travel.getTag()).addToBackStack("back").commit();

                                                }
                                            }) .show();

                                }



                            } catch (Exception e) {
                                progressDialog.dismiss();
                                new AlertDialog.Builder(getActivity())
                                        .setTitle("Error")
                                        .setMessage(" Unable To Register Claim At Tpa End ")
                                        .setIcon(android.R.drawable.btn_dialog)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                            }
                                        }) .show();



                            }

                        }
                    },new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("onErrorResponse", error.toString());
                    progressDialog.dismiss();
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Error")
                            .setMessage(" Unable To Register Claim At Tpa End ")
                            .setIcon(android.R.drawable.btn_dialog)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {

                                }
                            }) .show();

                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Bearer " + token);
                    return headers;
                }
            };
            SpinnerModal marSel = (SpinnerModal) policy_type_spin.getSelectedItem();
            SpinnerModalFamilyData patientk = (SpinnerModalFamilyData) patient_name_spin.getSelectedItem();



            HashMap<String, String> params = new HashMap<>();
            params.put("member_id", patientk.getSelValue());
            params.put("reason", admitted_for.getText().toString());
            params.put("claim_amt", claim_amount_reimurse.getText().toString());
            params.put("planned_date", dte_of_adminsiion.getText().toString());
            params.put("hospital_name", hos_name.getText().toString());
            params.put("doctor_name", doc_name.getText().toString());
            params.put("mobile_no", contact.getText().toString());
            params.put("email", email_id.getText().toString());
            params.put("discharge_date", discharge_date.getText().toString());
            params.put("policy_id", marSel.getSelKey());
            params.put("claim_type", "reimbursement");
            params.put("remark", remark_reimburse.getText().toString());
            params.put("tpa_member_id", patientk.getSelValue());
            params.put("tpa_member_name", doc_name.getText().toString());
            params.put("status", "1");

            params.put("tpa_emp_id", "1");
            smr.setParams(params);
            rq.add(smr);


        }



    }



    public void getBankCity(final String bank_name, final String set_bank_city) {

        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        String url = con.base_url+"/api/admin/get/emp_member";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {

                         JSONObject objectd=new JSONObject(response);

                            JSONArray jsonArr = objectd.getJSONArray("data");
                            bank_city = new ArrayList<>();
                            bank_cityList = new ArrayList<>();
                            bank_cityList.add(new SpinnerModalFamilyData("Select Patient Name", "0","",""
                            ,"","",""));
                            bank_city.add("0");
                            for (int i = 0; i < jsonArr.length(); i++) {
                                JSONObject jsonObj = jsonArr.getJSONObject(i);



                                bank_cityList.add(new SpinnerModalFamilyData(jsonObj.getString("name"),
                                        String.valueOf(jsonObj.getInt("member_id")),
                                        String.valueOf(jsonObj.getLong("mobile")),
                                        jsonObj.getString("email"),
                                        "",
                                        "",
                                        ""));
                                bank_city.add(jsonObj.getString("name"));
                            }
                            bank_cityAdapter = new ArrayAdapter<SpinnerModalFamilyData>(getActivity(), R.layout.spinner_item, bank_cityList);
                            bank_cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            patient_name_spin.setAdapter(bank_cityAdapter);


                        } catch (Exception e) {
                            Log.e("onErrorResponse", e.toString());
                        }

                    }
                },new Response.ErrorListener() {
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




        SpinnerModal patientk = (SpinnerModal) policy_type_spin_no.getSelectedItem();




        HashMap<String, String> params = new HashMap<>();
        params.put("policy_id", patientk.getSelKey());
        params.put("employee_id", emp_id);
        smr.setParams(params);
        rq.add(smr);
    }
    public void setProfileDet() {

    String url = con.base_url+"/api/admin/user";
        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);

                    Log.d("response",response);
                    JSONArray  jsonObj=js.getJSONArray("data");

                    for (int i = 0; i < jsonObj.length(); i++) {
                        JSONObject explrObject = jsonObj.getJSONObject(i);








                        emp_id = explrObject.getString("employee_id");

                    }

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
        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", user_id);
        params.put("master_user_type_id", "5");


        mStringRequest.setParams(params);
        rq.add(mStringRequest);

    }


    public  void EmployeeDetail(){
    String url = con.base_url+"/api/admin/user";

        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);

                    Log.d("response",response);
                    JSONArray  jsonObj=js.getJSONArray("data");

                    for (int i = 0; i < jsonObj.length(); i++) {
                        JSONObject explrObject = jsonObj.getJSONObject(i);





                        String first_name_input = explrObject.getString("name");







                        if (first_name_input != "null" && !first_name_input.isEmpty()) {
                            emp_name.setText(first_name_input);
                        }







                    }



                } catch (JSONException e) {
                    e.printStackTrace();
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
        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", user_id);
        params.put("master_user_type_id", "5");


        mStringRequest.setParams(params);
        rq.add(mStringRequest);
    }




    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
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


}

