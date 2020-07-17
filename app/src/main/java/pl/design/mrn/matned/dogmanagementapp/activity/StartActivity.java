package pl.design.mrn.matned.dogmanagementapp.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.DogDao;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.DogModel;

public class StartActivity extends AppCompatActivity {

    private Button gotToDogHealthCard;
    private Button gotToDogDataCard;
    private Button gotToSettingsCard;
    private Button gotToInfoCard;
    private Button gotToEmergencyCard;

    private Button addDogButton;

    private RecyclerView dogRecyclerView;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initView() {
        initializeButtons();
        initializeActionHoldersForButtons();
        initRecycleView();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceType")
    private void initializeButtons() {
        gotToDogHealthCard = findViewById(R.id.healthButton);
        gotToDogDataCard = findViewById(R.id.dogButton);
        gotToSettingsCard = findViewById(R.id.settingsButton);
        gotToInfoCard = findViewById(R.id.infoButton);
        gotToEmergencyCard = findViewById(R.id.emergencyButton);
        dogRecyclerView = findViewById(R.id.recyclerViewMain);
        addDogButton = findViewById(R.id.addDogButton);


    }

    private void initializeActionHoldersForButtons() {
        gotToDogDataCard.setOnClickListener(clickListener(StartActivity.this, DogDataActivity.class, 101));
        gotToDogHealthCard.setOnClickListener(clickListener(StartActivity.this, HealthActivity.class,102));
        gotToSettingsCard.setOnClickListener(clickListener(StartActivity.this, SettingsActivity.class, 103));
        gotToInfoCard.setOnClickListener(clickListener(StartActivity.this, InfoActivity.class, 104));
        gotToEmergencyCard.setOnClickListener(clickListener(StartActivity.this, EmergencyActivity.class, 105));
        addDogButton.setOnClickListener(clickListener(StartActivity.this, AddDogActivity.class, 106));
    }

    private View.OnClickListener clickListener(Context context, Class aClass, int number) {
        return v -> {
            Intent intent = new Intent(context, aClass);
            startActivityForResult(intent, number);
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initRecycleView() {
        RecyclerView.LayoutManager dogList_layoutManager = new LinearLayoutManager(this);
        dogRecyclerView.setLayoutManager(dogList_layoutManager);
        dogRecyclerView.setHasFixedSize(true);
        addNewDogList();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addNewDogList() {
        DogDao dao = new DogDao(StartActivity.this);
        List<DogModel> dogs = dao.findAll();
        RecyclerView.Adapter<DogItemAdapter.ViewHolder> dogAdapter = new DogItemAdapter(this, dogs);
        dogRecyclerView.setAdapter(dogAdapter);
    }

}
