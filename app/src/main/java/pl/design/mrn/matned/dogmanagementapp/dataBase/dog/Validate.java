package pl.design.mrn.matned.dogmanagementapp.dataBase.dog;

import android.annotation.SuppressLint;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.dataBase.Sex;

import static pl.design.mrn.matned.dogmanagementapp.Statics.DATE_FORMAT;

public class Validate {

    public static boolean notEmpty(String data){
        if(data != null)
            return data.trim().length() > 0;
        return false;
    }

    public static boolean selectedSexIn(Spinner spinner){
        return spinner.getSelectedItemPosition() == 1 || spinner.getSelectedItemPosition() == 2;
    }

    public static boolean isNumeric(String data){
        try{
            int temp = Integer.parseInt(data.trim().replace(" ",""));
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public static boolean dateFormat(String date){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat f = new SimpleDateFormat(DATE_FORMAT);
        try{
            Date temp = f.parse(date);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public static boolean noSignsInText(String data){
        return data.trim().matches("^[a-zA-Z0-9\\s]*$");
    }

    public static boolean checkText(String txt){
        return notEmpty(txt) && noSignsInText(txt);
    }

    public static boolean checkET(TextView et) {
        if (!Validate.notEmpty(et.getText().toString())) {
            et.setBackgroundResource(R.drawable.roundcornerstextred);
            return false;
        } else {
            et.setBackgroundResource(R.drawable.roundcornerstext);
            return true;
        }
    }

}
