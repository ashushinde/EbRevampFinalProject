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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

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
import com.palm.newbenefit.Adapter.MyHosClaimAdapter;
import com.palm.newbenefit.Adapter.MyIntimateClaimAdapter;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.ApiConfig.RecyclerTouchListener;
import com.palm.newbenefit.Module.MyHosClaimModel;
import com.palm.newbenefit.Module.MyIntimateClaimModel;
import com.palm.newbenefit.Module.SpinnerModal;
import com.palm.newbenefit.R;
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

public class e_cashless_claim extends Fragment {
    int gg;
    Constants con = null;
    Context context;
    String mobileNumber = null;
    String token = null;
    String user_id = null;


    List<MyIntimateClaimModel> ob = null;
    MyIntimateClaimAdapter adapter = null;

    List<MyIntimateClaimModel> ob_e = null;
    MyIntimateClaimAdapter adapter_e = null;


    ArrayList<String> bank_city = null;
    ArrayList<SpinnerModal> bank_cityList = null;
    ArrayAdapter<SpinnerModal> bank_cityAdapter = null;
    ArrayList<SpinnerModal> bank_nameList = null;
    ArrayAdapter<SpinnerModal> bank_nameAdapter = null;
    ArrayList<String> bank_name = null;


    List<MyHosClaimModel> oba = null;
    MyHosClaimAdapter adaptera = null;

    RecyclerView recyclerView,voluntary,ghi_recycle_plan;
    int amountrs = 500;
    String emp_id,employer_id,employee_id=null,employer_id_data;

    String company_id;
    ImageView info_text,info_text_plan,info_text_voluntary;
    Button group_cover,voluntary_cover;
    private RequestQueue requestQueue;
    TextView emp_name;
    String claim_reimb_id,claim_id;
    Spinner policy_type_spin,bank_citySpin,policy_type_spin_no;

    public e_cashless_claim() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_e_cashless_claim, container, false);
        con = new Constants();


        SharedPreferences prefs = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);
        //((MainActivity) getActivity()).setTitle("E-Cashless Claims");
        token = prefs.getString("api_token", null);
        user_id = prefs.getString("user_id", null);

        recyclerView = v.findViewById(R.id.ghi_recycle);
        ghi_recycle_plan= v.findViewById(R.id.ghi_recycle_plan);
        info_text= v.findViewById(R.id.info_text);
        info_text_plan= v.findViewById(R.id.info_text_plan);
        info_text_voluntary= v.findViewById(R.id.info_text_voluntary);
        voluntary= v.findViewById(R.id.voluntary);
        policy_type_spin= v.findViewById(R.id.policy_type_spin);
        bank_citySpin = v.findViewById(R.id.bank_citySpin);
        policy_type_spin_no = v.findViewById(R.id.policy_type_spin_no);
        setProfileDet();
        GetEmployeeId();
