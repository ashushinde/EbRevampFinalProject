package com.palm.newbenefit.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.R;
import com.palm.newbenefit.models.ContactUsMain;

import java.util.List;

public class ContactusmainDataAdapter extends
        RecyclerView.Adapter<ContactusmainDataAdapter.ViewHolder> {
Constants con;
    private List<ContactUsMain> mTrain;
    private Context context = null;
    private String compareCount = null;


    // Pass in the contact array into the constructor
    public ContactusmainDataAdapter(Context context, List<ContactUsMain> train) {
        this.context = context;
        mTrain = train;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
         con=new Constants();
        // Inflate the custom layout
        View trainingView = inflater.inflate(R.layout.contact_list_data, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(context, trainingView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        ContactUsMain train = mTrain.get(position);
        con=new Constants();
        // Set item views based on your views and data model
        //TextView type = viewHolder.p_type;

        viewHolder.leval_count.setText(train.getLevelcount());


      if((  train.getEmpname().equalsIgnoreCase(""))
      ||(train.getEmpname().equalsIgnoreCase("null")
      ||(train.getEmpname().equalsIgnoreCase("false")
      ||(train.getEmpname().equalsIgnoreCase("0"))))){
          viewHolder.name.setText("-");

      }else {
          viewHolder.name.setText(train.getEmpname());


      }


        if((  train.getAddress().equalsIgnoreCase(""))
                ||(train.getAddress().equalsIgnoreCase("null")
                ||(train.getAddress().equalsIgnoreCase("false")
                ||(train.getAddress().equalsIgnoreCase("0"))))){
            viewHolder.address.setText("-");

        }else {
            viewHolder.address.setText(train.getAddress());

        }


        if((  train.getContactno().equalsIgnoreCase(""))
                ||(train.getContactno().equalsIgnoreCase("null")
                ||(train.getContactno().equalsIgnoreCase("false")
                ||(train.getContactno().equalsIgnoreCase("0"))))){
            viewHolder.contactno.setText("-");

        }else {
            viewHolder.contactno.setText(train.getContactno());

        }


        if((  train.getEmailid().equalsIgnoreCase(""))
                ||(train.getEmailid().equalsIgnoreCase("null")
                ||(train.getEmailid().equalsIgnoreCase("false")
                ||(train.getEmailid().equalsIgnoreCase("0"))))){
            viewHolder.emailid.setText("-");

        }else {
            viewHolder.emailid.setText(train.getEmailid());



        }


if(train.getLevelcount().equalsIgnoreCase("1")){
    viewHolder.linear.setBackgroundResource(R.drawable.level1);
}else  if(train.getLevelcount().equalsIgnoreCase("2")){
    viewHolder.linear.setBackgroundResource(R.drawable.level2);
}if(train.getLevelcount().equalsIgnoreCase("3")){
            viewHolder.linear.setBackgroundResource(R.drawable.level3);
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
        public TextView leval_count, name,contactno,emailid,address;
       LinearLayout linear;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final Context context, View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

           // p_type = (TextView) itemView.findViewById(R.id.p_type);
            leval_count = itemView.findViewById(R.id.leval_count);
            name = itemView.findViewById(R.id.name);

            contactno = itemView.findViewById(R.id.contactno);

            emailid = itemView.findViewById(R.id.emailid);
            address = itemView.findViewById(R.id.address);

            linear= itemView.findViewById(R.id.linear);




        }
    }
}
