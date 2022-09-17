package com.palm.newbenefit.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.palm.newbenefit.Activity.HealthCheckupActivity;
import com.palm.newbenefit.Activity.MainActivity;
import com.palm.newbenefit.Adapter.HealthCheckupAdapter;

import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.ApiConfig.RecyclerTouchListener;
import com.palm.newbenefit.Module.HealthCheckup;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;


public class HealthChekupFragment extends Fragment {


    Calendar newCalendar;
    String token;
    ProgressDialog progressDialog = null;
    Constants con;

    List<HealthCheckup> ob = null;
    HealthCheckupAdapter adapter = null;
    RecyclerView hos_claim_recycle;
    public HealthChekupFragment() {
        // Required empty public constructor
    }

    Context context;
    ImageView info_text_GH;
    TextView saveBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragement_healthchekup_demo, container, false);


        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).setTitle("");


         SharedPreferences prefs = getActivity().getSharedPreferences( getActivity().getPackageName(), Context.MODE_PRIVATE);

        token = prefs.getString("api_token", null);
        hos_claim_recycle =v.findViewById(R.id.hos_claim_recycle);
        info_text_GH = v.findViewById(R.id.info_text_GH);


        con = new Constants();

        saveBtn=v.findViewById(R.id.saveBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( getActivity(), HealthCheckupActivity.class);
                intent.putExtra("page_type", "add");
                startActivity(intent);
            }
        });






        int numberOfColumns = 1;
        hos_claim_recycle.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));

        hos_claim_recycle.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), hos_claim_recycle, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {





            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager manager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        hos_claim_recycle.setLayoutManager(manager);






        return v;

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!getUserVisibleHint()) {


            return;
        }


        if (isNetworkAvailable()) {

            ob = new ArrayList<>();
            adapter = new HealthCheckupAdapter(getActivity(), ob);
            hos_claim_recycle.setAdapter(adapter);

            setBankDet();
            // Data();
        }else {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error?")
                    .setMessage("Please Check Your Internet Connection")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
        }
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        context = getActivity();
        if (isVisibleToUser && (getActivity() != null)) {


            if (isNetworkAvailable()) {

                ob = new ArrayList<>();
                adapter = new HealthCheckupAdapter(getActivity(), ob);
                hos_claim_recycle.setAdapter(adapter);

                setBankDet();
                // Data();
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


        ob = new ArrayList<>();
        adapter = new HealthCheckupAdapter(getActivity(), ob);
        hos_claim_recycle.setAdapter(adapter);

        String url = con.base_url+"/api/broker/get-health-checkup-list";
        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            Log.d("health",response);





                    JSONObject resp= new JSONObject(response);

                    String statusa= String.valueOf(resp.getBoolean("status"));



                    if (statusa.equalsIgnoreCase("false")) {
                        info_text_GH.setVisibility(View.VISIBLE);
                        hos_claim_recycle.setVisibility(View.GONE);
                    } else {

                        info_text_GH.setVisibility(View.GONE);
                        hos_claim_recycle.setVisibility(View.VISIBLE);
                        JSONArray jsonObj =resp.getJSONArray("data");

                        for (int j = 0; j < jsonObj.length(); j++) {
                            JSONObject jo_area = (JSONObject) jsonObj.get(j);


                            String policy_number = jo_area.getString("policy_number");
                            String employee_code = jo_area.getString("employee_code");
                            String employee_name = (jo_area.getString("employee_name"));
                            String relation_with_employee = (jo_area.getString("relation_with_employee"));
                            String member_name = (jo_area.getString("member_name"));
                            String appointment_request_date_time = jo_area.getString("appointment_request_date_time");
                            String alternate_appointment_request_date_time = jo_area.getString("alternate_appointment_request_date_time");


                            String appointment_status = jo_area.getString("appointment_status");
                            String appointment_date_time = jo_area.getString("appointment_date_time");
                            String checkup_type = (jo_area.getString("checkup_type"));
                            String status_updated_by = (jo_area.getString("status_updated_by"));
                            String employer_name = (jo_area.getString("employer_name"));
                            String employee_member_mapping_id = jo_area.getString("employee_member_mapping_id");
                            String contact = jo_area.getString("contact");

                            String email = jo_area.getString("email");
                            String address_line_1 = jo_area.getString("address_line_1");
                            String address_line_2 = (jo_area.getString("address_line_2"));
                            String city_id = (jo_area.getString("city_id"));
                            String state_id = (jo_area.getString("state_id"));
                            String pincode = jo_area.getString("pincode");
                            String health_check_up_report = jo_area.getString("health_check_up_report");

                            String is_checkup_done = jo_area.getString("email");
                            String id = jo_area.getString("id");


                            ob.add(new HealthCheckup(policy_number,employee_code,employee_name,relation_with_employee
                            ,member_name,appointment_request_date_time,alternate_appointment_request_date_time
                            ,appointment_status,appointment_date_time,checkup_type,status_updated_by
                            ,employer_name,employee_member_mapping_id,contact,email,address_line_1
                            ,address_line_2,city_id,state_id,pincode,health_check_up_report,is_checkup_done,id));


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
        params.put("user_type_name", "Employee");

        smr.setParams(params);
        rq.add(smr);

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