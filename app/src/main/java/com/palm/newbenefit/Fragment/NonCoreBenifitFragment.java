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
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.palm.newbenefit.Adapter.NonCoreAdapter;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.ApiConfig.RecyclerTouchListener;
import com.palm.newbenefit.Module.NonCoreData;
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

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class NonCoreBenifitFragment extends Fragment {

    int gg;
    Constants con = null;
    Context context;
    String mobileNumber = null;
    String token = null;
    String user_id = null;
    List<NonCoreData> ob = null;
    NonCoreAdapter adapter = null;
    RecyclerView recyclerView = null;
    String emp_id, policy_no;
    ImageView info_text_GH;
    public NonCoreBenifitFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_non_core_benifit, container, false);
        con = new Constants();
        SharedPreferences prefs = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);
        policy_no = prefs.getString("policy_no", null);
        emp_id = prefs.getString("api_token", null);
        recyclerView = v.findViewById(R.id.ncore_recycle);

        info_text_GH = v.findViewById(R.id.info_text_GH);
        ob = new ArrayList<>();
        adapter = new NonCoreAdapter(getActivity(), ob, emp_id);
        recyclerView.setAdapter(adapter);


        if (isNetworkAvailable()) {


            setBankDet();

        }else {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error?")
                    .setMessage("Please Check Your Internet Connection")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
        }









        // Data();

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


        return v;

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        context = getActivity();
        if (isVisibleToUser && (getActivity() != null)) {




            if (isNetworkAvailable()) {

                //  getActivity();


                ob = new ArrayList<>();
                adapter = new NonCoreAdapter(getActivity(), ob, emp_id);
                recyclerView.setAdapter(adapter);

                setBankDet();
            }else {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Error?")
                        .setMessage("Please Check Your Internet Connection")
                        .setIcon(android.R.drawable.btn_dialog)
                        .setNegativeButton(android.R.string.ok, null).show();
            }












        }
    }

    private void setBankDet() {
        RequestQueue rq = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();

        ob = new ArrayList<>();
        adapter = new NonCoreAdapter(getActivity(), ob, emp_id);
        recyclerView.setAdapter(adapter);
        String url =con.base_url+"/flexi_details";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (response.equalsIgnoreCase("\r\n[]")) {
                                info_text_GH.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                            } else {
                                info_text_GH.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);

                                JSONObject jsonObjh = new JSONObject(response);

                                JSONArray array = jsonObjh.getJSONArray("data");


                                // JSONArray jsonObj = new JSONArray(response);


                                for (int j = 0; j < array.length(); j++) {
                                    JSONObject jsonarr = array.getJSONObject(j);

                                    String master_flexi_benefit_id = ("id");
                                    String flexi_benefit_name = jsonarr.getString("flexi_benefit_name");
                                    String img_name = jsonarr.getString("img_name");
                                    String flexi_type = ("");
                                    String employee_flexi_benefit_transaction_id = ("");
                                    String emp_id = ("");
                                    String fr_id = ("");
                                    String employee_flexi_benifit_id = jsonarr.getString("master_flexi_benefit_id");
                                    String transac_start_date = ("");

                                    String transac_end_date = ("");

                                    String transac_type = ("");

                                    String reimbursement_amount = ("");
                                    String gst_amount = (jsonarr.getString("pay_amount"));

                                    String reimbursement_ingst_amount = ("");
                                    String final_amount = (jsonarr.getString("final_amount"));

                                    String balance_amount = (jsonarr.getString("balance_amount"));
                                    String deduction_type = (jsonarr.getString("deduction_type"));

                                    String remarks = ("");
                                    String relationship = ("");

                                    String sum_insured = "";
                                    String add_info_1 = ("");
                                    String add_info_2 = ("");
                                    String add_info_3 = ("");
                                    String add_info_4 = ("");
                                    String add_info_5 = ("");
                                    String created_at = ("");
                                    String updated_at = ("");


                                    ob.add(new NonCoreData(master_flexi_benefit_id, flexi_benefit_name, img_name, flexi_type, employee_flexi_benefit_transaction_id, user_id, fr_id, employee_flexi_benifit_id, transac_start_date, transac_end_date, transac_type, reimbursement_amount, gst_amount, reimbursement_ingst_amount, final_amount, balance_amount, deduction_type, remarks, relationship, sum_insured, add_info_1, add_info_2, add_info_3, add_info_4, add_info_5, created_at, updated_at));
                                }

                                adapter.notifyDataSetChanged();
                            }


                        } catch (Exception e) {

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        /*      smr.addStringParam("token", token);*/



      /*  smr.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        mRequestQueue.add(smr);*/
        HashMap<String, String> params = new HashMap<>();
        params.put("policy_type", "Wellness");
        params.put("emp_id", emp_id);

        smr.setParams(params);
        rq.add(smr);
    }


   /* public void Data() {
        ob.add(new NonCoreData("1", "Gym", "", ""
                , "", "16", "", "", "", "", "", ""
                , "", "", "500", "100", "", "", "", "", "", ""
                , "", "", "", "", ""));


        ob.add(new NonCoreData("2", "Elder Care", "", ""
                , "", "15", "", "", "", "", "", ""
                , "", "", "658", "200", "", "", "", "", "", ""
                , "", "", "", "", ""));


        ob.add(new NonCoreData("3", "Health Check Up", "", ""
                , "", "12", "", "", "", "", "", ""
                , "", "", "700", "300", "", "", "", "", "", ""
                , "", "", "", "", ""));


        ob.add(new NonCoreData("4", "Yoga / zumba\\\\r\\\\n\\\\r\\\\n", "", ""
                , "", "14", "", "", "", "", "", ""
                , "", "", "800", "400", "", "", "", "", "", ""
                , "", "", "", "", ""));


        ob.add(new NonCoreData("5", "Condition Mgmt Program", "", ""
                , "", "14", "", "", "", "", "", ""
                , "", "", "1200", "500", "", "", "", "", "", ""
                , "", "", "", "", ""));


        ob.add(new NonCoreData("5", "Nutrition & Dietician Counselling", "", ""
                , "", "14", "", "", "", "", "", ""
                , "", "", "1200", "600", "", "", "", "", "", ""
                , "", "", "", "", ""));


        ob.add(new NonCoreData("5", "Smart Watch", "", ""
                , "", "11", "", "", "", "", "", ""
                , "", "", "1200", "600", "", "", "", "", "", ""
                , "", "", "", "", ""));


        ob.add(new NonCoreData("5", "Home HealthCare", "", ""
                , "", "19", "", "", "", "", "", ""
                , "", "", "1200", "600", "", "", "", "", "", ""
                , "", "", "", "", ""));


        ob.add(new NonCoreData("2", "Vaccination & Immunization Care", "", ""
                , "", "15", "", "", "", "", "", ""
                , "", "", "658", "200", "", "", "", "", "", ""
                , "", "", "", "", ""));


        adapter.notifyDataSetChanged();


    }
*/

    @Override
    public void onResume() {
        super.onResume();
        if (!getUserVisibleHint()) {


            return;
        }




        if (isNetworkAvailable()) {

            //  getActivity();


            setBankDet();
        }else {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error?")
                    .setMessage("Please Check Your Internet Connection")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
        }








        setBankDet();
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


