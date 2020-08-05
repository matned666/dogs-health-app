package pl.design.mrn.matned.dogmanagementapp.dataBase.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import pl.design.mrn.matned.dogmanagementapp.dataBase.DaoInterface;

import static pl.design.mrn.matned.dogmanagementapp.Statics.DATABASE_NAME;
import static pl.design.mrn.matned.dogmanagementapp.Statics.DATE_FORMAT;

public class MessagesDao extends SQLiteOpenHelper implements DaoInterface<Messages> {

    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    public MessagesDao(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public boolean add(Messages messages) {
        return false;
    }

    @Override
    public List<Messages> findAll() {
        return null;
    }

    @Override
    public Messages findById(int id) {
        return null;
    }

    @Override
    public boolean remove(Messages messages) {
        return false;
    }

    @Override
    public boolean removeAll() {
        return false;
    }

    @Override
    public boolean update(Messages updated_T_Data) {
        return false;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
