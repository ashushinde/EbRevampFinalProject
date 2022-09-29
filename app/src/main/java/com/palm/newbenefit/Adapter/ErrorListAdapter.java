package com.palm.newbenefit.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.Module.SpinnerModal;
import com.kmd.newbenefit.R;

import java.util.List;

public class ErrorListAdapter extends
        RecyclerView.Adapter<ErrorListAdapter.ViewHolder> {

    private List<SpinnerModal> mTrain;
    private Context context = null;
    Constants con;
    String check="0";
    // Pass in the contact array into the constructor
    public ErrorListAdapter(Context context, List<SpinnerModal> train) {

        this.context = context;
        mTrain = train;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View trainingView = inflater.inflate(R.layout.error_list, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(context, trainingView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        SpinnerModal train = mTrain.get(position);





        viewHolder.error_name.setText(train.getSelKey());









    }






    @Override
    public int getItemCount() {
        return mTrain.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView error_name;



        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final Context context, View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            error_name=  itemView.findViewById(R.id.error_name);


        }
    }
}

