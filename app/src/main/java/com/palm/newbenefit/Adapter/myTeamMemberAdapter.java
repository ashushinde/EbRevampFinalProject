package com.palm.newbenefit.Adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
import com.palm.newbenefit.Activity.DemoActivty;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.Module.Member_Team;
import com.palm.newbenefit.Module.SpinnerModal;
import com.kmd.newbenefit.R;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

;import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class myTeamMemberAdapter extends RecyclerView.Adapter<myTeamMemberAdapter.ViewHolder> {
    Constants con;
    private List<Member_Team> mTrain;
    private Context context = null;
    private ScaleGestureDetector scaleGestureDetector;
    private float mScaleFactor = 1.0f;
    ProgressDialog progressDialog = null;
    // Pass in the contact array into the constructor
    public myTeamMemberAdapter(Context context, List<Member_Team> train) {
        this.context = context;
        mTrain = train;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        con = new Constants();
        // Inflate the custom layout
        View trainingView = inflater.inflate(R.layout.team_member_list, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(context, trainingView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        Member_Team train = mTrain.get(position);
        con = new Constants();
        // Set item views based on your views and data model
        //TextView type = viewHolder.p_type;
        TextView cover = viewHolder.cov;
        TextView member = viewHolder.m_cov;
        TextView covera = viewHolder.cover;



            viewHolder.frogm.setText(train.getName());
        viewHolder.company_name.setText(train.getDesignation_name());

        viewHolder.days.setText(train.getEmail());
        viewHolder.m_cov.setText(train.getContact_no());






        viewHolder.status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder alerdialog = new AlertDialog.Builder(context);

                alerdialog.setTitle("Delete Member");
                alerdialog.setMessage("Are you sure you want to delete this member?");
                alerdialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alerdialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Member_Team train = mTrain.get(position);
                        String url = con.base_url+"/api/admin/delete/team-member-details/"+train.getId();
                       // RequestQueue mRequestQueue = Volley.newRequestQueue(context);

                        RequestQueue mRequestQueue = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory()));
                        mRequestQueue.getCache().clear();
                        StringRequest mStringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {



                                            JSONObject json=new JSONObject(response);
                                            String status= String.valueOf(json.getBoolean("status"));
                                            String message=json.getString("message");

                                            if(status.equalsIgnoreCase("true")){
                                                new AlertDialog.Builder(context)
                                                        .setTitle("Success")
                                                        .setMessage(message)
                                                        .setIcon(R.drawable.checkmark)
                                                        .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                                int newPosition = viewHolder.getAdapterPosition();
                                                                mTrain.remove(newPosition);
                                                                notifyItemRemoved(newPosition);
                                                                notifyItemRangeChanged(newPosition, mTrain.size());
                                                            }
                                                        }).show();

                                            }else {
                                                new AlertDialog.Builder(context)
                                                        .setTitle("Error")
                                                        .setMessage(message)
                                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                                        .setNegativeButton(android.R.string.ok, null).show();
                                            }


                                        } catch (Exception e) {
                                            //progressDialog.dismiss();

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
                                SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);

                             String   emp_id = prefs.getString("api_token", null);

                                headers.put("Authorization", "Bearer " + emp_id);
                                return headers;
                            }
                        };

                        mRequestQueue.add(mStringRequest);
                    }
                }).show();

            }

        });





        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {


                showDialog(position);

            }

        });


    }
    public static boolean isValid(String s) {
        // The given argument to compile() method
        // is regular expression. With the help of
        // regular expression we can validate mobile
        // number.
        // 1) Begins with 0 or 91
        // 2) Then contains 7 or 8 or 9.
        // 3) Then contains 9 digits
        Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}");

        // Pattern class contains matcher() method
        // to find matching between given number
        // and regular expression
        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }
    private void showDialog(int position) {
        Dialog myDialog;
        Member_Team train = mTrain.get(position);
        myDialog = new Dialog(context);
        String selBankPath_cheque=null;

        ImageView dismiss;

        myDialog.setContentView(R.layout.add_member);

        dismiss = myDialog.findViewById(R.id.ab);
        RelativeLayout adhaarBackLayout_cheque1= myDialog.findViewById(R.id.adhaarBackLayout_cheque1);

        EditText name= myDialog.findViewById(R.id.name);
        Spinner industry_type= myDialog.findViewById(R.id.industry_type);
        EditText email_id= myDialog.findViewById(R.id.email_id);
        EditText mobile= myDialog.findViewById(R.id.mobile);

        Button btn_policy= myDialog.findViewById(R.id.btn_policy);
        Button send= myDialog.findViewById(R.id.send);


        name.setText(train.getName());
        email_id.setText(train.getEmail());
        mobile.setText(train.getContact_no());

     

        String url = con.base_url+"/api/admin/get/rfq-config-data";
       // RequestQueue mRequestQueue = Volley.newRequestQueue(context);
       RequestQueue mRequestQueue = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);
                    ArrayList<String> bank_name = null;
                    ArrayList<SpinnerModal> bank_nameList = null;
                    ArrayAdapter<SpinnerModal> bank_nameAdapter = null;

                    Log.d("response",response);
                    JSONObject data=js.getJSONObject("data");
                    JSONArray jsonArr=data.getJSONArray("industries");
                    bank_name = new ArrayList<>();
                    bank_nameList = new ArrayList<>();

                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObj = jsonArr.getJSONObject(i);


                        bank_nameList.add(new SpinnerModal(jsonObj.getString("id"), jsonObj.getString("name")));
                        bank_name.add(jsonObj.getString("name"));

                    }
                    bank_nameAdapter = new ArrayAdapter<SpinnerModal>(context, R.layout.spinner_item, bank_nameList);
                    bank_nameAdapter.setDropDownViewResource(R.layout.spinner_item);

                    industry_type.setAdapter(bank_nameAdapter);


                    if (!train.getDesignation_id().equals(""))
                        industry_type.setSelection(bank_nameAdapter.getPosition(bank_nameList.get(bank_name.indexOf(train.getDesignation_id()))));


                } catch (Exception e) {


                }

            }
        },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onErrorResponse", error.toString());
            }
        }) {

        };
        mRequestQueue.add(mStringRequest);


        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });

        btn_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });





        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count=0;

                if (name.getText().toString().trim().length() == 0) {
                    ++count;
                    name.setError(" name is Required");
                } else {
                    name.setError(null);
                }
                if (mobile.getText().toString().trim().isEmpty() || !isValid(mobile.getText().toString().trim()) || mobile.getText().toString().length() < 10 || mobile.getText().toString().trim().length() > 13) {
                    ++count;
                    mobile.setError("Invalid Mobile");
                } else {
                    mobile.setError(null);
                }


                if (email_id.getText().toString().trim().length() == 0) {
                    ++count;
                    email_id.setError("Enter  email id");
                } else {
                    email_id.setError(null);
                }


                if(Patterns.EMAIL_ADDRESS.matcher(email_id.getText().toString().trim()).matches()){
                    email_id.setError(null);
                }else {
                    email_id.setError("Invalid Email");
                    ++count;
                }





                SpinnerModal patient = (SpinnerModal) industry_type.getSelectedItem();
                if (patient.selValue.equalsIgnoreCase("")) {
                    ++count;
                    new AlertDialog.Builder(context)
                            .setTitle("Error")
                            .setMessage("Please Select Industry type")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setNegativeButton(android.R.string.ok, null).show();
                    return;
                }
                if(count==0){
                    AddMember(name.getText().toString().trim(),email_id.getText().toString().trim(),mobile.getText().toString().trim(),patient.getSelKey());
                }
            }
        });

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        myDialog.show();
    }

    private void AddMember(String name,String emailid,String Mobile,String designation) {

        progressDialog = ProgressDialog.show(context, "",
                "Saving. Please wait...", true);
     //   RequestQueue rq = Volley.newRequestQueue(context);


     RequestQueue rq = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        String url = con.base_url + "/api/admin/update/team-member-details";
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

                                new AlertDialog.Builder(context)
                                        .setTitle("Success")
                                        .setMessage(message)
                                        .setIcon(android.R.drawable.ic_dialog_info)
                                        .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                Intent intent = new Intent(context, DemoActivty.class);    //jump to next activity
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);



                                            }
                                        }).show();

                            }else {

                                new AlertDialog.Builder(context)
                                        .setTitle("Error")
                                        .setMessage(message)
                                        .setIcon(android.R.drawable.ic_dialog_info)
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
                SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);

                String token = prefs.getString("api_token", null);
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };









        HashMap<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("email", emailid);
        params.put("contact_no", Mobile);

        params.put("designation_id", designation);

        smr.setParams(params);
        rq.add(smr);
    }
        // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mTrain.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView p_type,opd_cover,days,company_name, m_cov, cov, cover, premium, frogm, premium_data;
        public ImageView edit, status;
        LinearLayout balance, pre,opd,doc;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final Context context, View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            frogm = (TextView) itemView.findViewById(R.id.frogm);

            m_cov = (TextView) itemView.findViewById(R.id.m_cov);
            cov = (TextView) itemView.findViewById(R.id.cov);

            // p_img = (ImageView) itemView.findViewById(R.id.p_img);
            cover = (TextView) itemView.findViewById(R.id.cover);
            premium_data = (TextView) itemView.findViewById(R.id.premium_data);
            balance = (LinearLayout) itemView.findViewById(R.id.balance);
            pre = (LinearLayout) itemView.findViewById(R.id.pre);
            opd= (LinearLayout) itemView.findViewById(R.id.opd);
            opd_cover= (TextView) itemView.findViewById(R.id.opd_cover);
            doc= (LinearLayout) itemView.findViewById(R.id.doc);
            status= (ImageView) itemView.findViewById(R.id.status);
            company_name= (TextView) itemView.findViewById(R.id.company_name);
            days= (TextView) itemView.findViewById(R.id.days);
            edit= itemView.findViewById(R.id.edit);
        }
    }



    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView = imageView;
            //Toast.makeText(getApplicationContext(), "Please wait, it may take a few minute...", Toast.LENGTH_SHORT).show();
        }

        protected Bitmap doInBackground(String... urls) {
            String imageURL = urls[0];
            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bimage = BitmapFactory.decodeStream(in);

            } catch (Exception e) {

                e.printStackTrace();
            }
            return bimage;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
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
