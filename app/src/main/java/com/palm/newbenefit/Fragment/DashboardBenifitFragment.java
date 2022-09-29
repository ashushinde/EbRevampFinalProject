package com.palm.newbenefit.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.palm.newbenefit.Activity.MainActivity;
import com.palm.newbenefit.Adapter.DashBenifitTabAdapter;
import com.palm.newbenefit.Adapter.DashBenifitTabAdaptersingle;
import com.palm.newbenefit.Adapter.DashBenifitTabAdaptertwogroup;
import com.palm.newbenefit.Adapter.DashBenifitTabAdaptertwovol;
import com.palm.newbenefit.Adapter.FormAdapter;
import com.palm.newbenefit.ApiConfig.Constants;
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
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class DashboardBenifitFragment extends Fragment {

    private ViewPager viewPager;
    Button plan, summary;
    Bundle bundle;
    boolean respnce = false;
    FormAdapter adapter;
    Constants con;
    LinearLayout group_cover,form_center_two,form_center_three;



    ArrayList<String>obGroup;
    ArrayList<String>obVol;

    String token = null;
    TextView covernot_found;
    public DashboardBenifitFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_dashboard_benifit, container, false);
        con = new Constants();

        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).setTitle(" ");


        SharedPreferences prefs = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);
        token = prefs.getString("api_token", null);

        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).setTitle("");



        group_cover= (LinearLayout) v.findViewById(R.id.group_cover);
        form_center_two= (LinearLayout) v.findViewById(R.id.form_center_two);
        form_center_three= (LinearLayout) v.findViewById(R.id.form_center_three);
        covernot_found=  v.findViewById(R.id.covernot_found);
        viewPager = (ViewPager) v.findViewById(R.id.pager);


        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (viewPager.getCurrentItem() == 0) {
                    viewPager.setCurrentItem(-1, false);
                    return true;
                }
                else if (viewPager.getCurrentItem() == 1) {
                    viewPager.setCurrentItem(1, false);
                    return true;
                }
                else if (viewPager.getCurrentItem() == 2) {
                    viewPager.setCurrentItem(2, false);
                    return true;
                }
                return true;
            }
        });



        setBankDet();



        //Creating our pager adapter

        //Adding adapter to pager



        group_cover.setBackgroundResource(R.drawable.click_on_tab);
        form_center_two.setBackgroundResource(R.drawable.click_tab);
        form_center_three.setBackgroundResource(R.drawable.click_tab);

        group_cover.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {



                viewPager.setCurrentItem(0);
                group_cover.setBackgroundResource(R.drawable.click_on_tab);
                form_center_two.setBackgroundResource(R.drawable.click_tab);
                form_center_three.setBackgroundResource(R.drawable.click_tab);

            }
        });

        form_center_two.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                if(group_cover.getVisibility()==View.VISIBLE){
                    viewPager.setCurrentItem(1);
                    form_center_two.setBackgroundResource(R.drawable.click_on_tab);
                    group_cover.setBackgroundResource(R.drawable.click_tab);
                    form_center_three.setBackgroundResource(R.drawable.click_tab);
                }else {
                    viewPager.setCurrentItem(0);
                    form_center_two.setBackgroundResource(R.drawable.click_on_tab);
                    group_cover.setBackgroundResource(R.drawable.click_tab);
                    form_center_three.setBackgroundResource(R.drawable.click_tab);

                }



            }
        });

        form_center_three.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                if(group_cover.getVisibility()==View.VISIBLE){
                  if(form_center_two.getVisibility()==View.VISIBLE){
                      viewPager.setCurrentItem(2);
                      form_center_three.setBackgroundResource(R.drawable.click_on_tab);
                      form_center_two.setBackgroundResource(R.drawable.click_tab);
                      group_cover.setBackgroundResource(R.drawable.click_tab);
                  }else {
                      viewPager.setCurrentItem(2);
                      form_center_three.setBackgroundResource(R.drawable.click_on_tab);
                      form_center_two.setBackgroundResource(R.drawable.click_tab);
                      group_cover.setBackgroundResource(R.drawable.click_tab);
                  }
                }else {
                    if(form_center_two.getVisibility()==View.VISIBLE){
                        viewPager.setCurrentItem(1);
                        form_center_three.setBackgroundResource(R.drawable.click_on_tab);
                        form_center_two.setBackgroundResource(R.drawable.click_tab);
                        group_cover.setBackgroundResource(R.drawable.click_tab);

                    }else {
                        viewPager.setCurrentItem(0);
                        form_center_three.setBackgroundResource(R.drawable.click_on_tab);
                        form_center_two.setBackgroundResource(R.drawable.click_tab);
                        group_cover.setBackgroundResource(R.drawable.click_tab);

                    }

                }



            }
        });



        return v;


    }


    private void setBankDet() {

        obGroup=new ArrayList<>();
        obVol=new ArrayList<>();

        String url = con.base_url+"/api/employee/get/dashboard";

        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);

                    Log.d("response",response);




                    String  status= String.valueOf(js.getBoolean("status"));

                    if(status.equalsIgnoreCase("false")){
                        group_cover.setVisibility(View.GONE);
                        form_center_two.setVisibility(View.GONE);
                    }else {
                        JSONArray jsonObj=js.getJSONArray("data");

                        if (jsonObj.length() == 0){
                            group_cover.setVisibility(View.GONE);
                            form_center_two.setVisibility(View.GONE);
                        }else {



                            for (int j = 0; j < jsonObj.length(); j++) {

                                JSONObject jo_area = (JSONObject) jsonObj.get(j);

                                String premium = (jo_area.getString("premium"));


                                if(premium.isEmpty()||premium.equalsIgnoreCase("null")
                                        ||premium.equalsIgnoreCase("0")){

                                    obGroup.add(premium);
                                }else {

                                    obVol.add(premium);

                                }




                            }


                            if(obGroup.size()==0){
                                group_cover.setVisibility(View.GONE);
                            }else {


                                group_cover.setVisibility(View.VISIBLE);
                            }

                            if(obVol.size()==0){

                                form_center_two.setVisibility(View.GONE);
                            }else {
                                form_center_two.setVisibility(View.VISIBLE);
                            }



                        }




                    }


                    if(group_cover.getVisibility()==View.VISIBLE){
                        covernot_found.setVisibility(GONE);
                        if(form_center_two.getVisibility()==View.VISIBLE){
                            DashBenifitTabAdapter adapter = new DashBenifitTabAdapter(getFragmentManager(), 3);
                            viewPager.setAdapter(adapter);
                            covernot_found.setVisibility(GONE);
                            form_center_three.setVisibility(VISIBLE);
                        }else {
                            covernot_found.setVisibility(GONE);
                            form_center_three.setVisibility(VISIBLE);
                            DashBenifitTabAdaptertwogroup adapter = new DashBenifitTabAdaptertwogroup(getFragmentManager(), 2);
                            viewPager.setAdapter(adapter);
                        }

                    }else {
                        if(form_center_two.getVisibility()==View.VISIBLE){
                            covernot_found.setVisibility(GONE);
                            DashBenifitTabAdaptertwovol adapter = new DashBenifitTabAdaptertwovol(getFragmentManager(), 2);
                            viewPager.setAdapter(adapter);
                            form_center_three.setVisibility(VISIBLE);
                        }else {
                            setBankDetTerm();
                            DashBenifitTabAdaptersingle adapter = new DashBenifitTabAdaptersingle(getFragmentManager(), 1);
                            viewPager.setAdapter(adapter);



                        }

                    }


                    if(group_cover.getVisibility()==View.VISIBLE){
                        if(form_center_two.getVisibility()==View.VISIBLE){

                            form_center_three.setBackgroundResource(R.drawable.click_tab);
                            form_center_two.setBackgroundResource(R.drawable.click_tab);
                            group_cover.setBackgroundResource(R.drawable.click_on_tab);
                        }
                    }else {
                        if(form_center_two.getVisibility()==View.VISIBLE){

                            form_center_three.setBackgroundResource(R.drawable.click_tab);
                            form_center_two.setBackgroundResource(R.drawable.click_on_tab);
                            group_cover.setBackgroundResource(R.drawable.click_tab);

                        }else {

                            form_center_three.setBackgroundResource(R.drawable.click_on_tab);
                            form_center_two.setBackgroundResource(R.drawable.click_tab);
                            group_cover.setBackgroundResource(R.drawable.click_tab);

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


    private void setBankDetTerm() {




        String url = con.base_url+"/api/employee/get/all-policy-member-app";
        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

//                int allsum = 0;
                        Double allPremium=0.0;
                        try {

                            JSONObject data= new JSONObject(response);

                            Log.e("group_response", data.toString());

                            String statusa= String.valueOf(data.getBoolean("status"));



                            if (statusa.equalsIgnoreCase("false")) {
                               form_center_three.setVisibility(GONE);
                                covernot_found.setVisibility(VISIBLE);
                                viewPager.setVisibility(GONE);

                            } else {
                                viewPager.setVisibility(VISIBLE);
                                form_center_three.setVisibility(View.VISIBLE);
                                covernot_found.setVisibility(VISIBLE);



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
        rq.add(mStringRequest);
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