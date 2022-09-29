package com.palm.newbenefit.Fragment;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.palm.newbenefit.Activity.TrackActivity;
import com.palm.newbenefit.Adapter.MyHosClaimAdapter;
import com.palm.newbenefit.Adapter.MyIntimateClaimAdapter;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.ApiConfig.DateTimeFormater;
import com.palm.newbenefit.ApiConfig.MyDateListener;
import com.palm.newbenefit.ApiConfig.RecyclerTouchListener;
import com.palm.newbenefit.ApiConfig.Utils;
import com.palm.newbenefit.Module.MyHosClaimModel;
import com.palm.newbenefit.Module.MyIntimateClaimModel;
import com.palm.newbenefit.Module.SpinnerModal;
import com.palm.newbenefit.Module.SpinnerModalFamilyData;
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
import java.util.Calendar;
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
public class MyTrackClaimFragment extends Fragment {
    int gg;
    Constants con = null;
    Context context;
    String mobileNumber = null;
    String token = null;
    String user_id = null;
    private SimpleDateFormat dateFormatter;
    Calendar newCalendar;
    int day_b;
    int month_b;
    int year_b;
    String _year;
    String _month, _day;
    int day = 0;
    int month = 0;
    int year = 0;
    String claimidd="yes";
    int day_a = 0;
    int month_a = 0;
    int year_a = 0;
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

    RecyclerView recyclerView, voluntary, ghi_recycle_plan;
    int amountrs = 500;
    String emp_id, employer_id, employer_id_my,employee_id = null, employer_id_data;

    String company_id;
    ImageView info_text, info_text_plan, info_text_voluntary;
    TextView submit, voluntary_cover;
    private RequestQueue requestQueue;
    TextView emp_name;
    String claim_reimb_id, claim_id;
    Spinner policy_type_spin, bank_citySpin,bank_branchSpin, policy_type_spin_no;
    LinearLayout intimation_lin;
    LinearLayout export;
    ProgressDialog progressDialog = null;
    public MyTrackClaimFragment() {
        // Required empty public constructor
    }

