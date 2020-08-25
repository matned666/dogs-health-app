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

import java.text.ParseException;
import java.util.Calendar;

import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.Validate;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.TeethControl;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.TeethControlDao;
import pl.design.mrn.matned.dogmanagementapp.listeners.DataPositionListener;

import static pl.design.mrn.matned.dogmanagementapp.ImageAdvancedFunction.setImage;
import static pl.design.mrn.matned.dogmanagementapp.TextStrings_PL.WARNING_FIELD;
import static pl.design.mrn.matned.dogmanagementapp.TextStrings_PL.WRONG_DATE;

public class TeethControlActivityEdit extends SuperEditClass{

    private Button save;
    private Button back;
    private Button delete;
    private EditText descET;
    private EditText dateET;
    private EditText nextDateET;
    private EditText noteET;

    private TeethControlDao dao;
    private TeethControl teethControl;
    private boolean isDatePutOrExp;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dao = new TeethControlDao(this);
        teethControl = dao.findById(DataPositionListener.getInstance().getSelectedItemId());
        setContentView(R.layout.healthdata_teeth_control_add_edit);
        initialize();
        onSavedReload(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initLocalViews() {
        save = findViewById(R.id.teeth_save_btn);
        back = findViewById(R.id.teeth_back_btn);
        delete = findViewById(R.id.teeth_delete_btn);
        descET = findViewById(R.id.teeth_description_dataText);
        dateET = findViewById(R.id.teeth_date_dataText);
        nextDateET = findViewById(R.id.teeth_next_date_dataText);
        noteET = findViewById(R.id.teeth_note_dataText);
        photoStampIV = findViewById(R.id.teeth_photo);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void setOtherOnClickListeners() {
        save.setOnClickListener(updateClickListener());
        back.setOnClickListener(v -> finish());
    }

    @Override
    protected void fillAllViews() {
        descET.setText(teethControl.getDescription());
        if (teethControl.getDateOfControl() != null)
            dateET.setText(dateFormat.format(teethControl.getDateOfControl()));
        else dateET.setText("");
        if (teethControl.getDateOfNextControl() != null)
            nextDateET.setText(dateFormat.format(teethControl.getDateOfNextControl()));
        else nextDateET.setText("");
        noteET.setText(teethControl.getNote());
        photoPath = teethControl.getPhoto();
        if (Validate.notEmpty(photoPath)) showImage();
    }

    @Override
    protected void onSavedReload(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            String description = savedInstanceState.getString("DESC");
            if (description != null) descET.setText(description);
            String date = savedInstanceState.getString("DATE");
            if (date != null) dateET.setText(date);
            String nextDate = savedInstanceState.getString("NEXT_DATE");
            if (nextDate != null) nextDateET.setText(nextDate);
            String note = savedInstanceState.getString("NOTE");
            if (note != null) noteET.setText(note);
            String photoPath = savedInstanceState.getString("PATH");
            if (Validate.notEmpty(photoPath))
                setImage(photoStampIV, photoPath, this, getResources());

            }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("DESC", descET.getText().toString());
        outState.putString("DATE", dateET.getText().toString());
        outState.putString("NEXT_DATE", nextDateET.getText().toString());
        outState.putString("NOTE", noteET.getText().toString());
        outState.putString("PHOTO_PATH", photoPath);
        super.onSaveInstanceState(outState);
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
                teethControl.setDescription(descET.getText().toString());
                try {
                    teethControl.setDateOfControl(dateFormat.parse(dateET.getText().toString()));
                } catch (ParseException e) {
                    Toast.makeText(this, WRONG_DATE, Toast.LENGTH_SHORT).show();
                }
                try {
                    teethControl.setDateOfNextControl(dateFormat.parse(nextDateET.getText().toString()));
                } catch (ParseException e) {
                    Toast.makeText(this, WRONG_DATE, Toast.LENGTH_SHORT).show();
                }
                teethControl.setNote(noteET.getText().toString());
                teethControl.setPhoto(photoPath);
                dao.update(teethControl);
                finish();
            }else{
                Toast.makeText(this, WARNING_FIELD, Toast.LENGTH_SHORT).show();
            }
        };
    }


    @Override
    protected boolean validation() {
        return checkDateFormat(dateET);
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
