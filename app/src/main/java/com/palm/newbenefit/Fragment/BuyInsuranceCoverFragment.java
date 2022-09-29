package com.palm.newbenefit.Fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.palm.newbenefit.Adapter.AddcoverAdapterBuy;
import com.palm.newbenefit.Adapter.AddcoverAdapterHowerBuy;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.ApiConfig.RecyclerTouchListener;
import com.palm.newbenefit.Module.AddCover;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class BuyInsuranceCoverFragment extends Fragment {

    int gg;
    Constants con = null;
    Context context;
    String mobileNumber = null;

    String user_id = null;

    List<AddCover> ob = null;
    AddcoverAdapterBuy adapter = null;

    List<AddCover> oba = null;
    AddcoverAdapterHowerBuy adaptera = null;

    RecyclerView recyclerView ,boxes;

    String token;

    ImageView info_text;
    private boolean doInOnAttach = false;
    public BuyInsuranceCoverFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_buy_insurance_cover, container, false);
        con = new Constants();
        SharedPreferences prefs = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);

        token = prefs.getString("api_token", null);

        boxes= v.findViewById(R.id.boxes);
        info_text = v.findViewById(R.id.info_text);

        recyclerView= v.findViewById(R.id.hos_claim_recycle);









        int numberOfColumnsh = 1;
        boxes.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumnsh));

        boxes.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), boxes, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                AddCover train = oba.get(position);

                ob = new ArrayList<>();
                adapter = new AddcoverAdapterBuy(getActivity(), ob,token);
                recyclerView.setAdapter(adapter);



                setBankDetRefresh(train.getPolicy_sub_type_name());






            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager managerh = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        boxes.setLayoutManager(managerh);



        int numberOfColumnsr = 1;
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumnsr));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {



            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager managerr = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(managerr);



        return v;

    }







    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        context = getActivity();
        if (isVisibleToUser && (getActivity() != null)) {



            if (isNetworkAvailable()) {



                oba = new ArrayList<>();
                adaptera = new AddcoverAdapterHowerBuy(getActivity(), oba,token);
                boxes.setAdapter(adaptera);


                ob = new ArrayList<>();
                adapter = new AddcoverAdapterBuy(getActivity(), ob,token);
                recyclerView.setAdapter(adapter);



                setBankDet();
            }else {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Error?")
                        .setMessage("Please Check Your Internet Connection")
                        .setIcon(android.R.drawable.btn_dialog)
                        .setNegativeButton(android.R.string.ok, null).show();
            }












            //  getActivity();



        }


    }


    private void setBankDet() {


        oba = new ArrayList<>();
        adaptera = new AddcoverAdapterHowerBuy(getActivity(), oba, token);
        boxes.setAdapter(adaptera);

        String url = con.base_url+"/api/insurer/get/customer-buy-insurance";

      RequestQueue rq = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                    JSONObject data= new JSONObject(response);

                    String statusa= String.valueOf(data.getBoolean("status"));




                    if (statusa.equalsIgnoreCase("false")) {
                        info_text.setVisibility(View.VISIBLE);
                        boxes.setVisibility(View.GONE);
                    } else {
                        info_text.setVisibility(View.GONE);
                        boxes.setVisibility(View.VISIBLE);

                        JSONArray jsonObj =data.getJSONArray("data");

                        for (int j = 0; j < jsonObj.length(); j++) {

                            JSONObject jo_area = (JSONObject) jsonObj.get(j);



                            String policy_id= String.valueOf(jo_area.getInt("id"));
                            String policy_sub_type_name= jo_area.getString("name");
                            String policy_sub_type_id= String.valueOf(jo_area.getString("ic_id"));
                            String has_flex=jo_area.getString("url");
                            String has_payroll= jo_area.getString("media");
                            String enrollement_status= "";
                            String suminsured="";
                            String premium= "";


                            oba.add(new AddCover(policy_id,policy_sub_type_name,policy_sub_type_id,
                                    has_flex,has_payroll,enrollement_status,suminsured,premium,"","",""));
                        }





                        adaptera.notifyDataSetChanged();


                        JSONObject jo_areaa = (JSONObject) jsonObj.get(0);

                        setBankDetRefresh(jo_areaa.getString("insurer_name"));
                    }
                } catch (Exception e) {
                    Log.e("myerror", e.toString());
                }

            }
        },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("myerror", error.toString());

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
        params.put("broker_id", "1");

        smr.setParams(params);
        rq.add(smr);




    }


    private void setBankDetRefresh(String name) {
        Log.d("policyName",name);



        ob = new ArrayList<>();
        adapter = new AddcoverAdapterBuy(getActivity(), ob, token);
        recyclerView.setAdapter(adapter);

        String url = con.base_url+"/api/insurer/get/customer-buy-insurance";

     RequestQueue rq = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                    JSONObject data= new JSONObject(response);

                    String statusa= String.valueOf(data.getBoolean("status"));



                    if (statusa.equalsIgnoreCase("false")) {
                        info_text.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        info_text.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        JSONArray jsonObj =data.getJSONArray("data");

                        for (int j = 0; j < jsonObj.length(); j++) {

                            JSONObject jo_area = (JSONObject) jsonObj.get(j);


                            if(name.equalsIgnoreCase(jo_area.getString("name"))){
                                String policy_id= String.valueOf(jo_area.getInt("id"));
                                String policy_sub_type_name= jo_area.getString("name");
                                String policy_sub_type_id= String.valueOf(jo_area.getString("ic_id"));
                                String has_flex=jo_area.getString("url");
                                String has_payroll= jo_area.getString("media");
                                String enrollement_status= "";
                                String suminsured="";
                                String premium= "";




                                ob.add(new AddCover(policy_id,policy_sub_type_name,policy_sub_type_id,
                                        has_flex,has_payroll,enrollement_status,suminsured,premium,"","",""));
                            }



                        }

                        adapter.notifyDataSetChanged();
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






        HashMap<String, String> params = new HashMap<>();
        params.put("broker_id", "1");

        smr.setParams(params);
        rq.add(smr);
    }



    @Override
    public void onResume() {
        super.onResume();
        if (!getUserVisibleHint()) {


            return;
        }


        if (isNetworkAvailable()) {


            setBankDet();
        }else {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error?")
                    .setMessage("Please Check Your Internet Connection")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
        }









    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
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

