package com.palm.newbenefit.Fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.R;
import com.palm.tatarewamp.SslData.NullHostNameVerifier;

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
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeedBackFragment extends Fragment {

EditText edt_type;


    ProgressDialog progressDialog = null;

    Button btn_can;
    Constants con;
    String token;
    ImageView excellent,verygood,good,fair,unhappy;
    String id="0";
    public FeedBackFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_feed_back, container, false);
        SharedPreferences prefs =getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);

        token = prefs.getString("api_token", null);
        edt_type=v.findViewById(R.id.edt_type);


        excellent=v.findViewById(R.id.excellent);
        verygood=v.findViewById(R.id.verygood);
        good=v.findViewById(R.id.good);
        fair=v.findViewById(R.id.fair);
        unhappy=v.findViewById(R.id.unhappy);



        btn_can=v.findViewById(R.id.btn_can);
        con=new Constants();
        btn_can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


             int count=0;



                if (edt_type.getText().toString().trim().length()<=1) {
                    ++count;
                    edt_type.setError("Add Your Feedback");
                } else {
                    edt_type.setError(null);
                }


if(id.equalsIgnoreCase("0")){
    ++count;
    new AlertDialog.Builder(getActivity())
            .setTitle("Error")
            .setMessage("Please Select Expression")
            .setIcon(android.R.drawable.btn_dialog)
            .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int whichButton) {

                }
            }).show();
}


                if(count==0){
                    saveBankDet();
                }


            }
        });


        excellent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               id="10";
               excellent.setBackgroundResource(R.drawable.nav_back);
                verygood.setBackgroundResource(R.drawable.transparent_card);
                good.setBackgroundResource(R.drawable.transparent_card);
                fair.setBackgroundResource(R.drawable.transparent_card);
                unhappy.setBackgroundResource(R.drawable.transparent_card);


            }
        });
        verygood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                id="5";
                verygood.setBackgroundResource(R.drawable.nav_back);
                excellent.setBackgroundResource(R.drawable.transparent_card);
                good.setBackgroundResource(R.drawable.transparent_card);
                fair.setBackgroundResource(R.drawable.transparent_card);
                unhappy.setBackgroundResource(R.drawable.transparent_card);
            }
        });
        good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                id="4";
                good.setBackgroundResource(R.drawable.nav_back);
                verygood.setBackgroundResource(R.drawable.transparent_card);
                excellent.setBackgroundResource(R.drawable.transparent_card);
                fair.setBackgroundResource(R.drawable.transparent_card);
                unhappy.setBackgroundResource(R.drawable.transparent_card);
            }
        });
        fair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                id="3";
                fair.setBackgroundResource(R.drawable.nav_back);
                verygood.setBackgroundResource(R.drawable.transparent_card);
                good.setBackgroundResource(R.drawable.transparent_card);
                excellent.setBackgroundResource(R.drawable.transparent_card);
                unhappy.setBackgroundResource(R.drawable.transparent_card);
            }
        });
        unhappy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                id="1";
                unhappy.setBackgroundResource(R.drawable.nav_back);
                verygood.setBackgroundResource(R.drawable.transparent_card);
                good.setBackgroundResource(R.drawable.transparent_card);
                fair.setBackgroundResource(R.drawable.transparent_card);
                excellent.setBackgroundResource(R.drawable.transparent_card);
            }
        });




      return v;
    }
    private void saveBankDet() {
        progressDialog = ProgressDialog.show(getActivity(), "",
                "Saving. Please wait...", true);


       RequestQueue rq = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        String url = con.base_url + "/api/broker/add/feedback";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {


                            Log.d("save data",response);

                            progressDialog.dismiss();


                            JSONObject js=new JSONObject(response);

                            String status= String.valueOf(js.getBoolean("status"));
                            String message=js.getString("message");



                            if (status.equals("true")) {

                                new AlertDialog.Builder(getActivity())
                                        .setTitle("Success")
                                        .setMessage(message)
                                        .setIcon(R.drawable.checkmark)
                                        .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                DashboardBenifitFragment travel = new DashboardBenifitFragment();
                                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout_for_fragement, travel, travel.getTag()).addToBackStack("back").commit();

                                            }
                                        }).show();

                            }else {

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
















        HashMap<String, String> params = new HashMap<>();
        params.put("ratings", id);
        params.put("feedback", edt_type.getText().toString());


        Log.d("ratings", id);
        Log.d("feedback", edt_type.getText().toString());


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

