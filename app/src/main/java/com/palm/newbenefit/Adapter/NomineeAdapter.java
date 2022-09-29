package com.palm.newbenefit.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.DatabaseHelper.DBHelper;
import com.palm.newbenefit.Module.AddNominee;
import com.kmd.newbenefit.R;

import java.util.List;

public class NomineeAdapter extends
        RecyclerView.Adapter<NomineeAdapter.ViewHolder> {
    Constants con;
    private List<AddNominee> mTrain;
    private Context context = null;
    String emp_id;
    DBHelper db;


    // Pass in the contact array into the constructor
    public NomineeAdapter(Context context, List<AddNominee> train) {
        this.context = context;
        mTrain = train;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        con = new Constants();
        // Inflate the custom layout
        View trainingView = inflater.inflate(R.layout.nominee_enroll_list, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(context, trainingView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        AddNominee train = mTrain.get(position);
        con = new Constants();
        // Set item views based on your views and data model


        viewHolder.relation.setText(train.getNom_relation());
        viewHolder.first_name.setText(train.getNom_first_name());
        viewHolder.last_name.setText(train.getNom_last_name());
        viewHolder.gender.setText(train.getShare_percent());
        viewHolder.dob.setText(train.getNom_dob());

        viewHolder.fam_cover.setVisibility(View.GONE);
        viewHolder.premium.setVisibility(View.GONE);


        if (viewHolder.first_name_g.getText().length() < 0) {
            viewHolder.guar.setVisibility(View.VISIBLE);
            viewHolder.guar_detail.setVisibility(View.VISIBLE);
        } else {
            viewHolder.guar.setVisibility(View.GONE);
            viewHolder.guar_detail.setVisibility(View.GONE);
        }


        viewHolder.first_name_g.setText(train.getG_first_name());
        viewHolder.last_name_g.setText(train.getG_last_name());

        viewHolder.dob_g.setText(train.getG_dob());
        viewHolder.relation_g.setText(train.getG_relation());


        viewHolder.download.setVisibility(View.GONE);
        viewHolder.edit.setVisibility(View.GONE);


        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {



/*
                if (db.getNotesCount() > 0) {
                    new AlertDialog.Builder(context)
                            .setTitle("Success")
                            .setMessage("Data Deleted")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setNegativeButton(android.R.string.ok, null).show();

                } else {
                    new AlertDialog.Builder(context)
                            .setTitle("Success !")
                            .setMessage("Data  Deleted Successfully")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setNegativeButton(android.R.string.ok, null).show();
                }*/

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

                alertDialog.setTitle("Delete Member");
                alertDialog.setMessage("Are you sure you want to delete nominee?");
                alertDialog.setNegativeButton(R.string.no,null);
                alertDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new AlertDialog.Builder(context)
                                .setTitle("Success")
                                .setMessage("Nominee Deleted Successfully")
                                .setIcon(R.drawable.checkmark)
                                .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {

                                        AddNominee train = mTrain.get(position);

                                        db = new DBHelper(context);
                                        db.deleteNominee(train.getId());
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


        viewHolder.expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                if(viewHolder.hide.getVisibility() == View.VISIBLE){

                    viewHolder.hide.setVisibility(View.GONE);
                    viewHolder.expand.setImageResource(R.drawable.ic_add_black_24dp);
                    viewHolder.know.setText("View More");
                }
                else {
                    viewHolder.hide.setVisibility(View.VISIBLE);

                    viewHolder.expand.setImageResource(R.drawable.ic_remove_black_24dp);
                    viewHolder.know.setText("View Less");
                }



            }

        });



        viewHolder.know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                if(viewHolder.hide.getVisibility() == View.VISIBLE){

                    viewHolder.hide.setVisibility(View.GONE);
                    viewHolder.expand.setImageResource(R.drawable.ic_add_black_24dp);
                    viewHolder.know.setText("View More");
                }
                else {
                    viewHolder.hide.setVisibility(View.VISIBLE);

                    viewHolder.expand.setImageResource(R.drawable.ic_remove_black_24dp);
                    viewHolder.know.setText("View Less");
                }



            }

        });



        viewHolder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {


                final Dialog dialog = new Dialog(context);
                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.activity_edit_data);


                Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);

                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });

                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);


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
        public TextView relation, first_name,
                first_name_g, last_name_g, dob_g, relation_g,
                last_name, gender, dob, age, age_type, fam_cover, premium,know;
        public ImageView edit, expand, delete, download;
        LinearLayout hide;
        LinearLayout guar, guar_detail;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final Context context, View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            dob_g = (TextView) itemView.findViewById(R.id.dob_g);
            relation = (TextView) itemView.findViewById(R.id.relation);
            first_name_g = (TextView) itemView.findViewById(R.id.first_name_g);
            last_name_g = (TextView) itemView.findViewById(R.id.last_name_g);
            relation_g = (TextView) itemView.findViewById(R.id.relation_g);
            first_name = (TextView) itemView.findViewById(R.id.first_name);
            last_name = (TextView) itemView.findViewById(R.id.last_name);
            gender = (TextView) itemView.findViewById(R.id.gender);

            fam_cover = (TextView) itemView.findViewById(R.id.fam_cover);
            premium = (TextView) itemView.findViewById(R.id.premium);
            dob = (TextView) itemView.findViewById(R.id.dob);
            edit = (ImageView) itemView.findViewById(R.id.edit);
            expand = (ImageView) itemView.findViewById(R.id.expand);
            hide = (LinearLayout) itemView.findViewById(R.id.hide);
            delete = (ImageView) itemView.findViewById(R.id.delete);
            download = (ImageView) itemView.findViewById(R.id.download);
            know= (TextView) itemView.findViewById(R.id.know);
            guar = (LinearLayout) itemView.findViewById(R.id.guar);
            guar_detail = (LinearLayout) itemView.findViewById(R.id.guar_detail);
        }
    }
}