//        group_cover.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//            @SuppressLint("ResourceAsColor")
//            @Override
//            public void onClick(View v) {
//                group_cover.setBackgroundResource(R.drawable.nav_back_tab);
//                voluntary_cover.setBackgroundResource(R.drawable.tab_curve);
//                voluntary_cover.setTextColor(Color.BLACK);
//                group_cover.setTextColor(Color.WHITE);
//
//                ob = new ArrayList<>();
//                adapter = new HomeGroupAdapter(getActivity(), ob);
//                recyclerView.setAdapter(adapter);
//                // demo();
//                setBankDet("group");
//            }
//        });
//
//
//        voluntary_cover.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("ResourceAsColor")
//            @Override
//            public void onClick(View v) {
//                voluntary_cover.setBackgroundResource(R.drawable.nav_back_tab);
//
//                group_cover.setBackgroundResource(R.drawable.tab_curve);
//                group_cover.setTextColor(Color.BLACK);
//                voluntary_cover.setTextColor(Color.WHITE);
//
//                ob = new ArrayList<>();
//                adapter = new HomeGroupAdapter(getActivity(), ob);
//                recyclerView.setAdapter(adapter);
//                // demo();
//                setBankDet("voluntary");
//
//
//            }
//        });


        if (isNetworkAvailable()) {


            //getBankName();

            //Data();
        }else {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error?")
                    .setMessage("Please Check Your Internet Connection")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
        }




        policy_type_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // SpinnerModal bank_city_modal = (SpinnerModalFamilyData) adapterView.getSelectedItem();
                SpinnerModal bank_name_modal = (SpinnerModal) adapterView.getSelectedItem();
                getBankNamenumber(bank_name_modal.getBank_id());


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
        policy_type_spin_no.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // SpinnerModal bank_city_modal = (SpinnerModalFamilyData) adapterView.getSelectedItem();
                SpinnerModal bank_name_modal = (SpinnerModal) adapterView.getSelectedItem();
                getBankCity();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        bank_citySpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerModal bank_city_modal = (SpinnerModal) adapterView.getSelectedItem();



                if (isNetworkAvailable()) {



                    if(bank_city_modal.getSelKey().equalsIgnoreCase("")){

                        ob_e = new ArrayList<>();
                        adapter_e = new MyIntimateClaimAdapter(getActivity(), ob_e);
                        ghi_recycle_plan.setAdapter(adapter_e);

                        setBankDetCahsless();





                    }else {


                        ob_e = new ArrayList<>();
                        adapter_e = new MyIntimateClaimAdapter(getActivity(), ob_e);
                        ghi_recycle_plan.setAdapter(adapter_e);



                        setBankDetM_cashless();


                    }



                }else {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Error?")
                            .setMessage("Please Check Your Internet Connection")
                            .setIcon(android.R.drawable.btn_dialog)
                            .setNegativeButton(android.R.string.ok, null).show();
                }









            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




