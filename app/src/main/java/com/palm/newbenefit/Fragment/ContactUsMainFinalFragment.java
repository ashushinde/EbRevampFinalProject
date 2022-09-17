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
import com.palm.newbenefit.Adapter.ContactusmainAdapter;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.ApiConfig.RecyclerTouchListener;
import com.palm.newbenefit.R;
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

public class ContactUsMainFinalFragment extends Fragment {

    public ContactUsMainFinalFragment() {
        // Required empty public constructor
    }


    RecyclerView broker_recycle,employer_recycle,tpa_recycle;


    List<ContactUsMain> brokerlist = null;
    ContactusmainAdapter broker_adapter = null;

    List<ContactUsMain> employerlist = null;
    ContactusmainAdapter emp_adapter = null;

    List<ContactUsMain> tpalist = null;
    ContactusmainAdapter tpa_adapter = null;

    Constants con = null;
    Context context;
    String token = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_contact_us_main_final, container, false);
        con=new Constants();
        SharedPreferences prefs = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);
        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).setTitle("");


        token = prefs.getString("api_token", null);
        broker_recycle=v.findViewById(R.id.broker_recycle);
        employer_recycle=v.findViewById(R.id.employer_recycle);
        tpa_recycle=v.findViewById(R.id.tpa_recycle);



        brokerlist = new ArrayList<>();
        broker_adapter = new ContactusmainAdapter(getActivity(), brokerlist);
        broker_recycle.setAdapter(broker_adapter);


        employerlist = new ArrayList<>();
        emp_adapter = new ContactusmainAdapter(getActivity(), employerlist);
        employer_recycle.setAdapter(emp_adapter);


        tpalist = new ArrayList<>();
        tpa_adapter = new ContactusmainAdapter(getActivity(), tpalist);
        tpa_recycle.setAdapter(tpa_adapter);


        // demo();
        MyContactUs();

        //MyContactLog();



        int numberOfColumns = 1;
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

        String url = con.base_url+"/api/admin/get/contact-us?"+"user_type_name=Employee";

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
                            broker_recycle.setVisibility(View.GONE);

                        }else {
                            JSONObject jsonObja = js.getJSONObject("data");

                            JSONArray tpadetails = jsonObja.getJSONArray("tpa_details");
                            JSONArray brokers = jsonObja.getJSONArray("broker_details");
                            JSONArray employer = jsonObja.getJSONArray("employer_details");

                            if (brokers.length() == 0) {
                                broker_recycle.setVisibility(View.GONE);
                            } else {
                                broker_recycle.setVisibility(View.VISIBLE);


                                for (int j = 0; j < brokers.length(); j++) {

                                    JSONObject jo_area = (JSONObject) brokers.get(j);


                                    try {

                                        String name = jo_area.getString("name");


                                        brokerlist.add(new ContactUsMain("Broker", name));
                                    } catch (JSONException ex) {
                                        ex.printStackTrace();
                                    }


                                }


                            }
                            broker_adapter.notifyDataSetChanged();




                            if (employer.length() == 0) {
                                employer_recycle.setVisibility(View.GONE);
                            } else {
                                employer_recycle.setVisibility(View.VISIBLE);


                                for (int j = 0; j < employer.length(); j++) {

                                    JSONObject jo_area = (JSONObject) employer.get(j);


                                    try {

                                        String name = jo_area.getString("name");


                                        employerlist.add(new ContactUsMain("Employer", name));
                                    } catch (JSONException ex) {
                                        ex.printStackTrace();
                                    }


                                }


                            }
                            emp_adapter.notifyDataSetChanged();



                            if (tpadetails.length() == 0) {
                                tpa_recycle.setVisibility(View.GONE);
                            } else {
                                tpa_recycle.setVisibility(View.VISIBLE);


                                for (int j = 0; j < tpadetails.length(); j++) {

                                    JSONObject jo_area = (JSONObject) tpadetails.get(j);


                                    try {

                                        String name = jo_area.getString("name");


                                        tpalist.add(new ContactUsMain("TPA", name));
                                    } catch (JSONException ex) {
                                        ex.printStackTrace();
                                    }


                                }


                            }
                            tpa_adapter.notifyDataSetChanged();





                        }







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

    void MyContactLog(){

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
                            broker_recycle.setVisibility(View.GONE);

                        }else {
                            JSONArray jsonObja = js.getJSONArray("data");



                                for (int j = 0; j < jsonObja.length(); j++) {

                                    JSONObject jo_area = (JSONObject) jsonObja.get(j);


                                    try {

                                        String type = jo_area.getString("type");


                                        JSONArray data = jo_area.getJSONArray("data");




                                            if(type.equalsIgnoreCase("Broker")) {

                                                for (int m = 0; m < data.length(); m++) {

                                                    JSONObject jo_areas = (JSONObject) data.get(j);
                                                    brokerlist.add(new ContactUsMain("Broker",
                                                            jo_areas.getString("broker_name")));
                                                }



                                            }else  if(type.equalsIgnoreCase("IC")){
                                                for (int m = 0; m < data.length(); m++) {

                                                    JSONObject jo_areas = (JSONObject) data.get(j);
                                                    brokerlist.add(new ContactUsMain("IC",
                                                            jo_areas.getString("broker_name")));
                                                }
                                            }
                                         /*   }else if(type.equalsIgnoreCase("IC")){
                                                brokerlist.add(new ContactUsMain("IC",
                                                        jo_areas.getString("insurer_name")));
                                            }else if(type.equalsIgnoreCase("Employer")){
                                                brokerlist.add(new ContactUsMain("Broker",
                                                        jo_areas.getString("broker_name")));
                                            }else if(type.equalsIgnoreCase("TPA")){
                                                brokerlist.add(new ContactUsMain("Broker",
                                                          jo_areas.getString("broker_name")));
                                            }*/





                                    } catch (JSONException ex) {
                                        ex.printStackTrace();
                                    }


                                }


                            }
                            broker_adapter.notifyDataSetChanged();





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