package com.palm.newbenefit.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.palm.newbenefit.Adapter.MultiAdapter;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.ApiConfig.DateTimeFormater;
import com.palm.newbenefit.ApiConfig.MyDateListener;
import com.palm.newbenefit.ApiConfig.Utils;
import com.palm.newbenefit.Module.Employee;
import com.palm.newbenefit.Module.SpinnerModal;
import com.kmd.newbenefit.R;
import com.palm.tatarewamp.SslData.NullHostNameVerifier;
import com.google.android.material.snackbar.Snackbar;

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

public class HealthCheckupActivity extends AppCompatActivity  {
TextView thumbnail;
    private SimpleDateFormat dateFormatter;
    Calendar newCalendar;
    String token;
    String employer_id_data;
    ProgressDialog progressDialog = null;
    Constants con;
    Context context;
    String page_type;
String employee_id=null;
    String data="0";
    String employer_id;
    private int mYear, mMonth, mDay, mHour, mMinute,am_pm;
    String invalid="1";
    Spinner policy_type,policy_name;
    TextView relation_type;
    EditText member_name,mobile_no,email,health_date,alter_date,address_one,address_two,pincode,state,city;
    EditText health_time,alter_time;
    Button submit,submit_two;
    String emp_id;
    private RecyclerView recyclerView;
    private ArrayList<Employee> employees = new ArrayList<>();
    private MultiAdapter adapter;
    private AppCompatButton btnGetSelected;
    String member_names,emails,contact_nos,pincodes,adress1,adress2,health_dates,alter_dates,relations;
    String health_date_st,health_time_st;
    String alter_date_st,alter_time_st;
    String user_id;
    ArrayList<String> bank_city = null;
    ArrayList<SpinnerModal> bank_cityList = null;
    ArrayAdapter<SpinnerModal> bank_cityAdapter = null;
    ArrayList<SpinnerModal> bank_nameList = null;
    ArrayAdapter<SpinnerModal> bank_nameAdapter = null;
    ArrayList<String> bank_name = null;

    String city_id,id;
    String member_mapping_id_one,member_mapping_id_two;
    String  state_id ,state_id_two,city_id_two;
    LinearLayout    policy_type_lin,relation_lin,policy_name_lin;
    Button group_cover,voluntary_cover;
    String one_member="",two_member="";

    EditText member_name_two,mobile_no_two,email_two,health_date_two,alter_date_two,address_one_two,
            address_two_two,pincode_two,state_two,city_two;
    EditText health_time_two,alter_time_two;
    LinearLayout two_member_lin,one_member_lin;
    CardView cards;
    List<String> ob = null;
    List<String> oba = null;
    ScrollView scrolling;
    int day = 0;
    int month = 0;
    int year = 0;
    String _year;
    String _month, _day;
    int day_a = 0;
    int month_a = 0;
    int year_a= 0;

    int day_b ;
    int month_b;
    int year_b ;

