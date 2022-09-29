package com.palm.newbenefit.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class FamilyDataActivity extends AppCompatActivity {
    Spinner relation_type;
    EditText first_name, m_date, last_name, dob, email_id, contact, pincode, building, city, state;
    Toolbar settingtoolbar;
    Constants con;
    Context context;
    TextView call;
    CheckBox check;
    Button submit, clear, edit;
    String family_id;
    String user_id;
    LinearLayout marr_date;
    ArrayList<String> relation_Status = null;
    ArrayList<SpinnerModal> Relation_list = null;
    ArrayAdapter<SpinnerModal> RelationAdapter = null;
    DatePickerDialog.OnDateSetListener datePickerListener = null;
    ProgressDialog progressDialog = null;
    private DatePickerDialog fromDatePickerDialog;
    String data="0";
    String invalid="1";
    String token;
    private SimpleDateFormat dateFormatter;
    Calendar newCalendar;
    ArrayList<SpinnerModal> bank_nameList = null;
    ArrayAdapter<SpinnerModal> bank_nameAdapter = null;
    ArrayList<String> bank_name = null;
    ImageView info_text;
    EditText relation;
    Button delete;
    String policy_no;
    String relationn;
    SwitchCompat switchCompat;

    String dob_status="0";
    LinearLayout tab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_data);
        dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
      //  AnalyticsApplication.getInstance().trackEvent("Family Member Detail Page", "Family Member Detail Page Displaying", "Family Member Detail Page Displaying");

        newCalendar = Calendar.getInstance();
        con = new Constants();
        context = getApplicationContext();
        clear = findViewById(R.id.cancle);
       /* settingtoolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_chevron_left_black_24dp));
        settingtoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/

         switchCompat = (SwitchCompat) findViewById(R.id.swOnOff);
        switchCompat.setOnCheckedChangeListener(onCheckedChanged());
        tab = (LinearLayout) findViewById(R.id.tab);

        SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);

        token = prefs.getString("api_token", null);
        user_id = prefs.getString("user_id", null);

        check = findViewById(R.id.check);
        relation_type = findViewById(R.id.relation_type);
        m_date = findViewById(R.id.m_date);
        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        dob = findViewById(R.id.dob);
        email_id = findViewById(R.id.email_id);
        contact = findViewById(R.id.contact);
        pincode = findViewById(R.id.pincode);
        building = findViewById(R.id.building);

        city = findViewById(R.id.city);
        state = findViewById(R.id.state);

        submit = findViewById(R.id.submit);
        edit = findViewById(R.id.edit);
        marr_date = findViewById(R.id.marr_date);
        relation= findViewById(R.id.relation);
        info_text=findViewById(R.id.info_text);
        delete=findViewById(R.id.delete);

        String dataa = String.valueOf(building.getText().toString().length());



        info_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();

            }
        });
        Intent intent = getIntent();



        family_id = intent.getStringExtra("fam_id");




        if(pincode.getText().toString().trim().length()>0){

            if (isNetworkAvailable()) {
                getPincode();
            }else {
                new AlertDialog.Builder(FamilyDataActivity.this)
                        .setTitle("Error?")
                        .setMessage("Please Check Your Internet Connection")
                        .setIcon(android.R.drawable.btn_dialog)
                        .setNegativeButton(android.R.string.ok, null).show();
            }

        }



        getBankCity(family_id);



      /*  if (relationn != "null" && !relationn.isEmpty()) {
            relation_type.setSelection(RelationAdapter.getPosition(Relation_list.get(relation_Status.indexOf(relationn))));
        }
*/


