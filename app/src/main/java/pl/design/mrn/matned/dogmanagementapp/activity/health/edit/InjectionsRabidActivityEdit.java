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
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.InjectionRabid;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.InjectionRabidDao;
import pl.design.mrn.matned.dogmanagementapp.listeners.DataPositionListener;

import static pl.design.mrn.matned.dogmanagementapp.ImageAdvancedFunction.setImage;
import static pl.design.mrn.matned.dogmanagementapp.TextStrings_PL.WARNING_FIELD;

public class InjectionsRabidActivityEdit extends SuperEditClass{

    private Button save;
    private Button back;
    private Button delete;
    private EditText medicineET;
    private EditText descET;
    private EditText dateET;
    private EditText nextDateET;
    private EditText noteET;

    private InjectionRabidDao dao;
    private InjectionRabid injectionRabid;
    private boolean isDatePutOrExp;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dao = new InjectionRabidDao(this);
        injectionRabid = dao.findById(DataPositionListener.getInstance().getSelectedItemId());
        setContentView(R.layout.healthdata_rabies_vaxine_add_edit);
        initialize();
        onSavedReload(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initLocalViews() {
        save = findViewById(R.id.rabiesVaxine_save_btn);
        back = findViewById(R.id.rabiesVaxine_back_btn);
        delete = findViewById(R.id.rabiesVaxine_delete_btn);
        medicineET = findViewById(R.id.rabiesVaxine_name_textData);
        descET = findViewById(R.id.rabiesVaxine_description_textData);
        dateET = findViewById(R.id.rabiesVaxine_date_textData);
        nextDateET = findViewById(R.id.rabiesVaxine_next_date_textData);
        noteET = findViewById(R.id.rabiesVaxine_note_textData);
        photoStampIV = findViewById(R.id.rabiesVaxine_photo);
    }

    @Override
    protected void fillAllViews() {
        medicineET.setText(injectionRabid.getMedicine());
        descET.setText(injectionRabid.getDescription());
        if (injectionRabid.getTreatmentDate() != null)
            dateET.setText(dateFormat.format(injectionRabid.getTreatmentDate()));
        else nextDateET.setText("");
        if (injectionRabid.getNextTreatment() != null)
            nextDateET.setText(dateFormat.format(injectionRabid.getNextTreatment()));
        else nextDateET.setText("");
        noteET.setText(injectionRabid.getNote());
        photoPath = injectionRabid.getPhoto();
        if (Validate.notEmpty(photoPath)) {
            setImage(photoStampIV, photoPath, this, getResources());

        }
    }

    @Override
    protected void onSavedReload(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            String name = savedInstanceState.getString("NAME");
            if (name != null) medicineET.setText(name);
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
        outState.putString("NAME", medicineET.getText().toString());
        outState.putString("DESC", descET.getText().toString());
        outState.putString("DATE", dateET.getText().toString());
        outState.putString("NEXT_DATE", nextDateET.getText().toString());
        outState.putString("NOTE", noteET.getText().toString());
        outState.putString("PHOTO_PATH", photoPath);
        super.onSaveInstanceState(outState);
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
                injectionRabid.setMedicine(medicineET.getText().toString());
                injectionRabid.setDescription(descET.getText().toString());
                try {
                    injectionRabid.setTreatmentDate(dateFormat.parse(dateET.getText().toString()));
                } catch (ParseException e) {
                    injectionRabid.setTreatmentDate(null);
                }
                try {
                    injectionRabid.setNextTreatment(dateFormat.parse(nextDateET.getText().toString()));
                } catch (ParseException e) {
                    injectionRabid.setNextTreatment(null);
                }
                injectionRabid.setNote(noteET.getText().toString());
                injectionRabid.setPhoto(photoPath);
                dao.update(injectionRabid);
                finish();
            }else{
                Toast.makeText(this, WARNING_FIELD, Toast.LENGTH_SHORT).show();
            }
        };
    }


    @Override
    protected boolean validation() {
        boolean b1 = checkDateFormat(dateET);
        boolean b2 = checkDateFormat(nextDateET);
        boolean b3 = checkIsNotEmpty(medicineET);
        return b1 && b2 && b3;
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
