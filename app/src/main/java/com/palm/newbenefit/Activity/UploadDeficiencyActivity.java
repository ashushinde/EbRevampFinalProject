package com.palm.newbenefit.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.palm.newbenefit.R;

public class UploadDeficiencyActivity extends AppCompatActivity {
EditText claim_id,raised_date,reason_def;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_deficiency);
        claim_id=findViewById(R.id.claim_id);
        raised_date=findViewById(R.id.raised_date);
        reason_def=findViewById(R.id.reason_def);

    }
}