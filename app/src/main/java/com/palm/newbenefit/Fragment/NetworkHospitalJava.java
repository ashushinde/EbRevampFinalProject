package com.palm.newbenefit.Fragment;


import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ScrollView;
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
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.palm.newbenefit.Activity.MainActivity;
import com.palm.newbenefit.Adapter.MapAdapter;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.ApiConfig.RecyclerTouchListener;
import com.palm.newbenefit.Module.MapAddress;
import com.palm.newbenefit.Module.SpinnerModal;
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
public class NetworkHospitalJava extends Fragment {
    Spinner select_type, state, city;
    Constants con = null;
    Context context;
    String mobileNumber = null;
    String token = null;
    String user_id = null;
    String employee_id=null;

    RecyclerView course_history_recycle;
    String bank_city_value = "";
    String bank_branch_value = "";
    String bank_name_value = "";
    ArrayList<SpinnerModal> manufacturerSpinnerModal, modelSpinnerModal, versionSpinnerModal, rtoSpinnerModal, additionalCoverSpinnerModal, noClaimBonusSpinnerModal, previousInsurerSpinnerModal;

    String policy_no;
    ArrayList<String> rtoList;
    String policy_sub_type_name = null;
    Spinner bank_citySpin = null;
    Spinner policy_type_spin_no = null;

    Spinner bank_nameSpin = null;
    ArrayList<String> bank_city = null;
    ArrayList<SpinnerModal> bank_cityList = null;
    ArrayAdapter<SpinnerModal> bank_cityAdapter = null;
    ArrayList<SpinnerModal> bank_nameList = null;
    ArrayAdapter<SpinnerModal> bank_nameAdapter = null;
    ArrayList<String> bank_name = null;
    Spinner bank_branchSpin = null;
    ArrayList<String> bank_branch = null;
    ArrayList<SpinnerModal> bank_branchList = null;
    ArrayAdapter<SpinnerModal> bank_branchAdapter = null;

    List<MapAddress> ob = null;
    MapAdapter adapter = null;
    RecyclerView recyclerView = null;
    ArrayAdapter<SpinnerModal> ManufacturerAdapter, ModelAdapter, VersionAdapter, RtoAdapter, AdditionalCoverAdapter, noClaimBonusAdapter, previousInsurerAdapter;
    TextView policy, policyd;
    private RequestQueue queue;
    Spinner policy_type_spin;
ImageView map,no_image,logo;
TextView content,title;
    public NetworkHospitalJava() {
        // Required empty public constructor
    }

    AutoCompleteTextView rtoNumberTextView,rtoNumberTextView_search;
    TextView submit;
    ScrollView otp_resent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_network_hospital_java, container, false);

        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).setTitle("");
        con = new Constants();
        context = getActivity();
        recyclerView = v.findViewById(R.id.network_cycle);
        submit= v.findViewById(R.id.submit);
        rtoNumberTextView = v.findViewById(R.id.rtoNumberBike);
        otp_resent= v.findViewById(R.id.otp_resent);
        rtoNumberTextView_search = v.findViewById(R.id.rtoNumberBike_search);
        SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);

        token = prefs.getString("api_token", null);

        user_id = prefs.getString("user_id", null);


        policy_type_spin= v.findViewById(R.id.policy_type_spin);
        policy_type_spin_no= v.findViewById(R.id.policy_type_spin_no);
        bank_citySpin = v.findViewById(R.id.bank_citySpin);
        bank_branchSpin = v.findViewById(R.id.bank_branchSpin);
        map= v.findViewById(R.id.map);
        no_image= v.findViewById(R.id.no_image);

        logo= v.findViewById(R.id.logo);
        title= v.findViewById(R.id.title);
        content= v.findViewById(R.id.content);


        if (isNetworkAvailable()) {
            GetEmployeeId();






           // getRtoList();
            // Data();
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
                SpinnerModal bank_name_modal = (SpinnerModal) adapterView.getSelectedItem();

                getBankNamenumber(bank_name_modal.getSelKey());


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        policy_type_spin_no.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerModal bank_name_modal = (SpinnerModal) adapterView.getSelectedItem();

                submit.setVisibility(View.VISIBLE);
                getBankCity();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                if (isNetworkAvailable()) {


                    setCertDataByName();
        }else {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error?")
                    .setMessage("Please Check Your Internet Connection")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
        }

            }
        });



