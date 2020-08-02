package pl.design.mrn.matned.dogmanagementapp.activity.dataactivity.edit;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import pl.design.mrn.matned.dogmanagementapp.DatePicker.*;

import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.activity.Add_DogActivity;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.Validate;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.Tattoo;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.TattooDao;
import pl.design.mrn.matned.dogmanagementapp.listeners.DataPositionListener;

import static pl.design.mrn.matned.dogmanagementapp.DatePicker.initializeDatePicker;
import static pl.design.mrn.matned.dogmanagementapp.Statics.DATE_FORMAT;

public class TattooActivityEdit extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private Button ok;
    private Button cancel;
    private Button delete;
    private TextView putDateTV;
    private TextView descriptionTV;

    private TattooDao dao;
    private Tattoo tattoo;

    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tattoo_dialog);
        initViews();
        fillAllFields();
    }

    private void initViews() {
        ok = findViewById(R.id.okTattooDialog);
        cancel = findViewById(R.id.cancelTattooDialog);
        delete = findViewById(R.id.deleteTattooDialog);
        putDateTV = findViewById(R.id.tattooPutDate);
        descriptionTV = findViewById(R.id.tattooDescription);
        initializeDatePicker(putDateTV, this, this);
        dao = new TattooDao(this);
        tattoo = dao.findById(DataPositionListener.getInstance().getSelectedItemId());
        initEndingListeners();
    }

    private void fillAllFields() {
        putDateTV.setText(dateFormat.format(tattoo.getTattooDate()));
        descriptionTV.setText(tattoo.getDescription());
    }


    private void initEndingListeners() {
        ok.setOnClickListener(v -> {
            if(validation()){
                try {
                    tattoo.setTattooDate(dateFormat.parse(putDateTV.getText().toString()));
                } catch (ParseException ignored) {
                }
                tattoo.setDescription(descriptionTV.getText().toString());
                finish();
            }
        });
        cancel.setOnClickListener(v -> finish());
        delete.setOnClickListener(v -> {
            dao.remove(tattoo);
            finish();
        });
    }

    private boolean validation() {
        boolean b1 = checkET(descriptionTV);
        boolean b2 = checkDate(putDateTV);
        return b1 && b2;
    }

    private boolean checkDate(TextView et) {
        if (!Validate.dateFormat(et.getText().toString())) {
            et.setBackgroundResource(R.drawable.roundcornerstextred);
            return false;
        } else {
            et.setBackgroundResource(R.drawable.roundcornerstext);
            return true;
        }
    }


    private boolean checkET(TextView et) {
        if (!Validate.notEmpty(et.getText().toString())) {
            et.setBackgroundResource(R.drawable.roundcornerstextred);
            return false;
        } else {
            et.setBackgroundResource(R.drawable.roundcornerstext);
            return true;
        }
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar cal = new GregorianCalendar(year, month, dayOfMonth);
        dateFormat.setTimeZone(cal.getTimeZone());
        putDateTV.setText(dateFormat.format(cal.getTime()));
    }


}
