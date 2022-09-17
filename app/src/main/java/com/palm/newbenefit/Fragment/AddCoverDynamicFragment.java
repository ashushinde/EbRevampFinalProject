package com.palm.newbenefit.Fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.palm.newbenefit.Adapter.AddcoverAdapter;
import com.palm.newbenefit.Adapter.AddcoverAdapterHower;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.ApiConfig.RecyclerTouchListener;
import com.palm.newbenefit.Module.AddCover;
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
public class AddCoverDynamicFragment extends Fragment {

    int gg;
    Constants con = null;
    Context context;
    String mobileNumber = null;

    String user_id = null;

    List<AddCover> ob = null;
    AddcoverAdapter adapter = null;

    List<AddCover> oba = null;
    AddcoverAdapterHower adaptera = null;

    RecyclerView recyclerView ,boxes;
    String premium="null";
    String token;
    String policy_id;

    ImageView info_text;
    private boolean doInOnAttach = false;
    public AddCoverDynamicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_add_cover_dynamic, container, false);

        con = new Constants();
        SharedPreferences prefs = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);

        token = prefs.getString("api_token", null);
        policy_id = prefs.getString("policy_id", null);

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
                adapter = new AddcoverAdapter(getActivity(), ob,token);
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
                adaptera = new AddcoverAdapterHower(getActivity(), oba,token);
                boxes.setAdapter(adaptera);


                ob = new ArrayList<>();
                adapter = new AddcoverAdapter(getActivity(), ob,token);
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
        adaptera = new AddcoverAdapterHower(getActivity(), oba, token);
        boxes.setAdapter(adaptera);

        String url = con.base_url+"/api/employee/get/cover/details?"+"policy_id="+policy_id;

        //RequestQueue rq = Volley.newRequestQueue(getActivity());

      RequestQueue rq = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject data= new JSONObject(response);


                    Log.d("hower",response);


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
                            //JSONObject jo_areaa = (JSONObject) jsonObj.get(0);




                            String policy_id= String.valueOf(jo_area.getInt("policy_id"));
                            String policy_sub_type_name= jo_area.getString("policy_name");
                            String policy_sub_type_id= String.valueOf(jo_area.getInt("policy_sub_type_id"));
                            String has_flex= String.valueOf(jo_area.getInt("has_flex"));
                            String has_payroll= String.valueOf(jo_area.getInt("has_payroll"));
                            String enrollement_status= String.valueOf(jo_area.getInt("enrollement_status"));
                            String suminsured= jo_area.getString("suminsured");
                            String policy_name= jo_area.getString("policy_sub_type_name");


                            String description= jo_area.getString("description");
                            String flexi_benefit_id= String.valueOf(jo_area.getInt("flexi_benefit_id"));
                            String premium_type_id;


                            try{
                                premium_type_id= String.valueOf(jo_area.getInt("premium_type_id"));

                            }catch (Exception e){
                                premium_type_id= "0";

                            }
                            String old_suminsured=null;

                            try{
                                 old_suminsured= String.valueOf(jo_area.getInt("top_up_suminsured"));

                            }catch (Exception e){
                                 old_suminsured= "0";

                            }

                           /* if(premium_type_id.equalsIgnoreCase("5")||
                                    premium_type_id.equalsIgnoreCase("6")||
                                    premium_type_id.equalsIgnoreCase("7")) {

                                 premium = "null";
                            }else {
                                 premium = (jo_area.getString("premium"));

                            }
*/
                            premium = (jo_area.getString("premium"));

                            oba.add(new AddCover(policy_id,policy_sub_type_name,policy_sub_type_id,
                                    has_flex,has_payroll,enrollement_status,suminsured,premium,description,flexi_benefit_id,premium_type_id,old_suminsured,policy_name));

                          }

                            adaptera.notifyDataSetChanged();

                           JSONObject jo_areaa = (JSONObject) jsonObj.get(0);

                          setBankDetRefresh(jo_areaa.getString("policy_name"));


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("onErrorResponse", e.toString());
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


    private void setBankDetRefresh(String name) {
        Log.d("policyName",name);



        ob = new ArrayList<>();
        adapter = new AddcoverAdapter(getActivity(), ob, token);
        recyclerView.setAdapter(adapter);

        String url = con.base_url+"/api/employee/get/cover/details?"+"policy_id="+policy_id;
       // RequestQueue rq = Volley.newRequestQueue(getActivity());

       RequestQueue rq = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject data= new JSONObject(response);

                    Log.d("addcover",response);

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

                            if(name.equalsIgnoreCase(jo_area.getString("policy_name"))){
                                String policy_id= String.valueOf(jo_area.getInt("policy_id"));
                                String policy_sub_type_name= jo_area.getString("policy_name");
                                String policy_sub_type_id= String.valueOf(jo_area.getInt("policy_sub_type_id"));
                                String has_flex= String.valueOf(jo_area.getInt("has_flex"));
                                String has_payroll= String.valueOf(jo_area.getInt("has_payroll"));
                                String enrollement_status= String.valueOf(jo_area.getInt("enrollement_status"));
                                String suminsured= jo_area.getString("suminsured");
                                String description= jo_area.getString("policy_description");
                                String flexi_benefit_id= jo_area.getString("flexi_benefit_id");
                                String premium_type_id= jo_area.getString("premium_type_id");
                                String policy_name= jo_area.getString("policy_sub_type_name");

                                String old_suminsured=null;
                                try{
                                    old_suminsured= String.valueOf(jo_area.getInt("top_up_suminsured"));

                                }catch (Exception e){
                                    old_suminsured="0";

                                }



                                    premium = String.valueOf(jo_area.getString("premium"));



                                /*if(premium_type_id.equalsIgnoreCase("5")||
                                        premium_type_id.equalsIgnoreCase("6")||
                                        premium_type_id.equalsIgnoreCase("7")) {

                                    premium = "null";
                                }else {
                                    premium = String.valueOf(jo_area.getString("premium"));

                                }
*/

                                ob.add(new AddCover(policy_id,policy_sub_type_name,policy_sub_type_id,
                                        has_flex,has_payroll,enrollement_status,suminsured,premium,description,flexi_benefit_id,premium_type_id,old_suminsured,policy_name));
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




        rq.add(mStringRequest);
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

