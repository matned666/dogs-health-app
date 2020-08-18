package pl.design.mrn.matned.dogmanagementapp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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

import pl.design.mrn.matned.dogmanagementapp.dataBase.app.MessagesDao;
import pl.design.mrn.matned.dogmanagementapp.listeners.PositionListener;
import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.activity.adapters.DogItemAdapter;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.DogDao;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.DogModel;

public class StartActivity extends AppCompatActivity {

    public static final String SELECTED_POSITION = "SELECTED_POSITION";
    public static final String POSITION_ID = "POSITION_ID";
    private Button goToDogHealthCard;
    private Button goToDogDataCard;
    private Button goToSettingsCard;
    private Button goToInfoCard;
    private Button gotToMessagesCard;

    private Button addDogButton;

    private RecyclerView dogRecyclerView;
    private PositionListener positionListener;

    private DogDao dao;
    private List<DogModel> dogs;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dao = new DogDao(this);
        dogs = dao.findAll();
        positionListener = PositionListener.getInstance();
        positionListener.setSelectedDogId(dogs.get(0).getId());
        positionListener.setPosition(0);
        if (savedInstanceState != null) {
            Integer selectedPos = ((Integer) savedInstanceState.get(SELECTED_POSITION));
            Integer selectedId = ((Integer) savedInstanceState.get(POSITION_ID));
            if(selectedPos != null) positionListener.setPosition(selectedPos);
            if(selectedId != null) positionListener.setSelectedDogId(selectedId);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onResume() {
        super.onResume();
        dogs = dao.findAll();
        initView();
        MessagesDao mDao = new MessagesDao(this);
        if (mDao.isAnyUnreadMessage()){
            Drawable img = getResources().getDrawable(R.drawable.ic_message_incoming);
            gotToMessagesCard.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
        }else{
            Drawable img = getResources().getDrawable(R.drawable.ic_message);
            gotToMessagesCard.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("SELECTED_POSITION",  positionListener.getPosition());
        outState.putSerializable("POSITION_ID", positionListener.getSelectedDogId());
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
        gotToMessagesCard = findViewById(R.id.emergencyButton);
        dogRecyclerView = findViewById(R.id.recyclerViewMain);
        addDogButton = findViewById(R.id.addDogButton);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("ShowToast")
    private void initializeActionHoldersForButtons() {
        if(dogs.size() > 0) goToDogDataCard.setOnClickListener(clickListener(DogDataActivity.class));
        else goToDogDataCard.setOnClickListener(v -> Toast.makeText(StartActivity.this, "Lista psów jest pusta, dodaj jakiegoś.", Toast.LENGTH_SHORT).show());
        if(dogs.size() > 0) goToDogHealthCard.setOnClickListener(clickListener(HealthActivity.class));
        else goToDogHealthCard.setOnClickListener(v -> Toast.makeText(StartActivity.this, "Lista psów jest pusta, dodaj jakiegoś.", Toast.LENGTH_SHORT).show());
        goToSettingsCard.setOnClickListener(clickListener(SettingsActivity.class));
        goToInfoCard.setOnClickListener(clickListener(InfoActivity.class));
        addDogButton.setOnClickListener(clickListenerAdd());
        gotToMessagesCard.setOnClickListener(clickListener(MessagesListActivity.class));
    }

    private View.OnClickListener clickListener(Class aClass) {
        return v -> {
            Intent intent = new Intent(StartActivity.this, aClass);
            startActivity(intent);
        };
    }

    private View.OnClickListener clickListenerAdd() {
        return v -> {
            Intent intent = new Intent(StartActivity.this, Add_DogActivity.class);
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
        RecyclerView.Adapter<DogItemAdapter.ViewHolder> dogAdapter = new DogItemAdapter(this, dogs, getResources());
        dogRecyclerView.setAdapter(dogAdapter);
    }

}
