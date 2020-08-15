package pl.design.mrn.matned.dogmanagementapp.activity.health.edit;

import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import java.io.File;
import java.text.ParseException;
import java.util.Calendar;

import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.Validate;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.Allergies;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.AllergiesDao;
import pl.design.mrn.matned.dogmanagementapp.listeners.DataPositionListener;
import pl.design.mrn.matned.dogmanagementapp.listeners.PositionListener;

import static pl.design.mrn.matned.dogmanagementapp.TextStrings.WARNING_FIELD;
import static pl.design.mrn.matned.dogmanagementapp.TextStrings.WRONG_DATE;

public class AllergiesActivityEdit extends SuperEditClass{


    private Button save;
    private Button back;
    private Button delete;
    private EditText nameET;
    private EditText descET;
    private EditText dateOfDiscoveryET;
    private EditText dateOfTreatmentET;
    private EditText nextDateOfTreatmentET;
    private EditText noteET;

    private int datePut;
    private AllergiesDao dao;
    private Allergies allergy;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dao = new AllergiesDao(this);
        allergy = dao.findById(DataPositionListener.getInstance().getSelectedItemId());
        datePut = 0;
        setContentView(R.layout.healthdata_allergy_add_edit);
        initialize();
        onSavedReload(savedInstanceState);
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
        photoStampIV.setVisibility(View.GONE);
    }

    @Override
    protected void fillAllViews() {
        nameET.setText(allergy.getAllergen());
        descET.setText(allergy.getDescription());
        if(allergy.getDateOfDetection() != null) dateOfDiscoveryET.setText(dateFormat.format(allergy.getDateOfDetection()));
        if(allergy.getDateOfTreatment() != null) dateOfTreatmentET.setText(dateFormat.format(allergy.getDateOfTreatment()));
        if(allergy.getDateOfNextTreatment() != null) nextDateOfTreatmentET.setText(dateFormat.format(allergy.getDateOfNextTreatment()));
        noteET.setText(allergy.getNote());
        photoPath = allergy.getPhoto();
        if (Validate.notEmpty(photoPath))showImage();
    }

    @Override
    protected void onSavedReload(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            String name = savedInstanceState.getString("NAME");
            if (name != null) nameET.setText(name);
            String description = savedInstanceState.getString("DESC");
            if (description != null) descET.setText(description);
            String dateDiscovery = savedInstanceState.getString("DATE");
            if (dateDiscovery != null) dateOfDiscoveryET.setText(dateDiscovery);
            String treatmentDate = savedInstanceState.getString("NEXT_DATE");
            if (treatmentDate != null) dateOfTreatmentET.setText(treatmentDate);
            String nextTreatmentDate = savedInstanceState.getString("NEXT_NEXT_DATE");
            if (nextTreatmentDate != null) nextDateOfTreatmentET.setText(nextTreatmentDate);
            String note = savedInstanceState.getString("NOTE");
            if (note != null) noteET.setText(note);
            photoPath = savedInstanceState.getString("PHOTO_PATH");
            if (Validate.notEmpty(photoPath)) {
                photoUri = FileProvider.getUriForFile(
                        this,
                        "pl.design.mrn.matned.dogmanagementapp.fileprovider",
                        new File(photoPath));
                showImage();
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("NAME", nameET.getText().toString());
        outState.putString("DESC", descET.getText().toString());
        outState.putString("DATE", dateOfDiscoveryET.getText().toString());
        outState.putString("NEXT_DATE", dateOfTreatmentET.getText().toString());
        outState.putString("NEXT_NEXT_DATE", nextDateOfTreatmentET.getText().toString());
        outState.putString("NOTE", noteET.getText().toString());
        outState.putString("PHOTO_PATH", photoPath);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void initDeleteOnClickListener() {
        showAlertButton();
    }

    @Override
    protected void deleteRecord() {
        delete.setOnClickListener(v-> deleteRecord(dao));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void setOtherOnClickListeners() {
        save.setOnClickListener(updateClickListener());
        back.setOnClickListener(v -> finish());
    }

    @Override
    protected View.OnClickListener updateClickListener() {
        return v -> {
            if(validation()) {
                allergy.setAllergen(nameET.getText().toString());
                allergy.setDescription(descET.getText().toString());
                try {
                    allergy.setDateOfDetection(dateFormat.parse(dateOfDiscoveryET.getText().toString()));
                } catch (ParseException e) {
                    Toast.makeText(this, WRONG_DATE, Toast.LENGTH_SHORT).show();
                }
                try {
                    allergy.setDateOfTreatment(dateFormat.parse(dateOfTreatmentET.getText().toString()));
                } catch (ParseException ignored) {
                }
                try {
                    allergy.setDateOfNextTreatment(dateFormat.parse(nextDateOfTreatmentET.getText().toString()));
                } catch (ParseException ignored) {
                }
                allergy.setNote(noteET.getText().toString());
                allergy.setPhoto(photoPath);
                allergy.setDogId(PositionListener.getInstance().getSelectedDogId());
                dao.update(allergy);
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

