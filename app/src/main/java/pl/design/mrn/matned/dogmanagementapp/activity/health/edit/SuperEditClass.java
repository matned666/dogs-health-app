package pl.design.mrn.matned.dogmanagementapp.activity.health.edit;

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
        initPhotoClick();
    }



}
