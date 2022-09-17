package com.palm.newbenefit.Fragment;


import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.CheckBox;
import android.widget.ImageView;
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
import com.palm.newbenefit.Adapter.MemberEnrollAdapterBEnefit;
import com.palm.newbenefit.Adapter.NomineeEnrollAdapterBenefit;
import com.palm.newbenefit.Adapter.NoteAdapter;
import com.palm.newbenefit.Adapter.myFlexsummaryAdapterBenefit;
import com.palm.newbenefit.Adapter.myvoluntaryAdapterBenefit;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.ApiConfig.RecyclerTouchListener;
import com.palm.newbenefit.Module.FlexBenefit;
import com.palm.newbenefit.Module.MemberEnroll;
import com.palm.newbenefit.Module.NomineeEnroll;
import com.palm.newbenefit.Module.Note;
import com.palm.newbenefit.Module.VoluntaryBenefit;
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
import java.util.Arrays;
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
public class MyBenefirSummaryFragment extends Fragment {
    int gg;
    Constants con = null;
    Context context;
    String mobileNumber = null;

    String user_id = null;

    List<MemberEnroll> ob = null;
    MemberEnrollAdapterBEnefit adapter = null;

    List<NomineeEnroll> oba = null;
    NomineeEnrollAdapterBenefit adaptera = null;


    List<FlexBenefit> obw = null;
    myFlexsummaryAdapterBenefit adapterw = null;


    List<VoluntaryBenefit> obv = null;
    myvoluntaryAdapterBenefit adapterv = null;


    List<Note> obnote = null;
    NoteAdapter noteadapter = null;
    String nominee_requirement;

    String status = "1";
    TextView flex_wallet,flex_utilisation,to_pay;

