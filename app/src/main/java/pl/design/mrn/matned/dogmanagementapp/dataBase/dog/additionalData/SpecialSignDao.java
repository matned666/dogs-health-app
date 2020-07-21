package pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.List;

import pl.design.mrn.matned.dogmanagementapp.dataBase.DaoInterface;

public class SpecialSignDao extends SQLiteOpenHelper implements DaoInterface<Breeding> {

    public SpecialSignDao(@Nullable Context context) {
        super(context, "dogs_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public boolean add(Breeding breeding) {
        return false;
    }

    @Override
    public List<Breeding> findAll() {
        return null;
    }

    @Override
    public boolean remove(Breeding breeding) {
        return false;
    }

    @Override
    public Breeding findById(int id) {
        return null;
    }

    @Override
    public boolean update(int id_toUpdate, Breeding updated_T_Data) {
        return false;
    }
}
