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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.Module.VoluntaryBenefit;
import com.palm.newbenefit.R;

import java.util.Date;
import java.util.List;

public class GroupCoverNomineeAdapter extends RecyclerView.Adapter<GroupCoverNomineeAdapter.ViewHolder> {

    private Context context;
    private List<VoluntaryBenefit> groupCoverMemberLists;
    private String userName,userGender,userRelation,userDOB;
    private Dialog myDialog;
    Constants con;

    public GroupCoverNomineeAdapter(Context context, List<VoluntaryBenefit> groupCoverMemberLists) {
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

        View view = inflater.inflate(R.layout.nominne_list_summary, parent, false);

        ViewHolder viewHolder = new ViewHolder(context, view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        VoluntaryBenefit groupCoverMemberList1 = groupCoverMemberLists.get(position);

        if(groupCoverMemberList1.getLast_name().equalsIgnoreCase("null")){
            holder.name.setText(groupCoverMemberList1.getFirst_name());
        }else {
            holder.name.setText(groupCoverMemberList1.getFirst_name()+" " +groupCoverMemberList1.getLast_name());
        }

        if(groupCoverMemberList1.getGender().equalsIgnoreCase("null")){
            holder.gName.setText("-");
            holder.grelation.setText("-");
        }else {
            if(groupCoverMemberList1.getMember_email().equalsIgnoreCase("null")){
                holder.gName.setText(groupCoverMemberList1.getGender());
            }else {
                holder.gName.setText(groupCoverMemberList1.getGender()+" " +groupCoverMemberList1.getMember_email());
            }

            holder.grelation.setText(groupCoverMemberList1.getMember_mob_no());
        }


        holder.relation.setText(groupCoverMemberList1.getEmployee_premium());



            holder.suminsured.setText(groupCoverMemberList1.getSuminsured() +" " +"%");



    }

    @Override
    public int getItemCount() {
        return groupCoverMemberLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name,suminsured,gName,grelation,relation;

        public ViewHolder(Context context, View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.memberName);
            suminsured= itemView.findViewById(R.id.suminsured);
            gName= itemView.findViewById(R.id.gName);
            grelation= itemView.findViewById(R.id.grelation);
            relation= itemView.findViewById(R.id.relation);
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

