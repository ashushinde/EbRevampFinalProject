package com.palm.newbenefit.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.ApiConfig.RecyclerTouchListener;
import com.palm.newbenefit.Module.MyInsuranceFlex;
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

;

public class Feature_flex_adapterSum extends RecyclerView.Adapter<Feature_flex_adapterSum.ViewHolder> {
    Constants con;
    private List<MyInsuranceFlex> mTrain;
    private Context context = null;
    String token;

    int lastselectedposition=-1;


    List<MyInsuranceFlex> obab = null;
    Feature_flex_adapterSumFeature adapterab = null;


    // Pass in the contact array into the constructor
    public Feature_flex_adapterSum(Context context, List<MyInsuranceFlex> train) {
        this.context = context;
        mTrain = train;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        con = new Constants();
        // Inflate the custom layout
        View trainingView = inflater.inflate(R.layout.suminsured_list, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(context, trainingView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        if(lastselectedposition == position){
            viewHolder.feature_recycle.setVisibility(View.VISIBLE);
        }else{
            viewHolder.feature_recycle.setVisibility(View.GONE);
        }

        SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);

        token = prefs.getString("api_token", null);
        // Get the data model based on position
        MyInsuranceFlex train = mTrain.get(position);
        con = new Constants();


          viewHolder.flex_name.setText(train.getBroker_id());
           viewHolder.premium.setText(train.getBroker());





           viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   if(lastselectedposition>=0){
                       notifyItemChanged(position);
                   }

                   if(viewHolder.hide.getVisibility()==View.VISIBLE){
                       viewHolder.hide.setVisibility(View.GONE);
                   }else {
                       viewHolder.hide.setVisibility(View.VISIBLE);
                       lastselectedposition=position;
                   }


               }
           });






        int numberOfColumns = 1;






        viewHolder.feature_recycle.setLayoutManager(new GridLayoutManager(context, numberOfColumns));

        viewHolder.feature_recycle.addOnItemTouchListener(new RecyclerTouchListener(context, viewHolder.feature_recycle, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager policy_cycle_termterm = new GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false);
        viewHolder.feature_recycle.setLayoutManager(policy_cycle_termterm);








        obab = new ArrayList<>();
        adapterab = new Feature_flex_adapterSumFeature(context, obab);
        viewHolder.feature_recycle.setAdapter(adapterab);



        String url = con.base_url+"/api/employee/get/flex-benefit/policies";
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js = new JSONObject(response);

                    Log.d("response", response);


                    JSONArray jsonObj = js.getJSONArray("data");

                    if (jsonObj.length() == 0) {

                    } else {

                        for (int j = 0; j < jsonObj.length(); j++) {

                            JSONObject jo_area = (JSONObject) jsonObj.get(j);

                            String  id= jo_area.getString("id");

                            JSONArray benefits = jo_area.getJSONArray("benefits");


                            if(id.equalsIgnoreCase(train.getId())){

                                for (int k = 0; k < benefits.length(); k++) {

                                    JSONObject enhance_suminsuredsk = (JSONObject) benefits.get(j);

                                    String benefit_name = enhance_suminsuredsk.getString("benefit_name");


                                    obab.add(new MyInsuranceFlex(benefit_name
                                    ));

                                }

                            }




                        }
                    }


                    adapterab.notifyDataSetChanged();

                }catch (Exception e){

                }

            }
        },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("InseonErrorResponse", error.toString());
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



    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mTrain.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView flex_name,premium;
        RecyclerView feature_recycle;
        LinearLayout hide;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final Context context, View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            flex_name = (TextView) itemView.findViewById(R.id.flex_name);
            premium=(TextView) itemView.findViewById(R.id.premium);
            feature_recycle= itemView.findViewById(R.id.feature_recycle);
            hide= itemView.findViewById(R.id.hide);

        }
    }


    private SSLSocketFactory getSocketFactory() {

        CertificateFactory cf = null;
        try {
            cf = CertificateFactory.getInstance("X.509");
            InputStream caInput = context.getResources().openRawResource(R.raw.cert);
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
