package com.palm.newbenefit.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.Module.FlexBenefitWellness;
import com.kmd.newbenefit.R;
import com.palm.tatarewamp.SslData.NullHostNameVerifier;
import com.squareup.picasso.Picasso;

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
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

;import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class myFlexAdapter extends RecyclerView.Adapter<myFlexAdapter.ViewHolder> {
    Constants con;
    private List<FlexBenefitWellness> mTrain;
    private Context context = null;
    String token;

    // Pass in the contact array into the constructor
    public myFlexAdapter(Context context, List<FlexBenefitWellness> train) {
        this.context = context;
        mTrain = train;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        con = new Constants();
        // Inflate the custom layout
        View trainingView = inflater.inflate(R.layout.flex_list, parent, false);

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
        FlexBenefitWellness train = mTrain.get(position);
        con = new Constants();



            viewHolder.flex_name.setText(train.getFlexi_benefit_name());

            if(train.getFlexi_benefit_description().equalsIgnoreCase("")||train.getFlexi_benefit_description().equalsIgnoreCase("null")){


            }else {
                viewHolder.flex_discription.setText(train.getFlexi_benefit_description());
            }

        viewHolder.final_amount.setText(train.getFlex_balance());


        viewHolder.allocated_amt.setText(train.getAllocated_amount());



        viewHolder.allocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {






                Context context = v.getContext();
                FlexBenefitWellness train = mTrain.get(position);




                if(train.getFlexi_benefit_name().equalsIgnoreCase("sodexo")
                &&(!train.getAmount().equalsIgnoreCase(""))){





                    final Dialog dialog = new Dialog(context);
                    dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.allocate_value);

                    ImageView image = dialog.findViewById(R.id.image);
                    ImageView ab = (ImageView) dialog.findViewById(R.id.ab);
                    TextView close = (TextView) dialog.findViewById(R.id.close);
                    RadioGroup vol_rg = (RadioGroup) dialog.findViewById(R.id.vol_rg);
                    TextView allocate = (TextView) dialog.findViewById(R.id.allocate);
                    TextView name = (TextView) dialog.findViewById(R.id.name);

                    name.setText(train.getFlexi_benefit_name());
                    ab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    });

                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    });

                    String amount_sodexo=train.getAmount();

                    List<String> myList = new ArrayList<String>(Arrays.asList(amount_sodexo.split(",")));

                    final RadioButton[] rb = new RadioButton[myList.size()];
//                                                  RadioGroup rg = new RadioGroup(context); //create the RadioGroup
                    vol_rg.setOrientation(RadioGroup.VERTICAL);//or RadioGroup.VERTICAL


                    Drawable img = context.getResources().getDrawable( R.drawable.rupee );
                    img.setBounds( 0, 0, 30, 30 );

                    for (int i = 0; i < myList.size(); i++) {
                        rb[i] = new RadioButton(context);
                        if (myList.get(i).toString().isEmpty()||
                                myList.get(i).toString().equalsIgnoreCase("null")||
                                myList.get(i).toString().equalsIgnoreCase("0"))
                        {
                            rb[i].setText((myList.get(i).toString()));
                        }else {
                            try {
                                int data= Integer.parseInt(myList.get(i).toString());

                                String cover_data = NumberFormat.getNumberInstance(Locale.US).format(data);

                                rb[i].setText(cover_data);
                            }catch (Exception e){
                                rb[i].setText(myList.get(i).toString());
                            }
                        }

                        rb[i].setCompoundDrawables( img, null, null, null );

                        rb[i].setId(Integer.parseInt(myList.get(i).toString()));
                        vol_rg.addView(rb[i]);
                    }


                    allocate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            int selectedRadioButtonID = vol_rg.getCheckedRadioButtonId();
                           // RequestQueue rq = Volley.newRequestQueue(context);

                          RequestQueue rq = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory()));
