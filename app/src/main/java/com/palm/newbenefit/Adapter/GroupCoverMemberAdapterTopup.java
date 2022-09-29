package com.palm.newbenefit.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.Module.VoluntaryBenefit;
import com.kmd.newbenefit.R;

import java.util.Date;
import java.util.List;

public class GroupCoverMemberAdapterTopup extends RecyclerView.Adapter<GroupCoverMemberAdapterTopup.ViewHolder> {

    private Context context;
    private List<VoluntaryBenefit> groupCoverMemberLists;
    private String userName,userGender,userRelation,userDOB;
    private Dialog myDialog;
    Constants con;

    public GroupCoverMemberAdapterTopup(Context context, List<VoluntaryBenefit> groupCoverMemberLists) {
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

        View view = inflater.inflate(R.layout.member_list_summary_view, parent, false);

        ViewHolder viewHolder = new ViewHolder(context, view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        VoluntaryBenefit groupCoverMemberList1 = groupCoverMemberLists.get(position);






        holder.relation.setText(groupCoverMemberList1.getRelation_name());


        if((groupCoverMemberList1.getRelation_name().equalsIgnoreCase("Self"))
                ||groupCoverMemberList1.getRelation_name().equalsIgnoreCase("Spouse/Partner")){

            if(groupCoverMemberList1.getGender().equalsIgnoreCase("Male")){
                holder.user.setBackgroundResource(R.drawable.man);
            }else {
                holder.user.setBackgroundResource(R.drawable.woman);
            }

        }
        if((groupCoverMemberList1.getRelation_name().equalsIgnoreCase("Mother"))
                ||(groupCoverMemberList1.getRelation_name().equalsIgnoreCase("Mother-In-Law"))){
            holder.user.setBackgroundResource(R.drawable.grandmother);
        }

        if(groupCoverMemberList1.getRelation_name().equalsIgnoreCase("Father")
        ){
            holder.user.setBackgroundResource(R.drawable.grandfather);
        }

        if(groupCoverMemberList1.getRelation_name().equalsIgnoreCase("Father-in-law")
        ){
            holder.user.setBackgroundResource(R.drawable.grandfather);
        }

        if(groupCoverMemberList1.getRelation_name().equalsIgnoreCase("Mother")
        ){
            holder.user.setBackgroundResource(R.drawable.grandmother);
        }

        if(groupCoverMemberList1.getRelation_name().equalsIgnoreCase("Mother-in-law")
        ){
            holder.user.setBackgroundResource(R.drawable.grandmother);
        }

        if(groupCoverMemberList1.getRelation_name().equalsIgnoreCase("Daughter")
        ){
            holder.user.setBackgroundResource(R.drawable.girl);
        }
        if(groupCoverMemberList1.getRelation_name().equalsIgnoreCase("Son")
        ){
            holder.user.setBackgroundResource(R.drawable.boy);
        }



        if(groupCoverMemberList1.getEmployee_premium().equalsIgnoreCase("0.0")
                ||groupCoverMemberList1.getEmployee_premium().equalsIgnoreCase("0")
                ||groupCoverMemberList1.getEmployee_premium().equalsIgnoreCase("0.00")){
            holder.an_premium.setText("-");
            holder.annual.setVisibility(View.GONE);

        }else {
            holder.an_premium.setText(groupCoverMemberList1.getEmployee_premium());
            holder.annual.setVisibility(View.VISIBLE);

        }




        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Dialog myDialog;
                VoluntaryBenefit groupCoverMemberList1 = groupCoverMemberLists.get(position);

                myDialog = new Dialog(context);

                myDialog.setContentView(R.layout.member_view);


                TextView sum= myDialog.findViewById(R.id.sum);
                TextView premium= myDialog.findViewById(R.id.premium);
                TextView relation= myDialog.findViewById(R.id.relation);
                TextView gender= myDialog.findViewById(R.id.gender);
                TextView marr = myDialog.findViewById(R.id.marr);
                LinearLayout mdate = myDialog.findViewById(R.id.mdate);

                TextView fname= myDialog.findViewById(R.id.fname);
                TextView dob= myDialog.findViewById(R.id.dob);

                TextView age= myDialog.findViewById(R.id.age);
                TextView mob= myDialog.findViewById(R.id.mob);
                TextView email= myDialog.findViewById(R.id.email);
                TextView emp_premium= myDialog.findViewById(R.id.emp_premium);
                LinearLayout member_show= myDialog.findViewById(R.id.member_show);
                ImageView send= myDialog.findViewById(R.id.close);


                relation.setText(groupCoverMemberList1.getLast_name());


                gender.setText(groupCoverMemberList1.getGender());
                fname.setText(groupCoverMemberList1.getFirst_name());

                age.setText(groupCoverMemberList1.getAge()+" "+groupCoverMemberList1.getAgetype());

                if(groupCoverMemberList1.getMember_mob_no().equalsIgnoreCase("null")){
                    mob.setText("-");
                }else {
                    mob.setText(groupCoverMemberList1.getMember_mob_no());
                }

                if(groupCoverMemberList1.getMember_email().equalsIgnoreCase("null")){
                    email.setText("-");
                }else {
                    email.setText(groupCoverMemberList1.getMember_email());
                }

                if(groupCoverMemberList1.getMarriage_date().equalsIgnoreCase("null")){
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
                    member_show.setVisibility(View.GONE);
                }else {
                    emp_premium.setText(groupCoverMemberList1.getEmployee_premium());
                    member_show.setVisibility(View.VISIBLE);
                }


                if(groupCoverMemberList1.getAllsum().equalsIgnoreCase("0")){
                    sum.setText("-");

                }else {
                    sum.setText(groupCoverMemberList1.getAllsum());

                }



                if(groupCoverMemberList1.getAllpremium().equalsIgnoreCase("0.0")
                        ||groupCoverMemberList1.getAllpremium().equalsIgnoreCase("0")
                        ||groupCoverMemberList1.getAllpremium().equalsIgnoreCase("0.00")){
                    premium.setText("-");
                }else {
                    premium.setText(groupCoverMemberList1.getAllpremium());
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


    }
    @Override
    public int getItemCount() {
        return groupCoverMemberLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView relation;
        LinearLayout annual;
        TextView an_premium;
        ImageView user;


        public ViewHolder(Context context, View itemView) {
            super(itemView);

            relation = itemView.findViewById(R.id.relation);
            annual= itemView.findViewById(R.id.annual);
            an_premium= itemView.findViewById(R.id.an_premium);
            user= itemView.findViewById(R.id.user);
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

}

