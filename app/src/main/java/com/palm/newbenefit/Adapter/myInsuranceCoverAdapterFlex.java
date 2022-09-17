package com.palm.newbenefit.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.ApiConfig.RecyclerTouchListener;
import com.palm.newbenefit.Module.MyInsuranceFlex;
import com.palm.newbenefit.R;
import com.palm.tatarewamp.SslData.NullHostNameVerifier;

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
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;



public class myInsuranceCoverAdapterFlex extends RecyclerView.Adapter<myInsuranceCoverAdapterFlex.ViewHolder> {
    Constants con;
    private List<MyInsuranceFlex> mTrain;
    private Context context = null;
    String token;

    TextView desc=null;


    List<MyInsuranceFlex> obab = null;
    Feature_flex_adapterSum adapterab = null;


    // Pass in the contact array into the constructor
    public myInsuranceCoverAdapterFlex(Context context, List<MyInsuranceFlex> train) {
        this.context = context;
        mTrain = train;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        con = new Constants();
        // Inflate the custom layout
        View trainingView = inflater.inflate(R.layout.in_flex_list, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(context, trainingView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);

        token = prefs.getString("api_token", null);
        // Get the data model based on position
        MyInsuranceFlex train = mTrain.get(position);
        con = new Constants();


        viewHolder.plan_name.setText(train.getPolicy_name());
        viewHolder.description.setText(train.getDescription());
        viewHolder.flex_name.setText(train.getPolicy_number());












        if(train.getPolicy_sub_type_id().equalsIgnoreCase("2")||
                train.getPolicy_sub_type_id().equalsIgnoreCase("5")){
            viewHolder.two_five.setVisibility(View.VISIBLE);
            viewHolder.one_four.setVisibility(View.GONE);
            viewHolder.three_six.setVisibility(View.GONE);


        }else if(train.getPolicy_sub_type_id().equalsIgnoreCase("1")||
                train.getPolicy_sub_type_id().equalsIgnoreCase("4")){

            viewHolder.two_five.setVisibility(View.GONE);
            viewHolder.one_four.setVisibility(View.VISIBLE);
            viewHolder.three_six.setVisibility(View.GONE);


        }else if(train.getPolicy_sub_type_id().equalsIgnoreCase("3")||
                train.getPolicy_sub_type_id().equalsIgnoreCase("6")){

            viewHolder.two_five.setVisibility(View.GONE);
            viewHolder.one_four.setVisibility(View.GONE);
            viewHolder.three_six.setVisibility(View.VISIBLE);
        }


        obab = new ArrayList<>();
        adapterab = new Feature_flex_adapterSum(context, obab);
        viewHolder.insurance_recycle.setAdapter(adapterab);







        int numberOfColumns = 1;

        viewHolder.insurance_recycle.setLayoutManager(new GridLayoutManager(context, numberOfColumns));

        viewHolder.insurance_recycle.addOnItemTouchListener(new RecyclerTouchListener(context, viewHolder.insurance_recycle, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager policy_cycle_termterm = new GridLayoutManager(context, 2, GridLayoutManager.HORIZONTAL,
                false);
        viewHolder.insurance_recycle.setLayoutManager(policy_cycle_termterm);





        for (int k = 0; k < train.getSuminsuredlist().size(); k++) {


            obab.add(new MyInsuranceFlex(train.getId(),train.getSuminsuredlist().get(k), train.getPremiumlist().get(k)
            ));

        }
        adapterab.notifyDataSetChanged();



       /* String url = con.base_url+"/api/employee/get/flex-benefit/policies";
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

                            String  id= jo_area.getString("policy_number");



                            if(id.equalsIgnoreCase(train.getId())){

                                try{
                                    JSONArray enhance_suminsureds = jo_area.getJSONArray("enhance_suminsureds");
                                    JSONArray enhance_premiums = jo_area.getJSONArray("enhance_premiums");


                                    for (int k = 0; k < enhance_suminsureds.length(); k++) {

                                        JSONObject enhance_suminsuredsk = (JSONObject) enhance_suminsureds.get(j);
                                        JSONObject enhance_premiumsk = (JSONObject) enhance_premiums.get(j);

                                        String sum_insured = enhance_suminsuredsk.getString("sum_insured");
                                        String total_premium = enhance_premiumsk.getString("total_premium");


                                        obab.add(new MyInsuranceFlex(train.getId(),sum_insured, total_premium
                                        ));

                                    }
                                    adapterab.notifyDataSetChanged();


                                }catch (Exception e){
                                    Log.e("mysumerror", e.toString());

                                }



                            }




                        }
                    }



                }catch (Exception e){
                    Log.e("InseonErrorResponse", e.toString());
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

*/









        viewHolder.hide.setVisibility(View.GONE);
        viewHolder.expand.setImageResource(R.drawable.ic_add_black_24dp);


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                if(viewHolder.hide.getVisibility() == View.VISIBLE){

                    viewHolder.hide.setVisibility(View.GONE);
                    viewHolder.expand.setImageResource(R.drawable.ic_add_black_24dp);

                }
                else {



                    viewHolder.hide.setVisibility(View.VISIBLE);

                    viewHolder.expand.setImageResource(R.drawable.ic_remove_black_24dp);

                }



            }

        });






    }



    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mTrain.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView plan_name,description,flex_name;
        ImageView expand,one_four,three_six,two_five;
        LinearLayout hide;
        RecyclerView insurance_recycle;



        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final Context context, View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            plan_name = (TextView) itemView.findViewById(R.id.plan_name);
            expand=  itemView.findViewById(R.id.expand);
            hide=  itemView.findViewById(R.id.hide);
            description=  itemView.findViewById(R.id.description);
            description=  itemView.findViewById(R.id.description);
            flex_name=  itemView.findViewById(R.id.flex_name);
            hide=  itemView.findViewById(R.id.hide);
            insurance_recycle=  itemView.findViewById(R.id.insurance_recycle);
            one_four=itemView.findViewById(R.id.one_four);
            three_six=itemView.findViewById(R.id.three_six);
            two_five = (ImageView) itemView.findViewById(R.id.two_five);
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
