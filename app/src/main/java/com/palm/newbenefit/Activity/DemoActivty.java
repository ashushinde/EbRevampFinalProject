package com.palm.newbenefit.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.biometric.BiometricPrompt;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
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
import com.palm.newbenefit.ApiConfig.AnalyticsApplication;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.DatabaseHelper.DBHelper;
import com.palm.newbenefit.Module.Module;
import com.palm.newbenefit.R;
import com.palm.tatarewamp.SslData.NullHostNameVerifier;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class DemoActivty extends AppCompatActivity {

    TextView btn_login;
    EditText mobNo, password;

    ProgressDialog progressDialog = null;
    Constants con;
    String token = null;
    ImageView img_btn_show_hide_password;
    String str_CheckTrueFalse = null;
    TextView forgot_password;
    LinearLayout userauth;
    private RequestQueue requestQueue;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    List<Module> ob = null;
    LinearLayout getquotes;
    private static int CODE_AUTHENTICATION_VERIFICATION = 241;
    String userdataname = null;
    AppCompatCheckBox facelock, pattern;

    String network_hospital = "no",
            claim = "no",
            my_wellness = "no",
            form_center = "no",
            contact_us = "no",
            help = "no",
            enrollment = "no", my_policy = "no"
            ,flex="no";

    String SubmitClaim = "no", IntimateClaim = "no",
            downloadecard="no",
            TrackClaim = "no", PortalClaims = "no", OverallClaim = "no";
    TextView policy;
    String mobile,PAss;
    DBHelper db;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demologin);

        con = new Constants();

      //  AnalyticsApplication.getInstance().trackEvent("Login Screen", "Login Page Displaying", "Login Page Displaying");

        btn_login = findViewById(R.id.btn_login);
        forgot_password = findViewById(R.id.forgot_password);
        getquotes = findViewById(R.id.getquotes);
        policy = findViewById(R.id.policy);
        // handleScreenTrackingAnalytics();

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), ForgetPasswordActivity.class);    //jump to next activity
                startActivity(intent);
                finish();
            }
        });


        policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), PassowrdPolicyActivity.class);    //jump to next activity
                startActivity(intent);

            }
        });


        mobNo = findViewById(R.id.mobNo);
        password = findViewById(R.id.password);
        userauth = findViewById(R.id.userauth);

        SharedPreferences prefs1 = getSharedPreferences(getApplicationContext().getPackageName(), Context.MODE_PRIVATE);


        token = prefs1.getString("api_token", null);
        String user_id =null;
         mobile = prefs1.getString("mobile", null);
         PAss = prefs1.getString("password", null);


        String codevalue = prefs1.getString("codevalue", null);

        userdataname = prefs1.getString("UserDataName", null);
        con = new Constants();
        token = con.generteToken();



try{
    if (!mobile.equalsIgnoreCase("null")||
            !mobile.equalsIgnoreCase("")) {

        LoginScreen(mobile,PAss,codevalue);

          /*  Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();*/
        userauth.setVisibility(View.GONE);
    } else {
        userauth.setVisibility(View.GONE);
    }
}catch (Exception e){

}



        img_btn_show_hide_password = findViewById(R.id.img_btn_show_hide_password);
        str_CheckTrueFalse = null;
        password.setTransformationMethod(new PasswordTransformationMethod());
        img_btn_show_hide_password.setImageResource(R.drawable.ic_visibility_off_black_24dp);

        img_btn_show_hide_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isChecked()) {
                    str_CheckTrueFalse = null;
                    password.setTransformationMethod(new PasswordTransformationMethod());
                    img_btn_show_hide_password.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                } else {
                    password.setTransformationMethod(null);
                    img_btn_show_hide_password.setImageResource(R.drawable.ic_eye);
                    str_CheckTrueFalse = "checked";
                }
            }
        });
//        if (user_id != null) {
//            biometricPrompt.authenticate(promptInfo);
//        }

