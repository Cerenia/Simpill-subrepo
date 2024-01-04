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
        RecyclerView rw = findViewById(R.id.log);
        // Usually this would not happen on the UI thread. Keeping in simple for test repo.
        Adapter a = new Adapter(db.getAllEntries().getList());
        rw.setAdapter(a);
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
