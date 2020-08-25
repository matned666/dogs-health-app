package pl.design.mrn.matned.dogmanagementapp.activity.dataactivity.edit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
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

import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.Validate;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.Chip;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.ChipDao;
import pl.design.mrn.matned.dogmanagementapp.listeners.DataPositionListener;

import static pl.design.mrn.matned.dogmanagementapp.Statics.DATE_FORMAT;

public class ChipActivityEdit extends SuperEditDataClass implements DatePickerDialog.OnDateSetListener{

    private Button ok;
    private Button cancel;
    private Button delete;
    private TextView chipNumber;
    private TextView chipingDate;
    private TextView chipExpDate;
    private TextView chipDescription;

    private Chip chip;
    private ChipDao dao;

    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    private boolean isDatePutOrExp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chip_dialog);
        init();
        fillAllFields();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == Activity.RESULT_OK) {
                finish();
            }
        }
    }

    private void init() {
        initViews();
        initEndingListeners();

    }

    private void initViews() {
        dao = new ChipDao(this);
        chip = dao.findById(DataPositionListener.getInstance().getSelectedItemId());
        ok = findViewById(R.id.okChipDialog);
        cancel = findViewById(R.id.cancelChipDialog);
        delete = findViewById(R.id.deleteChipDialogBtn);
        chipNumber = findViewById(R.id.chipNumber);
        chipingDate = findViewById(R.id.chipPutDate);
        chipExpDate = findViewById(R.id.chipExpiryDate);
        chipDescription = findViewById(R.id.chipDescription);
        isDatePutOrExp = true;
        initializeDatePicker();
    }

    private void initEndingListeners() {
        ok.setOnClickListener(v -> {
            if (validation()) {
                chip.setChipNumber(chipNumber.getText().toString());
                chip.setChipDescription(chipDescription.getText().toString());
                try {
                    chip.setPutDate(dateFormat.parse(chipingDate.getText().toString()));
                } catch (ParseException ignored) {
                }
                try {
                    chip.setExpDate(dateFormat.parse(chipExpDate.getText().toString()));
                } catch (ParseException ignored) {
                }
                dao.update(chip);
                finish();
            }
        });
        delete.setOnClickListener(v -> showAlertButton());
        cancel.setOnClickListener(v -> finish());
    }

    private void fillAllFields() {
        if (chip.getChipNumber() != null)
            chipNumber.setText(chip.getChipNumber());
        if (chip.getPutDate() != null)
            chipingDate.setText(dateFormat.format(chip.getPutDate()));
        if (chip.getExpDate() != null)
            chipExpDate.setText(dateFormat.format(chip.getExpDate()));
        if (chip.getChipDescription() != null)
            chipDescription.setText(chip.getChipDescription());
    }

    private boolean validation() {
        return checkET(chipNumber);
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
        if (isDatePutOrExp) chipingDate.setText(dateFormat.format(cal.getTime()));
        else chipExpDate.setText(dateFormat.format(cal.getTime()));
    }

    public void initializeDatePicker() {
        chipingDate.setOnClickListener(v -> {
            isDatePutOrExp = true;
            datePickDialog();
        });
        chipingDate.setRawInputType(InputType.TYPE_NULL);
        chipingDate.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                isDatePutOrExp = true;
                datePickDialog();
            }
        });
        chipExpDate.setOnClickListener(v -> {
            isDatePutOrExp = false;
            datePickDialog();
        });
        chipExpDate.setRawInputType(InputType.TYPE_NULL);
        chipExpDate.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                isDatePutOrExp = false;
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
        dao.remove(chip);
        finish();
    }
}
