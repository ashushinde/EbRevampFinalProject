package com.palm.newbenefit.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.DatabaseHelper.DBHelper;
import com.palm.newbenefit.Module.ImageData;
import com.kmd.newbenefit.R;

import java.util.List;

public class ViewImageAdapter extends
        RecyclerView.Adapter<ViewImageAdapter.ViewHolder> {
    Constants con;
    private List<ImageData> mTrain;
    private Context context = null;
    DBHelper db;


    // Pass in the contact array into the constructor
    public ViewImageAdapter(Context context, List<ImageData> train) {
        this.context = context;
        mTrain = train;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        con=new Constants();
        // Inflate the custom layout
        View trainingView = inflater.inflate(R.layout.view_doc_list, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(context, trainingView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        final ImageData train = mTrain.get(position);
         con=new Constants();






         if(train.getImage().contains("pdf")){

             viewHolder.img.setVisibility(View.GONE);
             viewHolder.pdf_name.setVisibility(View.VISIBLE);
             viewHolder.pdf_name.setText(train.getImage());
         }else {

             viewHolder.img.setVisibility(View.VISIBLE);
             viewHolder.pdf_name.setVisibility(View.GONE);
             Bitmap bmp = BitmapFactory.decodeFile(train.getImage());
             viewHolder.img.setImageBitmap(bmp);
         }






        viewHolder.policy_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {


                db= new DBHelper(context);

/*




                    if (db.getimageCount() > 0) {
                        new AlertDialog.Builder(context)
                                .setTitle("Success")
                                .setMessage("Image Deleted Successfully")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setNegativeButton(android.R.string.ok, null).show();

                    } else {
                        new AlertDialog.Builder(context)
                                .setTitle("Success")
                                .setMessage("Image Deleted Successfully")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setNegativeButton(android.R.string.ok, null).show();

                    }

*/





                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

                alertDialog.setTitle("Delete Document");
                alertDialog.setMessage("Are you sure you want to delete Document?");
                alertDialog.setNegativeButton(R.string.no,null);
                alertDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new AlertDialog.Builder(context)
                                .setTitle("Success")
                                .setMessage("Document Deleted Successfully")
                                .setIcon(R.drawable.checkmark)
                                .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        db.deleteImage(train.getImage());


                                        ImageData train = mTrain.get(position);
                                        int newPosition = viewHolder.getAdapterPosition();
                                        mTrain.remove(newPosition);
                                        notifyItemRemoved(newPosition);
                                        notifyItemRangeChanged(newPosition, mTrain.size());



                                    }
                                }).show();
                    }
                }).show();










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

        public ImageView img;
        public Button policy_detail,policy_doc;
        TextView pdf_name;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final Context context, View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);


            img = (ImageView) itemView.findViewById(R.id.img);
            policy_detail = (Button) itemView.findViewById(R.id.policy_detail);
            policy_doc = (Button) itemView.findViewById(R.id.policy_doc);
            pdf_name= (TextView) itemView.findViewById(R.id.pdf_name);
        }
    }
}
