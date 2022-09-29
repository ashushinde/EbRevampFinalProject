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
import android.widget.LinearLayout;
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
import com.palm.newbenefit.Adapter.myvoluntaryAdapterBenefitSumm;
import com.palm.newbenefit.Adapter.myvoluntaryAdapterGroupMyTermSumm;
import com.palm.newbenefit.Adapter.myvoluntaryAdapterGroupSumm;
import com.palm.newbenefit.Adapter.myvoluntaryAdapterMediclaimSumm;
import com.palm.newbenefit.Adapter.myvoluntaryAdapterPersonalSumm;
import com.palm.newbenefit.Adapter.myvoluntaryAdapterTermSumm;
import com.palm.newbenefit.Adapter.myvoluntaryAdapterVolMedSumm;
import com.palm.newbenefit.Adapter.myvoluntaryAdapterVolTermSumm;
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
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class CoverDataFragmentSumm extends Fragment {

    Constants con = null;

    String token = null;
    String user_id = null;




    int amountrs = 500;

    int topay_count;




LinearLayout summary;


    List<VoluntaryBenefit> policyob = null;
    myvoluntaryAdapterBenefitSumm policySummaryAdapter = null;



    List<VoluntaryBenefit> policyobextra = null;
    myvoluntaryAdapterBenefitSumm policySummaryAdapterextra= null;




    List<VoluntaryBenefit> policyobvolterm = null;
    myvoluntaryAdapterVolTermSumm policySummaryAdaptervolterm = null;



    List<VoluntaryBenefit> policyobvolmed = null;
    myvoluntaryAdapterVolMedSumm policySummaryAdaptervolmed = null;



    List<VoluntaryBenefit> policyob_term = null;
    myvoluntaryAdapterGroupMyTermSumm policySummaryAdapter_term = null;


    List<VoluntaryBenefit> policyob_Group = null;
    myvoluntaryAdapterGroupSumm policySummaryAdapter_Group = null;

    List<VoluntaryBenefit> policyob_Personal = null;
    myvoluntaryAdapterPersonalSumm policySummaryAdapter_Personal = null;


    List<VoluntaryBenefit> policyob_Medi = null;
    myvoluntaryAdapterMediclaimSumm policySummaryAdapter_Medi = null;


    List<VoluntaryBenefit> policyob_top = null;
    myvoluntaryAdapterTermSumm policySummaryAdapter_top = null;

    public CoverDataFragmentSumm() {
        // Required empty public constructor
    }

LinearLayout hide_data;


    double premoium_amount;
    String installement;

    TextView premium;
    TextView month;

    RecyclerView policy_cycle,policy_cycle_term,policy_cycle_Medi,policy_cycle_Group,policy_cycle_Personal
            ,policy_cycle_top,volterm_cycle,volmed_cycle,volmed_cycleextra;


    TextView month_hide;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_cover_data_summ, container, false);




      con = new Constants();

       SharedPreferences prefs = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);
        token = prefs.getString("api_token", null);
        user_id = prefs.getString("user_id", null);


        summary= v.findViewById(R.id.summary);


        policy_cycle= v.findViewById(R.id.policy_cycle);
        policy_cycle_term= v.findViewById(R.id.policy_cycle_term);
        policy_cycle_Medi= v.findViewById(R.id.policy_cycle_Medi);
        hide_data= v.findViewById(R.id.hide_data);
        policy_cycle_Group= v.findViewById(R.id.policy_cycle_Group);
        policy_cycle_Personal= v.findViewById(R.id.policy_cycle_Personal);
        policy_cycle_top= v.findViewById(R.id.policy_cycle_top);
        volterm_cycle= v.findViewById(R.id.volterm_cycle);
        volmed_cycle= v.findViewById(R.id.volmed_cycle);
        premium= v.findViewById(R.id.premium);
        month= v.findViewById(R.id.month);
        month_hide= v.findViewById(R.id.month_hide);
        volmed_cycleextra= v.findViewById(R.id.volmed_cycleextra);



        if (isNetworkAvailable()) {





            getAllVol();



            //Data();
        }else {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error?")
                    .setMessage("Please Check Your Internet Connection")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
        }







        int numberOfColumns = 1;
















        volterm_cycle.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));

        volterm_cycle.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), volterm_cycle, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager managerfdf = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        volterm_cycle.setLayoutManager(managerfdf);




        volmed_cycle.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));

        volmed_cycle.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), volmed_cycle, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager managerdf = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        volmed_cycle.setLayoutManager(managerdf);





        policy_cycle.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));

        policy_cycle.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), policy_cycle, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager managefcrd = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        policy_cycle.setLayoutManager(managefcrd);




        policy_cycle_top.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));

        policy_cycle_top.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), policy_cycle_top, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager policy_cycle_topmanager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        policy_cycle_top.setLayoutManager(policy_cycle_topmanager);






        policy_cycle_Medi.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));

        policy_cycle_Medi.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), policy_cycle_Medi, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager managerdd = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        policy_cycle_Medi.setLayoutManager(managerdd);



        policy_cycle_Group.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
        policy_cycle_Group.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), policy_cycle_Group, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager managefrdd = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        policy_cycle_Group.setLayoutManager(managefrdd);



        policy_cycle_Personal.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
        policy_cycle_Personal.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), policy_cycle_Personal, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager fhg = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        policy_cycle_Personal.setLayoutManager(fhg);












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





        volmed_cycleextra.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));

        volmed_cycleextra.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), volmed_cycleextra, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager policy_cycle_termtermf = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        volmed_cycleextra.setLayoutManager(policy_cycle_termtermf);




        return v;

    }



    private void getAllVol() {




        policyob_term= new ArrayList<>();
        policySummaryAdapter_term = new myvoluntaryAdapterGroupMyTermSumm(getActivity(), policyob_term);
        policy_cycle_term.setAdapter(policySummaryAdapter_term);

        policyob_Medi= new ArrayList<>();
        policySummaryAdapter_Medi = new myvoluntaryAdapterMediclaimSumm(getActivity(), policyob_Medi);
        policy_cycle_Medi.setAdapter(policySummaryAdapter_Medi);


        policyob_Group= new ArrayList<>();
        policySummaryAdapter_Group = new myvoluntaryAdapterGroupSumm(getActivity(), policyob_Group);
        policy_cycle_Group.setAdapter(policySummaryAdapter_Group);


        policyob_Personal= new ArrayList<>();
        policySummaryAdapter_Personal = new myvoluntaryAdapterPersonalSumm(getActivity(), policyob_Personal);
        policy_cycle_Personal.setAdapter(policySummaryAdapter_Personal);



        policyobvolterm= new ArrayList<>();
        policySummaryAdaptervolterm = new myvoluntaryAdapterVolTermSumm(getActivity(), policyobvolterm);
        volterm_cycle.setAdapter(policySummaryAdaptervolterm);



        policyobvolmed= new ArrayList<>();
        policySummaryAdaptervolmed = new myvoluntaryAdapterVolMedSumm(getActivity(), policyobvolmed);
        volmed_cycle.setAdapter(policySummaryAdaptervolmed);


        policyob_top= new ArrayList<>();
        policySummaryAdapter_top= new myvoluntaryAdapterTermSumm(getActivity(), policyob_top);
        policy_cycle_top.setAdapter(policySummaryAdapter_top);



        policyob= new ArrayList<>();
        policySummaryAdapter = new myvoluntaryAdapterBenefitSumm(getActivity(), policyob);
        policy_cycle.setAdapter(policySummaryAdapter);



        policyobextra= new ArrayList<>();
        policySummaryAdapterextra = new myvoluntaryAdapterBenefitSumm(getActivity(), policyobextra);
        volmed_cycleextra.setAdapter(policySummaryAdapterextra);



        String url = con.base_url+"/api/employee/get/dashboard";
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
                        JSONArray  jsonObj=js.getJSONArray("data");

                        if (jsonObj.length() == 0){
                            summary.setVisibility(View.GONE);
                        }else {
                            summary.setVisibility(View.VISIBLE);



                            for (int j = 0; j < jsonObj.length(); j++) {

                                JSONObject jo_area = (JSONObject) jsonObj.get(j);
                                String po_id = String.valueOf(jo_area.getString("policy_id"));
                                String policy_sub_type_name = String.valueOf(jo_area.getString("policy_sub_type_name"));
                                int po_idd = jo_area.getInt("premium");


                                if(policy_sub_type_name.equalsIgnoreCase("Group Term Life")){
                                    Log.d("policyid",po_id);
                                    setBankDetTerm(po_id);
                                }

                                if(policy_sub_type_name.equalsIgnoreCase("Personal Accident Top Up")){
                                    Log.d("policyid",po_id);
                                    setBankDetPersonal(po_id);
                                }


                                if(policy_sub_type_name.equalsIgnoreCase("Term Life Top Up")){
                                    Log.d("policyid",po_id);
                                    setBankDetTermTop(po_id);
                                }

                                if(policy_sub_type_name.equalsIgnoreCase("Group Personal Accident")){
                                    Log.d("policyid",po_id);
                                    setBankDetGroup(po_id);
                                }

                                if(policy_sub_type_name.equalsIgnoreCase("Mediclaim Top Up")){
                                    Log.d("Medpolicyid",po_id);
                                    setBankDetMediclaim(po_id);
                                }

                                if(policy_sub_type_name.equalsIgnoreCase("Group Mediclaim")){
                                    Log.d("groupmediclam",po_id);

                                    if(po_id.equalsIgnoreCase("143")){
                                        setBankDetVoluntarys(po_id);
                                    }else {
                                        setBankDetVoluntarysExtra(po_id);
                                    }

                                }

                                if(policy_sub_type_name.equalsIgnoreCase("Voluntary Term Life Top Up")){
                                    Log.d("policyid",po_id);
                                    setBankDetVolunterm(po_id);
                                }

                                if(policy_sub_type_name.equalsIgnoreCase("Voluntary Mediclaim Top Up")){
                                    Log.d("policyid",po_id);
                                    setBankDetVolunMed(po_id);
                                }











                            }





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


    private void setBankDetVoluntarys(String policy_id) {


        policyobextra= new ArrayList<>();
        policySummaryAdapterextra = new myvoluntaryAdapterBenefitSumm(getActivity(), policyobextra);
        volmed_cycleextra.setAdapter(policySummaryAdapterextra);


        String url = con.base_url+"/api/employee/get/all-policy-member?policy_id="+policy_id;
        RequestQueue rq = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int allsum = 0;
                Double allPremium=0.0;
                try {

                    JSONObject data= new JSONObject(response);

                    Log.e("mediclaim", data.toString());

                    String statusa= String.valueOf(data.getBoolean("status"));



                    if (statusa.equalsIgnoreCase("false")) {
                        policy_cycle.setVisibility(View.GONE);

                    } else {
                        policy_cycle.setVisibility(View.VISIBLE);


                        JSONObject jsonObja =data.getJSONObject("data");


                        JSONArray jsonObj =jsonObja.getJSONArray("Group Mediclaim");





                        JSONObject jo_area = (JSONObject) jsonObj.get(0);


                        String policy_name = (jo_area.getString("policy_name"));
                        double employee_premium=0.0;
                        for (int k = 0;k < jsonObj.length(); k++) {

                            JSONObject jo_areag = (JSONObject) jsonObj.get(k);
                            int suminsured = jo_areag.getInt("suminsured");

                            try{
                                employee_premium= jo_areag.getDouble("employee_premium");

                            }catch (Exception e){
                                employee_premium= 0.0;

                            }

                            allsum=suminsured+allsum;
                            allPremium=employee_premium+allPremium;



                        }




                        String allsumdata=String.valueOf(allsum);
                        String allPremiumdata= String.valueOf(allPremium);



                        JSONObject jo_areaf = (JSONObject) jsonObj.get(0);

                        String name = (jo_areaf.getString("policy_name"));
                        String first_name = (jo_areaf.getString("first_name"));
                        String last_name = (jo_areaf.getString("last_name"));
                        String suminsured = String.valueOf((jo_areaf.getString("suminsured")));
                        String dob = (jo_areaf.getString("dob"));
                        String gender = (jo_areaf.getString("gender"));
                        String member_email = jo_areaf.getString("member_email");
                        String member_mob_no = jo_areaf.getString("member_mob_no");
                        String suminsurued_type_id = jo_areaf.getString("suminsurued_type_id");
                        String installment = jo_areaf.getString("installment");

if(installment.equalsIgnoreCase("null")){
    month_hide.setVisibility(View.GONE);
    month.setVisibility(View.GONE);
}else {
    month_hide.setVisibility(View.VISIBLE);
    month.setVisibility(View.VISIBLE);
}
                        month.setText(installment);
                        policyob.add(new VoluntaryBenefit(name,first_name,last_name,allsumdata,
                                dob,gender,member_email,allPremiumdata,policy_id,suminsurued_type_id));





                        policySummaryAdapter.notifyDataSetChanged();

                        Log.e("policyob", policyob.toString());
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



    private void setBankDetVoluntarysExtra(String policy_id) {


        policyob= new ArrayList<>();
        policySummaryAdapter = new myvoluntaryAdapterBenefitSumm(getActivity(), policyob);
        policy_cycle.setAdapter(policySummaryAdapter);


        String url = con.base_url+"/api/employee/get/all-policy-member?policy_id="+policy_id;
        RequestQueue rq = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int allsum = 0;
                Double allPremium=0.0;
                try {

                    JSONObject data= new JSONObject(response);

                    Log.e("mediclaim", data.toString());

                    String statusa= String.valueOf(data.getBoolean("status"));



                    if (statusa.equalsIgnoreCase("false")) {
                        policy_cycle.setVisibility(View.GONE);

                    } else {
                        policy_cycle.setVisibility(View.VISIBLE);


                        JSONObject jsonObja =data.getJSONObject("data");


                        JSONArray jsonObj =jsonObja.getJSONArray("Group Mediclaim");





                        JSONObject jo_area = (JSONObject) jsonObj.get(0);


                        String policy_name = (jo_area.getString("policy_name"));
                        double employee_premium=0.0;
                        for (int k = 0;k < jsonObj.length(); k++) {

                            JSONObject jo_areag = (JSONObject) jsonObj.get(k);
                            int suminsured = jo_areag.getInt("suminsured");

                            try{
                                employee_premium= jo_areag.getDouble("employee_premium");

                            }catch (Exception e){
                                employee_premium= 0.0;

                            }

                            allsum=suminsured+allsum;
                            allPremium=employee_premium+allPremium;



                        }




                        String allsumdata=String.valueOf(allsum);
                        String allPremiumdata= String.valueOf(allPremium);



                        JSONObject jo_areaf = (JSONObject) jsonObj.get(0);

                        String name = (jo_areaf.getString("policy_name"));
                        String first_name = (jo_areaf.getString("first_name"));
                        String last_name = (jo_areaf.getString("last_name"));
                        String suminsured = String.valueOf((jo_areaf.getString("suminsured")));
                        String dob = (jo_areaf.getString("dob"));
                        String gender = (jo_areaf.getString("gender"));
                        String member_email = jo_areaf.getString("member_email");
                        String member_mob_no = jo_areaf.getString("member_mob_no");
                        String suminsurued_type_id = jo_areaf.getString("suminsurued_type_id");
                        String installment = jo_areaf.getString("installment");

                        if(installment.equalsIgnoreCase("null")){
                            month_hide.setVisibility(View.GONE);
                            month.setVisibility(View.GONE);
                        }else {
                            month_hide.setVisibility(View.VISIBLE);
                            month.setVisibility(View.VISIBLE);
                        }
                        month.setText(installment);
                        policyobextra.add(new VoluntaryBenefit(name,first_name,last_name,allsumdata,
                                dob,gender,member_email,allPremiumdata,policy_id,suminsurued_type_id));





                        policySummaryAdapterextra.notifyDataSetChanged();

                        Log.e("policyob", policyob.toString());
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


    private void setBankDetVolunterm(String policy_id) {





        policyobvolterm= new ArrayList<>();
        policySummaryAdaptervolterm = new myvoluntaryAdapterVolTermSumm(getActivity(), policyobvolterm);
        volterm_cycle.setAdapter(policySummaryAdaptervolterm);


        String url = con.base_url+"/api/employee/get/all-policy-member?policy_id="+policy_id;
        RequestQueue rq = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int allsum = 0;
                Double allPremium=0.0;
                try {

                    JSONObject data= new JSONObject(response);

                    Log.e("groupmedi_response", data.toString());

                    String statusa= String.valueOf(data.getBoolean("status"));



                    if (statusa.equalsIgnoreCase("false")) {
                        volterm_cycle.setVisibility(View.GONE);

                    } else {
                        volterm_cycle.setVisibility(View.VISIBLE);


                        JSONObject jsonObja =data.getJSONObject("data");


                        JSONArray jsonObj =jsonObja.getJSONArray("Voluntary Term Life Top Up");





                        JSONObject jo_area = (JSONObject) jsonObj.get(0);


                        String policy_name = (jo_area.getString("policy_name"));
                        double employee_premium=0.0;
                        for (int k = 0;k < jsonObj.length(); k++) {

                            JSONObject jo_areag = (JSONObject) jsonObj.get(k);
                            int suminsured = jo_areag.getInt("suminsured");

                            try{
                                employee_premium= jo_areag.getDouble("employee_premium");

                            }catch (Exception e){
                                employee_premium= 0.0;

                            }

                            allsum=suminsured+allsum;
                            allPremium=employee_premium+allPremium;



                        }




                        String allsumdata=String.valueOf(allsum);
                        String allPremiumdata= String.valueOf(allPremium);



                        JSONObject jo_areaf = (JSONObject) jsonObj.get(0);

                        String name = (jo_areaf.getString("policy_name"));
                        String first_name = (jo_areaf.getString("first_name"));
                        String last_name = (jo_areaf.getString("last_name"));
                        String suminsured = String.valueOf((jo_areaf.getString("suminsured")));
                        String dob = (jo_areaf.getString("dob"));
                        String gender = (jo_areaf.getString("gender"));
                        String member_email = jo_areaf.getString("member_email");
                        String member_mob_no = jo_areaf.getString("member_mob_no");
                        String suminsurued_type_id = jo_areaf.getString("suminsurued_type_id");
                        String installment = jo_areaf.getString("installment");

                        if(installment.equalsIgnoreCase("null")){
                            month_hide.setVisibility(View.GONE);
                            month.setVisibility(View.GONE);
                        }else {
                            month_hide.setVisibility(View.VISIBLE);
                            month.setVisibility(View.VISIBLE);
                        }
                        month.setText(installment);
                        policyobvolterm.add(new VoluntaryBenefit(policy_name,first_name,last_name,allsumdata,
                                dob,gender,member_email,allPremiumdata,policy_id,suminsurued_type_id));






                        policySummaryAdaptervolterm.notifyDataSetChanged();

                        Log.e("policyob", policyob.toString());
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


    private void setBankDetVolunMed(String policy_id) {







        policyobvolmed= new ArrayList<>();
        policySummaryAdaptervolmed = new myvoluntaryAdapterVolMedSumm(getActivity(), policyobvolmed);
        volmed_cycle.setAdapter(policySummaryAdaptervolmed);


        String url = con.base_url+"/api/employee/get/all-policy-member?policy_id="+policy_id;
        RequestQueue rq = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int allsum = 0;
                Double allPremium=0.0;
                try {

                    JSONObject data= new JSONObject(response);

                    Log.e("groupmedi_response", data.toString());

                    String statusa= String.valueOf(data.getBoolean("status"));



                    if (statusa.equalsIgnoreCase("false")) {
                        volmed_cycle.setVisibility(View.GONE);

                    } else {
                        volmed_cycle.setVisibility(View.VISIBLE);


                        JSONObject jsonObja =data.getJSONObject("data");


                        JSONArray jsonObj =jsonObja.getJSONArray("Voluntary Mediclaim Top Up");





                        JSONObject jo_area = (JSONObject) jsonObj.get(0);


                        String policy_name = (jo_area.getString("policy_name"));
                        double employee_premium=0.0;
                        for (int k = 0;k < jsonObj.length(); k++) {

                            JSONObject jo_areag = (JSONObject) jsonObj.get(k);
                            int suminsured = jo_areag.getInt("suminsured");
                            int opd_suminsured = jo_areag.getInt("opd_suminsured");

                            try{
                                employee_premium= jo_areag.getDouble("employee_premium");

                            }catch (Exception e){
                                employee_premium= 0.0;

                            }

                            allsum=suminsured+allsum;

                            allsum=allsum+opd_suminsured;
                            allPremium=employee_premium+allPremium;



                        }




                        String allsumdata=String.valueOf(allsum);
                        String allPremiumdata= String.valueOf(allPremium);



                        JSONObject jo_areaf = (JSONObject) jsonObj.get(0);

                        String name = (jo_areaf.getString("policy_name"));
                        String first_name = (jo_areaf.getString("first_name"));
                        String last_name = (jo_areaf.getString("last_name"));
                        String suminsured = String.valueOf((jo_areaf.getString("suminsured")));
                        String dob = (jo_areaf.getString("dob"));
                        String gender = (jo_areaf.getString("gender"));
                        String member_email = jo_areaf.getString("member_email");
                        String member_mob_no = jo_areaf.getString("member_mob_no");
                        String suminsurued_type_id = jo_areaf.getString("suminsurued_type_id");
                        String installment = jo_areaf.getString("installment");

                        if(installment.equalsIgnoreCase("null")){
                            month_hide.setVisibility(View.GONE);
                            month.setVisibility(View.GONE);
                        }else {
                            month_hide.setVisibility(View.VISIBLE);
                            month.setVisibility(View.VISIBLE);
                        }

                        month.setText(installment);
                        policyobvolmed.add(new VoluntaryBenefit(policy_name,first_name,last_name,allsumdata,
                                dob,gender,member_email,allPremiumdata,policy_id,suminsurued_type_id));





                        policySummaryAdaptervolmed.notifyDataSetChanged();

                        Log.e("policyob", policyob.toString());
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


     private void setBankDetTerm(String policy_id) {


        policyob_term= new ArrayList<>();
        policySummaryAdapter_term = new myvoluntaryAdapterGroupMyTermSumm(getActivity(), policyob_term);
        policy_cycle_term.setAdapter(policySummaryAdapter_term);


        String url = con.base_url+"/api/employee/get/all-policy-member?policy_id="+policy_id;
         RequestQueue rq = Volley.newRequestQueue(getActivity(),
                 new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int allsum = 0;
                Double allPremium=0.0;
                try {

                    JSONObject data= new JSONObject(response);

                    Log.e("group_response", data.toString());

                    String statusa= String.valueOf(data.getBoolean("status"));



                    if (statusa.equalsIgnoreCase("false")) {
                        policy_cycle_term.setVisibility(View.GONE);

                    } else {
                        policy_cycle_term.setVisibility(View.VISIBLE);


                        JSONObject jsonObja =data.getJSONObject("data");


                        JSONArray jsonObj =jsonObja.getJSONArray("Group Term Life");





                        JSONObject jo_area = (JSONObject) jsonObj.get(0);


                        String policy_name = (jo_area.getString("policy_name"));



                        for (int k = 0;k < jsonObj.length(); k++) {

                            JSONObject jo_areag = (JSONObject) jsonObj.get(k);
                            int suminsured = jo_areag.getInt("suminsured");
                            double employee_premium= jo_areag.getDouble("employee_premium");

                            allsum=suminsured+allsum;
                            allPremium=employee_premium+allPremium;

                        }


                        String allsumdata=String.valueOf(allsum);
                        String allPremiumdata= String.valueOf(allPremium);

                        JSONObject jo_areaf = (JSONObject) jsonObj.get(0);

                        String name = (jo_areaf.getString("policy_name"));
                        String first_name = (jo_areaf.getString("first_name"));
                        String last_name = (jo_areaf.getString("last_name"));
                        String suminsured = String.valueOf((jo_areaf.getString("suminsured")));
                        String dob = (jo_areaf.getString("dob"));
                        String gender = (jo_areaf.getString("gender"));
                        String member_email = jo_areaf.getString("member_email");
                        String member_mob_no = jo_areaf.getString("member_mob_no");
                        String suminsurued_type_id = jo_areaf.getString("suminsurued_type_id");

                        policyob_term.add(new VoluntaryBenefit(policy_name,first_name,last_name,allsumdata,
                                dob,gender,member_email,allPremiumdata,policy_id,suminsurued_type_id));
                        policy_cycle_term.setAdapter(policySummaryAdapter_term);
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


    private void setBankDetTermTop(String policy_id) {



        policyob_top= new ArrayList<>();
        policySummaryAdapter_top= new myvoluntaryAdapterTermSumm(getActivity(), policyob_top);
        policy_cycle_top.setAdapter(policySummaryAdapter_top);


        String url = con.base_url+"/api/employee/get/all-policy-member?policy_id="+policy_id;
        RequestQueue rq = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int allsum = 0;
                Double allPremium=0.0;
                try {

                    JSONObject data= new JSONObject(response);

                    Log.e("group_response", data.toString());

                    String statusa= String.valueOf(data.getBoolean("status"));



                    if (statusa.equalsIgnoreCase("false")) {
                        policy_cycle_top.setVisibility(View.GONE);

                    } else {
                        policy_cycle_top.setVisibility(View.VISIBLE);


                        JSONObject jsonObja =data.getJSONObject("data");


                        JSONArray jsonObj =jsonObja.getJSONArray("Term Life Top Up");





                        JSONObject jo_area = (JSONObject) jsonObj.get(0);


                        String policy_name = (jo_area.getString("policy_name"));

                        for (int k = 0;k < jsonObj.length(); k++) {

                            JSONObject jo_areag = (JSONObject) jsonObj.get(k);
                            int suminsured = jo_areag.getInt("suminsured");
                            double employee_premium= jo_areag.getDouble("employee_premium");

                            allsum=suminsured+allsum;
                            allPremium=employee_premium+allPremium;



                        }

                        String allsumdata=String.valueOf(allsum);
                        String allPremiumdata= String.valueOf(allPremium);



                        JSONObject jo_areaf = (JSONObject) jsonObj.get(0);

                        String name = (jo_areaf.getString("policy_name"));
                        String first_name = (jo_areaf.getString("first_name"));
                        String last_name = (jo_areaf.getString("last_name"));
                        String suminsured = String.valueOf((jo_areaf.getString("suminsured")));
                        String dob = (jo_areaf.getString("dob"));
                        String gender = (jo_areaf.getString("gender"));
                        String member_email = jo_areaf.getString("member_email");
                        String member_mob_no = jo_areaf.getString("member_mob_no");
                        String suminsurued_type_id = jo_areaf.getString("suminsurued_type_id");

                        policyob_top.add(new VoluntaryBenefit(policy_name,first_name,last_name,allsumdata,
                                dob,gender,member_email,allPremiumdata,policy_id,suminsurued_type_id));



                        policy_cycle_top.setAdapter(policySummaryAdapter_top);


                        policySummaryAdapter_top.notifyDataSetChanged();
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

    private void setBankDetGroup(String policy_id) {


        policyob_Group= new ArrayList<>();
        policySummaryAdapter_Group = new myvoluntaryAdapterGroupSumm(getActivity(), policyob_Group);
        policy_cycle_Group.setAdapter(policySummaryAdapter_Group);


        String url = con.base_url+"/api/employee/get/all-policy-member?policy_id="+policy_id;
        RequestQueue rq = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int allsum = 0;
                Double allPremium=0.0;
                try {

                    JSONObject data= new JSONObject(response);

                    Log.e("group_response", data.toString());

                    String statusa= String.valueOf(data.getBoolean("status"));



                    if (statusa.equalsIgnoreCase("false")) {
                        policy_cycle_Group.setVisibility(View.GONE);

                    } else {
                        policy_cycle_Group.setVisibility(View.VISIBLE);


                        JSONObject jsonObja =data.getJSONObject("data");


                        JSONArray jsonObj =jsonObja.getJSONArray("Group Personal Accident");





                        JSONObject jo_area = (JSONObject) jsonObj.get(0);


                        String policy_name = (jo_area.getString("policy_name"));

                        for (int k = 0;k < jsonObj.length(); k++) {

                            JSONObject jo_areag = (JSONObject) jsonObj.get(k);
                            int suminsured = jo_areag.getInt("suminsured");
                            double employee_premium= jo_areag.getDouble("employee_premium");

                            allsum=suminsured+allsum;
                            allPremium=employee_premium+allPremium;



                        }


                        String allsumdata=String.valueOf(allsum);
                        String allPremiumdata= String.valueOf(allPremium);



                        JSONObject jo_areaf = (JSONObject) jsonObj.get(0);

                        String name = (jo_areaf.getString("policy_name"));
                        String first_name = (jo_areaf.getString("first_name"));
                        String last_name = (jo_areaf.getString("last_name"));
                        String suminsured = String.valueOf((jo_areaf.getString("suminsured")));
                        String dob = (jo_areaf.getString("dob"));
                        String gender = (jo_areaf.getString("gender"));
                        String member_email = jo_areaf.getString("member_email");
                        String member_mob_no = jo_areaf.getString("member_mob_no");
                        String suminsurued_type_id = jo_areaf.getString("suminsurued_type_id");
                        policyob_Group.add(new VoluntaryBenefit(policy_name,first_name,last_name,allsumdata,
                                dob,gender,member_email,allPremiumdata,policy_id,suminsurued_type_id));





                        policySummaryAdapter_Group.notifyDataSetChanged();
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



    private void setBankDetPersonal(String policy_id) {


        policyob_Personal= new ArrayList<>();
        policySummaryAdapter_Personal= new myvoluntaryAdapterPersonalSumm(getActivity(), policyob_Personal);
        policy_cycle_Personal.setAdapter(policySummaryAdapter_Personal);


        String url = con.base_url+"/api/employee/get/all-policy-member?policy_id="+policy_id;
         RequestQueue rq = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int allsum = 0;
                Double allPremium=0.0;
                try {

                    JSONObject data= new JSONObject(response);

                    Log.e("group_response", data.toString());

                    String statusa= String.valueOf(data.getBoolean("status"));



                    if (statusa.equalsIgnoreCase("false")) {
                        policy_cycle_Personal.setVisibility(View.GONE);

                    } else {
                        policy_cycle_Personal.setVisibility(View.VISIBLE);


                        JSONObject jsonObja =data.getJSONObject("data");


                        JSONArray jsonObj =jsonObja.getJSONArray("Personal Accident Top Up");





                        JSONObject jo_area = (JSONObject) jsonObj.get(0);


                        String policy_name = (jo_area.getString("policy_name"));

                        for (int k = 0;k < jsonObj.length(); k++) {

                            JSONObject jo_areag = (JSONObject) jsonObj.get(k);
                            int suminsured = jo_areag.getInt("suminsured");
                            double employee_premium= jo_areag.getDouble("employee_premium");

                            allsum=suminsured+allsum;
                            allPremium=employee_premium+allPremium;



                        }

                        String allsumdata=String.valueOf(allsum);
                        String allPremiumdata= String.valueOf(allPremium);



                        JSONObject jo_areaf = (JSONObject) jsonObj.get(0);

                        String name = (jo_areaf.getString("policy_name"));
                        String first_name = (jo_areaf.getString("first_name"));
                        String last_name = (jo_areaf.getString("last_name"));
                        String suminsured = String.valueOf((jo_areaf.getString("suminsured")));
                        String dob = (jo_areaf.getString("dob"));
                        String gender = (jo_areaf.getString("gender"));
                        String member_email = jo_areaf.getString("member_email");
                        String member_mob_no = jo_areaf.getString("member_mob_no");
                        String suminsurued_type_id = jo_areaf.getString("suminsurued_type_id");

                        policyob_Personal.add(new VoluntaryBenefit(policy_name,first_name,last_name,allsumdata,
                                dob,gender,member_email,allPremiumdata,policy_id,suminsurued_type_id));





                        policySummaryAdapter_Personal.notifyDataSetChanged();
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



    private void setBankDetMediclaim(String policy_id) {


        policyob_Medi= new ArrayList<>();
        policySummaryAdapter_Medi = new myvoluntaryAdapterMediclaimSumm(getActivity(), policyob_Medi);
        policy_cycle_Medi.setAdapter(policySummaryAdapter_Medi);


        String url = con.base_url+"/api/employee/get/all-policy-member?policy_id="+policy_id;
        RequestQueue rq = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int allsum = 0;
                Double allPremium=0.0;
                try {

                    JSONObject data= new JSONObject(response);

                    Log.e("mediclaim_response", data.toString());

                    String statusa= String.valueOf(data.getBoolean("status"));



                    if (statusa.equalsIgnoreCase("false")) {
                        policy_cycle_Medi.setVisibility(View.GONE);

                    } else {
                        policy_cycle_Medi.setVisibility(View.VISIBLE);


                        JSONObject jsonObja =data.getJSONObject("data");


                        JSONArray jsonObj =jsonObja.getJSONArray("Mediclaim Top Up");





                        JSONObject jo_area = (JSONObject) jsonObj.get(0);


                        String policy_name = (jo_area.getString("policy_name"));

                        for (int k = 0;k < jsonObj.length(); k++) {

                            JSONObject jo_areag = (JSONObject) jsonObj.get(k);
                            int suminsured = jo_areag.getInt("suminsured");
                            double employee_premium= jo_areag.getDouble("employee_premium");

                            allsum=suminsured+allsum;
                            allPremium=employee_premium+allPremium;



                        }





                        String allsumdata=String.valueOf(allsum);
                        String allPremiumdata= String.valueOf(allPremium);



                        JSONObject jo_areaf = (JSONObject) jsonObj.get(0);

                        String name = (jo_areaf.getString("policy_name"));
                        String first_name = (jo_areaf.getString("first_name"));
                        String last_name = (jo_areaf.getString("last_name"));
                        String suminsured = String.valueOf((jo_areaf.getString("suminsured")));
                        String dob = (jo_areaf.getString("dob"));
                        String gender = (jo_areaf.getString("gender"));
                        String member_email = jo_areaf.getString("member_email");
                        String member_mob_no = jo_areaf.getString("member_mob_no");
                        String suminsurued_type_id = jo_areaf.getString("suminsurued_type_id");

                        policyob_Medi.add(new VoluntaryBenefit(policy_name,first_name,last_name,allsumdata,
                                dob,gender,member_email,allPremiumdata,policy_id,suminsurued_type_id));





                        policySummaryAdapter_Medi.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    Log.e("medimyerror", e.toString());
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

