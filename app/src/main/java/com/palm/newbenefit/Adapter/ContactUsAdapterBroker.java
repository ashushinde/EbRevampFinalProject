package com.palm.newbenefit.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.Module.ContactUs;
import com.kmd.newbenefit.R;

import java.io.InputStream;
import java.util.List;

;

public class ContactUsAdapterBroker extends RecyclerView.Adapter<ContactUsAdapterBroker.ViewHolder> {
    Constants con;
    private List<ContactUs> mTrain;
    private Context context = null;

    // Pass in the contact array into the constructor
    public ContactUsAdapterBroker(Context context, List<ContactUs> train) {
        this.context = context;
        mTrain = train;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        con = new Constants();
        // Inflate the custom layout
        View trainingView = inflater.inflate(R.layout.broker_list, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(context, trainingView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        ContactUs train = mTrain.get(position);
        con = new Constants();
        // Set item views based on your views and data model
        //TextView type = viewHolder.p_type;
        String tpa_namea=train.getName();
        String tpa_add_1=train.getAddress_line_1();
        String tpa_add_2=train.getAddress_line_2();
        String tpa_add_3=train.getAddress_line_3();
        String tpa_email_1=train.getEmail_1();
        String tpa_contact_1=train.getContact_1();
        String tpa_contact_2=train.getContact_2();
        String tpa_email_2=train.getEmail_2();


        if (tpa_namea != "null" && !tpa_namea.isEmpty() && !tpa_namea.equals("")) {
            viewHolder.tpa_name_a.setText(tpa_namea);
            viewHolder.tpa_name.setVisibility(View.VISIBLE);
            viewHolder.tpa_name_a.setVisibility(View.VISIBLE);
            viewHolder.tpaname.setText(tpa_namea);
        } else {
            viewHolder. tpa_name.setVisibility(View.GONE);
            viewHolder.tpa_name_a.setVisibility(View.GONE);
        }

        if (tpa_add_1 != "null" && !tpa_add_1.isEmpty() && !tpa_add_1.equals("")) {
            viewHolder.address_tpa_a.setText(tpa_add_1);
            viewHolder. tpa_add_a.setVisibility(View.VISIBLE);
            viewHolder.  address_tpa_a.setVisibility(View.VISIBLE);
        }else {
            viewHolder. tpa_add_a.setVisibility(View.GONE);
            viewHolder.address_tpa_a.setVisibility(View.GONE);
        }



        if (tpa_add_2 != "null" && !tpa_add_2.isEmpty() && !tpa_add_2.equals("")) {
            viewHolder. address_tpa_b.setText(tpa_add_2);
            viewHolder.tpa_id_b.setVisibility(View.VISIBLE);
            viewHolder. address_tpa_b.setVisibility(View.VISIBLE);

        }else {
            viewHolder. tpa_id_b.setVisibility(View.GONE);
            viewHolder. address_tpa_b.setVisibility(View.GONE);
        }




        if ((tpa_add_3 != "null" && !tpa_add_3.isEmpty() && !tpa_add_3.equals(""))) {
            viewHolder.address_tpa_c.setText(tpa_add_3);
            viewHolder.tpa_id_c.setVisibility(View.VISIBLE);
            viewHolder. address_tpa_c.setVisibility(View.VISIBLE);
        }else {
            viewHolder. tpa_id_c.setVisibility(View.GONE);
            viewHolder.address_tpa_c.setVisibility(View.GONE);
        }



        if ((tpa_email_1 != "null" && !tpa_email_1.isEmpty() && !tpa_email_1.equals(""))
                || (tpa_email_2 != "null" && !tpa_email_2.isEmpty() && !tpa_email_2.equals(""))) {

            viewHolder. tpa_email_id.setVisibility(View.VISIBLE);

            if (tpa_email_1 != "null" && !tpa_email_1.isEmpty() && !tpa_email_1.equals("")){
                viewHolder.email_tpa_a.setText(tpa_email_1);
                viewHolder.email_tpa_a.setVisibility(View.VISIBLE);
            }else {
                viewHolder.  email_tpa_a.setVisibility(View.GONE);
            }


            if((tpa_email_1 != "null" && !tpa_email_1.isEmpty() && !tpa_email_1.equals(""))
                    && (tpa_email_2 != "null" && !tpa_email_2.isEmpty() && !tpa_email_2.equals(""))) {


                viewHolder. email_tpa_b.setText(tpa_email_2);
            }else  if((tpa_email_2 != "null" && !tpa_email_2.isEmpty() && !tpa_email_2.equals(""))){

                viewHolder.  email_tpa_b.setText(tpa_email_2);

            }else {
                viewHolder. email_tpa_b.setVisibility(View.GONE);
            }

        }else {
            viewHolder. tpa_email_id.setVisibility(View.GONE);

        }


        if ((tpa_contact_1 != "null" && !tpa_contact_1.isEmpty() && !tpa_contact_1.equals(""))
                || (tpa_contact_2 != "null" && !tpa_contact_2.isEmpty() && !tpa_contact_2.equals(""))) {

            viewHolder. tpa_contact.setVisibility(View.VISIBLE);

            if (tpa_contact_1 != "null" && !tpa_contact_1.isEmpty() && !tpa_contact_1.equals("")){
                viewHolder.  contact_tpa_a.setText(tpa_contact_1);
                viewHolder. contact_tpa_a.setVisibility(View.VISIBLE);
            }else {
                viewHolder. contact_tpa_a.setVisibility(View.GONE);
            }


            if((tpa_contact_1 != "null" && !tpa_contact_1.isEmpty() && !tpa_contact_1.equals(""))
                    && (tpa_contact_2 != "null" && !tpa_contact_2.isEmpty() && !tpa_contact_2.equals(""))) {


                viewHolder. contact_tpa_b.setText(tpa_contact_2);
            }else  if((tpa_contact_2 != "null" && !tpa_contact_2.isEmpty() && !tpa_contact_2.equals(""))){

                viewHolder. contact_tpa_b.setText(tpa_contact_2);

            }else {
                viewHolder. contact_tpa_b.setVisibility(View.GONE);
            }

        }else {
            viewHolder.tpa_contact.setVisibility(View.GONE);

        }


if(viewHolder.contact_tpa_a.getText().toString().startsWith("0"))
{
    viewHolder.contact_tpa_a.setText("-");
}

        if(viewHolder.contact_tpa_b.getText().toString().startsWith("0"))
        {
            viewHolder.contact_tpa_b.setText("-");
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

        public  TextView tpa_name,tpa_contact,tpa_email_id,tpa_add_a,tpa_id_b,tpa_id_c;
        public TextView tpa_name_a,contact_tpa_a,contact_tpa_b,email_tpa_a,email_tpa_b,
                address_tpa_a,address_tpa_b,tpaname,
                address_tpa_c;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final Context context, View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            tpa_name = itemView.findViewById(R.id.tpa_name);
            tpa_contact = itemView.findViewById(R.id.tpa_contact);
            tpa_email_id = itemView.findViewById(R.id.tpa_email_id);
            tpa_add_a = itemView.findViewById(R.id.tpa_add_a);
            tpa_id_b = itemView.findViewById(R.id.tpa_id_b);
            tpa_id_c = itemView.findViewById(R.id.tpa_id_c);
            tpa_name_a = itemView.findViewById(R.id.tpa_name_a);
            contact_tpa_a = itemView.findViewById(R.id.contact_tpa_a);
            contact_tpa_b = itemView.findViewById(R.id.contact_tpa_b);
            email_tpa_a = itemView.findViewById(R.id.email_tpa_a);
            email_tpa_b = itemView.findViewById(R.id.email_tpa_b);
            address_tpa_a = itemView.findViewById(R.id.address_tpa_a);
            address_tpa_c= itemView.findViewById(R.id.address_tpa_c);
            address_tpa_b=itemView.findViewById(R.id.address_tpa_b);
            tpaname=itemView.findViewById(R.id.tpaname);
        }
    }


    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView = imageView;
            //Toast.makeText(getApplicationContext(), "Please wait, it may take a few minute...", Toast.LENGTH_SHORT).show();
        }

        protected Bitmap doInBackground(String... urls) {
            String imageURL = urls[0];
            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bimage = BitmapFactory.decodeStream(in);

            } catch (Exception e) {

                e.printStackTrace();
            }
            return bimage;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }
}
