package com.palm.newbenefit.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.palm.newbenefit.Adapter.ViewDataAdapter;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.DatabaseHelper.DBHelper;
import com.palm.newbenefit.Module.BillAllData;
import com.palm.newbenefit.Module.SpinnerModal;
import com.kmd.newbenefit.R;

import java.util.ArrayList;
import java.util.List;

public class EditDataActivity extends AppCompatActivity {
    Constants con = null;
    Context context;
    String mobileNumber = null;
    String token = null;
    String user_id = null;

    RecyclerView course_history_recycle;
    int gg;
    SearchView mSearcha;
    LinearLayout ll_main_data;
    RelativeLayout allempnotdatafound;
    // List<BillAllData> ob;

    private List<BillAllData> ob ;
    ViewDataAdapter adapter = null;
    RecyclerView recyclerView = null;
    TextView tv_data_not_found;
    DBHelper db;
    BillAllData bill;
    TextView thumbnail;
    ImageView info_text;


    ArrayList<String> bank_name = null;
    ArrayList<SpinnerModal> bank_nameList = null;
    ArrayAdapter<SpinnerModal> bank_nameAdapter = null;

    // Toolbar settingtoolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data2);
        con = new Constants();
        recyclerView = findViewById(R.id.policy_recycle);
        // bill=new BillAllData();
        ob =new ArrayList<BillAllData>();
        db=new DBHelper(this);
        thumbnail=findViewById(R.id.thumbnail);
        info_text=findViewById(R.id.info_text);
        bank_name = new ArrayList<>();

        info_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();

            }
        });


        ob=  db.getdata();

        for (BillAllData background1 : ob) {

            bank_name.add("\""+ background1.getBill_amount()+"\"");







        }





        adapter = new ViewDataAdapter(this, ob);



        RecyclerView.LayoutManager reLayoutManager =new LinearLayoutManager(EditDataActivity.this);
        recyclerView.setLayoutManager(reLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);







       /* SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        mobileNumber = prefs.getString("mobileNumber", null);
        token = prefs.getString("token", null);
        user_id = prefs.getString("user_id", null);



        // Data();


        int numberOfColumns = 1;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                *//* FamilyData train = ob.get(position);*//*

         *//*Intent intent = new Intent(getApplicationContext(), FamilyDataActivity.class);

                intent.putExtra("first_name", train.getFirst_name());
                intent.putExtra("last_name", train.getLast_name());
                intent.putExtra("address", train.getAddress());
                intent.putExtra("number", train.getContact_number());
                intent.putExtra("dob", train.getDate_of_birth());
                intent.putExtra("relation", train.getRelation());
                intent.putExtra("location", train.getLocation());
                intent.putExtra("pincode", train.getPincode());
                intent.putExtra("city", train.getCity());
                intent.putExtra("state", train.getState());
                startActivity(intent);*//*


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

*/
    }











    void getProfileDet() {
        ob=  db.getdata();


    }



    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }




}

