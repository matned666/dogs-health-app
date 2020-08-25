package pl.design.mrn.matned.dogmanagementapp.dataBase.health;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import pl.design.mrn.matned.dogmanagementapp.dataBase.DaoBase;
import pl.design.mrn.matned.dogmanagementapp.dataBase.DaoFragmentInterface;

import static pl.design.mrn.matned.dogmanagementapp.Statics.*;

public class InjectionOtherDao extends DaoBase implements DaoFragmentInterface<InjectionOther> {

    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

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
        String query = "SELECT * FROM " + OTHER_VACCINE_TABLE + " WHERE " + DOG_ID + " = " + id;
        return getListByQuery(query);
    }

    @Override
    public boolean remove(int id) {
        String query = "DELETE FROM " + OTHER_VACCINE_TABLE + " WHERE " + OTHER_VACCINE_ID + " = " + id;
        return getCursor(query);    }

    @Override
    public boolean add(InjectionOther injectionOther) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(OTHER_VACCINE_MEDICINE, injectionOther.getMedicine());
        cv.put(OTHER_VACCINE_DESCRIPTION, injectionOther.getDescription());
        if (injectionOther.getTreatmentDate() != null)
            cv.put(OTHER_VACCINE_DATE, dateFormat.format(injectionOther.getTreatmentDate()));
        if (injectionOther.getNextTreatment() != null)
            cv.put(OTHER_VACCINE_NEXT_DATE, dateFormat.format(injectionOther.getNextTreatment()));
        cv.put(OTHER_VACCINE_NOTE, injectionOther.getNote());
        cv.put(OTHER_VACCINE_STAMP_PHOTO, injectionOther.getPhoto());
        cv.put(DOG_ID, injectionOther.getDogId());
        long insert = db.insert(OTHER_VACCINE_TABLE, null, cv);
        return insert != -1;    }

    @Override
    public List<InjectionOther> findAll() {
        String query = "SELECT * FROM " + OTHER_VACCINE_TABLE;
        return getListByQuery(query);    }

    @Override
    public InjectionOther findById(int id) {
        String query = "SELECT * FROM " + OTHER_VACCINE_TABLE + " WHERE " + OTHER_VACCINE_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        InjectionOther injectionOther = null;
        if (cursor.moveToFirst()) {
            injectionOther = getInjection(cursor);
        }
        cursor.close();
        db.close();
        return injectionOther;    }

    @Override
    public boolean remove(InjectionOther injectionOther) {
        String query = "DELETE FROM " + OTHER_VACCINE_TABLE + " WHERE " + OTHER_VACCINE_ID + " = " + injectionOther.getId();
        return getCursor(query);    }

    @Override
    public boolean removeAll() {
        String query = "DELETE FROM " + OTHER_VACCINE_TABLE;
        return getCursor(query);    }

    @Override
    public boolean update(InjectionOther updated_T_Data) {
        String query = "" +
                "UPDATE " + OTHER_VACCINE_TABLE + " SET " +
                OTHER_VACCINE_MEDICINE + " = '" + updated_T_Data.getMedicine() + "', " +
                OTHER_VACCINE_DATE + " = '" + dateFormat.format(updated_T_Data.getTreatmentDate()) + "', " +
                OTHER_VACCINE_NEXT_DATE + " = '" + dateFormat.format(updated_T_Data.getNextTreatment()) + "'";
        if (updated_T_Data.getDescription() != null) query += ", " + OTHER_VACCINE_DESCRIPTION + " = '" + updated_T_Data.getDescription() + "'";
        if (updated_T_Data.getNote() != null) query += ", " + OTHER_VACCINE_NOTE + " = '" + updated_T_Data.getNote() + "'";
        if (updated_T_Data.getPhoto() != null) query += ", " + OTHER_VACCINE_STAMP_PHOTO + " = '" + updated_T_Data.getPhoto() + "'";
        query += ", " + DOG_ID + " = " + updated_T_Data.getDogId() + " " +
                "WHERE " +
                OTHER_VACCINE_ID + " = " + updated_T_Data.getId();
        return getCursor(query);     }

    private List<InjectionOther> getListByQuery(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<InjectionOther> list = new LinkedList<>();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                InjectionOther injectionOther = getInjection(cursor);
                list.add(injectionOther);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    private InjectionOther getInjection(Cursor cursor) {
        InjectionOther a = new InjectionOther();
        a.setId(cursor.getInt(0));
        a.setMedicine(cursor.getString(1));
        a.setDescription(cursor.getString(2));
        try {
            a.setTreatmentDate(dateFormat.parse(cursor.getString(3)));
        } catch (Exception ignored) {
        }
        try {
            a.setNextTreatment(dateFormat.parse(cursor.getString(4)));
        } catch (Exception ignored) {
        }
        a.setNote(cursor.getString(5));
        a.setPhoto(cursor.getString(6));
        a.setDogId(cursor.getInt(7));
        return a;    }

}
