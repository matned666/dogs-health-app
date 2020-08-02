package pl.design.mrn.matned.dogmanagementapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.InputType;
import android.widget.TextView;

import java.util.Calendar;

public class DatePicker {

    public static void initializeDatePicker(TextView tv, Context context, DatePickerDialog.OnDateSetListener listener) {
        tv.setOnClickListener(v -> datePickDialog(context, listener));
        tv.setRawInputType(InputType.TYPE_NULL);
        tv.setOnFocusChangeListener((v, hasFocus) -> datePickDialog(context, listener));
    }

    private static void datePickDialog(Context context, DatePickerDialog.OnDateSetListener listener) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(context, listener, year, month, day);
        datePicker.show();
    }
}
