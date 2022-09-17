package com.palm.newbenefit.Adapter;

import android.app.DatePickerDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.palm.newbenefit.Activity.EditMemberActivity;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.Module.MemberEnroll;
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
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class MemberEnrollAdapterBEnefit extends
        RecyclerView.Adapter<MemberEnrollAdapterBEnefit.ViewHolder> {
    Constants con;
    private List<MemberEnroll> mTrain;
    private Context context = null;
    String emp_id,policy_no;

    // Pass in the contact array into the constructor
    public MemberEnrollAdapterBEnefit(Context context, List<MemberEnroll> train, String emp_id) {
        this.context = context;
        mTrain = train;
        emp_id = emp_id;
        con=new Constants();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        con = new Constants();
        // Inflate the custom layout
        View trainingView = inflater.inflate(R.layout.member_enroll_benefitlist, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(context, trainingView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        MemberEnroll train = mTrain.get(position);
        con = new Constants();
        // Set item views based on your views and data model

        DatePickerDialog fromDatePickerDialog;
        viewHolder.relation.setText(train.getFr_name());
        viewHolder.first_name.setText(train.getEmp_firstname());
        viewHolder.last_name.setText(train.getEmp_lastname());
        viewHolder.gender.setText(train.getGender());
        viewHolder.dob.setText(train.getBdate());
        viewHolder.age.setText(train.getAge());
        viewHolder.age_type.setText(train.getAge_type());


        if(train.getFr_name().equalsIgnoreCase("Self")){
            viewHolder.delete.setVisibility(View.GONE);
        }



        if(train.getPolicy_mem_sum_insured().equalsIgnoreCase("null")||train.getPolicy_mem_sum_insured().isEmpty()){
            viewHolder.fam_cover.setText(train.getPolicy_mem_sum_insured());
        }else {

            try{
                int data= Integer.parseInt(train.getPolicy_mem_sum_insured());

                String cover_data = NumberFormat.getNumberInstance(Locale.US).format(data);
                viewHolder.fam_cover.setText(cover_data);
            }catch (Exception e){
                viewHolder.fam_cover.setText(train.getPolicy_mem_sum_insured());
            }

        }







        if(train.getPolicy_mem_sum_insured().equalsIgnoreCase("null")||train.getPolicy_mem_sum_insured().equalsIgnoreCase("0")){
            viewHolder.hidden.setVisibility(View.GONE);
        }else {
            viewHolder.hidden.setVisibility(View.VISIBLE);
        }





        if (train.getEmployee_policy_mem_sum_premium().equalsIgnoreCase("0")||train.getEmployee_policy_mem_sum_premium().equalsIgnoreCase("null")||
                train.getEmployee_policy_mem_sum_premium().isEmpty()) {


            viewHolder.premium.setText(train.getEmployee_policy_mem_sum_premium());
            viewHolder.prem.setVisibility(View.GONE);
        } else {

            try{

                viewHolder.premium.setText(train.getEmployee_policy_mem_sum_premium());
                viewHolder.prem.setVisibility(View.VISIBLE);
            }catch (Exception e){

            }


        }


        Calendar newCalendar;
        SimpleDateFormat dateFormatter;
        newCalendar = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        fromDatePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                viewHolder.dob.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


       /* viewHolder.dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDatePickerDialog.show();
            }
        });*/

       // RequestQueue rq = Volley.newRequestQueue(context);


        RequestQueue rq = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();

        String url = con.base_url+"/get_profile_per_details";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {


                            JSONObject jsonObjh = new JSONObject(response);


                            JSONObject jsonObj = jsonObjh.getJSONObject("flag");


                            String benifit_all_data=jsonObj.getString("benifit_all_data");
                            String nominee_data=jsonObj.getString("nominee_data");
                            String subQuery1=jsonObj.getString("subQuery1");


                            String enrollment_flag=jsonObj.getString("enrollment_flag");

                            String can_edit=jsonObjh.getString("can_edit");
                            if(((!(benifit_all_data.equalsIgnoreCase(null)) && (!(nominee_data.equalsIgnoreCase(null)) && (subQuery1 .equalsIgnoreCase("Y")))||(enrollment_flag.equalsIgnoreCase("Y"))))){
                                viewHolder.delete.setVisibility(View.GONE);
                                viewHolder.edit.setVisibility(View.GONE);
                                viewHolder.download.setVisibility(View.GONE);
                            }
                            else if((!(benifit_all_data.equalsIgnoreCase("null")) || (subQuery1 .equalsIgnoreCase("Y"))||(enrollment_flag.equalsIgnoreCase("Y")))){
                                viewHolder.delete.setVisibility(View.GONE);
                                viewHolder.edit.setVisibility(View.GONE);
                                viewHolder.download.setVisibility(View.GONE);
                            }
                            else {

                                if(train.getFr_name().equalsIgnoreCase("Self")){

                                    viewHolder.delete.setVisibility(View.GONE);
                                    viewHolder.edit.setVisibility(View.GONE);
                                    viewHolder.download.setVisibility(View.VISIBLE);

                                }else {
                                    Log.d("a","3");
                                    if (can_edit.equalsIgnoreCase("0")) {
                                        viewHolder.delete.setVisibility(View.GONE);
                                        viewHolder.edit.setVisibility(View.GONE);
                                        viewHolder.download.setVisibility(View.VISIBLE);
                                        Log.d("b","a");

                                    }else {
                                        Log.d("b","b");
                                        viewHolder.delete.setVisibility(View.VISIBLE);
                                        viewHolder.edit.setVisibility(View.VISIBLE);
                                        viewHolder.download.setVisibility(View.VISIBLE);
                                    }


                                }



                            }





                        } catch (Exception e) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        policy_no = prefs.getString("policy_no", null);
        emp_id = prefs.getString("api_token", null);




        HashMap<String, String> params = new HashMap<>();
        params.put("emp_id", emp_id);
        params.put("policy_no",policy_no);

        smr.setParams(params);
        rq.add(smr);

        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
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
                        RequestQueue rq = Volley.newRequestQueue(context);


                      //  RequestQueue rq = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory()));

                        MemberEnroll train = mTrain.get(position);
                        String url = con.base_url+"/api/employee/remove/family/members";
                        SimpleMultiPartRequest smr = new SimpleMultiPartRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
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
                                headers.put("Authorization", "Bearer " + emp_id);
                                return headers;
                            }
                        };

                        smr.addStringParam("policy_id", "6");
                        smr.addStringParam("member_id", train.getMember_id());


                        Log.d("policy_id", "6");
                        Log.d("member_id", train.getMember_id());

                        rq.add(smr);
                    }
                }).show();

            }

        });


        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                Context context = v.getContext();
                MemberEnroll train = mTrain.get(position);

                Intent intent = new Intent(context, EditMemberActivity.class);
                intent.putExtra("first_namef", train.getEmp_firstname());
                intent.putExtra("last_namef", train.getEmp_lastname());
                intent.putExtra("agef", train.getAge());
                intent.putExtra("agetypef", train.getAge_type());
                intent.putExtra("dobf", train.getBdate());
                intent.putExtra("relationf", train.getFr_name());
                intent.putExtra("family_idf", train.getFamily_id());
                intent.putExtra("genderf", train.getGender());
                intent.putExtra("member_idf", train.getPolicy_member_id());
                intent.putExtra("fam_idf", train.getFamily_id());
                intent.putExtra("policy_detail_idf", train.getPolicy_detail_id());
                intent.putExtra("frid", train.getFr_id());
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                context.startActivity(intent);
            }

        });

        viewHolder.expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                if(viewHolder.hide.getVisibility() == View.VISIBLE){

                    viewHolder.hide.setVisibility(View.GONE);
                    viewHolder.expand.setImageResource(R.drawable.ic_add_black_24dp);
                    viewHolder.know.setText("View More");

                }
                else {

                    viewHolder.hide.setVisibility(View.VISIBLE);
                    viewHolder.expand.setImageResource(R.drawable.ic_remove_black_24dp);
                    viewHolder.know.setText("View Less");
                }



            }

        });



        viewHolder.know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                if(viewHolder.hide.getVisibility() == View.VISIBLE){

                    viewHolder.hide.setVisibility(View.GONE);
                    viewHolder.expand.setImageResource(R.drawable.ic_add_black_24dp);
                    viewHolder.know.setText("View More");
                }
                else {
                    viewHolder.hide.setVisibility(View.VISIBLE);

                    viewHolder.expand.setImageResource(R.drawable.ic_remove_black_24dp);
                    viewHolder.know.setText("View Less");
                }



            }

        });


        viewHolder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {


                Context context = v.getContext();


                MemberEnroll train = mTrain.get(position);

                if (train.getTpa_member_name().equalsIgnoreCase("null") || train.getTpa_member_name().equalsIgnoreCase("")) {
                    new AlertDialog.Builder(context)
                            .setTitle("Alert")
                            .setMessage("Health E-card will be available post Enrollment window")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setNegativeButton(android.R.string.ok, null).show();
                } else {


        DownloadManager downloadManager = null;
        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(train.getTpa_member_name());
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        Long reference = downloadManager.enqueue(request);
 }


            }
        });


    }


    @Override
    public int getItemCount() {
        return mTrain.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView relation, first_name, last_name, gender, dob, age, age_type, fam_cover, premium,know;
        public ImageView edit, expand, delete, download;
        LinearLayout hide, prem,hidden;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final Context context, View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            relation = (TextView) itemView.findViewById(R.id.relation);

            first_name = (TextView) itemView.findViewById(R.id.first_name);
            know= (TextView) itemView.findViewById(R.id.know);
            last_name = (TextView) itemView.findViewById(R.id.last_name);
            gender = (TextView) itemView.findViewById(R.id.gender);
            age_type = (TextView) itemView.findViewById(R.id.age_type);
            age = (TextView) itemView.findViewById(R.id.age);
            fam_cover = (TextView) itemView.findViewById(R.id.fam_cover);
            premium = (TextView) itemView.findViewById(R.id.premium);
            dob = (TextView) itemView.findViewById(R.id.dob);
            edit = (ImageView) itemView.findViewById(R.id.edit);
            expand = (ImageView) itemView.findViewById(R.id.expand);

            hide = (LinearLayout) itemView.findViewById(R.id.hide);
            delete = (ImageView) itemView.findViewById(R.id.delete);
            download = (ImageView) itemView.findViewById(R.id.download);
            prem = (LinearLayout) itemView.findViewById(R.id.prem);
            hidden= (LinearLayout) itemView.findViewById(R.id.hidden);
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
