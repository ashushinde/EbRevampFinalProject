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
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.palm.newbenefit.Adapter.GroupCoverMemberAdapterMember;

import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.ApiConfig.RecyclerTouchListener;
import com.palm.newbenefit.Module.VoluntaryBenefit;
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
public class MemberEnrolledFragment extends Fragment {
    int gg;
    Constants con = null;
    Context context;
    String mobileNumber = null;

    String user_id = null;

    List<VoluntaryBenefit> ob = null;
    GroupCoverMemberAdapterMember adapter = null;
    RecyclerView recyclerView = null;

    String token,policy_id;
    ImageView info_text;
    private boolean doInOnAttach = false;
    TextView title,subtitle;
    String shown;
    public MemberEnrolledFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_member_enrolled, container, false);
        con = new Constants();
        SharedPreferences prefs = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);

        token = prefs.getString("api_token", null);
        policy_id = prefs.getString("policy_id", null);
        shown = prefs.getString("shown", null);

        recyclerView = v.findViewById(R.id.hos_claim_recycle);
        info_text = v.findViewById(R.id.info_text);



        //setBankDet();




        //Data();


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

                ob = new ArrayList<>();
                adapter = new GroupCoverMemberAdapterMember(getActivity(), ob);
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


        ob = new ArrayList<>();
        adapter = new GroupCoverMemberAdapterMember(getActivity(), ob);
        recyclerView.setAdapter(adapter);

        Log.d("policy_id",policy_id);

        String urls = con.base_url+"/api/employee/get/enroll/members?policy_id="+policy_id;
        RequestQueue rq = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        StringRequest mStringRequests = new StringRequest(Request.Method.GET, urls, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int allsum = 0;
                Double allPremium=0.0;
                try {

                    JSONObject data= new JSONObject(response);

                    Log.e("group_response", data.toString());

                    String statusa= String.valueOf(data.getString("status"));


                    if(statusa.equalsIgnoreCase("false")){

                        recyclerView.setVisibility(View.GONE);
                        info_text.setVisibility(View.VISIBLE);


                    }else {
                        recyclerView.setVisibility(View.VISIBLE);
                        info_text.setVisibility(View.GONE);

                        JSONArray jsonObj =data.getJSONArray("data");









                        for (int k = 0;k < jsonObj.length(); k++) {

                            JSONObject jo_areag = (JSONObject) jsonObj.get(k);
                            int suminsured = jo_areag.getInt("suminsured");
                            double employee_premium= jo_areag.getDouble("employee_premium");

                            Log.d("suminsured", String.valueOf(allsum));
                            Log.d("premium", String.valueOf(employee_premium));


                            allsum=suminsured+allsum;
                            allPremium=employee_premium+allPremium;



                        }

                        Log.d("suminsured", String.valueOf(allsum));
                        Log.d("premium", String.valueOf(allPremium));


                        String allsumdata=String.valueOf(allsum);
                        String allPremiumdata= String.valueOf(allPremium);







                        for (int i=0;i<jsonObj.length();i++){

                            JSONObject groupCoverMemberList = (JSONObject) jsonObj.get(i);

                            String id = (groupCoverMemberList.getString("id"));
                            String marriage_date = (groupCoverMemberList.getString("marriage_date"));

                            String relation_name = (groupCoverMemberList.getString("relation_name"));
                            String relation_id = (groupCoverMemberList.getString("relation_id"));


                            String first_name = (groupCoverMemberList.getString("first_name"));
                            String last_name = (groupCoverMemberList.getString("last_name"));
                            String suminsured = String.valueOf((groupCoverMemberList.getString("suminsured")));
                            String dob = (groupCoverMemberList.getString("dob"));
                            String gender = (groupCoverMemberList.getString("gender"));
                            String member_email = groupCoverMemberList.getString("email");
                            String member_mob_no = groupCoverMemberList.getString("mobile_no");
                            String employee_premium = groupCoverMemberList.getString("employee_premium");
                            String age = groupCoverMemberList.getString("age");
                            String age_type = groupCoverMemberList.getString("age_type");
                            String number_of_time_salary = groupCoverMemberList.getString("number_of_time_salary");

                            Log.d("relationname",relation_name);


                            ob.add(new VoluntaryBenefit(first_name,first_name,last_name,suminsured,
                                    dob,gender,member_email,member_mob_no,employee_premium,allsumdata,
                                    allPremiumdata,age,age_type,id,marriage_date,relation_name,relation_id,
                                    number_of_time_salary));



                        }

                        adapter.notifyDataSetChanged();

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




        rq.add(mStringRequests);










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
