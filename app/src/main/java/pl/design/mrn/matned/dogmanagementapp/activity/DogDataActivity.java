package pl.design.mrn.matned.dogmanagementapp.activity;


import android.content.Context;
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

import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.DogDao;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.DogModel;

import static pl.design.mrn.matned.dogmanagementapp.ImageAdvancedFunction.setImage;

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

    private Integer dogId;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dog_informations);
        initView();
        Intent mIntent = getIntent();
        dogId = mIntent.getIntExtra("SELECTED_POSITION", 0) + 1;
        putData(dogId);

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
        editDog = findViewById(R.id.editSelectedDog);
        backBtn = findViewById(R.id.backInfo);
        dogPhoto = findViewById(R.id.photoInfo);
        backBtn.setOnClickListener(clickListener(this));
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void putData(int dogId) {
        DogDao dao = new DogDao(DogDataActivity.this);
        DogModel dog = dao.findById(dogId);
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

    private View.OnClickListener clickListener(Context context) {
        return v -> {
            Intent intent = new Intent(context, StartActivity.class);
            intent.putExtra("SELECTED_POSITION2", dogId-1);
            startActivity(intent);
        };
    }
}