//        TextWatcher fieldValidatorTextWatcher = new TextWatcher() {
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (filterLongEnough()) {
//
//                    setCertDataByName();
//                }
//            }
//
//            private boolean filterLongEnough() {
//                return rtoNumberTextView.getText().toString().trim().length() > 0;
//            }
//        };
//
//
//
//
//
//
//        if (isNetworkAvailable()) {
//
//
//            rtoNumberTextView.addTextChangedListener(fieldValidatorTextWatcher);
//        }else {
//            new AlertDialog.Builder(getActivity())
//                    .setTitle("Error?")
//                    .setMessage("Please Check Your Internet Connection")
//                    .setIcon(android.R.drawable.btn_dialog)
//                    .setNegativeButton(android.R.string.ok, null).show();
//        }






        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                   downloaddata();
            }
        });







        TextWatcher fieldValidatorTextWatchera = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (filterLongEnough()) {


                    setCertDataByName_search();
                }
            }

            private boolean filterLongEnough() {
                return rtoNumberTextView_search.getText().toString().trim().length() > 5;
            }
        };



        if (isNetworkAvailable()) {


            rtoNumberTextView_search.addTextChangedListener(fieldValidatorTextWatchera);




        }else {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error?")
                    .setMessage("Please Check Your Internet Connection")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
        }












        bank_citySpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerModal bank_city_modal = (SpinnerModal) adapterView.getSelectedItem();



                if (isNetworkAvailable()) {


                    getBankbranch(bank_city_modal.getSelKey());


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

        bank_branchSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerModal bank_branch_modal = (SpinnerModal) adapterView.getSelectedItem();
                if (!bank_branch_modal.selValue.equals("")) {



                    if (isNetworkAvailable()) {



                        ob = new ArrayList<>();
                        adapter = new MapAdapter(getActivity(), ob);
                        recyclerView.setAdapter(adapter);

                        setCertData();




                    }else {
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Error?")
                                .setMessage("Please Check Your Internet Connection")
                                .setIcon(android.R.drawable.btn_dialog)
                                .setNegativeButton(android.R.string.ok, null).show();
                    }










                    //adress();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);


        return v;
    }




    public void setCertData() {
        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        String url =con.base_url+"/api/admin/get/networkhospital/details";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        ob = new ArrayList<>();
                        adapter = new MapAdapter(getActivity(), ob);
                        recyclerView.setAdapter(adapter);

                        try {

                            Log.d("response_data",response);


                            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);


                            JSONObject jsonObja = new JSONObject(response);


                            String status= String.valueOf(jsonObja.getBoolean("status"));


                            if(status.equalsIgnoreCase("false")){
                                recyclerView.setVisibility(View.GONE);
                                no_image.setVisibility(View.VISIBLE);
                            }else {
                                recyclerView.setVisibility(View.VISIBLE);
                                no_image.setVisibility(View.GONE);
                                JSONArray jsonObj=jsonObja.getJSONArray("data");

                                for (int i = 0; i < jsonObj.length(); i++) {
                                    JSONObject explrObject = jsonObj.getJSONObject(i);
                                    ob.add(new MapAddress(explrObject.getString("id"),
                                            explrObject.getString("policy_id"),
                                            "",
                                            "",
                                            "",
                                            explrObject.getString("hospital_name"),
                                            explrObject.getString("address1"),
                                            explrObject.getString("address2"),
                                            explrObject.getString("city_name"),
                                            explrObject.getString("state_name"),
                                            explrObject.getString("phone_no"),
                                            explrObject.getString("pin_code"),
                                            "",
                                            "",
                                            explrObject.getString("email"),
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            ""));
                                }

                                adapter.notifyDataSetChanged();

                            }



                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },new Response.ErrorListener() {
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

        //SpinnerModal bank_name_modal = (SpinnerModal) bank_nameSpin.getSelectedItem();
        SpinnerModal bank_state_modal = (SpinnerModal) policy_type_spin_no.getSelectedItem();
        SpinnerModal bank_city_modal = (SpinnerModal) bank_branchSpin.getSelectedItem();
      /*  smr.addStringParam("emp_id", "1");
        smr.addStringParam("mobReq", "1");*/





        /*smr.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        mRequestQueue.add(smr);*/
        HashMap<String, String> params = new HashMap<>();
        params.put("city_name", bank_city_modal.getSelValue());
        params.put("policy_id", bank_state_modal.getSelKey());

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
                                    bank_nameList.add(new SpinnerModal(jsonObj.getString("policy_sub_type_id"),
                                            jsonObj.getString("policy_sub_type_name")));
                                    bank_name.add(jsonObj.getString("policy_sub_type_name"));
                                }
                            }





                            bank_nameAdapter = new ArrayAdapter<SpinnerModal>(getActivity(), R.layout.spinner_item, bank_nameList);
                            bank_nameAdapter.setDropDownViewResource(R.layout.spinner_item);


                            policy_type_spin.setAdapter(bank_nameAdapter);
                            // policy_type_spin_no.setAdapter(bank_nameAdapter);



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
        params.put("employer_id", employee_id);


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


                            Log.d("policies",response);
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



        HashMap<String, String> params = new HashMap<>();
        params.put("employer_id", employee_id);
        params.put("policy_sub_type_id", set_bank_name);
        params.put("user_type_name", "Employee");


        smr.setParams(params);
        rq.add(smr);
    }



    public void getBankCity() {
       RequestQueue rq = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        String url = con.base_url+"/api/admin/get/networkhospital/state";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {


                            JSONObject jsonArra = new JSONObject(response);

                            String status= String.valueOf(jsonArra.getBoolean("status"));

                            if(status.equalsIgnoreCase("false")){

                                bank_city = new ArrayList<>();
                                bank_cityList = new ArrayList<>();
                                bank_cityList.add(new SpinnerModal("", "Select State"));
                                bank_city.add("");

                            }else {
                                JSONArray array=jsonArra.getJSONArray("data");
                                bank_city = new ArrayList<>();
                                bank_cityList = new ArrayList<>();
                                bank_cityList.add(new SpinnerModal("", "Select State"));
                                bank_city.add("");


                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject jsonObj = array.getJSONObject(i);

                                    bank_cityList.add(new SpinnerModal(jsonObj.getString("state_name"),
                                            jsonObj.getString("state_name")));
                                    bank_city.add(jsonObj.getString("state_name"));
                                }

                            }


                            bank_cityAdapter = new ArrayAdapter<SpinnerModal>(context, R.layout.spinner_item, bank_cityList);
                            bank_cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            bank_citySpin.setAdapter(bank_cityAdapter);

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

        SpinnerModal bank_city_modal = (SpinnerModal) policy_type_spin_no.getSelectedItem();


       /* smr.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        mRequestQueue.add(smr);*/

        HashMap<String, String> params = new HashMap<>();
        params.put("policy_id", bank_city_modal.getSelKey());

        smr.setParams(params);
        rq.add(smr);
    }


    public  void Announcement(){
        String url = con.base_url+"/api/admin/get/annoucement/module-wise";
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
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




                        String type_name = explrObject.getString("type_name");


                        JSONArray  module=explrObject.getJSONArray("module");


                        for (int j = 0; j < module.length(); j++) {
                            JSONObject explrObjectj = module.getJSONObject(j);

                            String module_name = explrObjectj.getString("module_name");

                            if(module_name.equalsIgnoreCase("Network Hospital")){

                                String contenta= explrObject.getString("content");
                                String term_condition = explrObject.getString("term_condition");



                                content.setText(contenta);
                                title.setText(term_condition);

                                if(type_name.equalsIgnoreCase("Banner")){

                                    String media = explrObject.getString("media");
                                    Picasso.get().load(media).into(logo);




                                }




                            }



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
        mRequestQueue.add(mStringRequest);
    }





    public void getBankbranch(final String bank_city) {
        SpinnerModal bank_name_modal = (SpinnerModal) bank_citySpin.getSelectedItem();
       RequestQueue rq = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        String url = con.base_url+"/api/admin/get/networkhospital/city";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {


                        JSONObject jsonArra = new JSONObject(response);

                        String status= String.valueOf(jsonArra.getBoolean("status"));

                        if(status.equalsIgnoreCase("false")){
                            bank_branch = new ArrayList<>();
                            bank_branchList = new ArrayList<>();
                            bank_branchList.add(new SpinnerModal("", "Select City"));
                            bank_branch.add("");

                        }else {


                        bank_branch = new ArrayList<>();
                        bank_branchList = new ArrayList<>();
                        bank_branchList.add(new SpinnerModal("", "Select City"));
                        bank_branch.add("");
                        JSONArray array=jsonArra.getJSONArray("data");

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonObj = array.getJSONObject(i);


                            bank_branchList.add(new SpinnerModal(jsonObj.getString("CITY_NAME"), jsonObj.getString("CITY_NAME")));
                            bank_branch.add(jsonObj.getString("CITY_NAME"));
                        }

                        }
                        bank_branchAdapter = new ArrayAdapter<SpinnerModal>(context, R.layout.spinner_item, bank_branchList);
                        bank_branchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        bank_branchSpin.setAdapter(bank_branchAdapter);


                    } catch (Exception e) {

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
        params.put("state_name", bank_name_modal.getSelKey());
        SpinnerModal bank_city_modal = (SpinnerModal) policy_type_spin_no.getSelectedItem();
        params.put("policy_id", bank_city_modal.getSelKey());


        smr.setParams(params);
        rq.add(smr);
    }





    public void downloaddata() {
        SpinnerModal bank_name_modal = (SpinnerModal) bank_citySpin.getSelectedItem();
        RequestQueue rq = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        String url = con.base_url+"/api/admin/network-hospital/report-export";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {


                        JSONObject jsonArra = new JSONObject(response);

                        String status=jsonArra.getString("status");

                        if(status.equalsIgnoreCase("false")){
                            Snackbar snackbar = Snackbar
                                    .make(otp_resent, "No Data Found!", Snackbar.LENGTH_LONG);
                            snackbar.show();

                        }else {
                            JSONObject data=jsonArra.getJSONObject("data");

                            String download_report=data.getString("download_report");

                            DownloadManager downloadManager = null;
                            downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
                            Uri uri = Uri.parse(download_report);
                            DownloadManager.Request request = new DownloadManager.Request(uri);
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            Long reference = downloadManager.enqueue(request);

                            Snackbar snackbar = Snackbar
                                    .make(otp_resent, "Your File has been exported successfully!", Snackbar.LENGTH_LONG);
                            snackbar.show();


                        }



                    } catch (Exception e) {

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


        SpinnerModal bank_city_modal = (SpinnerModal) policy_type_spin_no.getSelectedItem();

        params.put("policy_id", bank_city_modal.getSelKey());

try{
    SpinnerModal citymodule = (SpinnerModal) bank_branchSpin.getSelectedItem();


    if(bank_name_modal.getSelKey().equalsIgnoreCase("")){

    }else {
        params.put("state_name", bank_name_modal.getSelKey());
    }

    if(citymodule.getSelKey().equalsIgnoreCase("")){

    }else {
        params.put("city_name", citymodule.getSelValue());
    }

    if(rtoNumberTextView_search.getText().toString().length()==0){

    }else {
        params.put("pin_code", rtoNumberTextView_search.getText().toString());
    }

    if(rtoNumberTextView.getText().toString().length()==0){

    }else {
        params.put("hospital_name", rtoNumberTextView.getText().toString());
    }




}catch (Exception e){

}

        smr.setParams(params);
        rq.add(smr);
    }




    public void setCertDataByName() {

       RequestQueue rq = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
       rq.getCache().clear();

        String url = con.base_url+"/api/admin/get/networkhospital/name";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ob = new ArrayList<>();
                        adapter = new MapAdapter(getActivity(), ob);
                        recyclerView.setAdapter(adapter);

                        try {

                            Log.d("response_data",response);


                            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);


                            JSONObject jsonObja = new JSONObject(response);


                            String status= String.valueOf(jsonObja.getBoolean("status"));


                            if(status.equalsIgnoreCase("false")){

                                recyclerView.setVisibility(View.GONE);

                                no_image.setVisibility(View.VISIBLE);

                            }else {
                                recyclerView.setVisibility(View.VISIBLE);
                                no_image.setVisibility(View.GONE);
                                JSONArray jsonObj=jsonObja.getJSONArray("data");

                                for (int i = 0; i < jsonObj.length(); i++) {
                                    JSONObject explrObject = jsonObj.getJSONObject(i);
                                    ob.add(new MapAddress(explrObject.getString("id"),
                                            explrObject.getString("policy_id"),
                                            "",
                                            "",
                                            "",
                                            explrObject.getString("hospital_name"),
                                            explrObject.getString("address1"),
                                            explrObject.getString("address2"),
                                            explrObject.getString("city_name"),
                                            explrObject.getString("state_name"),
                                            explrObject.getString("phone_no"),
                                            explrObject.getString("pin_code"),
                                            "",
                                            "",
                                            explrObject.getString("email"),
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            ""));
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




        HashMap<String, String> params = new HashMap<>();
        params.put("hospital_name", rtoNumberTextView.getText().toString());
        SpinnerModal bank_city_modal = (SpinnerModal) policy_type_spin_no.getSelectedItem();
        params.put("policy_id", bank_city_modal.getSelKey());


        smr.setParams(params);


        rq.add(smr);
    }


   void GetEmployeeId(){
   String url = con.base_url+"/api/admin/user";
       RequestQueue rq = Volley.newRequestQueue(getActivity(),
               new HurlStack(null, getSocketFactory()));

       rq.getCache().clear();
       StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {

               try {
                   JSONObject js=new JSONObject(response);

                   Log.d("mydata",response);
                   JSONArray jsonObj=js.getJSONArray("data");

                   for (int i = 0; i < jsonObj.length(); i++) {
                       JSONObject explrObject = jsonObj.getJSONObject(0);





                       String employer_id = explrObject.getString("employer_id");





                       if (employer_id != "null" && !employer_id.isEmpty()) {

                           employee_id=employer_id;

                           getBankName();
                           Announcement();
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
       rq.add(mStringRequest);

   }

    public void setCertDataByName_search() {


        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        String url = con.base_url+"/api/admin/get/statcity/pincode";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ob = new ArrayList<>();
                        adapter = new MapAdapter(getActivity(), ob);
                        recyclerView.setAdapter(adapter);

                        try {

                            Log.d("response_data",response);


                            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);


                            JSONObject jsonObja = new JSONObject(response);


                            String status= String.valueOf(jsonObja.getBoolean("status"));


                            if(status.equalsIgnoreCase("false")){
                                recyclerView.setVisibility(View.GONE);

                                no_image.setVisibility(View.VISIBLE);
                            }else {

                                recyclerView.setVisibility(View.VISIBLE);

                                no_image.setVisibility(View.GONE);
                                JSONArray jsonObj=jsonObja.getJSONArray("data");

                                for (int i = 0; i < jsonObj.length(); i++) {
                                    JSONObject explrObject = jsonObj.getJSONObject(i);
                                    ob.add(new MapAddress(explrObject.getString("id"),
                                            explrObject.getString("policy_id"),
                                            "",
                                            "",
                                            "",
                                            explrObject.getString("hospital_name"),
                                            explrObject.getString("address1"),
                                            explrObject.getString("address2"),
                                            explrObject.getString("city_name"),
                                            explrObject.getString("state_name"),
                                            explrObject.getString("phone_no"),
                                            explrObject.getString("pin_code"),
                                            "",
                                            "",
                                            explrObject.getString("email"),
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            ""));
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


        /*smr.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        mRequestQueue.add(smr);*/
        HashMap<String, String> params = new HashMap<>();
        params.put("pin_code", rtoNumberTextView_search.getText().toString());
        SpinnerModal bank_city_modal = (SpinnerModal) policy_type_spin_no.getSelectedItem();
        params.put("policy_id", bank_city_modal.getSelKey());



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