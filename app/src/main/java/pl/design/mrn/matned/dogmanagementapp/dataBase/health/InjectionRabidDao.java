package pl.design.mrn.matned.dogmanagementapp.dataBase.health;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.List;

import pl.design.mrn.matned.dogmanagementapp.dataBase.DaoFragmentInterface;

import static pl.design.mrn.matned.dogmanagementapp.Statics.DATABASE_NAME;

public class InjectionRabidDao extends SQLiteOpenHelper implements DaoFragmentInterface<InjectionRabid> {
    public InjectionRabidDao(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public List<InjectionRabid> getListByMasterId(int id) {
        return null;
    }

    @Override
    public boolean remove(int id) {
        return false;
    }

    @Override
    public boolean add(InjectionRabid injectionRabid) {
        return false;
    }

    @Override
    public List<InjectionRabid> findAll() {
        return null;
    }

    @Override
    public InjectionRabid findById(int id) {
        return null;
    }

    @Override
    public boolean remove(InjectionRabid injectionRabid) {
        return false;
    }

    @Override
    public boolean removeAll() {
        return false;
    }

    @Override
    public boolean update(InjectionRabid updated_T_Data) {
        return false;
    }
}
