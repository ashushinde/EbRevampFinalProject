package com.palm.newbenefit.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.Module.MyDashboard;
import com.kmd.newbenefit.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyDashboardAdapter extends
        RecyclerView.Adapter<MyDashboardAdapter.ViewHolder> {
Constants con;
    private List<MyDashboard> mTrain;
    private Context context = null;

    // Pass in the contact array into the constructor
    public MyDashboardAdapter(Context context, List<MyDashboard> train) {
        this.context = context;
        mTrain = train;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
         con=new Constants();
        // Inflate the custom layout
        View trainingView = inflater.inflate(R.layout.new_dashboard_list, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(context, trainingView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        MyDashboard train = mTrain.get(position);
        con=new Constants();
        // Set item views based on your views and data model


        viewHolder.heading.setText(train.getHeading());
        if(!train.getSub_heading().equalsIgnoreCase("null")){
            viewHolder.sub_heading.setText(train.getSub_heading());
        }else {
            viewHolder.sub_heading.setText(null);
        }
         viewHolder.heading.setTextColor(Color.parseColor(train.getTextColor()));
         viewHolder.sub_heading.setTextColor(Color.parseColor(train.getTextColor()));


         Picasso.get().load(train.getImage()).into(viewHolder.logo);


        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setColor(Color.parseColor(train.getCardBackground()));
        shape.setStroke(2, Color.parseColor(train.getCardColor()));
        shape.setCornerRadius(60);
        shape.setGradientRadius(20);
        shape.setInnerRadius(20);

        viewHolder.my_family.setBackground(shape);


        GradientDrawable shapes = new GradientDrawable();
        shapes.setShape(GradientDrawable.RECTANGLE);
        shapes.setColor(Color.parseColor(train.getCardColor()));
        shapes.setStroke(2, Color.parseColor(train.getCardColor()));
        shapes.setCornerRadius(60);
        shapes.setGradientRadius(20);
        shapes.setInnerRadius(20);

        viewHolder.button_select.setBackground(shapes);








/*
        GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(context, R.drawable.card_trans).mutate();
        drawable.setColor(Color.parseColor(train.getCardBackground()));
        drawable.setStroke(3, Color.parseColor(train.getCardColor()));*/

       // viewHolder.my_family.setBackgroundColor(Color.parseColor(train.getCardBackground()));
    }







    @Override
    public int getItemCount() {
        return mTrain.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView heading,sub_heading;
        LinearLayout my_family,button_select;

      ImageView logo;
        public ViewHolder(final Context context, View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            sub_heading = (TextView) itemView.findViewById(R.id.sub_heading);
            heading = (TextView) itemView.findViewById(R.id.heading);
            button_select =  itemView.findViewById(R.id.button_select);

            logo= (ImageView) itemView.findViewById(R.id.logo);
            my_family=itemView.findViewById(R.id.my_family);


        }
    }
}
