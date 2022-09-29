package com.palm.newbenefit.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.kmd.newbenefit.R;
import com.palm.newbenefit.models.ContactUsMain;
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

public class ContactusLogAdapter extends
        RecyclerView.Adapter<ContactusLogAdapter.ViewHolder> {
Constants con;
    private List<ContactUsMain> mTrain;
    private Context context = null;
    private String compareCount = null;

    List<ContactUsMain> tpalist = null;
    ContactusmainDataAdapter tpa_adapter = null;

    // Pass in the contact array into the constructor
    public ContactusLogAdapter(Context context, List<ContactUsMain> train) {
        this.context = context;
        mTrain = train;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
         con=new Constants();
        // Inflate the custom layout
        View trainingView = inflater.inflate(R.layout.contact_list, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(context, trainingView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        ContactUsMain train = mTrain.get(position);
        con = new Constants();
        // Set item views based on your views and data model
        //TextView type = viewHolder.p_type;

        viewHolder.admin.setText(train.getMainname());
        viewHolder.tpaname.setText(train.getSubname());


        tpalist = new ArrayList<>();
        tpa_adapter = new ContactusmainDataAdapter(context, tpalist);
        viewHolder.all_recycle.setAdapter(tpa_adapter);


        int numberOfColumns = 1;
        viewHolder.all_recycle.setLayoutManager(new GridLayoutManager(context, numberOfColumns));

        viewHolder.all_recycle.addOnItemTouchListener(new RecyclerTouchListener(context, viewHolder.all_recycle, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager manager = new GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false);
        viewHolder.all_recycle.setLayoutManager(manager);


        String url = con.base_url+"/api/employee/get/contact-logs";
       /// RequestQueue mRequestQueue = Volley.newRequestQueue(context);

        RequestQueue mRequestQueuea = Volley.newRequestQueue(context);
        mRequestQueuea.getCache().clear();

        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js = null;
                    try {
                        js = new JSONObject(response);

                        Log.d("response", response);


                        String status = String.valueOf(js.getBoolean("status"));

                        if (status.equalsIgnoreCase("false")) {
                            viewHolder.all_recycle.setVisibility(View.GONE);

                        } else {
                            JSONArray jsonObja = js.getJSONArray("data");


                            JSONArray brokers = null;

                            for (int h = 0; h < jsonObja.length(); h++) {

                                JSONObject jo_areas = (JSONObject) jsonObja.get(h);

                                if(train.getMainname().equalsIgnoreCase(jo_areas.getString("type"))){
                                    brokers = jo_areas.getJSONArray("data");



                                          JSONObject jo_area = (JSONObject) brokers.get(0);
                                         String mainname = train.getMainname();
                                          try{
                                              JSONObject jo_area1 = (JSONObject) brokers.get(1);

                                              String subname2 = jo_area1.getString("name");
                                              String empname2 = jo_area1.getString("name");
                                              String emailid2 = jo_area1.getString("email");
                                              String contactno2 = jo_area1.getString("contact");
                                              String address2 = jo_area1.getString("address");
                                              String levelcount2 = "2";

                                              String subname1 = jo_area.getString("name");
                                              String empname1 = jo_area.getString("name");
                                              String emailid1 = jo_area.getString("email");
                                              String contactno1 = jo_area.getString("contact");
                                              String address1 = jo_area.getString("address");
                                              String levelcount1 = "1";


                                              tpalist.add(new ContactUsMain(mainname, subname1, empname1, emailid1, contactno1, address1, levelcount1));


                                              if (((contactno2.equalsIgnoreCase(""))
                                                      || (contactno2.equalsIgnoreCase("null")
                                                      || (contactno2.equalsIgnoreCase("false")
                                                      || (contactno2.equalsIgnoreCase("0")))))
                                                      &&
                                                      ((address2.equalsIgnoreCase(""))
                                                              || (address2.equalsIgnoreCase("null")
                                                              || (address2.equalsIgnoreCase("false")
                                                              || (address2.equalsIgnoreCase("0")))))
                                                      &&
                                                      ((emailid2.equalsIgnoreCase(""))
                                                              || (emailid2.equalsIgnoreCase("null")
                                                              || (emailid2.equalsIgnoreCase("false")
                                                              || (emailid2.equalsIgnoreCase("0")))))
                                              ) {




                                                  tpalist.add(new ContactUsMain(subname2, subname2, subname2,
                                                          "-",
                                                          "-",
                                                          "-",
                                                          "2"));


                                              } else {
                                                  tpalist.add(new ContactUsMain(mainname, subname2, empname2, emailid2, contactno2, address2, levelcount2));


                                              }

                                          }catch (Exception e){
                                              JSONObject jo_area1 = (JSONObject) brokers.get(0);

                                              String subname2 = jo_area1.getString("name");
                                              String empname2 = jo_area1.getString("name");
                                              String emailid2 = jo_area1.getString("email");
                                              String contactno2 = jo_area1.getString("contact");
                                              String address2 = jo_area1.getString("address");
                                              String levelcount2 = "2";

                                              String subname1 = jo_area.getString("name");
                                              String empname1 = jo_area.getString("name");
                                              String emailid1 = jo_area.getString("email");
                                              String contactno1 = jo_area.getString("contact");
                                              String address1 = jo_area.getString("address");
                                              String levelcount1 = "1";


                                              tpalist.add(new ContactUsMain(mainname, subname1, empname1, emailid1, contactno1, address1, levelcount1));


                                          }






                                    try {



                                        String subname1 = jo_area.getString("name");
                                        String empname1 = jo_area.getString("name");
                                        String emailid1 = jo_area.getString("email");
                                        String contactno1 = jo_area.getString("contact");
                                        String address1 = jo_area.getString("address");
                                        String levelcount1 = "1";












                                        try{
                                            JSONObject jo_area2 = (JSONObject) brokers.get(2);
                                            String subname3 = jo_area2.getString("name");
                                            String empname3 = jo_area2.getString("name");
                                            String emailid3 = jo_area2.getString("email");
                                            String contactno3 = jo_area2.getString("contact");
                                            String address3 = jo_area2.getString("address");
                                            String levelcount3 = "3";

                                            if (((contactno3.equalsIgnoreCase(""))
                                                    || (contactno3.equalsIgnoreCase("null")
                                                    || (contactno3.equalsIgnoreCase("false")
                                                    || (contactno3.equalsIgnoreCase("0")))))
                                                    &&
                                                    ((address3.equalsIgnoreCase(""))
                                                            || (address3.equalsIgnoreCase("null")
                                                            || (address3.equalsIgnoreCase("false")
                                                            || (address3.equalsIgnoreCase("0")))))
                                                    &&
                                                    ((emailid3.equalsIgnoreCase(""))
                                                            || (emailid3.equalsIgnoreCase("null")
                                                            || (emailid3.equalsIgnoreCase("false")
                                                            || (emailid3.equalsIgnoreCase("0")))))
                                            ) {


                                                tpalist.add(new ContactUsMain(subname1, subname1, subname1,
                                                        "-",
                                                        "-",
                                                        "-",
                                                        "3"));


                                            } else {
                                                tpalist.add(new ContactUsMain(mainname, subname3, empname3, emailid3, contactno3, address3, levelcount3));


                                            }
                                        }catch (Exception e){

                                        }




                                    } catch (JSONException ex) {
                                        ex.printStackTrace();
                                    }


                                }

                            }
                            tpa_adapter.notifyDataSetChanged();





                        }


                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                } catch (Exception e) {
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

                SharedPreferences prefs =context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);


                String token = prefs.getString("api_token", null);
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        mRequestQueuea.add(mStringRequest);




    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mTrain.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView admin, tpaname;
        RecyclerView all_recycle;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final Context context, View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            // p_type = (TextView) itemView.findViewById(R.id.p_type);
            admin = itemView.findViewById(R.id.admin);
            tpaname = itemView.findViewById(R.id.tpaname);

            all_recycle = itemView.findViewById(R.id.all_recycle);


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
