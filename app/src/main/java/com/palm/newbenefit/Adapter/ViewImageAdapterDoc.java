package com.palm.newbenefit.Adapter;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.DatabaseHelper.DBHelper;
import com.palm.newbenefit.Module.ImageData;
import com.kmd.newbenefit.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewImageAdapterDoc extends
        RecyclerView.Adapter<ViewImageAdapterDoc.ViewHolder> {
    Constants con;
    DBHelper db;
    private List<ImageData> mTrain;
    private Context context = null;


    // Pass in the contact array into the constructor
    public ViewImageAdapterDoc(Context context, List<ImageData> train) {
        this.context = context;
        mTrain = train;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        con=new Constants();
        // Inflate the custom layout
        View trainingView = inflater.inflate(R.layout.view_doc_list_doc, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(context, trainingView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
        // Get the data model based on position
        final ImageData train = mTrain.get(position);
         con=new Constants();









             viewHolder.pdf_name.setVisibility(View.VISIBLE);
             viewHolder.pdf_name.setText(train.getImagedata());

             if((train.getImage().contains(".png"))||
                     (train.getImage().contains(".jpg"))||
                     (train.getImage().contains(".jpeg"))
             ){
                 Picasso.get().load(train.getImage()).into(viewHolder.img);

             }else {

             }




         viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 try{
                     Log.d("myissue",train.getImage());
                     Context context = view.getContext();

                     Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                             Uri.parse(train.getImage()));
                     context. startActivity(browserIntent);

                 }catch (Exception e){

                     Context context = view.getContext();
                     Log.d("myissue",e.toString());
                     String urlString = train.getImage();
                     Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(urlString));
                     intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                     intent.setPackage("com.android.chrome");
                     try {
                         context.startActivity(intent);
                     } catch (ActivityNotFoundException ex) {
                         // Chrome browser presumably not installed so allow user to choose instead
                         intent.setPackage(null);
                         context.startActivity(intent);
                     }
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


ImageView  img;
        TextView pdf_name;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final Context context, View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);


            img=  itemView.findViewById(R.id.img);
            pdf_name= (TextView) itemView.findViewById(R.id.pdf_name);
        }
    }
}
