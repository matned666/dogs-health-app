package pl.design.mrn.matned.dogmanagementapp.dataBase.health;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import pl.design.mrn.matned.dogmanagementapp.dataBase.DaoFragmentInterface;

import static pl.design.mrn.matned.dogmanagementapp.Statics.*;

public class InjectionRabidDao extends SQLiteOpenHelper implements DaoFragmentInterface<InjectionRabid> {

    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

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
    public boolean update(InjectionRabid updated_T_Data) {
        String query = "" +
                "UPDATE " + RABIES_VACCINE_TABLE + " SET " +
                RABIES_VACCINE_MEDICINE + " = '" + updated_T_Data.getMedicine() + "'";
        if (updated_T_Data.getDescription() != null) query += ", " + RABIES_VACCINE_DESCRIPTION + " = '" + updated_T_Data.getDescription() + "'";
        if (updated_T_Data.getTreatmentDate() != null) query += ", " + RABIES_VACCINE_DATE + " = '" + dateFormat.format(updated_T_Data.getTreatmentDate()) + "'";
        if (updated_T_Data.getNextTreatment() != null) query += ", " + RABIES_VACCINE_NEXT_DATE + " = '" + dateFormat.format(updated_T_Data.getNextTreatment()) + "'";
        if (updated_T_Data.getNote() != null) query += ", " + RABIES_VACCINE_NOTE + " = '" + updated_T_Data.getNote() + "'";
        if (updated_T_Data.getPhoto() != null) query += ", " + RABIES_VACCINE_STAMP_PHOTO + " = '" + updated_T_Data.getPhoto() + "'";
        query += ", " + DOG_ID + " = " + updated_T_Data.getDogId() + " " +
                "WHERE " +
                RABIES_VACCINE_ID + " = " + updated_T_Data.getId();
        return getCursor(query);    }

    @Override
    public boolean add(InjectionRabid injectionRabid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(RABIES_VACCINE_MEDICINE, injectionRabid.getMedicine());
        cv.put(RABIES_VACCINE_DESCRIPTION, injectionRabid.getDescription());
        if (injectionRabid.getTreatmentDate() != null)
            cv.put(RABIES_VACCINE_DATE, dateFormat.format(injectionRabid.getTreatmentDate()));
        if (injectionRabid.getNextTreatment() != null)
            cv.put(RABIES_VACCINE_NEXT_DATE, dateFormat.format(injectionRabid.getNextTreatment()));
        cv.put(RABIES_VACCINE_NOTE, injectionRabid.getNote());
        cv.put(RABIES_VACCINE_STAMP_PHOTO, injectionRabid.getPhoto());
        cv.put(DOG_ID, injectionRabid.getDogId());
        long insert = db.insert(RABIES_VACCINE_TABLE, null, cv);
        return insert != -1;
    }

    @Override
    public List<InjectionRabid> findAll() {
        String query = "SELECT * FROM " + RABIES_VACCINE_TABLE;
        return getListByQuery(query);    }

    @Override
    public InjectionRabid findById(int id) {
        String query = "SELECT * FROM " + RABIES_VACCINE_TABLE + " WHERE " + RABIES_VACCINE_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        InjectionRabid injectionRabid = null;
        if (cursor.moveToFirst()) {
            injectionRabid = getInjectionRabid(cursor);
        }
        cursor.close();
        db.close();
        return injectionRabid;
    }

    @Override
    public boolean remove(InjectionRabid injectionRabid) {
        String query = "DELETE FROM " + RABIES_VACCINE_TABLE + " WHERE " + RABIES_VACCINE_ID + " = " + injectionRabid.getId();
        return getCursor(query);
    }

    @Override
    public boolean removeAll() {
        String query = "DELETE FROM " + RABIES_VACCINE_TABLE;
        return getCursor(query);      }

    @Override
    public List<InjectionRabid> getListByMasterId(int id) {
        String query = "SELECT * FROM " + RABIES_VACCINE_TABLE + " WHERE " + DOG_ID + " = " + id;
        return getListByQuery(query);    }

    @Override
    public boolean remove(int id) {
        String query = "DELETE FROM " + RABIES_VACCINE_TABLE + " WHERE " + RABIES_VACCINE_ID + " = " + id;
        return getCursor(query);
    }

    private boolean getCursor(String query) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        boolean end = cursor.moveToFirst();
        cursor.close();
        return end;
    }

    private InjectionRabid getInjectionRabid(Cursor cursor) {
        InjectionRabid a = new InjectionRabid();
        a.setId(cursor.getInt(0));
        a.setMedicine(cursor.getString(1));
        a.setDescription(cursor.getString(2));
        try {
            a.setTreatmentDate(dateFormat.parse(cursor.getString(3)));
        } catch (Exception e) {
            a.setTreatmentDate(null);
        }
        try {
            a.setNextTreatment(dateFormat.parse(cursor.getString(4)));
        } catch (Exception e) {
            a.setNextTreatment(null);
        }
        a.setNote(cursor.getString(5));
        a.setPhoto(cursor.getString(6));
        a.setDogId(cursor.getInt(7));
        return a;
    }

    private List<InjectionRabid> getListByQuery(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<InjectionRabid> list = new LinkedList<>();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                InjectionRabid injectionRabid = getInjectionRabid(cursor);
                list.add(injectionRabid);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
}

