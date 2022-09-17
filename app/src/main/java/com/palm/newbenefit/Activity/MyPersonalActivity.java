package com.palm.newbenefit.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.palm.newbenefit.ApiConfig.Constants;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class MyPersonalActivity extends AppCompatActivity {
    EditText emp_name, dob, doj, email, alternate_email,
            contact_number, emg_contact_no, emg_contact_person, designation,grade;

    TextView emp_detail_save;

    String user_id;
    Constants con;
    ProgressDialog progressDialog = null;
    String mobileNumber = null;
    String token = null;
    String emp_id = null;
    LinearLayout designation_linear;
    String policy_no;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Button send;

    LinearLayout email_lin,mob,em_con,em_mob,grade_data;
    LinearLayout view_more,hidden;
    TextView view_data;
    ImageView back;
    EditText address,city,pincode,state,companyname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_personal);
        con = new Constants();
        SharedPreferences prefs = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);

        token = prefs.getString("api_token", null);

        user_id = prefs.getString("user_id", null);

        emp_name = findViewById(R.id.emp_name);
        dob = findViewById(R.id.dob);
        doj = findViewById(R.id.doj);
        email =findViewById(R.id.email);
        alternate_email = findViewById(R.id.alternate_email);
        contact_number =findViewById(R.id.contact_number);
        emg_contact_no = findViewById(R.id.emg_contact_no);
        designation_linear= findViewById(R.id.designation_linear);
        address = findViewById(R.id.address);
        pincode = findViewById(R.id.pincode);
        city = findViewById(R.id.city);
        state = findViewById(R.id.state);
        companyname = findViewById(R.id.companyname);

        emg_contact_person = findViewById(R.id.emg_contact_person);
        designation = findViewById(R.id.designation);
        emp_detail_save = findViewById(R.id.emp_detail_save);
        send = findViewById(R.id.send);
        view_data = findViewById(R.id.view_data);
        grade= findViewById(R.id.grade);
        email_lin = findViewById(R.id.email_lin);
        mob = findViewById(R.id.mob);
        em_con = findViewById(R.id.em_con);
        em_mob = findViewById(R.id.em_mob);
        view_more= findViewById(R.id.view_more);
        hidden= findViewById(R.id.hidden);
        back= findViewById(R.id.back);
        grade_data= findViewById(R.id.grade_data);

        address.setLongClickable(false);
        pincode.setLongClickable(false);
        city.setLongClickable(false);
        designation.setLongClickable(false);
        companyname.setLongClickable(false);
        emp_name.setLongClickable(false);

        view_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hidden.getVisibility()==View.GONE){
                    view_data.setText("View Less");
                    hidden.setVisibility(View.VISIBLE);
                }else {
                    view_data.setText("View More");
                    hidden.setVisibility(View.GONE);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              finish();
            }
        });


        if (isNetworkAvailable()) {

            //  MyPersonalActivity.this;


            setProfileDet();

        }else {
            new AlertDialog.Builder(MyPersonalActivity.this)
                    .setTitle("Error?")
                    .setMessage("Please Check Your Internet Connection")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
        }





        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isNetworkAvailable()) {

                    emp_detail_save.setVisibility(View.VISIBLE);
                    alternate_email.setFocusableInTouchMode(true);
                    emg_contact_no.setFocusableInTouchMode(true);
                    emg_contact_person.setFocusableInTouchMode(true);
                    contact_number.setFocusableInTouchMode(true);


                    alternate_email.setEnabled(true);
                    emg_contact_no.setEnabled(true);
                    emg_contact_person.setEnabled(true);
                    contact_number.setEnabled(true);

                    email_lin.setBackgroundResource(R.drawable.edit_back);
                    mob.setBackgroundResource(R.drawable.edit_back);
                    em_con.setBackgroundResource(R.drawable.edit_back);
                    em_mob.setBackgroundResource(R.drawable.edit_back);


                    send.setVisibility(View.GONE);
                }else {
                    new AlertDialog.Builder(MyPersonalActivity.this)
                            .setTitle("Error?")
                            .setMessage("Please Check Your Internet Connection")
                            .setIcon(android.R.drawable.btn_dialog)
                            .setNegativeButton(android.R.string.ok, null).show();
                }



            }
        });





        emp_detail_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isNetworkAvailable()) {

                    SendData();

                }else {
                    new AlertDialog.Builder(MyPersonalActivity.this)
                            .setTitle("Error?")
                            .setMessage("Please Check Your Internet Connection")
                            .setIcon(android.R.drawable.btn_dialog)
                            .setNegativeButton(android.R.string.ok, null).show();
                }



            }
        });




    }


    public void setProfileDet() {

        String url = con.base_url+"/api/admin/user";
        RequestQueue mRequestQueue = Volley.newRequestQueue(MyPersonalActivity.this,
                new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);

                    Log.d("response",response);
                    JSONArray  jsonObj=js.getJSONArray("data");

                    for (int i = 0; i < jsonObj.length(); i++) {
                        JSONObject explrObject = jsonObj.getJSONObject(i);







                        String emp_firstname = explrObject.getString("name");
                        String bdate = explrObject.getString("dob");
                        //  String last_name = explrObject.getString("emp_lastname");
                        String dojg = explrObject.getString("doj");
                        String emaild = explrObject.getString("email");
                        String alt_email = explrObject.getString("alternate_email");
                        String mob_no = explrObject.getString("mobile_no");
                        String emg_cno = explrObject.getString("emergency_contact");
                        String emp_emg_cont_name = explrObject.getString("emergency_contact_name");
                        String designation_name = explrObject.getString("employee_desination");
                        String company_name = explrObject.getString("company_name");
                        String states = explrObject.getString("state");
                        String citys = explrObject.getString("city");
                        String pincodes = explrObject.getString("pincode");
                        String addresss = explrObject.getString("address");
                        String grades = explrObject.getString("employee_grade");

                        if (!company_name.equalsIgnoreCase("null")) {
                            companyname.setText(company_name );
                        }


                        if (!states.equalsIgnoreCase("null")) {
                            state.setText(states );
                        }

                        if (!citys.equalsIgnoreCase("null")) {
                            city.setText(citys );
                        }


                        if (!addresss.equalsIgnoreCase("null")) {
                            address.setText(addresss );
                        }

                        if (!pincodes.equalsIgnoreCase("null")) {
                            pincode.setText(pincodes );
                        }
                        if (emp_firstname != "null" || !emp_firstname.isEmpty()) {
                            emp_name.setText(emp_firstname );
                        }


                               /* if (last_name != "null" && !last_name.isEmpty()) {
                                    contact_person.setText(last_name);
                                }
*/
                        if (bdate != "null" || !bdate.isEmpty()) {



                            try{

                                String strCurrentDate = bdate;
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                Date newDate = format.parse(strCurrentDate);

                                format = new SimpleDateFormat("dd-MM-yyyy");

                                dob.setText(format.format(newDate));
                            }catch (Exception e){
                                dob.setText(bdate);
                            }

                        }


                        if (dojg != "null" || !dojg.isEmpty()) {


                            try{

                                String strCurrentDate = dojg;
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                Date newDate = format.parse(strCurrentDate);

                                format = new SimpleDateFormat("dd-MM-yyyy");

                                doj.setText(format.format(newDate));
                            }catch (Exception e){
                                doj.setText(dojg);
                            }
                        }




                        if(grades.equalsIgnoreCase("null"))
                        {
                            grade.setText(null);
                            grade_data.setVisibility(View.GONE);
                        }else {
                            grade.setText(grades);
                            grade_data.setVisibility(View.VISIBLE);
                        }

                        if (  !emg_cno.isEmpty()|| !emg_cno.equalsIgnoreCase("false")
                                ||!emg_cno.equalsIgnoreCase("null")) {
                            emg_contact_no.setText(emg_cno);
                        }


                        if((emaild.equalsIgnoreCase("null"))
                                ||(emaild.equalsIgnoreCase("false")))
                        {
                            email.setText(null);
                        }else {
                            email.setText(emaild);
                        }

                        if((alt_email.equalsIgnoreCase("null"))
                                ||(alt_email.equalsIgnoreCase("false")))
                        {
                            alternate_email.setText(null);
                        }else {
                            alternate_email.setText(alt_email);
                        }


                        if(mob_no.equalsIgnoreCase("null")
                                ||mob_no.equalsIgnoreCase("false"))
                        {
                            contact_number.setText(null);
                        }else {
                            contact_number.setText(mob_no);
                        }

                        if(emg_cno.equalsIgnoreCase("null"))
                        {
                            emg_contact_no.setText(null);
                        }else {
                            emg_contact_no.setText(emg_cno);
                        }


                        if(emp_emg_cont_name.equalsIgnoreCase("null"))
                        {
                            emg_contact_person.setText(null);
                        }else {
                            emg_contact_person.setText(emp_emg_cont_name);
                        }

                        if(designation_name.equalsIgnoreCase("null"))
                        {
                            designation_linear.setVisibility(View.GONE);
                            designation.setText(null);
                        }else {
                            designation.setText(designation_name);
                            designation_linear.setVisibility(View.VISIBLE);
                        }



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

    public void SendData() {

        int count = 0;
            if (alternate_email.getText().toString().isEmpty()) {
                alternate_email.setError(null);


            }else {

                if (!alternate_email.getText().toString().matches(emailPattern)) {
                    count++;
                    alternate_email.setError("Invalid Email Address");
                } else {

                    if (alternate_email.getText().toString().equalsIgnoreCase(email.getText().toString())) {
                        count++;
                        alternate_email.setError("Alternate Email Id Should Be Different");
                    } else {
                        alternate_email.setError(null);
                    }


                }
            }





        if (emg_contact_no.getText().toString().isEmpty()) {
            emg_contact_no.setError(null);
        }else {
            if (emg_contact_no.getText().toString().trim().isEmpty() || !isValid(emg_contact_no.getText().toString().trim()) || emg_contact_no.getText().toString().length() < 10 || emg_contact_no.getText().toString().trim().length() > 13) {
                ++count;
                emg_contact_no.setError("Invalid Mobile");
            } else {

                if(emg_contact_no.getText().toString().equalsIgnoreCase(contact_number.getText().toString())){
                    ++count;
                    emg_contact_no.setError("Enter Unique Mobile");
                }else {
                    emg_contact_no.setError(null);
                }

            }
        }









            if(emg_contact_person.getText().toString().equalsIgnoreCase(emp_name.getText().toString())){
                ++count;
                emg_contact_person.setError("Enter Unique Person Name");
            }else {
                emg_contact_person.setError(null);
            }






        if (contact_number.getText().toString().trim().isEmpty() || !isValid(contact_number.getText().toString().trim()) || contact_number.getText().toString().trim().length() < 10 || contact_number.getText().toString().trim().length() > 13) {
            ++count;
            contact_number.setError("Invalid Mobile");
        } else {
            contact_number.setError(null);
        }

        if (count == 0) {


            progressDialog = ProgressDialog.show(MyPersonalActivity.this, "",
                    "Saving. Please wait...", true);
            RequestQueue rq = Volley.newRequestQueue(MyPersonalActivity.this,
                    new HurlStack(null, getSocketFactory()));
            JSONObject param = new JSONObject();

            try {


                if(!emg_contact_person.getText().toString().isEmpty()){
                    param.put("emergency_contact_name", emg_contact_person.getText().toString());

                }


               if(!emg_contact_no.getText().toString().isEmpty()){
                param.put("emergency_contact_number", emg_contact_no.getText().toString());
              }




                if(!alternate_email.getText().toString().isEmpty()){
                    param.put("alternate_email", alternate_email.getText().toString());
                }



                if(!contact_number.getText().toString().isEmpty()){
                    param.put("mobile_no", contact_number.getText().toString());

                }

                param.put("user_type_name", "Employee");

            } catch (JSONException e) {
                e.printStackTrace();
            }



            String url =con.base_url+"/api/admin/update/user";
            JsonObjectRequest smr = new JsonObjectRequest(Request.Method.PATCH, url , param,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try{

                                progressDialog.dismiss();


                                String errorCode = response.getString("message");
                                String status = String.valueOf(response.getString("status"));



                                if(status.equalsIgnoreCase("true")){
                                    new AlertDialog.Builder(MyPersonalActivity.this)
                                            .setTitle("Success")
                                            .setMessage(errorCode)
                                            .setIcon(R.drawable.checkmark)
                                            .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                                                public void onClick(DialogInterface dialog, int whichButton) {

                                                    emp_detail_save.setVisibility(View.GONE);
                                                    alternate_email.setFocusableInTouchMode(false);
                                                    emg_contact_no.setFocusableInTouchMode(false);
                                                    emg_contact_person.setFocusableInTouchMode(false);
                                                    contact_number.setFocusableInTouchMode(false);


                                                    alternate_email.setEnabled(false);
                                                    emg_contact_no.setEnabled(false);
                                                    emg_contact_person.setEnabled(false);
                                                    contact_number.setEnabled(false);



                                                    email_lin.setBackgroundResource(R.drawable.edit_back_grey);
                                                    mob.setBackgroundResource(R.drawable.edit_back_grey);
                                                    em_con.setBackgroundResource(R.drawable.edit_back_grey);
                                                    em_mob.setBackgroundResource(R.drawable.edit_back_grey);


                                                    send.setVisibility(View.VISIBLE);



                                                }
                                            }).show();




                                }else {

                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(MyPersonalActivity.this);
                                    alertDialog.setTitle("Alert");
                                    alertDialog.setMessage(errorCode);
                                    alertDialog.setCancelable(false);
                                    alertDialog.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    });
                                    alertDialog.show();




                                }

                            } catch (Exception e) {
                                progressDialog.dismiss();

                            }

                        }
                    },   new Response.ErrorListener() {
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




            rq.add(smr);
        }

    }

    //    void getprofile(){
