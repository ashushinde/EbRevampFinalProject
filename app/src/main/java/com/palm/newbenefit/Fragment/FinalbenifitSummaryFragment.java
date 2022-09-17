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
import com.palm.newbenefit.Adapter.myvoluntaryAdapterGroupMyTermSumm;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.ApiConfig.RecyclerTouchListener;
import com.palm.newbenefit.Module.VoluntaryBenefit;
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

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class FinalbenifitSummaryFragment extends Fragment {
    Constants con = null;

    String token = null;
    String user_id = null;




    int amountrs = 500;

    int topay_count;




    LinearLayout summary;

    LinearLayout hide_data;


    double premoium_amount;
    String installement;

    TextView premium;
    TextView month;

    RecyclerView policy_cycle_term;

    LinearLayout submitdata;
    TextView month_hide;




    List<VoluntaryBenefit> policyob_term = null;
    List<String> policy_no = null;
    myvoluntaryAdapterGroupMyTermSumm policySummaryAdapter_term = null;




    public FinalbenifitSummaryFragment() {
        // Required empty public constructor
    }


TextView summarydd,summaryd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_benifitsum, container, false);
        con = new Constants();

        SharedPreferences prefs = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);
        token = prefs.getString("api_token", null);
        user_id = prefs.getString("user_id", null);


        summary= v.findViewById(R.id.summary);

        summarydd= v.findViewById(R.id.summarydd);
        summaryd= v.findViewById(R.id.summaryd);

        policy_cycle_term= v.findViewById(R.id.policy_cycle_term);

        hide_data= v.findViewById(R.id.hide_data);

        premium= v.findViewById(R.id.premium);
        month= v.findViewById(R.id.month);
        month_hide= v.findViewById(R.id.month_hide);

        submitdata= v.findViewById(R.id.submitdata);

        submitdata.setVisibility(View.GONE);
        summaryd.setVisibility(View.GONE);
        summarydd.setVisibility(View.GONE);
        if (isNetworkAvailable()) {





          //  getAllVol();

            setBankDetTerm();

            //Data();
        }else {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error?")
                    .setMessage("Please Check Your Internet Connection")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
        }







        int numberOfColumns = 1;























        policy_cycle_term.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));

        policy_cycle_term.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), policy_cycle_term, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager policy_cycle_termterm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        policy_cycle_term.setLayoutManager(policy_cycle_termterm);









        return v;

    }



    private void getAllVol() {



        String url = con.base_url+"/api/employee/get/balance/utilization?";

        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);

                    Log.d("response",response);

                    JSONObject  jsonObj=js.getJSONObject("data");


                    //Premium




                    String toPay = String.valueOf(jsonObj.getString("to_pay"));


                    if(toPay.equalsIgnoreCase("0")
                            ||toPay.equalsIgnoreCase("null")
                            ||toPay.equalsIgnoreCase(""))
                    {
                     hide_data.setVisibility(GONE);
                    }
                    else {

                        premium.setText(toPay);
                      /*  try{
                            int data= Integer.parseInt(String.valueOf(toPay));

                            String cover_data = NumberFormat.getNumberInstance(Locale.US).format(data);

                            viewHolder.to_pay.setText(cover_data);
                        }catch (Exception e){
                            viewHolder.to_pay.setText(toPay);
                        }
*/
                        hide_data.setVisibility(VISIBLE);
                    }


                } catch (Exception e) {
                    Log.e("onErrorResponse", e.toString());
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






       /* String url = con.base_url+"/api/employee/get/dashboard";
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);

                    Log.d("response",response);


                    double allPremium=0.0;

                    String  status= String.valueOf(js.getBoolean("status"));

                    if(status.equalsIgnoreCase("false")){
                        summary.setVisibility(View.GONE);
                    }else {
                        JSONArray jsonObj=js.getJSONArray("data");

                        if (jsonObj.length() == 0){
                            summary.setVisibility(View.GONE);
                        }else {
                            summary.setVisibility(View.VISIBLE);











                            for (int k = 0; k < jsonObj.length(); k++) {
                                JSONObject jo_aread = (JSONObject) jsonObj.get(k);
                                int po_idd = jo_aread.getInt("premium");

                                allPremium= (po_idd+allPremium);

                            }




                        }

                        try{
                            double data= allPremium;

                            String cover_data = NumberFormat.getNumberInstance(Locale.US).format(data);

                            premium.setText(cover_data);
                        }catch (Exception e){
                            premium.setText(String.valueOf(allPremium));
                        }


                        if(premium.getText().toString().trim().equalsIgnoreCase("0")){
                            hide_data.setVisibility(View.GONE);
                        }else {
                            hide_data.setVisibility(View.VISIBLE);
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
        mRequestQueue.add(mStringRequest);*/

    }










    private void setBankDetTerm() {


         policyob_term= new ArrayList<>();
         policy_no= new ArrayList<>();
        policySummaryAdapter_term = new myvoluntaryAdapterGroupMyTermSumm(getActivity(), policyob_term);
        policy_cycle_term.setAdapter(policySummaryAdapter_term);


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
                        policy_cycle_term.setVisibility(View.GONE);
                        hide_data.setVisibility(GONE);

                    } else {
                        policy_cycle_term.setVisibility(View.VISIBLE);
                        hide_data.setVisibility(VISIBLE);

                        JSONArray jsonObja =data.getJSONArray("data");







                        for (int k = 0;k < jsonObja.length(); k++) {

                            JSONObject jo_areag = (JSONObject) jsonObja.get(k);

                            double employee_premium= jo_areag.getDouble("employee_premium");


                            allPremium=employee_premium+allPremium;

                        }



                        String allPremiumdata= String.valueOf(allPremium);

                        if(allPremiumdata.equalsIgnoreCase("0")
                                ||allPremiumdata.equalsIgnoreCase("null")
                                ||allPremiumdata.equalsIgnoreCase("")||
                        allPremiumdata.equalsIgnoreCase("0.0"))
                        {
                            hide_data.setVisibility(GONE);
                        }
                        else {

                            premium.setText(allPremiumdata);
                      /*  try{
                            int data= Integer.parseInt(String.valueOf(toPay));

                            String cover_data = NumberFormat.getNumberInstance(Locale.US).format(data);

                            viewHolder.to_pay.setText(cover_data);
                        }catch (Exception e){
                            viewHolder.to_pay.setText(toPay);
                        }
*/
                            hide_data.setVisibility(VISIBLE);
                        }



                        for (int k = 0;k < jsonObja.length(); k++) {

                            JSONObject jo_areaf = (JSONObject) jsonObja.get(k);

                            String name = (jo_areaf.getString("policy_name"));
                            String first_name = (jo_areaf.getString("first_name"));
                            String last_name = (jo_areaf.getString("last_name"));
                           // String suminsured = String.valueOf((jo_areaf.getString("suminsured")));
                            String dob = (jo_areaf.getString("dob"));
                            String gender = (jo_areaf.getString("gender"));
                            String member_email = jo_areaf.getString("member_email");
                            String member_mob_no = jo_areaf.getString("member_mob_no");
                            String suminsurued_type_id = jo_areaf.getString("suminsurued_type_id");
                            String policy_id = jo_areaf.getString("policy_id");
                            String policy_number = jo_areaf.getString("policy_number");
                            int suminsured = jo_areaf.getInt("suminsured");
                            double employee_premium= jo_areaf.getDouble("employee_premium");

                            String number_of_time_salary = jo_areaf.getString("number_of_time_salary");
                            String policy_sub_type_id = jo_areaf.getString("policy_sub_type_id");
                            String installment = jo_areaf.getString("installment");
                            String opd_suminsured = jo_areaf.getString("opd_suminsured");




                            if(installment.equalsIgnoreCase("null")){
                                month_hide.setVisibility(View.GONE);
                                month.setVisibility(View.GONE);
                            }else {
                                month_hide.setVisibility(View.VISIBLE);
                                month.setVisibility(View.VISIBLE);
                            }

                            month.setText(installment);



                         /*   Log.d("12345","----");
                            Log.d("12345","policy_number "+policy_number);
                            Log.d("12345","suminsured "+suminsured);
                            Log.d("12345","allPremium "+allPremium);*/
                            if(!policy_no.contains(policy_number)){
                                policy_no.add(policy_number);
                                policyob_term.add(new VoluntaryBenefit(name, first_name, last_name,policy_number,
                                        dob, gender, member_email,
                                        opd_suminsured, policy_id, suminsurued_type_id,
                                        number_of_time_salary,policy_sub_type_id));

                            }/*else{
                                VoluntaryBenefit voluntaryBenefit = policyob_term.get(policyob_term.size()-1);
                                int newSum = Integer.parseInt(voluntaryBenefit.getAllsum()) + suminsured;
                                voluntaryBenefit.setAllsum(newSum+"");
                                policyob_term.remove(policyob_term.size()-1);
                                policyob_term.add(voluntaryBenefit);

                            }*/






                        }

                        policySummaryAdapter_term.notifyDataSetChanged();

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

