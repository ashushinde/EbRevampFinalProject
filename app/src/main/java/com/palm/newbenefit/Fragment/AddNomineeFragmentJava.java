package com.palm.newbenefit.Fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.palm.newbenefit.Activity.NomineeViewActivity;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.ApiConfig.DateTimeFormater;
import com.palm.newbenefit.ApiConfig.MyDateListener;
import com.palm.newbenefit.ApiConfig.Utils;
import com.palm.newbenefit.DatabaseHelper.DBHelper;
import com.palm.newbenefit.Module.AddNominee;
import com.palm.newbenefit.Module.SpinnerModal;
import com.kmd.newbenefit.R;
import com.palm.tatarewamp.SslData.NullHostNameVerifier;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class AddNomineeFragmentJava extends Fragment {
    ArrayList<String> n_First_name = null;
    ArrayList<String> n_last_name = null;
    Context context;
    ArrayList<String> addshare = null;
    ArrayList<String> n_dob = null;
    ArrayList<String> n_relation = null;
    ArrayList<String> g_First_name = null;
    ArrayList<String> g_last_name = null;
    ArrayList<String> g_dob = null;
    ArrayList<String> g_relation = null;
    ArrayList<String> share_n = null;

    FragmentManager manager;
    ArrayList<String> bank_name = null;
    ArrayList<SpinnerModal> bank_nameList = null;
    ArrayAdapter<SpinnerModal> bank_nameAdapter = null;
    Spinner relation_type_spin,relation_nominee;
    String policy_no;
    String gender = "Female";
    RadioButton male, female;
    Button saveBtn;
    EditText first_name, last_name, dob, share;
    EditText first_namen, last_namen, dobn;
    private SimpleDateFormat dateFormatter;
    LinearLayout guar_detail;
    TextView hos_add_button, hos_clear_btn, send, view_data;
    Constants con;

    ArrayList<String> list=new ArrayList<String>();

    int data = 0;
    Calendar newCalendar = Calendar.getInstance();
    ProgressDialog progressDialog = null;
    private DatePickerDialog fromDatePickerDialog;

    DBHelper db;
    AddNominee typedata;



    private List<AddNominee> ob;
    ArrayList Nomineelist=new ArrayList();


    public AddNomineeFragmentJava() {
        // Required empty public constructor
    }


String token,policy_id;

    String employer_id ,emp_id;
    String user_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_add_nominee_java, container, false);
        context=getActivity();
        manager = getActivity().getSupportFragmentManager();

        con= new Constants();
        relation_type_spin = v.findViewById(R.id.relation_type_spin);
        newCalendar = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        // saveBtn = v.findViewById(R.id.saveBtn);
        db = new DBHelper(getActivity());

        typedata = new AddNominee();
        db.removeAll();
        list=new ArrayList<>();

        SharedPreferences prefs = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);

        token = prefs.getString("api_token", null);
        user_id = prefs.getString("user_id", null);

        policy_id = prefs.getString("policy_id", null);
        send =  v.findViewById(R.id.send);

        hos_add_button =  v.findViewById(R.id.hos_add_button);
        view_data =  v.findViewById(R.id.view_data);

        guar_detail = v.findViewById(R.id.guar_detail);
        first_name = v.findViewById(R.id.first_name);
        last_name = v.findViewById(R.id.last_name);
        dob = v.findViewById(R.id.dob);
        share = v.findViewById(R.id.share);
        first_namen = v.findViewById(R.id.first_namen);
        last_namen = v.findViewById(R.id.last_namen);
        relation_nominee = v.findViewById(R.id.relation_nominee);
        dobn = v.findViewById(R.id.dobn);


        setProfileDet();


        GetNominee();










        view_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), NomineeViewActivity.class);
                startActivity(intent);

            }
        });


        hos_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





                if (isNetworkAvailable()) {



                    SendHospital();

               //  SendDataToServer();


                }else {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Error?")
                            .setMessage("Please Check Your Internet Connection")
                            .setIcon(android.R.drawable.btn_dialog)
                            .setNegativeButton(android.R.string.ok, null).show();
                }







            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (isNetworkAvailable()) {



                    int count = 0;

                    SpinnerModal patient = (SpinnerModal) relation_type_spin.getSelectedItem();


                    if (patient.getSelValue().equalsIgnoreCase("")) {
                        ++count;
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Error")
                                .setMessage("Please Select Relation With Employee")
                                .setIcon(android.R.drawable.btn_dialog)
                                .setNegativeButton(android.R.string.ok, null).show();
                        return;
                    }


                    if (first_name.getText().toString().trim().length()<=1) {
                        ++count;
                        first_name.setError("First Name is Required");
                    } else {
                        first_name.setError(null);
                    }


       /* if (last_name.getText().toString().trim().length() == 0) {
            ++count;
            last_name.setError("Last Name is Required");
        } else {
            last_name.setError(null);
        }*/

                    if (dob.getText().toString().trim().length() == 0) {
                        ++count;
                        dob.setError("Date Of Birth is Required");
                    } else {
                        dob.setError(null);
                    }

                    try{
                        int shareper= Integer.parseInt(share.getText().toString().toString());

                        if (share.getText().toString().trim().length() == 0) {
                            ++count;
                            share.setError("Enter Share");
                        } else {
                            if(share.getText().toString().trim().startsWith("0"))
                            {
                                ++count;
                                share.setError("Enter Valid Share");
                            }

                            else if(shareper>100){
                                ++count;
                                share.setError("Enter Valid Share");
                            }else {
                                share.setError(null);
                            }
                        }
                    }catch (Exception e){
                        if (share.getText().toString().trim().length() == 0) {
                            ++count;
                            share.setError("Enter Share");
                        }
                    }












        /*if (share.getText().toString().trim().length() == 0) {
            ++count;
            share.setError("Enter Share");
        } else {
            share.setError(null);
        }*/


                    if (guar_detail.getVisibility() == View.VISIBLE) {

                        SpinnerModal patienta = (SpinnerModal) relation_nominee.getSelectedItem();


                        if (patienta.getSelValue().equalsIgnoreCase("")) {
                            ++count;
                            new AlertDialog.Builder(getActivity())
                                    .setTitle("Error")
                                    .setMessage("Please Select Gardian Relation ")
                                    .setIcon(android.R.drawable.btn_dialog)
                                    .setNegativeButton(android.R.string.ok, null).show();
                            return;
                        }

                        if (first_namen.getText().toString().trim().length() == 0) {
                            ++count;
                            first_namen.setError("Guardian First Name is Required");
                        } else {
                            first_namen.setError(null);
                        }


        /*    if (last_namen.getText().toString().trim().length() == 0) {
                ++count;
                last_namen.setError("Guardian Last Name is Required");
            } else {
                last_namen.setError(null);
            }*/

                        if (dobn.getText().toString().trim().length() == 0) {
                            ++count;
                            dobn.setError("Guardian Date of Birth is Required");
                        } else {
                            dobn.setError(null);
                        }

                    }


                    if (count == 0) {
                        SendDataToServer();
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











        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.desablePostDatePicker(getActivity(), new MyDateListener() {
                    @Override
                    public void onDateSelect(int selectedYear, int selectedMonth, int selectedDay) {
                        newCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                        newCalendar.set(Calendar.MONTH, selectedMonth);
                        newCalendar.set(Calendar.YEAR, selectedYear);
                        String date = DateTimeFormater.getFormatedDate(newCalendar.getTime());




                        dob.setText(date);


                        Calendar doba = Calendar.getInstance();
                        Calendar today = Calendar.getInstance();

                        Log.d("date",selectedYear+"-"+selectedMonth+"-"+selectedDay);

                        doba.set(selectedYear, selectedMonth, selectedDay);

                        int age = today.get(Calendar.YEAR) - doba.get(Calendar.YEAR);

                        if (today.get(Calendar.DAY_OF_YEAR) < doba.get(Calendar.DAY_OF_YEAR)){
                            age--;
                        }

                        Integer ageInt = new Integer(age);
                        String ageS = ageInt.toString();




                        if (ageInt <= 18) {
                            guar_detail.setVisibility(View.VISIBLE);
                        } else {
                            guar_detail.setVisibility(View.GONE);
                        }

                        //travel_request_todateshow.setText("");
                    }
                });

            }
        });


        dobn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(
                        getActivity(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker,
                                          int selectedyear, int selectedmonth,
                                          int selectedday) {
                        selectedyear=selectedyear;
                        mcurrentDate.set(Calendar.YEAR, selectedyear);
                        mcurrentDate.set(Calendar.MONTH, selectedmonth);
                        mcurrentDate.set(Calendar.DAY_OF_MONTH, selectedday);
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                        dobn.setText(sdf.format(mcurrentDate.getTime()));


                    }
                }, mYear-18, mMonth, mDay);
                mcurrentDate.set(mYear-18,mMonth,mDay);
                long value=mcurrentDate.getTimeInMillis();
                mDatePicker.getDatePicker().setMaxDate(value);
                mDatePicker.show();

            }
        });


        relation_type_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerModal bank_name_modal = (SpinnerModal) adapterView.getSelectedItem();
                if (!bank_name_modal.selValue.equals("")) {
                    try {


                        if (isNetworkAvailable()) {

                            MembersData(bank_name_modal.getSelValue());
                        } else {
                            new AlertDialog.Builder(getActivity())
                                    .setTitle("Error?")
                                    .setMessage("Please Check Your Internet Connection")
                                    .setIcon(android.R.drawable.btn_dialog)
                                    .setNegativeButton(android.R.string.ok, null).show();
                        }


                    } catch (Exception e) {

                    }
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });










        getBankNameNominee();
        GetNominee();






        return v;
    }


    public void SendHospital() {

        int count = 0;

        SpinnerModal patient = (SpinnerModal) relation_type_spin.getSelectedItem();


        if (patient.getSelValue().equalsIgnoreCase("")) {
            ++count;
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error")
                    .setMessage("Please Select Relation With Employee")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
            return;
        }


        if (first_name.getText().toString().trim().length()<=1) {
            ++count;
            first_name.setError("First Name is Required");
        } else {
            first_name.setError(null);
        }


       /* if (last_name.getText().toString().trim().length() == 0) {
            ++count;
            last_name.setError("Last Name is Required");
        } else {
            last_name.setError(null);
        }*/

        if (dob.getText().toString().trim().length() == 0) {
            ++count;
            dob.setError("Date Of Birth is Required");
        } else {
            dob.setError(null);
        }



        if (share.getText().toString().trim().length() == 0) {
            ++count;
            share.setError("Enter Share");
        } else {
            if(share.getText().toString().trim().startsWith("0"))
            {
                ++count;
                share.setError("Enter Valid Share");
            }
            else {
                share.setError(null);
            }
        }










        /*if (share.getText().toString().trim().length() == 0) {
            ++count;
            share.setError("Enter Share");
        } else {
            share.setError(null);
        }*/


        if (guar_detail.getVisibility() == View.VISIBLE) {

            SpinnerModal patienta = (SpinnerModal) relation_nominee.getSelectedItem();


            if (patienta.getSelValue().equalsIgnoreCase("")) {
                ++count;
                new AlertDialog.Builder(getActivity())
                        .setTitle("Error")
                        .setMessage("Please Select Gardian Relation ")
                        .setIcon(android.R.drawable.btn_dialog)
                        .setNegativeButton(android.R.string.ok, null).show();
                return;
            }

            if (first_namen.getText().toString().trim().length() == 0) {
                ++count;
                first_namen.setError("Guardian First Name is Required");
            } else {
                first_namen.setError(null);
            }


        /*    if (last_namen.getText().toString().trim().length() == 0) {
                ++count;
                last_namen.setError("Guardian Last Name is Required");
            } else {
                last_namen.setError(null);
            }*/

            if (dobn.getText().toString().trim().length() == 0) {
                ++count;
                dobn.setError("Guardian Date of Birth is Required");
            } else {
                dobn.setError(null);
            }

        }


        if (count == 0) {

          //  SendDataToServer();

            data=0;
            int amt = Integer.parseInt(share.getText().toString());

            data = data + amt;


            if (data > 100) {

                new AlertDialog.Builder(getActivity())
                        .setTitle("Error")
                        .setMessage("Share % Cannot Exceed 100%")
                        .setIcon(android.R.drawable.btn_dialog)
                        .setNegativeButton(android.R.string.ok, null).show();

            } else {


                // SpinnerModal policy = (SpinnerModal) relation_type_spin.getSelectedItem();

                boolean isd = false;
                SpinnerModal patients = (SpinnerModal) relation_type_spin.getSelectedItem();
                SpinnerModal patienta = (SpinnerModal) relation_nominee.getSelectedItem();


                if (guar_detail.getVisibility() == View.VISIBLE) {


                    isd = db.AddNominee(new AddNominee(first_name.getText().toString(), last_name.getText().toString()
                            , dob.getText().toString(), patients.getSelKey(), patienta.getSelValue(),
                            first_namen.getText().toString(), last_namen.getText().toString(),
                            dobn.getText().toString(), share.getText().toString()));


                } else {

                    isd = db.AddNominee(new AddNominee(first_name.getText().toString(), last_name.getText().toString()
                            , dob.getText().toString(), patients.getSelKey(), patienta.getSelValue(),
                            first_namen.getText().toString(), last_namen.getText().toString(),
                            dobn.getText().toString(), share.getText().toString()));


                }


                if (isd == true) {

                    dob.setText("");
                    GetNominee();


                    getBankNameNominee();
                    first_name.setText("");
                    first_namen.setText("");
                    dobn.setText("");
                    last_name.setText("");
                    share.setText("");
                    /*data=0;*/
                    last_namen.setText("");
                    guar_detail.setVisibility(View.GONE);


                    SendDataToServer();

                 /*   hos_add_button.setVisibility(View.GONE);
                    view_data.setVisibility(View.GONE);
                    send.setVisibility(View.VISIBLE);*/

                 /*   AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                    alertDialog.setTitle("Success");
                    alertDialog.setIcon(R.drawable.checkmark);
                    alertDialog.setMessage("Data Added Successfully !");
                    alertDialog.setCancelable(false);
                    alertDialog.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                            dob.setText("");
                            GetNominee();


                            getBankNameNominee();
                            first_name.setText("");
                            first_namen.setText("");
                            dobn.setText("");
                            last_name.setText("");
                            share.setText("");
                            *//*data=0;*//*
                            last_namen.setText("");
                            guar_detail.setVisibility(View.GONE);


                        }
                    });
                    alertDialog.show();
*/




                    /*new AlertDialog.Builder(getActivity())
                            .setTitle("Success")
                            .setMessage("Data Add Successfully !")
                            .setIcon(R.drawable.checkmark)
                            .setNegativeButton(android.R.string.ok, null).show();

*/
                   /* dob.setText("");
                    relation_nominee.setText("");
                    relation_type_spin.setText("");
                    first_name.setText("");
                    first_namen.setText("");
                    dobn.setText("");
                    last_name.setText("");
                    share.setText("");
                    data=0;
                    last_namen.setText("");*/
                   // guar_detail.setVisibility(View.GONE);
                } else {
                   /* view_data.setVisibility(View.GONE);
                    send.setVisibility(View.GONE);


                    new AlertDialog.Builder(getActivity())
                            .setTitle("Alert")
                            .setMessage("Error !")
                            .setIcon(android.R.drawable.btn_dialog)
                            .setNegativeButton(android.R.string.ok, null).show();*/
                }

            }
        }


    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        context = getActivity();
        if (isVisibleToUser && (getActivity() != null)) {
            //  getActivity();


            db = new DBHelper(getActivity());
            typedata = new AddNominee();
            db.removeAll();
            dob.setText("");

          getBankNameNominee();
            GetNominee();


            first_name.setText("");
            first_namen.setText("");
            dobn.setText("");
            last_name.setText("");
            share.setText("");
            data=0;
            send.setVisibility(View.VISIBLE);
            view_data.setVisibility(View.GONE);
            hos_add_button.setText("Add");

            last_namen.setText("");
            guar_detail.setVisibility(View.GONE);


        }


    }

    @SuppressLint("NewApi")
    public void SendDataToServer() {

        progressDialog = ProgressDialog.show(getActivity(), "",
                "Saving. Please wait...", true);





        AddNominee();




    }



    void NominneConfig(){
        list=new ArrayList<String>();
        RequestQueue rq = Volley.newRequestQueue(getActivity());
rq.getCache().clear();

        //  RequestQueue rq = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));

        String url = con.base_url+"/api/broker/get/nominee-config?configurable_type=policy&configurable_id="+policy_id;
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {



                            Log.d("nomneelistt",response);
                            JSONObject jsonObject = new JSONObject(response);
                           JSONArray array =jsonObject.getJSONArray("data");

                           if(array.toString().isEmpty()
                           || array.toString().equalsIgnoreCase("[]")){
                               list.add("no");
                               getBankName(list);
                           }else {

                               JSONObject object = array.getJSONObject(0);
                               String allowed_relations =object.getString("allowed_relations");


                               List<String> myList = new ArrayList<String>(Arrays.asList(allowed_relations.split(",")));


                               for (int i = 0; i < myList.size(); i++) {
                                   list.add(myList.get(i));

                               }


                               Log.d("nominee_config", String.valueOf(list));



                               getBankName(list);
                           }














                        } catch (Exception e) {

                            e.printStackTrace();
                            Log.d("nominee_config", e.toString());

                            list.add("no");
                            getBankName(list);
                        }

                    }
                },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onErrorResponse", error.toString());
               // progressDialog.dismiss();
                list.add("no");
                getBankName(list);
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



    void AddNominee() {

        RequestQueue rq = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();




        SpinnerModal patients = (SpinnerModal) relation_type_spin.getSelectedItem();
        SpinnerModal patienta = (SpinnerModal) relation_nominee.getSelectedItem();

        JSONArray nominee_fname=new JSONArray();
        nominee_fname.put(first_name.getText().toString());

        JSONArray nominee_lname=new JSONArray();
        nominee_lname.put(last_name.getText().toString());

        JSONArray nominee_relation_id=new JSONArray();
        nominee_relation_id.put(patients.getSelKey());

        JSONArray nominee_dob=new JSONArray();
        nominee_dob.put(dob.getText().toString());


        JSONArray share_per=new JSONArray();
        share_per.put(share.getText().toString());




        JSONObject params = new JSONObject();





try{
    if(first_namen.getText().toString().length()==0) {

        params.put("nominee_fname", nominee_fname.toString());
        params.put("nominee_lname",nominee_lname.toString());
        params.put("nominee_relation_id", nominee_relation_id.toString());
        params.put("nominee_dob", nominee_dob.toString());
        params.put("share_per", share_per.toString());
        params.put("employee_id", emp_id);
        params.put("emp_id", employer_id);
        params.put("policy_id", policy_id);
        params.put("agent", "mobile");
    }else {

        JSONArray guardian_fname=new JSONArray();
        guardian_fname.put(first_namen.getText().toString());



        JSONArray guardian_lname=new JSONArray();
        guardian_lname.put(last_namen.getText().toString());

        JSONArray g_dob=new JSONArray();

        try{

            String strCurrentDate = dobn.getText().toString();
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            Date newDate = format.parse(strCurrentDate);

            format = new SimpleDateFormat("yyyy-MM-dd");


            g_dob.put(format.format(newDate));
        }catch (Exception e){
            g_dob.put(dobn.getText().toString());
        }


        JSONArray guardian_relation_id=new JSONArray();
        guardian_relation_id.put(patienta.getSelKey());

        params.put("nominee_fname", nominee_fname.toString());
        params.put("nominee_lname",nominee_lname.toString());
        params.put("nominee_relation_id", nominee_relation_id.toString());
        params.put("nominee_dob", nominee_dob.toString());
        params.put("share_per", share_per.toString());
        params.put("employee_id", emp_id);
        params.put("emp_id", employer_id);
        params.put("policy_id", policy_id);
        params.put("agent", "mobile");
        params.put("guardian_fname",guardian_fname.toString());
        params.put("guardian_lname", guardian_lname.toString());
        params.put("guardian_relation_id",guardian_relation_id.toString() );
        params.put("guardian_dob",g_dob.toString());



    }




}catch (Exception e){

}


        String url = con.base_url + "/api/employee/add/nominee";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String data = null;

                        try {

                            progressDialog.dismiss();
                            Log.d("registerData", String.valueOf(response));



                            String  status= String.valueOf(response.getString("status"));
                            String message=response.getString("message");
                            if(status.equalsIgnoreCase("true")){
                                new AlertDialog.Builder(getActivity())
                                        .setTitle("Success")
                                        .setMessage(message)
                                        .setIcon(R.drawable.checkmark)
                                        .setNegativeButton(android.R.string.ok, null).show();


                            }else {
                                new AlertDialog.Builder(getActivity())
                                        .setTitle("Error")
                                        .setMessage(message)
                                        .setIcon(android.R.drawable.btn_dialog)
                                        .setNegativeButton(android.R.string.ok, null).show();

                            }



                        } catch (Exception e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        try {
                            progressDialog.dismiss();
                            if (error.networkResponse.data != null) {
                                try {
                                    String statusCode = String.valueOf(error.networkResponse.statusCode);
                                    Log.d("statusCode", String.valueOf(statusCode));

                                    String body = new String(error.networkResponse.data, "utf-8");

                                    JSONObject data = new JSONObject(body);

                                    Log.d("registerData", String.valueOf(data));

                                    String message = data.getString("message");


                                    JSONArray arraydata = data.getJSONArray("errors");

                                    AlertDialog.Builder builder = new AlertDialog.Builder
                                            (getActivity());
                                    builder.setTitle(message);

                                    final List<String> lables = new ArrayList<>();

                                    for (int i = 0; i < arraydata.length(); i++) {
                                        /*  JSONObject jsonObj = arraydata.getJSONObject(i);*/
                                        lables.add(String.valueOf(arraydata.get(i)));
                                    }


                                    ArrayAdapter<String> dataAdapter =
                                            new ArrayAdapter<String>(getActivity(),
                                                    android.R.layout.simple_dropdown_item_1line, lables);
                                    builder.setAdapter(dataAdapter, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(getActivity(), "You have selected " + lables.get(which), Toast.LENGTH_LONG).show();
                                        }
                                    });
                                    AlertDialog dialog = builder.create();
                                    dialog.show();

/*

                                        new AlertDialog.Builder(UploadPassportActivity.this)
                                                .setTitle("Error")
                                                .setMessage((arraydata.toString()))
                                                .setIcon(R.drawable.alert)
                                                .setNegativeButton(android.R.string.ok, null).show();
*/


                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                    progressDialog.dismiss();
                                }
                            } else {
                                String statusCode = String.valueOf(error.networkResponse.statusCode);
                                Log.d("statusCode", String.valueOf(statusCode));
                            }

                        } catch (Exception e) {
                        }

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                Log.e("my_Response", String.valueOf(response.data));
                Log.e("statusCode", String.valueOf(response.statusCode));


                return super.parseNetworkResponse(response);
            }
        };


        rq.add(jsonObjectRequest);

    }






    private void getBankName(ArrayList list) {
      //  String url = con.base_url+"/api/admin/get/master/relation";
        String url=con.base_url+"/api/admin/get/family-construct?policy_id="+policy_id;

        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    Log.d("listbank",list.toString());

                    if(list.toString().equalsIgnoreCase("[[\"\"]]")
                    ||list.toString().equalsIgnoreCase("[[\"\"], [\"\"]]")
                    ){
                        Members();
                    }else if(list.toString().equalsIgnoreCase("[no, no]")){
                        Members();
                    }  else if(list.toString().equalsIgnoreCase("[no]")) {
                        Members();
                    }

                    else {
                        Log.d("list",list.toString());
                        JSONObject js=new JSONObject(response);

                        Log.d("response",response);
                        JSONArray jsonArr=js.getJSONArray("data");
                        bank_name = new ArrayList<>();
                        bank_nameList = new ArrayList<>();
                        bank_nameList.add(new SpinnerModal("", "Select Family Relation"));
                        bank_name.add("");
                        for (int i = 0; i < jsonArr.length(); i++) {
                            JSONObject jsonObj = jsonArr.getJSONObject(i);

                            if (!jsonObj.getString("name").equalsIgnoreCase("self")) {



                                Log.d("list",list.toString());

                                Log.d("Nomineelist",Nomineelist.toString());

                                Log.d("yes", String.valueOf(list.toString().contains(jsonObj.getString("id"))));

                                if(list.toString().contains(jsonObj.getString("id"))){


                                    if(Nomineelist.toString().contains("Daughter")
                                    ||Nomineelist.toString().contains("Son")
                                    ||Nomineelist.toString().contains("Brother")||
                                    Nomineelist.toString().contains("Sister")){
                                        bank_nameList.add(new SpinnerModal(jsonObj.getString("id"), jsonObj.getString("name")));
                                        bank_name.add(jsonObj.getString("name"));
                                    }else {
                                        if(!Nomineelist.toString().contains(jsonObj.getString("name"))){
                                            bank_nameList.add(new SpinnerModal(jsonObj.getString("id"), jsonObj.getString("name")));
                                            bank_name.add(jsonObj.getString("name"));
                                        }
                                    }











                                }
                                else if(list.toString().equalsIgnoreCase("no")){

                                    if(Nomineelist.toString().contains("Daughter")
                                            ||Nomineelist.toString().contains("Son")
                                            ||Nomineelist.toString().contains("Brother")||
                                            Nomineelist.toString().contains("Sister")){
                                        bank_nameList.add(new SpinnerModal(jsonObj.getString("id"), jsonObj.getString("name")));
                                        bank_name.add(jsonObj.getString("name"));
                                    }else {
                                        if(!Nomineelist.toString().contains(jsonObj.getString("name"))){
                                            bank_nameList.add(new SpinnerModal(jsonObj.getString("id"), jsonObj.getString("name")));
                                            bank_name.add(jsonObj.getString("name"));
                                        }
                                    }
                                }

                            }
                        }
                        bank_nameAdapter = new ArrayAdapter<SpinnerModal>(getActivity(), R.layout.spinner_item, bank_nameList);
                        bank_nameAdapter.setDropDownViewResource(R.layout.spinner_item);
                        relation_type_spin.setAdapter(bank_nameAdapter);

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
        mRequestQueue.add(mStringRequest);
    }


    void GetNominee(){

        Nomineelist=new ArrayList<>();

        String url=con.base_url+"/api/employee/get/nominee";

         RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {


                     Log.d("nominne",response);
                        JSONObject js=new JSONObject(response);

                            if(js.getString("status").equalsIgnoreCase("false")){
                                Relations();
                               // Nomineelist.add("null");
                            }else {
                                JSONArray jsonArr=js.getJSONArray("data");

                                for (int i = 0; i < jsonArr.length(); i++) {
                                    JSONObject jsonObj = jsonArr.getJSONObject(i);



                                    Nomineelist.add(jsonObj.getString("nominee_relation"));
                                }

                                NominneConfig();

                            }











                } catch (Exception e) {


                            NominneConfig();

                }

            }
        },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onErrorResponse", error.toString());

                NominneConfig();

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
        smr.setParams(params);
        mRequestQueue.add(smr);
    }



    void Members (){
        String url = con.base_url+"/api/employee/get/enroll/members?policy_id="+policy_id;

      RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);
                    Log.d("Nomineelist",Nomineelist.toString());
                    Log.d("Memeberresponse",response);
                    JSONArray jsonArr=js.getJSONArray("data");
                    bank_name = new ArrayList<>();
                    bank_nameList = new ArrayList<>();
                    bank_nameList.add(new SpinnerModal("", "Select Family Relation"));
                    bank_name.add("");
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObj = jsonArr.getJSONObject(i);

                        if (!jsonObj.getString("relation_name").equalsIgnoreCase("self")) {


                            if(list.toString().equalsIgnoreCase("[null, null]")){
                                Relations();
                            }else  if(list.isEmpty()){
                                Relations();


                                }else  if(list.toString().equalsIgnoreCase("[null]")){
                              Relations();
                             }else   if(list.toString().equalsIgnoreCase("[no]")){
                                Relations();
                             }

                              else   if(list.toString().equalsIgnoreCase("[no, no]")){
                            Relations();
                               }

                            else {
                                if(Nomineelist.toString().contains("Daughter")
                                        ||Nomineelist.toString().contains("Son")
                                        ||Nomineelist.toString().contains("Brother")||
                                        Nomineelist.toString().contains("Sister")){
                                    bank_nameList.add(new SpinnerModal(jsonObj.getString("id"),
                                            jsonObj.getString("relation_name")));
                                    bank_name.add(jsonObj.getString("relation_name"));
                                }else {
                                    if(!Nomineelist.toString().contains(jsonObj.getString("relation_name"))){
                                        bank_nameList.add(new SpinnerModal(jsonObj.getString("id"),
                                                jsonObj.getString("relation_name")));
                                        bank_name.add(jsonObj.getString("relation_name"));
                                    }
                                }
                            }

                        }
                    }
                    bank_nameAdapter = new ArrayAdapter<SpinnerModal>(getActivity(), R.layout.spinner_item, bank_nameList);
                    bank_nameAdapter.setDropDownViewResource(R.layout.spinner_item);
                    relation_type_spin.setAdapter(bank_nameAdapter);


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
        mRequestQueue.add(mStringRequest);
    }



    void Relations (){
       /// String url = con.base_url+"/api/admin/get/master/relation";
        String url=con.base_url+"/api/admin/get/family-construct?policy_id="+policy_id;
       RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);

                    JSONArray jsonArr=js.getJSONArray("data");
                    bank_name = new ArrayList<>();
                    bank_nameList = new ArrayList<>();
                    bank_nameList.add(new SpinnerModal("", "Select Family Relation"));
                    bank_name.add("");
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObj = jsonArr.getJSONObject(i);

                        if (!jsonObj.getString("name").equalsIgnoreCase("self")) {

                            bank_nameList.add(new SpinnerModal(jsonObj.getString("id"),
                                        jsonObj.getString("name")));
                                bank_name.add(jsonObj.getString("name"));


                        }
                    }

                    if(bank_nameList.toString().equalsIgnoreCase("[Select Family Relation]")){
                        getNormalNominee();

                    }
                    Log.e("bank_nameList", bank_nameList.toString());

                    bank_nameAdapter = new ArrayAdapter<SpinnerModal>(getActivity(), R.layout.spinner_item, bank_nameList);
                    bank_nameAdapter.setDropDownViewResource(R.layout.spinner_item);
                    relation_type_spin.setAdapter(bank_nameAdapter);


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
        mRequestQueue.add(mStringRequest);
    }


    void MembersData (String valuedata){
        String url = con.base_url+"/api/employee/get/enroll/members?policy_id="+policy_id;

         RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);


                    JSONArray jsonArr=js.getJSONArray("data");

                    Log.e("data", String.valueOf(data));

                    dob.setText(null);
                    first_name.setText(null);
                    last_name.setText(null);


                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObj = jsonArr.getJSONObject(i);

                        Log.e("valuedata", valuedata);



                        if (jsonObj.getString("relation_name").equalsIgnoreCase(valuedata)) {



                            if(jsonObj.getString("first_name").equalsIgnoreCase("")
                                    || jsonObj.getString("first_name").equalsIgnoreCase("null")){
                                first_name.setText(null);
                            }else {
                                first_name.setText(jsonObj.getString("first_name"));

                                //last_name.setFocusableInTouchMode(false);
                            }



                           if(jsonObj.getString("last_name").equalsIgnoreCase("")
                           || jsonObj.getString("last_name").equalsIgnoreCase("null")){
                               last_name.setText(null);
                           }else {
                               last_name.setText(jsonObj.getString("last_name"));
                               //last_name.setFocusableInTouchMode(false);
                           }



                            if(jsonObj.getString("dob").equalsIgnoreCase("")
                                    || jsonObj.getString("dob").equalsIgnoreCase("null")){
                                dob.setText(null);
                            }else {
                                try{

                                    String strCurrentDate = jsonObj.getString("dob");
                                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                    Date newDate = format.parse(strCurrentDate);

                                    format = new SimpleDateFormat("dd-MM-yyyy");

                                    dob.setText(format.format(newDate));


                                    Calendar doba = Calendar.getInstance();
                                    Calendar today = Calendar.getInstance();

                                    String[] items1 = dob.getText().toString().trim().split("-");
                                    int date1= Integer.parseInt(items1[0]);
                                    int month= Integer.parseInt(items1[1]);
                                    int year= Integer.parseInt(items1[2]);

                                    Log.d("datenew",year+"-"+month+"-"+date1);


                                    doba.set(year, month, date1);

                                    int age = today.get(Calendar.YEAR) - doba.get(Calendar.YEAR);

                                    if (today.get(Calendar.DAY_OF_YEAR) < doba.get(Calendar.DAY_OF_YEAR)){
                                        age--;
                                    }

                                    Integer ageInt = new Integer(age);
                                    String ageS = ageInt.toString();







                                    if (ageInt <= 18) {
                                        guar_detail.setVisibility(View.VISIBLE);
                                    } else {
                                        guar_detail.setVisibility(View.GONE);
                                    }


                                }catch (Exception e){
                                    dob.setText(jsonObj.getString("dob"));



                                    Calendar doba = Calendar.getInstance();
                                    Calendar today = Calendar.getInstance();

                                    String[] items1 = jsonObj.getString("dob").split("-");
                                    int date1= Integer.parseInt(items1[0]);
                                    int month= Integer.parseInt(items1[1]);
                                    int year= Integer.parseInt(items1[2]);

                                    Log.d("datenew",year+"-"+month+"-"+date1);


                                    doba.set(year, month, date1);

                                    int age = today.get(Calendar.YEAR) - doba.get(Calendar.YEAR);

                                    if (today.get(Calendar.DAY_OF_YEAR) < doba.get(Calendar.DAY_OF_YEAR)){
                                        age--;
                                    }

                                    Integer ageInt = new Integer(age);
                                    String ageS = ageInt.toString();







                                    if (ageInt <= 18) {
                                        guar_detail.setVisibility(View.VISIBLE);
                                    } else {
                                        guar_detail.setVisibility(View.GONE);
                                    }

                                }

                            }



                           // first_name.setFocusableInTouchMode(false);


                        }


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
        mRequestQueue.add(mStringRequest);
    }

    private void getBankNameNominee() {
        String url = con.base_url+"/api/admin/get/master/relation";


        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);

                    Log.d("relationresponse",response);
                    JSONArray jsonArr=js.getJSONArray("data");
                    bank_name = new ArrayList<>();
                    bank_nameList = new ArrayList<>();
                    bank_nameList.add(new SpinnerModal("", "Select Family Relation"));
                    bank_name.add("");
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObj = jsonArr.getJSONObject(i);

                        if (!jsonObj.getString("name").equalsIgnoreCase("self")) {
                            if (jsonObj.getString("name").equalsIgnoreCase("Father")||
                                    jsonObj.getString("name").equalsIgnoreCase("Mother")||
                                    jsonObj.getString("name").equalsIgnoreCase("Siblings")) {

                                        bank_nameList.add(new SpinnerModal(jsonObj.getString("id"), jsonObj.getString("name")));
                                        bank_name.add(jsonObj.getString("name"));


                            }

                        }
                    }
                    bank_nameAdapter = new ArrayAdapter<SpinnerModal>(getActivity(), R.layout.spinner_item, bank_nameList);
                    bank_nameAdapter.setDropDownViewResource(R.layout.spinner_item);

                    relation_nominee.setAdapter(bank_nameAdapter);


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
        mRequestQueue.add(mStringRequest);
    }


    private void getNormalNominee() {
        String url = con.base_url+"/api/admin/get/master/relation";


        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);

                    Log.d("relationresponse",response);
                    JSONArray jsonArr=js.getJSONArray("data");
                    bank_name = new ArrayList<>();
                    bank_nameList = new ArrayList<>();
                    bank_nameList.add(new SpinnerModal("", "Select Family Relation"));
                    bank_name.add("");
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObj = jsonArr.getJSONObject(i);

                        if (!jsonObj.getString("name").equalsIgnoreCase("self")) {


                                bank_nameList.add(new SpinnerModal(jsonObj.getString("id"), jsonObj.getString("name")));
                                bank_name.add(jsonObj.getString("name"));




                        }
                    }
                    bank_nameAdapter = new ArrayAdapter<SpinnerModal>(getActivity(), R.layout.spinner_item, bank_nameList);
                    bank_nameAdapter.setDropDownViewResource(R.layout.spinner_item);
                    relation_type_spin.setAdapter(bank_nameAdapter);

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
        mRequestQueue.add(mStringRequest);
    }


    public void setProfileDet() {

        String url = con.base_url+"/api/admin/user";


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







                         employer_id = explrObject.getString("employer_id");
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

    public static boolean isValid(String s) {
        // The given argument to compile() method
        // is regular expression. With the help of
        // regular expression we can validate mobile
        // number.
        // 1) Begins with 0 or 91
        // 2) Then contains 7 or 8 or 9.
        // 3) Then contains 9 digits
        Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}");

        // Pattern class contains matcher() method
        // to find matching between given number
        // and regular expression
        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }
}