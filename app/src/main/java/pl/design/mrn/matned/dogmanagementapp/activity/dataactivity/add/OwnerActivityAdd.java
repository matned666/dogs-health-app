package pl.design.mrn.matned.dogmanagementapp.activity.dataactivity.add;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
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
import java.util.LinkedList;
import java.util.List;

import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.Validate;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.Owner;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.OwnerDao;
import pl.design.mrn.matned.dogmanagementapp.listeners.DataPositionListener;
import pl.design.mrn.matned.dogmanagementapp.listeners.PositionListener;

import static pl.design.mrn.matned.dogmanagementapp.Statics.DATA_SPLITMENT_REGEX;
import static pl.design.mrn.matned.dogmanagementapp.Statics.DATE_FORMAT;
import static pl.design.mrn.matned.dogmanagementapp.TextStrings.NEW_OWNER_TEXT;

public class OwnerActivityAdd extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

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

    private List<Owner> owners;

    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_dialog);
        initViews();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initViews() {
        isDateFromOrTo = true;
        ok = findViewById(R.id.okOwnerDialog);
        cancel = findViewById(R.id.cancelOwnerDialog);
        delete = findViewById(R.id.deleteOwnerDialog);
        delete.setVisibility(View.GONE);
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
        initEndingListeners();
        initSpinner();
    }

    private void fillAllFields() {
        if (owner.getName() != null) ownerNameTV.setText(owner.getName());
        else ownerNameTV.setText("");
        if (owner.getSurname() != null) ownerSurnameTV.setText(owner.getSurname());
        else ownerSurnameTV.setText("");
        if (owner.getAddress() != null) ownerAddressTV.setText(owner.getAddress());
        else ownerAddressTV.setText("");
        if (owner.getPhoneNumber() != null) ownerPhoneTV.setText(owner.getPhoneNumber());
        else ownerPhoneTV.setText("");
        if (owner.getEmail() != null) ownerEmailTV.setText(owner.getEmail());
        else ownerEmailTV.setText("");
        if (owner.getDateFrom() != null)
            ownerFromDateTV.setText(dateFormat.format(owner.getDateFrom()));
        else ownerFromDateTV.setText("");
        if (owner.getDateTo() != null) ownerToDateTV.setText(dateFormat.format(owner.getDateTo()));
        else ownerToDateTV.setText("");
        if (owner.getDescription() != null) ownerDescriptionTV.setText(owner.getDescription());
        else ownerDescriptionTV.setText("");
    }


    private void initEndingListeners() {
        ok.setOnClickListener(v -> {
            if (validation()) {
                owner = new Owner();
                owner.setName(ownerNameTV.getText().toString());
                owner.setSurname(ownerSurnameTV.getText().toString());
                owner.setAddress(ownerAddressTV.getText().toString());
                owner.setPhoneNumber(ownerPhoneTV.getText().toString());
                owner.setEmail(ownerEmailTV.getText().toString());
                try {
                    owner.setDateFrom(dateFormat.parse(ownerFromDateTV.getText().toString()));
                } catch (Exception ignored) {
                }
                try {
                    owner.setDateTo(dateFormat.parse(ownerToDateTV.getText().toString()));
                } catch (Exception ignored) {
                }
                owner.setDescription(ownerDescriptionTV.getText().toString());
                owner.setDog_id(PositionListener.getInstance().getSelectedDogId());
                dao.add(owner);
                finish();
            }
        });
        cancel.setOnClickListener(v -> finish());
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
        owners = new LinkedList<>();
        owners.add(new Owner());
        owners.addAll(dao.findAll());
        String[] array = getOwnersDesc(owners);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.drpdn, array);
        ownersSpinner.setAdapter(adapter);
        ownersSpinner.setOnItemSelectedListener(clickItem());
    }

    private AdapterView.OnItemSelectedListener clickItem() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                owner = owners.get(position);
                int ownerId = owner.getId();
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
            String data;
            if (owners.get(i).getName() != null && owners.get(i).getSurname() != null)
                data = owners.get(i).getId() + " " + DATA_SPLITMENT_REGEX + " " +
                        owners.get(i).getName() + " " + owners.get(i).getSurname();
            else data = NEW_OWNER_TEXT;
            array[i] = data;
        }
        return array;
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

}
