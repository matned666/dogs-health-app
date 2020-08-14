package pl.design.mrn.matned.dogmanagementapp.activity.health.edit;

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
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.Validate;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.InjectionOther;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.InjectionOtherDao;
import pl.design.mrn.matned.dogmanagementapp.listeners.DataPositionListener;

import static pl.design.mrn.matned.dogmanagementapp.TextStrings.WARNING_FIELD;
import static pl.design.mrn.matned.dogmanagementapp.TextStrings.WRONG_DATE;

public class InjectionsOtherActivityEdit extends SuperEditClass{

    private Button save;
    private Button back;
    private Button delete;
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
        injectionOther = dao.findById(DataPositionListener.getInstance().getSelectedItemId());
        setContentView(R.layout.healthdata_other_deseases_vaxine_add_edit);
        initSuper();
        initLocalViews();
        fillAllViews();
        initDatePicker();
        setOtherOnClickListeners();
        //       TODO  onSavedReload(savedInstanceState);
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
    protected void fillAllViews(){
        medicineET.setText(injectionOther.getMedicine());
        descET.setText(injectionOther.getDescription());
        if (injectionOther.getTreatmentDate() != null)
            dateET.setText(dateFormat.format(injectionOther.getTreatmentDate()));
        else dateET.setText("");
        if (injectionOther.getNextTreatment() != null)
            nextDateET.setText(dateFormat.format(injectionOther.getNextTreatment()));
        else dateET.setText("");
        noteET.setText(injectionOther.getNote());
        photoPath = injectionOther.getPhoto();
        if (Validate.notEmpty(photoPath)) showImage();
    }

    @Override
    protected void initDeleteOnClickListener() {
        delete.setOnClickListener(v-> deleteRecord(dao));
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
                dao.update(injectionOther);
                finish();
            }else{
                Toast.makeText(this, WARNING_FIELD, Toast.LENGTH_SHORT).show();
            }
        };    }

    @Override
    protected void changeDateViews(Calendar cal) {
        if (isDatePutOrExp) dateET.setText(dateFormat.format(cal.getTime()));
        else nextDateET.setText(dateFormat.format(cal.getTime()));
    }

    @Override
    protected boolean validation() {
        boolean b1 = checkDateFormat(dateET);
        boolean b2 = checkDateFormat(nextDateET);
        boolean b3 = checkIsNotEmpty(medicineET);
        return b1 && b2 && b3;
    }






}
