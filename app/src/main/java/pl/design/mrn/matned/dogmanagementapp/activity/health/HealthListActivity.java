package pl.design.mrn.matned.dogmanagementapp.activity.health;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.activity.adapters.SignDataElementAdapter;
import pl.design.mrn.matned.dogmanagementapp.activity.adapters.health.AllergiesAdapter;
import pl.design.mrn.matned.dogmanagementapp.activity.dataactivity.add.UniqueSignActivityAdd;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.SpecialSign;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.SpecialSignDao;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.Allergies;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.AllergiesDao;
import pl.design.mrn.matned.dogmanagementapp.listeners.DataPositionListener;
import pl.design.mrn.matned.dogmanagementapp.listeners.PositionListener;

import static pl.design.mrn.matned.dogmanagementapp.Statics.ALLERGIES;
import static pl.design.mrn.matned.dogmanagementapp.Statics.LIST_ELEMENT_ACTIVITY;
import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE;
import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE_INFO;
import static pl.design.mrn.matned.dogmanagementapp.TextStrings.SPECIAL_SIGN_LIST_TITLE;

public class HealthListActivity extends AppCompatActivity {

    private static final String ALLERGIES_TITLE = "ALLERGIES_TITLE";
    private TextView title;
    private RecyclerView recyclerView;
    private Button addNew;
    private Button back;
    private String listActivity;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataPositionListener.getInstance().setPosition(0);
        Intent intent = getIntent();
        listActivity = intent.getStringExtra(USAGE);
        setContentView(R.layout.data_list_activity);
        title = findViewById(R.id.titleDataList);
        recyclerView = findViewById(R.id.recyclerViewDataList);
        addNew = findViewById(R.id.addNewDataList);
        back = findViewById(R.id.cancelDataList);
        onClick();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void onClick() {
        back.setOnClickListener(abc -> finish());
        initAdapter();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initAdapter() {
        switch (listActivity){
            case "R_INJECTION": {
                initRInjectionAdapter();
                break;
            }
            case  "O_INJECTION": {
                initOInjectionAdapter();
                break;
            }
            case  "DEWORMING": {
                initDewormingAdapter();
                break;
            }
            case  "BIRTH": {
                initBirthAdapter();
                break;
            }
            case  "TEETH": {
                initTeethAdapter();
                break;
            }

            case  "TREATMENT": {
                initTreatmentAdapter();
                break;
            }
            case  "ALLERGIES": {
                initAllergiesAdapter();
                break;
            }

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initRInjectionAdapter() {
        title.setText(ALLERGIES_TITLE);
        initRecycleView();
        AllergiesDao dao = new AllergiesDao(this);
        List<Allergies> list = dao.getListByMasterId(PositionListener.getInstance().getSelectedDogId());
        if (list.size() > 0)
            DataPositionListener.getInstance().setSelectedItemId(list.get(0).getId());
        RecyclerView.Adapter<AllergiesAdapter.ViewHolder> signDataElementAdapter = new AllergiesAdapter(list,  this);
        recyclerView.setAdapter(signDataElementAdapter);
        addNew.setOnClickListener(v -> {
            Intent intent = new Intent(this, UniqueSignActivityAdd.class);
            startActivityForResult(intent, 100);
        });
    }

    private void initOInjectionAdapter() {
    }

    private void initDewormingAdapter() {
    }

    private void initBirthAdapter() {
    }

    private void initTeethAdapter() {
    }

    private void initTreatmentAdapter() {
    }

    private void initAllergiesAdapter() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initRecycleView() {
        RecyclerView.LayoutManager list_layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(list_layoutManager);
        recyclerView.setHasFixedSize(true);
    }


}
