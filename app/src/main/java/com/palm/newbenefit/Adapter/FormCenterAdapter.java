package com.palm.newbenefit.Adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


import com.palm.newbenefit.Activity.PdfActivityjava;
import com.palm.newbenefit.Module.FormCenter;
import com.palm.newbenefit.R;

import java.util.List;

public class FormCenterAdapter extends
        RecyclerView.Adapter<FormCenterAdapter.ViewHolder> {

    private List<FormCenter> mTrain;
    private Context context = null;

    // Pass in the contact array into the constructor
    public FormCenterAdapter(Context context, List<FormCenter> train) {
        this.context = context;
        mTrain = train;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View trainingView = inflater.inflate(R.layout.form_center_list, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(context, trainingView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        // Get the data model based on position
        FormCenter train = mTrain.get(position);

        // Set item views based on your views and data model
        TextView textView = viewHolder.nameTextView;
        textView.setText(train.getForm_center_name());



        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                        try {


                            Context context = v.getContext();

                            FormCenter train = mTrain.get(position);


                            if (train.getPdf_file().endsWith(".xlsx") ||
                                    train.getPdf_file().endsWith(".pdf") ||
                                    train.getPdf_file().endsWith(".xls") ||

                                    train.getPdf_file().endsWith("docx")) {

                                new AlertDialog.Builder(context)
                                        .setTitle("Success")
                                        .setMessage("File Downloaded Sucessfully !")
                                        .setIcon(R.drawable.checkmark)
                                        .setNegativeButton(android.R.string.ok, null).show();
                                DownloadManager downloadManager = null;
                                downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                                Uri uri = Uri.parse(train.getPdf_file());
                                DownloadManager.Request request = new DownloadManager.Request(uri);
                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                Long reference = downloadManager.enqueue(request);
                            } else {
                                Intent intent = new Intent(context, PdfActivityjava.class);
                                intent.putExtra("pol", train.getPdf_file());

                                context.startActivity(intent);
                            }


                        }catch (Exception e) {

                        }
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
        public TextView nameTextView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final Context context, View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);

        }
    }
}
