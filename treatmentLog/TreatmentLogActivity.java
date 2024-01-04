package com.example.simpill.ext.treatmentLog;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpill.R;


public class TreatmentLogActivity extends AppCompatActivity {

    private final TreatmentLogDatabase db = new TreatmentLogDatabase(this, null);
    private RecyclerView logs;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = findViewById(R.id.title);
        logs = findViewById(R.id.log);
        initializeTreatmentLog();
        title.setText(R.string.treatment_log_title);
    }

    private void initializeTreatmentLog(){

    }

    public void randomMethod(){
        /**
         *
         * "IXwDmcyAEZEUvkES0IXy144JB SimPillAddOn start"
         * "IXwDmcyAEZEUvkES0IXy144JB SimPillAddOn end"
         *
         */
    }
}
