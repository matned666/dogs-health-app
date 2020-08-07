package pl.design.mrn.matned.dogmanagementapp.activity.health;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import pl.design.mrn.matned.dogmanagementapp.R;

public class HealthListActivity extends AppCompatActivity {

    private TextView label;
    private RecyclerView recyclerView;
    private Button addNew;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_activity);
        label = findViewById(R.id.titleDataList);
        recyclerView = findViewById(R.id.recyclerViewDataList);
        addNew = findViewById(R.id.addNewDataList);
        back = findViewById(R.id.cancelDataList);
        onClick();
    }

    private void onClick() {
        back.setOnClickListener(abc -> finish());
        initAdapter();
    }

    private void initAdapter() {
    }



}