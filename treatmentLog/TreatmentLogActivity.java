package com.example.simpill.ext.treatmentLog;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpill.R;
import com.example.simpill.SharedPrefs;
import com.example.simpill.Simpill;


public class TreatmentLogActivity extends AppCompatActivity {

    private RecyclerView logs;
    private TextView title;
    private final SharedPrefs sharedPrefs = new SharedPrefs(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViewAndDesign();
        title = findViewById(R.id.title);
        logs = findViewById(R.id.log_list);
        initializeTreatmentLog();
        title.setText(R.string.treatment_log_title);
    }

    private void setContentViewAndDesign() {
        int theme = sharedPrefs.getThemesPref();
        if (theme == Simpill.BLUE_THEME) {
            setTheme(R.style.SimpillAppTheme_BlueBackground);
        } else if (theme == Simpill.GREY_THEME) {
            setTheme(R.style.SimpillAppTheme_GreyBackground);
        } else if (theme == Simpill.BLACK_THEME) {
            setTheme(R.style.SimpillAppTheme_BlackBackground);
        } else {
            setTheme(R.style.SimpillAppTheme_PurpleBackground);
        }
        setContentView(R.layout.log_view);
    }

    private void initializeTreatmentLog(){
        // Usually this would not happen on the UI thread. Keeping in simple for test repo.
        Adapter a = new Adapter(TreatmentLogDatabaseHelper.Factory.getDatabase(this).getAllEntries().getList());
        logs.setLayoutManager(new LinearLayoutManager(this));
        logs.setAdapter(a);
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
