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
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.Treatment;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.TreatmentDao;
import pl.design.mrn.matned.dogmanagementapp.listeners.DataPositionListener;

import static pl.design.mrn.matned.dogmanagementapp.ImageAdvancedFunction.setImage;
import static pl.design.mrn.matned.dogmanagementapp.TextStrings_PL.WARNING_FIELD;
import static pl.design.mrn.matned.dogmanagementapp.TextStrings_PL.WRONG_DATE;

public class TreatmentActivityEdit extends SuperEditClass{

    private Button save;
    private Button back;
    private Button delete;
    private EditText nameET;
    private EditText descET;
    private EditText dateET;
    private EditText nextDateET;
    private EditText noteET;

    private TreatmentDao dao;
    private Treatment treatment;
    private boolean isDatePutOrExp;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dao = new TreatmentDao(this);
        treatment = dao.findById(DataPositionListener.getInstance().getSelectedItemId());
        setContentView(R.layout.healthdata_treatment_add_edit);
        initialize();
        onSavedReload(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initLocalViews() {
        save = findViewById(R.id.treatment_save_btn);
        back = findViewById(R.id.treatment_back_btn);
        delete = findViewById(R.id.treatment_delete_btn);
        nameET = findViewById(R.id.treatment_name_dataText);
        descET = findViewById(R.id.treatment_description_dataText);
        dateET = findViewById(R.id.treatment_date_dataText);
        nextDateET = findViewById(R.id.treatment_next_date_dataText);
        noteET = findViewById(R.id.treatment_note_dataText);
        photoStampIV = findViewById(R.id.treatment_photo);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void setOtherOnClickListeners() {
        save.setOnClickListener(updateClickListener());
        back.setOnClickListener(v -> finish());
    }

    @Override
    protected void initDeleteOnClickListener() {
        delete.setOnClickListener(v-> showAlertButton());
    }

    @Override
    protected void deleteRecord() {
        deleteRecord(dao);
    }

    @Override
    protected View.OnClickListener updateClickListener() {
        return v -> {
            if(validation()) {
                treatment.setIllness(nameET.getText().toString());
                treatment.setDescription(descET.getText().toString());
                try {
                    treatment.setDateOfTreatment(dateFormat.parse(dateET.getText().toString()));
                } catch (ParseException e) {
                    Toast.makeText(this, WRONG_DATE, Toast.LENGTH_SHORT).show();
                }
                try {
                    treatment.setDateOfNextTreatment(dateFormat.parse(nextDateET.getText().toString()));
                } catch (ParseException e) {
                    Toast.makeText(this, WRONG_DATE, Toast.LENGTH_SHORT).show();
                }
                treatment.setNote(noteET.getText().toString());
                treatment.setPhoto(photoPath);
                dao.update(treatment);
                finish();
            }else{
                Toast.makeText(this, WARNING_FIELD, Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    protected void fillAllViews() {
        nameET.setText(treatment.getIllness());
        descET.setText(treatment.getDescription());
        if (treatment.getDateOfTreatment() != null)
            dateET.setText(dateFormat.format(treatment.getDateOfTreatment()));
        else dateET.setText("");
        if (treatment.getDateOfNextTreatment() != null)
            nextDateET.setText(dateFormat.format(treatment.getDateOfNextTreatment()));
        else nextDateET.setText("");
        noteET.setText(treatment.getNote());
        photoPath = treatment.getPhoto();
        if (Validate.notEmpty(photoPath))
            setImage(photoStampIV, photoPath, this, getResources());
    }

    @Override
    protected void onSavedReload(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            String name = savedInstanceState.getString("NAME");
            if (name != null) nameET.setText(name);
            String description = savedInstanceState.getString("DESC");
            if (description != null) descET.setText(description);
            String date = savedInstanceState.getString("DATE");
            if (date != null) dateET.setText(date);
            String nextDate = savedInstanceState.getString("NEXT_DATE");
            if (nextDate != null) nextDateET.setText(nextDate);
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
        outState.putString("DATE", dateET.getText().toString());
        outState.putString("NEXT_DATE", nextDateET.getText().toString());
        outState.putString("NOTE", noteET.getText().toString());
        outState.putString("PHOTO_PATH", photoPath);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected boolean validation() {
        return checkIsNotEmpty(nameET);
    }

    @Override
    protected void changeDateViews(Calendar cal) {
        if (isDatePutOrExp) dateET.setText(dateFormat.format(cal.getTime()));
        else nextDateET.setText(dateFormat.format(cal.getTime()));
    }

    @Override
    protected void initDatePicker() {
        dateET.setOnClickListener(v -> {
            isDatePutOrExp = true;
            datePickDialog();
        });
        dateET.setRawInputType(InputType.TYPE_NULL);
        dateET.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                isDatePutOrExp = true;
                datePickDialog();
            }
        });
        nextDateET.setOnClickListener(v -> {
            isDatePutOrExp = false;
            datePickDialog();
        });
        nextDateET.setRawInputType(InputType.TYPE_NULL);
        nextDateET.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                isDatePutOrExp = false;
                datePickDialog();
            }
        });
    }

}
