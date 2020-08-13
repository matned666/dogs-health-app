package pl.design.mrn.matned.dogmanagementapp.dataBase.health;

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

public class DewormingDao extends SQLiteOpenHelper implements DaoFragmentInterface<Deworming> {

    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    public DewormingDao(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public boolean update(Deworming updated_T_Data) {
        String query = "" +
                "UPDATE " + DEWORMING_TABLE + " SET " +
                DEWORMING_MEDICINE + " = '" + updated_T_Data.getMedicine() + "'";
        if (updated_T_Data.getTreatmentDate() != null) query += ", " + DEWORMING_DATE + " = '" + dateFormat.format(updated_T_Data.getTreatmentDate()) + "'";
        if (updated_T_Data.getNextTreatment() != null) query += ", " + DEWORMING_NEXT_DATE + " = '" + dateFormat.format(updated_T_Data.getNextTreatment()) + "'";
        if (updated_T_Data.getDescription() != null) query += ", " + DEWORMING_DESCRIPTION + " = '" + updated_T_Data.getDescription() + "'";
        if (updated_T_Data.getNote() != null) query += ", " + DEWORMING_NOTE + " = '" + updated_T_Data.getNote() + "'";
        if (updated_T_Data.getPhoto() != null) query += ", " + DEWORMING_STAMP_PHOTO + " = '" + updated_T_Data.getPhoto() + "'";
        query += ", " + DOG_ID + " = " + updated_T_Data.getDogId() + " " +
                "WHERE " +
                DEWORMING_ID + " = " + updated_T_Data.getId();
        return getCursor(query);    }

    @Override
    public boolean add(Deworming deworming) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DEWORMING_MEDICINE, deworming.getMedicine());
        cv.put(DEWORMING_DESCRIPTION, deworming.getDescription());
        if (deworming.getTreatmentDate() != null)
            cv.put(DEWORMING_DATE, dateFormat.format(deworming.getTreatmentDate()));
        if (deworming.getNextTreatment() != null)
            cv.put(DEWORMING_NEXT_DATE, dateFormat.format(deworming.getNextTreatment()));
        cv.put(DEWORMING_NOTE, deworming.getNote());
        cv.put(DEWORMING_STAMP_PHOTO, deworming.getPhoto());
        cv.put(DOG_ID, deworming.getDogId());
        long insert = db.insert(DEWORMING_TABLE, null, cv);
        return insert != -1;
    }

    @Override
    public List<Deworming> findAll() {
        String query = "SELECT * FROM " + DEWORMING_TABLE;
        return getListByQuery(query);    }

    @Override
    public Deworming findById(int id) {
        String query = "SELECT * FROM " + DEWORMING_TABLE + " WHERE " + DEWORMING_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Deworming deworming = null;
        if (cursor.moveToFirst()) {
            deworming = getDeworming(cursor);
        }
        cursor.close();
        db.close();
        return deworming;
    }

    @Override
    public boolean remove(Deworming deworming) {
        String query = "DELETE FROM " + DEWORMING_TABLE + " WHERE " + DEWORMING_ID + " = " + deworming.getId();
        return getCursor(query);
    }

    @Override
    public boolean removeAll() {
        String query = "DELETE FROM " + DEWORMING_TABLE;
        return getCursor(query);      }

    @Override
    public List<Deworming> getListByMasterId(int id) {
        String query = "SELECT * FROM " + DEWORMING_TABLE + " WHERE " + DOG_ID + " = " + id;
        return getListByQuery(query);    }

    @Override
    public boolean remove(int id) {
        String query = "DELETE FROM " + DEWORMING_TABLE + " WHERE " + DEWORMING_ID + " = " + id;
        return getCursor(query);
    }

    private boolean getCursor(String query) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        boolean end = cursor.moveToFirst();
        cursor.close();
        return end;
    }

    private Deworming getDeworming(Cursor cursor) {
        Deworming a = new Deworming();
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

    private List<Deworming> getListByQuery(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Deworming> list = new LinkedList<>();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Deworming deworming = getDeworming(cursor);
                list.add(deworming);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
}

