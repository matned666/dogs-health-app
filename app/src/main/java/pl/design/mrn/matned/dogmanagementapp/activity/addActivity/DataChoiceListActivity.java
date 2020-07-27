package pl.design.mrn.matned.dogmanagementapp.activity.addActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.activity.DogDataActivity;
import pl.design.mrn.matned.dogmanagementapp.activity.Edit_DogActivity;

import static pl.design.mrn.matned.dogmanagementapp.Statics.LIST_ELEMENT_ACTIVITY;
import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE;
import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE_INFO;

public class DataChoiceListActivity extends AppCompatActivity {

    private String usage;
    private String listActivity;
    private RecyclerView recyclerView;
    private Button cancelBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_list_activity);
        Intent intent = getIntent();
        usage = intent.getStringExtra(USAGE);
        listActivity = intent.getStringExtra(LIST_ELEMENT_ACTIVITY);
        init();
    }

    private void init() {
        recyclerView = findViewById(R.id.recyclerViewDataList);
        cancelBtn = findViewById(R.id.cancelDataList);
        cancelBtn.setOnClickListener(v-> {
            finish();
        });
    }
}