//        if (user_id != null) {
//
//            if(userdataname.equalsIgnoreCase("Customer")){
//                Intent intent = new Intent(getApplicationContext(), CustomerMainActivity.class);
//                startActivity(intent);
//                finish();
//            }else if(userdataname.equalsIgnoreCase("Sales")||userdataname.equalsIgnoreCase("Broker")){
//                Intent intent = new Intent(getApplicationContext(), SalesMainActivity.class);
//                startActivity(intent);
//                finish();
//            }else {
//
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//
//        }
        btn_login.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable()) {


                    int count = 0;





/*
                    if(Patterns.EMAIL_ADDRESS.matcher(mobNo.getText().toString().trim()).matches()){
                        mobNo.setError(null);
                    }else {
                        mobNo.setError("Invalid Email");
                        ++count;
                    }*/


                    if (mobNo.getText().toString().trim().length() == 0) {
                        ++count;
                        mobNo.setError("Enter Email/Code/Mobile");
                    } else {
                        mobNo.setError(null);
                    }


                    if (password.getText().toString().trim().length() < 8) {
                        ++count;
                        password.setError("The password Must Be At Least 8 Characters.");
                    } else {
                        password.setError(null);
                    }


                    if (count == 0) {
                        progressDialog = ProgressDialog.show(DemoActivty.this, "",
                                "Saving. Please wait...", true);


                       // RequestQueue rq = Volley.newRequestQueue(DemoActivty.this);
                        RequestQueue rq = Volley.newRequestQueue(DemoActivty.this,new HurlStack(null, getSocketFactory()));

                        // RequestQueue rq = Volley.newRequestQueue(DemoActivty.this);
                        // RequestQueue rq = Volley.newRequestQueue(DemoActivty.this,
                        //  new HurlStack(null, getSocketFactory()));
                        // RequestQueue rq = Volley.newRequestQueue(DemoActivty.this,new HurlStack(null, getSocketFactory()));
                        rq.getCache().clear();
                        String url = con.base_url + "/api/admin/login";

                        StringRequest smr = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        try {
                                            Log.d("loginresponse", response);
                                            progressDialog.dismiss();
                                            JSONObject js = new JSONObject(response);
                                            String status = String.valueOf(js.getBoolean("status"));
                                            String api_token = js.getString("api_token");
                                            String message = js.getString("message");
                                            String type = js.getString("type");


                                           // AnalyticsApplication.getInstance().trackEvent("LoginButtonClick", response, "Login Sucessfull");


                                            if (status.equalsIgnoreCase("true")) {

                                               /*  String tokendata = js.getString("api_token");

                                                 GetEmployeeId(tokendata);*/


                                                if (message.equalsIgnoreCase("Please Change Password!")) {


                                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DemoActivty.this);
                                                    alertDialogBuilder.setTitle("Password Reset Required");
                                                    alertDialogBuilder.setMessage("As per policy, password change is mandatory at the time of first login");
                                                    alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface arg0, int arg1) {

                                                            SharedPreferences prefs = getSharedPreferences(getApplicationContext().getPackageName(), Context.MODE_PRIVATE);
                                                            SharedPreferences.Editor editor = prefs.edit();
                                                            try {
                                                                editor.putString("user_id", js.getString("api_token"));
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }
                                                            editor.apply();
                                                            Intent intent = new Intent(getApplicationContext(), QuestionsAnsActivity.class);
                                                            startActivity(intent);

                                                        }
                                                    });
                                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                                    alertDialog.show();

                                                } else {

                                                    SharedPreferences prefs = getSharedPreferences(getApplicationContext().getPackageName(), Context.MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = prefs.edit();
                                                    editor.putString("mobile", mobNo.getText().toString());
                                                    editor.putString("password", password.getText().toString());
                                                    editor.apply();

                                                    String tokendata = js.getString("api_token");

                                                    GetEmployeeId(tokendata);

                                                }


                                            } else if (message.equalsIgnoreCase("Password Expired ! Please Reset Password")) {
                                                SharedPreferences prefs = getSharedPreferences(getApplicationContext().getPackageName(), Context.MODE_PRIVATE);
                                                SharedPreferences.Editor editor = prefs.edit();
                                                editor.putString("user_id", js.getString("api_token"));
                                                editor.apply();
                                                Intent intent = new Intent(getApplicationContext(), ChangePassforceActivity.class);
                                                startActivity(intent);

                                            } else {


                                                new AlertDialog.Builder(DemoActivty.this)
                                                        .setTitle("Error")
                                                        .setMessage(message)
                                                        .setIcon(android.R.drawable.btn_dialog)
                                                        .setNegativeButton(android.R.string.ok, null).show();

                                            }


                                        } catch (Exception e) {


                                            new AlertDialog.Builder(DemoActivty.this)
                                                    .setTitle("Error")
                                                    .setMessage("The Email Must Be A Valid Email Address.")
                                                    .setIcon(android.R.drawable.btn_dialog)
                                                    .setNegativeButton(android.R.string.ok, null).show();

                                            progressDialog.dismiss();
                                           /* new AlertDialog.Builder(DemoActivty.this)
                                                    .setTitle("Error?")
                                                    .setMessage("Due to multiple wrong login attempts . You are blocked for 15 minutes.")
                                                    .setIcon(android.R.drawable.btn_dialog)
                                                    .setNegativeButton(android.R.string.ok, null).show();
*/
                                            Log.d("response", e.toString());
                                            e.printStackTrace();

                                           // AnalyticsApplication.getInstance().trackEvent("LoginButtonClick", "The email must be a valid email address.", "Login Button Error");


                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("firstapiresponse", error.toString());
                                progressDialog.dismiss();
                               // AnalyticsApplication.getInstance().trackEvent("LoginButtonClick", error.toString(), "Login Button Error");

                            }
                        }) {


                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> headers = new HashMap<>();

                                return headers;
                            }

                        };




                        HashMap<String, String> params = new HashMap<>();
                        String regex = "[0-9]+";
                        if (Patterns.EMAIL_ADDRESS.matcher(mobNo.getText().toString().trim()).matches()) {
                            SharedPreferences prefs = getSharedPreferences(getApplicationContext().getPackageName(), Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("codevalue","email");
                            editor.apply();
                            params.put("email", mobNo.getText().toString().trim());
                            Log.d("email", mobNo.getText().toString().trim());
                        } else if (mobNo.getText().toString().trim().isEmpty() ||
                                !isValid(mobNo.getText().toString().trim()) || mobNo.getText().toString().length() < 10 ||
                                mobNo.getText().toString().trim().length() > 13) {
                            SharedPreferences prefs = getSharedPreferences(getApplicationContext().getPackageName(), Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("codevalue","code");
                            editor.apply();

                            params.put("code", mobNo.getText().toString().trim());
                            Log.d("code", mobNo.getText().toString().trim());

                        } else {
                            SharedPreferences prefs = getSharedPreferences(getApplicationContext().getPackageName(), Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("codevalue","mobile");
                            editor.apply();
                            params.put("mobile", mobNo.getText().toString().trim());
                            Log.d("mobile", mobNo.getText().toString().trim());
                        }

                        params.put("password", password.getText().toString().trim());


                        smr.setParams(params);
                        rq.add(smr);


                    }
                } else {
                    new AlertDialog.Builder(DemoActivty.this)
                            .setTitle("Error")
                            .setMessage("Please Check Your Internet Connection")
                            .setIcon(android.R.drawable.btn_dialog)
                            .setNegativeButton(android.R.string.ok, null).show();
                }

            }
        });


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


    void LoginScreen(String mobile,String Password,String codevalue){
        progressDialog = ProgressDialog.show(DemoActivty.this, "",
                "Saving. Please wait...", true);


        RequestQueue rq = Volley.newRequestQueue(DemoActivty.this,new HurlStack(null, getSocketFactory()));
        // RequestQueue rq = Volley.newRequestQueue(DemoActivty.this);
        // RequestQueue rq = Volley.newRequestQueue(DemoActivty.this,
        //  new HurlStack(null, getSocketFactory()));
        // RequestQueue rq = Volley.newRequestQueue(DemoActivty.this,new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        String url = con.base_url + "/api/admin/login";

        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.d("loginresponse", response);
                            progressDialog.dismiss();
                            JSONObject js = new JSONObject(response);
                            String status = String.valueOf(js.getBoolean("status"));
                            String api_token = js.getString("api_token");
                            String message = js.getString("message");
                            String type = js.getString("type");


                         //   AnalyticsApplication.getInstance().trackEvent("LoginButtonClick", response, "Login Sucessfull");


                            if (status.equalsIgnoreCase("true")) {

                                               /*  String tokendata = js.getString("api_token");

                                                 GetEmployeeId(tokendata);*/


                                if (message.equalsIgnoreCase("Please Change Password!")) {


                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DemoActivty.this);
                                    alertDialogBuilder.setTitle("Password Reset Required");
                                    alertDialogBuilder.setMessage("As per policy, password change is mandatory at the time of first login");
                                    alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {

                                            SharedPreferences prefs = getSharedPreferences(getApplicationContext().getPackageName(), Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = prefs.edit();
                                            try {
                                                editor.putString("user_id", js.getString("api_token"));
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }




                                            Intent intent = new Intent(getApplicationContext(), QuestionsAnsActivity.class);
                                            startActivity(intent);

                                        }
                                    });
                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    alertDialog.show();

                                } else {
                                    SharedPreferences prefs = getSharedPreferences(getApplicationContext().getPackageName(), Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = prefs.edit();
                                    editor.putString("mobile", mobile);
                                    editor.putString("password", PAss);
                                    editor.apply();
                                    String tokendata = js.getString("api_token");

                                    GetEmployeeId(tokendata);

                                }


                            } else if (message.equalsIgnoreCase("Password Expired ! Please Reset Password")) {
                                SharedPreferences prefs = getSharedPreferences(getApplicationContext().getPackageName(), Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("user_id", js.getString("api_token"));
                                editor.apply();
                                Intent intent = new Intent(getApplicationContext(), ChangePassforceActivity.class);
                                startActivity(intent);

                            } else {


                                new AlertDialog.Builder(DemoActivty.this)
                                        .setTitle("Error")
                                        .setMessage(message)
                                        .setIcon(android.R.drawable.btn_dialog)
                                        .setNegativeButton(android.R.string.ok, null).show();

                            }


                        } catch (Exception e) {


                            new AlertDialog.Builder(DemoActivty.this)
                                    .setTitle("Error")
                                    .setMessage("The Email Must Be A Valid Email Address.")
                                    .setIcon(android.R.drawable.btn_dialog)
                                    .setNegativeButton(android.R.string.ok, null).show();

                            progressDialog.dismiss();
                                           /* new AlertDialog.Builder(DemoActivty.this)
                                                    .setTitle("Error?")
                                                    .setMessage("Due to multiple wrong login attempts . You are blocked for 15 minutes.")
                                                    .setIcon(android.R.drawable.btn_dialog)
                                                    .setNegativeButton(android.R.string.ok, null).show();
*/
                            Log.d("response", e.toString());
                            e.printStackTrace();

                            //AnalyticsApplication.getInstance().trackEvent("LoginButtonClick", "The email must be a valid email address.", "Login Button Error");


                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("firstapiresponse", error.toString());
                progressDialog.dismiss();
               // AnalyticsApplication.getInstance().trackEvent("LoginButtonClick", error.toString(), "Login Button Error");

            }
        }) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();

                return headers;
            }

        };




        HashMap<String, String> params = new HashMap<>();


            params.put(codevalue, mobile);


        params.put("password", Password);


        smr.setParams(params);
        rq.add(smr);


    }


    void LoginData(String mytoken ,String userid){
        RequestQueue rq = Volley.newRequestQueue(DemoActivty.this,
                new HurlStack(null, getSocketFactory()));
      /*  RequestQueue rq = Volley.newRequestQueue(DemoActivty.this,
                new HurlStack(null, getSocketFactory()));*/
        rq.getCache().clear();
        String url = con.base_url+"/api/admin/login";

        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.d("finalloginresponse",response);
                            progressDialog.dismiss();
                            JSONObject js = new JSONObject(response);
                            String status = String.valueOf(js.getBoolean("status"));
                            String api_token = js.getString("api_token");
                            String message = js.getString("message");
                            String type = js.getString("type");



                            JSONArray modules=js.getJSONArray("modules");


                            for (int i = 0; i < modules.length(); i++) {
                                JSONObject explrObject = modules.getJSONObject(i);
                                String  name = explrObject.getString("name");

                                if(name.equalsIgnoreCase("Network Hospital")){
                                    network_hospital="yes";

                                }else  if(name.equalsIgnoreCase("Form Center")){
                                    form_center="yes";

                                }else  if(name.equalsIgnoreCase("View Flex Benefits")){
                                    flex="yes";



                                }else  if(name.equalsIgnoreCase("Help")){
                                help="yes";

                                }else  if(name.equalsIgnoreCase("Enrolment")){
                                    enrollment="yes";

                                }else  if(name.equalsIgnoreCase("Claims")){
                                    claim="yes";

                                }else  if(name.equalsIgnoreCase("Contact Us")){
                                    contact_us="yes";

                                }else  if(name.equalsIgnoreCase("My Policy")){
                                    my_policy="yes";

                                }else  if(name.equalsIgnoreCase("Claims")) {
                                    claim = "yes";

                                }else  if(name.equalsIgnoreCase("My Wellness")) {
                                        my_wellness = "yes";


                                }else  if(name.equalsIgnoreCase("Submit Claim")) {
                                   SubmitClaim = "yes";

                                }else  if(name.equalsIgnoreCase("Intimate Claim")) {
                                    IntimateClaim = "yes";

                                }else  if(name.equalsIgnoreCase("Track Claim")) {
                                    TrackClaim = "yes";

                                }else  if(name.equalsIgnoreCase("Portal Claims")) {
                                    PortalClaims = "yes";

                                }else  if(name.equalsIgnoreCase("Overall Claims")) {
                                    OverallClaim = "yes";

                                }
                                else  if(name.equalsIgnoreCase("Download E-Card")) {
                                    downloadecard = "yes";

                                }






                            }



                          //  AnalyticsApplication.getInstance().trackEvent("LoginButtonClick", response, "Login Sucessfull");




                            if(status.equalsIgnoreCase("true")){

                                SharedPreferences prefs = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("api_token", api_token);
                                // editor.putString("modules", array.toString());
                                editor.putString("user_id", userid);
                                editor.putString("network_hospital", network_hospital);
                                editor.putString("help", help);
                                editor.putString("enrollment", enrollment);
                                editor.putString("claim", claim);
                                editor.putString("contact_us", contact_us);
                                editor.putString("my_policy", my_policy);
                                editor.putString("form_center", form_center);
                                editor.putString("my_wellness", my_wellness);

                                editor.putString("SubmitClaim", SubmitClaim);
                                editor.putString("IntimateClaim", IntimateClaim);
                                editor.putString("TrackClaim", TrackClaim);
                                editor.putString("PortalClaims", PortalClaims);
                                editor.putString("OverallClaim", OverallClaim);
                                editor.putString("flex", flex);
                                editor.putString("downloadecard", downloadecard);



//                                if (mobile != null) {
//                                    editor.putString("mobile", mobile);
//                                    editor.putString("password", PAss);
//
//                                }else {
//                                    editor.putString("mobile", mobNo.getText().toString().trim());
//                                    editor.putString("password", password.getText().toString().trim());
//
//                                }
//






                                  editor.apply();
                                  Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                  startActivity(intent);
                                                    finish();





                            }else if(message.equalsIgnoreCase("Password Expired ! Please Reset Password")){
                                SharedPreferences prefs = getSharedPreferences(getApplicationContext().getPackageName(), Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("user_id", js.getString("api_token"));
                                editor.apply();
                                Intent intent = new Intent(getApplicationContext(), ChangePassforceActivity.class);
                                startActivity(intent);

                            } else {
                                new AlertDialog.Builder(DemoActivty.this)
                                        .setTitle("Error")
                                        .setMessage(message)
                                        .setIcon(android.R.drawable.btn_dialog)
                                        .setNegativeButton(android.R.string.ok, null).show();

                            }





                        } catch (Exception e)
                        {
                            progressDialog.dismiss();
                            new AlertDialog.Builder(DemoActivty.this)
                                    .setTitle("Error")
                                    .setMessage("Due to multiple wrong login attempts . You are blocked for 15 minutes.")
                                    .setIcon(android.R.drawable.btn_dialog)
                                    .setNegativeButton(android.R.string.ok, null).show();

                            Log.d("response",e.toString());
                            e.printStackTrace();

                            //AnalyticsApplication.getInstance().trackEvent("LoginButtonClick", "The email must be a valid email address.", "Login Button Error");



                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response",error.toString());
                progressDialog.dismiss();
               // AnalyticsApplication.getInstance().trackEvent("LoginButtonClick", error.toString(), "Login Button Error");

            }
        })


        {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();

                return headers;
            }

        };





        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", userid);
        params.put("switch", "1");
        params.put("master_user_type_id","5");


        smr.setParams(params);
        rq.add(smr);






    }


    void GetEmployeeId(String api_token){
        String url = con.base_url+"/api/admin/user";

     RequestQueue mRequestQueue = Volley.newRequestQueue(DemoActivty.this);
 /*     RequestQueue mRequestQueue = Volley.newRequestQueue(DemoActivty.this,
              new HurlStack(null, getSocketFactory()));*/
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);

                 Log.d("mydata",response);
                    JSONArray jsonObj=js.getJSONArray("data");

                    for (int i = 0; i < jsonObj.length(); i++) {
                        JSONObject explrObject = jsonObj.getJSONObject(0);






                       String  user_id = explrObject.getString("id");



                        LoginData(api_token,user_id);


                       }



                } catch (JSONException e) {
                    e.printStackTrace();
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
                headers.put("Authorization", "Bearer " + api_token);
                return headers;
            }
        };
        mRequestQueue.add(mStringRequest);
    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode==CODE_AUTHENTICATION_VERIFICATION)
        {
            if(userdataname.equalsIgnoreCase("Customer")){
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }else if(userdataname.equalsIgnoreCase("Sales")||userdataname.equalsIgnoreCase("Broker")){
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
                finish();
            }else {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
            Toast.makeText(this, "Success: Verified user's identity", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Failure: Unable to verify user's identity", Toast.LENGTH_SHORT).show();
        }
    }
*/



    public static String encrypt(String Data, Key key, IvParameterSpec ivSpec) throws Exception {

        Cipher c = Cipher.getInstance("AES/CBC/ZeroBytePadding");
        c.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        byte[] encVal = c.doFinal(Data.getBytes());
        String encryptedValue = Base64.encodeToString(encVal, Base64.NO_WRAP);
        return encryptedValue;
    }




    private boolean isChecked() {
        boolean checked = false;

        if (str_CheckTrueFalse != null) {
            checked = true;
        }

        return checked;
    }




    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
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


    public static String encoderfun(byte[] decval) {
        String conVal= Base64.encodeToString(decval,Base64.DEFAULT);
        return conVal;
    }
}

