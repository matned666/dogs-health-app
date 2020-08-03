package pl.design.mrn.matned.dogmanagementapp.activity.dataactivity.add;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
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

import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.Validate;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.Tattoo;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.TattooDao;
import pl.design.mrn.matned.dogmanagementapp.listeners.PositionListener;

import static pl.design.mrn.matned.dogmanagementapp.Statics.DATE_FORMAT;

public class TattooActivityAdd extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private Button ok;
    private Button cancel;
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
    }

    private void initViews() {
        ok = findViewById(R.id.okTattooDialog);
        cancel = findViewById(R.id.cancelTattooDialog);
        Button delete = findViewById(R.id.deleteTattooDialog);
        delete.setVisibility(View.GONE);
        putDateTV = findViewById(R.id.tattooPutDate);
        descriptionTV = findViewById(R.id.tattooDescription);
        initializeDatePicker();
        dao = new TattooDao(this);
        initEndingListeners();
    }


    private void initEndingListeners() {
        ok.setOnClickListener(v -> {
            if(validation()){
                tattoo = new Tattoo();
                tattoo.setDogId(PositionListener.getInstance().getSelectedDogId());
                try {
                    tattoo.setTattooDate(dateFormat.parse(putDateTV.getText().toString()));
                } catch (ParseException ignored) {
                }
                tattoo.setDescription(descriptionTV.getText().toString());
                dao.add(tattoo);
            }
        });
        cancel.setOnClickListener(v -> finish());
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

    public void initializeDatePicker() {
        putDateTV.setOnClickListener(v -> datePickDialog());
        putDateTV.setRawInputType(InputType.TYPE_NULL);
        putDateTV.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) datePickDialog();
        });
    }

    private void datePickDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(this, this, year, month, day);
        datePicker.show();
    }



}
