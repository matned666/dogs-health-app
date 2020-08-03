package pl.design.mrn.matned.dogmanagementapp.activity.dataactivity.add;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.activity.adapters.ChipDataElementAdapter;
import pl.design.mrn.matned.dogmanagementapp.activity.adapters.NoteDataElementAdapter;
import pl.design.mrn.matned.dogmanagementapp.activity.adapters.OwnerDataElementAdapter;
import pl.design.mrn.matned.dogmanagementapp.activity.adapters.SignDataElementAdapter;
import pl.design.mrn.matned.dogmanagementapp.activity.adapters.TattooDataElementAdapter;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.Chip;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.ChipDao;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.Note;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.NoteDao;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.Owner;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.OwnerDao;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.SpecialSign;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.SpecialSignDao;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.Tattoo;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.TattooDao;
import pl.design.mrn.matned.dogmanagementapp.listeners.DataPositionListener;

import static pl.design.mrn.matned.dogmanagementapp.Statics.CHIP;
import static pl.design.mrn.matned.dogmanagementapp.Statics.LIST_ELEMENT_ACTIVITY;
import static pl.design.mrn.matned.dogmanagementapp.Statics.NOTE_ACTIVITY;
import static pl.design.mrn.matned.dogmanagementapp.Statics.OWNER;
import static pl.design.mrn.matned.dogmanagementapp.Statics.SIGN;
import static pl.design.mrn.matned.dogmanagementapp.Statics.TATTOO;
import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE_ADD;
import static pl.design.mrn.matned.dogmanagementapp.TextStrings.CHIP_LIST_TITLE;
import static pl.design.mrn.matned.dogmanagementapp.TextStrings.NOTE_LIST_TITLE;
import static pl.design.mrn.matned.dogmanagementapp.TextStrings.OWNER_LIST_TITLE;
import static pl.design.mrn.matned.dogmanagementapp.TextStrings.SPECIAL_SIGN_LIST_TITLE;
import static pl.design.mrn.matned.dogmanagementapp.TextStrings.TATTOO_LIST_TITLE;

public class DataChoiceListActivityAdd extends AppCompatActivity {

    private String listActivity;
    private TextView title;
    private RecyclerView recyclerView;
    private Button addNew;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataPositionListener.getInstance().setPosition(0);
        Intent intent = getIntent();
        listActivity = intent.getStringExtra(LIST_ELEMENT_ACTIVITY);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.data_list_activity);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void init() {
        recyclerView = findViewById(R.id.recyclerViewDataList);
        Button cancelBtn = findViewById(R.id.cancelDataList);
        addNew = findViewById(R.id.addNewDataList);
        title = findViewById(R.id.titleDataList);
        cancelBtn.setOnClickListener(v -> {
            finish();
        });
        startRecyclerView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    private void startRecyclerView() {
        switch (listActivity) {
            case CHIP: {
                initChipList();
                break;
            }
            case TATTOO: {
                initTattooList();
                break;
            }
            case SIGN: {
                initSignsList();
                break;
            }
            case NOTE_ACTIVITY: {
                initNoteList();
                break;
            }
            case OWNER: {
                initOwnerList();
                break;
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initChipList() {
        title.setText(CHIP_LIST_TITLE);
        initRecycleView();
        ChipDao dao = new ChipDao(this);
        List<Chip> list = dao.findAll();
        if (list.size() > 0)
            DataPositionListener.getInstance().setSelectedItemId(list.get(0).getChipId());
        RecyclerView.Adapter<ChipDataElementAdapter.ViewHolder> chipAdapter = new ChipDataElementAdapter(list, USAGE_ADD, this);
        recyclerView.setAdapter(chipAdapter);
        addNew.setOnClickListener(v -> {
            Intent intent = new Intent(this, ChipActivityAdd.class);
            startActivityForResult(intent, 100);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initOwnerList() {
        title.setText(OWNER_LIST_TITLE);
        initRecycleView();
        OwnerDao dao = new OwnerDao(this);
        List<Owner> list = dao.findAll();
        if (list.size() > 0)
            DataPositionListener.getInstance().setSelectedItemId(list.get(0).getId());
        RecyclerView.Adapter<OwnerDataElementAdapter.ViewHolder> ownerAdapter = new OwnerDataElementAdapter(list, USAGE_ADD, this);
        recyclerView.setAdapter(ownerAdapter);
        addNew.setOnClickListener(v -> {
            Intent intent = new Intent(this, OwnerActivityAdd.class);
            startActivityForResult(intent, 100);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initSignsList() {
        title.setText(SPECIAL_SIGN_LIST_TITLE);
        initRecycleView();
        SpecialSignDao dao = new SpecialSignDao(this);
        List<SpecialSign> list = dao.findAll();
        if (list.size() > 0)
            DataPositionListener.getInstance().setSelectedItemId(list.get(0).getSignId());
        RecyclerView.Adapter<SignDataElementAdapter.ViewHolder> signDataElementAdapter = new SignDataElementAdapter(list, USAGE_ADD, this);
        recyclerView.setAdapter(signDataElementAdapter);
        addNew.setOnClickListener(v -> {
            Intent intent = new Intent(this, UniqueSignActivityAdd.class);
            startActivityForResult(intent, 100);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initNoteList() {
        title.setText(NOTE_LIST_TITLE);
        initRecycleView();
        NoteDao dao = new NoteDao(this);
        List<Note> list = dao.findAll();
        if (list.size() > 0)
            DataPositionListener.getInstance().setSelectedItemId(list.get(0).getNoteId());
        RecyclerView.Adapter<NoteDataElementAdapter.ViewHolder> noteDataElementAdapter = new NoteDataElementAdapter(list, USAGE_ADD, this);
        recyclerView.setAdapter(noteDataElementAdapter);
        addNew.setOnClickListener(v -> {
            Intent intent = new Intent(this, NoteActivityAdd.class);
            startActivityForResult(intent, 100);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initTattooList() {
        title.setText(TATTOO_LIST_TITLE);
        initRecycleView();
        TattooDao dao = new TattooDao(this);
        List<Tattoo> list = dao.findAll();
        if (list.size() > 0)
            DataPositionListener.getInstance().setSelectedItemId(list.get(0).getTattooId());
        RecyclerView.Adapter<TattooDataElementAdapter.ViewHolder> tattooDataElementAdapter = new TattooDataElementAdapter(list, USAGE_ADD, this);
        recyclerView.setAdapter(tattooDataElementAdapter);
        addNew.setOnClickListener(v -> {
            Intent intent = new Intent(this, TattooActivityAdd.class);
            startActivityForResult(intent, 100);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initRecycleView() {
        RecyclerView.LayoutManager list_layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(list_layoutManager);
        recyclerView.setHasFixedSize(true);
    }

}
