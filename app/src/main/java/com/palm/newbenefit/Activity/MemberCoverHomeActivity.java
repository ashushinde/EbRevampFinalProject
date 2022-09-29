package com.palm.newbenefit.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
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
import com.palm.newbenefit.Adapter.PolicyMemberHomeAdapter;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.ApiConfig.RecyclerTouchListener;
import com.palm.newbenefit.Module.MemberData;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class MemberCoverHomeActivity extends AppCompatActivity {
    Constants con = null;
    Context context;
    String mobileNumber = null;
    String token = null;
    String user_id = null;
    RecyclerView course_history_recycle;
    SearchView mSearcha;
    LinearLayout ll_main_data;
    RelativeLayout allempnotdatafound;
    String emp_firstname, member_id;
    List<MemberData> ob = null;
    PolicyMemberHomeAdapter adapter = null;
    RecyclerView recyclerView = null;
    String mm;

    String booking_status;
    TextView tv_data_not_found;
    Toolbar settingtoolbar;
    ImageView info_textl;
    String emp_id,coverName;
    ImageView info_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_cover_home);
        con = new Constants();
        SharedPreferences prefs = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);


        token = prefs.getString("api_token", null);


        info_text=findViewById(R.id.info_text);

        info_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();

            }
        });
        Intent intent = getIntent();
        booking_status = intent.getStringExtra("pol");





        recyclerView = findViewById(R.id.member_recycle);
        info_textl = findViewById(R.id.info_textl);


        //Data();


        int numberOfColumns = 1;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                MemberData train = ob.get(position);

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



    @Override
    public void onResume() {
        super.onResume();


        ob = new ArrayList<>();
        adapter = new PolicyMemberHomeAdapter(this, ob, emp_id, booking_status);
        recyclerView.setAdapter(adapter);

        if (isNetworkAvailable()) {

            setBankDet();

        }else {
            new AlertDialog.Builder(MemberCoverHomeActivity.this)
                    .setTitle("Error?")
                    .setMessage("Please Check Your Internet Connection")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
        }









    }

    private void setBankDet() {
        ob = new ArrayList<>();
        adapter = new PolicyMemberHomeAdapter(this, ob, emp_id, booking_status);
        recyclerView.setAdapter(adapter);
        RequestQueue rq = Volley.newRequestQueue(MemberCoverHomeActivity.this,
                new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        String url = con.base_url+"/api/employee/get/member-details";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {


                            JSONObject js=new JSONObject(response);

                            Log.d("response",response);







                            JSONArray  jsonObj=js.getJSONArray("data");



                            for (int k = 0; k < jsonObj.length(); k++) {

                                JSONObject jo_areaa = (JSONObject) jsonObj.get(k);
                                String policy_sub_type_id = jo_areaa.getString("policy_sub_type_id");
                                String policy_id = jo_areaa.getString("policy_id");
                                 coverName = jo_areaa.getString("policy_sub_type_name");
                                JSONArray  arraymembers=jo_areaa.getJSONArray("members");


                                if (arraymembers.length()==0) {
                                    info_textl.setVisibility(View.VISIBLE);
                                    recyclerView.setVisibility(View.GONE);
                                } else {
                                    info_textl.setVisibility(View.GONE);
                                    recyclerView.setVisibility(View.VISIBLE);



                            for (int j = 0; j < arraymembers.length(); j++) {

                                JSONObject jo_area = (JSONObject) arraymembers.get(j);



                                //  String family_relation_id = jo_area.getString("family_relation_id");
                                // String policy_no = (jo_area.getString("policy_no"));
                                // String family_id = (jo_area.getString("family_id"));
                                String fr_id = (jo_area.getString("last_name"));
                                String emp_lastname = (jo_area.getString("last_name"));

                                emp_firstname = (jo_area.getString("first_name"));


                                String fr_name = (jo_area.getString("relation"));


                                if (fr_id.equalsIgnoreCase("0")) {

                                    member_id = (jo_area.getString("ecard_url"));
                                } else {

                                    member_id = (jo_area.getString("ecard_url"));
                                }
                                // String bdate = (jo_area.getString("bdate"));

                                String tpa_member_id = (jo_area.getString("tpa_member_id"));


                                String policy_mem_sum_insured = String.valueOf((jo_area.getString("suminsured")));
                                String policy_mem_sum_premium = (jo_area.getString("ecard_url"));
                                String start_date = (jo_area.getString("cover_start_date"));
                                String cover_end_date = (jo_area.getString("cover_end_date"));

                                String dob = (jo_area.getString("dob"));
                                String status = (jo_area.getString("ecard_url"));
                                String TPA_id = (jo_area.getString("relation"));
                                String client_id = (jo_area.getString("ecard_url"));

                                String tpa_member_name = (jo_area.getString("tpa_member_name"));
                                String data;
                                mm= (jo_area.getString("enrollement_status"));

                                String emp_code;

                                if(policy_sub_type_id.equalsIgnoreCase("1")||policy_sub_type_id.equalsIgnoreCase("4")){

                                    emp_code = (jo_area.getString("tpa_member_id"));
                                }
                                else if (policy_sub_type_id.equalsIgnoreCase("2")||policy_sub_type_id.equalsIgnoreCase("3")||policy_sub_type_id.equalsIgnoreCase("5")
                                        ||policy_sub_type_id.equalsIgnoreCase("6")){
                                    emp_code = (jo_area.getString("emp_code"));

                                }else {
                                    emp_code = (jo_area.getString("member_id"));

                                }
                               String emp_codes = (jo_area.getString("member_id"));
                                String opd_suminsured = (jo_area.getString("opd_suminsured"));
                                String gender = (jo_area.getString("gender"));
                                String image_url = (jo_area.getString("image_url"));

                                ob.add(new MemberData(emp_firstname, member_id, fr_name, dob,
                                        policy_mem_sum_insured, start_date, mm, TPA_id, emp_lastname,
                                        tpa_member_name, gender, coverName, emp_code, policy_sub_type_id,
                                        cover_end_date,opd_suminsured,image_url,emp_codes));
                            }
                                }
                            }
                            adapter.notifyDataSetChanged();


                        } catch (Exception e) {
                            Log.e("OnMemberError", e.toString());
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

        /*      smr.addStringParam("token", token);*/


        HashMap<String, String> params = new HashMap<>();

        Log.d("policy_id", booking_status);


        params.put("policy_id", booking_status);


        smr.setParams(params);
        rq.add(smr);



    }


    public void Data() {








      /*   ob.add(new MemberData("Ashu","1","Daughter","16/08/1993","123","10/8/2018","InProgress","aa"));
         ob.add(new MemberData("Monal","1","Daughter","16/08/1993","123","10/8/2018","Active","aa"));
         ob.add(new MemberData("Namrata","1","Daughter","16/08/1993","123","10/8/2018","InProgress","aa"));
         ob.add(new MemberData("Sidhesh","1","SON","16/08/1993","123","10/8/2018","Active","aa"));
         ob.add(new MemberData("Sadhna","1","Daughter","16/08/1993","123","10/8/2018","InProgress","aa"));
*/

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

