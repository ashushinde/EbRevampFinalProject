package com.palm.newbenefit.Activity;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.palm.newbenefit.Adapter.ViewImageAdapter;
import com.palm.newbenefit.Adapter.ViewImageAdapterDoc;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.DatabaseHelper.DBHelper;
import com.palm.newbenefit.Module.ImageData;
import com.palm.newbenefit.Module.Wellness;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class ViewDataActivtyDoc extends AppCompatActivity {
    Constants con = null;
    Context context;
    String mobileNumber = null;
    String token = null;
    String user_id = null;
    RecyclerView course_history_recycle;
    int gg;
    SearchView mSearcha;
    LinearLayout ll_main_data;
    RelativeLayout allempnotdatafound;
    // List<BillAllData> ob;
    ViewImageAdapterDoc adapter = null;
    RecyclerView recyclerView = null;
    TextView tv_data_not_found;
    DBHelper db;
    ImageData bill;
    ImageView info_text;
    String claimid;
    private List<ImageData> ob ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data_activty);
        con = new Constants();
        recyclerView = findViewById(R.id.policy_recycle);
        // bill=new BillAllData();
        ob =new ArrayList<ImageData>();
        db=new DBHelper(this);
        info_text=findViewById(R.id.info_text);
        Intent intent = getIntent();
        SharedPreferences prefs =getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);

        token = prefs.getString("api_token", null);
        user_id = prefs.getString("user_id", null);



        claimid = intent.getStringExtra("claim_id");

        info_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();

            }
        });
        GetEmployeeId();











       /* SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        mobileNumber = prefs.getString("mobileNumber", null);
        token = prefs.getString("token", null);
        user_id = prefs.getString("user_id", null);



        // Data();


        int numberOfColumns = 1;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                *//* FamilyData train = ob.get(position);*//*

         *//*Intent intent = new Intent(getApplicationContext(), FamilyDataActivity.class);

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
                startActivity(intent);*//*


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

*/
    }


















    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    void GetEmployeeId(){
        String url = con.base_url+"/api/admin/get-claim-data";


        RequestQueue mRequestQueue = Volley.newRequestQueue(ViewDataActivtyDoc.this,
                new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject explrObjectd=new JSONObject(response);

                            JSONObject explrObject=explrObjectd.getJSONObject("data");

                            JSONArray tpa_claim_documents = explrObject.getJSONArray("tpa_claim_documents");


                            for (int j = 0; j < tpa_claim_documents.length(); j++) {

                                JSONObject objects=tpa_claim_documents.getJSONObject(j);

                                String id = String.valueOf(tpa_claim_documents.get(j));
                                String document_url = objects.getString("document_url");
                                String document_name = objects.getString("document_name");









                                ob.add(new ImageData(document_url,document_name));


                            }

                            adapter = new ViewImageAdapterDoc(ViewDataActivtyDoc.this, ob);



                            RecyclerView.LayoutManager reLayoutManager =new LinearLayoutManager(ViewDataActivtyDoc.this);
                            recyclerView.setLayoutManager(reLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(adapter);




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


        params.put("claim_id", claimid);


        smr.setParams(params);




        mRequestQueue.add(smr);





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
