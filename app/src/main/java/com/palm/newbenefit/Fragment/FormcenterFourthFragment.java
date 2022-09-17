package com.palm.newbenefit.Fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.palm.newbenefit.Activity.MainActivity;
import com.palm.newbenefit.Adapter.FormAdapter;
import com.palm.newbenefit.Adapter.FormCenterAdapter;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.ApiConfig.RecyclerTouchListener;
import com.palm.newbenefit.Module.FormCenter;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class FormcenterFourthFragment extends Fragment {
    String token = null;
    private ViewPager viewPager;
    Button plan, summary;
    Bundle bundle;
    boolean respnce = false;
    FormAdapter adapter;
    Constants con;
    LinearLayout group_cover,form_center_two,form_center_three,form_center_four;
    ArrayList<FormCenter> ob = null;
    FormCenterAdapter adapters = null;
    RecyclerView recyclerView;
    ImageView info_text;
    String grp_term="0";
    String group="0";
    String per="0";



    public FormcenterFourthFragment() {
        // Required empty public constructor
    }
    SharedPreferences prefs;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_formcenter_fourth, container, false);

        con = new Constants();


        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).setTitle("");

        prefs = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);

        token = prefs.getString("api_token", null);

        group_cover= (LinearLayout) v.findViewById(R.id.group_cover);
        form_center_two= (LinearLayout) v.findViewById(R.id.form_center_two);
        form_center_three= (LinearLayout) v.findViewById(R.id.form_center_three);

        form_center_four= (LinearLayout) v.findViewById(R.id.form_center_four);

        group_cover.setBackgroundResource(R.drawable.click_on_tab);
        form_center_two.setBackgroundResource(R.drawable.click_tab);
        form_center_three.setBackgroundResource(R.drawable.click_tab);
        form_center_four.setBackgroundResource(R.drawable.click_tab);

        group_cover.setVisibility(View.GONE);
        form_center_two.setVisibility(View.GONE);
        form_center_three.setVisibility(View.GONE);
        form_center_four.setVisibility(View.GONE);


        form_center_four.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                setBankDet("Group Personal Accident");
                group_cover.setBackgroundResource(R.drawable.click_tab);
                form_center_two.setBackgroundResource(R.drawable.click_tab);
                form_center_four.setBackgroundResource(R.drawable.click_on_tab);
                form_center_three.setBackgroundResource(R.drawable.click_tab);

            }
        });

        group_cover.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                setBankDet("Group Mediclaim");
                group_cover.setBackgroundResource(R.drawable.click_on_tab);
                form_center_two.setBackgroundResource(R.drawable.click_tab);
                form_center_three.setBackgroundResource(R.drawable.click_tab);
                form_center_four.setBackgroundResource(R.drawable.click_tab);

            }
        });

        form_center_two.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                setBankDet("Personal Accidant");
                form_center_two.setBackgroundResource(R.drawable.click_on_tab);
                group_cover.setBackgroundResource(R.drawable.click_tab);
                form_center_three.setBackgroundResource(R.drawable.click_tab);
                form_center_four.setBackgroundResource(R.drawable.click_tab);

            }
        });

        form_center_three.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                setBankDet("Group Term Life");
                form_center_three.setBackgroundResource(R.drawable.click_on_tab);
                form_center_two.setBackgroundResource(R.drawable.click_tab);
                group_cover.setBackgroundResource(R.drawable.click_tab);
                form_center_four.setBackgroundResource(R.drawable.click_tab);

            }
        });


        con=new Constants();
        prefs = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);

        token = prefs.getString("api_token", null);

        SharedPreferences prefs = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);

        token = prefs.getString("api_token", null);
        recyclerView = v.findViewById(R.id.core_recycle);
        info_text= v.findViewById(R.id.info_text);




            ob = new ArrayList<>();
            adapters = new FormCenterAdapter(getActivity(), ob);
            recyclerView.setAdapter(adapters);



            setBankDets();



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



            //Data();


        return v;


    }


    private void setBankDets() {

        ob = new ArrayList<>();
        adapters = new FormCenterAdapter(getActivity(), ob);
        recyclerView.setAdapter(adapters);



        String url = con.base_url+ "/api/employee/get/employee-documnet-list?user_type_name=employee";
        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try{


                    Log.d("wellness",response);



                    JSONObject resp= new JSONObject(response);

                  JSONArray jsonObj =resp.getJSONArray("data");


                  if(jsonObj.length()==0){

                      info_text.setVisibility(View.VISIBLE);

                  }else {
                      for (int j = 0; j < jsonObj.length(); j++) {
                          JSONObject jo_area = (JSONObject) jsonObj.get(j);

                          if(jo_area.getString("policy_name").equalsIgnoreCase("Group Mediclaim")){

                              group="1";

                              group_cover.setVisibility(View.VISIBLE);



                          }

                          if(jo_area.getString("policy_name").equalsIgnoreCase("Personal Accidant")){

                              per="1";
                              form_center_two.setVisibility(View.VISIBLE);


                          }

                          if(jo_area.getString("policy_name").equalsIgnoreCase("Group Term Life")){
                              grp_term="1";
                              form_center_three.setVisibility(View.VISIBLE);


                          }

                          if(jo_area.getString("policy_name").equalsIgnoreCase("Group Personal Accident")){

                              form_center_four.setVisibility(View.VISIBLE);


                          }




                      }




                      if(group.equalsIgnoreCase("1")){
                          setBankDet("Group Mediclaim");
                      }else if(per.equalsIgnoreCase("1")){
                          setBankDet("Personal Accidant");
                      }else if(grp_term.equalsIgnoreCase("1")){
                          setBankDet("Group Term Life");
                      }else {
                          setBankDet("Group Personal Accident");
                      }

                  }




                } catch (Exception e) {
                    Log.e("onErrorResponse", e.toString());
                }

            }
        },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onErrorResponsemy", error.toString());

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


    private void setBankDet(String policy) {

        ob = new ArrayList<>();
        adapters = new FormCenterAdapter(getActivity(), ob);
        recyclerView.setAdapter(adapters);

        String url = con.base_url+ "/api/employee/get/employee-documnet-list?user_type_name=employee";
        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try{


                    Log.d("wellness",response);



                    JSONObject resp= new JSONObject(response);

                  JSONArray jsonObj =resp.getJSONArray("data");


                    for (int j = 0; j < jsonObj.length(); j++) {
                        JSONObject jo_area = (JSONObject) jsonObj.get(j);


                        if(jo_area.getString("policy_name").equalsIgnoreCase(policy)){
                            ob.add(new FormCenter(jo_area.getString("document_name"),
                                    jo_area.getString("document_path")));
                        }



                    }







                    adapters.notifyDataSetChanged();



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





        rq.add(mStringRequest);


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