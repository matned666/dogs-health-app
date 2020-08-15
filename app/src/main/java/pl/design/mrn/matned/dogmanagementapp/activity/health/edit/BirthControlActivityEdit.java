package pl.design.mrn.matned.dogmanagementapp.activity.health.edit;

import android.annotation.SuppressLint;
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
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.BirthControl;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.BirthControlDao;
import pl.design.mrn.matned.dogmanagementapp.listeners.DataPositionListener;

import static pl.design.mrn.matned.dogmanagementapp.TextStrings.WARNING_FIELD;
import static pl.design.mrn.matned.dogmanagementapp.TextStrings.WRONG_DATE;

public class BirthControlActivityEdit extends SuperEditClass{

    protected Button save;
    protected Button delete;
    protected Button back;
    private EditText numberOfPupsET;
    private EditText descET;
    private EditText dateET;
    private EditText noteET;

    private BirthControlDao dao;
    private BirthControl birthControl;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dao = new BirthControlDao(this);
        birthControl = dao.findById(DataPositionListener.getInstance().getSelectedItemId());
        setContentView(R.layout.healthdata_birth_control_add_edit);
        initialize();
        onSavedReload(savedInstanceState);
    }

    @Override
    protected void initLocalViews() {
        save = findViewById(R.id.birth_save_btn);
        delete = findViewById(R.id.birth_delete_button);
        back = findViewById(R.id.birth_back_button);
        photoStampIV = findViewById(R.id.birth_photo);
        numberOfPupsET = findViewById(R.id.birth_number_of_pups_dataText);
        descET = findViewById(R.id.birth_description_dataText);
        dateET = findViewById(R.id.birth_date_dataText);
        noteET = findViewById(R.id.birth_note_dataText);
        photoStampIV.setVisibility(View.GONE);
    }

    @Override
    protected void onSavedReload(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            String name = savedInstanceState.getString("NAME");
            if (name != null) numberOfPupsET.setText(name);
            String description = savedInstanceState.getString("DESC");
            if (description != null) descET.setText(description);
            String date = savedInstanceState.getString("DATE");
            if (date != null) dateET.setText(date);
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
        outState.putString("NAME", numberOfPupsET.getText().toString());
        outState.putString("DESC", descET.getText().toString());
        outState.putString("DATE", dateET.getText().toString());
        outState.putString("NOTE", noteET.getText().toString());
        outState.putString("PHOTO_PATH", photoPath);
        super.onSaveInstanceState(outState);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void fillAllViews() {
        numberOfPupsET.setText(Integer.toString(birthControl.getNumberOfChildren()));
        descET.setText(birthControl.getDescription());
        dateET.setText(dateFormat.format(birthControl.getDateOfBirth()));
        noteET.setText(birthControl.getNote());
        photoPath = birthControl.getPhoto();
        if (Validate.notEmpty(photoPath))showImage();
    }

    @Override
    protected void initDatePicker() {
        dateET.setOnClickListener(v -> datePickDialog());
        dateET.setRawInputType(InputType.TYPE_NULL);
        dateET.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                datePickDialog();
            }
        });
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void setOtherOnClickListeners() {
        save.setOnClickListener(updateClickListener());
        back.setOnClickListener(v -> finish());
    }

    @Override
    protected View.OnClickListener updateClickListener() {
        return v -> {
            if(validation()) {
                birthControl.setNumberOfChildren(Integer.parseInt(numberOfPupsET.getText().toString()));
                birthControl.setDescription(descET.getText().toString());
                try {
                    birthControl.setDateOfBirth(dateFormat.parse(dateET.getText().toString()));
                } catch (ParseException e) {
                    Toast.makeText(this, WRONG_DATE, Toast.LENGTH_SHORT).show();
                }
                birthControl.setNote(noteET.getText().toString());
                birthControl.setPhoto(photoPath);
                dao.update(birthControl);
                finish();
            }else{
                Toast.makeText(this, WARNING_FIELD, Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    protected void changeDateViews(Calendar cal) {
        dateET.setText(dateFormat.format(cal.getTime()));
    }


    @Override
    protected boolean validation() {
        boolean b1 = checkIsNumeric(numberOfPupsET);
        boolean b2 = checkDateFormat(dateET);
        return b1 && b2;
    }

    @Override
    protected void initDeleteOnClickListener() {
        showAlertButton();
    }

    @Override
    protected void deleteRecord() {
        delete.setOnClickListener(v-> deleteRecord(dao));
    }
}
