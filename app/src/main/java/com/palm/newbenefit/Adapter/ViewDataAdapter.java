package com.palm.newbenefit.Adapter;

import android.content.Context;
import android.content.DialogInterface;

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
import com.palm.newbenefit.Module.BillAllData;
import com.palm.newbenefit.R;

import java.util.List;

public class ViewDataAdapter extends
        RecyclerView.Adapter<ViewDataAdapter.ViewHolder> {
    Constants con;
    private List<BillAllData> mTrain;
    private Context context = null;
    DBHelper db;


    // Pass in the contact array into the constructor
    public ViewDataAdapter(Context context, List<BillAllData> train) {

        this.context = context;
        mTrain = train;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        con=new Constants();
        // Inflate the custom layout
        View trainingView = inflater.inflate(R.layout.view_data_list, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(context, trainingView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        final BillAllData train = mTrain.get(position);
         con=new Constants();
        // Set item views based on your views and data model

        TextView policyies = viewHolder.policy;
        TextView member = viewHolder.member_cover;
        TextView isnusre = viewHolder.insure_name;

      /*  if (!mTrain.get(position).getInsure_com_img_path().isEmpty() && mTrain.get(position).getInsure_com_img_path() != null) {

            File imgFile = new File(con.base_url+"/"+ mTrain.get(position).getInsure_com_img_path());
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            viewHolder.logo_policy.setImageBitmap(myBitmap);

        }
*/


        policyies.setText(train.getBill_number());
        member.setText(train.getBill_amount());
        isnusre.setText(train.getBill_Date());
        viewHolder.type.setText(train.getType());
        viewHolder.comment.setText(train.getComment());







        viewHolder.policy_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {


                db= new DBHelper(context);


/*

                db.deleteRow(train.getBill_number());
                db.deleteRowno(train.getBill_number());
                db.deleteRowamt(train.getBill_number());
                db.deleteRowcost(train.getBill_number());

                db.deleteRowcomment(train.getBill_number());
                db.deletedate(train.getBill_number());


                BillAllData train = mTrain.get(position);
                int newPosition = viewHolder.getAdapterPosition();
                mTrain.remove(newPosition);
                notifyItemRemoved(newPosition);
                notifyItemRangeChanged(newPosition, mTrain.size());
*/

/*

                    if (db.getNotesCount() > 0) {
                        new AlertDialog.Builder(context)
                                .setTitle("Success")
                                .setMessage("Data Deleted")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setNegativeButton(android.R.string.ok, null).show();

                    } else {
                        new AlertDialog.Builder(context)
                                .setTitle("Success")
                                .setMessage("Data not Deleted")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setNegativeButton(android.R.string.ok, null).show();

                    }
*/





                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

                alertDialog.setTitle("Delete Data");
                alertDialog.setMessage("Are you sure you want to delete Data?");
                alertDialog.setNegativeButton(R.string.no,null);

                alertDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new AlertDialog.Builder(context)
                                .setTitle("Success")
                                .setMessage("Data Deleted Successfully")
                                .setIcon(R.drawable.checkmark)
                                .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {

                                        db.deleteRow(train.getBill_number());
                                        db.deleteRowno(train.getBill_number());
                                        db.deleteRowamt(train.getBill_number());
                                        db.deleteRowcost(train.getBill_number());

                                        db.deleteRowcomment(train.getBill_number());
                                        db.deletedate(train.getBill_number());
                                         db.deletebillldata(train.getBill_number());


                                        BillAllData train = mTrain.get(position);
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
        public TextView policy_type,policy,type,comment,insure_name,member_cover;
        public ImageView logo_policy;
        public Button policy_detail;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final Context context, View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            policy_type = (TextView) itemView.findViewById(R.id.policy_type);
            policy = (TextView) itemView.findViewById(R.id.policy);
            insure_name = (TextView) itemView.findViewById(R.id.insure_name);
            member_cover = (TextView) itemView.findViewById(R.id.member_cover);
            logo_policy = (ImageView) itemView.findViewById(R.id.logo_policy);
            policy_detail = (Button) itemView.findViewById(R.id.policy_detail);

            type= (TextView) itemView.findViewById(R.id.type);
            comment= (TextView) itemView.findViewById(R.id.comment);
        }
    }
}