    ImageView info_text;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_checkup);

        dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        SharedPreferences prefs = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);

        token = prefs.getString("api_token", null);
        user_id = prefs.getString("user_id", null);



        newCalendar = Calendar.getInstance();
        con = new Constants();
        context = getApplicationContext();
        thumbnail=findViewById(R.id.thumbnail);
        policy_type=findViewById(R.id.policy_type);
        policy_name=findViewById(R.id.policy_name);
        relation_type=findViewById(R.id.relation_type);


        member_name=findViewById(R.id.member_name);
        mobile_no=findViewById(R.id.mobile_no);
        email=findViewById(R.id.email);
        health_date=findViewById(R.id.health_date);
        alter_date=findViewById(R.id.alter_date);

        health_time=findViewById(R.id.health_time);
        alter_time=findViewById(R.id.alter_time);


        address_one=findViewById(R.id.address_one);
        address_two=findViewById(R.id.address_two);
        pincode=findViewById(R.id.pincode);
        state=findViewById(R.id.state);
        city=findViewById(R.id.city);
        info_text=findViewById(R.id.info_text);
        member_name_two=findViewById(R.id.member_name_two);
        mobile_no_two=findViewById(R.id.mobile_no_two);
        email_two=findViewById(R.id.email_two);
        health_date_two=findViewById(R.id.health_date_two);
        alter_date_two=findViewById(R.id.alter_date_two);

        health_time_two=findViewById(R.id.health_time_two);
        alter_time_two=findViewById(R.id.alter_time_two);


        address_one_two=findViewById(R.id.address_one_two);
        address_two_two=findViewById(R.id.address_two_two);
        pincode_two=findViewById(R.id.pincode_two);
        state_two=findViewById(R.id.state_two);
        city_two=findViewById(R.id.city_two);

        policy_type_lin=findViewById(R.id.policy_type_lin);
        relation_lin=findViewById(R.id.relation_lin);
        policy_name_lin=findViewById(R.id.policy_name_lin);
        submit=findViewById(R.id.submit);

        group_cover=findViewById(R.id.group_cover);
        voluntary_cover=findViewById(R.id.voluntary_cover);
        scrolling=findViewById(R.id.scrolling);

        two_member_lin=findViewById(R.id.two_member_lin);
        one_member_lin=findViewById(R.id.one_member_lin);
        cards=findViewById(R.id.cards);
        submit_two=findViewById(R.id.submit_two);
        setProfileDet();
        GetEmployeeId();
        Intent intent = getIntent();

       page_type = intent.getStringExtra("page_type");


        info_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

     if(page_type.equalsIgnoreCase("edit")){
         member_names = intent.getStringExtra("member_name");
         emails = intent.getStringExtra("email");
         contact_nos = intent.getStringExtra("contact_no");
         relations = intent.getStringExtra("relations");
         pincodes = intent.getStringExtra("pincode");
         adress1 = intent.getStringExtra("adress1");
         adress2 = intent.getStringExtra("adress2");
         health_dates = intent.getStringExtra("health_date");
         alter_dates = intent.getStringExtra("alter_date");
         id = intent.getStringExtra("id");
         
         member_name.setText(member_names);
         email.setText(emails);
         if(contact_nos.equalsIgnoreCase("0")||contact_nos.equalsIgnoreCase("null")){
             mobile_no.setText(null);
         }else {
             mobile_no.setText(contact_nos);
         }
         pincode.setText(pincodes);

         getPincode();
         address_one.setText(adress1);
         
         if(adress2.equalsIgnoreCase("")||adress2.equalsIgnoreCase("null")){
             
         }else {
             address_two.setText(adress2);
         }
       


         thumbnail.setText("Edit Health Checkup");
         policy_type.setVisibility(View.GONE);
         policy_name.setVisibility(View.GONE);
         relation_type.setVisibility(View.GONE);

         policy_type_lin.setVisibility(View.GONE);
         policy_name_lin.setVisibility(View.GONE);
         relation_lin.setVisibility(View.GONE);
         one_member_lin.setVisibility(View.VISIBLE);
         two_member_lin.setVisibility(View.GONE);




         String date = health_dates;
         String[] parts = date.split(" ");

         health_date_st = parts[0];
         health_time_st = parts[1];


         try{
             String strCurrentDate = health_date_st;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
             Date newDate = format.parse(strCurrentDate);

             format = new SimpleDateFormat("dd-MM-yyyy");

             health_date.setText(format.format(newDate));
         }catch (Exception e){
             health_date.setText(health_date_st);
         }


         String dates = alter_dates;
         String[] partss = dates.split(" ");


         alter_date_st= partss[0];
         alter_time_st = partss[1];

         try{

             String strCurrentDate = alter_time_st;
             SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
             Date newDate = format.parse(strCurrentDate);

             format = new SimpleDateFormat("hh:mm:ss a");

            alter_time.setText(format.format(newDate));

         }catch (Exception e){

             alter_time.setText(alter_time_st);
         }



         try{

             String strCurrentDate = health_time_st;
             SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
             Date newDate = format.parse(strCurrentDate);

             format = new SimpleDateFormat("hh:mm:ss a");

             health_time.setText(format.format(newDate));

         }catch (Exception e){

             health_time.setText(health_time_st);
         }



         try{

             String strCurrentDate = alter_date_st;
             SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
             Date newDate = format.parse(strCurrentDate);

             format = new SimpleDateFormat("dd-MM-yyyy");

             alter_date.setText(format.format(newDate));
         }catch (Exception e){
             alter_date.setText(alter_date_st);
         }

     }else {
         thumbnail.setText("Add Health Checkup");
         policy_type.setVisibility(View.VISIBLE);
         policy_name.setVisibility(View.VISIBLE);
         relation_type.setVisibility(View.VISIBLE);
         policy_type_lin.setVisibility(View.VISIBLE);
         policy_name_lin.setVisibility(View.VISIBLE);
         relation_lin.setVisibility(View.VISIBLE);
         one_member_lin.setVisibility(View.VISIBLE);
         two_member_lin.setVisibility(View.VISIBLE);

     }

        setBankDet();

        if(pincode.getText().toString().trim().length()>0){

            if (isNetworkAvailable()) {
                getPincode();
            }else {
                new AlertDialog.Builder(HealthCheckupActivity.this)
                        .setTitle("Error?")
                        .setMessage("Please Check Your Internet Connection")
                        .setIcon(android.R.drawable.btn_dialog)
                        .setNegativeButton(android.R.string.ok, null).show();
            }

        }


        if(pincode_two.getText().toString().trim().length()>0){

            if (isNetworkAvailable()) {
                getPincode_two();
            }else {
                new AlertDialog.Builder(HealthCheckupActivity.this)
                        .setTitle("Error?")
                        .setMessage("Please Check Your Internet Connection")
                        .setIcon(android.R.drawable.btn_dialog)
                        .setNegativeButton(android.R.string.ok, null).show();
            }

        }

        group_cover.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                group_cover.setBackgroundResource(R.drawable.nav_back_tab);
                voluntary_cover.setBackgroundResource(R.drawable.tab_curve);
                voluntary_cover.setTextColor(Color.BLACK);
                group_cover.setTextColor(Color.WHITE);

                getData(one_member,"one");

                one_member_lin.setVisibility(View.VISIBLE);
                two_member_lin.setVisibility(View.GONE);


            }
        });


        voluntary_cover.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                voluntary_cover.setBackgroundResource(R.drawable.nav_back_tab);
                group_cover.setBackgroundResource(R.drawable.tab_curve);
                group_cover.setTextColor(Color.BLACK);
                voluntary_cover.setTextColor(Color.WHITE);


                getData(two_member,"two");
                one_member_lin.setVisibility(View.GONE);
                two_member_lin.setVisibility(View.VISIBLE);

            }
        });

        if(relation_type.getText().toString().equalsIgnoreCase("")){


            if(page_type.equalsIgnoreCase("edit")){

                cards.setVisibility(View.GONE);
                one_member_lin.setVisibility(View.VISIBLE);
                two_member_lin.setVisibility(View.GONE);
            }else {
                cards.setVisibility(View.GONE);
                one_member_lin.setVisibility(View.GONE);
                two_member_lin.setVisibility(View.GONE);
            }



        }else {

            cards.setVisibility(View.VISIBLE);


        }



        if(page_type.equalsIgnoreCase("add")){

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy,hh:mm:ss aa", Locale.getDefault());
            String currentDateandTime = sdf.format(new Date());


            String date = currentDateandTime;
            String[] parts = date.split(",");

            health_date_st = parts[0];
            health_time_st = parts[1];


            try{
                String strCurrentDate = health_date_st;
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                Date newDate = format.parse(strCurrentDate);

                format = new SimpleDateFormat("dd-MM-yyyy");

                alter_date.setText(format.format(newDate));
                alter_date_two.setText(format.format(newDate));

                health_date_two.setText(format.format(newDate));
                health_date.setText(format.format(newDate));
            }catch (Exception e){
                health_date_two.setText(health_date_st);
                health_date.setText(health_date_st);
                alter_date_two.setText(health_date_st);
                alter_date.setText(health_date_st);
            }



            try{

                String strCurrentDate = health_time_st;
                SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss aa");
                Date newDate = format.parse(strCurrentDate);

                format = new SimpleDateFormat("hh:mm:ss a");

                health_time.setText(format.format(newDate));
                health_time_two.setText(format.format(newDate));

                alter_time.setText(format.format(newDate));
                alter_time_two.setText(format.format(newDate));
            }catch (Exception e){
                health_time_two.setText(health_time_st);
                health_time.setText(health_time_st);
                alter_time_two.setText(health_time_st);
                alter_time.setText(health_time_st);

            }






        }



        health_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Utils.desablePreDatePicker(HealthCheckupActivity.this, new MyDateListener() {
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
                        health_date.setText(date);


                        //travel_request_todateshow.setText("");
                    }
                });

            }
        });

        alter_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                am_pm = c.get(Calendar.AM_PM);


                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(HealthCheckupActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                try{


                                    String AM_PM ;
                                    if(hourOfDay < 12) {
                                        AM_PM = "AM";
                                    } else {
                                        AM_PM = "PM";
                                    }



                                    try {

                                        String strCurrentDate = hourOfDay + ":" + minute;
                                        SimpleDateFormat format = new SimpleDateFormat("hh:mm");
                                        Date newDate = format.parse(strCurrentDate);

                                        format = new SimpleDateFormat("hh:mm:ss");

                                        String time=format.format(newDate);

                                        alter_time.setText(time + " " + AM_PM);
                                    } catch (Exception e) {
                                        alter_time.setText(hourOfDay + ":" + minute);
                                    }



                                }catch (Exception e){
                                    alter_time.setText(hourOfDay + ":" + minute);
                                }





                            }
                        }, mHour, mMinute,false);
                timePickerDialog.show();
            }
        });


        health_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                am_pm = c.get(Calendar.AM_PM);



                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(HealthCheckupActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {


                                try{



                                    String AM_PM ;
                                    if(hourOfDay < 12) {
                                        AM_PM = "AM";
                                    } else {
                                        AM_PM = "PM";
                                    }

                                    try {

                                        String strCurrentDate = hourOfDay + ":" + minute;
                                        SimpleDateFormat format = new SimpleDateFormat("hh:mm");
                                        Date newDate = format.parse(strCurrentDate);

                                        format = new SimpleDateFormat("hh:mm:ss");

                                        String time=format.format(newDate);

                                        health_time.setText(time + " " + AM_PM);
                                    } catch (Exception e) {
                                        health_time.setText(hourOfDay + ":" + minute);
                                    }
                                }catch (Exception e){
                                    health_time.setText(hourOfDay + ":" + minute);
                                }
                            }
                        }, mHour, mMinute,false);
                timePickerDialog.show();
            }
        });



        health_time_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                am_pm = c.get(Calendar.AM_PM);



                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(HealthCheckupActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {


                                try{


                                    String AM_PM ;
                                    if(hourOfDay < 12) {
                                        AM_PM = "AM";
                                    } else {
                                        AM_PM = "PM";
                                    }
                                    try {

                                        String strCurrentDate = hourOfDay + ":" + minute;
                                        SimpleDateFormat format = new SimpleDateFormat("hh:mm");
                                        Date newDate = format.parse(strCurrentDate);

                                        format = new SimpleDateFormat("hh:mm:ss");

                                        String time=format.format(newDate);

                                        health_time_two.setText(time + " " + AM_PM);
                                    } catch (Exception e) {
                                        health_time_two.setText(hourOfDay + ":" + minute);
                                    }
                                }catch (Exception e){
                                    health_time_two.setText(hourOfDay + ":" + minute);
                                }

                            }
                        }, mHour, mMinute,false);
                timePickerDialog.show();
            }
        });

        alter_time_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                am_pm = c.get(Calendar.AM_PM);



                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(HealthCheckupActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {


                                try{


                                    String AM_PM ;
                                    if(hourOfDay < 12) {
                                        AM_PM = "AM";
                                    } else {
                                        AM_PM = "PM";
                                    }

                                    try {

                                        String strCurrentDate = hourOfDay + ":" + minute;
                                        SimpleDateFormat format = new SimpleDateFormat("hh:mm");
                                        Date newDate = format.parse(strCurrentDate);

                                        format = new SimpleDateFormat("hh:mm:ss");

                                        String time=format.format(newDate);

                                        alter_time_two.setText(time + " " + AM_PM);
                                    } catch (Exception e) {
                                        alter_time_two.setText(hourOfDay + ":" + minute);
                                    }
                                }catch (Exception e){
                                    alter_time_two.setText(hourOfDay + ":" + minute);
                                }

                            }
                        }, mHour, mMinute,false);
                timePickerDialog.show();
            }
        });


        alter_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Utils.desablePreDatePicker(HealthCheckupActivity.this, new MyDateListener() {
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
                        alter_date.setText(date);


                        //travel_request_todateshow.setText("");
                    }
                });

            }
        });


        health_date_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.desablePreDatePicker(HealthCheckupActivity.this, new MyDateListener() {
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
                        health_date_two.setText(date);


                        //travel_request_todateshow.setText("");
                    }
                });

            }
        });
        alter_date_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Utils.desablePreDatePicker(HealthCheckupActivity.this, new MyDateListener() {
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
                        alter_date_two.setText(date);


                        //travel_request_todateshow.setText("");
                    }
                });

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

                    RequestQueue rq = Volley.newRequestQueue(HealthCheckupActivity.this,
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
                                            new AlertDialog.Builder(HealthCheckupActivity.this)
                                                    .setTitle("Invalid Pincode?")
                                                    .setMessage("Please Check the Pincode!")
                                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                                    .setNegativeButton(android.R.string.ok, null).show();

                                        } else  {

                                            invalid="0";
                                            Log.d("response",response);
                                            JSONArray jsonObj=js.getJSONArray("data");

                                            for (int i = 0; i < jsonObj.length(); i++) {
                                                JSONObject explrObject = jsonObj.getJSONObject(i);

                                                city.setText(explrObject.getString("city_name"));
                                                state.setText(explrObject.getString("state_name"));
                                                city_id=explrObject.getString("city_id");
                                                state_id=explrObject.getString("state_id");

                                            }

                                        }




                                    } catch (Exception e) {

                                        e.printStackTrace();
                                       /* new AlertDialog.Builder(HealthCheckupActivity.this)
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
            new AlertDialog.Builder(HealthCheckupActivity.this)
                    .setTitle("Error?")
                    .setMessage("Please Check Your Internet Connection")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
        }



        TextWatcher fieldValidatorTextWatchera = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (filterLongEnough()) {

                    RequestQueue rq = Volley.newRequestQueue(HealthCheckupActivity.this,
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

                                            city_two.setText("");
                                            state_two.setText("");
                                            new AlertDialog.Builder(HealthCheckupActivity.this)
                                                    .setTitle("Invalid Pincode?")
                                                    .setMessage("Please Check the Pincode!")
                                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                                    .setNegativeButton(android.R.string.ok, null).show();

                                        } else  {

                                            invalid="0";
                                            Log.d("response",response);
                                            JSONArray jsonObj=js.getJSONArray("data");

                                            for (int i = 0; i < jsonObj.length(); i++) {
                                                JSONObject explrObject = jsonObj.getJSONObject(i);

                                                city_two.setText(explrObject.getString("city_name"));
                                                state_two.setText(explrObject.getString("state_name"));
                                                city_id_two=explrObject.getString("city_id");
                                                state_id_two=explrObject.getString("state_id");

                                            }

                                        }




                                    } catch (Exception e) {

                                        e.printStackTrace();
                                       /* new AlertDialog.Builder(HealthCheckupActivity.this)
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

                    params.put("pincode", pincode_two.getText().toString().trim());

                    smr.setParams(params);
                    rq.add(smr);


                } else {
                    city_two.setText("");
                    state_two.setText("");
                }
            }

            private boolean filterLongEnough() {
                return pincode_two.getText().toString().trim().length() > 5;
            }
        };



        if (isNetworkAvailable()) {
            pincode_two.addTextChangedListener(fieldValidatorTextWatchera);

        }else {
            new AlertDialog.Builder(HealthCheckupActivity.this)
                    .setTitle("Error?")
                    .setMessage("Please Check Your Internet Connection")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
        }


        policy_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // SpinnerModal bank_city_modal = (SpinnerModalFamilyData) adapterView.getSelectedItem();
                SpinnerModal bank_name_modal = (SpinnerModal) adapterView.getSelectedItem();
                getBankNamenumber(bank_name_modal.getBank_id());


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
        policy_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // SpinnerModal bank_city_modal = (SpinnerModalFamilyData) adapterView.getSelectedItem();
                SpinnerModal bank_name_modal = (SpinnerModal) adapterView.getSelectedItem();
                    if((ob.contains(bank_name_modal.getSelKey())
                    && oba.contains(employee_id))){

                        Snackbar snackbar = Snackbar
                                .make(scrolling, "Health check-up can be booked for any 2 family members only!", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }else {
                        getBankCity();
                    }



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        relation_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog();

            }
        });


//        relation_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                // SpinnerModal bank_city_modal = (SpinnerModalFamilyData) adapterView.getSelectedItem();
//                SpinnerModal bank_name_modal = (SpinnerModal) adapterView.getSelectedItem();
//
//                if(bank_name_modal.getSelValue().equalsIgnoreCase("Select Member")){
//
//                }else {
//                    getData();
//                    member_name.setText(bank_name_modal.getBank_id());
//                    mobile_no.setText(bank_name_modal.getMinage());
//                    email.setText(bank_name_modal.getMinvalue());
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



        if(!two_member.equalsIgnoreCase("")){
            submit.setText("Save & Next");
        }else {
            submit.setText("submit");
        }


        submit_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()) {



                        AddSendData();


                }else {
                    new AlertDialog.Builder(HealthCheckupActivity.this)
                            .setTitle("Error?")
                            .setMessage("Please Check Your Internet Connection")
                            .setIcon(android.R.drawable.btn_dialog)
                            .setNegativeButton(android.R.string.ok, null).show();
                }



            }
        });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()) {

                    if(page_type.equalsIgnoreCase("edit")){
                        SendData();
                    }else {

                        if(!two_member.equalsIgnoreCase("")){


                            int count = 0;

                            if (member_name.getText().toString().trim().length() == 0) {
                                ++count;
                                member_name.setError("Name is Required");
                            } else {
                                member_name.setError(null);
                            }
                            if (mobile_no.getText().toString().trim().isEmpty() || !isValid(mobile_no.getText().toString().trim()) ||
                                    mobile_no.getText().toString().length() < 10 ||
                                    mobile_no.getText().toString().trim().length() > 13) {
                                ++count;
                                mobile_no.setError("Invalid Mobile");
                            } else {
                                mobile_no.setError(null);
                            }

                            if (address_one.getText().toString().trim().length() == 0) {
                                ++count;
                                address_one.setError("Address is Required");
                            } else {
                                address_one.setError(null);
                            }



                            if (email.getText().toString().trim().length() == 0) {
                                ++count;
                                email.setError("Enter Email Id");
                            } else {
                                email.setError(null);
                            }

//            if (health_date.getText().toString().trim().length() == 0) {
//                ++count;
//                health_date.setError("date is Required");
//            } else {
//                health_date.setError(null);
//            }
//
//
//            if (alter_date.getText().toString().trim().length() == 0) {
//                ++count;
//                alter_date.setError("date is Required");
//            } else {
//                alter_date.setError(null);
//            }

                            if(Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches()){
                                email.setError(null);
                            }else {
                                email.setError("Invalid Email");
                                ++count;
                            }



                            if(pincode.getText().toString().trim().length()==0){
                                pincode.setError("Enter  Valid Pincode");
                                ++count;
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

                            if(count==0){
                                voluntary_cover.setBackgroundResource(R.drawable.nav_back_tab);
                                group_cover.setBackgroundResource(R.drawable.tab_curve);
                                group_cover.setTextColor(Color.BLACK);
                                voluntary_cover.setTextColor(Color.WHITE);


                                getData(two_member,"two");
                                one_member_lin.setVisibility(View.GONE);
                                two_member_lin.setVisibility(View.VISIBLE);
                            }

                        }else {
                            AddSendData();
                        }


                    }

                }else {
                    new AlertDialog.Builder(HealthCheckupActivity.this)
                            .setTitle("Error?")
                            .setMessage("Please Check Your Internet Connection")
                            .setIcon(android.R.drawable.btn_dialog)
                            .setNegativeButton(android.R.string.ok, null).show();
                }



            }
        });



    }

    private void showDialog() {
        Dialog myDialog;


        myDialog = new Dialog(HealthCheckupActivity.this);

        myDialog.setContentView(R.layout.multispinner_layout);





        this.btnGetSelected = (AppCompatButton) myDialog.findViewById(R.id.btnGetSelected);
        this.recyclerView = (RecyclerView) myDialog.findViewById(R.id.recyclerView);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter = new MultiAdapter(this, employees);
        recyclerView.setAdapter(adapter);

        createList();

        btnGetSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter.getSelected().size() > 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < adapter.getSelected().size(); i++) {
                        stringBuilder.append(adapter.getSelected().get(i).getRelation_name() + " "+
                                adapter.getSelected().get(i).getName());
                        stringBuilder.append("\n");

                        if(adapter.getSelected().size()==2){
                            group_cover.setText(adapter.getSelected().get(0).getRelation_name());
                            voluntary_cover.setText(adapter.getSelected().get(1).getRelation_name());


                            member_name.setText(adapter.getSelected().get(0).getName());



                            member_name_two.setText(adapter.getSelected().get(1).getName());


                            if(adapter.getSelected().get(0).getMobile().equalsIgnoreCase("0")
                                    || adapter.getSelected().get(0).getMobile().equalsIgnoreCase("null")){
                                mobile_no.setText(null);
                            }else {
                                mobile_no.setText(adapter.getSelected().get(0).getMobile());
                            }

                            if(adapter.getSelected().get(0).getEmail().equalsIgnoreCase("0")
                                    || adapter.getSelected().get(0).getEmail().equalsIgnoreCase("null")){
                                email.setText(null);
                            }else {
                                email.setText(adapter.getSelected().get(0).getEmail());


                            }



                            if(adapter.getSelected().get(1).getMobile().equalsIgnoreCase("0")
                            || adapter.getSelected().get(1).getMobile().equalsIgnoreCase("null")){
                                mobile_no_two.setText(null);
                            }else {
                                mobile_no_two.setText(adapter.getSelected().get(1).getMobile());
                            }

                            if(adapter.getSelected().get(1).getEmail().equalsIgnoreCase("0")
                                    || adapter.getSelected().get(1).getEmail().equalsIgnoreCase("null")){
                                email_two.setText(null);
                            }else {
                                email_two.setText(adapter.getSelected().get(1).getEmail());

                            }


                          //  member_mapping_id_one=adapter.getSelected().get(0).getMember_id()

                             one_member=adapter.getSelected().get(0).getMember_id();
                            two_member=adapter.getSelected().get(1).getMember_id();
                            cards.setVisibility(View.VISIBLE);
                            Log.d("relation",one_member);
                            Log.d("relation",two_member);



                            getData(one_member,"one");

                            group_cover.setVisibility(View.VISIBLE);
                            voluntary_cover.setVisibility(View.VISIBLE);


                            group_cover.setBackgroundResource(R.drawable.nav_back_tab);
                            voluntary_cover.setBackgroundResource(R.drawable.tab_curve);
                            voluntary_cover.setTextColor(Color.BLACK);
                            group_cover.setTextColor(Color.WHITE);

                            getData(one_member,"one");

                            one_member_lin.setVisibility(View.VISIBLE);
                            two_member_lin.setVisibility(View.GONE);


                            submit.setText("Save & Next");
                            submit_two.setText("Submit");


                        }else {

                            member_name.setText(adapter.getSelected().get(0).getName());
                            mobile_no.setText(adapter.getSelected().get(0).getMobile());
                            email.setText(adapter.getSelected().get(0).getEmail());

                            group_cover.setText(adapter.getSelected().get(0).getName());
                            one_member=adapter.getSelected().get(0).getMember_id();
                            getData(one_member,"one");
                            Log.d("relation",one_member);


                           // Log.d("relation",two_member);


                            group_cover.setBackgroundResource(R.drawable.nav_back_tab);
                            voluntary_cover.setBackgroundResource(R.drawable.tab_curve);
                            voluntary_cover.setTextColor(Color.BLACK);
                            group_cover.setTextColor(Color.WHITE);

                            getData(one_member,"one");

                            one_member_lin.setVisibility(View.VISIBLE);
                            two_member_lin.setVisibility(View.GONE);

                            group_cover.setVisibility(View.VISIBLE);
                            voluntary_cover.setVisibility(View.GONE);
                        }

                    }
                    relation_type.setText(stringBuilder.toString().trim());




                } else {
                    relation_type.setText("No  Selection");
                    group_cover.setVisibility(View.GONE);
                    voluntary_cover.setVisibility(View.GONE);
                }


                myDialog.dismiss();
            }
        });




        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(myDialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        myDialog.getWindow().setAttributes(layoutParams);
        myDialog.show();


    }

    private void createList() {



        RequestQueue rq = Volley.newRequestQueue(HealthCheckupActivity.this,
                new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        String url = con.base_url+"/api/admin/get/emp_member";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("emp_member",response);
                            employees = new ArrayList<>();
                            JSONObject jsonArra = new JSONObject(response);

                            String status= String.valueOf(jsonArra.getBoolean("status"));

                            if(status.equalsIgnoreCase("false")){

                                bank_city = new ArrayList<>();
                                bank_cityList = new ArrayList<>();
                                bank_cityList.add(new SpinnerModal("", "Select Member"));
                                //bank_city.add("");

                            }else {
                                JSONArray array=jsonArra.getJSONArray("data");
                                bank_city = new ArrayList<>();
                                bank_cityList = new ArrayList<>();
                                bank_cityList.add(new SpinnerModal("", "Select Member"));
                                // bank_city.add("");


                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject jsonObj = array.getJSONObject(i);



                                            employees.add(new Employee(
                                            jsonObj.getString("relation_name")
                                            , jsonObj.getString("member_id")
                                            , jsonObj.getString("emp_code")
                                            , jsonObj.getString("ecard_url")
                                            , jsonObj.getString("tpa_member_id")
                                            ,jsonObj.getString("tpa_emp_id")
                                           ,jsonObj.getString("tpa_member_name")
                                            ,jsonObj.getString("policy_no")
                                            ,jsonObj.getString("start_date")
                                            ,jsonObj.getString("end_date")
                                            ,jsonObj.getString("email")
                                            ,jsonObj.getString("mobile")
                                            ,jsonObj.getString("name")));


                                  //  employees.setName("Employee " + (i));

                                    bank_cityList.add(new SpinnerModal(String.valueOf(jsonObj.getInt("member_id")),
                                            jsonObj.getString("relation_name"),  jsonObj.getString("name")
                                            ,  jsonObj.getString("mobile"),  jsonObj.getString("email")
                                            ,jsonObj.getString("name")));
                                    bank_city.add(jsonObj.getString("relation_name"));
                                }

                            }
                            adapter.setEmployees(employees);
                            // relation_type.setItems(bank_city);
//                            bank_cityAdapter = new ArrayAdapter<SpinnerModal>(HealthCheckupActivity.this, R.layout.spinner_item, bank_cityList);
//                            bank_cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                            relation_type.setAdapter(bank_cityAdapter);

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

        SpinnerModal bank_city_modal = (SpinnerModal) policy_name.getSelectedItem();




       /* smr.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        RequestQueue mRequestQueue = Volley.newRequestQueue(HealthCheckupActivity.this);
        mRequestQueue.add(smr);*/

        HashMap<String, String> params = new HashMap<>();
        params.put("policy_id", bank_city_modal.getSelKey());
        params.put("employee_id", emp_id);
        params.put("user_type_name", "Employee");


        Log.d("policy_id", bank_city_modal.getSelValue());

        smr.setParams(params);
        rq.add(smr);








//
//        for (int i = 0; i < 20; i++) {
//            Employee employee = new Employee();
//            employee.setName("Employee " + (i));
//
//            // for example to show at least one selection
////            if (i == 0) {
////                employee.setChecked(true);
////            }
//            //
//
//
//
//            //employee.setChecked(true);
//
//
//
//
//            employees.add(employee);
//        }

    }

    void GetEmployeeId(){
        String url = con.base_url+"/api/admin/user";

        RequestQueue mRequestQueue = Volley.newRequestQueue(HealthCheckupActivity.this,
                new HurlStack(null, getSocketFactory()));
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






                         employer_id = explrObject.getString("employee_id");

                        employer_id_data = explrObject.getString("employer_id");




                        if (employer_id != "null" && !employer_id.isEmpty()) {

                            employee_id=employer_id;

                            getBankName();




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
    public void getPincode() {


        RequestQueue rq = Volley.newRequestQueue(HealthCheckupActivity.this,
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
                                new AlertDialog.Builder(HealthCheckupActivity.this)
                                        .setTitle("Invalid Pincode?")
                                        .setMessage("Please Check the Pincode!")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setNegativeButton(android.R.string.ok, null).show();

                            } else  {

                                invalid="0";
                                Log.d("response",response);
                                JSONArray jsonObj=js.getJSONArray("data");

                                for (int i = 0; i < jsonObj.length(); i++) {
                                    JSONObject explrObject = jsonObj.getJSONObject(i);

                                    city.setText(explrObject.getString("city_name"));
                                    state.setText(explrObject.getString("state_name"));
                                    city_id=explrObject.getString("city_id");
                                    state_id=explrObject.getString("state_id");

                                }

                            }




                        } catch (Exception e) {

                            e.printStackTrace();
                                       /* new AlertDialog.Builder(HealthCheckupActivity.this)
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





    }

    public void getPincode_two() {


        RequestQueue rq = Volley.newRequestQueue(HealthCheckupActivity.this,
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

                                city_two.setText("");
                                state_two.setText("");
                                new AlertDialog.Builder(HealthCheckupActivity.this)
                                        .setTitle("Invalid Pincode?")
                                        .setMessage("Please Check the Pincode!")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setNegativeButton(android.R.string.ok, null).show();

                            } else  {

                                invalid="0";
                                Log.d("response",response);
                                JSONArray jsonObj=js.getJSONArray("data");

                                for (int i = 0; i < jsonObj.length(); i++) {
                                    JSONObject explrObject = jsonObj.getJSONObject(i);

                                    city_two.setText(explrObject.getString("city_name"));
                                    state_two.setText(explrObject.getString("state_name"));
                                    city_id_two=explrObject.getString("city_id");
                                    state_id_two=explrObject.getString("state_id");

                                }

                            }




                        } catch (Exception e) {

                            e.printStackTrace();
                                       /* new AlertDialog.Builder(HealthCheckupActivity.this)
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

        params.put("pincode", pincode_two.getText().toString().trim());

        smr.setParams(params);
        rq.add(smr);





    }

    private void getBankName() {
        //String url =con.base_url+"/api/employee/policies";
        String url =con.base_url+"/api/admin/get/policy/subtype";
        RequestQueue rq = Volley.newRequestQueue(HealthCheckupActivity.this,
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
                                    bank_nameList.add(new SpinnerModal(jsonObj.getString("policy_id"),
                                            jsonObj.getString("policy_sub_type_name"),jsonObj.getString("policy_sub_type_id")));
                                    bank_name.add(jsonObj.getString("policy_sub_type_name"));
                                }
                            }





                            bank_nameAdapter = new ArrayAdapter<SpinnerModal>(HealthCheckupActivity.this, R.layout.spinner_item, bank_nameList);
                            bank_nameAdapter.setDropDownViewResource(R.layout.spinner_item);


                            policy_type.setAdapter(bank_nameAdapter);
                            // policy_type_no.setAdapter(bank_nameAdapter);










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
        params.put("employer_id", employer_id_data);

        smr.setParams(params);
        rq.add(smr);
    }


    private void getBankNamenumber(final String set_bank_name) {
        String url =con.base_url+"/api/admin/get/policyno";
        RequestQueue rq = Volley.newRequestQueue(HealthCheckupActivity.this,
                new HurlStack(null, getSocketFactory()));

        //RequestQueue rq = Volley.newRequestQueue(HealthCheckupActivity.this, new HurlStack(null, getSocketFactory()));
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





                            bank_nameAdapter = new ArrayAdapter<SpinnerModal>(HealthCheckupActivity.this, R.layout.spinner_item, bank_nameList);
                            bank_nameAdapter.setDropDownViewResource(R.layout.spinner_item);



                            policy_name.setAdapter(bank_nameAdapter);



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
        params.put("employer_id", employer_id_data);
        params.put("policy_sub_type_id", set_bank_name);
        params.put("user_type_name", "Employee");

        smr.setParams(params);
        rq.add(smr);
    }


    public void getBankCity() {


         RequestQueue rq = Volley.newRequestQueue(HealthCheckupActivity.this, new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        String url = con.base_url+"/api/admin/get/emp_member";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("emp_member",response);

                            JSONObject jsonArra = new JSONObject(response);

                            String status= String.valueOf(jsonArra.getBoolean("status"));

                            if(status.equalsIgnoreCase("false")){

                                bank_city = new ArrayList<>();
                                bank_cityList = new ArrayList<>();
                                bank_cityList.add(new SpinnerModal("", "Select Member"));
                                //bank_city.add("");

                            }else {
                                JSONArray array=jsonArra.getJSONArray("data");
                                bank_city = new ArrayList<>();
                                bank_cityList = new ArrayList<>();
                                bank_cityList.add(new SpinnerModal("", "Select Member"));
                               // bank_city.add("");


                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject jsonObj = array.getJSONObject(i);

                                    bank_cityList.add(new SpinnerModal(String.valueOf(jsonObj.getInt("member_id")),
                                            jsonObj.getString("relation_name"),  jsonObj.getString("name")
                                    ,  jsonObj.getString("mobile"),  jsonObj.getString("email")
                                    ,jsonObj.getString("name")));
                                    bank_city.add(jsonObj.getString("relation_name"));
                                }

                            }

                           // relation_type.setItems(bank_city);
//                            bank_cityAdapter = new ArrayAdapter<SpinnerModal>(HealthCheckupActivity.this, R.layout.spinner_item, bank_cityList);
//                            bank_cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                            relation_type.setAdapter(bank_cityAdapter);

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

        SpinnerModal bank_city_modal = (SpinnerModal) policy_name.getSelectedItem();




       /* smr.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        RequestQueue mRequestQueue = Volley.newRequestQueue(HealthCheckupActivity.this);
        mRequestQueue.add(smr);*/

        HashMap<String, String> params = new HashMap<>();
        params.put("policy_id", bank_city_modal.getSelKey());
        params.put("employee_id", emp_id);
        params.put("user_type_name", "Employee");


        Log.d("policy_id", bank_city_modal.getSelValue());

        smr.setParams(params);
        rq.add(smr);
    }


    public void getData(String relationtypedata,String type) {

        String url_one = con.base_url+"/api/broker/get/health-checkup-by-member?";

        String url_two="employee_member_mapping_id="+relationtypedata;

        String url_three= "&user_type_name=Employee";

        String url = url_one +url_two+url_three;
       // RequestQueue mRequestQueue = Volley.newRequestQueue(HealthCheckupActivity.this);

    RequestQueue mRequestQueue = Volley.newRequestQueue(HealthCheckupActivity.this, new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                            Log.d("emp_member",response);

                            JSONObject jsonArra = new JSONObject(response);

                            String status= String.valueOf(jsonArra.getBoolean("status"));

                            if(status.equalsIgnoreCase("false")){

                                bank_city = new ArrayList<>();
                                bank_cityList = new ArrayList<>();
                                bank_cityList.add(new SpinnerModal("", "Select Member"));
                                bank_city.add("");

                            }else {

                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HealthCheckupActivity.this);
                                alertDialogBuilder.setMessage("Found Saved Data, do you want to continue with it ?");
                                alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {

                                        try {

                                        JSONObject array=jsonArra.getJSONObject("data");
                                        String id=array.getString("id");
                                        String employee_id=array.getString("employee_id");
                                        String policy_id=array.getString("policy_id");
                                        String employee_member_mapping_ids=array.getString("employee_member_mapping_id");
                                        String contacts=array.getString("contact");
                                        String emails=array.getString("email");
                                        String appointment_request_date_time=array.getString("appointment_request_date_time");
                                        String alternate_appointment_request_date_times=array.getString("alternate_appointment_request_date_time");
                                        String appointment_date_times=array.getString("appointment_date_time");
                                        String address_line_1s=array.getString("address_line_1");
                                        String address_line_2s=array.getString("address_line_2");

                                        String pincodes=array.getString("pincode");
                                        String appointment_status_id=array.getString("appointment_status_id");
                                        String checkup_type=array.getString("checkup_type");
                                        String status_updated_by=array.getString("status_updated_by");
                                        String health_check_up_status=array.getString("health_check_up_status");
                                        String health_check_up_report=array.getString("health_check_up_report");
                                        String created_by=array.getString("created_by");
                                        String updated_by=array.getString("updated_by");
                                        String deleted_by=array.getString("deleted_by");
                                        String created_at=array.getString("created_at");
                                        String updated_at=array.getString("updated_at");
                                        String deleted_at=array.getString("deleted_at");


if(type.equalsIgnoreCase("one")){

    city_id=array.getString("city_id");
    state_id=array.getString("state_id");
    pincode.setText(pincodes);
   // email.setText(emails);
   // mobile_no.setText(contacts);
    address_one.setText(address_line_1s);

    if(address_line_2s.equalsIgnoreCase("null")){

    }else {
        address_two.setText(address_line_2s);
    }

   // health_date.setText(appointment_request_date_time);
   // alter_date.setText(alternate_appointment_request_date_times);

    getPincode();
}else {
    city_id_two=array.getString("city_id");
    state_id_two=array.getString("state_id");
    pincode_two.setText(pincodes);
   // email_two.setText(emails);
   // mobile_no_two.setText(contacts);
   // address_one_two.setText(address_line_1s);

    if(address_line_2s.equalsIgnoreCase("null")){

    }else {
        address_two_two.setText(address_line_2s);
    }

   // health_date_two.setText(appointment_request_date_time);
   // alter_date_two.setText(alternate_appointment_request_date_times);

    getPincode_two();
}


                                        } catch (Exception e) {
                                            Log.e("onErrorResponse", e.toString());
                                        }

                                        arg0.dismiss();
                                    }
                                });
                                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        pincode.setText("");
                                        email.setText("");
                                        mobile_no.setText("");
                                        address_one.setText("");
                                        city_id=null;
                                        state_id=null;
                                         address_two.setText("");

                                        health_date.setText("");
                                        alter_date.setText("");


                                    }
                                });
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();




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

       // SpinnerModal bank_city_modal = (SpinnerModal) relation_type.getSelectedItem();




