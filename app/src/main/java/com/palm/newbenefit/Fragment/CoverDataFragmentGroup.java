package com.palm.newbenefit.Fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
import com.palm.newbenefit.Adapter.HomeGroupAdapter;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.ApiConfig.RecyclerTouchListener;
import com.palm.newbenefit.Module.Group;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class CoverDataFragmentGroup extends Fragment {
      Constants con = null;

    String token = null;
    String user_id = null;
    List<Group> ob = null;
    HomeGroupAdapter adapter = null;



    int amountrs = 500;

    RecyclerView recyclerView;


    ImageView info_text;

    private RequestQueue requestQueue;






    public CoverDataFragmentGroup() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_cover_data_group, container, false);




      con = new Constants();

       SharedPreferences prefs = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);
        token = prefs.getString("api_token", null);
        user_id = prefs.getString("user_id", null);

        recyclerView = v.findViewById(R.id.ghi_recycle);
        info_text= v.findViewById(R.id.info_text);





        EmployeeDetail();




        if (isNetworkAvailable()) {

            ob = new ArrayList<>();
            adapter = new HomeGroupAdapter(getActivity(), ob);
            recyclerView.setAdapter(adapter);
            // demo();
            setBankDet("group");










            //Data();
        }else {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error?")
                    .setMessage("Please Check Your Internet Connection")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
        }







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










        return v;

    }


    private void setBankDet(String fg) {

        String url = con.base_url+"/api/employee/get/dashboard";

     RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);

                    Log.d("response",response);




                   String  status= String.valueOf(js.getBoolean("status"));

                   if(status.equalsIgnoreCase("false")){
                       info_text.setVisibility(View.VISIBLE);
                       recyclerView.setVisibility(View.GONE);
                   }else {
                       JSONArray  jsonObj=js.getJSONArray("data");

                       if (jsonObj.length() == 0){
                           info_text.setVisibility(View.VISIBLE);
                           recyclerView.setVisibility(View.GONE);
                       }else {
                           info_text.setVisibility(View.GONE);
                           recyclerView.setVisibility(View.VISIBLE);




                           for (int j = 0; j < jsonObj.length(); j++) {

                               JSONObject jo_area = (JSONObject) jsonObj.get(j);
                               String po_id = String.valueOf(jo_area.getInt("policy_id"));
                               String id = String.valueOf(jo_area.getString("premium"));
                               String pol_id = jo_area.getString("policy_number");
                               String in_co_name = jo_area.getString("insurer_name");
                               String pol_mem_insured = String.valueOf((jo_area.getInt("suminsured")));
                               String cover_balance = String.valueOf((jo_area.getInt("suminsured")));
                               //String pol_mem_pre = (jo_area.getString("policy_mem_sum_premium"));
                               String pol_sub_type_name = (jo_area.getString("policy_name"));
                               String policy_sub_type_image_path = (jo_area.getString("insurer_logo"));
                               String insurer_companies_img_path = (jo_area.getString("policy_description"));
                               String policy_sub_type_id = (jo_area.getString("policy_sub_type_id"));
                               int member_count = (jo_area.getInt("total_member"));

//                                String opd = (jo_area.getString("Opd_sum_insure"));
//                                // String opd = "5000";
//
//                                String member = String.valueOf(member_count);
                               String cover_balancea = String.valueOf((jo_area.getInt("cover_balance")));
                               String opd_suminsured = String.valueOf((jo_area.getInt("opd_suminsured")));
                               String enhnace_cover = String.valueOf((jo_area.getString("enhnace_cover")));

                               String members = (jo_area.getString("members"));


                               members= members.replace("[","");
                               members=members.replace("]","");
                               members=members.replace("\"","");


                               String policy_sub_type_name = (jo_area.getString("policy_sub_type_name"));
                               String premium = (jo_area.getString("premium"));
                               double enhance_premium=0.0;
                               double allenhance_premium=0.0;
                               try{
                                   enhance_premium=jo_area.getDouble("enhnace_premium");
                               }catch (Exception e){
                                   enhance_premium=0.0;
                               }
                               Double idss = jo_area.getDouble("premium");

                               enhance_premium= enhance_premium+idss;

                               String ids= String.valueOf(enhance_premium);



                               if(premium.isEmpty()||premium.equalsIgnoreCase("null")
                                       ||premium.equalsIgnoreCase("0")){

                                   if(!cover_balance.equalsIgnoreCase("0")){
                                       ob.add(new Group(po_id,ids, pol_id, in_co_name, pol_mem_insured,
                                               "", pol_sub_type_name,
                                               policy_sub_type_image_path,
                                               insurer_companies_img_path,
                                               policy_sub_type_id, members, cover_balance,
                                               amountrs, "",cover_balancea,opd_suminsured,enhnace_cover));

                                   }


                               }else {

                               }

                           /*    if(policy_sub_type_name.contains("Group")){

                                   if(String.valueOf(jo_area.getString("premium")).equalsIgnoreCase("0")){
                                       ob.add(new Group(po_id,id, pol_id, in_co_name, pol_mem_insured, "", pol_sub_type_name, policy_sub_type_image_path, insurer_companies_img_path, policy_sub_type_id, members, cover_balance, amountrs, "",cover_balancea,opd_suminsured));

                                   }else {
                                       ob.add(new Group(po_id,id, pol_id, in_co_name, pol_mem_insured, "", pol_sub_type_name, policy_sub_type_image_path, insurer_companies_img_path, policy_sub_type_id, members, cover_balance, amountrs, "",cover_balancea,opd_suminsured));

                                   }
                               }else {
//                                   if(pol_sub_type_name.contains("Group")){
//
//                                   }else {
//                                       ob.add(new Group(id, pol_id, in_co_name, pol_mem_insured, "", pol_sub_type_name, policy_sub_type_image_path, insurer_companies_img_path, "", members, cover_balance, amountrs, "",cover_balancea,opd_suminsured));
//
//                                   }

                               }

*/

                           }

                           if(ob.isEmpty()){
                               info_text.setVisibility(View.VISIBLE);
                               recyclerView.setVisibility(View.GONE);
                           }else {
                               info_text.setVisibility(View.GONE);
                               recyclerView.setVisibility(View.VISIBLE);
                           }
                           adapter.notifyDataSetChanged();
                   }




                    }
                        } catch (Exception e) {
                    Log.e("onErrorResponse", e.toString());
                            e.printStackTrace();
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






    public  void EmployeeDetail(){
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





                        String first_name_input = explrObject.getString("name");
                        String logo = explrObject.getString("branding");








                        if (first_name_input != "null" && !first_name_input.isEmpty()) {

                        }

                        if (logo != "null" && !logo.isEmpty()) {


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

