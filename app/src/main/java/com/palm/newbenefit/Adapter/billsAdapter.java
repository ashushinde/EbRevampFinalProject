package com.palm.newbenefit.Adapter;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.Module.amount;
import com.palm.newbenefit.R;

import java.util.Date;
import java.util.List;

public class billsAdapter extends
        RecyclerView.Adapter<billsAdapter.ViewHolder> {
Constants con;
    private List<amount> mTrain;
    private Context context = null;
    private String compareCount = null;
    // Pass in the contact array into the constructor
    public billsAdapter(Context context, List<amount> train) {
        this.context = context;
        mTrain = train;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
         con=new Constants();
        // Inflate the custom layout
        View trainingView = inflater.inflate(R.layout.bil_list, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(context, trainingView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        amount train = mTrain.get(position);
        con=new Constants();
        // Set item views based on your views and data model
        //TextView type = viewHolder.p_type;
        TextView cover = viewHolder.amt;



        try{

            String strCurrentDate = train.getAmt();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date newDate = format.parse(strCurrentDate);

            format = new SimpleDateFormat("dd-MM-yyyy");

            cover.setText(format.format(newDate));
        }catch (Exception e){
            cover.setText(train.getAmt());
        }

















    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mTrain.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView amt;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final Context context, View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

           // p_type = (TextView) itemView.findViewById(R.id.p_type);
            amt = (TextView) itemView.findViewById(R.id.amt);





        }
    }
}
