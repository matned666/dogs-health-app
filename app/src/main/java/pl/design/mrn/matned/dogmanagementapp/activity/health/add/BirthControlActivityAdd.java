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
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.BirthControl;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.BirthControlDao;
import pl.design.mrn.matned.dogmanagementapp.listeners.PositionListener;

import static pl.design.mrn.matned.dogmanagementapp.TextStrings.WARNING_FIELD;
import static pl.design.mrn.matned.dogmanagementapp.TextStrings.WRONG_DATE;

public class BirthControlActivityAdd extends SuperAddClass{

    private Button save;
    private Button back;
    private Button delete;
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
        setContentView(R.layout.healthdata_birth_control_add_edit);
        initialize(dao);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initLocalViews() {
        save = findViewById(R.id.birth_save_btn);
        back = findViewById(R.id.birth_back_button);
        delete = findViewById(R.id.birth_delete_button);
        numberOfPupsET = findViewById(R.id.birth_number_of_pups_dataText);
        descET = findViewById(R.id.birth_description_dataText);
        dateET = findViewById(R.id.birth_date_dataText);
        noteET = findViewById(R.id.birth_note_dataText);
        photoStampIV = findViewById(R.id.birth_photo);
    }

    @Override
    protected void removeDeleteButton() {
        delete.setVisibility(View.GONE);
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
                birthControl = new BirthControl();
                birthControl.setNumberOfChildren(Integer.parseInt(numberOfPupsET.getText().toString()));
                birthControl.setDescription(descET.getText().toString());
                try {
                    birthControl.setDateOfBirth(dateFormat.parse(dateET.getText().toString()));
                } catch (ParseException e) {
                    Toast.makeText(this, WRONG_DATE, Toast.LENGTH_SHORT).show();
                }
                birthControl.setNote(noteET.getText().toString());
                birthControl.setPhoto(photoPath);
                birthControl.setDogId(PositionListener.getInstance().getSelectedDogId());
                dao.add(birthControl);
                finish();
            }else{
                Toast.makeText(this, WARNING_FIELD, Toast.LENGTH_SHORT).show();
            }
        };
    }


    @Override
    protected boolean validation() {
        boolean b1 = checkIsNumeric(numberOfPupsET);
        boolean b2 = checkDateFormat(dateET);
        return b1 && b2;
    }

    @Override
    protected void changeDateViews(Calendar cal) {
        dateET.setText(dateFormat.format(cal.getTime()));
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
}
