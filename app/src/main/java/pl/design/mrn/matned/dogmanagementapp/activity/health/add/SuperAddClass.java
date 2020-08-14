package pl.design.mrn.matned.dogmanagementapp.activity.health.add;

import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;

import pl.design.mrn.matned.dogmanagementapp.activity.health.SuperHealthClass;
import pl.design.mrn.matned.dogmanagementapp.dataBase.DaoFragmentInterface;

public abstract class SuperAddClass extends SuperHealthClass {

    protected abstract View.OnClickListener addClickListener();
    protected abstract void removeDeleteButton();

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void initialize(DaoFragmentInterface dao) {
        initSuper();
        initLocalViews();
        initDatePicker();
        setOtherOnClickListeners();
        removeDeleteButton();
        initPhotoClick();
    }

}
