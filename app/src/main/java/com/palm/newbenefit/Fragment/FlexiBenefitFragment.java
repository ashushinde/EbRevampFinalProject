package com.palm.newbenefit.Fragment;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.palm.newbenefit.Activity.MainActivity;

import com.palm.newbenefit.Adapter.myFlexAdapter;
import com.palm.newbenefit.Adapter.myFlexsummaryAdapter;
import com.palm.newbenefit.Adapter.myInsuranceCoverAdapter;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.ApiConfig.RecyclerTouchListener;
import com.palm.newbenefit.Module.FlexBenefit;
import com.palm.newbenefit.Module.FlexBenefitWellness;
import com.palm.newbenefit.Module.InsuranceCover;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class FlexiBenefitFragment extends Fragment  {
    int gg;
    Constants con = null;
    Context context;
    String mobileNumber = null;
    String token = null;
    String user_id = null;
    List<FlexBenefitWellness> ob = null;
    myFlexAdapter adapter = null;


    List<InsuranceCover> obab = null;
    myInsuranceCoverAdapter adapterab = null;


    List<InsuranceCover> obabs = null;

    RecyclerView recyclerView = null;
    RecyclerView insurance_recycle=null;
    int amountrs = 500;
    String emp_id;

    String company_id;
    ImageView info_text;
    Button group_cover,voluntary_cover,insurance_cover;
    private RequestQueue requestQueue;

    Spinner policy_type_spin;
    EditText dte_of_adminsiion,discharge_date,policy_name;
    EditText company_name,sum_insured,premium;
    Button send;
    ProgressDialog progressDialog = null;
    LinearLayout my_insurance_cover_form;
    ImageView bankPreview1;
    String selBankPath_cheque = null;
    List<FlexBenefit> oba = null;
    myFlexsummaryAdapter adaptera = null;
    RecyclerView recyclerViewa = null;

TextView flex_wallet,flex_utilisation,to_pay;
    public FlexiBenefitFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_flexi_benefit, container, false);
        con = new Constants();


        SharedPreferences prefs = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);
        ((MainActivity) getActivity()).setTitle("Flex Benefit");
        token = prefs.getString("api_token", null);
        recyclerView = v.findViewById(R.id.ghi_recycle);
        info_text= v.findViewById(R.id.info_text);
        group_cover= v.findViewById(R.id.group_cover);
        voluntary_cover= v.findViewById(R.id.voluntary_cover);
        insurance_cover= v.findViewById(R.id.insurance_cover);
        send= v.findViewById(R.id.send);


        flex_utilisation= v.findViewById(R.id.flex_utilisation);
        flex_wallet= v.findViewById(R.id.flex_wallet);
        to_pay= v.findViewById(R.id.to_pay);
        my_insurance_cover_form= v.findViewById(R.id.my_flex_summary);
        recyclerViewa = v.findViewById(R.id.wellness);

        insurance_recycle= v.findViewById(R.id.insurance_recycle);


        obab = new ArrayList<>();
        obabs=new ArrayList<>();
        adapterab = new myInsuranceCoverAdapter(getActivity(), obab);
        insurance_recycle.setAdapter(adapterab);




        insurance_cover.setBackgroundResource(R.drawable.nav_back_tab);
        group_cover.setBackgroundResource(R.drawable.tab_curve);
        voluntary_cover.setBackgroundResource(R.drawable.tab_curve);
        voluntary_cover.setTextColor(Color.BLACK);
        group_cover.setTextColor(Color.BLACK);
        insurance_cover.setTextColor(Color.WHITE);

        my_insurance_cover_form.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        insurance_recycle.setVisibility(View.VISIBLE);

        obab = new ArrayList<>();
        adapterab = new myInsuranceCoverAdapter(getActivity(), obab);
        insurance_recycle.setAdapter(adapterab);





        setInsurance();


        insurance_cover.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                insurance_cover.setBackgroundResource(R.drawable.nav_back_tab);
                group_cover.setBackgroundResource(R.drawable.tab_curve);
                voluntary_cover.setBackgroundResource(R.drawable.tab_curve);
                voluntary_cover.setTextColor(Color.BLACK);
                group_cover.setTextColor(Color.BLACK);
                insurance_cover.setTextColor(Color.WHITE);

                my_insurance_cover_form.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                insurance_recycle.setVisibility(View.VISIBLE);

                obab = new ArrayList<>();
                adapterab = new myInsuranceCoverAdapter(getActivity(), obab);
                insurance_recycle.setAdapter(adapterab);





                setInsurance();


            }
        });

        group_cover.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                group_cover.setBackgroundResource(R.drawable.nav_back_tab);
                voluntary_cover.setBackgroundResource(R.drawable.tab_curve);
                voluntary_cover.setTextColor(Color.BLACK);
                group_cover.setTextColor(Color.WHITE);

                insurance_cover.setBackgroundResource(R.drawable.tab_curve);
                insurance_cover.setTextColor(Color.BLACK);

                my_insurance_cover_form.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                insurance_recycle.setVisibility(View.GONE);


                ob = new ArrayList<>();
                adapter = new myFlexAdapter(getActivity(), ob);
                recyclerView.setAdapter(adapter);





                setBankDet();


            }
        });


        voluntary_cover.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                voluntary_cover.setBackgroundResource(R.drawable.nav_back_tab);

                group_cover.setBackgroundResource(R.drawable.tab_curve);
                group_cover.setTextColor(Color.BLACK);
                voluntary_cover.setTextColor(Color.WHITE);

                insurance_cover.setBackgroundResource(R.drawable.tab_curve);
                insurance_cover.setTextColor(Color.BLACK);

                my_insurance_cover_form.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                insurance_recycle.setVisibility(View.GONE);

                oba = new ArrayList<>();
                adaptera = new myFlexsummaryAdapter(getActivity(), oba);
                recyclerViewa.setAdapter(adaptera);

                setBankDetWelness();
                setBankDetFlex();


            }
        });




        if (isNetworkAvailable()) {


            insurance_cover.setBackgroundResource(R.drawable.nav_back_tab);
            group_cover.setBackgroundResource(R.drawable.tab_curve);
            voluntary_cover.setBackgroundResource(R.drawable.tab_curve);
            voluntary_cover.setTextColor(Color.BLACK);
            group_cover.setTextColor(Color.BLACK);
            insurance_cover.setTextColor(Color.WHITE);

            my_insurance_cover_form.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            insurance_recycle.setVisibility(View.VISIBLE);

            obab = new ArrayList<>();
            adapterab = new myInsuranceCoverAdapter(getActivity(), obab);
            insurance_recycle.setAdapter(adapterab);





            setInsurance();


//            my_insurance_cover_form.setVisibility(View.GONE);
//            recyclerView.setVisibility(View.GONE);
//            insurance_recycle.setVisibility(View.VISIBLE);


//            ob = new ArrayList<>();
//            adapter = new myFlexAdapter(getActivity(), ob);
//            recyclerView.setAdapter(adapter);
//
//
//
//
//
//            setBankDet();



        }else {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error?")
                    .setMessage("Please Check Your Internet Connection")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
        }







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








        int numberOfColumnsa = 1;
        recyclerViewa.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumnsa));

        recyclerViewa.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerViewa, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager managera = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerViewa.setLayoutManager(managera);





        insurance_recycle.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumnsa));

        insurance_recycle.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), insurance_recycle, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager manageras = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        insurance_recycle.setLayoutManager(manageras);




        return v;

    }



    private void setBankDet() {

        String url = con.base_url+"/api/employee/get/flex-benefit";
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
    //   RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);

                    Log.d("response",response);







                    JSONArray jsonObj=js.getJSONArray("data");

                    if (jsonObj.length() == 0){
                        info_text.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);

                    }else {
                        info_text.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);




                        for (int j = 0; j < jsonObj.length(); j++) {

                            JSONObject jo_area = (JSONObject) jsonObj.get(j);

                            String flexi_benefit_id = String.valueOf(jo_area.getInt("flexi_benefit_id"));
                            String amount = jo_area.getString("amount");
                            String flex_balance = flex_wallet.getText().toString();
                            String flexi_benefit_name = jo_area.getString("flexi_benefit_name");
                            String flexi_benefit_description = (jo_area.getString("flexi_benefit_description"));
                            String flexi_benefit_code = (jo_area.getString("flexi_benefit_code"));
                            String  flexi_benefit_type= (jo_area.getString("flexi_benefit_type"));
                            String  image= (jo_area.getString("image"));
                            String  alocated_amount= String.valueOf((jo_area.getInt("allocated_amount")));

                            ob.add(new FlexBenefitWellness(flexi_benefit_id,amount,flex_balance,flexi_benefit_name,
                                    flexi_benefit_description,flexi_benefit_code,flexi_benefit_type,image,alocated_amount));


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


    private void setInsurance() {

        String url = con.base_url+"/api/employee/get/flex-benefit/policies";
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
     // RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);

                    Log.d("response",response);







                    JSONArray jsonObj=js.getJSONArray("data");

                    if (jsonObj.length() == 0){
                        info_text.setVisibility(View.VISIBLE);
                        insurance_recycle.setVisibility(View.GONE);

                    }else {
                        info_text.setVisibility(View.GONE);
                        insurance_recycle.setVisibility(View.VISIBLE);


                        for (int j = 0; j < jsonObj.length(); j++) {

                            JSONObject jo_area = (JSONObject) jsonObj.get(j);
                            String policy_id = jo_area.getString("id");
                            String policy_name = jo_area.getString("policy_name");
                            String policy_number = jo_area.getString("policy_number");


                            try {
                                JSONArray benefits_databenefits = jo_area.getJSONArray("benefits");



                                if(benefits_databenefits.length()==0){


                                    int sum_insured = 0;
                                    int cover_data = 0;

                                    String plan_name = "";

                                    sum_insured = 0;
                                    String benefit_description = "";
                                    String enrollement_status = "";
                                    String enrollement_statuss = "";


                                    obabs.add(new InsuranceCover("", ""));


                                    String cover_total = String.valueOf("");

                                    obab.add(new InsuranceCover("", policy_name, policy_number, cover_total,
                                            "", j, 1, policy_id, "", ""));

                                }else {
                                    for (int k = 0; k < benefits_databenefits.length(); k++) {

                                        JSONObject benefits_data = (JSONObject) benefits_databenefits.get(k);
                                        int sum_insured = 0;
                                        int cover_data = 0;

                                        String plan_name = benefits_data.getString("benefit_name");

                                        sum_insured = benefits_data.getInt("sum_insured");
                                        String benefit_description = benefits_data.getString("benefit_description");
                                        String enrollement_status = benefits_data.getString("enrollement_status");
                                        String enrollement_statuss = benefits_data.getString("enrollement_status");

                                        JSONArray features = benefits_data.getJSONArray("features");


                                        for (int m = 0; m < features.length(); m++) {

                                            JSONObject feature_data = (JSONObject) features.get(m);
                                            String plan_names = feature_data.getString("name");
                                            String desca = feature_data.getString("description");
                                            int cover = feature_data.getInt("cover");
                                            String cover_by = feature_data.getString("cover_by");
                                            String cover_type = feature_data.getString("cover_type");

                                            cover_data = cover_data + cover;

                                            if (cover_by.equalsIgnoreCase("1") && cover_type.equalsIgnoreCase("2")) {
                                                cover_data = cover_data + cover;
                                            } else {
                                                cover_data = (cover_data + sum_insured) / 100;
                                            }


                                            obabs.add(new InsuranceCover(plan_names, desca));
                                        }

                                        String cover_total = String.valueOf(cover_data);

                                        obab.add(new InsuranceCover(plan_name, policy_name, policy_number, cover_total,
                                                benefit_description, j, k, policy_id, enrollement_statuss, enrollement_statuss));

                                    }
                                }


                            } catch (Exception e) {
                                Log.e("second", e.toString());

                                int sum_insured = 0;
                                int cover_data = 0;

                                String plan_name = "";

                                sum_insured = 0;
                                String benefit_description = "";
                                String enrollement_status = "";
                                String enrollement_statuss = "";


                                obabs.add(new InsuranceCover("", ""));


                                String cover_total = String.valueOf("");

                                obab.add(new InsuranceCover("", policy_name, policy_number, cover_total,
                                        "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.", j, 1, policy_id, "", ""));


                            }


                        }
                        adapterab.notifyDataSetChanged();

                    }

                } catch (Exception e) {
                    Log.e("three", e.toString());
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


    private void setBankDetWelness() {

        String url = con.base_url+"/api/employee/get/wellness-details";
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
    //  RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);

                    Log.d("response",response);







                    JSONArray jsonObj=js.getJSONArray("data");

                    if (jsonObj.length() == 0){
                        info_text.setVisibility(View.VISIBLE);

                        recyclerViewa.setVisibility(View.GONE);
                    }else {
                        info_text.setVisibility(View.GONE);

                        recyclerViewa.setVisibility(View.VISIBLE);



                        for (int j = 0; j < jsonObj.length(); j++) {

                            JSONObject jo_area = (JSONObject) jsonObj.get(j);

                            String name = jo_area.getString("name");
                            String flex_name = jo_area.getString("flex_name");
                            String deduction_type = jo_area.getString("deduction_type");
                            String final_amount = (jo_area.getString("final_amount"));



                            oba.add(new FlexBenefit(name,flex_name,deduction_type,final_amount));

                        }

                        adaptera.notifyDataSetChanged();

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

    private void setBankDetFlex() {

        String url = con.base_url+"/api/employee/get/flexi-details";
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
       // RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {


                    try {
                        JSONObject js=new JSONObject(response);

                        Log.d("response",response);

                        String status= String.valueOf(js.getBoolean("status"));
                        if(status.equalsIgnoreCase("true")){
                            JSONObject jsonObj=js.getJSONObject("data");

                            String flexWallet= String.valueOf(jsonObj.getInt("flexWallet"));
                            String walletUtilization= String.valueOf(jsonObj.getInt("walletUtilization"));
                            String toPay= String.valueOf(jsonObj.getInt("totalFlex"));

                            flex_wallet.setText(flexWallet);
                            flex_utilisation.setText(walletUtilization);
                            to_pay.setText(toPay);
                        }



                    } catch (JSONException ex) {
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

