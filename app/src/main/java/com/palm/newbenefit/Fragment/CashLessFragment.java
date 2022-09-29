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
import android.util.Patterns;
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
import com.kmd.newbenefit.R;
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
public class CashLessFragment extends Fragment {
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog ToDatePickerDialog;
    TextView emp_name;
    Spinner policy_type_spin,policy_type_spin_no;
    Spinner bank_citySpin = null;
    Spinner bank_nameSpin = null;
    ArrayList<String> bank_city = null;
    ArrayList<SpinnerModalFamilyData> bank_cityList = null;
    ArrayAdapter<SpinnerModalFamilyData> bank_cityAdapter = null;


    ArrayList<String> bank_cityc = null;
    ArrayList<SpinnerModal> bank_cityListc = null;
    ArrayAdapter<SpinnerModal> bank_cityAdapterc = null;


    ArrayList<SpinnerModal> bank_nameList = null;
    ArrayAdapter<SpinnerModal> bank_nameAdapter = null;
    ArrayList<String> bank_name = null;
    Spinner bank_branchSpin = null;
    ArrayList<String> bank_branch = null;
    ArrayList<SpinnerModal> bank_branchList = null;
    ArrayAdapter<SpinnerModal> bank_branchAdapter = null;

    Spinner patient_name_spin;
    EditText email_id, contact, hos_date, dte_of_adminsiion, doc_name, claim_amount_reimurse, admitted_for, paient_id, reason;
    TextView clear_user_btn, add_user_btn;
    private SimpleDateFormat dateFormatter;
    Constants con;

    String employee_id=null;
    String bank_name_value = "";

    String bank_city_value = "";

    public CashLessFragment() {
        // Required empty public constructor
    }
    String user_id;
    int day = 0;
    int month = 0;
    int year = 0;
    String _year;
    String _month, _day;
    Calendar newCalendar = Calendar.getInstance();

    Spinner hos_name;
    ProgressDialog progressDialog = null;
    String over = "NO";
    String bank_branch_value = "";
    String name = null;

    EditText patient_id;
    String token;
    String emp_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_cash_less, container, false);
        con = new Constants();

        SharedPreferences prefs = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);
        // newCalendar = Calendar.getInstance();

        token = prefs.getString("api_token", null);
        user_id = prefs.getString("user_id", null);

        emp_name = v.findViewById(R.id.emp_name);
        dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        newCalendar = Calendar.getInstance();
        policy_type_spin = v.findViewById(R.id.policy_type_spin);
        patient_name_spin = v.findViewById(R.id.patient_name_spin);
        policy_type_spin_no=v.findViewById(R.id.policy_type_spin_no);
        bank_citySpin = v.findViewById(R.id.bank_citySpin);
        bank_branchSpin = v.findViewById(R.id.bank_branchSpin);
        email_id = v.findViewById(R.id.email_id);
        contact = v.findViewById(R.id.contact);
        hos_date = v.findViewById(R.id.hos_date);
        dte_of_adminsiion = v.findViewById(R.id.dte_of_adminsiion);
        doc_name = v.findViewById(R.id.doc_name);
        hos_name = v.findViewById(R.id.hos_name);
        admitted_for = v.findViewById(R.id.admitted_for);
      /*  paient_id=v.findViewById(R.id.paient_id);
        reason=v.findViewById(R.id.reason);*/
        clear_user_btn = v.findViewById(R.id.clear_user_btn);
        add_user_btn = v.findViewById(R.id.add_user_btn);
        claim_amount_reimurse = v.findViewById(R.id.claim_amount_reimurse);
        patient_id= v.findViewById(R.id.patient_id);
          EmployeeDetail();
        GetEmployeeId();
        setProfileDet();
        newCalendar = Calendar.getInstance();

        clear_user_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contact.setText("");
                hos_date.setText("");
                email_id.setText("");
                dte_of_adminsiion.setText("");

                doc_name.setText("");
                hos_date.setText("");
                patient_id.setText("");
                claim_amount_reimurse.setText("");
                admitted_for.setText("");








                if (isNetworkAvailable()) {



                    getBankName(bank_name_value);
                    getstate(bank_name_value, bank_city_value);

                    getBankbranch(bank_city_value, bank_branch_value);


                    setCertData(bank_city_value);


                    SpinnerModal bank_branch_modal = (SpinnerModal) policy_type_spin.getSelectedItem();
                    getBankCity(bank_branch_modal.getSelValue(), "Select Patient Name");





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

        hos_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.maxdate(getActivity(), day, month, year, new MyDateListener() {
                    @Override
                    public void onDateSelect(int selectedYear, int selectedMonth, int selectedDay) {
                        newCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                        newCalendar.set(Calendar.MONTH, selectedMonth);
                        newCalendar.set(Calendar.YEAR, selectedYear);

                        String date = DateTimeFormater.getFormatedDate(newCalendar.getTime());
                        hos_date.setText(date);

                        //travel_request_todateshow.setText("");
                    }
                });

            }
        });