//       /* smr.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
//                0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//
//        RequestQueue mRequestQueue = Volley.newRequestQueue(HealthCheckupActivity.this);
//        mRequestQueue.add(smr);*/
//
//        HashMap<String, String> params = new HashMap<>();
//
//      //  params.put("employee_member_mapping_id", bank_city_modal.getSelKey());
//        params.put("user_type_name", "Employee");



      //  mStringRequest.setParams(params);
        mRequestQueue.add(mStringRequest);
    }
    public void setProfileDet() {

        String url = con.base_url+"/api/admin/user";
        RequestQueue mRequestQueue = Volley.newRequestQueue(HealthCheckupActivity.this,
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







                        employer_id = explrObject.getString("employee_id");
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
    void SendData(){


        int count = 0;

        if (member_name.getText().toString().trim().length() == 0) {
            ++count;
            member_name.setError("Name is Required");
        } else {
            member_name.setError(null);
        }
        if (mobile_no.getText().toString().trim().isEmpty() || !isValid(mobile_no.getText().toString().trim()) ||
                mobile_no.getText().toString().length() < 10 ||
                mobile_no.getText().toString().trim().length() > 13) {
            ++count;
            mobile_no.setError("Invalid Mobile");
        } else {
            mobile_no.setError(null);
        }

        if (address_one.getText().toString().trim().length() == 0) {
            ++count;
            address_one.setError("Address is Required");
        } else {
            address_one.setError(null);
        }



        if (email.getText().toString().trim().length() == 0) {
            ++count;
            email.setError("Enter Email Id");
        } else {
            email.setError(null);
        }

        if(Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches()){
            email.setError(null);
        }else {
            email.setError("Invalid Email");
            ++count;
        }

//            if (health_date.getText().toString().trim().length() == 0) {
//                ++count;
//                health_date.setError("date is Required");
//            } else {
//                health_date.setError(null);
//            }
//
//
//            if (alter_date.getText().toString().trim().length() == 0) {
//                ++count;
//                alter_date.setError("date is Required");
//            } else {
//                alter_date.setError(null);
//            }





        if(pincode.getText().toString().trim().length()==0){
            pincode.setError("Enter  Valid Pincode");
            ++count;
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



        // SpinnerModal patient = (SpinnerModal) relation_type.getSelectedItem();





        if (count == 0) {



            String  appointment_request_date_time=null;
            String alternate_appointment_request_date_time=null;


            try{

                String strCurrentDate = alter_date.getText().toString().trim()+ " " +alter_time.getText().toString().trim();
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa");
                Date newDate = format.parse(strCurrentDate);

                format = new SimpleDateFormat("yyyy-M-dd hh:mm:s");
                alternate_appointment_request_date_time=format.format(newDate);
            }catch (Exception e){
                alternate_appointment_request_date_time=alter_date.getText().toString().trim()+ " " +alter_time.getText().toString().trim();
            }



            try{

                String strCurrentDate = health_date.getText().toString().trim()+ " " +health_time.getText().toString().trim();
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa");
                Date newDate = format.parse(strCurrentDate);

                format = new SimpleDateFormat("yyyy-M-dd hh:mm:s");
                appointment_request_date_time=format.format(newDate);
            }catch (Exception e){
                appointment_request_date_time=health_date.getText().toString().trim()+ " " +health_time.getText().toString().trim();
            }


            JSONObject object = new JSONObject();
            try {
                object.put("appointment_request_date_time", appointment_request_date_time);
                object.put("alternate_appointment_request_date_time", alternate_appointment_request_date_time);
                object.put("state_id", state_id);
                object.put("city_id", city_id);
                object.put("pincode", pincode.getText().toString().trim());

                object.put("address_line_1", address_one.getText().toString().trim());
                object.put("address_line_2", address_two.getText().toString().trim());
                object.put("email", email.getText().toString().trim());
                object.put("contact", mobile_no.getText().toString().trim());
                object.put("employee_member_mapping_id", id);





            } catch (JSONException e) {
                e.printStackTrace();
            }



            JSONArray array=new JSONArray();
            array.put(object);



            progressDialog = ProgressDialog.show(this, "",
                    "Saving. Please wait...", true);
            RequestQueue rq = Volley.newRequestQueue(HealthCheckupActivity.this,
                    new HurlStack(null, getSocketFactory()));
            rq.getCache().clear();

            String url = con.base_url+"/api/broker/submit/health-checkup";
            StringRequest smr = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                progressDialog.dismiss();

                                Log.d("response",response);



                                JSONObject jsonObject = new JSONObject(response);
                                String errorCode = jsonObject.getString("message");
                                //   String status = jsonObject.getString("status");
                                String status = String.valueOf(jsonObject.getBoolean("status"));

                                if(status.equalsIgnoreCase("true")){
                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(HealthCheckupActivity.this);
                                    alertDialog.setTitle("");
                                    alertDialog.setMessage(errorCode);
                                    alertDialog.setCancelable(false);
                                    alertDialog.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            finish();
                                        }
                                    });
                                    alertDialog.show();
                                }else {
                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(HealthCheckupActivity.this);
                                    alertDialog.setTitle("Alert");
                                    alertDialog.setMessage(errorCode);
                                    alertDialog.setCancelable(false);
                                    alertDialog.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    });
                                    alertDialog.show();
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


                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();

                    params.put("user_type_name", "Employee");
                    params.put("action", "update");
                    params.put("members",array.toString());
                   params.put("type","mobile");


                    return params;
                }

            };


            //   SpinnerModal bank_name_modal = (SpinnerModal) relation_type.getSelectedItem();





            //  params.put("members",array.toString());

            // smr.setParams(params);
            rq.add(smr);


        }


    }
    
    
    void AddSendData(){
       


            int count = 0;

            if (member_name.getText().toString().trim().length() == 0) {
                ++count;
                member_name.setError("Name is Required");
            } else {
                member_name.setError(null);
            }
            if (mobile_no.getText().toString().trim().isEmpty() || !isValid(mobile_no.getText().toString().trim()) ||
                    mobile_no.getText().toString().length() < 10 ||
                    mobile_no.getText().toString().trim().length() > 13) {
                ++count;
                mobile_no.setError("Invalid Mobile");
            } else {
                mobile_no.setError(null);
            }

            if (address_one.getText().toString().trim().length() == 0) {
                ++count;
                address_one.setError("Address is Required");
            } else {
                address_one.setError(null);
            }



            if (email.getText().toString().trim().length() == 0) {
                ++count;
                email.setError("Enter Email Id");
            } else {
                email.setError(null);
            }

        if(Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches()){
            email.setError(null);
        }else {
            email.setError("Invalid Email");
            ++count;
        }

//            if (health_date.getText().toString().trim().length() == 0) {
//                ++count;
//                health_date.setError("date is Required");
//            } else {
//                health_date.setError(null);
//            }
//
//
//            if (alter_date.getText().toString().trim().length() == 0) {
//                ++count;
//                alter_date.setError("date is Required");
//            } else {
//                alter_date.setError(null);
//            }





            if(pincode.getText().toString().trim().length()==0){
                pincode.setError("Enter  Valid Pincode");
                ++count;
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


        if(!two_member.equalsIgnoreCase("")){
            if (member_name_two.getText().toString().trim().length() == 0) {
                ++count;
                member_name_two.setError("Name is Required");
            } else {
                member_name_two.setError(null);
            }
            if (mobile_no_two.getText().toString().trim().isEmpty() || !isValid(mobile_no_two.getText().toString().trim()) ||
                    mobile_no_two.getText().toString().length() < 10 ||
                    mobile_no_two.getText().toString().trim().length() > 13) {
                ++count;
                mobile_no_two.setError("Invalid Mobile");
            } else {
                mobile_no_two.setError(null);
            }

            if (address_one_two.getText().toString().trim().length() == 0) {
                ++count;
                address_one_two.setError("Address is Required");
            } else {
                address_one_two.setError(null);
            }



            if (email_two.getText().toString().trim().length() == 0) {
                ++count;
                email_two.setError("Enter Email Id");
            } else {
                email_two.setError(null);
            }

//            if (health_date.getText().toString().trim().length() == 0) {
//                ++count;
//                health_date.setError("date is Required");
//            } else {
//                health_date.setError(null);
//            }
//
//
//            if (alter_date.getText().toString().trim().length() == 0) {
//                ++count;
//                alter_date.setError("date is Required");
//            } else {
//                alter_date.setError(null);
//            }

            if(Patterns.EMAIL_ADDRESS.matcher(email_two.getText().toString().trim()).matches()){
                email_two.setError(null);
            }else {
                email_two.setError("Invalid Email");
                ++count;
            }



            if(pincode_two.getText().toString().trim().length()==0){
                pincode_two.setError("Enter  Valid Pincode");
                ++count;
            }else {
                if (pincode_two.getText().toString().trim().length()<6) {
                    ++count;
                    pincode_two.setError("Enter  Valid Pincode");
                } else {
                    if(invalid.equalsIgnoreCase("1")){
                        ++count;
                        pincode_two.setError("Enter  Valid Pincode");
                    }else {
                        pincode_two.setError(null);
                    }



                }
            }

        }

       // SpinnerModal patient = (SpinnerModal) relation_type.getSelectedItem();


        SpinnerModal policytype = (SpinnerModal) policy_type.getSelectedItem();
        if (policytype.getSelKey().equalsIgnoreCase("")) {
            ++count;
            new AlertDialog.Builder(HealthCheckupActivity.this)
                    .setTitle("Error")
                    .setMessage("Please Select Policy Type")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setNegativeButton(android.R.string.ok, null).show();
            return;
        }


        SpinnerModal name = (SpinnerModal) policy_name.getSelectedItem();
        if (name.getSelKey().equalsIgnoreCase("")) {
            ++count;
            new AlertDialog.Builder(HealthCheckupActivity.this)
                    .setTitle("Error")
                    .setMessage("Please Select Policy Name")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setNegativeButton(android.R.string.ok, null).show();
            return;
        }


            if (count == 0) {



                String  appointment_request_date_time=null;
                String alternate_appointment_request_date_time=null;


                try{

                    String strCurrentDate = alter_date.getText().toString().trim()+ " " +alter_time.getText().toString().trim();
                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa");
                    Date newDate = format.parse(strCurrentDate);

                    format = new SimpleDateFormat("yyyy-M-dd hh:mm:s");
                    alternate_appointment_request_date_time=format.format(newDate);
                }catch (Exception e){
                    alternate_appointment_request_date_time=alter_date.getText().toString().trim()+ " " +alter_time.getText().toString().trim();
                }


                try{

                    String strCurrentDate = health_date.getText().toString().trim()+ " " +health_time.getText().toString().trim();
                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa");
                    Date newDate = format.parse(strCurrentDate);

                    format = new SimpleDateFormat("yyyy-M-dd hh:mm:s");
                    appointment_request_date_time=format.format(newDate);
                }catch (Exception e){
                    appointment_request_date_time=health_date.getText().toString().trim()+ " " +health_time.getText().toString().trim();
                }


                JSONObject object = new JSONObject();
                try {
                    object.put("appointment_request_date_time", appointment_request_date_time);
                    object.put("alternate_appointment_request_date_time", alternate_appointment_request_date_time);
                    object.put("state_id", state_id);
                    object.put("city_id", city_id);
                    object.put("pincode", pincode.getText().toString().trim());
                    object.put("address_line_1", address_one.getText().toString().trim());
                    object.put("address_line_2", address_two.getText().toString().trim());
                    object.put("email", email.getText().toString().trim());
                    object.put("contact", mobile_no.getText().toString().trim());
                    object.put("employee_member_mapping_id", one_member);





                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String  appointment_request_date_time_two=null;
                String alternate_appointment_request_date_time_two=null;


                try{

                    String strCurrentDate = alter_date_two.getText().toString().trim()+ " " +alter_time_two.getText().toString().trim();
                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa");
                    Date newDate = format.parse(strCurrentDate);

                    format = new SimpleDateFormat("yyyy-M-dd hh:mm:s");
                    alternate_appointment_request_date_time_two=format.format(newDate);
                }catch (Exception e){
                    alternate_appointment_request_date_time_two=alter_date_two.getText().toString().trim()+ " " +alter_time_two.getText().toString().trim();
                }


                try{

                    String strCurrentDate = health_date_two.getText().toString().trim()+ " " +health_time_two.getText().toString().trim();
                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa");
                    Date newDate = format.parse(strCurrentDate);

                    format = new SimpleDateFormat("yyyy-M-dd hh:mm:s");
                    appointment_request_date_time_two=format.format(newDate);
                }catch (Exception e){
                    appointment_request_date_time_two=health_date_two.getText().toString().trim()+ " " +health_time_two.getText().toString().trim();
                }


                JSONObject objecta = new JSONObject();
                try {
                    objecta.put("alternate_appointment_request_date_time", alternate_appointment_request_date_time_two);
                    objecta.put("appointment_request_date_time", appointment_request_date_time_two);
                    objecta.put("state_id", state_id_two);
                    objecta.put("city_id", city_id_two);
                    objecta.put("pincode", pincode_two.getText().toString().trim());
                    objecta.put("address_line_1", address_one_two.getText().toString().trim());
                    objecta.put("address_line_2", address_two_two.getText().toString().trim());
                    objecta.put("email", email_two.getText().toString().trim());
                    objecta.put("contact", mobile_no_two.getText().toString().trim());
                    objecta.put("employee_member_mapping_id", two_member);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                JSONArray array=new JSONArray();
                array.put(object);

                if(two_member_lin.getVisibility()==View.VISIBLE){
                    array.put(objecta);
                }


                progressDialog = ProgressDialog.show(this, "",
                        "Saving. Please wait...", true);
                RequestQueue rq = Volley.newRequestQueue(HealthCheckupActivity.this,
                        new HurlStack(null, getSocketFactory()));
                rq.getCache().clear();

                String url = con.base_url+"/api/broker/submit/health-checkup";
                StringRequest smr = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    progressDialog.dismiss();

                                    Log.d("response",response);



                                    JSONObject jsonObject = new JSONObject(response);
                                    String errorCode = jsonObject.getString("message");
                                    //   String status = jsonObject.getString("status");
                                    String status = String.valueOf(jsonObject.getBoolean("status"));

                                    if(status.equalsIgnoreCase("true")){
                                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(HealthCheckupActivity.this);
                                        alertDialog.setTitle("");
                                        alertDialog.setMessage(errorCode);
                                        alertDialog.setCancelable(false);
                                        alertDialog.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                finish();
                                            }
                                        });
                                        alertDialog.show();
                                    }else {
                                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(HealthCheckupActivity.this);
                                        alertDialog.setTitle("Alert");
                                        alertDialog.setMessage(errorCode);
                                        alertDialog.setCancelable(false);
                                        alertDialog.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                            }
                                        });
                                        alertDialog.show();
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


                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();

                            params.put("user_type_name", "Employee");
                            params.put("action", "insert");
                            params.put("members",array.toString());
                           params.put("type","mobile");


                            return params;
                    }

                    };


             //   SpinnerModal bank_name_modal = (SpinnerModal) relation_type.getSelectedItem();





              //  params.put("members",array.toString());

               // smr.setParams(params);
                rq.add(smr);


            }


    }


    private void setBankDet() {

        ob=new ArrayList<>();
        oba=new ArrayList<>();
        String url = con.base_url+"/api/broker/get-health-checkup-list";
        RequestQueue rq = Volley.newRequestQueue(HealthCheckupActivity.this,
                new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {






                            JSONObject resp= new JSONObject(response);

                            String statusa= String.valueOf(resp.getBoolean("status"));



                            if (statusa.equalsIgnoreCase("false")) {

                            } else {

                                JSONArray jsonObj =resp.getJSONArray("data");

                                for (int j = 0; j < jsonObj.length(); j++) {
                                    JSONObject jo_area = (JSONObject) jsonObj.get(j);


                                    String policy_number = jo_area.getString("policy_id");
                                    String employee_code = jo_area.getString("employee_id");


                                    ob.add(policy_number);
                                    oba.add(employee_code);


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
        params.put("user_type_name", "Employee");

        smr.setParams(params);
        rq.add(smr);

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