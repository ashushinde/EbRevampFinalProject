package com.palm.newbenefit.Fragment;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.palm.newbenefit.Activity.DemoActivty;
import com.palm.newbenefit.Activity.MainActivity;
import com.palm.newbenefit.Adapter.MyOverAllAdapter;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.ApiConfig.RecyclerTouchListener;
import com.palm.newbenefit.Module.OverAllClaim;
import com.kmd.newbenefit.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OverAllClaimFragment extends Fragment {
    int gg;
    Constants con = null;
    Context context;
    String mobileNumber = null;
    String token = null;
    String user_id = null;



    List<OverAllClaim> ob_e = null;
    MyOverAllAdapter adapter_e = null;

    ProgressDialog progressDialog = null;
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


    public OverAllClaimFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_over_all_claim, container, false);
        con = new Constants();


        SharedPreferences prefs = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);
        ((MainActivity) getActivity()).setTitle("");
        token = prefs.getString("api_token", null);
        user_id = prefs.getString("user_id", null);

        ghi_recycle_plan= v.findViewById(R.id.ghi_recycle_plan);

        info_text_plan= v.findViewById(R.id.info_text_plan);


        //setProfileDet();
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














        int numberOfColumnsv = 1;

        ghi_recycle_plan.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumnsv));

        ghi_recycle_plan.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), ghi_recycle_plan, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager managervs = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        ghi_recycle_plan.setLayoutManager(managervs);






        return v;

    }



    private void setBankDetCahsless(String myid) {

        ob_e = new ArrayList<>();
        adapter_e = new MyOverAllAdapter(getActivity(), ob_e);
        ghi_recycle_plan.setAdapter(adapter_e);
        RequestQueue rq = Volley.newRequestQueue(getActivity());
        rq.getCache().clear();
        String url = con.base_url+ "/api/admin/all-cliam-details";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                               JSONObject resp= new JSONObject(response);

                            String statusa= String.valueOf(resp.getString("status"));



                            if (statusa.equalsIgnoreCase("false")) {
                                info_text_plan.setVisibility(View.VISIBLE);
                                ghi_recycle_plan.setVisibility(View.GONE);
                                progressDialog.dismiss();
                            } else {
                                info_text_plan.setVisibility(View.GONE);
                                ghi_recycle_plan.setVisibility(View.VISIBLE);
                                JSONArray jsonObj =resp.getJSONArray("data");

                                if(jsonObj.length()==0){
                                    info_text_plan.setVisibility(View.VISIBLE);
                                    ghi_recycle_plan.setVisibility(View.GONE);
                                }else {
                                    info_text_plan.setVisibility(View.GONE);
                                    ghi_recycle_plan.setVisibility(View.VISIBLE);


                                  for (int j = 0; j < jsonObj.length(); j++) {
                                    JSONObject jo_area = (JSONObject) jsonObj.get(j);


                                    String claim_id = (jo_area.getString("claim_id"));
                                    String employer_name = jo_area.getString("employer_name");
                                    String employee_name = jo_area.getString("employee_name");
                                    String member_relation = (jo_area.getString("member_relation"));
                                    String claim_amount = (jo_area.getString("claim_amount"));
                                    String settled_amount = (jo_area.getString("settled_amount"));
                                    String claim_type = (jo_area.getString("claim_type_ipd_opd"));
                                    String member_name = (jo_area.getString("member_name"));

                                    String tpa = jo_area.getString("tpa");
                                    String claim_registration_date = (jo_area.getString("claim_registration_date"));
                                    String claim_status = (jo_area.getString("claim_status"));
                                    String claim_tat = (jo_area.getString("claim_tat"));
                                     String color_code = (jo_area.getString("color_code"));
                                    String deduction_amount = (jo_area.getString("deduction_amount"));

                                    String type = (jo_area.getString("type"));
                                    String employee_email = (jo_area.getString("employee_mail"));

                                      String policy_number = (jo_area.getString("policy_number"));
                                      String claim_types = (jo_area.getString("claim_type"));
                                      String employee_code = (jo_area.getString("employee_code"));
                                      String substatus = (jo_area.getString("claim_sub_status"));

                                    ob_e.add(new OverAllClaim(claim_id,employer_name,employee_name,
                                            member_relation,member_name,claim_amount,settled_amount,claim_type ,
                                            tpa,claim_registration_date,claim_status,claim_tat,
                                            color_code,deduction_amount,type,employee_email,policy_number,employee_code,
                                            claim_types,substatus));

                                }


                                adapter_e.notifyDataSetChanged();
                                }

                                progressDialog.dismiss();

                            }


                        } catch (Exception e) {
                            progressDialog.dismiss();
                            Log.e("onErrorResponse", e.toString());
                        }

                    }
                },  new Response.ErrorListener() {
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




        HashMap<String, String> params = new HashMap<>();


        params.put("employee_id", myid);


        smr.setParams(params);
        rq.add(smr);

    }

    void GetEmployeeId(){
        progressDialog = ProgressDialog.show(getActivity(), "",
                "Saving. Please wait...", true);

        String url = con.base_url+"/api/admin/user";

        RequestQueue rq = Volley.newRequestQueue(getActivity());

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





                        String employer_id = explrObject.getString("employee_id");

                        employer_id_data = explrObject.getString("employer_id");



                        ob_e = new ArrayList<>();
                        adapter_e = new MyOverAllAdapter(getActivity(), ob_e);
                        ghi_recycle_plan.setAdapter(adapter_e);

                        setBankDetCahsless(employer_id);


                        if (employer_id != "null" && !employer_id.isEmpty()) {

                            employee_id=employer_id;

                           // getBankName();




                        }





                    }


  progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }


            }
        }, new Response.ErrorListener() {
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
        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", user_id);
        params.put("master_user_type_id", "5");


        mStringRequest.setParams(params);
        rq.add(mStringRequest);





    }





    public void setProfileDet() {
        progressDialog = ProgressDialog.show(getActivity(), "",
                "Saving. Please wait...", true);

    String url = con.base_url+"/api/admin/user";
       RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
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



                        ob_e = new ArrayList<>();
                        adapter_e = new MyOverAllAdapter(getActivity(), ob_e);
                        ghi_recycle_plan.setAdapter(adapter_e);

                        setBankDetCahsless(employer_id);

                    }

                } catch (Exception e) {
                    progressDialog.dismiss();

                }
            }
        }, new Response.ErrorListener() {
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



}

