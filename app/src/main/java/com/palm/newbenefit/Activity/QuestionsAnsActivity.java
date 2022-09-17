package com.palm.newbenefit.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.Module.SpinnerModal;
import com.palm.newbenefit.R;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class QuestionsAnsActivity extends AppCompatActivity {

    EditText ans_one,ans_two;
    Spinner question_one,question_two;
    SharedPreferences prefs;
    ArrayList<String> bank_name = null;
    ArrayList<SpinnerModal> bank_nameList = null;
    ArrayAdapter<SpinnerModal> bank_nameAdapter = null;


    ArrayList<String> bank_name2 = null;
    ArrayList<SpinnerModal> bank_nameList2 = null;
    ArrayAdapter<SpinnerModal> bank_nameAdapter2 = null;

    Constants con;
    String token;
    ProgressDialog progressDialog = null;
    Button submit;
    ImageView info_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_ans);

        prefs = getSharedPreferences(getApplicationContext().getPackageName(), Context.MODE_PRIVATE);


        token = prefs.getString("api_token", null);
        con = new Constants();
        ans_one=findViewById(R.id.ans_one);
        ans_two=findViewById(R.id.ans_two);

        question_one=findViewById(R.id.question_one);
        question_two=findViewById(R.id.question_two);
        submit=findViewById(R.id.submit);
        info_text=findViewById(R.id.info_text);


        info_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              finish();


            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int count = 0;


                SpinnerModal ques1 = (SpinnerModal) question_one.getSelectedItem();
                SpinnerModal ques2 = (SpinnerModal) question_two.getSelectedItem();

                if (ques1.getSelKey().trim().equalsIgnoreCase("")) {
                    ++count;
                    new AlertDialog.Builder(QuestionsAnsActivity.this)
                            .setTitle("Error")
                            .setMessage("Please Select  Question")
                            .setIcon(android.R.drawable.btn_dialog)
                            .setNegativeButton(android.R.string.ok, null).show();
                    return;
                }

                if (ques2.getSelKey().trim().equalsIgnoreCase("")) {
                    ++count;
                    new AlertDialog.Builder(QuestionsAnsActivity.this)
                            .setTitle("Error")
                            .setMessage("Please Select  Question")
                            .setIcon(android.R.drawable.btn_dialog)
                            .setNegativeButton(android.R.string.ok, null).show();
                    return;
                }

                if (ans_one.getText().toString().trim().length() == 0) {
                    ++count;
                    ans_one.setError("Answer Is Required");
                } else {
                    ans_one.setError(null);
                }

                if (ans_two.getText().toString().trim().length() == 0) {
                    ++count;
                    ans_two.setError("Answer Is Required");
                } else {
                    ans_two.setError(null);
                }


                if(count==0){


                    submitdata();

                }


            }
        });

        getBankName1();


    }


    void submitdata(){




        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(QuestionsAnsActivity.this);

        alertDialogBuilder.setMessage("Security questions set successfully.");
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                SharedPreferences prefs = getSharedPreferences(getApplicationContext().getPackageName(), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();

                 editor.putString("user_id", token);

                editor.apply();
                Intent intent = new Intent(getApplicationContext(), ChangePassforceActivity.class);
                startActivity(intent);
                finish();

            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();



       /* progressDialog = ProgressDialog.show(QuestionsAnsActivity.this, "",
                "Saving. Please wait...", true);

        RequestQueue rq = Volley.newRequestQueue(QuestionsAnsActivity.this, new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();



        String url = con.base_url + "/api/employee/enroll/family/members";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progressDialog.dismiss();


                            Log.d("response_dependent", response);
                            JSONObject jsonObject = new JSONObject(response);
                            String status = String.valueOf(jsonObject.getString("status"));
                            String message = jsonObject.getString("message");


                            if (status.equalsIgnoreCase("true")) {
                                new AlertDialog.Builder(QuestionsAnsActivity.this)
                                        .setTitle("Success")
                                        .setMessage(message)
                                        .setIcon(R.drawable.checkmark)
                                        .setNegativeButton(android.R.string.ok, null).show();

                            } else {
                                new AlertDialog.Builder(QuestionsAnsActivity.this)
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

        SpinnerModalFamilyData que1 = (SpinnerModalFamilyData) question_one.getSelectedItem();
        SpinnerModalFamilyData que2 = (SpinnerModalFamilyData) question_one.getSelectedItem();




        HashMap<String, String> params = new HashMap<>();

        params.put("member_relation_id", que1.getSelValue());
        params.put("member_gender", que2.getSelValue());
        params.put("member_firstname", ans_one.getText().toString().trim());
        params.put("member_firstname", ans_two.getText().toString().trim());


        smr.setParams(params);
        rq.add(smr);


*/


}


    private void getBankName1() {

        String url = con.base_url + "/api/admin/get/security-questions";

        RequestQueue mRequestQueue = Volley.newRequestQueue(QuestionsAnsActivity.this,
                new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    Log.d("banknameModule", response);
                    JSONObject js = new JSONObject(response);
                    JSONArray jsonArr = js.getJSONArray("data");


                    bank_name = new ArrayList<>();
                    bank_nameList = new ArrayList<>();
                    bank_nameList.add(new SpinnerModal("", "Select Questions"));
                    bank_name.add("");

                    bank_name2 = new ArrayList<>();
                    bank_nameList2 = new ArrayList<>();
                    bank_nameList2.add(new SpinnerModal("", "Select Questions"));
                    bank_name2.add("");
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObj = jsonArr.getJSONObject(i);

                        int id= Integer.parseInt(jsonObj.getString("id"));

                        if(id<7){
                            bank_nameList.add(new SpinnerModal(jsonObj.getString("id"),
                                    jsonObj.getString("question")));
                            bank_name.add(jsonObj.getString("question"));
                        }else {
                            bank_nameList2.add(new SpinnerModal(jsonObj.getString("id"),
                                    jsonObj.getString("question")));
                            bank_name2.add(jsonObj.getString("question"));
                        }


                    }
                    bank_nameAdapter = new ArrayAdapter<SpinnerModal>(QuestionsAnsActivity.this, R.layout.spinner_item, bank_nameList);
                    bank_nameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    question_one.setAdapter(bank_nameAdapter);


                    bank_nameAdapter2 = new ArrayAdapter<SpinnerModal>(QuestionsAnsActivity.this, R.layout.spinner_item, bank_nameList2);
                    bank_nameAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    question_two.setAdapter(bank_nameAdapter2);




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


        mRequestQueue.add(mStringRequest);
    }





    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public SSLSocketFactory getSocketFactory() {

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

        return null;
    }

}