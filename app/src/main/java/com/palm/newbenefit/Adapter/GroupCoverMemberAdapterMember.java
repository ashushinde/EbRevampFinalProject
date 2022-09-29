package com.palm.newbenefit.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.palm.newbenefit.Activity.EditMemberActivity;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.Module.VoluntaryBenefit;
import com.kmd.newbenefit.R;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class GroupCoverMemberAdapterMember extends RecyclerView.Adapter<GroupCoverMemberAdapterMember.ViewHolder> {

    private Context context;
    private List<VoluntaryBenefit> groupCoverMemberLists;
    private String userName,userGender,userRelation,userDOB;
    private Dialog myDialog;
    Constants con;

    public GroupCoverMemberAdapterMember(Context context, List<VoluntaryBenefit> groupCoverMemberLists) {
        this.context = context;
        this.groupCoverMemberLists = groupCoverMemberLists;
        con=new Constants();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();
        con=new Constants();

        myDialog = new Dialog(context);

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.member_enroll_list, parent, false);

        ViewHolder viewHolder = new ViewHolder(context, view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        VoluntaryBenefit groupCoverMemberList1 = groupCoverMemberLists.get(position);






    holder.relation.setText(groupCoverMemberList1.getRelation_name());


        if(groupCoverMemberList1.getEmployee_premium().equalsIgnoreCase("0.0")
                ||groupCoverMemberList1.getEmployee_premium().equalsIgnoreCase("0")
                ||groupCoverMemberList1.getEmployee_premium().equalsIgnoreCase("0.00")){
            holder.an_premium.setText("-");
            holder.annual.setVisibility(View.GONE);

        }else {

            try {
                int data= Integer.parseInt(groupCoverMemberList1.getEmployee_premium());

                String cover_data = NumberFormat.getNumberInstance(Locale.US).format(data);

                holder.an_premium.setText(cover_data);
            }catch (Exception e){
                holder.an_premium.setText(groupCoverMemberList1.getEmployee_premium());
            }

            if(groupCoverMemberList1.getNumber_of_time_salary().equalsIgnoreCase("3.00"))
            {
                holder.an_premium.setText("-");
                holder.annual.setVisibility(View.GONE);
            }else {
                holder.annual.setVisibility(View.VISIBLE);
            }


        }

        if(groupCoverMemberList1.getLast_name().equalsIgnoreCase("Self")){
            holder.delete.setVisibility(View.GONE);
            holder.edit.setVisibility(View.GONE);
        }else {
            SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);

            String   shown = prefs.getString("shown", null);


            if(shown.equalsIgnoreCase("all")){
                holder.delete.setVisibility(View.VISIBLE);
                holder.edit.setVisibility(View.VISIBLE);
            }else {
                holder.delete.setVisibility(View.GONE);
                holder.edit.setVisibility(View.GONE);
            }
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Dialog myDialog;

                myDialog = new Dialog(context);

                myDialog.setContentView(R.layout.member_view);
                VoluntaryBenefit groupCoverMemberList1 = groupCoverMemberLists.get(position);



                LinearLayout formember = myDialog.findViewById(R.id.formember);
                LinearLayout member_show = myDialog.findViewById(R.id.member_show);
                TextView marr = myDialog.findViewById(R.id.marr);
                LinearLayout mdate = myDialog.findViewById(R.id.mdate);
                LinearLayout sumdata=myDialog.findViewById(R.id.sumdata);
                LinearLayout pre_data=myDialog.findViewById(R.id.pre_data);
                LinearLayout mob_linear=myDialog.findViewById(R.id.mob_linear);

                formember.setVisibility(View.GONE);
                TextView sum= myDialog.findViewById(R.id.sum);
                TextView premium= myDialog.findViewById(R.id.premium);
                TextView relation= myDialog.findViewById(R.id.relation);
                TextView gender= myDialog.findViewById(R.id.gender);

                TextView fname= myDialog.findViewById(R.id.fname);
                TextView dob= myDialog.findViewById(R.id.dob);

                TextView age= myDialog.findViewById(R.id.age);
                TextView mob= myDialog.findViewById(R.id.mob);
                TextView email= myDialog.findViewById(R.id.email);
                TextView emp_premium= myDialog.findViewById(R.id.emp_premium);

                ImageView send= myDialog.findViewById(R.id.close);


                   relation.setText(groupCoverMemberList1.getRelation_name());


                gender.setText(groupCoverMemberList1.getGender());
                fname.setText(groupCoverMemberList1.getFirst_name());

                age.setText(groupCoverMemberList1.getAge()+" "+groupCoverMemberList1.getAgetype());

                if(groupCoverMemberList1.getMember_mob_no().equalsIgnoreCase("null")){
                    mob.setText("-");

                }else {
                    if(groupCoverMemberList1.getMember_mob_no().equalsIgnoreCase("false")){
                        mob_linear.setVisibility(View.GONE);
                    }else {
                        mob_linear.setVisibility(View.VISIBLE);
                    }
                    mob.setText(groupCoverMemberList1.getMember_mob_no());
                }

                if(groupCoverMemberList1.getMember_email().equalsIgnoreCase("null")){
                    email.setText("-");
                }else {
                    email.setText(groupCoverMemberList1.getMember_email());
                }


                if(groupCoverMemberList1.getMarriage_date().equalsIgnoreCase("null")
                ){
                    marr.setText("-");
                    mdate.setVisibility(View.GONE);
                }else if(groupCoverMemberList1.getMarriage_date().equalsIgnoreCase("0000-00-00")) {

                    marr.setText("-");
                    mdate.setVisibility(View.GONE);
                }else {
                    mdate.setVisibility(View.VISIBLE);


                    try{

                        String strCurrentDate = groupCoverMemberList1.getMarriage_date();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        Date newDate = format.parse(strCurrentDate);

                        format = new SimpleDateFormat("dd-MM-yyyy");

                        marr.setText(format.format(newDate));
                    }catch (Exception e){
                        marr.setText(groupCoverMemberList1.getMarriage_date());
                    }




                }



                if(groupCoverMemberList1.getEmployee_premium().equalsIgnoreCase("0.0")
                        ||groupCoverMemberList1.getEmployee_premium().equalsIgnoreCase("0")
                        ||groupCoverMemberList1.getEmployee_premium().equalsIgnoreCase("0.00")){
                    emp_premium.setText("-");
                }
                else {
                    emp_premium.setText(groupCoverMemberList1.getEmployee_premium());
                }


                if(groupCoverMemberList1.getSuminsured().equalsIgnoreCase("0.0")
                        ||groupCoverMemberList1.getSuminsured().equalsIgnoreCase("0")
                        ||groupCoverMemberList1.getSuminsured().equalsIgnoreCase("0.00")){
                    sum.setText("-");
                    sumdata.setVisibility(View.GONE);

                }else {
                    sum.setText(groupCoverMemberList1.getSuminsured());
                    sumdata.setVisibility(View.VISIBLE);
                }

                if(groupCoverMemberList1.getEmployee_premium().equalsIgnoreCase("0.0")
                        ||groupCoverMemberList1.getEmployee_premium().equalsIgnoreCase("0")
                        ||groupCoverMemberList1.getEmployee_premium().equalsIgnoreCase("0.00")){
                   premium.setText("-");
                   pre_data.setVisibility(View.GONE);
                }else {

                    if(groupCoverMemberList1.getNumber_of_time_salary().equalsIgnoreCase("3.00"))
                    {
                        premium.setText("-");
                        pre_data.setVisibility(View.GONE);

                    }else {
                        premium.setText(groupCoverMemberList1.getEmployee_premium());
                        pre_data.setVisibility(View.VISIBLE);

                    }
                }



                try{

                    String strCurrentDate = groupCoverMemberList1.getDob();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date newDate = format.parse(strCurrentDate);

                    format = new SimpleDateFormat("dd-MM-yyyy");

                    dob.setText(format.format(newDate));
                }catch (Exception e){
                    dob.setText(groupCoverMemberList1.getDob());
                }








                send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myDialog.dismiss();
                    }
                });

                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                myDialog.show();
            }




        });

        SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        String  policy_no = prefs.getString("policy_id", null);
        String emp_id = prefs.getString("api_token", null);





        holder.delete.setOnClickListener(new View.OnClickListener() {
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
                       // RequestQueue rq = Volley.newRequestQueue(context);

                     RequestQueue rq = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory()));
                     rq.getCache().clear();

                        VoluntaryBenefit train = groupCoverMemberLists.get(position);
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

                                                                int newPosition = holder.getAdapterPosition();
                                                                groupCoverMemberLists.remove(newPosition);
                                                                notifyItemRemoved(newPosition);
                                                                notifyItemRangeChanged(newPosition, groupCoverMemberLists.size());
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

                        smr.addStringParam("policy_id", policy_no);
                        smr.addStringParam("member_id", groupCoverMemberList1.getId());



                        rq.add(smr);
                    }
                }).show();

            }

        });


        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                Context context = v.getContext();
                VoluntaryBenefit train = groupCoverMemberLists.get(position);

                Intent intent = new Intent(context, EditMemberActivity.class);
                intent.putExtra("first_namef", train.getFirst_name());
                intent.putExtra("last_namef", train.getLast_name());
                intent.putExtra("agef", train.getMember_email());
                intent.putExtra("agetypef", train.getMember_mob_no());
                intent.putExtra("dobf", train.getDob());
                intent.putExtra("relationf", train.getRelation_name());
                intent.putExtra("family_idf", train.getRelation_id());
                intent.putExtra("genderf", train.getGender());
                intent.putExtra("member_idf", train.getId());
                intent.putExtra("mdob", train.getMarriage_date());



                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                context.startActivity(intent);
            }

        });
        VoluntaryBenefit groupCoverMemberList1h = groupCoverMemberLists.get(position);



        if((groupCoverMemberList1h.getRelation_name().equalsIgnoreCase("Self"))
        ||groupCoverMemberList1h.getRelation_name().equalsIgnoreCase("Spouse/Partner")){

            if(groupCoverMemberList1h.getGender().equalsIgnoreCase("Male")){
                holder.user.setBackgroundResource(R.drawable.man);
            }else {
                holder.user.setBackgroundResource(R.drawable.woman);
            }

        }
         if((groupCoverMemberList1h.getRelation_name().equalsIgnoreCase("Mother"))
                ||(groupCoverMemberList1h.getRelation_name().equalsIgnoreCase("Mother-In-Law"))){
            holder.user.setBackgroundResource(R.drawable.grandmother);
        }

         if(groupCoverMemberList1h.getRelation_name().equalsIgnoreCase("Father")
        ){
            holder.user.setBackgroundResource(R.drawable.grandfather);
        }

         if(groupCoverMemberList1h.getRelation_name().equalsIgnoreCase("Father-in-law")
        ){
            holder.user.setBackgroundResource(R.drawable.grandfather);
        }

         if(groupCoverMemberList1h.getRelation_name().equalsIgnoreCase("Mother")
        ){
            holder.user.setBackgroundResource(R.drawable.grandmother);
        }

         if(groupCoverMemberList1h.getRelation_name().equalsIgnoreCase("Mother-in-law")
        ){
            holder.user.setBackgroundResource(R.drawable.grandmother);
        }

         if(groupCoverMemberList1h.getRelation_name().equalsIgnoreCase("Daughter")
            ){
            holder.user.setBackgroundResource(R.drawable.girl);
        }
         if(groupCoverMemberList1h.getRelation_name().equalsIgnoreCase("Son")
        ){
            holder.user.setBackgroundResource(R.drawable.boy);
        }

        if(groupCoverMemberList1h.getRelation_name().equalsIgnoreCase("Siblings")
        ){
            holder.user.setBackgroundResource(R.drawable.man);
        }


        if(groupCoverMemberList1.getRelation_name().equalsIgnoreCase("Self")){
            holder.delete.setVisibility(View.GONE);
            holder.edit.setVisibility(View.GONE);
        }else {

            String   shown = prefs.getString("shown", null);


            if(shown.equalsIgnoreCase("all")){
                holder.delete.setVisibility(View.VISIBLE);
                holder.edit.setVisibility(View.VISIBLE);
            }else {
                holder.delete.setVisibility(View.GONE);
                holder.edit.setVisibility(View.GONE);
            }
        }


        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {


                Context context = v.getContext();


                VoluntaryBenefit train = groupCoverMemberLists.get(position);


                    new AlertDialog.Builder(context)
                            .setTitle("Alert")
                            .setMessage("Health E-card will be available post Enrollment window")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setNegativeButton(android.R.string.ok, null).show();
                /*} else {


                    DownloadManager downloadManager = null;
                    downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                    Uri uri = Uri.parse(train.getTpa_member_name());
                    DownloadManager.Request request = new DownloadManager.Request(uri);
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    Long reference = downloadManager.enqueue(request);
                }*/


            }
        });


    }

    @Override
    public int getItemCount() {
        return groupCoverMemberLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView relation;
        LinearLayout annual;
        TextView an_premium;
        public ImageView edit, delete, download,user;

        public ViewHolder(Context context, View itemView) {
            super(itemView);

            relation = itemView.findViewById(R.id.relation);
            annual= itemView.findViewById(R.id.annual);
            an_premium= itemView.findViewById(R.id.an_premium);

            edit = (ImageView) itemView.findViewById(R.id.edit);
            user = (ImageView) itemView.findViewById(R.id.user);

            delete = (ImageView) itemView.findViewById(R.id.delete);
            download = (ImageView) itemView.findViewById(R.id.download);
          //  itemView.setOnClickListener(this);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onClick(View view) {

            int position = getLayoutPosition();

            VoluntaryBenefit groupCoverMemberListClick = groupCoverMemberLists.get(position);

            userName = groupCoverMemberListClick.getFirst_name();
            userDOB = groupCoverMemberListClick.getDob();
            userGender = groupCoverMemberListClick.getGender();
            userRelation = groupCoverMemberListClick.getMember_email();

            //showDialog();


        }
    }

