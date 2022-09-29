package com.palm.newbenefit.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.Module.DocumentData;
import com.kmd.newbenefit.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DocumentAdapter extends
        RecyclerView.Adapter<DocumentAdapter.ViewHolder> {
Constants con;
    private List<DocumentData> mTrain;
    private Context context = null;
    private String compareCount = null;
    // Pass in the contact array into the constructor
    public DocumentAdapter(Context context, List<DocumentData> train) {
        this.context = context;
        mTrain = train;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
         con=new Constants();
        // Inflate the custom layout
        View trainingView = inflater.inflate(R.layout.image_list, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(context, trainingView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        DocumentData train = mTrain.get(position);
        con=new Constants();


        if(position==0){
            viewHolder.document_name.setText("Medical Bills & Discharge Summary");

        }else {
            viewHolder.document_name.setText(train.getDoc_name());
        }


          Picasso.get().load(train.getDoc_link()).into(viewHolder.document);












    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mTrain.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView document_name;
        ImageView document;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final Context context, View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            document_name = (TextView) itemView.findViewById(R.id.document_name);
            document = (ImageView) itemView.findViewById(R.id.document);





        }
    }
}
