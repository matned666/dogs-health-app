package pl.design.mrn.matned.dogmanagementapp.activity.health.edit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;

import pl.design.mrn.matned.dogmanagementapp.activity.health.SuperHealthClass;
import pl.design.mrn.matned.dogmanagementapp.dataBase.DaoFragmentInterface;
import pl.design.mrn.matned.dogmanagementapp.listeners.DataPositionListener;

public abstract class SuperEditClass extends SuperHealthClass {

    protected abstract View.OnClickListener updateClickListener();
    protected abstract void fillAllViews();
    protected abstract void initDeleteOnClickListener();
    protected abstract void deleteRecord();

    protected void deleteRecord(DaoFragmentInterface dao) {
        dao.remove(DataPositionListener.getInstance().getSelectedItemId());
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void initialize() {
        initSuper();
        initLocalViews();
        fillAllViews();
        initDatePicker();
        setOtherOnClickListeners();
        initDeleteOnClickListener();
//        initPhotoClick();
    }

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