    ArrayList<String> bank_branch = null;
    ArrayList<SpinnerModal> bank_branchList = null;
    ArrayAdapter<SpinnerModal> bank_branchAdapter = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_claims_track, container, false);
        con = new Constants();


        SharedPreferences prefs = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);

        token = prefs.getString("api_token", null);
        user_id = prefs.getString("user_id", null);

        recyclerView = v.findViewById(R.id.ghi_recycle);
        ghi_recycle_plan = v.findViewById(R.id.ghi_recycle_plan);
        info_text = v.findViewById(R.id.info_text);
        info_text_plan = v.findViewById(R.id.info_text_plan);
        info_text_voluntary = v.findViewById(R.id.info_text_voluntary);
        voluntary = v.findViewById(R.id.voluntary);
        policy_type_spin = v.findViewById(R.id.policy_type_spin);
        bank_citySpin = v.findViewById(R.id.bank_citySpin);
        policy_type_spin_no = v.findViewById(R.id.policy_type_spin_no);
        intimation_lin = v.findViewById(R.id.intimation_lin);
        bank_branchSpin=v. findViewById(R.id.bank_branchSpin);

        submit=v.findViewById(R.id.submit);
        export = v.findViewById(R.id.export);
        setProfileDet();
        GetEmployeeId();


        export.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showDialog();

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    SpinnerModal policy = (SpinnerModal) policy_type_spin_no.getSelectedItem();
                    SpinnerModal member = (SpinnerModal) bank_citySpin.getSelectedItem();
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


                    if (member.getSelKey().equalsIgnoreCase(" ")) {
                        ++count;
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Error")
                                .setMessage("Please Select Member Name")
                                .setIcon(android.R.drawable.btn_dialog)
                                .setNegativeButton(android.R.string.ok, null).show();
                        return;
                    }


                    try{


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
                        intent.putExtra("policy",member.getBank_id());
                        intent.putExtra("member",member.getSelKey());
                        intent.putExtra("claim",claim.getSelKey());
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


            //getBankName();

            //Data();
        } else {
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

                    SpinnerModal policyname = (SpinnerModal) policy_type_spin_no.getSelectedItem();

                    if (bank_city_modal.getSelKey().equalsIgnoreCase("")) {

                        ob_e = new ArrayList<>();
                        adapter_e = new MyIntimateClaimAdapter(getActivity(), ob_e,policyname.getSelKey());
                        ghi_recycle_plan.setAdapter(adapter_e);

                        setBankDetCahsless();


                        ob = new ArrayList<>();
                        adapter = new MyIntimateClaimAdapter(getActivity(), ob,policyname.getSelKey());
                        recyclerView.setAdapter(adapter);

                        setBankDet();


                        oba = new ArrayList<>();
                        adaptera = new MyHosClaimAdapter(getActivity(), oba);
                        voluntary.setAdapter(adaptera);


                        setBankDetVoluntary();

                    } else {


                        getBankbranch(bank_city_modal.getBank_id());


                        ob = new ArrayList<>();
                        adapter = new MyIntimateClaimAdapter(getActivity(), ob,policyname.getSelKey());
                        recyclerView.setAdapter(adapter);

                        setBankDetM();

                        ob_e = new ArrayList<>();
                        adapter_e = new MyIntimateClaimAdapter(getActivity(), ob_e,policyname.getSelKey());
                        ghi_recycle_plan.setAdapter(adapter_e);


                        setBankDetM_cashless();


                        oba = new ArrayList<>();
                        adaptera = new MyHosClaimAdapter(getActivity(), oba);
                        voluntary.setAdapter(adaptera);


                        setBankDetVoluntaryM();


                    }


                } else {
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

    public void getBankbranch(final String bank_city) {
        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();

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
                                try{
                                    String message= String.valueOf(objectd.getString("message"));
                                    claimidd="no";
                                    new AlertDialog.Builder(getActivity())
                                            .setTitle("Error?")
                                            .setMessage(message)
                                            .setIcon(android.R.drawable.btn_dialog)
                                            .setNegativeButton(android.R.string.ok, null).show();
                                }catch (Exception e){

                                }

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
                                    bank_branchList.add(new SpinnerModal(jsonObj.getString("claim_no"),
                                            jsonObj.getString("claim_no")+"-"+
                                                    jsonObj.getString("admit_date")));
                                    bank_branch.add(jsonObj.getString("claim_no"));
                                }

                            }




                            bank_branchAdapter = new ArrayAdapter<SpinnerModal>(getActivity(), R.layout.spinner_item, bank_branchList);
                            bank_branchAdapter.setDropDownViewResource(R.layout.spinner_item);
                            bank_branchSpin.setAdapter(bank_branchAdapter);

                            claimidd="yes";

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
        params.put("type", "1");

        smr.setParams(params);
        rq.add(smr);

    }

    @SuppressLint("NewApi")
    private void showDialog() {
        Dialog myDialog;

        myDialog = new Dialog(getActivity());

        myDialog.setContentView(R.layout.export_window);

        dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        newCalendar = Calendar.getInstance();

        EditText discharge_date = myDialog.findViewById(R.id.discharge_date);
        EditText hos_date = myDialog.findViewById(R.id.hos_date);
        TextView claim_clear_btn = myDialog.findViewById(R.id.claim_clear_btn);
        TextView add_user_btn = myDialog.findViewById(R.id.add_user_btn);
        Spinner claim_type_spin= myDialog.findViewById(R.id.claim_type_spin);


        bank_name = new ArrayList<>();
        bank_nameList = new ArrayList<>();

        bank_nameList.add(new SpinnerModal("hospitalization",
                "Hospitalization"));
        bank_name.add("Hospitalization");

        bank_nameList.add(new SpinnerModal("intimate",
                "Intimation"));
        bank_name.add("Intimation");


        bank_nameAdapter = new ArrayAdapter<SpinnerModal>(getActivity(), R.layout.spinner_item, bank_nameList);
        bank_nameAdapter.setDropDownViewResource(R.layout.spinner_item);


        claim_type_spin.setAdapter(bank_nameAdapter);


        hos_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Utils.desablePostDatePicker(getActivity(), new MyDateListener() {
                    @Override
                    public void onDateSelect(int selectedYear, int selectedMonth, int selectedDay) {
                        newCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                        newCalendar.set(Calendar.MONTH, selectedMonth);
                        newCalendar.set(Calendar.YEAR, selectedYear);

                        day = selectedDay;
                        month = selectedMonth;
                        year = selectedYear;

                        _day = String.valueOf(selectedDay);
                        _month = String.valueOf(selectedMonth);
                        _year = String.valueOf(selectedYear);


                        day_b = selectedDay;
                        month_b = selectedMonth;
                        year_b = selectedYear;


                        String date = DateTimeFormater.getFormatedDate(newCalendar.getTime());
                        hos_date.setText(date);
                        discharge_date.setText("");


                        //travel_request_todateshow.setText("");
                    }
                });
            }
        });


        discharge_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Utils.maxdateAdult(getActivity(), day, month, year, new MyDateListener() {
                    @Override
                    public void onDateSelect(int selectedYear, int selectedMonth, int selectedDay) {
                        newCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                        newCalendar.set(Calendar.MONTH, selectedMonth);
                        newCalendar.set(Calendar.YEAR, selectedYear);

                        day_a = selectedDay;
                        month_a = selectedMonth;
                        year_a = selectedYear;

                        String date = DateTimeFormater.getFormatedDate(newCalendar.getTime());
                        discharge_date.setText(date);

                        //travel_request_todateshow.setText("");
                    }
                });
            }
        });

        claim_clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             hos_date.setText(null);
                discharge_date.setText(null);
                myDialog.dismiss();
            }
        });


        add_user_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = ProgressDialog.show(getActivity(), "",
                        "Saving. Please wait...", true);


                RequestQueue rq = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
                rq.getCache().clear();
                String url = con.base_url + "/api/admin/export/portal-claims";
                StringRequest smr = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {


                                    Log.d("save data",response);

                                    progressDialog.dismiss();


                                    JSONObject js=new JSONObject(response);

                                    String status= String.valueOf(js.getString("status"));



                                    if (status.equals("true")) {

                                        JSONObject jsonobject=js.getJSONObject("data");

                                        String url=jsonobject.getString("url");

                                                        DownloadManager downloadManager = null;
                                                        downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
                           Uri uri = Uri.parse(url);
                              DownloadManager.Request request = new DownloadManager.Request(uri);
                          request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                          Long reference = downloadManager.enqueue(request);
                                                      myDialog.dismiss();


                                    }else {
                                        String message=js.getString("message");

                                        new AlertDialog.Builder(getActivity())
                                                .setTitle("Error")
                                                .setMessage(message)
                                                .setIcon(android.R.drawable.btn_dialog)
                                                .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                                                    public void onClick(DialogInterface dialog, int whichButton) {

                                                    }
                                                }).show();
                                    }
                                } catch (Exception e) {
                                    Log.e("my catche", e.toString());
                                    progressDialog.dismiss();

                                }
                            }
                        },  new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("onErrorResponse", error.toString());
                        progressDialog.dismiss();
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("Authorization", "Bearer " + token);
                        return headers;
                    }
                };













                SpinnerModal patient = (SpinnerModal) claim_type_spin.getSelectedItem();


                HashMap<String, String> params = new HashMap<>();
                params.put("start_date", hos_date.getText().toString().trim());
                params.put("end_date", discharge_date.getText().toString().trim());
                params.put("claim_type", patient.getSelKey());
                params.put("employer_id", employer_id_my);


                Log.d("employer_id", employer_id_my);


                smr.setParams(params);
                rq.add(smr);

            }
        });

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        myDialog.show();
    }





    private void setBankDet() {

        SpinnerModal policy_typ = (SpinnerModal) policy_type_spin_no.getSelectedItem();
        SpinnerModal policy_noo = (SpinnerModal) bank_citySpin.getSelectedItem();

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
                                        claim_reimb_id="-";
                                    }else {
                                        claim_reimb_id= jo_area.getString("claim_id");
                                    }
                                    String claim_request_date = (jo_area.getString("claim_request_date"));
                                    String name = jo_area.getString("member_name");
                                    String type = jo_area.getString("claim_type");
                                    String created_at = (jo_area.getString("claim_date"));
                                    String total_claim_amount = (jo_area.getString("claim_amt"));
                                    String claim_reimb_reason = (jo_area.getString("claim_disease"));
                                    String member_id = (jo_area.getString("member_id"));
                                    String employee_id = (jo_area.getString("employee_id"));
                                    String id = (jo_area.getString("id"));
                                    String claim_hospitalization_type = (jo_area.getString("claim_hospitalization_type"));








                                    ob.add(new
                                            MyIntimateClaimModel(
                                            employee_id,
                                            claim_reimb_id,
                                            name, type,
                                            created_at,
                                            total_claim_amount,
                                            claim_reimb_reason,
                                            claim_request_date,member_id,
                                            "hos",
                                            policy_typ.getSelKey(),
                                            policy_noo.getSelKey(),
                                            id,claim_hospitalization_type));


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
    //    SpinnerModal bank_city_modal = (SpinnerModal) bank_citySpin.getSelectedItem();


        HashMap<String, String> params = new HashMap<>();
        params.put("claim_type", "hospitalization");
        params.put("employee_id",emp_id);
        params.put("policy_id", bank_state_modal.getSelKey());
      /*  //params.put("employee_id", employee_id);*/
        params.put("member_id", " ");

        smr.setParams(params);
        rq.add(smr);

    }

    private void setBankDetCahsless() {
        SpinnerModal policy_typ = (SpinnerModal) policy_type_spin_no.getSelectedItem();
        SpinnerModal policy_noo = (SpinnerModal) bank_citySpin.getSelectedItem();

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
                                info_text_plan.setVisibility(View.VISIBLE);
                                ghi_recycle_plan.setVisibility(View.GONE);
                            } else {
                                info_text_plan.setVisibility(View.GONE);
                                ghi_recycle_plan.setVisibility(View.VISIBLE);
                                JSONArray jsonObj =resp.getJSONArray("data");

                                for (int j = 0; j < jsonObj.length(); j++) {
                                    JSONObject jo_area = (JSONObject) jsonObj.get(j);

                                    if(jo_area.getString("claim_request_id").equalsIgnoreCase("null")){
                                        claim_reimb_id="-";
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
                                    String claim_reimb_reason = (jo_area.getString("claim_disease"));
                                    String status = (jo_area.getString("claim_status"));

                                    String member_id = (jo_area.getString("member_id"));
                                    String employee_id = (jo_area.getString("employee_id"));
                                    String id = (jo_area.getString("id"));
                                    String claim_hospitalization_type = (jo_area.getString("claim_hospitalization_type"));






                                    ob_e.add(new
                                            MyIntimateClaimModel(
                                            employee_id,
                                            claim_reimb_id,
                                            name, type,
                                            created_at,
                                            total_claim_amount,
                                            claim_reimb_reason,
                                            claim_request_date,member_id,
                                            "hos",
                                            policy_typ.getSelKey(),
                                            policy_noo.getSelKey(),
                                            id,
                                            claim_hospitalization_type));






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
        params.put("employee_id",emp_id);

        params.put("policy_id", bank_state_modal.getSelKey());
        /*//params.put("employee_id", employee_id);*/
        params.put("member_id", "");

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





                        String employer_id = explrObject.getString("employee_id");

                         emp_id = explrObject.getString("employee_id");

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
        rq.add(mStringRequest);





    }
    private void setBankDetM() {

        SpinnerModal policy_typ = (SpinnerModal) policy_type_spin_no.getSelectedItem();
        SpinnerModal policy_noo = (SpinnerModal) bank_citySpin.getSelectedItem();

        ob = new ArrayList<>();
        adapter = new MyIntimateClaimAdapter(getActivity(), ob,policy_typ.getSelKey());
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
                                        claim_reimb_id="-";
                                    }else {
                                        claim_reimb_id= jo_area.getString("claim_id");
                                    }
                                    String claim_request_date = (jo_area.getString("claim_request_date"));
                                    String name = jo_area.getString("member_name");
                                    String type = jo_area.getString("claim_type");
                                    String created_at = (jo_area.getString("claim_date"));
                                    String total_claim_amount = (jo_area.getString("claim_amt"));
                                    String claim_reimb_reason = (jo_area.getString("claim_disease"));
                                    String member_id = (jo_area.getString("member_id"));
                                    String employee_id = (jo_area.getString("employee_id"));
                                    String id = (jo_area.getString("id"));


                                    String claim_hospitalization_type = (jo_area.getString("claim_hospitalization_type"));






                                    ob.add(new
                                            MyIntimateClaimModel(
                                            employee_id,
                                            claim_reimb_id,
                                            name, type,
                                            created_at,
                                            total_claim_amount,
                                            claim_reimb_reason,
                                            claim_request_date,member_id,
                                            "hos",
                                            policy_typ.getSelKey(),
                                            policy_noo.getSelKey(),
                                            id,
                                            claim_hospitalization_type));


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
        params.put("employee_id",emp_id);
        params.put("member_id", bank_city_modal.getSelKey());
        params.put("policy_id", bank_state_modal.getSelKey());
      /*  //params.put("employee_id", employee_id);*/

        smr.setParams(params);
        rq.add(smr);

    }


    private void setBankDetM_cashless() {
        SpinnerModal policyname = (SpinnerModal) policy_type_spin_no.getSelectedItem();

        ob_e = new ArrayList<>();
        adapter_e = new MyIntimateClaimAdapter(getActivity(), ob_e,policyname.getSelKey());
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
                                        claim_reimb_id="-";
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
                                    String claim_reimb_reason = (jo_area.getString("claim_disease"));

                                    String status = (jo_area.getString("claim_status"));
                                    String claim_hospitalization_type = (jo_area.getString("claim_hospitalization_type"));


                                    ob_e.add(new MyIntimateClaimModel(claim_reimb_id,claim_id ,
                                            name, type, created_at, total_claim_amount,
                                            claim_reimb_reason,claim_request_date,status,
                                            claim_hospitalization_type));


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
        params.put("employee_id",emp_id);
        params.put("member_id", bank_city_modal.getSelKey());
        params.put("policy_id", bank_state_modal.getSelKey());
        //params.put("employee_id", employee_id);

        smr.setParams(params);
        rq.add(smr);

    }

    private void getBankName() {
        //String url =con.base_url+"/api/employee/policies";
        String url =con.base_url+"/api/broker/get/employer/policy/type";
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

                                if(jsonObj.getString("name").equalsIgnoreCase("Group Mediclaim")||
                                        jsonObj.getString("name").equalsIgnoreCase("Group Personal Accident")){
                                    bank_nameList.add(new SpinnerModal(jsonObj.getString("id"),
                                            jsonObj.getString("name")));
                                    bank_name.add(jsonObj.getString("name"));
                                }
                            }






                            bank_nameAdapter = new ArrayAdapter<SpinnerModal>(getActivity(), R.layout.spinner_item, bank_nameList);
                            bank_nameAdapter.setDropDownViewResource(R.layout.spinner_item);


                            policy_type_spin.setAdapter(bank_nameAdapter);
                            // policy_type_spin_no.setAdapter(bank_nameAdapter);

                            SpinnerModal policyname = (SpinnerModal) policy_type_spin_no.getSelectedItem();


                            ob = new ArrayList<>();
                            adapter = new MyIntimateClaimAdapter(getActivity(), ob,policyname.getSelKey());
                            recyclerView.setAdapter(adapter);

                            setBankDet();


                            ob_e = new ArrayList<>();
                            adapter_e = new MyIntimateClaimAdapter(getActivity(), ob_e,policyname.getSelKey());
                            ghi_recycle_plan.setAdapter(adapter_e);

                            setBankDetCahsless();


                            oba = new ArrayList<>();
                            adaptera = new MyHosClaimAdapter(getActivity(), oba);
                            voluntary.setAdapter(adaptera);




                            setBankDetVoluntary();



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


        SpinnerModal bank_city_modal = (SpinnerModal) policy_type_spin.getSelectedItem();


        HashMap<String, String> params = new HashMap<>();
        params.put("employer_id", employer_id_data);
        params.put("policy_sub_type_id", bank_city_modal.getSelKey());
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

                                    bank_cityList.add(new SpinnerModal(jsonObj.getString("tpa_member_id"),
                                            String.valueOf(jsonObj.getString("name")),
                                            String.valueOf(jsonObj.getString("member_id")),
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



                            oba = new ArrayList<>();
                            adaptera = new MyHosClaimAdapter(getActivity(), oba);
                            voluntary.setAdapter(adaptera);


                            setBankDetVoluntary();

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





                        employer_id_my = explrObject.getString("employer_id");

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

    private void setBankDetVoluntary() {

        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        String url = con.base_url+"/api/admin/get/overall-claim";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject resp= new JSONObject(response);

                            String statusa= String.valueOf(resp.getBoolean("status"));



                            if (statusa.equalsIgnoreCase("false")) {
                                info_text_voluntary.setVisibility(View.GONE);
                                intimation_lin.setVisibility(View.GONE);
                                voluntary.setVisibility(View.GONE);
                            } else {

                                info_text_voluntary.setVisibility(View.GONE);
                                voluntary.setVisibility(View.VISIBLE);
                                JSONArray jsonObj =resp.getJSONArray("data");

                                for (int j = 0; j < jsonObj.length(); j++) {
                                    JSONObject jo_area = (JSONObject) jsonObj.get(j);
                                    if(jo_area.getString("claim_id").equalsIgnoreCase("null")){
                                        claim_reimb_id="-";
                                    }else {
                                        claim_reimb_id= jo_area.getString("claim_id");
                                    }
                                    String claim_request_date = (jo_area.getString("claim_request_date"));
                                    String name = jo_area.getString("member_name");
                                    String type = jo_area.getString("claim_type");
                                    String created_at = (jo_area.getString("claim_date"));
                                    String total_claim_amount = (jo_area.getString("claim_amount"));
                                    String claim_reimb_reason = (jo_area.getString("claim_reason"));


                                    oba.add(new MyHosClaimModel(claim_reimb_id, claim_reimb_id,
                                            name, created_at, total_claim_amount, type,
                                            "", claim_reimb_reason, claim_reimb_id,
                                            "",claim_request_date));


                                }


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
        params.put("claim_type", "intimate");
        params.put("employee_id",emp_id);

        SpinnerModal bank_state_modal = (SpinnerModal) policy_type_spin_no.getSelectedItem();

        params.put("member_id", " ");

        params.put("policy_id", bank_state_modal.getSelKey());
       /* //params.put("employee_id", employee_id);*/

        smr.setParams(params);
        rq.add(smr);

    }


    private void setBankDetVoluntaryM() {
        oba = new ArrayList<>();
        adaptera = new MyHosClaimAdapter(getActivity(), oba);
        voluntary.setAdapter(adaptera);

        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        String url = con.base_url+"/api/admin/get/overall-claim";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject resp= new JSONObject(response);

                            String statusa= String.valueOf(resp.getBoolean("status"));



                            if (statusa.equalsIgnoreCase("false")) {
                                info_text_voluntary.setVisibility(View.GONE);
                                intimation_lin.setVisibility(View.GONE);
                                voluntary.setVisibility(View.GONE);
                            } else {
                                info_text_voluntary.setVisibility(View.GONE);
                                intimation_lin.setVisibility(View.GONE);
                                voluntary.setVisibility(View.VISIBLE);
                                JSONArray jsonObj =resp.getJSONArray("data");

                                for (int j = 0; j < jsonObj.length(); j++) {
                                    JSONObject jo_area = (JSONObject) jsonObj.get(j);
                                    if(jo_area.getString("claim_id").equalsIgnoreCase("null")){
                                        claim_reimb_id="-";
                                    }else {
                                        claim_reimb_id= jo_area.getString("claim_id");
                                    }
                                    String claim_request_date = (jo_area.getString("claim_request_date"));
                                    String name = jo_area.getString("member_name");
                                    String type = jo_area.getString("claim_type");
                                    String created_at = (jo_area.getString("claim_date"));
                                    String total_claim_amount = (jo_area.getString("claim_amt"));
                                    String claim_reimb_reason = (jo_area.getString("claim_reason"));


                                    oba.add(new MyHosClaimModel(claim_reimb_id,
                                            claim_reimb_id, name, created_at,
                                            total_claim_amount, type, "",
                                            claim_reimb_reason, claim_reimb_id, ""
                                    ,claim_request_date));


                                }


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

        SpinnerModal bank_state_modal = (SpinnerModal) policy_type_spin_no.getSelectedItem();
        SpinnerModal bank_city_modal = (SpinnerModal) bank_citySpin.getSelectedItem();


        HashMap<String, String> params = new HashMap<>();
        params.put("employee_id",emp_id);
        params.put("claim_type", "intimate");

        params.put("member_id", bank_city_modal.getSelKey());
        params.put("policy_id", bank_state_modal.getSelKey());
        //params.put("employee_id", employee_id);

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

