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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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
import com.palm.newbenefit.DatabaseHelper.DBHelper;
import com.palm.newbenefit.Module.AddNominee;
import com.palm.newbenefit.Module.SpinnerModal;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class EditNomineeActivity extends AppCompatActivity {
    ArrayList<String> n_First_name = null;
    ArrayList<String> n_last_name = null;
    ArrayList<String> n_dob = null;
    ArrayList<String> n_relation = null;
    ArrayList<String> g_First_name = null;
    ArrayList<String> g_last_name = null;
    ArrayList<String> g_dob = null;
    ArrayList<String> g_relation = null;
    ArrayList<String> share_n = null;
    String user_id;
    ArrayList<String> bank_name = null;
    ArrayList<SpinnerModal> bank_nameList = null;
    ArrayAdapter<SpinnerModal> bank_nameAdapter = null;
    Spinner relation_type_spin,relation_nominee;
    String token,policy_id;
    String gender = "Female";
    RadioButton male, female;
    Button saveBtn;
    EditText first_name, last_name, dob, share;
    EditText  first_namen, last_namen, dobn;
    private SimpleDateFormat dateFormatter;
    LinearLayout guar_detail;
    TextView hos_add_button, hos_clear_btn, send, view_data;


    Calendar newCalendar = Calendar.getInstance();
    ProgressDialog progressDialog = null;
    private DatePickerDialog fromDatePickerDialog;

    DBHelper db;
    AddNominee typedata;
    Constants con;


    private List<AddNominee> ob;

    String Id;

    ImageView info_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_nominee);
        con=new Constants();