//
//
//        if (isNetworkAvailable()) {
//
//
//
//            getBankName(bank_name_value);
//
//
//
//
//        }else {
//            new AlertDialog.Builder(getActivity())
//                    .setTitle("Error?")
//                    .setMessage("Please Check Your Internet Connection")
//                    .setIcon(android.R.drawable.btn_dialog)
//                    .setNegativeButton(android.R.string.ok, null).show();
//        }
//


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

        policy_type_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerModal bank_name_modal = (SpinnerModal) adapterView.getSelectedItem();
                if (!bank_name_modal.selValue.equals("")) {




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
                        getstate(bank_name_value, bank_city_value);

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

        bank_citySpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerModal bank_city_modal = (SpinnerModal) adapterView.getSelectedItem();




                if (isNetworkAvailable()) {



                    getBankbranch(bank_city_modal.getSelKey(), bank_branch_value);




                }else {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Error?")
                            .setMessage("Please Check Your Internet Connection")
                            .setIcon(android.R.drawable.btn_dialog)
                            .setNegativeButton(android.R.string.ok, null).show();
                }









            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        bank_branchSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerModal bank_branch_modal = (SpinnerModal) adapterView.getSelectedItem();
                if (!bank_branch_modal.selValue.equals("")) {

                    setCertData(bank_branch_modal.selValue);
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
        return v;
    }
    void GetEmployeeId(){
        String url = con.base_url+"/api/admin/user";

        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();

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
        mRequestQueue.add(mStringRequest);

    }




    public void getBankbranch(final String bank_city, final String set_bank_branch) {
        SpinnerModal bank_name_modal = (SpinnerModal) bank_citySpin.getSelectedItem();
        SpinnerModal bank_name_modals = (SpinnerModal) policy_type_spin_no.getSelectedItem();
        RequestQueue rq = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();

        String url = con.base_url + "/api/admin/get/networkhospital/city";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {


                        JSONObject jsonArra = new JSONObject(response);

                        String status= String.valueOf(jsonArra.getBoolean("status"));

                        if(status.equalsIgnoreCase("false")){
                            bank_branch = new ArrayList<>();
                            bank_branchList = new ArrayList<>();
                            bank_branchList.add(new SpinnerModal("", "Select City"));
                            bank_branch.add("");

                        }else {


                            bank_branch = new ArrayList<>();
                            bank_branchList = new ArrayList<>();
                            bank_branchList.add(new SpinnerModal("", "Select City"));
                            bank_branch.add("");
                            JSONArray array=jsonArra.getJSONArray("data");

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jsonObj = array.getJSONObject(i);


                                bank_branchList.add(new SpinnerModal(jsonObj.getString("CITY_NAME"), jsonObj.getString("CITY_NAME")));
                                bank_branch.add(jsonObj.getString("CITY_NAME"));
                            }

                        }
                        bank_branchAdapter = new ArrayAdapter<SpinnerModal>(getActivity(), R.layout.spinner_item, bank_branchList);
                        bank_branchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        bank_branchSpin.setAdapter(bank_branchAdapter);


                    } catch (Exception e) {

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



        HashMap<String, String> params = new HashMap<>();
        params.put("state_name",  bank_name_modal.getSelKey());
        params.put("policy_id",  bank_name_modals.getSelKey());

        smr.setParams(params);
        rq.add(smr);




    }


    public void setCertData(String data) {
        RequestQueue rq = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();

        String url = con.base_url+"/api/admin/get/networkhospital/details";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject data=new JSONObject(response);
                            String status = String.valueOf(data.getBoolean("status"));



                            bank_branch = new ArrayList<>();
                            bank_branchList = new ArrayList<>();
                            bank_branchList.add(new SpinnerModal("", "Select Hospital Name"));
                            bank_branch.add("");

                            if(status.equalsIgnoreCase("false")){

                            }else {
                                JSONArray jsonArr = data.getJSONArray("data");
                                for (int i = 0; i < jsonArr.length(); i++) {
                                    JSONObject jsonObj = jsonArr.getJSONObject(i);


                                    bank_branchList.add(new SpinnerModal(String.valueOf(jsonObj.getInt("id")), jsonObj.getString("hospital_name")));
                                    bank_branch.add(jsonObj.getString("hospital_name"));
                                }

                            }
                            bank_branchAdapter = new ArrayAdapter<SpinnerModal>(getActivity(), R.layout.spinner_item, bank_branchList);
                            bank_branchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            hos_name.setAdapter(bank_branchAdapter);


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
        SpinnerModal bank_name_modal = (SpinnerModal) bank_branchSpin.getSelectedItem();
        SpinnerModal bank_state_modal = (SpinnerModal) policy_type_spin_no.getSelectedItem();






        HashMap<String, String> params = new HashMap<>();

        try {
            params.put("city_name", bank_name_modal.getSelValue());
            params.put("policy_id", bank_state_modal.getSelKey());

        }catch (Exception e){

        }

        smr.setParams(params);
        rq.add(smr);

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

    public void SendData() {

        int count = 0;

        SpinnerModal hos = (SpinnerModal) hos_name.getSelectedItem();
        SpinnerModal state = (SpinnerModal) bank_citySpin.getSelectedItem();
        SpinnerModal city = (SpinnerModal) bank_branchSpin.getSelectedItem();
        SpinnerModal policy = (SpinnerModal) policy_type_spin.getSelectedItem();
        SpinnerModalFamilyData patient = (SpinnerModalFamilyData) patient_name_spin.getSelectedItem();

        if (hos.getSelKey().trim().length() == 0) {
            ++count;
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error")
                    .setMessage("Please Select Hospital Name")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setNegativeButton(android.R.string.ok, null).show();
            return;
        }


        if (state.getSelKey().trim().length() == 0) {
            ++count;
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error")
                    .setMessage("Please Select State")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setNegativeButton(android.R.string.ok, null).show();
            return;
        }

        if (city.getSelKey().trim().length() == 0) {
            ++count;
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error")
                    .setMessage("Please Select City")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setNegativeButton(android.R.string.ok, null).show();
            return;
        }


        if (policy.getSelValue().trim().length() == 0) {
            ++count;
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error")
                    .setMessage("Please Select Policy Type")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setNegativeButton(android.R.string.ok, null).show();
            return;
        }

        if (patient.getSelValue().trim().length() == 0) {
            ++count;
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error")
                    .setMessage("Please Select Patient Name")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setNegativeButton(android.R.string.ok, null).show();
            return;
        }



        if (patient.getSelValue().equalsIgnoreCase("0")) {
            ++count;
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error")
                    .setMessage("Please Select Patient Name")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setNegativeButton(android.R.string.ok, null).show();
            return;
        }


        if (hos_date.getText().toString().trim().length() < 2) {
            ++count;
            hos_date.setError("select Discharge Date");
        } else {
            hos_date.setError(null);
        }


        if (patient_id.getText().toString().trim().length() < 2) {
            ++count;
            patient_id.setError("Enter Patient Id/Hospital File");
        } else {
            patient_id.setError(null);
        }


        if (contact.getText().toString().trim().isEmpty() || !isValid(contact.getText().toString().trim()) || contact.getText().toString().length() < 10 || contact.getText().toString().trim().length() > 13) {
            ++count;
            contact.setError("Invalid Mobile");
        } else {
            contact.setError(null);
        }

        if (dte_of_adminsiion.getText().toString().trim().length() < 2) {
            ++count;
            dte_of_adminsiion.setError("Select Planned Of Admission Date");
        } else {
            dte_of_adminsiion.setError(null);
        }

        if(Patterns.EMAIL_ADDRESS.matcher(email_id.getText().toString().trim()).matches()){
            email_id.setError(null);
        }else {
            email_id.setError("Invalid Email");
            ++count;
        }

        if (email_id.getText().toString().trim().length()< 2) {
            ++count;
            email_id.setError("Enter Email Id");
        } else {
            email_id.setError(null);
        }

        if (doc_name.getText().toString().trim().length() <2) {
            ++count;
            doc_name.setError("Doctor Name Is Required");
        } else {
            doc_name.setError(null);
        }


        if (claim_amount_reimurse.getText().toString().trim().length() <2) {
            ++count;
            claim_amount_reimurse.setError("Amount Is Required");
        } else {
            claim_amount_reimurse.setError(null);
        }



        if (admitted_for.getText().toString().trim().length() <2) {
            ++count;
            admitted_for.setError("Enter Reason For Hospitalization ?");
        } else {
            admitted_for.setError(null);
        }


        if (count == 0) {
            progressDialog = ProgressDialog.show(getActivity(), "",
                    "Saving. Please wait...", true);
            RequestQueue rq = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
            rq.getCache().clear();

            String url = con.base_url+"/api/admin/create/claim/intimate";
            StringRequest smr = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                progressDialog.dismiss();

                                Log.e("response",response);
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


                            }

                        }
                    },new Response.ErrorListener() {
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

            SpinnerModal marSel = (SpinnerModal) policy_type_spin_no.getSelectedItem();


            SpinnerModal statea = (SpinnerModal) bank_citySpin.getSelectedItem();
            SpinnerModal patientkg = (SpinnerModal) hos_name.getSelectedItem();
            SpinnerModal citya = (SpinnerModal) bank_branchSpin.getSelectedItem();









            SpinnerModalFamilyData patientk = (SpinnerModalFamilyData) patient_name_spin.getSelectedItem();


            HashMap<String, String> params = new HashMap<>();
            params.put("member_id", patientk.getSelValue());
            params.put("reason", admitted_for.getText().toString());
//            smr.addStringParam("claim_amt", claim_amount_reimurse.getText().toString());
            params.put("planned_date", dte_of_adminsiion.getText().toString());
            params.put("hospital_id", patientkg.getSelKey());
            params.put("doctor_name", doc_name.getText().toString());
            params.put("mobile_no", contact.getText().toString());
            params.put("email", email_id.getText().toString());
            params.put("discharge_date", hos_date.getText().toString());
            params.put("policy_id", marSel.getSelKey());
//            smr.addStringParam("policy_id", marSel.getSelKey());
            params.put("claim_type", "cashless");
            params.put("claim_amt",  claim_amount_reimurse.getText().toString());


            params.put("tpa_member_id", patientk.getAge());
            params.put("tpa_member_name", doc_name.getText().toString());
            params.put("status", "1");
            params.put("file_no", patient_id.getText().toString());
            params.put("state_name", statea.getSelValue());
            params.put("hospital_name", patientkg.getSelValue());
            params.put("city_name", citya.getSelValue());

            params.put("tpa_emp_id", "1");



            smr.setParams(params);
            rq.add(smr);


        }


    }



    private void getBankName(final String set_bank_name) {
        //String url =con.base_url+"/api/employee/policies";
        String url =con.base_url+"/api/admin/get/policy/subtype";
        RequestQueue rq = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
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


    private void getBankNamenumber(final String set_bank_name) {
        String url =con.base_url+"/api/admin/get/policyno";

        RequestQueue rq = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
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



    public void getBankCity(final String bank_name, final String set_bank_city) {
        RequestQueue rq = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
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
                                        jsonObj.getString("tpa_member_id"),
                                        "",
                                        ""));
                                bank_city.add(jsonObj.getString("name"));
                            }
                            bank_cityAdapter = new ArrayAdapter<SpinnerModalFamilyData>(getActivity(), R.layout.spinner_item, bank_cityList);
                            bank_cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            patient_name_spin.setAdapter(bank_cityAdapter);


                        } catch (Exception e) {

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
        params.put("employee_id", emp_id);

        params.put("policy_id", patientk.getSelKey());


        smr.setParams(params);
        rq.add(smr);
    }
    public void setProfileDet() {

        String url = con.base_url+"/api/admin/user";
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));

        mRequestQueue.getCache().clear();
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
        mRequestQueue.add(mStringRequest);

    }


    public  void EmployeeDetail(){
        String url = con.base_url+"/api/admin/user";
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));

        mRequestQueue.getCache().clear();
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
        mRequestQueue.add(mStringRequest);
    }



    public void getstate(final String bank_name, final String set_bank_city) {
        RequestQueue rq = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        String url = con.base_url+"/api/admin/get/networkhospital/state";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {


                            JSONObject jsonArra = new JSONObject(response);

                            String status= String.valueOf(jsonArra.getBoolean("status"));

                            if(status.equalsIgnoreCase("false")){

                                bank_cityc = new ArrayList<>();
                                bank_cityListc = new ArrayList<>();
                                bank_cityListc.add(new SpinnerModal("", "Select State"));
                                bank_cityc.add("");

                            }else {
                                JSONArray array=jsonArra.getJSONArray("data");
                                bank_cityc = new ArrayList<>();
                                bank_cityListc = new ArrayList<>();
                                bank_cityListc.add(new SpinnerModal("", "Select Hospital State"));
                                bank_cityc.add("");


                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject jsonObj = array.getJSONObject(i);

                                    bank_cityListc.add(new SpinnerModal(jsonObj.getString("state_name"),
                                            jsonObj.getString("state_name")));
                                    bank_cityc.add(jsonObj.getString("state_name"));
                                }

                            }


                            bank_cityAdapterc = new ArrayAdapter<SpinnerModal>(getActivity(), R.layout.spinner_item, bank_cityListc);
                            bank_cityAdapterc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            bank_citySpin.setAdapter(bank_cityAdapterc);

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

        SpinnerModal bank_city_modal = (SpinnerModal) policy_type_spin_no.getSelectedItem();


       /* smr.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        mRequestQueue.add(smr);*/

        HashMap<String, String> params = new HashMap<>();
        params.put("policy_id", bank_city_modal.getSelKey());

        Log.d("policy_id", bank_city_modal.getSelKey());


        smr.setParams(params);
        rq.add(smr);
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
