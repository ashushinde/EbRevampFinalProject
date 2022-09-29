package com.palm.newbenefit.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.palm.newbenefit.Adapter.DocumentAdapter;
import com.palm.newbenefit.Adapter.billsAdapter;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.ApiConfig.RecyclerTouchListener;
import com.palm.newbenefit.Module.DocumentData;
import com.palm.newbenefit.Module.amount;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class ClaimDetailActivity extends AppCompatActivity {

    LinearLayout user_detail, hospital_detail, claim_detail;

    EditText email_id, contact, hos_date, patient_name_spin,disease_name, discharge_date, hos_name, hos_address, hos_reason, bill_no, bill_date, bill_amount, comment, type;
    Button hos_add_button;
    EditText policy_type_spin,policy_type_spin_no, city, state,claim_subtype;

    EditText relationdrop,doc_name;
    RecyclerView ghi_recycle;

    String user_id;

    Constants con = null;
    Context context;


    RelativeLayout a, b, c;
    String token;

    LinearLayout sudexo_next,hospital_details,claim_submit;



    ProgressDialog progressDialog = null;

    Button  view_data;



    String claim_id,employee_id,employer_id,member_id,policy_id,policy_type_id,claim_type;


    View claimLine1,claimLine2;

    List<DocumentData> documentDataList = null;


    List<amount> ob_bill_no = null;
    List<amount> ob_bill_date = null;
    List<amount> ob_bill_amount = null;
    List<amount> ob_billcomment = null;
    List<amount> ob_type = null;

    DocumentAdapter docuadapter;

    billsAdapter bill_noadapter;
    billsAdapter bill_dateadapter;
    billsAdapter bill_amountadapter;
    billsAdapter billcommentadapter;
    billsAdapter typetadapter;


    RecyclerView bill_nos;
    RecyclerView bill_dates;
    RecyclerView bill_amounts;
    RecyclerView billcomments;
    RecyclerView types;
    ImageView info_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim_detail);

        context = ClaimDetailActivity.this;
        SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);

        token = prefs.getString("api_token", null);
        user_id = prefs.getString("user_id", null);


        con = new Constants();

        claimLine1 = findViewById(R.id.claimLine1);
        claimLine2 = findViewById(R.id.claimLine2);

        sudexo_next = findViewById(R.id.sudexo_next);
        hospital_details = findViewById(R.id.hospital_details);
        claim_submit = findViewById(R.id.claim_submit);
        claim_subtype = findViewById(R.id.claim_subtype);
        info_text= findViewById(R.id.info_text);
        user_detail = (LinearLayout) findViewById(R.id.user_detail);
        hospital_detail = (LinearLayout) findViewById(R.id.hospital_detail);
        claim_detail = (LinearLayout) findViewById(R.id.claim_detail);


        doc_name= (EditText) findViewById(R.id.doc_name);
        email_id = (EditText) findViewById(R.id.email_id);
        contact = (EditText) findViewById(R.id.contact);

        hos_date = (EditText) findViewById(R.id.hos_date);
        discharge_date = (EditText) findViewById(R.id.discharge_date);
        hos_name = (EditText) findViewById(R.id.hos_name);
        hos_address = (EditText) findViewById(R.id.hos_address);
        hos_reason = (EditText) findViewById(R.id.hos_reason);


        view_data = (Button) findViewById(R.id.view_data);
        policy_type_spin = (EditText) findViewById(R.id.policy_type_spin);
        policy_type_spin_no = (EditText) findViewById(R.id.policy_type_spin_no);
        patient_name_spin = findViewById(R.id.patient_name_spin);

        relationdrop = (EditText) findViewById(R.id.relationdrop);
        city = (EditText) findViewById(R.id.city);
        state = (EditText) findViewById(R.id.state);
        disease_name = (EditText) findViewById(R.id.disease_name);


        ghi_recycle = (RecyclerView) findViewById(R.id.ghi_recycle);


        a = (RelativeLayout) findViewById(R.id.a);
        b = (RelativeLayout) findViewById(R.id.b);
        c = (RelativeLayout) findViewById(R.id.c);

        Intent intent = getIntent();



        claim_id = intent.getStringExtra("claim_id");
        employee_id = intent.getStringExtra("employee_id");
        employer_id = intent.getStringExtra("employer_id");
        member_id = intent.getStringExtra("member_id");
        policy_id = intent.getStringExtra("policy_id");
        policy_type_id = intent.getStringExtra("policy_type_id");
        claim_type = intent.getStringExtra("claim_type");






        GetEmployeeId();


        //getCity(bank_name_value);

        view_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


              showDialog();


            }
        });


        user_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a.setVisibility(View.VISIBLE);
                b.setVisibility(View.GONE);
                c.setVisibility(View.GONE);
                claimLine1.setBackgroundResource(R.drawable.dash_line_);
                claimLine2.setBackgroundResource(R.drawable.dash_line_);

                sudexo_next.setBackgroundResource(R.drawable.circle_green);
                hospital_details.setBackgroundResource(R.drawable.circle_normal);
                claim_submit.setBackgroundResource(R.drawable.circle_normal);
               /* user_detail.setBackgroundResource(R.drawable.sudexo_cardview);
                hospital_detail.setBackgroundResource(R.drawable.edit_back);
                claim_detail.setBackgroundResource(R.drawable.edit_back);*/
            }
        });





        hospital_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.setVisibility(View.VISIBLE);
                a.setVisibility(View.GONE);
                c.setVisibility(View.GONE);
                claimLine1.setBackgroundResource(R.drawable.dash_line_active);
                claimLine2.setBackgroundResource(R.drawable.dash_line_);

                hospital_details.setBackgroundResource(R.drawable.circle_green);
                sudexo_next.setBackgroundResource(R.drawable.circle_normal);
                claim_submit.setBackgroundResource(R.drawable.circle_normal);


             /*   hospital_detail.setBackgroundResource(R.drawable.sudexo_cardview);
                user_detail.setBackgroundResource(R.drawable.edit_back);
                claim_detail.setBackgroundResource(R.drawable.edit_back);*/
            }
        });


        claim_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c.setVisibility(View.VISIBLE);
                b.setVisibility(View.GONE);
                a.setVisibility(View.GONE);

                claimLine1.setBackgroundResource(R.drawable.dash_line_);
                claimLine2.setBackgroundResource(R.drawable.dash_line_active);


                claim_submit.setBackgroundResource(R.drawable.circle_green);
                sudexo_next.setBackgroundResource(R.drawable.circle_normal);
                hospital_details.setBackgroundResource(R.drawable.circle_normal);
               /* claim_detail.setBackgroundResource(R.drawable.sudexo_cardview);
                user_detail.setBackgroundResource(R.drawable.edit_back);
                hospital_detail.setBackgroundResource(R.drawable.edit_back);*/
            }
        });

        int numberOfColumns = 1;
        ghi_recycle.setLayoutManager(new GridLayoutManager(ClaimDetailActivity.this, numberOfColumns));

        ghi_recycle.addOnItemTouchListener(new RecyclerTouchListener(ClaimDetailActivity.this, ghi_recycle, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager managerdd= new GridLayoutManager(ClaimDetailActivity.this, 1,
                GridLayoutManager.VERTICAL, false);
        ghi_recycle.setLayoutManager(managerdd);



    }



    private void showDialog() {
        Dialog myDialog;

        myDialog = new Dialog(ClaimDetailActivity.this);

        myDialog.setContentView(R.layout.bill_view);





        ImageView back= myDialog.findViewById(R.id.back);

        bill_nos= myDialog.findViewById(R.id.bill_nos);
        bill_dates= myDialog.findViewById(R.id.bill_dates);

        bill_amounts= myDialog.findViewById(R.id.bill_amounts);
        billcomments= myDialog.findViewById(R.id.billcomments);
        types= myDialog.findViewById(R.id.types);

        int numberOfColumns = 1;
        bill_nos.setLayoutManager(new GridLayoutManager(ClaimDetailActivity.this, numberOfColumns));

        bill_nos.addOnItemTouchListener(new RecyclerTouchListener(ClaimDetailActivity.this, bill_nos, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager managerd= new GridLayoutManager(ClaimDetailActivity.this, 1,
                GridLayoutManager.VERTICAL, false);
        bill_nos.setLayoutManager(managerd);




        bill_dates.setLayoutManager(new GridLayoutManager(ClaimDetailActivity.this, numberOfColumns));

        bill_dates.addOnItemTouchListener(new RecyclerTouchListener(ClaimDetailActivity.this, bill_dates, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager managerdd= new GridLayoutManager(ClaimDetailActivity.this, 1,
                GridLayoutManager.VERTICAL, false);
        bill_dates.setLayoutManager(managerdd);



        bill_amounts.setLayoutManager(new GridLayoutManager(ClaimDetailActivity.this, numberOfColumns));

        bill_amounts.addOnItemTouchListener(new RecyclerTouchListener(ClaimDetailActivity.this, bill_amounts, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager managerdf= new GridLayoutManager(ClaimDetailActivity.this, 1,
                GridLayoutManager.VERTICAL, false);
        bill_amounts.setLayoutManager(managerdf);



        billcomments.setLayoutManager(new GridLayoutManager(ClaimDetailActivity.this, numberOfColumns));

        billcomments.addOnItemTouchListener(new RecyclerTouchListener(ClaimDetailActivity.this, billcomments, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager managerdg= new GridLayoutManager(ClaimDetailActivity.this, 1,
                GridLayoutManager.VERTICAL, false);
        billcomments.setLayoutManager(managerdg);



        types.setLayoutManager(new GridLayoutManager(ClaimDetailActivity.this, numberOfColumns));

        types.addOnItemTouchListener(new RecyclerTouchListener(ClaimDetailActivity.this, types, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager managerdfgd= new GridLayoutManager(ClaimDetailActivity.this, 1,
                GridLayoutManager.VERTICAL, false);
        types.setLayoutManager(managerdfgd);


        ob_bill_no = new ArrayList<>();
        ob_bill_date = new ArrayList<>();
        ob_bill_amount = new ArrayList<>();
        ob_billcomment = new ArrayList<>();
        ob_type = new ArrayList<>();



        String url = con.base_url+"/api/admin/get/existing-claim-data";

        RequestQueue mRequestQueue = Volley.newRequestQueue(ClaimDetailActivity.this,
                new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject js = new JSONObject(response);


                            JSONObject data = js.getJSONObject("data");


                            String status = String.valueOf(js.getBoolean("status"));


                            if (status.equalsIgnoreCase("true")) {




                                ob_bill_no = new ArrayList<>();
                                bill_noadapter = new billsAdapter(ClaimDetailActivity.this, ob_bill_no);
                                bill_nos.setAdapter(bill_noadapter);

                                ob_bill_date = new ArrayList<>();
                                bill_dateadapter = new billsAdapter(ClaimDetailActivity.this, ob_bill_date);
                                bill_dates.setAdapter(bill_dateadapter);


                                ob_bill_amount = new ArrayList<>();
                                bill_amountadapter = new billsAdapter(ClaimDetailActivity.this, ob_bill_amount);
                                bill_amounts.setAdapter(bill_amountadapter);


                                ob_billcomment = new ArrayList<>();
                                billcommentadapter = new billsAdapter(ClaimDetailActivity.this, ob_billcomment);
                                billcomments.setAdapter(billcommentadapter);

                                ob_type = new ArrayList<>();
                                typetadapter = new billsAdapter(ClaimDetailActivity.this, ob_type);
                                types.setAdapter(typetadapter);


                                JSONObject tableBill=data.getJSONObject("tableBill");

                                JSONArray bill_no=tableBill.getJSONArray("bill_no");
                                for (int j = 0; j < bill_no.length(); j++) {

                                    String bills=bill_no.getString(j);

                                    ob_bill_no.add(new
                                            amount(bills));
                                }

                                bill_noadapter.notifyDataSetChanged();



                                JSONArray bill_date=tableBill.getJSONArray("bill_date");
                                for (int j = 0; j < bill_date.length(); j++) {

                                    String bills=bill_date.getString(j);

                                    ob_bill_date.add(new
                                            amount(bills));
                                }

                                bill_dateadapter.notifyDataSetChanged();



                                JSONArray bill_amt=tableBill.getJSONArray("bill_amt");
                                for (int j = 0; j < bill_amt.length(); j++) {

                                    String bills=bill_amt.getString(j);

                                    ob_bill_amount.add(new
                                            amount(bills));
                                }

                                bill_amountadapter.notifyDataSetChanged();



                                JSONArray comment=tableBill.getJSONArray("comment");
                                for (int j = 0; j < comment.length(); j++) {

                                    String bills=comment.getString(j);

                                    ob_billcomment.add(new
                                            amount(bills));
                                }

                                billcommentadapter.notifyDataSetChanged();


                                JSONArray reimburment_type=tableBill.getJSONArray("reimburment_type");
                                for (int j = 0; j < reimburment_type.length(); j++) {

                                    String bills=reimburment_type.getString(j);

                                    ob_type.add(new
                                            amount(bills));
                                }

                                typetadapter.notifyDataSetChanged();



                            }




                        } catch (JSONException jsonException) {
                            Log.d("ClaimError",jsonException.toString());
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

        params.put("claim_id", claim_type);
        params.put("employee_id", employee_id);
        params.put("employer_id", employer_id);
        params.put("member_id", member_id);
        params.put("policy_id", policy_id);
        params.put("policy_type_id", policy_type_id);
        params.put("claim_type","hospitalization");

        smr.setParams(params);
        mRequestQueue.add(smr);





        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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













    void GetEmployeeId(){
        String url = con.base_url+"/api/admin/get/existing-claim-data";

        RequestQueue mRequestQueue = Volley.newRequestQueue(ClaimDetailActivity.this,
                new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(String response) {
                        try {
                    JSONObject js = new JSONObject(response);


                    JSONObject data = js.getJSONObject("data");


                    String status = String.valueOf(js.getBoolean("status"));


                    if (status.equalsIgnoreCase("true")) {


                        String email = data.getString("email");
                        String mobile_no = data.getString("mobile_no");
                        String relation = data.getString("relation");
                        String admit_date = data.getString("admit_date");
                        String discharge_datse = data.getString("discharge_date");


                        String state_name = data.getString("state_name");
                        String city_name = data.getString("city_name");
                        String doctor_names = data.getString("doctor_name");
                        String hospital_name = data.getString("hospital_name");
                        String hospital_addres = data.getString("hospital_addres");

                        String disease = data.getString("disease");
                        String reason = data.getString("reason");

                        String patient_name = data.getString("patient_name");
                        String policy_name = data.getString("policy_name");
                        String policy_sub_type_name = data.getString("policy_sub_type_name");

                        String claim_hospitalization_type = data.getString("claim_hospitalization_type");




                        if(claim_hospitalization_type.equalsIgnoreCase("null")){
                            claim_subtype.setText("NA");
                        }else {
                            claim_subtype.setText(claim_hospitalization_type);
                        }

                        state.setText(state_name);
                        city.setText(city_name);
                        doc_name.setText(doctor_names);
                        hos_name.setText(hospital_name);
                        hos_address.setText(hospital_addres);

                        patient_name_spin.setText(patient_name);
                        relationdrop.setText(relation);

                        disease_name.setText(disease);

                        if(reason.equalsIgnoreCase("null")){
                            hos_reason.setText("NA");
                        }else {
                            hos_reason.setText(reason);
                        }

                        policy_type_spin_no.setText(policy_name);
                        policy_type_spin.setText(policy_sub_type_name);


                        email_id.setText(email);
                        contact.setText(mobile_no);
                        hos_date.setText(relation);



                        try{

                            String strCurrentDate = admit_date;
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            Date newDate = format.parse(strCurrentDate);

                            format = new SimpleDateFormat("dd-MM-yyyy");

                            hos_date.setText(format.format(newDate));
                        }catch (Exception e){
                            hos_date.setText(admit_date);
                        }


                        try{

                            String strCurrentDate = discharge_datse;
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            Date newDate = format.parse(strCurrentDate);

                            format = new SimpleDateFormat("dd-MM-yyyy");

                            discharge_date.setText(format.format(newDate));
                        }catch (Exception e){
                            discharge_date.setText(discharge_datse);
                        }



                        documentDataList = new ArrayList<>();

                        documentDataList = new ArrayList<>();
                        docuadapter = new DocumentAdapter(ClaimDetailActivity.this, documentDataList);
                        ghi_recycle.setAdapter(docuadapter);








                       JSONArray bill_no=data.getJSONArray("filenames");

                        for (int j = 0; j < bill_no.length(); j++) {
                            JSONObject jo_area = (JSONObject) bill_no.get(j);


                          String doc_name=jo_area.getString("doc_name");
                            String doc_link=jo_area.getString("doc_link");
                            String doc_type=jo_area.getString("doc_link");

                            documentDataList.add(new
                                    DocumentData(doc_name,doc_link,doc_type));
                        }

                        docuadapter.notifyDataSetChanged();



                        if(documentDataList.isEmpty()){
                            ghi_recycle.setVisibility(View.GONE);
                            info_text.setVisibility(View.VISIBLE);
                        }else {
                            ghi_recycle.setVisibility(View.VISIBLE);
                            info_text.setVisibility(View.GONE);
                        }


                    }




                            } catch (JSONException jsonException) {
                                Log.d("ClaimError",jsonException.toString());
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

        params.put("claim_id", claim_type);
        params.put("employee_id", employee_id);
        params.put("employer_id", employer_id);
        params.put("member_id", member_id);
        params.put("policy_id", policy_id);
        params.put("policy_type_id", policy_type_id);
        params.put("claim_type","hospitalization");

        smr.setParams(params);
        mRequestQueue.add(smr);



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





    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)ClaimDetailActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