//        relation_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                SpinnerModal bank_name_modal = (SpinnerModal) adapterView.getSelectedItem();
//
//
//                if (bank_name_modal.getSelKey().equalsIgnoreCase("Spouse")) {
//                    marr_date.setVisibility(View.VISIBLE);
//                    m_date.setVisibility(View.VISIBLE);
//
//                } else {
//                    marr_date.setVisibility(View.GONE);
//                    m_date.setVisibility(View.GONE);
//                }
//
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//

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

                    RequestQueue rq = Volley.newRequestQueue(FamilyDataActivity.this,
                            new HurlStack(null, getSocketFactory()));
                    rq.getCache().clear();
                    String url =con.base_url+"/api/employee/get/state-city";
                    StringRequest smr = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        Log.d("pincode",response);


                                        JSONObject js=new JSONObject(response);



                                        String status= String.valueOf(js.getBoolean("status"));


                                        if(status.equalsIgnoreCase("false")){

                                            invalid="1";

                                            city.setText("");
                                            state.setText("");
                                            new AlertDialog.Builder(FamilyDataActivity.this)
                                                    .setTitle("Invalid Pincode?")
                                                    .setMessage("Please Check the Pincode!")
                                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                                    .setNegativeButton(android.R.string.ok, null).show();

                                        } else  {

                                            invalid="0";
                                            Log.d("response",response);
                                            JSONArray  jsonObj=js.getJSONArray("data");

                                            for (int i = 0; i < jsonObj.length(); i++) {
                                                JSONObject explrObject = jsonObj.getJSONObject(i);

                                                city.setText(explrObject.getString("city_name"));
                                                state.setText(explrObject.getString("state_name"));

                                            }

                                        }




                                    } catch (Exception e) {

                                        e.printStackTrace();
                                       /* new AlertDialog.Builder(FamilyDataActivity.this)
                                                .setTitle("Invalid Pincode?")
                                                .setMessage("Please Check the Pincode!")
                                                .setIcon(android.R.drawable.ic_dialog_alert)
                                                .setNegativeButton(android.R.string.ok, null).show();*/
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

                    params.put("pincode", pincode.getText().toString().trim());

                    smr.setParams(params);
                    rq.add(smr);


                } else {
                    city.setText("");
                    state.setText("");
                }
            }

            private boolean filterLongEnough() {
                return pincode.getText().toString().trim().length() > 5;
            }
        };



        if (isNetworkAvailable()) {
            pincode.addTextChangedListener(fieldValidatorTextWatcher);

        }else {
            new AlertDialog.Builder(FamilyDataActivity.this)
                    .setTitle("Error?")
                    .setMessage("Please Check Your Internet Connection")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
        }


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()) {
                    SendData();
                }else {
                    new AlertDialog.Builder(FamilyDataActivity.this)
                            .setTitle("Error?")
                            .setMessage("Please Check Your Internet Connection")
                            .setIcon(android.R.drawable.btn_dialog)
                            .setNegativeButton(android.R.string.ok, null).show();
                }



            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (isNetworkAvailable()) {
                    new AlertDialog.Builder(FamilyDataActivity.this)
                            .setTitle("Wait !!!")
                            .setMessage("Are You Sure You Want To Delete Member ?")
                            .setNegativeButton(android.R.string.no, null)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface arg0, int arg1) {





                                    Delete_Data();

                                }
                            }).create().show();




                }else {
                    new AlertDialog.Builder(FamilyDataActivity.this)
                            .setTitle("Error?")
                            .setMessage("Please Check Your Internet Connection")
                            .setIcon(android.R.drawable.btn_dialog)
                            .setNegativeButton(android.R.string.ok, null).show();
                }






            }
        });


        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getBankbranch("");
                first_name.setText("");
                last_name.setText("");
                email_id.setText("");
                dob.setText("");
                contact.setText("");
                pincode.setText("");
                building.setText("");

                city.setText("");
                state.setText("");

                m_date.setText("");


            }
        });


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dob_status="1";

                data="1";


                first_name.setFocusableInTouchMode(true);
                last_name.setFocusableInTouchMode(true);
                email_id.setFocusableInTouchMode(true);
                contact.setFocusableInTouchMode(true);
                pincode.setFocusableInTouchMode(true);
                building.setFocusableInTouchMode(true);

                city.setFocusableInTouchMode(true);
                state.setFocusableInTouchMode(true);

                check.setFocusable(true);
                check.setEnabled(true);

                submit.setVisibility(View.VISIBLE);
                edit.setVisibility(View.GONE);



