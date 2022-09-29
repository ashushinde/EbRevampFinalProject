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
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

import com.palm.newbenefit.Adapter.myFlexAdapter;
import com.palm.newbenefit.Adapter.myFlexsummaryAdapter;
import com.palm.newbenefit.Adapter.myInsuranceCoverAdapter;
import com.palm.newbenefit.Adapter.myvoluntaryAdapterGroupMyTermSumm;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.ApiConfig.RecyclerTouchListener;
import com.palm.newbenefit.Module.FlexBenefit;
import com.palm.newbenefit.Module.FlexBenefitWellness;
import com.palm.newbenefit.Module.InsuranceCover;
import com.palm.newbenefit.Module.VoluntaryBenefit;
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

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * A simple {@link Fragment} subclass.
 */
public class InsuranceFlexbenifitFragment extends Fragment  {
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

    int amountrs = 500;
    String emp_id;

    String company_id;
    ImageView info_text;
    Button group_cover,voluntary_cover,insurance_cover;
    private RequestQueue requestQueue;

    Spinner policy_type_spin;
    EditText dte_of_adminsiion,discharge_date,policy_name;
    TextView company_name,sum_insured,premium;
    Button send;
    ProgressDialog progressDialog = null;
    LinearLayout my_insurance_cover_form;
    ImageView bankPreview1;
    String selBankPath_cheque = null;
    List<FlexBenefit> oba = null;
    myFlexsummaryAdapter adaptera = null;
    RecyclerView recyclerViewa = null;

    TextView flex_wallet,flex_utilisation,to_pay;

    LinearLayout summary;









    int topay_count;





    LinearLayout hide_data;


    double premoium_amount;
    String installement;


    TextView month;

    RecyclerView policy_cycle_term;

    LinearLayout submitdata;
    TextView month_hide;




    List<VoluntaryBenefit> policyob_term = null;
    List<String> policy_no = null;
    myvoluntaryAdapterGroupMyTermSumm policySummaryAdapter_term = null;


    public InsuranceFlexbenifitFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_insurance_flexbenifit, container, false);
        con = new Constants();


        SharedPreferences prefs = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);

        token = prefs.getString("api_token", null);
        recyclerView = v.findViewById(R.id.ghi_recycle);
        info_text= v.findViewById(R.id.info_text);
        group_cover= v.findViewById(R.id.group_cover);
        voluntary_cover= v.findViewById(R.id.voluntary_cover);
        insurance_cover= v.findViewById(R.id.insurance_cover);
        send= v.findViewById(R.id.send);

        summary= v.findViewById(R.id.summary);



        policy_cycle_term= v.findViewById(R.id.policy_cycle_term);

        hide_data= v.findViewById(R.id.hide_data);

        premium= v.findViewById(R.id.premium);
        month= v.findViewById(R.id.month);
        month_hide= v.findViewById(R.id.month_hide);

        flex_utilisation= v.findViewById(R.id.flex_utilisation);
        flex_wallet= v.findViewById(R.id.flex_wallet);
        to_pay= v.findViewById(R.id.to_pay);
        my_insurance_cover_form= v.findViewById(R.id.my_flex_summary);
        recyclerViewa = v.findViewById(R.id.wellness);






        if (isNetworkAvailable()) {





            getAllVol();

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
                summary.setVisibility(View.VISIBLE);

                info_text.setVisibility(GONE);





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
                summary.setVisibility(View.GONE);

                info_text.setVisibility(GONE);
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
                summary.setVisibility(View.GONE);

                oba = new ArrayList<>();
                adaptera = new myFlexsummaryAdapter(getActivity(), oba);
                recyclerViewa.setAdapter(adaptera);

                setBankDetWelness();
                setBankDetFlex();


            }
        });




        if (isNetworkAvailable()) {


            group_cover.setBackgroundResource(R.drawable.nav_back_tab);
            voluntary_cover.setBackgroundResource(R.drawable.tab_curve);
            voluntary_cover.setTextColor(Color.BLACK);
            group_cover.setTextColor(Color.WHITE);

            insurance_cover.setBackgroundResource(R.drawable.tab_curve);
            insurance_cover.setTextColor(Color.BLACK);

            my_insurance_cover_form.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            summary.setVisibility(View.GONE);


            ob = new ArrayList<>();
            adapter = new myFlexAdapter(getActivity(), ob);
            recyclerView.setAdapter(adapter);





            setBankDet();



        }else {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error?")
                    .setMessage("Please Check Your Internet Connection")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
        }








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








        return v;

    }



    private void setBankDet() {

        ob = new ArrayList<>();
        adapter = new myFlexAdapter(getActivity(), ob);
        recyclerView.setAdapter(adapter);


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
                                info_text.setVisibility(VISIBLE);
                            } else {
                                policy_cycle_term.setVisibility(View.VISIBLE);
                                info_text.setVisibility(GONE);


                                JSONArray jsonObja =data.getJSONArray("data");







                       /* for (int k = 0;k < jsonObja.length(); k++) {

                            JSONObject jo_areag = (JSONObject) jsonObja.get(k);
                            int suminsured = jo_areag.getInt("suminsured");
                            double employee_premium= jo_areag.getDouble("employee_premium");

                            allsum=suminsured+allsum;
                            allPremium=employee_premium+allPremium;

                        }


                        String allsumdata=String.valueOf(allsum);
                        String allPremiumdata= String.valueOf(allPremium);*/




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
                                                "", policy_id, suminsurued_type_id,
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

