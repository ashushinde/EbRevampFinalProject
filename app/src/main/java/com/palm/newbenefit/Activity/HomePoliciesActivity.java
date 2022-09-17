package com.palm.newbenefit.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.palm.newbenefit.Adapter.HomePliciesAdapter;
import com.palm.newbenefit.ApiConfig.AnalyticsApplication;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.ApiConfig.RecyclerTouchListener;
import com.palm.newbenefit.Module.PolicyData;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class HomePoliciesActivity extends AppCompatActivity {
    Constants con = null;
    Context context;
    String mobileNumber = null;
    String token = null;
    String user_id = null;
    RecyclerView course_history_recycle;
    int gg;

    LinearLayout ll_main_data;
    RelativeLayout allempnotdatafound;
    List<PolicyData> ob = null;
    HomePliciesAdapter adapter = null;
    RecyclerView recyclerView = null;
    TextView tv_data_not_found;
    String coverName;
    // Toolbar settingtoolbar;
    ImageView info_text,info_textl;
    String company_id;
    String coverID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_policies);
        con = new Constants();
//        AnalyticsApplication.getInstance().trackEvent("Policy List Page", "Policy List  Page Displaying",
//                "Policy List Page Displaying");

        Intent intent = getIntent();
        coverName =  intent.getStringExtra("coverName");
        coverID =  intent.getStringExtra("coverID");
        info_text=findViewById(R.id.info_text);
        info_textl=findViewById(R.id.info_textl);
        info_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();

            }
        });


        recyclerView = findViewById(R.id.policy_recycle);

        ob = new ArrayList<>();
        adapter = new HomePliciesAdapter(this, ob);
        recyclerView.setAdapter(adapter);

        /*settingtoolbar = (Toolbar) findViewById(R.id.policy_home);
        setSupportActionBar(settingtoolbar);
        getSupportActionBar().setTitle("Policy");
        settingtoolbar.setTitleTextColor(0xFFFFFFFF);*/
        con = new Constants();
        context = getApplicationContext();

      /*  settingtoolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_chevron_left_black_24dp));
        settingtoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
*/
        SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);

        token = prefs.getString("api_token", null);






        if (isNetworkAvailable()) {

            setBankDet(coverID);

        }else {
            new AlertDialog.Builder(HomePoliciesActivity.this)
                    .setTitle("Error?")
                    .setMessage("Please Check Your Internet Connection")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
        }











        // Data();


        int numberOfColumns = 1;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                /* FamilyData train = ob.get(position);*/

                /*Intent intent = new Intent(getApplicationContext(), FamilyDataActivity.class);

                intent.putExtra("first_name", train.getFirst_name());
                intent.putExtra("last_name", train.getLast_name());
                intent.putExtra("address", train.getAddress());
                intent.putExtra("number", train.getContact_number());
                intent.putExtra("dob", train.getDate_of_birth());
                intent.putExtra("relation", train.getRelation());
                intent.putExtra("location", train.getLocation());
                intent.putExtra("pincode", train.getPincode());
                intent.putExtra("city", train.getCity());
                intent.putExtra("state", train.getState());
                startActivity(intent);*/


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


    }


    private void setBankDet(String coverID) {
        String url = con.base_url+"/api/employee/get/dashboard";

        RequestQueue mRequestQueue = Volley.newRequestQueue(HomePoliciesActivity.this,
                new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js= null;
                    try {
                        js = new JSONObject(response);
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }

                    Log.d("response",response);


                    JSONArray  jsonObj= null;
                    try {
                        jsonObj = js.getJSONArray("data");
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }

                    if (jsonObj.length() == 0){
                        info_textl.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }else {
                        info_textl.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);




                        for (int j = 0; j < jsonObj.length(); j++) {

                            JSONObject jo_area = null;
                            try {
                                jo_area = (JSONObject) jsonObj.get(j);
                            } catch (JSONException ex) {
                                ex.printStackTrace();
                            }

                            String id = null;
                            try {
                                id = jo_area.getString("policy_id");

                            String pol_id = jo_area.getString("policy_number");
                            String in_co_name = jo_area.getString("insurer_name");
                            String pol_mem_insured = (jo_area.getString("suminsured"));
                            String cover_balance = (jo_area.getString("suminsured"));
                           String pol_mem_pre = (jo_area.getString("benifit_manual"));
                            String pol_sub_type_name = (jo_area.getString("policy_name"));
                            String policy_sub_type_image_path = (jo_area.getString("insurer_logo"));
                            String insurer_companies_img_path = (jo_area.getString("insurer_logo"));
                            //String policy_sub_type_id = (jo_area.getString("policy_sub_type_id"));
                            int member_count = (jo_area.getInt("total_member"));
//                                String members = (jo_area.getString("members"));
//                                String opd = (jo_area.getString("Opd_sum_insure"));
//                                // String opd = "5000";
//
//                                String member = String.valueOf(member_count);

                                String member= String.valueOf(member_count);


                                if(coverID.equalsIgnoreCase(pol_id)){
                                    ob.add(new PolicyData(id, pol_id, in_co_name, String.valueOf(pol_mem_insured), pol_mem_pre, pol_sub_type_name, policy_sub_type_image_path, insurer_companies_img_path, "", member, member,coverName));

                                }




                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }

                            adapter.notifyDataSetChanged();
                    }


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
        mRequestQueue.add(mStringRequest);
}


    public void Data() {

        ob.add(new PolicyData("1", "1", "as", String.valueOf(1200), "123", "GMC", "sf", "sdfs", "2", "12", "10"));
        ob.add(new PolicyData("1", "1", "as", String.valueOf(1200), "123", "GMC", "sf", "sdfs", "2", "12", "10"));
        ob.add(new PolicyData("1", "1", "as", String.valueOf(1200), "123", "GMC", "sf", "sdfs", "2", "12", "10"));
        ob.add(new PolicyData("1", "1", "as", String.valueOf(1200), "123", "GMC", "sf", "sdfs", "2", "12", "10"));
        ob.add(new PolicyData("1", "1", "as", String.valueOf(1200), "123", "GMC", "sf", "sdfs", "2", "12", "10"));
        ob.add(new PolicyData("1", "1", "as", String.valueOf(1200), "123", "GMC", "sf", "sdfs", "2", "12", "10"));
        ob.add(new PolicyData("1", "1", "as", String.valueOf(1200), "123", "GMC", "sf", "sdfs", "2", "12", "10"));
        adapter.notifyDataSetChanged();

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

