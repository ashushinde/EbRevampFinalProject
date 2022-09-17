package com.palm.newbenefit.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.palm.newbenefit.Activity.MainActivity;
import com.palm.newbenefit.Activity.MemberCoverHomeActivity;
import com.palm.newbenefit.Adapter.MyHosClaimAdapter;
import com.palm.newbenefit.Adapter.PolicyMemberHomeAdapter;
import com.palm.newbenefit.Adapter.ecardAdapter;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.ApiConfig.RecyclerTouchListener;
import com.palm.newbenefit.Module.Ecard;
import com.palm.newbenefit.Module.MemberData;
import com.palm.newbenefit.Module.SpinnerModal;
import com.palm.newbenefit.R;
import com.palm.tatarewamp.SslData.NullHostNameVerifier;
import com.squareup.picasso.Picasso;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class DownloadEcardFragment extends Fragment {
    Constants con = null;
    ImageView info_textl;
    String token = null;
    String user_id = null;
    RecyclerView course_history_recycle;
    SearchView mSearcha;
    LinearLayout ll_main_data;
    RelativeLayout allempnotdatafound;
    String emp_firstname, member_id;
    List<Ecard> ob = null;
    ecardAdapter adapter = null;
    RecyclerView recyclerView = null;
    LinearLayout member_layout;
    String mm;
    ArrayList<SpinnerModal> bank_nameList = null;
    ArrayAdapter<SpinnerModal> bank_nameAdapter = null;
    ArrayList<String> bank_name = null;
    Spinner bank_branchSpin = null;
    String booking_status;
    TextView tv_data_not_found;
    ArrayList<String> bank_city = null;
    ArrayList<SpinnerModal> bank_cityList = null;
    ArrayAdapter<SpinnerModal> bank_cityAdapter = null;

    Toolbar settingtoolbar;
    Spinner policy_type_spin, bank_citySpin, policy_type_spin_no;
    String emp_id,coverName;
    ImageView info_text;
    public DownloadEcardFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_download_ecard, container, false);
        con = new Constants();
        SharedPreferences prefs = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);


        token = prefs.getString("api_token", null);



        bank_citySpin = v.findViewById(R.id.bank_citySpin);
        policy_type_spin_no = v.findViewById(R.id.policy_type_spin_no);
        recyclerView = v.findViewById(R.id.member_recycle);
        info_textl = v.findViewById(R.id.info_textl);
        member_layout= v.findViewById(R.id.member_layout);

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
        ob = new ArrayList<>();
        adapter = new ecardAdapter(getActivity(), ob);
        recyclerView.setAdapter(adapter);

        if (isNetworkAvailable()) {
            User();


        }else {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error?")
                    .setMessage("Please Check Your Internet Connection")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
        }




        policy_type_spin_no.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // SpinnerModal bank_city_modal = (SpinnerModalFamilyData) adapterView.getSelectedItem();
                SpinnerModal bank_name_modal = (SpinnerModal) adapterView.getSelectedItem();


                setBankDet(emp_id,bank_name_modal.getSelKey(),"");

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        bank_citySpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // SpinnerModal bank_city_modal = (SpinnerModalFamilyData) adapterView.getSelectedItem();
                SpinnerModal bank_name_modal = (SpinnerModal) adapterView.getSelectedItem();


                if(!bank_name_modal.getSelKey().equalsIgnoreCase("")){
                    SpinnerModal policy = (SpinnerModal) policy_type_spin_no.getSelectedItem();
                    setBankDetMember(emp_id,policy.getSelKey(),bank_name_modal.getSelValue());

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        return v;
    }

    private void setBankDet(String employee_id,String policy,String memberid) {
        bank_city = new ArrayList<>();
        bank_cityList = new ArrayList<>();
        bank_cityList.add(new SpinnerModal("", "Select Member"));
        bank_city.add("");
        ob = new ArrayList<>();
        adapter = new ecardAdapter(getActivity(), ob);
        recyclerView.setAdapter(adapter);
        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        String url = con.base_url+"/api/admin/get/emp_member";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {


                            JSONObject js=new JSONObject(response);

                            Log.d("response",response);







                            JSONArray jsonObj=js.getJSONArray("data");

                            if (jsonObj.length()==0) {
                                info_textl.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                            } else {
                                info_textl.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);

                            }

                            if(jsonObj.length()==1){
                                member_layout.setVisibility(View.GONE);
                            }else {
                                member_layout.setVisibility(View.VISIBLE);
                            }

                            for (int k = 0; k < jsonObj.length(); k++) {

                                JSONObject jo_areaa = (JSONObject) jsonObj.get(k);




                                    String name=jo_areaa.getString("name");
                                    String relation_name=jo_areaa.getString("relation_name");
                                    String member_id=jo_areaa.getString("tpa_member_id");
                                    String emp_code=jo_areaa.getString("emp_code");
                                    String ecard_url=jo_areaa.getString("ecard_url");
                                    String tpa_member_id=jo_areaa.getString("tpa_member_id");
                                    String tpa_emp_id=jo_areaa.getString("tpa_emp_id");
                                    String policy_no=jo_areaa.getString("policy_no");
                                    String start_date=jo_areaa.getString("start_date");
                                    String end_date=jo_areaa.getString("end_date");
                                    String email=jo_areaa.getString("email");
                                    String mobile=jo_areaa.getString("mobile");


                                        ob.add(new Ecard( name,  relation_name,
                                                member_id,  emp_code,  ecard_url,
                                                tpa_member_id,  tpa_emp_id,  policy_no,
                                                start_date,  end_date,  email,  mobile));




                                bank_cityList.add(new SpinnerModal(
                                        jo_areaa.getString("tpa_member_id"),
                                        String.valueOf(jo_areaa.getString("name")),
                                        String.valueOf(jo_areaa.getString("tpa_member_id")),
                                        jo_areaa.getString("email"),
                                        jo_areaa.getString("relation_name"),
                                        "",
                                        ""));
                                bank_city.add(jo_areaa.getString("name"));


                            }
                            adapter.notifyDataSetChanged();
                            bank_cityAdapter = new ArrayAdapter<SpinnerModal>(getActivity(), R.layout.spinner_item, bank_cityList);
                            bank_cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            bank_citySpin.setAdapter(bank_cityAdapter);



                        } catch (Exception e) {
                            Log.e("OnMemberError", e.toString());
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

        /*      smr.addStringParam("token", token);*/


        HashMap<String, String> params = new HashMap<>();



        params.put("policy_id", policy);
        params.put("employee_id", employee_id);




        smr.setParams(params);
        rq.add(smr);



    }

    private void setBankDetMember(String employee_id,String policy,String memberid) {
        bank_city = new ArrayList<>();
        bank_cityList = new ArrayList<>();
        bank_cityList.add(new SpinnerModal("", "Select Member"));
        bank_city.add("");
        ob = new ArrayList<>();
        adapter = new ecardAdapter(getActivity(), ob);
        recyclerView.setAdapter(adapter);
        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        String url = con.base_url+"/api/admin/get/emp_member";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {


                            JSONObject js=new JSONObject(response);

                            Log.d("response",response);







                            JSONArray jsonObj=js.getJSONArray("data");

                            if (jsonObj.length()==0) {
                                info_textl.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                            } else {
                                info_textl.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);

                            }

                            if(jsonObj.length()==1){
                                member_layout.setVisibility(View.GONE);
                            }else {
                                member_layout.setVisibility(View.VISIBLE);
                            }

                            for (int k = 0; k < jsonObj.length(); k++) {

                                JSONObject jo_areaa = (JSONObject) jsonObj.get(k);




                                String name=jo_areaa.getString("name");
                                String relation_name=jo_areaa.getString("relation_name");
                                String member_id=jo_areaa.getString("tpa_member_id");
                                String emp_code=jo_areaa.getString("emp_code");
                                String ecard_url=jo_areaa.getString("ecard_url");
                                String tpa_member_id=jo_areaa.getString("tpa_member_id");
                                String tpa_emp_id=jo_areaa.getString("tpa_emp_id");
                                String policy_no=jo_areaa.getString("policy_no");
                                String start_date=jo_areaa.getString("start_date");
                                String end_date=jo_areaa.getString("end_date");
                                String email=jo_areaa.getString("email");
                                String mobile=jo_areaa.getString("mobile");

                                if(memberid.equalsIgnoreCase("Select Member")){
                                    ob.add(new Ecard( name,  relation_name,
                                            member_id,  emp_code,  ecard_url,
                                            tpa_member_id,  tpa_emp_id,  policy_no,
                                            start_date,  end_date,  email,  mobile));

                                }else {
                                    if(memberid.equalsIgnoreCase(name)){
                                        ob.add(new Ecard( name,  relation_name,
                                                member_id,  emp_code,  ecard_url,
                                                tpa_member_id,  tpa_emp_id,  policy_no,
                                                start_date,  end_date,  email,  mobile));



                                    }
                                }







                            }
                            adapter.notifyDataSetChanged();



                        } catch (Exception e) {
                            Log.e("OnMemberError", e.toString());
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

        /*      smr.addStringParam("token", token);*/


        HashMap<String, String> params = new HashMap<>();



        params.put("policy_id", policy);
        params.put("employee_id", employee_id);




        smr.setParams(params);
        rq.add(smr);



    }

    private void getBankNamenumber(final String employee_id) {
        String url =con.base_url+"/api/employee/get/dashboard";
        RequestQueue rq = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {


                          JSONObject data=new JSONObject(response);
                            JSONArray jsonArr = data.getJSONArray("data");





                            bank_name = new ArrayList<>();
                            bank_nameList = new ArrayList<>();

                            for (int i = 0; i < jsonArr.length(); i++) {
                                JSONObject jsonObj = jsonArr.getJSONObject(i);


if(jsonObj.getString("policy_sub_type_name").equalsIgnoreCase("Group Mediclaim")){
    bank_nameList.add(new SpinnerModal(jsonObj.getString("policy_id"),
            jsonObj.getString("policy_number")+"-"+
                    jsonObj.getString("policy_sub_type_name")));
    bank_name.add(jsonObj.getString("policy_id"));
}


                            }





                            bank_nameAdapter = new ArrayAdapter<SpinnerModal>(getActivity(), R.layout.spinner_item, bank_nameList);
                            bank_nameAdapter.setDropDownViewResource(R.layout.spinner_item);



                            policy_type_spin_no.setAdapter(bank_nameAdapter);



                        } catch (Exception e) {
//                            new AlertDialog.Builder(getActivity())
//                                    .setTitle("Error?")
//                                    .setMessage("No Data Found")
//                                    .setIcon(android.R.drawable.btn_dialog)
//                                    .setNegativeButton(android.R.string.ok, null).show();
                            bank_name = new ArrayList<>();
                            bank_nameList = new ArrayList<>();
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






    public void getBankCity() {
        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        String url = con.base_url+"/api/admin/get/emp_member";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("emp_member",response);

                            JSONObject jsonArra = new JSONObject(response);

                            String status= String.valueOf(jsonArra.getBoolean("status"));

                            if(status.equalsIgnoreCase("false")){

                                bank_city = new ArrayList<>();
                                bank_cityList = new ArrayList<>();
                                bank_cityList.add(new SpinnerModal("", "Select Member"));
                                bank_city.add("");

                            }else {
                                JSONArray array=jsonArra.getJSONArray("data");
                                bank_city = new ArrayList<>();
                                bank_cityList = new ArrayList<>();
                                bank_cityList.add(new SpinnerModal("", "Select Member"));
                                bank_city.add("");


                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject jsonObj = array.getJSONObject(i);

                                    bank_cityList.add(new SpinnerModal(jsonObj.getString("tpa_member_id"),
                                            String.valueOf(jsonObj.getString("name")),
                                            String.valueOf(jsonObj.getInt("tpa_member_id")),
                                            jsonObj.getString("email"),
                                            jsonObj.getString("relation_name"),
                                            "",
                                            ""));
                                    bank_city.add(jsonObj.getString("name"));
                                }

                            }


                            bank_cityAdapter = new ArrayAdapter<SpinnerModal>(getActivity(), R.layout.spinner_item, bank_cityList);
                            bank_cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            bank_citySpin.setAdapter(bank_cityAdapter);





                        } catch (Exception e) {
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

        SpinnerModal bank_city_modal = (SpinnerModal) policy_type_spin_no.getSelectedItem();




       /* smr.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        mRequestQueue.add(smr);*/

        HashMap<String, String> params = new HashMap<>();
        params.put("policy_id", bank_city_modal.getSelKey());
        params.put("employee_id", emp_id);
        params.put("user_type_name", "Employee");


        Log.d("policy_id", bank_city_modal.getSelValue());

        smr.setParams(params);
        rq.add(smr);
    }

    void User(){
        String url = con.base_url+"/api/admin/user";
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
       /* RequestQueue mRequestQueue = Volley.newRequestQueue(MainActivity.this,
                new HurlStack(null, getSocketFactory()));*/
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);

                    Log.d("mydata",response);
                    JSONArray jsonObj=js.getJSONArray("data");

                    for (int i = 0; i < jsonObj.length(); i++) {
                        JSONObject explrObject = jsonObj.getJSONObject(0);

                        String employee_id= explrObject.getString("employee_id");

                        emp_id= explrObject.getString("employee_id");





                        getBankNamenumber(employee_id);









                    }



                } catch (JSONException e) {

                    e.printStackTrace();
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
        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", user_id);
        params.put("master_user_type_id", "5");


        mStringRequest.setParams(params);
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
