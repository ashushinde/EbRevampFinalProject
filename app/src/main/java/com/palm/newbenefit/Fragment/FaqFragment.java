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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.palm.newbenefit.Adapter.FAQAdapter;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.ApiConfig.RecyclerTouchListener;
import com.palm.newbenefit.Module.FAQ;
import com.palm.newbenefit.Module.SpinnerModal;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class FaqFragment extends Fragment {
    String url;
    Constants con;
    public FaqFragment() {
        // Required empty public constructor
    }
    ArrayList<String> bank_city = null;
    ArrayList<SpinnerModal> bank_cityList = null;

    ArrayList<String> bank_cityt = null;
    ArrayList<SpinnerModal> bank_cityListt = null;

    ArrayList<String> bank_cityf = null;
    ArrayList<SpinnerModal> bank_cityListf = null;
    ArrayAdapter<SpinnerModal> bank_cityAdapter = null;
    String token;
Spinner relation_type_spin;

    List<FAQ> ob = null;

    RecyclerView recyclerView = null;


    FAQAdapter adapter;
    LinearLayout policy_type_linear;
    String id="0";
    ImageView no_data;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_faq, container, false);
        con=new Constants();
        SharedPreferences prefs =getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);

        token = prefs.getString("api_token", null);

        relation_type_spin=v.findViewById(R.id.relation_type_spin);
        recyclerView = v.findViewById(R.id.hos_claim_recycle);
        policy_type_linear= v.findViewById(R.id.policy_type_linear);
        no_data= v.findViewById(R.id.no_data);
        setProfileDet();
        //myfaq();

        ob = new ArrayList<>();
        adapter = new FAQAdapter(getActivity(), ob, token);
        recyclerView.setAdapter(adapter);




        int numberOfColumns = 1;
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager manager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);




        relation_type_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerModal bank_branch_modal = (SpinnerModal) adapterView.getSelectedItem();


                if (!bank_branch_modal.getSelKey().equals("")){


                    setBankDet(bank_branch_modal.getSelKey());

                }
                //ifsc_code.setText(bank_branch_modal.selValue.split("\\|")[1]);




            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });









        return v;
    }


    private void setBankDet(String id) {


        ob = new ArrayList<>();
        adapter = new FAQAdapter(getActivity(), ob, token);
        recyclerView.setAdapter(adapter);

        if(id.equalsIgnoreCase("0")){
            url = con.base_url+"/api/employee/get/faqs?policy_sub_type_id="+id;
        }else {
             url = con.base_url+"/api/employee/get/faqs?policy_sub_type_id="+id;
        }
        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject data= new JSONObject(response);

                      Log.d("status",response);



                        JSONArray jsonObj =data.getJSONArray("data");


                        if(jsonObj.length()==0){
                            no_data.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }else {
                            no_data.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }

                        for (int j = 0; j < jsonObj.length(); j++) {

                            JSONObject jo_area = (JSONObject) jsonObj.get(j);

                            String question =jo_area.getString("question");
                            String answer =jo_area.getString("answer");


                            ob.add(new FAQ("1",question,answer));
                        }

                        adapter.notifyDataSetChanged();

                } catch (Exception e) {

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

    public void setProfileDet() {
        String url = con.base_url+"/api/employee/get/dashboard";
        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);


                    JSONArray jsonObj = js.getJSONArray("data");

                    Log.d("data",jsonObj.toString());


                    bank_city = new ArrayList<>();
                    bank_cityList = new ArrayList<>();
                    bank_cityList.add(new SpinnerModal("", "Select Policy Type "));
                    bank_city.add("");


                    if(jsonObj.length()==0){

                        bank_city = new ArrayList<>();
                        bank_cityList = new ArrayList<>();
                        bank_cityList.add(new SpinnerModal("", "Group Mediclaim"));
                        bank_city.add("");

                    }else {
                        for (int i = 0; i < jsonObj.length(); i++) {
                            JSONObject explrObject = jsonObj.getJSONObject(i);


                            if(explrObject.getString("policy_name").equalsIgnoreCase("flat+flat top up")){

                            }else {

                                if(bank_city.contains(explrObject.getString("policy_sub_type_name"))){

                                }else {
                                    bank_cityList.add(new SpinnerModal(explrObject.getString("policy_sub_type_id"),
                                            explrObject.getString("policy_sub_type_name")));
                                    bank_city.add(explrObject.getString("policy_sub_type_name"));

                                    bank_cityAdapter = new ArrayAdapter<SpinnerModal>(getActivity(), R.layout.spinner_item, bank_cityList);
                                    bank_cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    relation_type_spin.setAdapter(bank_cityAdapter);
                                }


                            }



                        }
                    }


                } catch (Exception e) {

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

        rq.add(mStringRequest);

    }
    private void myfaq() {


        ob = new ArrayList<>();
        adapter = new FAQAdapter(getActivity(), ob, token);
        recyclerView.setAdapter(adapter);


            url = con.base_url+"/api/employee/get/faqs?policy_sub_type_id=1";

        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject data= new JSONObject(response);




                    JSONArray jsonObj =data.getJSONArray("data");

                    if(jsonObj.toString().equalsIgnoreCase("[]")){
                      //  policy_type_linear.setVisibility(View.GONE);
                        no_data.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }else {
                       // policy_type_linear.setVisibility(View.VISIBLE);
                        no_data.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }



                } catch (Exception e) {

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

    public void setProfileDet2() {
        String url = con.base_url+"/api/admin/getSubTypePolicy/2";

      RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);
                    JSONArray jsonObj = js.getJSONArray("data");



                    for (int i = 0; i < jsonObj.length(); i++) {
                        JSONObject explrObject = jsonObj.getJSONObject(i);

                        bank_cityList.add(new SpinnerModal(explrObject.getString("id"),
                                explrObject.getString("name")));
                        bank_city.add(explrObject.getString("name"));



                        bank_cityAdapter = new ArrayAdapter<SpinnerModal>(getActivity(), R.layout.spinner_item, bank_cityList);
                        bank_cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        relation_type_spin.setAdapter(bank_cityAdapter);


                    }



                } catch (Exception e) {

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
