package pl.design.mrn.matned.dogmanagementapp.activity;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import pl.design.mrn.matned.dogmanagementapp.activity.dataactivity.info.BreedingActivityInfo;
import pl.design.mrn.matned.dogmanagementapp.activity.dataactivity.info.DataChoiceListActivityInfo;
import pl.design.mrn.matned.dogmanagementapp.listeners.PositionListener;
import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.DogDao;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.DogModel;

import static pl.design.mrn.matned.dogmanagementapp.ImageAdvancedFunction.setImage;
import static pl.design.mrn.matned.dogmanagementapp.Statics.BREEDING;
import static pl.design.mrn.matned.dogmanagementapp.Statics.CHIP;
import static pl.design.mrn.matned.dogmanagementapp.Statics.LIST_ELEMENT_ACTIVITY;
import static pl.design.mrn.matned.dogmanagementapp.Statics.NOTE;
import static pl.design.mrn.matned.dogmanagementapp.Statics.OWNER;
import static pl.design.mrn.matned.dogmanagementapp.Statics.SIGN;
import static pl.design.mrn.matned.dogmanagementapp.Statics.START;
import static pl.design.mrn.matned.dogmanagementapp.Statics.TATTOO;
import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE;
import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE_EDIT;
import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE_INFO;

public class DogDataActivity extends AppCompatActivity {

    private TextView dogNameET;
    private TextView dogRaceET;
    private TextView dogBirthDatePickerET;
    private TextView dogColorET;
    private TextView dogSexET;
    private Button chipBtn;
    private Button tattooBtn;
    private Button signsBtn;
    private Button notesBtn;
    private Button breedingBtn;
    private Button ownerBtn;
    private Button editDog;
    private Button backBtn;
    private ImageView dogPhoto;

    private DogModel dog;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void init() {

        DogDao dao = new DogDao(DogDataActivity.this);
        int dogId = PositionListener.getInstance().getSelectedDogId();
        dog = dao.findById(dogId);
        setContentView(R.layout.dog_informations);
        initView();
        putData();
    }

    private void initView() {
        dogNameET = findViewById(R.id.dogNameInfo);
        dogRaceET = findViewById(R.id.dogRaceInfo);
        dogBirthDatePickerET = findViewById(R.id.birthDateInfo);
        dogColorET = findViewById(R.id.dogColorInfo);
        dogSexET = findViewById(R.id.dogSexInfo);
        chipBtn = findViewById(R.id.chipBtnInfo);
        tattooBtn = findViewById(R.id.tattooBtnInfo);
        signsBtn = findViewById(R.id.signsBtnInfo);
        notesBtn = findViewById(R.id.notesBtnInfo);
        breedingBtn = findViewById(R.id.breedingBtnInfo);
        ownerBtn = findViewById(R.id.ownerBtnInfo);
        editDog = findViewById(R.id.editDogBtnFromInfo);
        backBtn = findViewById(R.id.backInfo);
        dogPhoto = findViewById(R.id.photoInfo);
        initButtonOnClickListeners();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void putData() {
        dogNameET.setText(dog.getName());
        dogRaceET.setText(dog.getRace());
        dogBirthDatePickerET.setText(dog.getBirthDate().toString());
        dogColorET.setText(dog.getColor());
        dogSexET.setText(dog.getSex().name());
        if (dog.getDogImage() != null)
            setImage(dogPhoto, dog.getDogImage(), this, getResources());
        else {
            Drawable drawable;
            drawable = getResources().getDrawable(R.drawable.ic_stat_name);
            dogPhoto.setImageDrawable(drawable);
        }
    }

    private void initButtonOnClickListeners() {
        backBtn.setOnClickListener(clickListener(StartActivity.class, START));
        chipBtn.setOnClickListener(v -> showDial(DataChoiceListActivityInfo.class,CHIP));
        tattooBtn.setOnClickListener(v -> showDial(DataChoiceListActivityInfo.class, TATTOO));
        signsBtn.setOnClickListener(v -> showDial(DataChoiceListActivityInfo.class, SIGN));
        ownerBtn.setOnClickListener(v -> showDial(DataChoiceListActivityInfo.class, OWNER));
        notesBtn.setOnClickListener(v -> showDial(DataChoiceListActivityInfo.class, NOTE));
        breedingBtn.setOnClickListener(v -> showDial(BreedingActivityInfo.class, BREEDING));
        editDog.setOnClickListener(clickListener(Edit_DogActivity.class, USAGE_EDIT));
    }

    public void showDial(Class clazz, String el) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra(LIST_ELEMENT_ACTIVITY, el);
        startActivity(intent);
    }


    private View.OnClickListener clickListener(Class clazz, String use) {
        return v -> {
            Intent intent = new Intent(this, clazz);
            intent.putExtra(LIST_ELEMENT_ACTIVITY, use);
            intent.putExtra(USAGE, USAGE_INFO);
            startActivity(intent);
        };
    }
}
