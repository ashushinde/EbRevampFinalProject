package com.palm.newbenefit.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.palm.newbenefit.Adapter.MyWellnessAdapter;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.ApiConfig.RecyclerTouchListener;
import com.palm.newbenefit.Module.Wellness;
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


public class WellnessFragmentSecond extends Fragment {
    String user_id;
    Constants con = null;
    Context context;
    String mobileNumber = null;
    String token = null;



    List<Wellness> ob = null;
    MyWellnessAdapter adapter = null;
   RecyclerView  recyclerView;
   ImageView info_text;

    public WellnessFragmentSecond() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    String employee_id;
    TextView static_content;
    LinearLayout hide;
    Button group_cover,voluntary_cover;
    LinearLayout heaps,wellness;

    SharedPreferences prefs;
    WebView webview;
    ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_wellness_data, container, false);
        con = new Constants();

        con=new Constants();
        prefs = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);

        token = prefs.getString("api_token", null);
        user_id = prefs.getString("user_id", null);

        webview = v.findViewById(R.id.webView);
        progressBar = v.findViewById(R.id.progressBar);

        heapsUrl();
        SharedPreferences prefs = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);

        token = prefs.getString("api_token", null);
        recyclerView = v.findViewById(R.id.ghi_recycle);
        info_text= v.findViewById(R.id.info_text);
        static_content= v.findViewById(R.id.static_content);
        hide= v.findViewById(R.id.hide);
        group_cover= v.findViewById(R.id.group_cover);
        voluntary_cover= v.findViewById(R.id.voluntary_cover);
        heaps= v.findViewById(R.id.heaps);
        wellness= v.findViewById(R.id.wellness);


        wellness.setVisibility(View.VISIBLE);
        heaps.setVisibility(View.GONE);

        if (isNetworkAvailable()) {

            ob = new ArrayList<>();
            adapter = new MyWellnessAdapter(getActivity(), ob);
            recyclerView.setAdapter(adapter);



            GetEmployeeId();





            //Data();
        }else {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error?")
                    .setMessage("Please Check Your Internet Connection")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
        }

        group_cover.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                group_cover.setBackgroundResource(R.drawable.nav_back_tab);
                voluntary_cover.setBackgroundResource(R.drawable.tab_curve);
                voluntary_cover.setTextColor(Color.BLACK);
                group_cover.setTextColor(Color.WHITE);





                wellness.setVisibility(View.GONE);
                heaps.setVisibility(View.VISIBLE);


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

                wellness.setVisibility(View.VISIBLE);
                heaps.setVisibility(View.GONE);


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


        GridLayoutManager manager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);



//        webview.setWebViewClient(new android.webkit.WebViewClient());
//
//        webview.getSettings().setPluginState(WebSettings.PluginState.ON);
//        webview.getSettings().setJavaScriptEnabled(true);
//        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
//
//        webview.getSettings().setSupportMultipleWindows(false);
//        webview.getSettings().setSupportZoom(false);
//        webview.setVerticalScrollBarEnabled(false);
//        webview.setHorizontalScrollBarEnabled(false);
//
//
//        String number="https://wellness.connectandheal.com/api/auth/selfSign?session=rAr6/e7sxD3ZxGqSl3EWraI1iDsZoIzb75gTGLIOrrE";
//
//        Map <String, String> extraHeaders = new HashMap<String, String>();
//        extraHeaders.put("X-CNH-PARTNER","FYNTUNE");
//        webview.loadUrl(number, extraHeaders);


        return  v;
    }
    public class WebViewClient extends android.webkit.WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }


    void heapsUrl(){

        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        String url = con.base_url+"/api/admin/heaps/member-sync";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        try {

                            Log.d("response_data",response);
                            JSONObject js=new JSONObject(response);





                            JSONObject jsonObj=js.getJSONObject("data");




                            String url = jsonObj.getString("url");
                            String number = url.replaceAll("\"", "");
                            Log.d("Wellnessdata",number);


                            webview.setWebViewClient(new WebViewClient());

                            webview.getSettings().setPluginState(WebSettings.PluginState.ON);
                            webview.getSettings().setJavaScriptEnabled(true);
                            webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);

                            webview.getSettings().setSupportMultipleWindows(false);
                            webview.getSettings().setSupportZoom(false);
                            webview.setVerticalScrollBarEnabled(false);
                            webview.setHorizontalScrollBarEnabled(false);

                            webview.loadUrl(number);


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
        params.put("wellness_partner_id", "1");
        params.put("user_type_name", "Employee");


        smr.setParams(params);
        rq.add(smr);
    }

    private void setBankDet() {
        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        String url = con.base_url+ "/api/admin/get/all-employee-benefit";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                          Log.d("wellness",response);



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

                                    String id = jo_area.getString("id");
                                    String benefit_id = jo_area.getString("benefit_id");
                                    String benefit_name = (jo_area.getString("benefit_name"));
                                    String benefit_content = (jo_area.getString("benefit_content"));
                                    String benefit_image = (jo_area.getString("benefit_image"));
                                    String wellness_partner_id = jo_area.getString("wellness_partner_id");
                                    String wellness_partner_name = jo_area.getString("wellness_partner_name");
                                    String wellness_partner_url = (jo_area.getString("wellness_partner_url"));
                                    String wellness_partner_logo = (jo_area.getString("benefit_image"));

                                    String static_contents = (jo_area.getString("static_content"));



                                    if(static_contents.equalsIgnoreCase("null")){
                                        hide.setVisibility(View.GONE);


                                    }else {
                                        hide.setVisibility(View.VISIBLE);
                                        static_content.setText(static_contents);
                                    }



                                    ob.add(new Wellness(id,benefit_id,benefit_name,
                                            benefit_content,benefit_image,wellness_partner_id,wellness_partner_name,
                                            wellness_partner_url,wellness_partner_logo));


                                }
                               /* String imageurl ="https://lifekaplanuat-eb.tmibasl.in/assets/images/icon/visit.png";

                                ob.add(new Wellness("id","benefit_id","Visit",
                                        "VISIT empowers organisations enhance healthcare experience and" +
                                                " optimise medical costs for all their employees.",imageurl,imageurl,"Visit",
                                        imageurl,imageurl));*/





                                adapter.notifyDataSetChanged();


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





        HashMap<String, String> params = new HashMap<>();
        params.put("employee_id", employee_id);

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





                        if (employer_id != "null" && !employer_id.isEmpty()) {

                            employee_id=employer_id;

                            setBankDet();

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