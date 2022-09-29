package com.palm.newbenefit.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;

import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.palm.newbenefit.ApiConfig.DateTimeFormater;
import com.palm.newbenefit.ApiConfig.MyDateListener;
import com.palm.newbenefit.ApiConfig.Utils;
import com.palm.tatarewamp.SslData.NullHostNameVerifier;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;
import com.palm.newbenefit.Adapter.DiscAdapter;
import com.palm.newbenefit.Adapter.ExampleAdapter;
import com.palm.newbenefit.Adapter.RecommandAdapter;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.ApiConfig.RecyclerTouchListener;
import com.palm.newbenefit.Module.DiscData;
import com.palm.newbenefit.Module.SpinnerModal;
import com.palm.newbenefit.Module.SpinnerModalFamilyData;
import com.kmd.newbenefit.R;

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

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class PlanHospitalActivity extends AppCompatActivity {
    EditText policy_type_spin,policy_type_spin_no,patient_name_spin,state,city;
    String user_id;
    EditText insurar_name,tpa,employer_name,employee_name,sum_insured,
            relationdrop,email_id,contact,hos_name
            ,doc_name,hos_date,discharge_date;
  
    LinearLayout download_ecard;

    RelativeLayout adhaarBackLayout_cheque1,adhaarBackLayout_cheque1_two;
    ImageView bankPreview1,bankPreview1_two;
    AutoCompleteTextView ailment;
    Constants con = null;
    Context context;
    ArrayList<SpinnerModal> bank_nameList = null;
    ArrayAdapter<SpinnerModal> bank_nameAdapter = null;
    String state_id="";
    ArrayList<String> bank_name = null;

    ArrayList<String> bank_cityc = null;
    ArrayList<SpinnerModalFamilyData> bank_cityListc = null;
    ArrayAdapter<SpinnerModalFamilyData> bank_cityAdapterc = null;

    ArrayList<String> bank_city = null;
    ArrayList<SpinnerModal> bank_cityList = null;
    ArrayAdapter<SpinnerModal> bank_cityAdapter = null;
    String token;
    String emp_data;
    String employee_id;

    String bank_name_value;
    private SimpleDateFormat dateFormatter;
    String[] days;
    ProgressDialog progressDialog = null;
    Calendar newCalendar;
    LinearLayout proceed;
    RadioGroup radiogrp;
    RadioButton self,hr;
    String statusa;
    String hospital_state;


    TextView name,address,cityname,alignment,cost,treat_type,room_type;

    String bank_city_value = "";
    int day_b ;
    int month_b;
    int year_b ;
    String displayName;

    ArrayList<String> bank_branch = null;
    ArrayList<SpinnerModal> bank_branchList = null;
    ArrayAdapter<SpinnerModal> bank_branchAdapter = null;

    Bitmap myBitmap;
    String _year;
    String _month, _day;

    int day = 0;
    int month = 0;
    int year = 0;

    int day_a = 0;
    int month_a = 0;
    int year_a= 0;
    TextView status,sub;

    private ArrayList<String> mExampleList;
    private RecyclerView mRecyclerView;
    private ExampleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    String my_url;

    String id_proof,document;

    public static final int REQUEST_CODE_USER_PHOTO = 114;

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1890;

    public static final int REQUEST_CODE_USER_PHOTO_two = 115;

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE_two = 1891;

    String selBankPath=null,selBankPath_cheque=null;
    Button submit;
    TextView request_id,claim_id;
    String family_id;

    ImageView info_text;
    LinearLayout disc;

    List<DiscData> ob = null;
    DiscAdapter adapter = null;

    List<DiscData> ob_hos = null;
    RecommandAdapter adapter_hos = null;


    RecyclerView recyclerView = null;
    RecyclerView recyclerView_hos = null;
    String ecard_url;
    LinearLayout hos;
    String selectedData;
    String recommendion;
    EditText discharge_date_plan,hos_date_plan;
    TextView commentsdata;
    LinearLayout slot;
    String path=null;
    TextView bankPreview1_two_text,bankPreview1_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_hospital);
        con=new Constants();
        newCalendar = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);

        requestMultiplePermissions();
        context = PlanHospitalActivity.this;
        info_text=findViewById(R.id.info_text);
        bankPreview1_two_text=findViewById(R.id.bankPreview1_two_text);
        bankPreview1_text=findViewById(R.id.bankPreview1_text);

        disc=findViewById(R.id.disc);
        hr=findViewById(R.id.hr);
        self=findViewById(R.id.self);
        radiogrp=findViewById(R.id.radiogrp);
        submit=findViewById(R.id.submit);
        slot=findViewById(R.id.slot);
        discharge_date_plan=findViewById(R.id.discharge_date_plan);
        hos_date_plan=findViewById(R.id.hos_date_plan);
        commentsdata=findViewById(R.id.commentsdata);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(slot.getVisibility()==View.VISIBLE){

                    Planned();
                }else {
                    if (self.isChecked()) {
                        selectedData = "1";
                    } else if (hr.isChecked()) {
                        selectedData = "2";
                    }
                    ToggleStatus(selectedData);
                    ToggleStatusRecommend(selectedData,statusa,recommendion);
                }

            }
        });

        info_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();

            }
        });

        bankPreview1_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(id_proof));
                startActivity(browserIntent);

            }
        });

        bankPreview1_two_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(document));
                startActivity(browserIntent);

            }
        });


        recyclerView = findViewById(R.id.policy_recycle);
        proceed= findViewById(R.id.proceed);

        name = findViewById(R.id.name);
        address = findViewById(R.id.address);
        cityname = findViewById(R.id.cityname);
        alignment = findViewById(R.id.alignment);
        cost = findViewById(R.id.cost);
        treat_type = findViewById(R.id.treat_type);
        room_type = findViewById(R.id.room_type);


        status = findViewById(R.id.status);
        sub = findViewById(R.id.sub);
        hos= findViewById(R.id.hos);


        ob = new ArrayList<>();
        adapter = new DiscAdapter(this, ob);
        recyclerView.setAdapter(adapter);




        SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);

        token = prefs.getString("api_token", null);

        dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);

        policy_type_spin=findViewById(R.id.policy_type_spin);
        policy_type_spin_no=findViewById(R.id.policy_type_spin_no);
        patient_name_spin=findViewById(R.id.patient_name_spin);
        state=findViewById(R.id.state);
        city=findViewById(R.id.city);

        insurar_name=findViewById(R.id.insurar_name);
        tpa=findViewById(R.id.tpa);
        employer_name=findViewById(R.id.employer_name);
        employee_name=findViewById(R.id.employee_name);
        sum_insured=findViewById(R.id.sum_insured);
        relationdrop=findViewById(R.id.relationdrop);
        email_id=findViewById(R.id.email_id);
        contact=findViewById(R.id.contact);
        ailment=findViewById(R.id.ailment);
        hos_name=findViewById(R.id.hos_name);
        doc_name=findViewById(R.id.doc_name);
        hos_date=findViewById(R.id.hos_date);
        discharge_date=findViewById(R.id.discharge_date);
        submit=findViewById(R.id.submit);
        claim_id=findViewById(R.id.claim_id);
        request_id=findViewById(R.id.request_id);
        adhaarBackLayout_cheque1=findViewById(R.id.adhaarBackLayout_cheque1);
        adhaarBackLayout_cheque1_two=findViewById(R.id.adhaarBackLayout_cheque1_two);

        bankPreview1=findViewById(R.id.bankPreview1);
        bankPreview1_two=findViewById(R.id.bankPreview1_two);
        download_ecard=findViewById(R.id.download_ecard);
        Intent intent = getIntent();



        family_id = intent.getStringExtra("fam_id");



        GetEmployeeData();



        int numberOfColumns = 1;





        recyclerView.setLayoutManager(new GridLayoutManager(PlanHospitalActivity.this, numberOfColumns));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(PlanHospitalActivity.this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager manager = new GridLayoutManager(PlanHospitalActivity.this, 1, GridLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);


        hos_date_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {






                Utils.desablePreDatePicker(PlanHospitalActivity.this, new MyDateListener() {
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


                        day_b = selectedDay;
                        month_b =selectedMonth;
                        year_b = selectedYear;


                        String date = DateTimeFormater.getFormatedDate(newCalendar.getTime());
                        hos_date_plan.setText(date);
                        discharge_date_plan.setText("");


                        //travel_request_todateshow.setText("");
                    }
                });
            }
        });


        discharge_date_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Utils.maxdate(PlanHospitalActivity.this, day, month, year, new MyDateListener() {
                    @Override
                    public void onDateSelect(int selectedYear, int selectedMonth, int selectedDay) {
                        newCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                        newCalendar.set(Calendar.MONTH, selectedMonth);
                        newCalendar.set(Calendar.YEAR, selectedYear);

                        day_a = selectedDay;
                        month_a = selectedMonth;
                        year_a = selectedYear;

                        String date = DateTimeFormater.getFormatedDate(newCalendar.getTime());
                        discharge_date_plan.setText(date);

                        //travel_request_todateshow.setText("");
                    }
                });
            }
        });
        download_ecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                    DownloadManager downloadManager = null;


                    downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                    Uri uri = Uri.parse(ecard_url);
                    DownloadManager.Request request = new DownloadManager.Request(uri);
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    Long reference = downloadManager.enqueue(request);



            }
        });






    }
    void Planned(){
        String url = con.base_url+"/api/employee/select-recommend-or-current-hospital";

        RequestQueue rq = Volley.newRequestQueue(PlanHospitalActivity.this,
                new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();

        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject js=new JSONObject(response);



                            String message=js.getString("message");
                            new AlertDialog.Builder(PlanHospitalActivity.this)
                                    .setTitle("Success")
                                    .setMessage(message)
                                    .setIcon(R.drawable.checkmark)
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface dialog, int whichButton) {


                                            finish();


                                        }
                                    })
                                    .setNegativeButton(android.R.string.no, null).show();



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


        params.put("planned_date", hos_date_plan.getText().toString().trim());

        if(discharge_date_plan.getText().toString().trim().isEmpty()){

        }else {
            params.put("discharge_date",discharge_date_plan.getText().toString().trim());
        }

        params.put("claim_request_id", family_id);
        params.put("path", path);

        smr.setParams(params);
        rq.add(smr);

    }

    void ToggleStatus(String data){
        String url = con.base_url+"/api/employee/toggle-claim-status";

        RequestQueue rq = Volley.newRequestQueue(PlanHospitalActivity.this,
                new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();

        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject js=new JSONObject(response);



                                String message=js.getString("message");
                                  new AlertDialog.Builder(PlanHospitalActivity.this)
                                    .setTitle("Success")
                                    .setMessage(message)
                                    .setIcon(R.drawable.checkmark)
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface dialog, int whichButton) {


finish();


                                        }
                                    })
                                    .setNegativeButton(android.R.string.no, null).show();



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

        params.put("claim_request_id", family_id);
        params.put("status", data);

        smr.setParams(params);


        rq.add(smr);

    }

    void ToggleStatusRecommend(String data,String statuss,String recommendion){
        String url = con.base_url+"/api/employee/select-recommend-or-current-hospital";

        RequestQueue rq = Volley.newRequestQueue(PlanHospitalActivity.this,
                new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();

        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject js=new JSONObject(response);





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

        if(statuss.equalsIgnoreCase("3")&&  recommendion.equalsIgnoreCase("yes")&&
                data.equalsIgnoreCase("2")){

            params.put("has_gone_with_recommendation", "1");
            params.put("hospital_name", name.getText().toString());
            params.put("hospital_city", cityname.getText().toString());
            params.put("hospital_state", hospital_state);
            params.put("claim_request_id", family_id);
            params.put("path", path);




        }else {
            params.put("claim_request_id", family_id);
            params.put("path", path);


        }

        smr.setParams(params);


        rq.add(smr);
    }






    void GetEmployeeData(){
        String url = con.base_url+"/api/employee/get-claim-data-detail";

        RequestQueue rq = Volley.newRequestQueue(PlanHospitalActivity.this,
                new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();

        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject js=new JSONObject(response);



                    JSONArray jsonObj=js.getJSONArray("data");


                    for (int i = 0; i < jsonObj.length(); i++) {
                        JSONObject explrObject = jsonObj.getJSONObject(0);

                        String employee_id = explrObject.getString("employee_id");
                        String patient_names = explrObject.getString("patient_name");
                        String member_id = explrObject.getString("member_id");
                        String id = explrObject.getString("id");

                        patient_name_spin.setText(patient_names);
                        String insurer_id = explrObject.getString("insurer_id");
                        String tpa_name = explrObject.getString("tpa_name");
                        tpa.setText(tpa_name);
                        String tpa_id = explrObject.getString("tpa_id");
                        String employer_names = explrObject.getString("employer_name");
                        employer_name.setText(employer_names);
                        String employer_ids = explrObject.getString("employer_id");
                        String employee_names = explrObject.getString("employee_name");
                        employee_name.setText(employee_names);

                        String relation_with_employee = explrObject.getString("relation_with_employee");
                        relationdrop.setText(relation_with_employee);

                        String policy_sub_type_id = explrObject.getString("policy_sub_type_id");
                        String policy_type_name = explrObject.getString("policy_type_name");
                        String policy_type_id = explrObject.getString("policy_type_id");
                        String policy_no = explrObject.getString("policy_no");
                        policy_type_spin_no.setText(policy_no);
                        String policy_id = explrObject.getString("policy_id");
                        String insurer_name = explrObject.getString("insurer_name");

                        insurar_name.setText(insurer_name);
                        String claim_request_id = explrObject.getString("claim_request_id");
                        request_id.setText(claim_request_id);


                        String recommendation_id = explrObject.getString("recommendation_id");

                        String balance_cover = explrObject.getString("balance_cover");
                        sum_insured.setText(balance_cover);
                         statusa = explrObject.getString("status");
                        

                         ecard_url = explrObject.getString("ecard_url");




                        String policy_sub_type_name = explrObject.getString("policy_sub_type_name");

                        policy_type_spin.setText(policy_sub_type_name);

                        String email = explrObject.getString("email");

                        email_id.setText(email);
                        String discharge_dates = explrObject.getString("discharge_date");

                        if(discharge_dates.equalsIgnoreCase("null")){

                        }else {



                            try{

                                String strCurrentDate = discharge_dates;
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                Date newDate = format.parse(strCurrentDate);

                                format = new SimpleDateFormat("dd-MM-yyyy");

                                discharge_date.setText(format.format(newDate));
                            }catch (Exception e){
                                discharge_date.setText(discharge_dates);
                            }

                        }


                        String planned_date = explrObject.getString("planned_date");

                        try{

                            String strCurrentDate = planned_date;
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            Date newDate = format.parse(strCurrentDate);

                            format = new SimpleDateFormat("dd-MM-yyyy");

                            hos_date.setText(format.format(newDate));
                        }catch (Exception e){
                            hos_date.setText(planned_date);
                        }

                        String amount_from_buffer = explrObject.getString("amount_from_buffer");
                        String claim_amount = explrObject.getString("claim_amount");
                        String claim_type = explrObject.getString("claim_type");


                        String city_name = explrObject.getString("city_name");
                        city.setText(city_name);
                        String state_name = explrObject.getString("state_name");
                        state.setText(state_name);
                        String hospital_name = explrObject.getString("hospital_name");
                        hos_name.setText(hospital_name);
                        String comment = explrObject.getString("comment");
                        String tpa_intimate_no = explrObject.getString("tpa_intimate_no");

                        if(tpa_intimate_no.equalsIgnoreCase("null")){

                        }else {
                            claim_id.setText(tpa_intimate_no);
                        }
                        String mobile_no = explrObject.getString("mobile_no");
                        contact.setText(mobile_no);


                        String doctor_name = explrObject.getString("doctor_name");

                        if (doctor_name.equalsIgnoreCase("") || doctor_name.isEmpty() ||
                                doctor_name.equalsIgnoreCase("null")) {

                        } else {
                            doc_name.setText(doctor_name);
                        }
                        String reason = explrObject.getString("reason");
                        ailment.setText(reason);
                        String remark = explrObject.getString("remark");
                        String total_co_buffer_amount = explrObject.getString("total_co_buffer_amount");
                        String balance_co_buffer_amount = explrObject.getString("balance_co_buffer_amount");
                        String created_at = explrObject.getString("created_at");

                        path="tpa/"+"abcd"+"/"+"e-cashless-intimation/"+claim_request_id+"/"+"xyz";

                        JSONArray jsonObjs = explrObject.getJSONArray("media");

                         id_proof = jsonObjs.getString(0);
                         document = jsonObjs.getString(1);


                        if(id_proof.contains("pdf")){

                            bankPreview1_text.setText(id_proof);
                            bankPreview1_text.setVisibility(View.VISIBLE);
                            bankPreview1.setVisibility(View.GONE);
                        }else {
                            Picasso.get().load(id_proof).into(bankPreview1);

                            bankPreview1_text.setVisibility(View.GONE);
                            bankPreview1.setVisibility(View.VISIBLE);

                        }

                        if(document.contains("pdf")){
                            bankPreview1_two_text.setText(id_proof);
                            bankPreview1_two_text.setVisibility(View.VISIBLE);
                            bankPreview1.setVisibility(View.GONE);
                        }else {
                            bankPreview1_two_text.setVisibility(View.GONE);
                            bankPreview1_two.setVisibility(View.VISIBLE);
                            Picasso.get().load(document).into(bankPreview1_two);

                        }





                        JSONArray claim_deficiency = explrObject.getJSONArray("claim_deficiency");
                        if (claim_deficiency.length() == 0) {
                            disc.setVisibility(View.GONE);


                            if(statusa.equalsIgnoreCase("1")){
                                status.setText("Appointment Confirmed");
                            }else if(statusa.equalsIgnoreCase("4")){
                                status.setText("Appointment Rejected");
                            }else  {
                                status.setText("Processing");
                            }




                            if(statusa.equalsIgnoreCase("2")){
                                sub.setText("TPA Approval Pending");
                            }else    if(statusa.equalsIgnoreCase("3")){
                                sub.setText("HR Approval Pending");
                            }else if(statusa.equalsIgnoreCase("1")){
                                sub.setText("Request Processed By TPA");
                            }else {
                                sub.setText("Request Processed By HR");
                            }


                        } else {
                            disc.setVisibility(View.VISIBLE);


                            for (int j = 0; j < claim_deficiency.length(); j++) {

                                JSONObject jo_area = (JSONObject) claim_deficiency.get(j);


                                String ids = jo_area.getString("id");
                                String claim_id = jo_area.getString("claim_id");
                                String remarks = jo_area.getString("remark");
                                String responses = (jo_area.getString("response"));

                                String other_document = jo_area.getString("other_document");
                                String statuss = jo_area.getString("status");
                                String created_by = jo_area.getString("created_by");
                                String created_ats = (jo_area.getString("created_at"));

                                if (statusa.equalsIgnoreCase("1") && statuss.equalsIgnoreCase("0")) {
                                    status.setText("Appointment Confirmed");
                                } else if (statusa.equalsIgnoreCase("1") && statuss.equalsIgnoreCase("1")) {
                                    status.setText("Appointment Rejected");
                                } else if (statuss.equalsIgnoreCase("1")) {
                                    status.setText("Discrepancy Raised");
                                } else if (statuss.equalsIgnoreCase("2")) {
                                    status.setText("Discrepancy Rejected");
                                }else {
                                    status.setText("Discrepancy Raised");
                                }


                                if (statusa.equalsIgnoreCase("1") && statuss.equalsIgnoreCase("0")) {
                                    sub.setText("Request Processed by TPA");
                                } else if (statusa.equalsIgnoreCase("2")) {
                                    if (created_by.equalsIgnoreCase("0") && statuss.equalsIgnoreCase("1")) {
                                        sub.setText(" Discrepancy Raised by TPA");
                                    } else if (created_by.equalsIgnoreCase("0") && statuss.equalsIgnoreCase("2")) {
                                        sub.setText(" Discrepancy Rejected by TPA");
                                    }
                                    else {
                                        sub.setText(" Discrepancy Raised by TPA");
                                    }
                                } else if (statusa.equalsIgnoreCase("3")) {

                                    if (created_by.equalsIgnoreCase("1") && statuss.equalsIgnoreCase("1")) {
                                        sub.setText(" Discrepancy Raised by Employer");
                                    } else if (created_by.equalsIgnoreCase("1") && statuss.equalsIgnoreCase("2")) {
                                        sub.setText(" Discrepancy Rejected by Employer");
                                    }else {
                                        sub.setText(" Discrepancy Rejected by Employer");
                                    }

                                } else if (statusa.equalsIgnoreCase("4")) {
                                    sub.setText("Appointment Rejected by TPA");
                                }
                                else if(statusa.equalsIgnoreCase("1")){
                                    sub.setText("Request Processed By TPA");
                                }else {
                                    sub.setText("Request Processed By HR");
                                }


                                ob.add(new DiscData(claim_request_id, ids, remarks, responses, other_document
                                        , statuss, created_by, created_ats));


                            }


                            adapter.notifyDataSetChanged();
                        }
                        Log.e("statusa", statusa.toString());
                        if(statusa.equalsIgnoreCase("2")&&!comment.equalsIgnoreCase("null")){
                            slot.setVisibility(View.VISIBLE);
                            hos_date_plan.setText(planned_date);
                            discharge_date.setText(discharge_dates);
                            commentsdata.setText(comment);
                            submit.setVisibility(View.VISIBLE);
                            status.setText("Processing");
                            sub.setText("Alternate Date Suggested by TPA");
                        }else  if(statusa.equalsIgnoreCase("2")){

                            slot.setVisibility(View.GONE);

                            try{
                                JSONObject recommendation_data = explrObject.getJSONObject("recommendation_data");


                                if (recommendation_data.length() == 0) {
                                    recommendion="no";
                                    hos.setVisibility(View.GONE);
                                    proceed.setVisibility(View.GONE);
                                    submit.setVisibility(View.GONE);
                                } else {
                                    recommendion="yes";
                                    hos.setVisibility(View.VISIBLE);
                                    proceed.setVisibility(View.VISIBLE);
                                    submit.setVisibility(View.GONE);

                                    String hospital_names = recommendation_data.getString("hospital_name");
                                    String hospital_address = recommendation_data.getString("hospital_address");
                                    hospital_state = recommendation_data.getString("hospital_state");
                                    String hospital_city = recommendation_data.getString("hospital_city");
                                    String ailment_id = recommendation_data.getString("ailment_id");
                                    String ailment_name = recommendation_data.getString("reason");

                                    String ailment_cost = recommendation_data.getString("ailment_cost");
                                    String room_rent_type = recommendation_data.getString("room_rent_type");

                                    String treatment_type = recommendation_data.getString("treatment_type");




                                    name.setText(hospital_names);
                                    address.setText(hospital_address);
                                    cityname.setText(hospital_city);
                                    alignment.setText(ailment_name);
                                    cost.setText(ailment_cost);
                                    treat_type.setText(treatment_type);
                                    room_type.setText(room_rent_type);





                                }


                            }catch (Exception e){
                                Log.e("errordata", e.toString());
                                hos.setVisibility(View.GONE);
                                recommendion="no";
                                submit.setVisibility(View.GONE);
                                proceed.setVisibility(View.GONE);
                            }


                        }else {
                            hos.setVisibility(View.GONE);
                            recommendion="no";
                            slot.setVisibility(View.GONE);
                            submit.setVisibility(View.GONE);
                            proceed.setVisibility(View.GONE);
                        }






                    }

                } catch (JSONException e) {
                            Log.e("error", e.toString());
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


        params.put("claim_request_id", family_id);

        smr.setParams(params);
        rq.add(smr);


    }




    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)PlanHospitalActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    private void  requestMultiplePermissions(){
        Dexter.withActivity(PlanHospitalActivity.this)
                .withPermissions(

                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(PlanHospitalActivity.this, "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }


                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(PlanHospitalActivity.this, "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
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