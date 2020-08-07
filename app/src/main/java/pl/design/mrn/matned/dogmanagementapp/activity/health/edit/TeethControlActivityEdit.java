package pl.design.mrn.matned.dogmanagementapp.activity.health.edit;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import pl.design.mrn.matned.dogmanagementapp.R;

public class TeethControlActivityEdit extends AppCompatActivity {

    private TextView label;
    private RecyclerView recyclerView;
    private Button addNew;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_list_activity);
    }


}
