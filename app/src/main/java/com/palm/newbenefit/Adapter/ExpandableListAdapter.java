package com.palm.newbenefit.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.palm.newbenefit.Module.MenuModel;
import com.kmd.newbenefit.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by anupamchugh on 22/12/17.
 */


public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<MenuModel> listDataHeader;
    private HashMap<MenuModel, List<MenuModel>> listDataChild;
    int index = 0;
    public ExpandableListAdapter(Context context, List<MenuModel> listDataHeader,
                                 HashMap<MenuModel, List<MenuModel>> listChildData) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listChildData;
    }

    @Override
    public MenuModel getChild(int groupPosition, int childPosititon) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = getChild(groupPosition, childPosition).menuName;
      //  final String childImage = getChild(groupPosition, childPosition).url;

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_child, null);
        }
        TextView lblListItem = convertView.findViewById(R.id.lblListItem);
        lblListItem.setText(childText);

//        LinearLayout boxes = convertView.findViewById(R.id.boxes);
//
//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                index = groupPosition;
//
//
//                notifyDataSetChanged();
//            }
//        });
//
//        if(index==groupPosition){
//
//
//            boxes.setBackgroundResource(R.drawable.card_select);
//
//
//
//
//        }else{
//            boxes.setBackgroundResource(R.drawable.card_unselect);
//
//
//
//        }



        switch (childPosition) {
            case 0:

                ImageView lb1listimage = convertView.findViewById(R.id.lb1listimage);
                lb1listimage.setImageDrawable(convertView.getResources().getDrawable(R.drawable.claim_nav_a));
                break;

            case 1:
                ImageView lb1listimage1 = convertView.findViewById(R.id.lb1listimage);
                lb1listimage1.setImageDrawable(convertView.getResources().getDrawable(R.drawable.add_member_nav_a));
                break;

            case 2:
                ImageView lb1listimage2 = convertView.findViewById(R.id.lb1listimage);
                lb1listimage2.setImageDrawable(convertView.getResources().getDrawable(R.drawable.portalclaimnav_two));
                break;

            case 3:
                ImageView lb1listimage3 = convertView.findViewById(R.id.lb1listimage);
                lb1listimage3.setImageDrawable(convertView.getResources().getDrawable(R.drawable.tracknav_two));
                break;
            case 4:

                break;
        }

/*
            txtListChild.setText(childText);
            lb1listimage.setImageDrawable(Drawable.createFromPath(childImage));*/

            return convertView;
        }


    @Override
    public int getChildrenCount(int groupPosition) {

        if (this.listDataChild.get(this.listDataHeader.get(groupPosition)) == null)
            return 0;
        else
            return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                    .size();
    }

    @Override
    public MenuModel getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();

    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = getGroup(groupPosition).menuName;
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_header, null);
        }
        TextView lblListHeader = convertView.findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        ImageView lb1listimage = convertView.findViewById(R.id.img_grouplistimage);

        LinearLayout boxes = convertView.findViewById(R.id.boxes);

//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                index = groupPosition;
//
//
//              notifyDataSetChanged();
//            }
//        });
//
//        if(index==groupPosition){
//
//
//            boxes.setBackgroundResource(R.drawable.card_select);
//
//
//
//
//        }else{
//            boxes.setBackgroundResource(R.drawable.card_unselect);
//
//
//
//        }


        ImageView lb1listimagearr = convertView.findViewById(R.id.lb1listimagearr);
        ImageView lb1listimagearra = convertView.findViewById(R.id.lb1listimagearra);




        if(lblListHeader.getText().equals("Claim")){
            lb1listimagearr.setVisibility(View.VISIBLE);

        }else {
            lb1listimagearr.setVisibility(View.GONE);
        }
        switch (groupPosition) {
            case 0:
                lb1listimage.setImageDrawable(convertView.getResources().getDrawable(R.drawable.pro_nav_a));
                break;

            case 1:

                lb1listimage.setImageDrawable(convertView.getResources().getDrawable(R.drawable.dashboard_nav_a));
                break;

            case 2:

                lb1listimage.setImageDrawable(convertView.getResources().getDrawable(R.drawable.add_member_nav_a));
                break;

            case 3:

                lb1listimage.setImageDrawable(convertView.getResources().getDrawable(R.drawable.enrolmentnav_two));
                break;
            case 4:
                lb1listimage.setImageDrawable(convertView.getResources().getDrawable(R.drawable.network_hospital_nav_a));
                break;
            case 5:
                lb1listimage.setImageDrawable(convertView.getResources().getDrawable(R.drawable.wellness_a));
                break;

            case 6:
                lb1listimage.setImageDrawable(convertView.getResources().getDrawable(R.drawable.contactnav_two));
                break;
            case 7:
                lb1listimage.setImageDrawable(convertView.getResources().getDrawable(R.drawable.form_center_nav_a));
                break;
            case 8:
                lb1listimage.setImageDrawable(convertView.getResources().getDrawable(R.drawable.contact_us_a));
                break;
            case 9:
                lb1listimage.setImageDrawable(convertView.getResources().getDrawable(R.drawable.help));
                break;
            case 10:
                lb1listimage.setImageDrawable(convertView.getResources().getDrawable(R.drawable.my_policy));
                break;

            case 11:
                lb1listimage.setImageDrawable(convertView.getResources().getDrawable(R.drawable.claim));
                break;




        }


        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}