    RecyclerView recyclerView = null;
    RecyclerView recyclerViewa = null;
    TextView payto;
    int datas=0,dataa=0;
    String token,policy_id;
    ImageView info_text;
    private boolean doInOnAttach = false;
LinearLayout member_enroll,nominee_enroll,wellness,voluntary_cover,group_cover;
RecyclerView wellness_recycle,voluntary;
TextView note;
RecyclerView contentrecyle;
    TextView submit;
    CheckBox check;
    public MyBenefirSummaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_my_benefir_summary, container, false);

        con = new Constants();
        SharedPreferences prefs = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);

        token = prefs.getString("api_token", null);
        policy_id = prefs.getString("policy_id", null);

        recyclerView = v.findViewById(R.id.members);
        recyclerViewa= v.findViewById(R.id.nominee);
        wellness_recycle= v.findViewById(R.id.wellness_recycle);
        member_enroll= v.findViewById(R.id.member_enroll);
        nominee_enroll= v.findViewById(R.id.nominee_enroll);
        wellness_recycle= v.findViewById(R.id.wellness_recycle);
        flex_utilisation= v.findViewById(R.id.flex_utilisation);
        flex_wallet= v.findViewById(R.id.flex_wallet);
        to_pay= v.findViewById(R.id.to_pay);
        voluntary_cover= v.findViewById(R.id.voluntary_cover);
        group_cover= v.findViewById(R.id.group_cover);
        wellness= v.findViewById(R.id.wellness);
        payto= v.findViewById(R.id.payto);
        voluntary= v.findViewById(R.id.voluntary);

        note= v.findViewById(R.id.note);
        contentrecyle= v.findViewById(R.id.contentrecyle);
        submit= v.findViewById(R.id.submitAll);
        check= v.findViewById(R.id.check);
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





        int numberOfColumnsw = 1;
        wellness_recycle.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumnsw));

        wellness_recycle.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), wellness_recycle, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager managerw = new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        wellness_recycle.setLayoutManager(managerw);









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


        GridLayoutManager managera = new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        recyclerViewa.setLayoutManager(managera);








        int numberOfColumnsv = 1;
        voluntary.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumnsv));

        voluntary.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), voluntary, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager managerv = new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        voluntary.setLayoutManager(managerv);







        int numberOfColumnsn = 1;
        contentrecyle.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumnsn));

        contentrecyle.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), contentrecyle, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager managern = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        contentrecyle.setLayoutManager(managern);








        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (status.equalsIgnoreCase("0")) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                    alertDialog.setTitle("Do You Want To Proceed?");
                    alertDialog.setMessage("If You Click Yes, The Data Submitted By You Will Be Considered Final");
                    alertDialog.setNegativeButton(R.string.no, null);
                    alertDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {

                            if (isNetworkAvailable()) {

if(nominee_requirement.equalsIgnoreCase("1")){
    if(datas==100)
    {
        Data();
    }else {
        new AlertDialog.Builder(getActivity())
                .setTitle("Error?")
                .setMessage("Total share for nominee should be 100")
                .setIcon(android.R.drawable.btn_dialog)
                .setNegativeButton(android.R.string.ok, null).show();
    }
}else {
    Data();
}


                            }else {
                                new AlertDialog.Builder(getActivity())
                                        .setTitle("Error?")
                                        .setMessage("Please Check Your Internet Connection")
                                        .setIcon(android.R.drawable.btn_dialog)
                                        .setNegativeButton(android.R.string.ok, null).show();
                            }











                        }
                    });

                    alertDialog.show();


                } else {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Error")
                            .setMessage("Agree Above Information")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setNegativeButton(android.R.string.ok, null).show();
                }

            }
        });




        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                boolean checked = ((CheckBox) v).isChecked();
                // Check which checkbox was clicked
                if (checked){
                    status = "0";
                }
                else{
                    status="1";
                }






            }
        });



        return v;

    }



    public void Data() {
        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        String url =con.base_url+ "/api/employee/get/enroll/confirmation";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {





                            JSONObject jsonObject = new JSONObject(response);

                            Log.d("response",response);



                            String datato= String.valueOf(jsonObject.getBoolean("status"));
                            String message=jsonObject.getString("message");

                            if(datato.equalsIgnoreCase("true")){
                                new AlertDialog.Builder(getActivity())
                                        .setTitle("")
                                        .setMessage(message)
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setNegativeButton(android.R.string.ok, null).show();
                                submit.setVisibility(View.GONE);
                            }else {
                                new AlertDialog.Builder(getActivity())
                                        .setTitle("")
                                        .setMessage(message)
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setNegativeButton(android.R.string.ok, null).show();
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




        HashMap<String, String> params = new HashMap<>();
        params.put("policy_id", policy_id);
        params.put("confirmation_flag", "1");

        smr.setParams(params);
        rq.add(smr);

        //Application.getInstance().addToRequestQueue(smr);

    }



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        context = getActivity();
        if (isVisibleToUser && (getActivity() != null)) {



            if (isNetworkAvailable()) {

                ob = new ArrayList<>();
                adapter = new MemberEnrollAdapterBEnefit(getActivity(), ob, token);
                recyclerView.setAdapter(adapter);


                oba= new ArrayList<>();
                adaptera = new NomineeEnrollAdapterBenefit(getActivity(), oba, token);
                recyclerViewa.setAdapter(adaptera);


                obw= new ArrayList<>();
                adapterw = new myFlexsummaryAdapterBenefit(getActivity(), obw);
                wellness_recycle.setAdapter(adapterw);



                obv= new ArrayList<>();
                adapterv = new myvoluntaryAdapterBenefit(getActivity(), obv);
                voluntary.setAdapter(adapterv);



                obnote= new ArrayList<>();
                noteadapter = new NoteAdapter(getActivity(), obnote);
                contentrecyle.setAdapter(noteadapter);

                setBankDetVoluntary();
                setBankDetVoluntaryMediclaim();
                setBankDet();
                nominee();
                setBankDetFlex();
                setBankDetWelness();
                NoteData();
                NominneConfig();

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


    void NominneConfig(){

        RequestQueue rq = Volley.newRequestQueue(getActivity());
        rq.getCache().clear();

        //  RequestQueue rq = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));

        String url = "http://eb.fynity.in/api/broker/get/nominee-config?configurable_type=policy&configurable_id="+policy_id;
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {



                            Log.d("response_dependent",response);
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array =jsonObject.getJSONArray("data");

                            JSONObject object = array.getJSONObject(0);

                             nominee_requirement =object.getString("nominee_requirement");







                        } catch (Exception e) {

                            e.printStackTrace();
                            Log.d("nominee_config", e.toString());


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

        params.put("configurable_type", "policy");
        params.put("configurable_id", policy_id);
        smr.setParams(params);
        rq.add(smr);
    }


    private void setBankDetWelness() {


        obw = new ArrayList<>();
        adapterw = new myFlexsummaryAdapterBenefit(getActivity(), obw);
        wellness_recycle.setAdapter(adapterw);


        String url = con.base_url+"/api/employee/get/wellness-details";
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);

                    Log.d("response",response);
                 JSONArray jsonObj=js.getJSONArray("data");

                    if (jsonObj.length() == 0){
                        wellness.setVisibility(View.GONE);

                        wellness_recycle.setVisibility(View.GONE);
                    }else {
                        wellness.setVisibility(View.GONE);

                        wellness_recycle.setVisibility(View.GONE);



                        for (int j = 0; j < jsonObj.length(); j++) {

                            JSONObject jo_area = (JSONObject) jsonObj.get(j);

                            String name = jo_area.getString("name");
                            String flex_name = jo_area.getString("flex_name");
                            String deduction_type = jo_area.getString("deduction_type");
                            String final_amount = (jo_area.getString("final_amount"));



                            obw.add(new FlexBenefit(name,flex_name,deduction_type,final_amount));

                        }

                        adapterw.notifyDataSetChanged();

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





    private void NoteData() {


        obnote= new ArrayList<>();
        noteadapter = new NoteAdapter(getActivity(), obnote);
        contentrecyle.setAdapter(noteadapter);

        String url = con.base_url+"/api/employee/get/confirmation";
      RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);

                    Log.d("response",response);







                    JSONArray jsonObj=js.getJSONArray("data");

                    if (jsonObj.length() == 0){


                        contentrecyle.setVisibility(View.GONE);
                    }else {

                        contentrecyle.setVisibility(View.VISIBLE);



                        for (int j = 0; j < jsonObj.length(); j++) {



                            JSONObject jo_arear = (JSONObject) jsonObj.get(j);


                           String notea = jo_arear.getString("note");
                            note.setText(notea);

                            JSONArray jo_area = jo_arear.getJSONArray("content");
                            List<String> sellItems = Arrays.asList(jo_area.toString().split(","));




                            for (int h = 0; h < sellItems.size(); h++) {
                                obnote.add(new Note(jo_area.getString(h)));


                            }


                            noteadapter = new NoteAdapter(getActivity(), obnote);
                            contentrecyle.setAdapter(noteadapter);
                            Log.d("notelist",obnote.toString());
                            noteadapter.notifyDataSetChanged();





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


        HashMap<String, String> params = new HashMap<>();
        params.put("policy_sub_type_id","1");

        mStringRequest.setParams(params);


        mRequestQueue.add(mStringRequest);

    }














    private void setBankDetFlex() {

        String url = con.base_url+"/api/employee/get/salary-deduction?policy_id="+policy_id;
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
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

//                            String flexWallet= String.valueOf(jsonObj.getInt("flexWallet"));
//                            String walletUtilization= String.valueOf(jsonObj.getInt("walletUtilization"));
                            String toPay= String.valueOf(js.getInt("salary_deduction"));

                            flex_wallet.setText(toPay);
                            flex_utilisation.setText(toPay);
                            to_pay.setText(toPay);
                            payto.setText(toPay);
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


    private void setBankDet() {


        ob = new ArrayList<>();
        adapter = new MemberEnrollAdapterBEnefit(getActivity(), ob, token);
        recyclerView.setAdapter(adapter);

        String url = con.base_url+"/api/employee/get/enroll/members?"+policy_id;
        RequestQueue rq = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject data= new JSONObject(response);

                    String statusa= String.valueOf(data.getBoolean("status"));



                    if (statusa.equalsIgnoreCase("false")) {
                        member_enroll.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        member_enroll.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                        JSONArray jsonObj =data.getJSONArray("data");

                        for (int j = 0; j < jsonObj.length(); j++) {



                            JSONObject jo_area = (JSONObject) jsonObj.get(j);

                            String company_id ="";
                            String tpa_member_name = "";
                            String tpa_member_id = (jo_area.getString("health_card"));
                            String policy_detail_id = "";
                            String broker_id = "";
                            String family_relation_id = String.valueOf((jo_area.getInt("id")));
                            String status ="";
                            String policy_mem_sum_insured = (jo_area.getString("suminsured"));
                            String policy_no ="";
                            String TPA_id = "";
                            String policy_sub_type_id ="";
                            String family_id = (jo_area.getString("id"));
                            String fr_id = (jo_area.getString("relation_id"));
                            String fr_name = (jo_area.getString("relation_name"));
                            String emp_firstname = (jo_area.getString("first_name"));
                            String emp_lastname = jo_area.getString("last_name");
                            String gender = jo_area.getString("gender");
                            String bdate = jo_area.getString("dob");
                            String member_id = (jo_area.getString("id"));
                            String age = String.valueOf((jo_area.getInt("age")));
                            String age_type = (jo_area.getString("age_type"));
                            String policy_mem_sum_premium = (jo_area.getString("employee_premium"));
                            String start_date = "";
                            String policy_member_id = "";
                            String employee_policy_mem_sum_premium = jo_area.getString("employee_premium");
                            String pay_type = "";

                            ob.add(new MemberEnroll(company_id, tpa_member_name, tpa_member_id, policy_detail_id, broker_id,
                                    family_relation_id, status, policy_no, TPA_id, policy_sub_type_id, family_id,
                                    fr_id, fr_name, emp_firstname, emp_lastname, gender, bdate, member_id, age, age_type, policy_mem_sum_premium,
                                    start_date, policy_member_id, employee_policy_mem_sum_premium, pay_type, policy_mem_sum_insured));
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




    private void setBankDetVoluntary() {


        obv = new ArrayList<>();
        adapterv = new myvoluntaryAdapterBenefit(getActivity(), obv);
        voluntary.setAdapter(adapterv);

        String url = con.base_url+"/api/employee/get/all-policy-member?policy_id="+policy_id;
       RequestQueue rq = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject data= new JSONObject(response);

                    Log.e("group_response", data.toString());

                    String statusa= String.valueOf(data.getBoolean("status"));



                    if (statusa.equalsIgnoreCase("false")) {
                        voluntary_cover.setVisibility(View.GONE);
                        voluntary.setVisibility(View.GONE);
                    } else {
                        voluntary_cover.setVisibility(View.VISIBLE);
                        voluntary.setVisibility(View.VISIBLE);

                        JSONObject jsonObja =data.getJSONObject("data");

                        JSONArray jsonObj =jsonObja.getJSONArray("Group Mediclaim");

                        for (int j = 0; j < jsonObj.length(); j++) {

                            JSONObject jo_area = (JSONObject) jsonObj.get(j);


                            String name = (jo_area.getString("name"));
                            String first_name = (jo_area.getString("first_name"));
                            String last_name = (jo_area.getString("last_name"));
                            String suminsured = String.valueOf((jo_area.getString("suminsured")));
                            String dob = (jo_area.getString("dob"));
                            String gender = (jo_area.getString("gender"));
                            String member_email = jo_area.getString("member_email");
                            String member_mob_no = jo_area.getString("member_mob_no");

                            obv.add(new VoluntaryBenefit(name,first_name,last_name,suminsured,
                                    dob,gender,member_email,member_mob_no));
                        }

                        adapterv.notifyDataSetChanged();
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





    private void setBankDetVoluntaryMediclaim() {


        obv = new ArrayList<>();
        adapterv = new myvoluntaryAdapterBenefit(getActivity(), obv);
        voluntary.setAdapter(adapterv);

        String url = con.base_url+"/api/employee/get/all-policy-member?policy_id="+policy_id;
        RequestQueue rq = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject data= new JSONObject(response);

                    Log.e("group_response", data.toString());

                    String statusa= String.valueOf(data.getBoolean("status"));



                    if (statusa.equalsIgnoreCase("false")) {
                        voluntary_cover.setVisibility(View.GONE);
                        voluntary.setVisibility(View.GONE);
                    } else {
                        voluntary_cover.setVisibility(View.VISIBLE);
                        voluntary.setVisibility(View.VISIBLE);

                        JSONObject jsonObja =data.getJSONObject("data");

                        JSONArray jsonObj =jsonObja.getJSONArray("Group Mediclaim");

                        for (int j = 0; j < jsonObj.length(); j++) {

                            JSONObject jo_area = (JSONObject) jsonObj.get(j);


                            String name = (jo_area.getString("name"));
                            String first_name = (jo_area.getString("first_name"));
                            String last_name = (jo_area.getString("last_name"));
                            String suminsured = String.valueOf((jo_area.getString("suminsured")));
                            String dob = (jo_area.getString("dob"));
                            String gender = (jo_area.getString("gender"));
                            String member_email = jo_area.getString("member_email");
                            String member_mob_no = jo_area.getString("member_mob_no");

                            obv.add(new VoluntaryBenefit(name,first_name,last_name,suminsured,
                                    dob,gender,member_email,member_mob_no));
                        }

                        adapterv.notifyDataSetChanged();
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














    private void nominee() {


        oba = new ArrayList<>();
        adaptera = new NomineeEnrollAdapterBenefit(getActivity(), oba, token);
        recyclerViewa.setAdapter(adaptera);

        String url = con.base_url+"/api/employee/get/nominee";
        RequestQueue rq = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject data= new JSONObject(response);

                    Log.d("nominee",response);

                    String statusa= String.valueOf(data.getBoolean("status"));



                    if (statusa.equalsIgnoreCase("false")) {
                        nominee_enroll.setVisibility(View.GONE);
                        recyclerViewa.setVisibility(View.GONE);
                    } else {
                        nominee_enroll.setVisibility(View.VISIBLE);
                        recyclerViewa.setVisibility(View.VISIBLE);
                        JSONArray jsonObj =data.getJSONArray("data");
                        ArrayList<Integer> arrlist = new ArrayList<Integer>();

                        for (int j = 0; j < jsonObj.length(); j++) {

                            JSONObject jo_area = (JSONObject) jsonObj.get(j);


                            String fr_name = jo_area.getString("nominee_relation");
                            String nominee_id = jo_area.getString("id");
                            String emp_id = jo_area.getString("emp_id");
                            String nominee_fname = (jo_area.getString("nominee_fname"));
                            String nominee_lname = (jo_area.getString("nominee_lname"));
                            String fr_id = (jo_area.getString("nominee_relation"));
                            String guardian_relation = (jo_area.getString("guardian_relation"));
                            String guardian_fname = (jo_area.getString("guardian_fname"));
                            String guardian_lname = jo_area.getString("guardian_lname");
                            String guardian_dob = jo_area.getString("guardian_dob");
                            String share_percentile = jo_area.getString("share_per");
                            String nominee_dob = (jo_area.getString("nominee_dob"));
                            String confirmed_flag = "";
                            String confirmed_date = "";
                            String status = (jo_area.getString("is_confirm"));
                            String created_at = "";
                            datas += Integer.valueOf(share_percentile);
                            oba.add(new NomineeEnroll(fr_name,nominee_id,emp_id,nominee_fname,nominee_lname,fr_id,guardian_relation,guardian_fname,guardian_lname,guardian_dob,share_percentile,nominee_dob,confirmed_flag,confirmed_date,status,created_at));

                        }
                        Log.d("nominee",oba.toString());


//                        for (Integer number : arrlist) {
//                            datas=number+dataa;
//                        }
                        Log.d("sumdata", String.valueOf(datas));

                        adaptera.notifyDataSetChanged();
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
        params.put("policy_id", policy_id);

        mStringRequest.setParams(params);
        rq.add(mStringRequest);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (!getUserVisibleHint()) {


            return;
        }


        if (isNetworkAvailable()) {
            setBankDetVoluntary();
            setBankDet();
            nominee();
            setBankDetFlex();
            setBankDetWelness();
            NoteData();
            NominneConfig();
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
