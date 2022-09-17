package com.palm.newbenefit.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.palm.newbenefit.R;

public class PassowrdPolicyActivity extends AppCompatActivity {


    ImageView info_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passowrd_policy);

        info_text=findViewById(R.id.info_text);

        info_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}