//    private void onNameClick(String policyNo, String userName) {
//
//
//        String url = "https://uat.lifekaplan-eb.com/policy_member_detail_api";
//        SimpleMultiPartRequest smr = new SimpleMultiPartRequest(Request.Method.POST, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
////                            Log.i("01230123","Response Click" + response);
//
//                            JSONObject jsonObjectResponse = new JSONObject(response);
//
//                            JSONArray policyView = jsonObjectResponse.getJSONArray("policy_view");
//
//                            for (int i = 0; i < policyView.length(); i++) {
//
//                                JSONObject selectedObject = policyView.getJSONObject(i);
//
//                                user_name = selectedObject.getString("emp_firstname") + " " + selectedObject.getString("emp_lastname");
//
////                                Log.i("989898", "Got Name = " + userName + "Api Name = " + user_name);
//
//                                if (user_name.equals(userName)) {
//
////                                    Log.i("989898", "In ");
//
//                                    nameFinal = selectedObject.getString("emp_firstname") + " " + selectedObject.getString("emp_lastname");
//                                    DOB = selectedObject.getString("bdate");
//                                    Relation = selectedObject.getString("fr_name");
//                                    Gender = selectedObject.getString("gender");
//
//                                    break;
//
//                                } else {
//
//                                    nameFinal = userName;
//                                    Relation = "Self";
//                                    Gender = prefsGender;
//                                    DOB = "";
//
//                                }
//                            }
//
//                            showDialog();
//
////                            Log.i("787878","User Details - "+userName+ " " +DOB+" "+ Relation+" "+Gender);
//
//                        } catch (Exception e) {
//                            // Log.d("Exception ", e.getMessage());
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//
//        smr.addStringParam("emp_id", empId);
//        smr.addStringParam("policy_no", policyNo);
//
////        Log.i("01230123","EMP = "+empId+ " "+policyNo);
//
//        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
//        mRequestQueue.add(smr);
//    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void showDialog() {

        TextView name, dob, gender, relation;
        ImageView dismiss;


        myDialog.setContentView(R.layout.group_cover_info_popup);

        name = myDialog.findViewById(R.id.dialogUserName);
        dob = myDialog.findViewById(R.id.dialogUserDOB);
        gender = myDialog.findViewById(R.id.dialogUserGender);
        relation = myDialog.findViewById(R.id.dialogUserRelation);
        dismiss = myDialog.findViewById(R.id.dialogCancel);

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });


        try{

            String strCurrentDate = userName;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date newDate = format.parse(strCurrentDate);

            format = new SimpleDateFormat("dd-MM-yyyy");

            name.setText(format.format(newDate));
        }catch (Exception e){
            name.setText(userName);
        }
        dob.setText(userDOB);
        gender.setText(userGender);
        relation.setText(userRelation);

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        myDialog.show();
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

