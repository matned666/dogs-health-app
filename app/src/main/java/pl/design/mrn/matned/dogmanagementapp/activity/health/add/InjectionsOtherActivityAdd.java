package pl.design.mrn.matned.dogmanagementapp.activity.health.add;

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
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.InjectionOther;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.InjectionOtherDao;
import pl.design.mrn.matned.dogmanagementapp.listeners.PositionListener;

import static pl.design.mrn.matned.dogmanagementapp.TextStrings_PL.WARNING_FIELD;
import static pl.design.mrn.matned.dogmanagementapp.TextStrings_PL.WRONG_DATE;

public class InjectionsOtherActivityAdd extends SuperAddClass{

    private Button save;
    private Button delete;
    private Button back;
    private EditText medicineET;
    private EditText descET;
    private EditText dateET;
    private EditText nextDateET;
    private EditText noteET;

    private InjectionOtherDao dao;
    private InjectionOther injectionOther;
    private boolean isDatePutOrExp;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dao = new InjectionOtherDao(this);
        setContentView(R.layout.healthdata_other_deseases_vaxine_add_edit);
        initialize(dao);
        onSavedReload(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initLocalViews() {
        save = findViewById(R.id.otherVaxine_save_btn);
        back = findViewById(R.id.otherVaxine_back_btn);
        delete = findViewById(R.id.otherVaxine_delete_btn);
        medicineET = findViewById(R.id.otherVaxine_name_textData);
        descET = findViewById(R.id.otherVaxine_description_textData);
        dateET = findViewById(R.id.otherVaxine_date_textData);
        nextDateET = findViewById(R.id.otherVaxine_next_date_textData);
        noteET = findViewById(R.id.otherVaxine_note_textData);
        photoStampIV = findViewById(R.id.otherVaxine_notephoto);
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
        save.setOnClickListener(addClickListener());
        back.setOnClickListener(v -> finish());
    }

    @Override
    protected View.OnClickListener addClickListener() {
        return v -> {
            if(validation()) {
                injectionOther = new InjectionOther();
                injectionOther.setMedicine(medicineET.getText().toString());
                injectionOther.setDescription(descET.getText().toString());
                try {
                    injectionOther.setTreatmentDate(dateFormat.parse(dateET.getText().toString()));
                } catch (ParseException e) {
                    Toast.makeText(this, WRONG_DATE, Toast.LENGTH_SHORT).show();
                }
                try {
                    injectionOther.setNextTreatment(dateFormat.parse(nextDateET.getText().toString()));
                } catch (ParseException e) {
                    Toast.makeText(this, WRONG_DATE, Toast.LENGTH_SHORT).show();
                }
                injectionOther.setNote(noteET.getText().toString());
                injectionOther.setPhoto(photoPath);
                injectionOther.setDogId(PositionListener.getInstance().getSelectedDogId());
                dao.add(injectionOther);
                finish();
            }else{
                Toast.makeText(this, WARNING_FIELD, Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    protected void removeDeleteButton() {
        delete.setVisibility(View.GONE);
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