//        AnalyticsApplication.getInstance().trackEvent("Edit Nominee Page", "Edit Nominee Page Displaying", "Edit Nominee Page Displaying");
//
        relation_type_spin = findViewById(R.id.relation_type_spin);
        newCalendar = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        // saveBtn = v.findViewById(R.id.saveBtn);
        db = new DBHelper(this);

        typedata = new AddNominee();
        db.removeAll();


        SharedPreferences prefs = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);

        token = prefs.getString("api_token", null);
        policy_id = prefs.getString("policy_id", null);
        send = findViewById(R.id.send);

        hos_add_button = findViewById(R.id.hos_add_button);


        guar_detail = findViewById(R.id.guar_detail);
        first_name = findViewById(R.id.first_name);
        first_namen = findViewById(R.id.first_namen);
        last_name = findViewById(R.id.last_name);
        last_namen = findViewById(R.id.last_namen);
        dob = findViewById(R.id.dob);
        share = findViewById(R.id.share);
        relation_nominee = findViewById(R.id.relation_nominee);
        dobn = findViewById(R.id.dobn);
        info_text=findViewById(R.id.info_text);



        info_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();

            }
        });

        Intent intent = getIntent();


        Id = intent.getStringExtra("id");


        getBankCity(Id);


        hos_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isNetworkAvailable()) {
                    SendHospital();
                }else {
                    new AlertDialog.Builder(EditNomineeActivity.this)
                            .setTitle("Error?")
                            .setMessage("Please Check Your Internet Connection")
                            .setIcon(android.R.drawable.btn_dialog)
                            .setNegativeButton(android.R.string.ok, null).show();
                }


            }
        });


        TextWatcher fieldValidatorTextWatcher = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (filterLongEnough()) {
                    String birthdateStr = dob.getText().toString();
                    SimpleDateFormat df = new SimpleDateFormat("dd-mm-yyyy");
                    Date birthdate = null;
                    try {
                        birthdate = df.parse(birthdateStr);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Calendar birth = Calendar.getInstance();
                    birth.setTime(birthdate);
                    Calendar today = Calendar.getInstance();

                    int yearDifference = today.get(Calendar.YEAR)
                            - birth.get(Calendar.YEAR);

                    if (today.get(Calendar.MONTH) < birth.get(Calendar.MONTH)) {
                        yearDifference--;
                    } else {
                        if (today.get(Calendar.MONTH) == birth.get(Calendar.MONTH)
                                && today.get(Calendar.DAY_OF_MONTH) < birth
                                .get(Calendar.DAY_OF_MONTH)) {
                            yearDifference--;
                        }

                    }


                    if (yearDifference <= 18) {

                        guar_detail.setVisibility(View.VISIBLE);
                    } else {
                        guar_detail.setVisibility(View.GONE);
                    }

                }
            }

            private boolean filterLongEnough() {
                return dob.getText().toString().trim().length() > 0;
            }
        };



        if (isNetworkAvailable()) {
            dob.addTextChangedListener(fieldValidatorTextWatcher);
        }else {
            new AlertDialog.Builder(EditNomineeActivity.this)
                    .setTitle("Error?")
                    .setMessage("Please Check Your Internet Connection")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
        }






        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.desablePostDatePicker(EditNomineeActivity.this, new MyDateListener() {
                    @Override
                    public void onDateSelect(int selectedYear, int selectedMonth, int selectedDay) {
                        newCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                        newCalendar.set(Calendar.MONTH, selectedMonth);
                        newCalendar.set(Calendar.YEAR, selectedYear);
                        String date = DateTimeFormater.getFormatedDate(newCalendar.getTime());
                        dob.setText(date);
                        int age=0;

                        Calendar dob = Calendar.getInstance();
                        Calendar today = Calendar.getInstance();
                        dob.set(selectedYear, selectedMonth, selectedDay);

                        int monthToday = today.get(Calendar.MONTH) + 1;
                        int monthDOB = dob.get(Calendar.MONTH)+1;
                        age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

                        if (age <= 18) {

                            guar_detail.setVisibility(View.VISIBLE);
                        } else {
                            guar_detail.setVisibility(View.GONE);
                        }

                    }
                });

            }
        });


        dobn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(
                        EditNomineeActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker,
                                          int selectedyear, int selectedmonth,
                                          int selectedday) {
                        selectedyear=selectedyear;
                        mcurrentDate.set(Calendar.YEAR, selectedyear);
                        mcurrentDate.set(Calendar.MONTH, selectedmonth);
                        mcurrentDate.set(Calendar.DAY_OF_MONTH, selectedday);
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                        dobn.setText(sdf.format(mcurrentDate.getTime()));


                    }
                }, mYear-18, mMonth, mDay);
                mcurrentDate.set(mYear-18,mMonth,mDay);
                long value=mcurrentDate.getTimeInMillis();
                mDatePicker.getDatePicker().setMaxDate(value);
                mDatePicker.show();

            }
        });


        relation_type_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerModal bank_name_modal = (SpinnerModal) adapterView.getSelectedItem();
                if (!bank_name_modal.selValue.equals("")) {
                    try {


                        if (isNetworkAvailable()) {

                           // MembersData(bank_name_modal.getSelValue());
                        } else {
                            new AlertDialog.Builder(EditNomineeActivity.this)
                                    .setTitle("Error?")
                                    .setMessage("Please Check Your Internet Connection")
                                    .setIcon(android.R.drawable.btn_dialog)
                                    .setNegativeButton(android.R.string.ok, null).show();
                        }


                    } catch (Exception e) {

                    }
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });





    }



    public void getBankCity(String family_id) {
        RequestQueue rq = Volley.newRequestQueue(EditNomineeActivity.this,
                new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        String url = con.base_url+"/api/employee/get/nominee/byid";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject js=new JSONObject(response);

                            Log.d("mydata",response);
                            JSONArray jsonObj=js.getJSONArray("data");

                            for (int i = 0; i < jsonObj.length(); i++) {
                                JSONObject explrObject = jsonObj.getJSONObject(i);

                                String id = explrObject.getString("id");
                                String emp_id = explrObject.getString("emp_id");
                                String nominee_relation = explrObject.getString("nominee_relation");
                                String nominee_fname = explrObject.getString("nominee_fname");
                                String nominee_lname = explrObject.getString("nominee_lname");
                                String guardian_fname = explrObject.getString("guardian_fname");
                                String guardian_lname = explrObject.getString("guardian_lname");
                                String guardian_relation = explrObject.getString("guardian_relation");
                                String guardian_dob = explrObject.getString("guardian_dob");
                                String share_per = explrObject.getString("share_per");
                                String nominee_dob = explrObject.getString("nominee_dob");
                                String is_confirm = explrObject.getString("is_confirm");







                                share.setText(share_per);


                                first_name.setText(nominee_fname);

                                if(nominee_lname.equalsIgnoreCase("null")){

                                }else {
                                    last_name.setText(nominee_lname);
                                }



                                try{

                                    String strCurrentDate = nominee_dob;
                                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                    Date newDate = format.parse(strCurrentDate);

                                    format = new SimpleDateFormat("dd-MM-yyyy");

                                    dob.setText(format.format(newDate));
                                }catch (Exception e){
                                    dob.setText(nominee_dob);
                                }





                                if(guardian_fname.equalsIgnoreCase("null")||guardian_fname.isEmpty()){
                               /*     last_namen.setText(guardian_lname);
                                    first_namen.setText(guardian_fname);
                                    getBankNameNominee(guardian_relation);
                                    dobn.setText(nominee_dob);*/

                                    getBankNameNominee("");
                                    last_namen.setText(null);
                                    first_namen.setText(null);
                                    guar_detail.setVisibility(View.GONE);

                                }else {

                                    if(guardian_lname.equalsIgnoreCase("null")){

                                    }else {
                                        last_namen.setText(guardian_lname);
                                    }


                                    first_namen.setText(guardian_fname);
                                    getBankNameNominee(guardian_relation);
                                    guar_detail.setVisibility(View.VISIBLE);

                                    if(guardian_dob.equalsIgnoreCase("null")){

                                    }else {
                                        try{

                                            String strCurrentDate = guardian_dob;
                                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                            Date newDate = format.parse(strCurrentDate);

                                            format = new SimpleDateFormat("dd-MM-yyyy");

                                            dobn.setText(format.format(newDate));
                                        }catch (Exception e){
                                            dobn.setText(guardian_dob);
                                        }
                                    }


                                }



                                getBankName(nominee_relation);

                              //  relation_type_spin.setText(nominee_relation);





                              /*  if (dob.getText().toString().trim().length() > 0) {
                                    String birthdateStr =dob.getText().toString().trim();
                                    SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");
                                    Date birthdate = null;
                                    try {
                                        birthdate = df.parse(birthdateStr);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                    Calendar birth = Calendar.getInstance();
                                    birth.setTime(birthdate);
                                    Calendar today = Calendar.getInstance();

                                    int yearDifference = today.get(Calendar.YEAR)
                                            - birth.get(Calendar.YEAR);

                                    if (today.get(Calendar.MONTH) < birth.get(Calendar.MONTH)) {
                                        yearDifference--;
                                    } else {
                                        if (today.get(Calendar.MONTH) == birth.get(Calendar.MONTH)
                                                && today.get(Calendar.DAY_OF_MONTH) < birth
                                                .get(Calendar.DAY_OF_MONTH)) {
                                            yearDifference--;
                                        }

                                    }
                                    Log.d("yearDifference", String.valueOf(yearDifference));

                                    if (yearDifference <= 18) {

                                        guar_detail.setVisibility(View.VISIBLE);
                                    } else {
                                        guar_detail.setVisibility(View.GONE);
                                    }




                                }

*/



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



        HashMap<String, String> params = new HashMap<>();

        params.put("id", family_id);
        params.put("policy_id", policy_id);
        Log.d("id", family_id);
        smr.setParams(params);
        rq.add(smr);




    }


    private void getBankName(String id) {
        String url = con.base_url+"/api/admin/get/master/relation";
        RequestQueue mRequestQueue = Volley.newRequestQueue(EditNomineeActivity.this,
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
                    bank_nameAdapter = new ArrayAdapter<SpinnerModal>(EditNomineeActivity.this, R.layout.spinner_item, bank_nameList);
                    bank_nameAdapter.setDropDownViewResource(R.layout.spinner_item);

                    relation_type_spin.setAdapter(bank_nameAdapter);

                    if (!id.equals(""))
                        relation_type_spin.setSelection(bank_nameAdapter.getPosition(bank_nameList.get(bank_name.indexOf(id))));


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

    private void getBankNameNominee(String id) {
        String url = con.base_url+"/api/admin/get/master/relation";

        RequestQueue mRequestQueue = Volley.newRequestQueue(EditNomineeActivity.this,
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
                            if (jsonObj.getString("name").equalsIgnoreCase("Father")||
                                    jsonObj.getString("name").equalsIgnoreCase("Mother")||
                                    jsonObj.getString("name").equalsIgnoreCase("Siblings")) {

                                bank_nameList.add(new SpinnerModal(jsonObj.getString("id"), jsonObj.getString("name")));
                                bank_name.add(jsonObj.getString("name"));
                            }

                        }
                    }
                    bank_nameAdapter = new ArrayAdapter<SpinnerModal>(EditNomineeActivity.this, R.layout.spinner_item, bank_nameList);
                    bank_nameAdapter.setDropDownViewResource(R.layout.spinner_item);

                    relation_nominee.setAdapter(bank_nameAdapter);
                    Log.e("id", id);
                    if (!id.equals(""))
                        relation_nominee.setSelection(bank_nameAdapter.getPosition(bank_nameList.get(bank_name.indexOf(id))));



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




    public void SendHospital() {

        int count = 0;


        if (first_name.getText().toString().trim().length() == 0) {
            ++count;
            first_name.setError("first Name is Required");
        } else {
            first_name.setError(null);
        }




        if (dob.getText().toString().trim().length() == 0) {
            ++count;
            dob.setError("Date of birth is Required");
        } else {
            dob.setError(null);
        }


        if (share.getText().toString().trim().length() == 0) {
            ++count;
            share.setError("Enter share");
        } else {
            share.setError(null);
        }


        if (guar_detail.getVisibility() == View.VISIBLE) {
            if (first_namen.getText().toString().trim().length() == 0) {
                ++count;
                first_namen.setError("Guardian first Name is Required");
            } else {
                first_namen.setError(null);
            }




            if (dobn.getText().toString().trim().length() == 0) {
                ++count;
                dobn.setError("Guardian Date of birth is Required");
            } else {
                dobn.setError(null);
            }

        }


        if (count == 0) {

            progressDialog = ProgressDialog.show(EditNomineeActivity.this, "",
                    "Saving. Please wait...", true);

            RequestQueue rq = Volley.newRequestQueue(EditNomineeActivity.this,
                    new HurlStack(null, getSocketFactory()));
            String url = con.base_url+"/api/employee/update/nominee";
            StringRequest smr = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                progressDialog.dismiss();

                                Log.d("response_save",response);




                                JSONObject jsonObject = new JSONObject(response);

                                String value = String.valueOf(jsonObject.getBoolean("status"));
                                String message =jsonObject.getString("message");

                                if(value.equalsIgnoreCase("true")){

                                    new AlertDialog.Builder(EditNomineeActivity.this)
                                            .setTitle("Success")
                                            .setMessage(message)
                                            .setIcon(R.drawable.checkmark)
                                            .setNegativeButton(android.R.string.ok, null).show();




                                }else {
                                    new AlertDialog.Builder(EditNomineeActivity.this)
                                            .setTitle("Error")
                                            .setMessage(message)
                                            .setIcon(android.R.drawable.btn_dialog)
                                            .setNegativeButton(android.R.string.ok, null).show();
                                }



                            } catch (Exception e) {
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



            SpinnerModal patients = (SpinnerModal) relation_type_spin.getSelectedItem();
            SpinnerModal patienta = (SpinnerModal) relation_nominee.getSelectedItem();





            HashMap<String, String> params = new HashMap<>();





            params.put("nominee_fname", first_name.getText().toString());

            params.put("nominee_relation_id", patients.getSelKey());
            params.put("nominee_dob", dob.getText().toString());
            params.put("update_id", Id);
            params.put("policy_id", policy_id);
            params.put("status", "1");
            params.put("share_per", share.getText().toString().trim());

            if( last_name.getText().toString().length()==0){

            }else {
                params.put("nominee_lname", last_name.getText().toString());
            }




            if(guar_detail.getVisibility()==View.VISIBLE){
                params.put("guardian_fname", first_namen.getText().toString());
                params.put("guardian_relation_id", patienta.getSelKey());

                if( last_namen.getText().toString().length()==0){
                }
                else
                {
                    params.put("guardian_lname", last_namen.getText().toString());
                }
                params.put("guardian_dob", dobn.getText().toString());

            }


            smr.setParams(params);
            rq.add(smr);






        }


    }

    void MembersData (String valuedata){
        String url = "https://eb.fynity.in/api/employee/get/enroll/members?policy_id="+policy_id;
        RequestQueue mRequestQueue = Volley.newRequestQueue(EditNomineeActivity.this);

        //  RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);


                    JSONArray jsonArr=js.getJSONArray("data");


                    dob.setText(null);
                    first_name.setText(null);
                    last_name.setText(null);


                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObj = jsonArr.getJSONObject(i);

                        Log.e("valuedata", valuedata);



                        if (jsonObj.getString("relation_name").equalsIgnoreCase(valuedata)) {



                            if(jsonObj.getString("first_name").equalsIgnoreCase("")
                                    || jsonObj.getString("first_name").equalsIgnoreCase("null")){
                                first_name.setText(null);
                            }else {
                                first_name.setText(jsonObj.getString("first_name"));

                                //last_name.setFocusableInTouchMode(false);
                            }



                            if(jsonObj.getString("last_name").equalsIgnoreCase("")
                                    || jsonObj.getString("last_name").equalsIgnoreCase("null")){
                                last_name.setText(null);
                            }else {
                                last_name.setText(jsonObj.getString("last_name"));
                                //last_name.setFocusableInTouchMode(false);
                            }



                            if(jsonObj.getString("dob").equalsIgnoreCase("")
                                    || jsonObj.getString("dob").equalsIgnoreCase("null")){
                                dob.setText(null);
                            }else {
                                try{

                                    String strCurrentDate = jsonObj.getString("dob");
                                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                    Date newDate = format.parse(strCurrentDate);

                                    format = new SimpleDateFormat("dd-MM-yyyy");

                                    dob.setText(format.format(newDate));


                                    Calendar doba = Calendar.getInstance();
                                    Calendar today = Calendar.getInstance();

                                    String[] items1 = dob.getText().toString().trim().split("-");
                                    int date1= Integer.parseInt(items1[0]);
                                    int month= Integer.parseInt(items1[1]);
                                    int year= Integer.parseInt(items1[2]);

                                    Log.d("datenew",year+"-"+month+"-"+date1);


                                    doba.set(year, month, date1);

                                    int age = today.get(Calendar.YEAR) - doba.get(Calendar.YEAR);

                                    if (today.get(Calendar.DAY_OF_YEAR) < doba.get(Calendar.DAY_OF_YEAR)){
                                        age--;
                                    }

                                    Integer ageInt = new Integer(age);
                                    String ageS = ageInt.toString();







                                    if (ageInt <= 18) {
                                        guar_detail.setVisibility(View.VISIBLE);
                                    } else {
                                        guar_detail.setVisibility(View.GONE);
                                    }


                                }catch (Exception e){
                                    dob.setText(jsonObj.getString("dob"));



                                    Calendar doba = Calendar.getInstance();
                                    Calendar today = Calendar.getInstance();

                                    String[] items1 = jsonObj.getString("dob").split("-");
                                    int date1= Integer.parseInt(items1[0]);
                                    int month= Integer.parseInt(items1[1]);
                                    int year= Integer.parseInt(items1[2]);

                                    Log.d("datenew",year+"-"+month+"-"+date1);


                                    doba.set(year, month, date1);

                                    int age = today.get(Calendar.YEAR) - doba.get(Calendar.YEAR);

                                    if (today.get(Calendar.DAY_OF_YEAR) < doba.get(Calendar.DAY_OF_YEAR)){
                                        age--;
                                    }

                                    Integer ageInt = new Integer(age);
                                    String ageS = ageInt.toString();







                                    if (ageInt <= 18) {
                                        guar_detail.setVisibility(View.VISIBLE);
                                    } else {
                                        guar_detail.setVisibility(View.GONE);
                                    }

                                }

                            }



                            // first_name.setFocusableInTouchMode(false);


                        }


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
        mRequestQueue.add(mStringRequest);
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
}
