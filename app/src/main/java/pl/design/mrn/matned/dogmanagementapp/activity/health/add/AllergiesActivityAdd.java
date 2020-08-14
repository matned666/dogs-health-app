package pl.design.mrn.matned.dogmanagementapp.activity.health.add;

import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.util.Calendar;

import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.Allergies;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.AllergiesDao;
import pl.design.mrn.matned.dogmanagementapp.listeners.PositionListener;

import static pl.design.mrn.matned.dogmanagementapp.TextStrings.*;
import static pl.design.mrn.matned.dogmanagementapp.TextStrings.WRONG_DATE;

public class AllergiesActivityAdd extends SuperAddClass{


    private Button save;
    private Button back;
    private Button delete;
    private EditText nameET;
    private EditText descET;
    private EditText dateOfDiscoveryET;
    private EditText dateOfTreatmentET;
    private EditText nextDateOfTreatmentET;
    private EditText noteET;

    private AllergiesDao dao;
    private Allergies allergies;
    private int datePut;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dao = new AllergiesDao(this);
        allergies = new Allergies();
        datePut = 0;
        setContentView(R.layout.healthdata_allergy_add_edit);
        initialize(dao);
//      TODO  onSavedReload(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initLocalViews() {
        save = findViewById(R.id.allergy_save_btn);
        back = findViewById(R.id.allergy_back_btn);
        delete = findViewById(R.id.allergy_delete_btn);
        nameET = findViewById(R.id.allergen_name_textData);
        descET = findViewById(R.id.allergen_description_textData);
        dateOfDiscoveryET = findViewById(R.id.allergen_discoveryDate_textData);
        dateOfTreatmentET = findViewById(R.id.allergen_treatmentStartDate_textData);
        nextDateOfTreatmentET = findViewById(R.id.allergen_treatmentNextDate_textData);
        noteET = findViewById(R.id.allergen_note_textData);
        photoStampIV = findViewById(R.id.allergy_photo);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void setOtherOnClickListeners() {
        save.setOnClickListener(addClickListener());
        back.setOnClickListener(v -> finish());
    }

    @Override
    protected void removeDeleteButton() {
        delete.setVisibility(View.GONE);
    }

    @Override
    protected View.OnClickListener addClickListener() {
        return v -> {
            if(validation()) {
                allergies.setAllergen(nameET.getText().toString());
                allergies.setDescription(descET.getText().toString());
                try {
                    allergies.setDateOfTreatment(dateFormat.parse(dateOfTreatmentET.getText().toString()));
                } catch (ParseException e) {
                    Toast.makeText(this, WRONG_DATE, Toast.LENGTH_SHORT).show();
                }
                try {
                    allergies.setDateOfTreatment(dateFormat.parse(dateOfTreatmentET.getText().toString()));
                } catch (ParseException ignored) {
                }
                try {
                    allergies.setDateOfNextTreatment(dateFormat.parse(nextDateOfTreatmentET.getText().toString()));
                } catch (ParseException ignored) {
                }
                allergies.setNote(noteET.getText().toString());
                allergies.setPhoto(photoPath);
                allergies.setDogId(PositionListener.getInstance().getSelectedDogId());
                dao.add(allergies);
                finish();
            }else{
                Toast.makeText(this, WARNING_FIELD, Toast.LENGTH_SHORT).show();
            }
        };
    }


    @Override
    protected boolean validation() {
        return checkIsNotEmpty(nameET);
    }

    @Override
    protected void changeDateViews(Calendar cal) {
        if (datePut == 1) dateOfTreatmentET.setText(dateFormat.format(cal.getTime()));
        else if (datePut == 2) nextDateOfTreatmentET.setText(dateFormat.format(cal.getTime()));
        else dateOfDiscoveryET.setText(dateFormat.format(cal.getTime()));
    }

    @Override
    protected void initDatePicker() {
        dateOfTreatmentET.setOnClickListener(v -> {
            datePut = 1;
            datePickDialog();
        });
        dateOfTreatmentET.setRawInputType(InputType.TYPE_NULL);
        dateOfTreatmentET.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                datePut = 1;
                datePickDialog();
            }
        });
        nextDateOfTreatmentET.setOnClickListener(v -> {
            datePut = 2;
            datePickDialog();
        });
        nextDateOfTreatmentET.setRawInputType(InputType.TYPE_NULL);
        nextDateOfTreatmentET.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                datePut = 2;
                datePickDialog();
            }
        });

        dateOfDiscoveryET.setOnClickListener(v -> {
            datePut = 3;
            datePickDialog();
        });
        dateOfDiscoveryET.setRawInputType(InputType.TYPE_NULL);
        dateOfDiscoveryET.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                datePut = 3;
                datePickDialog();
            }
        });
    }

}

