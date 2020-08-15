package pl.design.mrn.matned.dogmanagementapp.activity.dataactivity.edit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.Validate;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.Owner;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.OwnerDao;
import pl.design.mrn.matned.dogmanagementapp.listeners.DataPositionListener;
import pl.design.mrn.matned.dogmanagementapp.listeners.PositionListener;

import static pl.design.mrn.matned.dogmanagementapp.Statics.DATA_SPLITMENT_REGEX;
import static pl.design.mrn.matned.dogmanagementapp.Statics.DATE_FORMAT;

public class OwnerActivityEdit extends SuperEditDataClass implements DatePickerDialog.OnDateSetListener {

    private Button ok;
    private Button cancel;
    private Button delete;
    private TextView ownerNameTV;
    private TextView ownerSurnameTV;
    private TextView ownerAddressTV;
    private TextView ownerPhoneTV;
    private TextView ownerEmailTV;
    private TextView ownerFromDateTV;
    private TextView ownerToDateTV;
    private TextView ownerDescriptionTV;
    private OwnerDao dao;
    private Owner owner;
    private Spinner ownersSpinner;
    private boolean isDateFromOrTo;

    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    private List<Owner> owners;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_dialog);
        initViews();
        fillAllFields();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initViews() {
        isDateFromOrTo = true;
        ok = findViewById(R.id.okOwnerDialog);
        cancel = findViewById(R.id.cancelOwnerDialog);
        delete = findViewById(R.id.deleteOwnerDialog);
        ownerNameTV = findViewById(R.id.ownerName);
        ownerSurnameTV = findViewById(R.id.ownerSurname);
        ownerAddressTV = findViewById(R.id.ownerAddress);
        ownerPhoneTV = findViewById(R.id.ownerPhone);
        ownerEmailTV = findViewById(R.id.ownerEmail);
        ownerFromDateTV = findViewById(R.id.ownerFromDate);
        ownerToDateTV = findViewById(R.id.ownerToDate);
        ownerDescriptionTV = findViewById(R.id.ownerDescription);
        ownersSpinner = findViewById(R.id.ownerDataSpinner);
        dao = new OwnerDao(this);
        owner = dao.findById(DataPositionListener.getInstance().getSelectedItemId());
        initEndingListeners();
        initSpinner();
    }

    private void fillAllFields() {
        if (owner.getName() != null) ownerNameTV.setText(owner.getName());
        if (owner.getSurname() != null) ownerSurnameTV.setText(owner.getSurname());
        if (owner.getAddress() != null) ownerAddressTV.setText(owner.getAddress());
        if (owner.getPhoneNumber() != null) ownerPhoneTV.setText(owner.getPhoneNumber());
        if (owner.getEmail() != null) ownerEmailTV.setText(owner.getEmail());
        if (owner.getDateFrom() != null) ownerFromDateTV.setText(dateFormat.format(owner.getDateFrom()));
        if (owner.getDateTo() != null) ownerToDateTV.setText(dateFormat.format(owner.getDateTo()));
        if (owner.getDescription() != null) ownerDescriptionTV.setText(owner.getDescription());
    }


    private void initEndingListeners() {
        ok.setOnClickListener(v -> {
            if (validation()){
                owner.setName(ownerNameTV.getText().toString());
                owner.setSurname(ownerSurnameTV.getText().toString());
                owner.setAddress(ownerAddressTV.getText().toString());
                owner.setPhoneNumber(ownerPhoneTV.getText().toString());
                owner.setEmail(ownerEmailTV.getText().toString());
                try {
                    owner.setDateFrom(dateFormat.parse(ownerFromDateTV.getText().toString()));
                } catch (ParseException ignored) {
                }
                try {
                    owner.setDateTo(dateFormat.parse(ownerToDateTV.getText().toString()));
                } catch (ParseException ignored) {
                }
                owner.setDescription(ownerDescriptionTV.getText().toString());
                dao.update(owner);
                finish();
            }
        });
        cancel.setOnClickListener(v -> finish());
        delete.setOnClickListener(v -> showAlertButton());
        initializeDatePicker();
    }

    private boolean validation() {
        checkET(ownerNameTV);
        checkET(ownerSurnameTV);
        return checkET(ownerNameTV) && checkET(ownerSurnameTV);
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initSpinner() {
        owners = dao.findAll();
        String[] array = getOwnersDesc(owners);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.drpdn, array);
        ownersSpinner.setAdapter(adapter);
        ownersSpinner.setOnItemSelectedListener (clickItem());
    }

    private AdapterView.OnItemSelectedListener clickItem() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int ownerId = owner.getId();
                owner = owners.get(position);
                owner.setId(ownerId);
                owner.setDog_id(PositionListener.getInstance().getSelectedDogId());
                fillAllFields();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }

    private String[] getOwnersDesc(List<Owner> owners) {
        String[] array = new String[owners.size()];
        for (int i = 0; i < array.length; i++) {
            String data = owners.get(i).getId() + " " + DATA_SPLITMENT_REGEX + " " + owners.get(i).getName() + " " + owners.get(i).getSurname();
            array[i] = data;
        }
        return array;
    }

    private int getTheSelectedOwnerId(String str) {
        String[] temp = str.split(DATA_SPLITMENT_REGEX);
        return Integer.parseInt(temp[0].trim());
    }



    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar cal = new GregorianCalendar(year, month, dayOfMonth);
        dateFormat.setTimeZone(cal.getTimeZone());
        if (isDateFromOrTo) ownerFromDateTV.setText(dateFormat.format(cal.getTime()));
        else ownerToDateTV.setText(dateFormat.format(cal.getTime()));
    }

    public void initializeDatePicker() {
        ownerFromDateTV.setOnClickListener(v -> {
            isDateFromOrTo = true;
            datePickDialog();
        });
        ownerFromDateTV.setRawInputType(InputType.TYPE_NULL);
        ownerFromDateTV.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                isDateFromOrTo = true;
                datePickDialog();
            }
        });
        ownerToDateTV.setOnClickListener(v -> {
            isDateFromOrTo = false;
            datePickDialog();
        });
        ownerToDateTV.setRawInputType(InputType.TYPE_NULL);
        ownerToDateTV.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                isDateFromOrTo = false;
                datePickDialog();
            }
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

    @Override
    protected void deleteRecord() {
        dao.remove(owner);
        finish();
    }
}
