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
import pl.design.mrn.matned.dogmanagementapp.activity.adapters.health.AllergiesAdapter;
import pl.design.mrn.matned.dogmanagementapp.activity.adapters.health.BirthControlAdapter;
import pl.design.mrn.matned.dogmanagementapp.activity.adapters.health.DewormingAdapter;
import pl.design.mrn.matned.dogmanagementapp.activity.adapters.health.InjectionOtherAdapter;
import pl.design.mrn.matned.dogmanagementapp.activity.adapters.health.InjectionRabiesAdapter;
import pl.design.mrn.matned.dogmanagementapp.activity.adapters.health.TeethControlAdapter;
import pl.design.mrn.matned.dogmanagementapp.activity.adapters.health.TreatmentAdapter;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.Allergies;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.AllergiesDao;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.BirthControl;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.BirthControlDao;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.Deworming;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.DewormingDao;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.InjectionOther;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.InjectionOtherDao;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.InjectionRabid;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.InjectionRabidDao;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.TeethControl;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.TeethControlDao;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.Treatment;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.TreatmentDao;
import pl.design.mrn.matned.dogmanagementapp.listeners.DataPositionListener;
import pl.design.mrn.matned.dogmanagementapp.listeners.PositionListener;

import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE;
import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE_ADD;
import static pl.design.mrn.matned.dogmanagementapp.TextStrings.*;

public class HealthListActivity extends AppCompatActivity {


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
        title.setText(RABIES_VAXINE_TITLE);
        initRecycleView();
        InjectionRabidDao dao = new InjectionRabidDao(this);
        List<InjectionRabid> list = dao.getListByMasterId(PositionListener.getInstance().getSelectedDogId());
        if (list.size() > 0)
            DataPositionListener.getInstance().setSelectedItemId(list.get(0).getId());
        RecyclerView.Adapter<InjectionRabiesAdapter.ViewHolder> signDataElementAdapter = new InjectionRabiesAdapter(list,  this);
        recyclerView.setAdapter(signDataElementAdapter);
        initAddNewButtonClickListener(InjectionsRabidActivity.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initOInjectionAdapter() {
        title.setText(OTHER_VAXINE_TITLE);
        initRecycleView();
        InjectionOtherDao dao = new InjectionOtherDao(this);
        List<InjectionOther> list = dao.getListByMasterId(PositionListener.getInstance().getSelectedDogId());
        if (list.size() > 0)
            DataPositionListener.getInstance().setSelectedItemId(list.get(0).getId());
        RecyclerView.Adapter<InjectionOtherAdapter.ViewHolder> signDataElementAdapter = new InjectionOtherAdapter(list,  this);
        recyclerView.setAdapter(signDataElementAdapter);
        initAddNewButtonClickListener(InjectionsOtherActivity.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initDewormingAdapter() {
        title.setText(DEWORMING_TITLE);
        initRecycleView();
        DewormingDao dao = new DewormingDao(this);
        List<Deworming> list = dao.getListByMasterId(PositionListener.getInstance().getSelectedDogId());
        if (list.size() > 0)
            DataPositionListener.getInstance().setSelectedItemId(list.get(0).getId());
        RecyclerView.Adapter<DewormingAdapter.ViewHolder> signDataElementAdapter = new DewormingAdapter(list,  this);
        recyclerView.setAdapter(signDataElementAdapter);
        initAddNewButtonClickListener(DeWormingActivity.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initBirthAdapter() {
        title.setText(BIRTH_TITLE);
        initRecycleView();
        BirthControlDao dao = new BirthControlDao(this);
        List<BirthControl> list = dao.getListByMasterId(PositionListener.getInstance().getSelectedDogId());
        if (list.size() > 0)
            DataPositionListener.getInstance().setSelectedItemId(list.get(0).getId());
        RecyclerView.Adapter<BirthControlAdapter.ViewHolder> signDataElementAdapter = new BirthControlAdapter(list,  this);
        recyclerView.setAdapter(signDataElementAdapter);
        initAddNewButtonClickListener(BirthControlActivity.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initTeethAdapter() {
        title.setText(TEETH_TITLE);
        initRecycleView();
        TeethControlDao dao = new TeethControlDao(this);
        List<TeethControl> list = dao.getListByMasterId(PositionListener.getInstance().getSelectedDogId());
        if (list.size() > 0)
            DataPositionListener.getInstance().setSelectedItemId(list.get(0).getId());
        RecyclerView.Adapter<TeethControlAdapter.ViewHolder> signDataElementAdapter = new TeethControlAdapter(list,  this);
        recyclerView.setAdapter(signDataElementAdapter);
        initAddNewButtonClickListener(TeethControlActivity.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initTreatmentAdapter() {
        title.setText(TREATMENT_TITLE);
        initRecycleView();
        TreatmentDao dao = new TreatmentDao(this);
        List<Treatment> list = dao.getListByMasterId(PositionListener.getInstance().getSelectedDogId());
        if (list.size() > 0)
            DataPositionListener.getInstance().setSelectedItemId(list.get(0).getId());
        RecyclerView.Adapter<TreatmentAdapter.ViewHolder> signDataElementAdapter = new TreatmentAdapter(list,  this);
        recyclerView.setAdapter(signDataElementAdapter);
        initAddNewButtonClickListener(TreatmentActivity.class);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initAllergiesAdapter() {
        title.setText(ALLERGIES_TITLE);
        initRecycleView();
        AllergiesDao dao = new AllergiesDao(this);
        List<Allergies> list = dao.getListByMasterId(PositionListener.getInstance().getSelectedDogId());
        if (list.size() > 0)
            DataPositionListener.getInstance().setSelectedItemId(list.get(0).getId());
        RecyclerView.Adapter<AllergiesAdapter.ViewHolder> signDataElementAdapter = new AllergiesAdapter(list,  this);
        recyclerView.setAdapter(signDataElementAdapter);
        initAddNewButtonClickListener(AllergiesActivity.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initRecycleView() {
        RecyclerView.LayoutManager list_layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(list_layoutManager);
        recyclerView.setHasFixedSize(true);
    }



    private void initAddNewButtonClickListener(Class clazz) {
        addNew.setOnClickListener(v -> {
            Intent intent = new Intent(this, clazz);
            intent.putExtra(USAGE, USAGE_ADD);
            startActivityForResult(intent, 100);
        });
    }

}
