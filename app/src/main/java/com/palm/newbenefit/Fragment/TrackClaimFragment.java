package com.palm.newbenefit.Fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.palm.newbenefit.Activity.TrackActivity;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.Module.SpinnerModal;
import com.palm.newbenefit.Module.SpinnerModalFamilyData;
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
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrackClaimFragment extends Fragment {
    ProgressDialog progressDialog = null;
    Constants con = null;
    Context context;
    String mobileNumber = null;
    String token = null;
    String user_id = null;
    RecyclerView course_history_recycle;
    String bank_city_value = "";
    String bank_branch_value = "";
    String bank_name_value = "";
    String claimidd="yes";
    TextView register,insurar;
    Spinner bank_citySpin = null;
    Spinner bank_nameSpin = null;
    Spinner policy_name = null;

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
    Toolbar settingtoolbar;

String employee_id;

    ArrayList<String> bank_cityc = null;
    ArrayList<SpinnerModalFamilyData> bank_cityListc = null;
    ArrayAdapter<SpinnerModalFamilyData> bank_cityAdapterc = null;





   String emp_data;


    TextView submit;
    public TrackClaimFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_track_claim2, container, false);
        con=new Constants();

        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).setTitle("");
        SharedPreferences prefs = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);

        token = prefs.getString("api_token", null);
        user_id = prefs.getString("user_id", null);

        policy_name=v. findViewById(R.id.policy_name);
        bank_citySpin=v. findViewById(R.id.bank_citySpin);
        bank_branchSpin=v. findViewById(R.id.bank_branchSpin);
        bank_nameSpin=v.findViewById(R.id.bank_nameSpin);
        submit=v.findViewById(R.id.submit);
      /*  settingtoolbar = (Toolbar) findViewById(R.id.add_family_data);
        setSupportActionBar(settingtoolbar);
        getSupportActionBar().setTitle("Track Claim");
        settingtoolbar.setTitleTextColor(0xFFFFFFFF);


        settingtoolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_chevron_left_black_24dp));
        settingtoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
        //setCertData();
        GetEmployeeData();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    SpinnerModal policy = (SpinnerModal) bank_nameSpin.getSelectedItem();
                    SpinnerModalFamilyData member = (SpinnerModalFamilyData) bank_citySpin.getSelectedItem();
                    SpinnerModal claim = (SpinnerModal) bank_branchSpin.getSelectedItem();

                    int count = 0;


                    if (policy.getSelValue().trim().length() == 0) {
                        ++count;
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Error")
                                .setMessage("Please Select Policy Type")
                                .setIcon(android.R.drawable.btn_dialog)
                                .setNegativeButton(android.R.string.ok, null).show();
                        return;
                    }


                    if (member.getFamily_gender().equalsIgnoreCase(" ")) {
                        ++count;
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Error")
                                .setMessage("Please Select Member Name")
                                .setIcon(android.R.drawable.btn_dialog)
                                .setNegativeButton(android.R.string.ok, null).show();
                        return;
                    }


try{
    if (claim.getSelKey().trim().length() == 0) {
        ++count;
        new AlertDialog.Builder(getActivity())
                .setTitle("Error")
                .setMessage("Please Select Claim Id")
                .setIcon(android.R.drawable.btn_dialog)
                .setNegativeButton(android.R.string.ok, null).show();
        return;
    }

    if(claimidd.equalsIgnoreCase("no")){
        ++count;
        new AlertDialog.Builder(getActivity())
                .setTitle("Error")
                .setMessage("No Claim Id Found")
                .setIcon(android.R.drawable.btn_dialog)
                .setNegativeButton(android.R.string.ok, null).show();
        return;
    }
}catch (Exception e){

}


                    if(count==0){


                        Intent intent = new Intent(getActivity(), TrackActivity.class);
                        intent.putExtra("policy",member.getFamily_dob());
                        intent.putExtra("member",member.getSelValue());
                        intent.putExtra("claim",claim.getSelValue());
                        getActivity(). startActivity(intent);


                    }


                }catch (Exception e){


                        new AlertDialog.Builder(getActivity())
                                .setTitle("Error")
                                .setMessage("No Claim Id Found")
                                .setIcon(android.R.drawable.btn_dialog)
                                .setNegativeButton(android.R.string.ok, null).show();
                        return;

                }




            }
        });





        if (isNetworkAvailable()) {




   GetEmployeeId();

//          getBankName(bank_name_value);


        }else {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error?")
                    .setMessage("Please Check Your Internet Connection")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
        }








        bank_nameSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerModal bank_name_modal = (SpinnerModal) adapterView.getSelectedItem();
                if (!bank_name_modal.selValue.equals(""))





                    if (isNetworkAvailable()) {


                        GetEmployeeId();
                        getBankNamenumber(bank_name_modal.getSelKey());





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



        policy_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerModal bank_name_modal = (SpinnerModal) adapterView.getSelectedItem();
                if (!bank_name_modal.selValue.equals(""))





                    if (isNetworkAvailable()) {






                        getBankCity(bank_name_modal.selValue, bank_city_value);

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


        bank_citySpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerModalFamilyData bank_city_modal = (SpinnerModalFamilyData) adapterView.getSelectedItem();
                if (!bank_city_modal.selValue.equals(""))



                    if (isNetworkAvailable()) {






                        getBankbranch(bank_city_modal.getFamily_dob(), bank_branch_value);


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

                //setCertData();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return  v;
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

                            getBankName(bank_name_value);
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
        rq.add(mStringRequest);

    }
    private void getBankName(final String set_bank_name) {
        //String url =con.base_url+"/api/employee/policies";
        String url =con.base_url+"/api/broker/get/employer/policy/type";
        RequestQueue rq = Volley.newRequestQueue(getActivity());

       // RequestQueue rq = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
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


                                if(jsonObj.getString("name").equalsIgnoreCase("Group Mediclaim")||
                                        jsonObj.getString("name").equalsIgnoreCase("Group Personal Accident")){
                                    bank_nameList.add(new SpinnerModal(jsonObj.getString("id"),
                                            jsonObj.getString("name")));
                                    bank_name.add(jsonObj.getString("name"));
                                }
                            }





                            bank_nameAdapter = new ArrayAdapter<SpinnerModal>(getActivity(), R.layout.spinner_item, bank_nameList);
                            bank_nameAdapter.setDropDownViewResource(R.layout.spinner_item);


                            bank_nameSpin.setAdapter(bank_nameAdapter);
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
        RequestQueue rq = Volley.newRequestQueue(getActivity());

        //RequestQueue rq = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
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



                            policy_name.setAdapter(bank_nameAdapter);



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




        HashMap<String, String> params = new HashMap<>();
        params.put("employer_id", employee_id);
        params.put("policy_sub_type_id", set_bank_name);
        params.put("user_type_name", "Employee");


        smr.setParams(params);
        rq.add(smr);
    }




    public void getBankCity(final String  bank_name, final String set_bank_city) {

        progressDialog = ProgressDialog.show(getActivity(), "",
                "Saving. Please wait...", true);

        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();

        String url = con.base_url+"/api/admin/get/emp_member";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject objectd=new JSONObject(response);

                            JSONArray jsonArr = objectd.getJSONArray("data");
                            bank_cityc = new ArrayList<>();
                            bank_cityListc = new ArrayList<>();
                            bank_cityListc.add(new SpinnerModalFamilyData("Select Patient Name", "0",",",""
                                    ,"","",""));
                            bank_cityc.add("0");
                            for (int i = 0; i < jsonArr.length(); i++) {
                                JSONObject jsonObj = jsonArr.getJSONObject(i);



                                bank_cityListc.add(new SpinnerModalFamilyData(jsonObj.getString("name"),
                                        String.valueOf(jsonObj.getString("tpa_member_id")),
                                        String.valueOf(jsonObj.getInt("member_id")),
                                        jsonObj.getString("email"),
                                        jsonObj.getString("relation_name"),

                                        "",
                                        ""));
                                bank_cityc.add(jsonObj.getString("name"));
                            }
                            bank_cityAdapterc = new ArrayAdapter<SpinnerModalFamilyData>(getActivity(), R.layout.spinner_item, bank_cityListc);
                            bank_cityAdapterc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            bank_citySpin.setAdapter(bank_cityAdapterc);


                            progressDialog.dismiss();
                        } catch (Exception e) {
                            progressDialog.dismiss();
                            Log.e("membererror", e.toString());
                        }

                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
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




        SpinnerModal patientk = (SpinnerModal) policy_name.getSelectedItem();

        HashMap<String, String> params = new HashMap<>();
        params.put("employee_id", emp_data);

        params.put("policy_id", patientk.getSelKey());

        smr.setParams(params);
        rq.add(smr);

        progressDialog.dismiss();

    }
    void GetEmployeeData(){
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

                        emp_data = explrObject.getString("employee_id");




                        if (employer_id != "null" && !employer_id.isEmpty()) {

                            employee_id=employer_id;



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


    public void getBankbranch(final String bank_city, final String set_bank_branch) {
        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        SpinnerModal bank_name_modal = (SpinnerModal) bank_nameSpin.getSelectedItem();

        String url = con.base_url+"/api/admin/get/claimId";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject objectd=new JSONObject(response);
                            Log.e("claimresponse", response.toString());
                            String status= String.valueOf(objectd.getString("status"));
                            if (status.equalsIgnoreCase("false")) {
                                /*try{
                                    String message= String.valueOf(objectd.getString("message"));
                                    claimidd="no";
                                    new AlertDialog.Builder(getActivity())
                                            .setTitle("Error?")
                                            .setMessage(message)
                                            .setIcon(android.R.drawable.btn_dialog)
                                            .setNegativeButton(android.R.string.ok, null).show();
                                }catch (Exception e){

                                }*/

                            }



                            bank_branch = new ArrayList<>();
                            bank_branchList = new ArrayList<>();
                            bank_branchList.add(new SpinnerModal("", "Select Claim Id"));
                            bank_branch.add("");




                                JSONArray jsonArr = objectd.getJSONArray("data");

                                for (int i = 0; i < jsonArr.length(); i++) {

                                    JSONObject jsonObj = jsonArr.getJSONObject(i);

                                    if(jsonObj.getString("claim_no").equalsIgnoreCase("null")){

                                    }else {
                                        bank_branchList.add(new SpinnerModal(jsonObj.getString("claim_no"), jsonObj.getString("claim_no")));
                                        bank_branch.add(jsonObj.getString("claim_no"));
                                    }

                                }




                            bank_branchAdapter = new ArrayAdapter<SpinnerModal>(getActivity(), R.layout.spinner_item, bank_branchList);
                            bank_branchAdapter.setDropDownViewResource(R.layout.spinner_item);
                            bank_branchSpin.setAdapter(bank_branchAdapter);



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
        params.put("member_id", bank_city);
        Log.d("member_id", bank_city);

        smr.setParams(params);
        rq.add(smr);

    }

    public void setCertData() {
        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        String url = con.base_url + "/get_dates_on_claim_id";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray jsonObj = new JSONArray(response);


                            for (int i = 0; i < jsonObj.length(); i++) {
                                JSONObject explrObject = jsonObj.getJSONObject(i);

                                String date= explrObject.getString("register_date");

                                register.setText(date);
                            }


                        } catch (Exception e) {
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




        SpinnerModal bank_city_modal = (SpinnerModal) bank_branchSpin.getSelectedItem();


       /* smr.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        mRequestQueue.add(smr);*/

        HashMap<String, String> params = new HashMap<>();
        params.put("claim_id", bank_city_modal.getSelValue());


        smr.setParams(params);
        rq.add(smr);




        RequestQueue rqa = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rqa.getCache().clear();

        String urla = con.base_url + "/get_datesdocs_on_claim_id";
        StringRequest smra = new StringRequest(Request.Method.POST, urla,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray jsonObj = new JSONArray(response);



                            for (int i = 0; i < jsonObj.length(); i++) {
                                JSONObject explrObject = jsonObj.getJSONObject(i);

                                String date= explrObject.getString("doc_submitted");

                                insurar.setText(date);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });




        SpinnerModal bank_city_modala = (SpinnerModal) bank_branchSpin.getSelectedItem();


       /* smr.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue mRequestQueuea = Volley.newRequestQueue(getActivity());
        mRequestQueuea.add(smr);
*/


        HashMap<String, String> paramss = new HashMap<>();
        paramss.put("emp_id", "1");
        paramss.put("mobReq", "1");
        paramss.put("claim_id", bank_city_modala.getSelValue());


        smra.setParams(params);

        rqa.add(smra);







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
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