//        RequestQueue rq = Volley.newRequestQueue(MyPersonalActivity.this, new HurlStack(null, getSocketFactory()));
//
//        String url = con.base_url+"/get_profile_per_details";
//        SimpleMultiPartRequest smr = new SimpleMultiPartRequest(Request.Method.POST, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//
//
//                            JSONObject jsonObjh = new JSONObject(response);
//
//                            Log.d("allresponse",response);
//
//
//                            JSONObject jsonObj = jsonObjh.getJSONObject("flag");
//                            Log.d("flag",jsonObj.toString());
//
//
//                            String benifit_all_data=jsonObj.getString("benifit_all_data");
//                            String nominee_data=jsonObj.getString("nominee_data");
//                            String subQuery1=jsonObj.getString("subQuery1");
//                            String enrollment_flag=jsonObj.getString("enrollment_flag");
//                            String can_edit=jsonObjh.getString("can_edit");
//                            Log.d("can_edit",can_edit);
//
//
//                            if(((!(benifit_all_data.equalsIgnoreCase(null)) && (!(nominee_data.equalsIgnoreCase(null)) && (subQuery1 .equalsIgnoreCase("Y")))||(enrollment_flag.equalsIgnoreCase("Y"))))){
//                                send.setVisibility(View.GONE);
//                                Log.d("a","1");
//                                //emp_detail_save.setVisibility(View.GONE);
//                            }
//                            else if((!(benifit_all_data.equalsIgnoreCase("null")) || (subQuery1 .equalsIgnoreCase("Y"))||(enrollment_flag.equalsIgnoreCase("Y")))){
//                                send.setVisibility(View.GONE);
//                                Log.d("a","2");
//                                //emp_detail_save.setVisibility(View.GONE);
//                            }
//                            else {
//                                Log.d("a","3");
//                                if (can_edit.equalsIgnoreCase("0")) {
//                                    send.setVisibility(View.GONE);
//                                    Log.d("b","a");
//
//                                }else {
//                                    Log.d("b","b");
//                                    send.setVisibility(View.VISIBLE);
//                                }
//
//                                //hos_add_button.setVisibility(View.VISIBLE);
//                            }
//
//
//
//
//
//                        } catch (Exception e) {
//
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//
//        smr.addStringParam("emp_id", emp_id);
//        smr.addStringParam("policy_no",policy_no);
//
//        Log.d("policy_no",policy_no);
//
//
//
//      /*  smr.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
//                0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//
//        RequestQueue mRequestQueue = Volley.newRequestQueue(MyPersonalActivity.this);
//        mRequestQueue.add(smr);*/
//
//        rq.add(smr);
//    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)MyPersonalActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
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

