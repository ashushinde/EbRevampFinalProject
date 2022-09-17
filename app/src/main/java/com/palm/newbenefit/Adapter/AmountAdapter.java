package com.palm.newbenefit.Adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.Module.amount;
import com.palm.newbenefit.R;

import java.util.List;

public class AmountAdapter extends
        RecyclerView.Adapter<AmountAdapter.ViewHolder> {
Constants con;
    private List<amount> mTrain;
    private Context context = null;
    private String compareCount = null;
    // Pass in the contact array into the constructor
    public AmountAdapter(Context context, List<amount> train) {
        this.context = context;
        mTrain = train;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
         con=new Constants();
        // Inflate the custom layout
        View trainingView = inflater.inflate(R.layout.amount_list, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(context, trainingView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        amount train = mTrain.get(position);
        con=new Constants();
        // Set item views based on your views and data model
        //TextView type = viewHolder.p_type;
        TextView cover = viewHolder.amt;

        cover.setText(train.getAmt());


        viewHolder.amt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               compareCount=viewHolder.amt.getText().toString();
               // ((coreAc)context).getCompareCount();

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
        public RadioButton amt;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final Context context, View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

           // p_type = (TextView) itemView.findViewById(R.id.p_type);
            amt = (RadioButton) itemView.findViewById(R.id.amt);





        }
    }
}
