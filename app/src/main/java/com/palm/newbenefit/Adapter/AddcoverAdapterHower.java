package com.palm.newbenefit.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.Module.AddCover;
import com.palm.newbenefit.R;

import java.util.List;

public class AddcoverAdapterHower extends
        RecyclerView.Adapter<AddcoverAdapterHower.ViewHolder> {
    Constants con;
    private List<AddCover> mTrain;
    private Context context = null;
    String Flex_Wallet,toPay,wallet_utilization;

    String dtype_vol;
    String emp_id;
    int payroll,walletFlex;
    int index = 0;
    // Pass in the contact array into the constructor
    public AddcoverAdapterHower(Context context, List<AddCover> train, String emp_id) {
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
        View trainingView = inflater.inflate(R.layout.box_list, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(context, trainingView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);

        emp_id = prefs.getString("api_token", null);

        // Get the data model based on position
        AddCover train = mTrain.get(position);
        con = new Constants();

            viewHolder.name.setText(train.getPolicy_sub_type_name());


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = position;


                notifyDataSetChanged();
            }
        });

        if(index==position){
            viewHolder.boxes.setBackgroundResource(R.drawable.clickcover);




        }else{
            viewHolder.boxes.setBackgroundResource(R.drawable.cover_hover);



        }















    }




    @Override
    public int getItemCount() {
        return mTrain.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView name;
        RecyclerView  additionalInfo;
        LinearLayout boxes;




        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final Context context, View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.top_up);
            additionalInfo= (RecyclerView) itemView.findViewById(R.id.hos_claim_recycle);
            boxes= (LinearLayout) itemView.findViewById(R.id.boxes);

        }
    }


}
