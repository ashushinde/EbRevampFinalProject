package com.palm.newbenefit.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.palm.newbenefit.Activity.MainActivity;
import com.palm.newbenefit.Adapter.EnrollmentCrdAdapter;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.ApiConfig.RecyclerTouchListener;
import com.palm.newbenefit.Module.EnrollmentCards;
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
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import static android.view.View.VISIBLE;

public class MyEnrollMentCards extends Fragment {


    int gg;
    Constants con = null;
    Context context;
    String mobileNumber = null;
    String token = null;
    String user_id = null;
    String emp_id;
    List<EnrollmentCards> ob = null;
    EnrollmentCrdAdapter adapter = null;
    RecyclerView recyclerView = null;
    String name = null;
    ImageView info_text_GH;

    public MyEnrollMentCards() {
        // Required empty public constructor
    }

    TextView title,content;
    RelativeLayout announcement;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_my_enroll_ment_cards, container, false);


        SharedPreferences prefs = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);
        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).setTitle("");

        token = prefs.getString("api_token", null);


        con = new Constants();
        emp_id = prefs.getString("user_id", null);

        info_text_GH = v.findViewById(R.id.info_text_GH);

        recyclerView = v.findViewById(R.id.core_recycle);
        title=v.findViewById(R.id.title);
        content=v.findViewById(R.id.content);
        announcement=v.findViewById(R.id.announcement);

        ob = new ArrayList<>();
        adapter = new EnrollmentCrdAdapter(getActivity(), ob, emp_id);
        recyclerView.setAdapter(adapter);





        if (isNetworkAvailable()) {







            setBankDet();
            Announcement();

        }else {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error?")
                    .setMessage("Please Check Your Internet Connection")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
        }







        // data();


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


        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);


        return v;

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        context=getActivity();
        if (isVisibleToUser && (getActivity()!=null)) {




            if (isNetworkAvailable()) {



                //  getActivity();

                //HttpsTrustManager.allowAllSSL();
                ob = new ArrayList<>();
                adapter = new EnrollmentCrdAdapter(getActivity(), ob,emp_id);
                recyclerView.setAdapter(adapter);


                setBankDet();

            }else {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Error?")
                        .setMessage("Please Check Your Internet Connection")
                        .setIcon(android.R.drawable.btn_dialog)
                        .setNegativeButton(android.R.string.ok, null).show();
            }










        }
    }
    private void Announcement() {




        String url = con.base_url+"/api/admin/get/annoucement/module-wise?user_type_name=Employee";
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();

        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject data= new JSONObject(response);



                    String statusa= String.valueOf(data.getBoolean("status"));



                    if (statusa.equalsIgnoreCase("false")) {

                        announcement.setVisibility(View.GONE);
                    } else {
                        JSONArray array = data.getJSONArray("data");

                        if(array.length()==0){
                            announcement.setVisibility(View.GONE);
                        }else {
                            announcement.setVisibility(VISIBLE);
                        }

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject explrObject = array.getJSONObject(i);
                            String titles=explrObject.getString("title");
                            String contents=explrObject.getString("content");
                            JSONArray module = explrObject.getJSONArray("module");

                            for (int j = 0; j < module.length(); j++) {
                                JSONObject explrObjects = module.getJSONObject(j);

                                String modulname=explrObjects.getString("module_name");
                                if(modulname.equalsIgnoreCase("Enrolment")){

                                    title.setText(titles);
                                    content.setText(contents);
                                    announcement.setVisibility(VISIBLE);
                                }
                            }


                        }


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
        mRequestQueue.add(mStringRequest);
    }



    private void setBankDet() {
RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        String url =con.base_url+"/api/employee/policies";
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                        try {

                            String datap;
                            if(response.equalsIgnoreCase("\r\n[]")){
                                datap = response.replace("\r\n[]", "");
                            }else{
                                datap = response;
                            }


                            if (datap.equalsIgnoreCase("[]")) {
                                info_text_GH.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                            } else {
                                info_text_GH.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);

                                JSONObject jsonObjh = new JSONObject(response);

                                JSONArray array = jsonObjh.getJSONArray("data");


                                // JSONArray jsonObj = new JSONArray(response);

                                for (int j = 0; j < array.length(); j++) {
                                    JSONObject jsonarr = array.getJSONObject(j);



                                    String id = jsonarr.getString("id");
                                    String policy_number = jsonarr.getString("policy_number");
                                    String policy_sub_type_id = jsonarr.getString("policy_sub_type_id");
                                    String name = jsonarr.getString("name");
                                    String enrollement_status = jsonarr.getString("enrollement_status");
                                    String enrollement_confirmed = jsonarr.getString("enrollement_confirmed");
                                    String enrollement_start_date = jsonarr.getString("enrollement_start_date");
                                    String enrollement_end_date = jsonarr.getString("enrollement_end_date");
                                    String policy_enrollment_window = jsonarr.getString("policy_enrollment_window");


                                    if(policy_sub_type_id.equalsIgnoreCase("1")||policy_sub_type_id.equalsIgnoreCase("2")||
                                            policy_sub_type_id.equalsIgnoreCase("3"))
                                        ob.add(new EnrollmentCards(id,policy_number,policy_sub_type_id,name,enrollement_status
                                                ,enrollement_confirmed,enrollement_start_date,enrollement_end_date
                                                ,policy_enrollment_window));








                                 /*   if(enrollement_confirmed.equalsIgnoreCase("0")&&enrollement_status.equalsIgnoreCase("1")
                                    &&policy_sub_type_id.equalsIgnoreCase("1")||policy_sub_type_id.equalsIgnoreCase("2")){
                                        ob.add(new EnrollmentCards(id,policy_number,policy_sub_type_id,name,enrollement_status
                                                ,enrollement_confirmed,enrollement_start_date,enrollement_end_date));
                                    }else {


                                    }

*/


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
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };







        mRequestQueue.add(mStringRequest);
    }


  /*  public void data() {
        ob.add(new CoreData("1", "Sodexo", "", ""
                , "", "16", "", "", "", "", "", ""
                , "", "", "500", "", "", "", "", "", "", ""
                , "", "", "", "", ""));


        ob.add(new CoreData("2", "GHI Top-Up", "", ""
                , "", "15", "", "", "", "", "", ""
                , "", "", "658", "", "", "", "", "", "", ""
                , "", "", "", "", ""));


        ob.add(new CoreData("3", "Health Check Up", "", ""
                , "", "12", "", "", "", "", "", ""
                , "", "", "700", "", "", "", "", "", "", ""
                , "", "", "", "", ""));


        ob.add(new CoreData("4", "Parental Cover", "", ""
                , "", "14", "", "", "", "", "", ""
                , "", "", "800", "", "", "", "", "", "", ""
                , "", "", "", "", ""));


        ob.add(new CoreData("5", "GTLI Top-up", "", ""
                , "", "14", "", "", "", "", "", ""
                , "", "", "1200", "", "", "", "", "", "", ""
                , "", "", "", "", ""));


        adapter.notifyDataSetChanged();
    }*/

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


