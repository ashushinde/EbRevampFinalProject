package com.palm.newbenefit.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.palm.newbenefit.Adapter.myPolicyAdapter;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.ApiConfig.RecyclerTouchListener;
import com.palm.newbenefit.Module.Policy;
import com.palm.newbenefit.R;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class MyPoliciesActivity extends AppCompatActivity {
    int gg;
    Constants con = null;
    Context context;
    String mobileNumber = null;
    String token = null;
    String user_id = null;
    List<Policy> ob = null;
    myPolicyAdapter adapter = null;
    RecyclerView recyclerView = null;
    int amountrs = 500;
    String emp_id;

    String company_id;
    ImageView info_text;
    Button group_cover,voluntary_cover;
    private RequestQueue requestQueue;
ImageView info_text_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_policies);
        con = new Constants();


        SharedPreferences prefs = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);

        token = prefs.getString("api_token", null);
        user_id = prefs.getString("user_id", null);

        recyclerView =findViewById(R.id.ghi_recycle);
        info_text=findViewById(R.id.info_text);
        info_text_back=findViewById(R.id.info_text_back);


        info_text_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        if (isNetworkAvailable()) {

            ob = new ArrayList<>();
            adapter = new myPolicyAdapter(this, ob);
            recyclerView.setAdapter(adapter);
            // demo();
            setBankDet("group");




            //Data();
        }else {
            new AlertDialog.Builder(this)
                    .setTitle("Error?")
                    .setMessage("Please Check Your Internet Connection")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
        }







        int numberOfColumns = 1;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager manager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);



    }


    private void setBankDet(String id) {

        String url = con.base_url+"/api/employee/get/mypolicy";

        RequestQueue mRequestQueue = Volley.newRequestQueue(MyPoliciesActivity.this,
                new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);

                    String statusa= String.valueOf(js.getBoolean("status"));

                    Log.d("response",response);









                    if (statusa.equalsIgnoreCase("false")){
                        info_text.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }else {
                        info_text.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);


                        JSONArray jsonObj=js.getJSONArray("data");

                        for (int j = 0; j < jsonObj.length(); j++) {

                            JSONObject jo_area = (JSONObject) jsonObj.get(j);

                            String id = jo_area.getString("id");
                            String cover_type = jo_area.getString("cover_type");
                            String policy_no = jo_area.getString("policy_no");
                            String company_name = (jo_area.getString("company_name"));
                            String suminsured = String.valueOf((jo_area.getInt("suminsured")));
                            String premium = (jo_area.getString("premium"));
                            String start_date = (jo_area.getString("start_date"));
                            String end_date = (jo_area.getString("end_date"));
                            String status = String.valueOf((jo_area.getInt("status")));
                            String renew_status = String.valueOf((jo_area.getInt("renew_status")));
                            String day_left = String.valueOf((jo_area.getInt("day_left")));
                            String redirect_url = (jo_area.getString("doc_path"));


                            ob.add(new Policy(id,cover_type,policy_no,company_name,
                                    suminsured,premium,start_date,end_date,status,renew_status
                                    ,day_left,redirect_url));

                        }


                        if(ob.isEmpty()){
                            info_text.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }else {
                            info_text.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }
                        adapter.notifyDataSetChanged();

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

            SSLContext context = SSLContext.getInstance("TLSv1.2");
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

