package com.palm.newbenefit.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.palm.newbenefit.Activity.MainActivity;
import com.palm.newbenefit.Adapter.ContactusLogAdapter;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.ApiConfig.RecyclerTouchListener;
import com.kmd.newbenefit.R;
import com.palm.newbenefit.models.ContactUsMain;
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

public class ContactLogFragment extends Fragment {

RecyclerView broker_recycle,
        employer_recycle,ic_recycle,tpa_recycle;
    public ContactLogFragment() {
        // Required empty public constructor
    }
    List<ContactUsMain> brokerlist = null;
    ContactusLogAdapter broker_adapter = null;

    List<ContactUsMain> employerlist = null;
    ContactusLogAdapter emp_adapter = null;

    List<ContactUsMain> tpalist = null;
    ContactusLogAdapter tpa_adapter = null;

    List<ContactUsMain> icelist = null;
    ContactusLogAdapter ice_adapter = null;
    Constants con = null;
    Context context;
    String token = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_contact_log, container, false);
        context=getActivity();
        broker_recycle=v.findViewById(R.id.broker_recycle);
        employer_recycle=v.findViewById(R.id.employer_recycle);
        ic_recycle=v.findViewById(R.id.ic_recycle);
        tpa_recycle=v.findViewById(R.id.tpa_recycle);

        con=new Constants();
        SharedPreferences prefs = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);
        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).setTitle("");


        token = prefs.getString("api_token", null);
        MyContactUs();

        //MyContactLog();



        int numberOfColumns = 1;
        ic_recycle.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));

        ic_recycle.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), ic_recycle, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager managersf = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        ic_recycle.setLayoutManager(managersf);




        broker_recycle.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));

        broker_recycle.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), broker_recycle, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager manager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        broker_recycle.setLayoutManager(manager);




        employer_recycle.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));

        employer_recycle.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), employer_recycle, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager managers = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        employer_recycle.setLayoutManager(managers);




        tpa_recycle.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));

        tpa_recycle.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), tpa_recycle, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager managerd = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        tpa_recycle.setLayoutManager(managerd);




        return v;
    }


    void MyContactUs(){

        tpalist = new ArrayList<>();
        tpa_adapter = new ContactusLogAdapter(context, tpalist);
        tpa_recycle.setAdapter(tpa_adapter);



        employerlist = new ArrayList<>();
        emp_adapter = new ContactusLogAdapter(context, employerlist);
        employer_recycle.setAdapter(emp_adapter);



         brokerlist = new ArrayList<>();
         broker_adapter = new ContactusLogAdapter(context, brokerlist);
         broker_recycle.setAdapter(broker_adapter);


         icelist = new ArrayList<>();
         ice_adapter = new ContactusLogAdapter(context, icelist);
         ic_recycle.setAdapter(ice_adapter);


        String url = con.base_url+"/api/employee/get/contact-logs";

        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js= null;
                    try {
                        js = new JSONObject(response);

                        Log.d("response",response);




                        String  status= String.valueOf(js.getBoolean("status"));

                        if(status.equalsIgnoreCase("false")){


                        }else {
                            JSONArray jsonObja = js.getJSONArray("data");




                                for (int j = 0; j < jsonObja.length(); j++) {

                                    JSONObject jo_area = (JSONObject) jsonObja.get(j);


                                    try {

                                        String type = jo_area.getString("type");

                                        if(type.equalsIgnoreCase("Broker")){

                                            JSONArray data = jo_area.getJSONArray("data");
                                            if(data.length()==0){
                                                broker_recycle.setVisibility(View.GONE);
                                            }else {
                                                broker_recycle.setVisibility(View.VISIBLE);
                                                JSONObject mydata = (JSONObject) data.get(0);
                                                brokerlist.add(new ContactUsMain("Broker", mydata.getString("broker_name")));
                                            }


                                        }else  if(type.equalsIgnoreCase("IC")){
                                            JSONArray data = jo_area.getJSONArray("data");
                                            if(data.length()==0){
                                                ic_recycle.setVisibility(View.GONE);
                                            }else {
                                                ic_recycle.setVisibility(View.VISIBLE);
                                                JSONObject mydata = (JSONObject) data.get(0);
                                                icelist.add(new ContactUsMain("IC", mydata.getString("insurer_name")));

                                            }


                                        }else  if(type.equalsIgnoreCase("Employer")){
                                            JSONArray data = jo_area.getJSONArray("data");

                                            if(data.length()==0){
                                                employer_recycle.setVisibility(View.GONE);
                                            }else {
                                                employer_recycle.setVisibility(View.VISIBLE);
                                                JSONObject mydata = (JSONObject) data.get(0);
                                                if(mydata.getString("employer_name").equalsIgnoreCase("null")){
                                                    employerlist.add(new ContactUsMain("Employer", mydata.getString("policy_number")));

                                                }else {
                                                    employerlist.add(new ContactUsMain("Employer", mydata.getString("employer_name")));
                                                }

                                            }


                                        }else  if(type.equalsIgnoreCase("TPA")){
                                            JSONArray data = jo_area.getJSONArray("data");

                                            if(data.length()==0){

                                                tpa_recycle.setVisibility(View.GONE);
                                            }else {
                                                tpa_recycle.setVisibility(View.VISIBLE);
                                                JSONObject mydata = (JSONObject) data.get(0);
                                                tpalist.add(new ContactUsMain("TPA", mydata.getString("tpa_name")));

                                            }


                                        }



                                    } catch (JSONException ex) {
                                        ex.printStackTrace();
                                    }


                                }


                            }
                        broker_adapter.notifyDataSetChanged();
                        tpa_adapter.notifyDataSetChanged();
                        emp_adapter.notifyDataSetChanged();
                        ice_adapter.notifyDataSetChanged();












                    }  catch (JSONException ex) {
                        ex.printStackTrace();
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