package com.palm.newbenefit.Fragment;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.palm.newbenefit.Activity.MainActivity;
import com.palm.newbenefit.ApiConfig.Constants;
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
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import static android.view.View.VISIBLE;


public class NewDashBoardFragment extends Fragment {


LinearLayout my_health,my_wellness,my_family,my_benifit,opd_care,surgery,my_doctor_call,connect_heal;
    public NewDashBoardFragment() {
        // Required empty public constructor
    }
    String user_id;
    SharedPreferences prefs;
    String token;
    Constants con;

    String network_hospital,
            claims,
            my_wellnesss,
            form_center,
            contact_us,
            help,
            enrollments,my_policy;
    TextView title,content;
    RelativeLayout announcement;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_new_dash_board, container, false);
        my_health=v.findViewById(R.id.my_health);
        my_wellness=v.findViewById(R.id.my_wellness);
        my_family=v.findViewById(R.id.my_family);
        my_benifit=v.findViewById(R.id.my_benifit);
        my_doctor_call=v.findViewById(R.id.my_doctor_call);
        surgery=v.findViewById(R.id.surgery);
        opd_care=v.findViewById(R.id.opd_care);
        connect_heal=v.findViewById(R.id.connect_heal);
        title=v.findViewById(R.id.title);
        content=v.findViewById(R.id.content);
        announcement=v.findViewById(R.id.announcement);
        con=new Constants();
        prefs = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);

        token = prefs.getString("api_token", null);
        user_id = prefs.getString("user_id", null);

        network_hospital = prefs.getString("network_hospital", null);
        help = prefs.getString("help", null);
        enrollments = prefs.getString("enrollment", null);
        claims = prefs.getString("claim", null);
        contact_us = prefs.getString("contact_us", null);
        my_policy = prefs.getString("my_policy", null);

        form_center = prefs.getString("form_center", null);
        my_wellnesss = prefs.getString("my_wellness", null);

        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).setTitle("");


        if(my_wellnesss.equalsIgnoreCase("yes")){
            my_wellness.setVisibility(View.VISIBLE);
        }else {
            my_wellness.setVisibility(View.GONE);
        }

        if(enrollments.equalsIgnoreCase("yes")){
            my_family.setVisibility(View.VISIBLE);
        }else {
            my_family.setVisibility(View.GONE);
        }

