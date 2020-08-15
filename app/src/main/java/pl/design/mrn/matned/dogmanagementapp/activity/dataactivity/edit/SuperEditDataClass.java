package pl.design.mrn.matned.dogmanagementapp.activity.dataactivity.edit;

import android.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;

public abstract class SuperEditDataClass extends AppCompatActivity {

    protected abstract void deleteRecord();

    protected void showAlertButton(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Czy na pewno skasować?");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Tak",
                (dialog, id) -> {
                    deleteRecord();
                    finish();
                });
        builder1.setNegativeButton(
                "Nie",
                (dialog, id) -> dialog.cancel());
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