//                if (isNetworkAvailable()) {
//
//                    saveBankDet();
//
//                }else {
//                    new AlertDialog.Builder(FamilyDataActivity.this)
//                            .setTitle("Error?")
//                            .setMessage("Please Check Your Internet Connection")
//                            .setIcon(android.R.drawable.btn_dialog)
//                            .setNegativeButton(android.R.string.ok, null).show();
//                }









            }
        });


        if (check.isChecked()) {


            if (isNetworkAvailable()) {

                setProfileDet();
            }else {
                new AlertDialog.Builder(FamilyDataActivity.this)
                        .setTitle("Error?")
                        .setMessage("Please Check Your Internet Connection")
                        .setIcon(android.R.drawable.btn_dialog)
                        .setNegativeButton(android.R.string.ok, null).show();
            }



        } else {

        }




        m_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(data.equalsIgnoreCase("1")){

                    Utils.desablePostDatePicker(FamilyDataActivity.this, new MyDateListener() {
                        @Override
                        public void onDateSelect(int selectedYear, int selectedMonth, int selectedDay) {
                            newCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                            newCalendar.set(Calendar.MONTH, selectedMonth);
                            newCalendar.set(Calendar.YEAR, selectedYear);

                            String date = DateTimeFormater.getFormatedDate(newCalendar.getTime());
                            m_date.setText(date);

                            //travel_request_todateshow.setText("");
                        }
                    });
                }




            }
        });



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

               // SpinnerModal bank_branch_modal = (SpinnerModal) relation_type.getSelectedItem();

                String relation_name=relation.getText().toString();

                if (relation_name.equalsIgnoreCase("Spouse") ||
                        relation_name.equalsIgnoreCase("Mother") ||
                        relation_name.equalsIgnoreCase("Father") ||relation_name .equalsIgnoreCase("Mother-in-law") ||
                        relation_name.equalsIgnoreCase("Father-in-law")) {

                    final Calendar mcurrentDate = Calendar.getInstance();
                    int mYear = mcurrentDate.get(Calendar.YEAR);
                    int mMonth = mcurrentDate.get(Calendar.MONTH);
                    int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog mDatePicker = new DatePickerDialog(
                            FamilyDataActivity.this, new DatePickerDialog.OnDateSetListener() {
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

                    Utils.desablePostDatePicker(FamilyDataActivity.this, new MyDateListener() {
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



















        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (isNetworkAvailable()) {

                    setProfileDet();

                }else {
                    new AlertDialog.Builder(FamilyDataActivity.this)
                            .setTitle("Error?")
                            .setMessage("Please Check Your Internet Connection")
                            .setIcon(android.R.drawable.btn_dialog)
                            .setNegativeButton(android.R.string.ok, null).show();
                }


            }

        });




    }



    private CompoundButton.OnCheckedChangeListener onCheckedChanged() {
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switch (buttonView.getId()) {
                    case R.id.swOnOff:
                        setButtonState(isChecked);
                        break;

                }
            }
        };
    }

    private void setButtonState(boolean statea) {
        if (statea) {
            if (isNetworkAvailable()) {

                setProfileDet();

            }else {
                new AlertDialog.Builder(FamilyDataActivity.this)
                        .setTitle("Error?")
                        .setMessage("Please Check Your Internet Connection")
                        .setIcon(android.R.drawable.btn_dialog)
                        .setNegativeButton(android.R.string.ok, null).show();
            }

        } else {

                pincode.setText("");

                building.setText("");

                city.setText("");

                state.setText("");


        }
    }

    public void getBankbranch(final String bank_city) {


        String url = con.base_url+"/api/employee/get/relation";

        RequestQueue mRequestQueue = Volley.newRequestQueue(FamilyDataActivity.this,
                new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);

                    Log.d("response",response);
                    JSONArray  jsonArr=js.getJSONArray("data");
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
                            relation_type.setAdapter(bank_nameAdapter);

                            if (!bank_city.equals(""))
                                relation_type.setSelection(bank_nameAdapter.getPosition(bank_nameList.get(bank_name.indexOf(bank_city))));


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
    public void getBankCity(String family_id) {
        RequestQueue rq = Volley.newRequestQueue(FamilyDataActivity.this,
                new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        String url = con.base_url+"/api/employee/get/family/member/detail";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject js=new JSONObject(response);

                            Log.d("mydata",response);
                            JSONArray  jsonObj=js.getJSONArray("data");

                            for (int i = 0; i < jsonObj.length(); i++) {
                                JSONObject explrObject = jsonObj.getJSONObject(i);




                                String id=explrObject.getString("id");
                                String first_namae=explrObject.getString("first_name");
                                String last_namea=explrObject.getString("last_name");
                                String doba=explrObject.getString("dob");
                                String marriage_date=explrObject.getString("marriage_date");
                                String member_mob_no=explrObject.getString("member_mob_no");
                                String member_email=explrObject.getString("member_email");
                                String citya=explrObject.getString("city");
                                String statea=explrObject.getString("state");
                                String address=explrObject.getString("address");
                                relationn=explrObject.getString("relation_id");
                                String relationna=explrObject.getString("relation_name");
                                String pincodea=explrObject.getString("pincode");



                                if(first_namae.equals("null")||first_namae.isEmpty()){

                                }else {
                                    first_name.setText(first_namae);

                                }


                                if(last_namea.equals("null")||last_namea.isEmpty()){

                                }else {
                                    last_name.setText(last_namea);

                                }

                                if(member_mob_no.equals("null")||member_mob_no.isEmpty()){

                                }else {
                                    contact.setText(member_mob_no);


                                }


                                if(doba.equals("null")||doba.isEmpty()){

                                }else {
                                    dob.setText(doba);

                                }





                                if(marriage_date.equals("null")||marriage_date.isEmpty()){

                                }else {
                                    m_date.setText(marriage_date);

                                }




                                if(member_email.equals("null")||member_email.isEmpty()){

                                }else {
                                    email_id.setText(member_email);

                                }




                                if(address.equals("null")||address.isEmpty()){

                                }else {
                                    building.setText(address);

                                }

                                if(pincodea.equals("null")||pincodea.isEmpty()){

                                }else {
                                    pincode.setText(pincodea);

                                }


                                if(citya.equals("null")||citya.isEmpty()){

                                }else {
                                    city.setText(citya);

                                }

                                if(statea.equals("null")||statea.isEmpty()){

                                }else {
                                    state.setText(statea);

                                }




                                if(relationna.equals("null")||relationna.isEmpty()){


                                }else {
                                    relation.setText(relationna);

                                }













                                if (isNetworkAvailable()) {
                                    getBankbranch(relationn);
                                }else {
                                    new AlertDialog.Builder(FamilyDataActivity.this)
                                           .setTitle("Error?")
                                           .setMessage("Please Check Your Internet Connection")
                                            .setIcon(android.R.drawable.btn_dialog)
                                            .setNegativeButton(android.R.string.ok, null).show();
                                }








                                if (relationna.equalsIgnoreCase("Spouse")) {
                                    marr_date.setVisibility(View.VISIBLE);
                                    m_date.setVisibility(View.VISIBLE);

                                } else {
                                    marr_date.setVisibility(View.GONE);
                                    m_date.setVisibility(View.GONE);
                                }



                                if (relationna.equalsIgnoreCase("Self")) {
                                  tab.setVisibility(View.GONE);

                                } else {
                                    tab.setVisibility(View.VISIBLE);
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



        HashMap<String, String> params = new HashMap<>();


        params.put("member_id", family_id);
        Log.d("member_id", family_id);

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



    public void getPincode() {


        RequestQueue rq = Volley.newRequestQueue(FamilyDataActivity.this,
                new HurlStack(null, getSocketFactory()));
        String url =con.base_url+"/api/employee/get/state-city";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            Log.d("pincode", response);

                            if(response.equalsIgnoreCase("null")){

                                invalid="1";
                                Log.d("invalid", invalid);

                                city.setText("");
                                state.setText("");
                                new AlertDialog.Builder(FamilyDataActivity.this)
                                        .setTitle("Invalid Pincode?")
                                        .setMessage("Please Check the Pincode!")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setNegativeButton(android.R.string.ok, null).show();

                            }else  if (!response.equalsIgnoreCase("0")) {

                                invalid="0";
                                Log.d("invalid", invalid);
                                JSONObject jsonObject = new JSONObject(response);

                                city.setText(jsonObject.getString("city_name"));
                                state.setText(jsonObject.getString("state_name"));
                            }



                            else {

                                invalid="1";
                                Log.d("invalid", invalid);
                                city.setText("");
                                state.setText("");
                                new AlertDialog.Builder(FamilyDataActivity.this)
                                        .setTitle("Invalid Pincode?")
                                        .setMessage("Please Check the Pincode!")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setNegativeButton(android.R.string.ok, null).show();
                            }
                        } catch (Exception e) {

                            e.printStackTrace();
                                       /* new AlertDialog.Builder(FamilyDataActivity.this)
                                                .setTitle("Invalid Pincode?")
                                                .setMessage("Please Check the Pincode!")
                                                .setIcon(android.R.drawable.ic_dialog_alert)
                                                .setNegativeButton(android.R.string.ok, null).show();*/
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });




        HashMap<String, String> params = new HashMap<>();


        params.put("pincode", pincode.getText().toString().trim());

        smr.setParams(params);
        rq.add(smr);




    }


    public void SendData() {

        int count = 0;

        if (first_name.getText().toString().trim().length() == 0) {
            ++count;
            first_name.setError("First Name is Required");
        } else {
            first_name.setError(null);
        }


        if (contact.getText().toString().trim().isEmpty() || !isValid(contact.getText().toString().trim()) || contact.getText().toString().length() < 10 || contact.getText().toString().trim().length() > 13) {
            ++count;
            contact.setError("Invalid Mobile");
        } else {
            contact.setError(null);
        }

        if (last_name.getText().toString().trim().length() == 0) {
            ++count;
            last_name.setError("Last Name is Required");
        } else {
            last_name.setError(null);
        }



        if (email_id.getText().toString().trim().length() == 0) {
            ++count;
            email_id.setError("Enter Email Id");
        } else {
            email_id.setError(null);
        }

        if (dob.getText().toString().trim().length() == 0) {
            ++count;
            dob.setError("DOB is Required");
        } else {
            dob.setError(null);
        }

        if(Patterns.EMAIL_ADDRESS.matcher(email_id.getText().toString().trim()).matches()){
            email_id.setError(null);
        }else {
            email_id.setError("Invalid Email");
            ++count;
        }

        if(pincode.getText().toString().trim().length()==0){

        }else {
            if (pincode.getText().toString().trim().length()<6) {
                ++count;
                pincode.setError("Enter  Valid Pincode");
            } else {
                if(invalid.equalsIgnoreCase("1")){
                    ++count;
                    pincode.setError("Enter  Valid Pincode");
                }else {
                    pincode.setError(null);
                }



            }
        }






        if (count == 0) {
            progressDialog = ProgressDialog.show(this, "",
                    "Saving. Please wait...", true);

            RequestQueue rq = Volley.newRequestQueue(FamilyDataActivity.this,
                    new HurlStack(null, getSocketFactory()));
            String url = con.base_url+"/api/employee/update/family-member";
            StringRequest smr = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                progressDialog.dismiss();

                                Log.d("invalid pincode",response);

                                JSONObject jsonObject = new JSONObject(response);
                                String errorCode = String.valueOf(jsonObject.getBoolean("status"));
                                String msg = jsonObject.getString("message");


                                if (errorCode.equalsIgnoreCase("true")) {

                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(FamilyDataActivity.this);

                                    alertDialog.setTitle("Success");
                                    alertDialog.setMessage(msg);
                                    alertDialog.setIcon(R.drawable.checkmark);
                                    alertDialog.setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent = new Intent(FamilyDataActivity.this, FamilyMemberActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                            startActivity(intent);
                                        }
                                    }).show();

//                                    new AlertDialog.Builder(FamilyDataActivity.this)
//                                            .setTitle("Success")
//                                            .setMessage("SuccessFully Updated!")
//                                            .setIcon(android.R.drawable.ic_dialog_alert)
//                                            .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                                                @Override
//                                                public void onClick(DialogInterface dialogInterface, int i) {
//
//                                                    context.startActivity(new Intent(FamilyDataActivity.this, FamilyMemberActivity.class));
//
//                                                }
//                                            }).show();

                                } else {
                                    new AlertDialog.Builder(FamilyDataActivity.this)
                                            .setTitle("Alert")
                                            .setMessage(msg)
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .setNegativeButton(android.R.string.ok, null).show();
                                }
                            } catch (Exception e) {
                                progressDialog.dismiss();

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

            if (m_date.getVisibility() == View.VISIBLE) {
                params.put("marriage_date", m_date.getText().toString());
                Log.d("marriage_date", m_date.getText().toString());
            } else {
//                smr.addStringParam("marriage_date", " ");
//                Log.d("marriage_date", " ");
            }

            params.put("relation_id", relationn);
            params.put("update_id", family_id);
            params.put("first_name", first_name.getText().toString());
            params.put("last_name", last_name.getText().toString());
            params.put("dob", dob.getText().toString());
            params.put("member_mob_no", contact.getText().toString());
            params.put("member_email", email_id.getText().toString());

            params.put("state", state.getText().toString());
            params.put("city", city.getText().toString());

            if(pincode.getText().toString().length()==0){

            }else {
                params.put("pincode", pincode.getText().toString());
            }

            if(building.getText().toString().length()==0){

            }else {
                params.put("address", building.getText().toString());
            }


            smr.setParams(params);



            rq.add(smr);

        }


    }




    public void Delete_Data() {

        progressDialog = ProgressDialog.show(this, "",
                "Saving. Please wait...", true);

        RequestQueue rq = Volley.newRequestQueue(FamilyDataActivity.this,
                new HurlStack(null, getSocketFactory()));
        String url = con.base_url+"/api/employee/delete/family-member"+family_id;
        StringRequest postRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progressDialog.dismiss();

                            Log.d("delete_issue",response);

                            JSONObject js=new JSONObject(response);

                          String status= String.valueOf(js.getBoolean("status"));
                          String message=js.getString("message");
                          if(status.equalsIgnoreCase("true")){

                              AlertDialog.Builder alertDialog = new AlertDialog.Builder(FamilyDataActivity.this);
                              alertDialog.setTitle("Success");
                              alertDialog.setMessage(response);
                              alertDialog.setIcon(R.drawable.checkmark);
                              alertDialog.setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                  @Override
                                  public void onClick(DialogInterface dialogInterface, int i) {
                                      Intent intent = new Intent(FamilyDataActivity.this, FamilyMemberActivity.class);
                                      intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                      startActivity(intent);
                                  }
                              }).show();

                          }else {
                              new AlertDialog.Builder(FamilyDataActivity.this)
                                      .setTitle("Error?")
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








        rq.add(postRequest);






    }



    public void setProfileDet() {

        String url = con.base_url+"/api/admin/user";

        RequestQueue mRequestQueue = Volley.newRequestQueue(FamilyDataActivity.this,
                new HurlStack(null, getSocketFactory()));
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



                            String pincode_input = explrObject.getString("pincode");
                            String building_input = explrObject.getString("address");
                            String city_input = explrObject.getString("city");
                            String state_input = explrObject.getString("state");


                            if (pincode_input .equalsIgnoreCase("null")  || pincode_input.isEmpty()) {

                            }else {
                                pincode.setText(pincode_input);
                            }


                        if (building_input .equalsIgnoreCase("null")  || building_input.isEmpty()) {

                        }else {
                            building.setText(building_input);
                        }


                        if (city_input .equalsIgnoreCase("null")  || city_input.isEmpty()) {

                        }else {
                            city.setText(city_input);
                        }


                        if (state_input .equalsIgnoreCase("null")  || state_input.isEmpty()) {

                        }else {
                            state.setText(state_input);
                        }






                    }



                } catch (JSONException e) {
                    e.printStackTrace();
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
        params.put("user_id", user_id);
        params.put("master_user_type_id", "5");


        mStringRequest.setParams(params);
        mRequestQueue.add(mStringRequest);



    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }



    private void saveBankDet() {

        progressDialog = ProgressDialog.show(this, "",
                "Saving. Please wait...", true);

        RequestQueue rq = Volley.newRequestQueue(FamilyDataActivity.this,
                new HurlStack(null, getSocketFactory()));
        String url = con.base_url + "/edit";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {


                            Log.d("response data",response);

                            progressDialog.dismiss();


                            JSONObject jsonObject=new JSONObject(response);
                            String check=jsonObject.getString("check");



                            if (check.equals("N")) {
                                delete.setVisibility(View.GONE);
                                clear.setVisibility(View.GONE);


                            }
                            else {
                                delete.setVisibility(View.VISIBLE);
                                clear.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
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

        /* smr.addStringParam("emp_id", emp_id);*/




        HashMap<String, String> params = new HashMap<>();


        params.put("family_id", family_id);

        smr.setParams(params);
        rq.add(smr);




        rq.add(smr);
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

            HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {

                    Log.e("CipherUsed", session.getCipherSuite());
                    return hostname.compareTo("https://uat.lifekaplan-eb.com/")==0; //The Hostname of your server

                }
            };


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

