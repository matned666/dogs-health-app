package pl.design.mrn.matned.dogmanagementapp.dataBase.health;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.List;

import pl.design.mrn.matned.dogmanagementapp.dataBase.DaoFragmentInterface;

import static pl.design.mrn.matned.dogmanagementapp.Statics.DATABASE_NAME;

public class InjectionOtherDao extends SQLiteOpenHelper implements DaoFragmentInterface<InjectionOther> {
    public InjectionOtherDao(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public List<InjectionOther> getListByMasterId(int id) {
        return null;
    }

    @Override
    public boolean remove(int id) {
        return false;
    }

    @Override
    public boolean add(InjectionOther injectionOther) {
        return false;
    }

    @Override
    public List<InjectionOther> findAll() {
        return null;
    }

    @Override
    public InjectionOther findById(int id) {
        return null;
    }

    @Override
    public boolean remove(InjectionOther injectionOther) {
        return false;
    }

    @Override
    public boolean removeAll() {
        return false;
    }

    @Override
    public boolean update(InjectionOther updated_T_Data) {
        return false;
    }
}
