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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
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
import com.android.volley.toolbox.Volley;
import com.palm.newbenefit.Adapter.myFlexAdapter;
import com.palm.newbenefit.Adapter.myFlexsummaryAdapter;
import com.palm.newbenefit.Adapter.myInsuranceCoverAdapterFlex;
import com.palm.newbenefit.Adapter.myvoluntaryAdapterGroupMyTermSumm;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.ApiConfig.RecyclerTouchListener;
import com.palm.newbenefit.Module.FlexBenefit;
import com.palm.newbenefit.Module.FlexBenefitWellness;
import com.palm.newbenefit.Module.MyInsuranceFlex;
import com.palm.newbenefit.Module.VoluntaryBenefit;
import com.kmd.newbenefit.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class InsuranceFlexFragment extends Fragment {


    int gg;
    Constants con = null;
    Context context;
    String mobileNumber = null;
    String token = null;
    String user_id = null;
    List<FlexBenefitWellness> ob = null;
    myFlexAdapter adapter = null;


    List<MyInsuranceFlex> obab = null;
    myInsuranceCoverAdapterFlex adapterab = null;




    RecyclerView recyclerView = null;
    RecyclerView insurance_recycle=null;
    int amountrs = 500;
    String emp_id;

    String company_id;
    ImageView info_text;
    Button group_cover,voluntary_cover,insurance_cover;
    private RequestQueue requestQueue;



    Button send;
    ProgressDialog progressDialog = null;
    LinearLayout my_insurance_cover_form;
    ImageView bankPreview1;
    String selBankPath_cheque = null;
    List<FlexBenefit> oba = null;
    myFlexsummaryAdapter adaptera = null;
    RecyclerView recyclerViewa = null;

    TextView flex_wallet,flex_utilisation,to_pay;


    public InsuranceFlexFragment() {
        // Required empty public constructor
    }





    int topay_count;




    LinearLayout summary;






    List<VoluntaryBenefit> policyob_term = null;
    List<String> policy_no = null;
    myvoluntaryAdapterGroupMyTermSumm policySummaryAdapter_term = null;





    LinearLayout hide_data;


    double premoium_amount;
    String installement;

    TextView premium;
    TextView month;

    RecyclerView policy_cycle_term;


    TextView month_hide;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_insurance_flex, container, false);

        SharedPreferences prefs = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);
        con=new Constants();

        token = prefs.getString("api_token", null);
        recyclerView = v.findViewById(R.id.ghi_recycle);
        info_text= v.findViewById(R.id.info_text);
        group_cover= v.findViewById(R.id.group_cover);
        voluntary_cover= v.findViewById(R.id.voluntary_cover);
        insurance_cover= v.findViewById(R.id.insurance_cover);
        send= v.findViewById(R.id.send);


        flex_utilisation= v.findViewById(R.id.flex_utilisation);
        flex_wallet= v.findViewById(R.id.flex_wallet);
        to_pay= v.findViewById(R.id.to_pay);
        my_insurance_cover_form= v.findViewById(R.id.my_flex_summary);
        recyclerViewa = v.findViewById(R.id.wellness);

        insurance_recycle= v.findViewById(R.id.insurance_recycle);

        summary= v.findViewById(R.id.summary);



        policy_cycle_term= v.findViewById(R.id.policy_cycle_term);

        hide_data= v.findViewById(R.id.hide_data);

        premium= v.findViewById(R.id.premium);
        month= v.findViewById(R.id.month);
        month_hide= v.findViewById(R.id.month_hide);


        obab = new ArrayList<>();

        adapterab = new myInsuranceCoverAdapterFlex(getActivity(), obab);
        insurance_recycle.setAdapter(adapterab);




        insurance_cover.setBackgroundResource(R.drawable.nav_back_tab);
        group_cover.setBackgroundResource(R.drawable.tab_curve);
        voluntary_cover.setBackgroundResource(R.drawable.tab_curve);
        voluntary_cover.setTextColor(Color.BLACK);
        group_cover.setTextColor(Color.BLACK);
        insurance_cover.setTextColor(Color.WHITE);

        my_insurance_cover_form.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        insurance_recycle.setVisibility(View.VISIBLE);

       /* obab = new ArrayList<>();
        adapterab = new myInsuranceCoverAdapterFlex(getActivity(), obab);
        insurance_recycle.setAdapter(adapterab);





        setInsurance();
*/

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
                insurance_recycle.setVisibility(View.VISIBLE);

                obab = new ArrayList<>();
                adapterab = new myInsuranceCoverAdapterFlex(getActivity(), obab);
                insurance_recycle.setAdapter(adapterab);





                setInsurance();


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
                insurance_recycle.setVisibility(View.GONE);


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
                insurance_recycle.setVisibility(View.GONE);

              /*  oba = new ArrayList<>();
                adaptera = new myFlexsummaryAdapter(getActivity(), oba);
                recyclerViewa.setAdapter(adaptera);
*/
               /* setBankDetWelness();
                setBankDetFlex();*/


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



            }
        });



        int numberOfColumns = 1;


        int numberOfColumnsa = 1;




















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






        if (isNetworkAvailable()) {


            insurance_cover.setBackgroundResource(R.drawable.nav_back_tab);
            group_cover.setBackgroundResource(R.drawable.tab_curve);
            voluntary_cover.setBackgroundResource(R.drawable.tab_curve);
            voluntary_cover.setTextColor(Color.BLACK);
            group_cover.setTextColor(Color.BLACK);
            insurance_cover.setTextColor(Color.WHITE);

            my_insurance_cover_form.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            insurance_recycle.setVisibility(View.VISIBLE);

            obab = new ArrayList<>();
            adapterab = new myInsuranceCoverAdapterFlex(getActivity(), obab);
            insurance_recycle.setAdapter(adapterab);





            setInsurance();


//            my_insurance_cover_form.setVisibility(View.GONE);
//            recyclerView.setVisibility(View.GONE);
//            insurance_recycle.setVisibility(View.VISIBLE);


//            ob = new ArrayList<>();
//            adapter = new myFlexAdapter(getActivity(), ob);
//            recyclerView.setAdapter(adapter);
//
//
//
//
//
//            setBankDet();



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












        insurance_recycle.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumnsa));

        insurance_recycle.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), insurance_recycle, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager manageras = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        insurance_recycle.setLayoutManager(manageras);




        return v;

    }



    private void setBankDet() {

        String url = con.base_url+"/api/employee/get/flex-benefit";
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());

        //RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
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


     void setInsurance() {
         obab = new ArrayList<>();
         adapterab = new myInsuranceCoverAdapterFlex(getActivity(), obab);
         insurance_recycle.setAdapter(adapterab);

        String url = con.base_url+"/api/employee/get/flex-benefit/policies";
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js = new JSONObject(response);

                    Log.d("response", response);


                    JSONArray jsonObj = js.getJSONArray("data");

                    if (jsonObj.length() == 0) {
                        info_text.setVisibility(View.VISIBLE);
                        insurance_recycle.setVisibility(View.GONE);

                    } else {
                        info_text.setVisibility(View.GONE);
                        insurance_recycle.setVisibility(View.VISIBLE);


                        for (int j = 0; j < jsonObj.length(); j++) {

                            JSONObject jo_area = (JSONObject) jsonObj.get(j);

                            String  id= jo_area.getString("id");
                            String  policy_number= jo_area.getString("policy_number");
                            String  policy_name= jo_area.getString("policy_name");
                            String  description= jo_area.getString("description");
                            String  broker_id= jo_area.getString("broker_id");
                            String  broker= jo_area.getString("broker");
                            String  insurer_id= jo_area.getString("insurer_id");
                            String  insurer= jo_area.getString("insurer");
                            String  tpa_id= jo_area.getString("tpa_id");
                            String  tpa= jo_area.getString("tpa");
                            String  employer_id= jo_area.getString("employer_id");
                            String  employer= jo_area.getString("employer");
                            String  policy_type_id= jo_area.getString("policy_type_id");
                            String  policy_type= jo_area.getString("policy_type");
                            String  policy_sub_type_id= jo_area.getString("policy_sub_type_id");

                            String  policy_sub_type= jo_area.getString("policy_sub_type");
                            String  no_of_member= jo_area.getString("no_of_member");
                            String  policy_status= jo_area.getString("policy_status");
                            String  start_date= jo_area.getString("start_date");
                            String  end_date= jo_area.getString("end_date");
                            String  enrollement_status= jo_area.getString("enrollement_status");
                            String  enrollment_window= jo_area.getString("enrollment_window");
                            String  enrollement_type= jo_area.getString("enrollement_type");
                            String  enrollement_days= jo_area.getString("enrollement_days");
                            String  policy_rater_type_name= jo_area.getString("policy_rater_type_name");

                            ArrayList<String>suminsuredlist=new ArrayList<>();
                            ArrayList<String>premiumlist=new ArrayList<>();

                            try{
                                JSONArray enhance_suminsureds = jo_area.getJSONArray("enhance_suminsureds");
                                JSONArray enhance_premiums = jo_area.getJSONArray("enhance_premiums");



                                for (int k = 0; k < enhance_suminsureds.length(); k++) {

                                    JSONObject enhance_suminsuredsk = (JSONObject) enhance_suminsureds.get(k);


                                    suminsuredlist.add(enhance_suminsuredsk.getString("sum_insured"));
                                }

                                for (int k = 0; k < enhance_premiums.length(); k++) {

                                    JSONObject enhance_suminsuredsk = (JSONObject) enhance_premiums.get(k);


                                    premiumlist.add(enhance_suminsuredsk.getString("total_premium"));
                                }

                            }catch (JSONException e){

                            }



                            obab.add(new MyInsuranceFlex( id,  policy_number,  policy_name,  description,
                                     broker_id,  broker,  insurer_id,  insurer,
                                     tpa_id,  tpa,  employer_id,  employer,
                                     policy_type_id,  policy_type,  policy_sub_type_id,
                                     policy_sub_type,  no_of_member,  policy_status,
                                     start_date,  end_date,  enrollement_status,
                                     enrollment_window,  enrollement_type,  enrollement_days,
                                     policy_rater_type_name,suminsuredlist,premiumlist,suminsuredlist));



                        }
                    }


                    adapterab.notifyDataSetChanged();

                }catch (Exception e){

                }

            }
        },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("InseonErrorResponse", error.toString());
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

        //RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
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






    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void getAllVol() {







        String url = con.base_url+"/api/employee/get/dashboard";
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
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
        mRequestQueue.add(mStringRequest);

    }










    private void setBankDetTerm() {


        policyob_term= new ArrayList<>();
        policy_no= new ArrayList<>();
        policySummaryAdapter_term = new myvoluntaryAdapterGroupMyTermSumm(getActivity(), policyob_term);
        policy_cycle_term.setAdapter(policySummaryAdapter_term);


        String url = con.base_url+"/api/employee/get/all-policy-member-app";
        RequestQueue rq = Volley.newRequestQueue(getActivity());

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

                            } else {
                                policy_cycle_term.setVisibility(View.VISIBLE);


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
                                                "", policy_id, suminsurued_type_id));

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
















}