rq.getCache().clear();
                            String url = con.base_url+"/api/employee/store/flexi_benefit";
                            StringRequest smr = new StringRequest(Request.Method.POST, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {

                                                Log.d("response",response);


                                                JSONObject jsonObject = new JSONObject(response);
                                                String errorCode = jsonObject.getString("message");

                                                String status = String.valueOf(jsonObject.getBoolean("status"));



                                                if(status.equalsIgnoreCase("false")){
                                                    new AlertDialog.Builder(context)
                                                            .setTitle("Error")
                                                            .setMessage(errorCode)
                                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                                            .setNegativeButton(android.R.string.ok, null).show();
                                                }else {

                                                    viewHolder.allocated_amt.setText(String.valueOf(selectedRadioButtonID));



                                                    String url = con.base_url+"/api/employee/get/flexi-details";
                                                   // RequestQueue mRequestQueue = Volley.newRequestQueue(context);

                                                   RequestQueue mRequestQueue = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory()));
                                                    mRequestQueue.getCache().clear();
                                                    StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String response) {

                                                            try {


                                                                try {
                                                                    JSONObject js=new JSONObject(response);

                                                                    Log.d("response",response);

                                                                    String status= String.valueOf(js.getBoolean("status"));
                                                                    if(status.equalsIgnoreCase("true")){
                                                                        JSONObject jsonObj=js.getJSONObject("data");

                                                                        String flexWallet= String.valueOf(jsonObj.getInt("flexWallet"));
                                                                        String walletUtilization= String.valueOf(jsonObj.getInt("walletUtilization"));
                                                                        String toPay= String.valueOf(jsonObj.getInt("toPay"));

                                                                        viewHolder.final_amount.setText(flexWallet);



                                                                        dialog.cancel();

                                                                    }



                                                                } catch (JSONException ex) {
                                                                    ex.printStackTrace();
                                                                }



                                                            } catch (Exception e) {
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



                                                    new AlertDialog.Builder(context)
                                                            .setTitle("Success")
                                                            .setMessage(errorCode)
                                                            .setIcon(R.drawable.checkmark)
                                                            .setNegativeButton(android.R.string.ok, null).show();
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
                                    return headers;
                                }
                            };












                            HashMap<String, String> params = new HashMap<>();
                            params.put("flexi_benefit_id", train.getFlexi_benefit_id());
                            params.put("flex_amount", String.valueOf(selectedRadioButtonID));

                            smr.setParams(params);
                            rq.add(smr);
























                        }
                    });








                    dialog.show();
                    Window window = dialog.getWindow();
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);






                }else {


                    final Dialog dialog = new Dialog(context);
                    dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.allocate_value_other);

                    ImageView image = dialog.findViewById(R.id.image);
                    ImageView ab = (ImageView) dialog.findViewById(R.id.ab);
                    TextView close = (TextView) dialog.findViewById(R.id.close);
                    EditText vol_rg = (EditText) dialog.findViewById(R.id.vol_rg);
                    TextView allocate = (TextView) dialog.findViewById(R.id.allocate);

                    TextView name = (TextView) dialog.findViewById(R.id.name);

                    name.setText(train.getFlexi_benefit_name());
                    ab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    });

                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    });

                    vol_rg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {



                        }
                    });

                    allocate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            int count =0;

                            if(vol_rg.getText().toString().length()==0){
                                vol_rg.setError("Please Enter Amount");
                                count++;
                            }

                            if(count==0){
                               // RequestQueue rq = Volley.newRequestQueue(context);

                              RequestQueue rq = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory()));
rq.getCache().clear();
                                String url = con.base_url+"/api/employee/store/flexi_benefit";
                                StringRequest smr = new StringRequest(Request.Method.POST, url,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                try {

                                                    Log.d("response",response);


                                                    JSONObject jsonObject = new JSONObject(response);
                                                    String errorCode = jsonObject.getString("message");

                                                    String status = String.valueOf(jsonObject.getBoolean("status"));



                                                    if(status.equalsIgnoreCase("false")){
                                                        new AlertDialog.Builder(context)
                                                                .setTitle("Error")
                                                                .setMessage(errorCode)
                                                                .setIcon(android.R.drawable.ic_dialog_alert)
                                                                .setNegativeButton(android.R.string.ok, null).show();
                                                    }else {

                                                       viewHolder.allocated_amt.setText(vol_rg.getText().toString());



                                                        String url = con.base_url+"/api/employee/get/flexi-details";
                                                        //RequestQueue mRequestQueue = Volley.newRequestQueue(context);

                                                      RequestQueue mRequestQueue = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory()));
                                                        mRequestQueue.getCache().clear();
                                                        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                                            @Override
                                                            public void onResponse(String response) {

                                                                try {


                                                                    try {
                                                                        JSONObject js=new JSONObject(response);

                                                                        Log.d("response",response);

                                                                        String status= String.valueOf(js.getBoolean("status"));
                                                                        if(status.equalsIgnoreCase("true")){
                                                                            JSONObject jsonObj=js.getJSONObject("data");

                                                                            String flexWallet= String.valueOf(jsonObj.getInt("flexWallet"));
                                                                            String walletUtilization= String.valueOf(jsonObj.getInt("walletUtilization"));
                                                                            String toPay= String.valueOf(jsonObj.getInt("toPay"));


                                                                            viewHolder.final_amount.setText(flexWallet);
                                                                            dialog.cancel();

                                                                        }



                                                                    } catch (JSONException ex) {
                                                                        ex.printStackTrace();
                                                                    }



                                                                } catch (Exception e) {
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


                                                        dialog.cancel();
                                                        new AlertDialog.Builder(context)
                                                                .setTitle("Success")
                                                                .setMessage(errorCode)
                                                                .setIcon(R.drawable.checkmark)
                                                                .setNegativeButton(android.R.string.ok, null).show();
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
                                        return headers;
                                    }
                                };











                                HashMap<String, String> params = new HashMap<>();
                                params.put("flexi_benefit_id", train.getFlexi_benefit_id());
                                params.put("flex_amount", vol_rg.getText().toString());


                                smr.setParams(params);
                                rq.add(smr);


                            }





                        }
                    });








                    dialog.show();
                    Window window = dialog.getWindow();
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);






                }


            }

        });
















    Picasso.get().load(train.getImage()).into(viewHolder.s_logo);




    }



    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mTrain.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView flex_name,allocated_amt,flex_discription,final_amount,allocate;
        public ImageView s_logo;



        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final Context context, View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            flex_name = (TextView) itemView.findViewById(R.id.flex_name);
            flex_discription = (TextView) itemView.findViewById(R.id.flex_discription);
            allocated_amt= (TextView) itemView.findViewById(R.id.allocated_amt);
            final_amount = (TextView) itemView.findViewById(R.id.final_amount);
            allocate = (TextView) itemView.findViewById(R.id.allocate);
            s_logo = (ImageView) itemView.findViewById(R.id.s_logo);

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
