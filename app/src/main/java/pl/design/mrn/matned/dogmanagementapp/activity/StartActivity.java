package pl.design.mrn.matned.dogmanagementapp.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pl.design.mrn.matned.dogmanagementapp.PositionListener;
import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.activity.fragment.DogItemAdapter;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.DogDao;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.DogModel;

public class StartActivity extends AppCompatActivity {

    private Button goToDogHealthCard;
    private Button goToDogDataCard;
    private Button goToSettingsCard;
    private Button goToInfoCard;
    private Button gotToEmergencyCard;

    private Button addDogButton;

    private int selectedPosition;

    private RecyclerView dogRecyclerView;
    private PositionListener positionListener;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        positionListener = PositionListener.getInstance();
        selectedPosition = positionListener.getPosition();
        if (savedInstanceState != null) {
            selectedPosition = (int) savedInstanceState.get("SELECTED_POSITION");
            positionListener = (PositionListener) savedInstanceState.get("POSITION_HOLDER");
        }
        initView();

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("SELECTED_POSITION",  selectedPosition);
        outState.putSerializable("POSITION_HOLDER", positionListener);
        super.onSaveInstanceState(outState);
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
        goToDogHealthCard = findViewById(R.id.healthButton);
        goToDogDataCard = findViewById(R.id.dogButton);
        goToSettingsCard = findViewById(R.id.settingsButton);
        goToInfoCard = findViewById(R.id.infoButton);
        gotToEmergencyCard = findViewById(R.id.emergencyButton);
        dogRecyclerView = findViewById(R.id.recyclerViewMain);
        addDogButton = findViewById(R.id.addDogButton);


    }

    @SuppressLint("ShowToast")
    private void initializeActionHoldersForButtons() {
        goToDogDataCard.setOnClickListener(clickListener(StartActivity.this, DogDataActivity.class));
        goToDogHealthCard.setOnClickListener(clickListener(StartActivity.this, HealthActivity.class));
        goToSettingsCard.setOnClickListener(clickListener(StartActivity.this, SettingsActivity.class));
        goToInfoCard.setOnClickListener(clickListener(StartActivity.this, InfoActivity.class));
        addDogButton.setOnClickListener(clickListener(StartActivity.this, AddDogActivity.class));
        gotToEmergencyCard.setOnClickListener(v -> Toast.makeText(StartActivity.this, "Position: "+ positionListener.getPosition(), Toast.LENGTH_SHORT).show());
    }



    private View.OnClickListener clickListener(Context context, Class aClass) {
        return v -> {
            Intent intent = new Intent(context, aClass);
            selectedPosition = positionListener.getPosition();
            intent.putExtra("SELECTED_POSITION", selectedPosition);
            startActivity(intent);
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
        RecyclerView.Adapter<DogItemAdapter.ViewHolder> dogAdapter = new DogItemAdapter(this, dogs, getResources());
        dogRecyclerView.setAdapter(dogAdapter);
    }

}