int numberOfColumnsv=1;

        ghi_recycle_plan.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumnsv));

        ghi_recycle_plan.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), ghi_recycle_plan, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager managervs = new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        ghi_recycle_plan.setLayoutManager(managervs);






        return v;

    }




    private void setBankDetCahsless() {
        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        String url = con.base_url+ "/api/admin/get/overall-claim";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            String data;

                            Log.d("claim",response);



                            JSONObject resp= new JSONObject(response);

                            String statusa= String.valueOf(resp.getBoolean("status"));



                            if (statusa.equalsIgnoreCase("false")) {
                                info_text_plan.setVisibility(View.VISIBLE);
                                ghi_recycle_plan.setVisibility(View.GONE);
                            } else {
                                info_text_plan.setVisibility(View.GONE);
                                ghi_recycle_plan.setVisibility(View.VISIBLE);
                                JSONArray jsonObj =resp.getJSONArray("data");

                                for (int j = 0; j < jsonObj.length(); j++) {
                                    JSONObject jo_area = (JSONObject) jsonObj.get(j);

                                    if(jo_area.getString("claim_request_id").equalsIgnoreCase("null")){
                                        claim_reimb_id="NA";
                                    }else {
                                        claim_reimb_id= jo_area.getString("claim_request_id");
                                    }

                                    if(jo_area.getString("claim_id").equalsIgnoreCase("null")){
                                        claim_id="NA";
                                    }else {
                                        claim_id= jo_area.getString("claim_id");
                                    }

                                    String claim_request_date = (jo_area.getString("claim_request_date"));
                                    String name = jo_area.getString("member_name");
                                    String type = jo_area.getString("claim_type");
                                    String created_at = (jo_area.getString("claim_date"));
                                    String total_claim_amount = (jo_area.getString("claim_amt"));
                                    String claim_reimb_reason = (jo_area.getString("claim_reason"));
                                    String status = (jo_area.getString("claim_status"));


                                    ob_e.add(new MyIntimateClaimModel(claim_reimb_id,claim_id ,
                                            name, type, created_at, total_claim_amount,
                                            claim_reimb_reason,claim_request_date,status));


                                }


                                adapter_e.notifyDataSetChanged();


                            }


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


        SpinnerModal bank_state_modal = (SpinnerModal) policy_type_spin_no.getSelectedItem();
        //    SpinnerModal bank_city_modal = (SpinnerModal) bank_citySpin.getSelectedItem();


        HashMap<String, String> params = new HashMap<>();
        params.put("claim_type", "e-cashless");

        params.put("policy_id", bank_state_modal.getSelKey());
        params.put("employee_id", employee_id);
        params.put("member_id", " ");

        smr.setParams(params);
        rq.add(smr);

    }

    void GetEmployeeId(){
    String url = con.base_url+"/api/admin/user";

        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);

                    Log.d("mydata",response);
                    JSONArray jsonObj=js.getJSONArray("data");

                    for (int i = 0; i < jsonObj.length(); i++) {
                        JSONObject explrObject = jsonObj.getJSONObject(0);





                        String employer_id = explrObject.getString("employee_id");

                        employer_id_data = explrObject.getString("employer_id");




                        if (employer_id != "null" && !employer_id.isEmpty()) {

                            employee_id=employer_id;

                            getBankName();




                        }





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
    private void setBankDetM() {
        ob = new ArrayList<>();
        adapter = new MyIntimateClaimAdapter(getActivity(), ob);
        recyclerView.setAdapter(adapter);
        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        String url = con.base_url+ "/api/admin/get/overall-claim";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            String data;



                            JSONObject resp= new JSONObject(response);

                            String statusa= String.valueOf(resp.getBoolean("status"));



                            if (statusa.equalsIgnoreCase("false")) {
                                info_text.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                            } else {
                                info_text.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                                JSONArray jsonObj =resp.getJSONArray("data");

                                for (int j = 0; j < jsonObj.length(); j++) {
                                    JSONObject jo_area = (JSONObject) jsonObj.get(j);

                                    if(jo_area.getString("claim_id").equalsIgnoreCase("null")){
                                        claim_reimb_id="NA";
                                    }else {
                                        claim_reimb_id= jo_area.getString("claim_id");
                                    }
                                    String claim_request_date = (jo_area.getString("claim_request_date"));
                                    String name = jo_area.getString("member_name");
                                    String type = jo_area.getString("claim_type");
                                    String created_at = (jo_area.getString("claim_date"));
                                    String total_claim_amount = (jo_area.getString("claim_amt"));
                                    String claim_reimb_reason = (jo_area.getString("claim_disease"));


                                    ob.add(new MyIntimateClaimModel("", claim_reimb_id,
                                            name, type, created_at,
                                            total_claim_amount, claim_reimb_reason,
                                            claim_request_date));


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

        SpinnerModal bank_state_modal = (SpinnerModal) policy_type_spin_no.getSelectedItem();
        SpinnerModal bank_city_modal = (SpinnerModal) bank_citySpin.getSelectedItem();




        HashMap<String, String> params = new HashMap<>();
        params.put("claim_type", "hospitalization");
        params.put("member_id", bank_city_modal.getSelKey());
        params.put("policy_id", bank_state_modal.getSelKey());
        params.put("employee_id", employee_id);

        smr.setParams(params);
        rq.add(smr);

    }


    private void setBankDetM_cashless() {
        ob_e = new ArrayList<>();
        adapter_e = new MyIntimateClaimAdapter(getActivity(), ob_e);
        ghi_recycle_plan.setAdapter(adapter_e);
        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        String url = con.base_url+ "/api/admin/get/overall-claim";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            String data;

                            Log.d("claimdata",response);



                            JSONObject resp= new JSONObject(response);

                            String statusa= String.valueOf(resp.getBoolean("status"));



                            if (statusa.equalsIgnoreCase("false")) {
                                info_text_plan.setVisibility(View.VISIBLE);
                                ghi_recycle_plan.setVisibility(View.GONE);
                            } else {
                                info_text_plan.setVisibility(View.GONE);
                                ghi_recycle_plan.setVisibility(View.VISIBLE);
                                JSONArray jsonObj =resp.getJSONArray("data");

                                for (int j = 0; j < jsonObj.length(); j++) {
                                    JSONObject jo_area = (JSONObject) jsonObj.get(j);

                                    if(jo_area.getString("claim_request_id").equalsIgnoreCase("null")){
                                        claim_reimb_id="NA";
                                    }else {
                                        claim_reimb_id= jo_area.getString("claim_request_id");
                                    }
                                    if(jo_area.getString("claim_id").equalsIgnoreCase("null")){
                                        claim_id="NA";
                                    }else {
                                        claim_id= jo_area.getString("claim_id");
                                    }
                                    String claim_request_date = (jo_area.getString("claim_request_date"));
                                    String name = jo_area.getString("member_name");
                                    String type = jo_area.getString("claim_type");
                                    String created_at = (jo_area.getString("claim_date"));
                                    String total_claim_amount = (jo_area.getString("claim_amt"));
                                    String claim_reimb_reason = (jo_area.getString("claim_reason"));

                                    String status = (jo_area.getString("claim_status"));


                                    ob_e.add(new MyIntimateClaimModel(claim_reimb_id,claim_id ,
                                            name, type, created_at, total_claim_amount,
                                            claim_reimb_reason,claim_request_date,status));


                                }


                                adapter_e.notifyDataSetChanged();


                            }


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

        SpinnerModal bank_state_modal = (SpinnerModal) policy_type_spin_no.getSelectedItem();
        SpinnerModal bank_city_modal = (SpinnerModal) bank_citySpin.getSelectedItem();




        HashMap<String, String> params = new HashMap<>();
        params.put("claim_type", "e-cashless");
        params.put("member_id", bank_city_modal.getSelKey());
        params.put("policy_id", bank_state_modal.getSelKey());
        params.put("employee_id", employee_id);

        smr.setParams(params);
        rq.add(smr);

    }

    private void getBankName() {
        //String url =con.base_url+"/api/employee/policies";
        String url =con.base_url+"/api/admin/get/policy/subtype";
        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
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
                                            jsonObj.getString("policy_sub_type_name"),jsonObj.getString("policy_sub_type_id")));
                                    bank_name.add(jsonObj.getString("policy_sub_type_name"));
                                }
                            }





                            bank_nameAdapter = new ArrayAdapter<SpinnerModal>(getActivity(), R.layout.spinner_item, bank_nameList);
                            bank_nameAdapter.setDropDownViewResource(R.layout.spinner_item);


                            policy_type_spin.setAdapter(bank_nameAdapter);
                            // policy_type_spin_no.setAdapter(bank_nameAdapter);





                            ob_e = new ArrayList<>();
                            adapter_e = new MyIntimateClaimAdapter(getActivity(), ob_e);
                            ghi_recycle_plan.setAdapter(adapter_e);

                            setBankDetCahsless();


                            oba = new ArrayList<>();
                            adaptera = new MyHosClaimAdapter(getActivity(), oba);
                            voluntary.setAdapter(adaptera);







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




        HashMap<String, String> params = new HashMap<>();
        params.put("employer_id", employer_id_data);

        smr.setParams(params);
        rq.add(smr);
    }


    private void getBankNamenumber(final String set_bank_name) {
        String url =con.base_url+"/api/admin/get/policyno";
        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {



                            JSONObject data=new JSONObject(response);
                            JSONArray jsonArr = data.getJSONArray("data");





                            bank_name = new ArrayList<>();
                            bank_nameList = new ArrayList<>();

                            for (int i = 0; i < jsonArr.length(); i++) {
                                JSONObject jsonObj = jsonArr.getJSONObject(i);


                                bank_nameList.add(new SpinnerModal(jsonObj.getString("id"),
                                        jsonObj.getString("policy_no")));
                                bank_name.add(jsonObj.getString("policy_no"));

                            }





                            bank_nameAdapter = new ArrayAdapter<SpinnerModal>(getActivity(), R.layout.spinner_item, bank_nameList);
                            bank_nameAdapter.setDropDownViewResource(R.layout.spinner_item);



                            policy_type_spin_no.setAdapter(bank_nameAdapter);



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



        HashMap<String, String> params = new HashMap<>();
        params.put("employer_id", employer_id_data);
        params.put("policy_sub_type_id", set_bank_name);
        params.put("user_type_name", "Employee");

        smr.setParams(params);
        rq.add(smr);
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

                                    bank_cityList.add(new SpinnerModal(String.valueOf(jsonObj.getInt("member_id")),
                                            jsonObj.getString("name")));
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
    public void setProfileDet() {

    String url = con.base_url+"/api/admin/user";
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);

                    Log.d("response",response);
                    JSONArray  jsonObj=js.getJSONArray("data");

                    for (int i = 0; i < jsonObj.length(); i++) {
                        JSONObject explrObject = jsonObj.getJSONObject(i);







                        employer_id = explrObject.getString("employee_id");
                        emp_id = explrObject.getString("employee_id");

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