//        if(claims.equalsIgnoreCase("yes")){
//            my_health.setVisibility(View.VISIBLE);
//        }else {
//            my_health.setVisibility(View.GONE);
//        }
        my_health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(claims.equalsIgnoreCase("yes")){
                    SubmitClaimFragment travel = new SubmitClaimFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout_for_fragement, travel, travel.getTag()).addToBackStack("back").commit();

                }


            }
        });
        opd_care.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://mera-doc.com/meraclinic/corporate/Palm_Insurance_Brokers"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.android.chrome");
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    // Chrome browser presumably not installed so allow user to choose instead
                    intent.setPackage(null);
                 startActivity(intent);
                }


            }
        });
        surgery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.pristyncare.com/gp/partners-palm-broker"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.android.chrome");
                try {
                  startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    // Chrome browser presumably not installed so allow user to choose instead
                    intent.setPackage(null);
                    startActivity(intent);
                }


            }
        });

        connect_heal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectHealFragment travel = new ConnectHealFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout_for_fragement, travel, travel.getTag()).addToBackStack("back").commit();

            }
        });


        my_doctor_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             showDialog();
            }
        });


        my_wellness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(my_wellnesss.equalsIgnoreCase("yes")){
                    WellnessFragmentSecond travel = new WellnessFragmentSecond();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout_for_fragement, travel, travel.getTag()).addToBackStack("back").commit();


                }

            }
        });

        my_family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(enrollments.equalsIgnoreCase("yes")){
                    MyEnrollMentCards travel = new MyEnrollMentCards();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout_for_fragement, travel, travel.getTag()).addToBackStack("back").commit();


                }


            }
        });

        my_benifit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DashboardBenifitFragment travel = new DashboardBenifitFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout_for_fragement, travel, travel.getTag()).addToBackStack("back").commit();

            }
        });


       // show_doctorCall();

        Announcement();

        return v;
    }



    void show_doctorCall(){
    String url = con.base_url+"/api/admin/user";

        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);

                    Log.d("mydata",response);
                    JSONArray jsonObj=js.getJSONArray("data");

                    for (int i = 0; i < jsonObj.length(); i++) {
                        JSONObject explrObject = jsonObj.getJSONObject(0);





                        String employer_id = explrObject.getString("employer_id");




                        if(employer_id.equalsIgnoreCase("17")){
                          my_doctor_call.setVisibility(View.GONE);
                      }else {
                            my_doctor_call.setVisibility(View.GONE);
                        }

                       // ShowCardMapping(employer_id);

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
        rq.add(mStringRequest);
    }

    void ShowCardMapping(String employeeid){
        String url = con.base_url+"/api/broker/get/dashboard-card-mapping?employer_id="+employeeid;

        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);

                    Log.d("mydata",response);
                    JSONArray jsonObj=js.getJSONArray("data");



                    if(jsonObj.length()==0){
                        my_family.setVisibility(View.VISIBLE);
                        my_benifit.setVisibility(View.VISIBLE);
                        my_wellness.setVisibility(View.VISIBLE);
                        my_health.setVisibility(View.VISIBLE);
                    }




                    for (int i = 0; i < jsonObj.length(); i++) {
                        JSONObject explrObject = jsonObj.getJSONObject(i);


                        JSONObject card_details = explrObject.getJSONObject("card_details");

                        String heading = card_details.getString("heading");
                        String show_card = explrObject.getString("show_card");

                        if (heading.equalsIgnoreCase("My Family")) {

                            if (show_card.equalsIgnoreCase("0")) {
                                my_family.setVisibility(View.GONE);
                            } else {
                                my_family.setVisibility(View.VISIBLE);
                            }


                        }

                        if (heading.equalsIgnoreCase("My Benefits")) {

                            if (show_card.equalsIgnoreCase("0")) {
                                my_benifit.setVisibility(View.GONE);
                            } else {
                                my_benifit.setVisibility(View.VISIBLE);
                            }


                        }
                        if (heading.equalsIgnoreCase("My Wellness")) {

                            if (show_card.equalsIgnoreCase("0")) {
                                my_wellness.setVisibility(View.GONE);
                            } else {
                                my_wellness.setVisibility(View.VISIBLE);
                            }


                        }

                        if (heading.equalsIgnoreCase("My Claim")) {

                            if (show_card.equalsIgnoreCase("0")) {
                                my_health.setVisibility(View.GONE);
                            } else {
                                my_health.setVisibility(View.VISIBLE);
                            }


                        }

                    }

                    if(jsonObj.length()==0)
                    {
                        my_benifit.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {

                    my_benifit.setVisibility(View.VISIBLE);
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                    my_benifit.setVisibility(View.VISIBLE);

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
        rq.add(mStringRequest);
    }


    private void Announcement() {




        String url = con.base_url+"/api/admin/get/annoucement/module-wise?user_type_name=Employee";
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();

        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {

                    JSONObject data= new JSONObject(response);



                    String statusa= String.valueOf(data.getBoolean("status"));



                    if (statusa.equalsIgnoreCase("false")) {

                         announcement.setVisibility(View.GONE);
                    } else {
                        JSONArray array = data.getJSONArray("data");

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject explrObject = array.getJSONObject(i);

                            JSONArray module = explrObject.getJSONArray("module");

                            for (int j = 0; j < module.length(); j++) {
                                JSONObject explrObjects = module.getJSONObject(j);

                                String modulname=explrObjects.getString("module_name");
                                if(modulname.equalsIgnoreCase("Home")){
                                    String titles=explrObject.getString("title");
                                    String contents=explrObject.getString("content");
                                    title.setText(titles);
                                    content.setText(contents);
                                    announcement.setVisibility(VISIBLE);
                                }
                            }


                            }


                    }


                } catch (Exception e) {
                    Log.e("myerror", e.toString());
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


    private void showDialog() {
        Dialog myDialog;

        myDialog = new Dialog(getActivity());
        String selBankPath_cheque=null;
        TextView name, dob, gender, relation;
        ImageView dismiss;

        myDialog.setContentView(R.layout.show_dotor_image);

        dismiss = myDialog.findViewById(R.id.ab);
        RelativeLayout adhaarBackLayout_cheque1= myDialog.findViewById(R.id.adhaarBackLayout_cheque1);

        ImageView bankPreview1= myDialog.findViewById(R.id.bankPreview1);



        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });








        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        myDialog.show();
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