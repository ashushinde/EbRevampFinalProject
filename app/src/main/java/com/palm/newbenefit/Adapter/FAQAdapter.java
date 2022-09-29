package com.palm.newbenefit.Adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.Module.FAQ;
import com.kmd.newbenefit.R;

import java.util.List;

public class FAQAdapter extends
        RecyclerView.Adapter<FAQAdapter.ViewHolder> {
    Constants con;
    private List<FAQ> mTrain;
    private Context context = null;
    String emp_id,policy_no;

    // Pass in the contact array into the constructor
    public FAQAdapter(Context context, List<FAQ> train, String emp_id) {
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
        View trainingView = inflater.inflate(R.layout.faq_list, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(context, trainingView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        FAQ train = mTrain.get(position);
        con = new Constants();
        // Set item views based on your views and data model

        DatePickerDialog fromDatePickerDialog;

        viewHolder.first_name.setText(train.getQuestion());

        viewHolder.gender.setText(train.getAnswer());























        viewHolder.expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                if(viewHolder.hide.getVisibility() == View.VISIBLE){

                    viewHolder.hide.setVisibility(View.GONE);
                    viewHolder.expand.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);

                }
                else {
                    viewHolder.hide.setVisibility(View.VISIBLE);
                    viewHolder.expand.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);

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



            first_name = (TextView) itemView.findViewById(R.id.first_name);

            gender = (TextView) itemView.findViewById(R.id.gender);

            expand = (ImageView) itemView.findViewById(R.id.expand);

            hide = (LinearLayout) itemView.findViewById(R.id.hide);
            hidden= (LinearLayout) itemView.findViewById(R.id.hidden);
        }
    }



}
