package com.palm.newbenefit.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.palm.newbenefit.R;
import com.palm.tatarewamp.SslData.NullHostNameVerifier;

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
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class TrackActivity extends AppCompatActivity {
    Constants con = null;
    TextView register, insurar;
    String policy, token;
    String member;
    String claim;
    TextView second, first;
    ImageView info_text;
    TextView claim_date,claim_id,dob,relation,member_name,emp_name,claim_deduct,claim_stle_amt,claim_amount,claim_apr_amount;
    LinearLayout datas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
        con = new Constants();

        SharedPreferences prefs = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);

        token = prefs.getString("api_token", null);

        register = findViewById(R.id.register);
        insurar = findViewById(R.id.insurar);


        member_name = findViewById(R.id.member_name);
        claim_date = findViewById(R.id.claim_date);
        claim_id = findViewById(R.id.claim_id);
        dob = findViewById(R.id.dob);
        relation = findViewById(R.id.relation);
        emp_name = findViewById(R.id.emp_name);
        claim_deduct= findViewById(R.id.claim_deduct);
        claim_stle_amt= findViewById(R.id.claim_stle_amt);
        claim_amount= findViewById(R.id.claim_amount);
        claim_apr_amount= findViewById(R.id.claim_apr_amount);

        datas= findViewById(R.id.data);
        info_text=findViewById(R.id.info_text);

        info_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();

            }
        });


        Intent intent = getIntent();
        policy = intent.getStringExtra("policy");


         member = intent.getStringExtra("member");

        claim = intent.getStringExtra("claim");

        second = findViewById(R.id.second);

        first = findViewById(R.id.first);







        if (isNetworkAvailable()) {


         //   setCertData();
            details();

        }else {
            new AlertDialog.Builder(TrackActivity.this)
                    .setTitle("Error?")
                    .setMessage("Please Check Your Internet Connection")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
        }







    }


    public void setCertData() {


    RequestQueue rq = Volley.newRequestQueue(TrackActivity.this, new HurlStack(null, getSocketFactory()));
rq.getCache().clear();
        String url = con.base_url + "/api/admin/get/trackClaim";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

Log.d("claimresponse",response);



                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });




        //smr.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));



       /* smr.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue mRequestQueue = Volley.newRequestQueue(TrackActivity.this);
        mRequestQueue.add(smr);*/


        HashMap<String, String> params = new HashMap<>();
        params.put("claim_id", claim);
        Log.d("claim_id", claim);


        smr.setParams(params);
        rq.add(smr);




    }



    public void details() {

       // RequestQueue rq = Volley.newRequestQueue(TrackActivity.this);


      RequestQueue rq = Volley.newRequestQueue(TrackActivity.this, new HurlStack(null, getSocketFactory()));
rq.getCache().clear();
        String url = con.base_url + "/api/admin/get/trackClaim";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            Log.d("claimresponse",response);

                            JSONObject jsonObj = new JSONObject(response);
                            String status= String.valueOf(jsonObj.getBoolean("status"));






if(status.equalsIgnoreCase("false")){
    String message=jsonObj.getString("errors");
    new AlertDialog.Builder(TrackActivity.this)
            .setTitle("Error")
            .setMessage(message)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setNegativeButton(android.R.string.ok, null).show();



}else {
    JSONObject data=jsonObj.getJSONObject("data");
    String dateStra = data.getString("document_submited");

    if (!(dateStra.equals("null")||dateStra.equalsIgnoreCase(""))) {




        insurar.setText(dateStra);



        second.setBackgroundResource(R.drawable.circle);

    } else {

        second.setBackgroundResource(R.drawable.circle_blue);

    }



    String dateStr = data.getString("claim_register");
    if (!(dateStr.equals("null")||dateStr.equalsIgnoreCase(""))) {


        register.setText(dateStr);


        first.setBackgroundResource(R.drawable.circle);
    } else {
        first.setBackgroundResource(R.drawable.circle_blue);



    }










    String claim_register_datea=data.getString("claim_register");
    String employee_namea=data.getString("employee_name");
    String member_namea=data.getString("member_name");
    //  String reject_easona=data.getString("reject_eason");
    String claim_Settled_Datea=data.getString("claim_settled");
    String discripancya=data.getString("discripancy");
    String claimed_amount=data.getString("claimed_amount");
    String claim_settled_amount=data.getString("claim_settled_amount");
    String deduction_amount=data.getString("deduction_amount");
    String claim_ids=data.getString("claim_id");
    String Claim_approved_amount=data.getString("Claim_approved_amount");

    if(claim_ids.isEmpty()
            ||claim_ids.equalsIgnoreCase("null")||
            claim_ids.equalsIgnoreCase("NULL")){


    }else {
        claim_id.setText(claim_ids);
    }


    if(claimed_amount.isEmpty()
            ||claimed_amount.equalsIgnoreCase("null")||
            claimed_amount.equalsIgnoreCase("NULL")){


    }else {
        claim_amount.setText(claimed_amount);
    }

    if(claim_settled_amount.isEmpty()
            ||claim_settled_amount.equalsIgnoreCase("null")||
            claim_settled_amount.equalsIgnoreCase("NULL")){


    }else {
        claim_stle_amt.setText(claim_settled_amount);
    }


    if(deduction_amount.isEmpty()
            ||deduction_amount.equalsIgnoreCase("null")||
            deduction_amount.equalsIgnoreCase("NULL")){


    }else {
        claim_deduct.setText(deduction_amount);
    }


    if(Claim_approved_amount.isEmpty()
            ||Claim_approved_amount.equalsIgnoreCase("null")||
            Claim_approved_amount.equalsIgnoreCase("NULL")){


    }else {
        claim_apr_amount.setText(Claim_approved_amount);
    }


    if(employee_namea.isEmpty()
        ||employee_namea.equalsIgnoreCase("null")||
        employee_namea.equalsIgnoreCase("NULL")){


        }else {

    datas.setVisibility(View.VISIBLE);
    emp_name.setText(employee_namea);
        }

    if(claim_Settled_Datea.isEmpty()
            ||claim_Settled_Datea.equalsIgnoreCase("null")||
            claim_Settled_Datea.equalsIgnoreCase("NULL")){


    }else {
        claim_date.setText(claim_Settled_Datea);
    }



    if(member_namea.isEmpty()
            ||member_namea.equalsIgnoreCase("null")||
            member_namea.equalsIgnoreCase("NULL")){


    }else {
        member_name.setText(member_namea);
    }


    if(claim_register_datea.isEmpty()
            ||claim_register_datea.equalsIgnoreCase("null")||
            claim_register_datea.equalsIgnoreCase("NULL")){


    }else {
        dob.setText(claim_register_datea);
    }

    if(discripancya.isEmpty()
            ||discripancya.equalsIgnoreCase("null")||
            discripancya.equalsIgnoreCase("NULL")){


    }else {
        relation.setText(discripancya);
    }







    //reject_reason.setText(reject_easona);






}






                        } catch (Exception e) {
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

        params.put("tpa_member_id", member);
        params.put("claim_id", claim);
        params.put("member_id", policy);
        // smr.addStringParam("policy_id", policy);



       Log.d("tpa_member_id", member);
        Log.d("claim_id", claim);
        Log.d("member_id", policy);
        //   Log.d("token", token);
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

