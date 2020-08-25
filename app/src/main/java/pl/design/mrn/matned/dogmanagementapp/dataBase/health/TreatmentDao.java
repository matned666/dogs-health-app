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

public class TreatmentDao extends DaoBase implements DaoFragmentInterface<Treatment> {

    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    public TreatmentDao(@Nullable Context context) {
        super(context, DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public boolean update(Treatment updated_T_Data) {
        String query = "" +
                "UPDATE " + TREATMENT_TABLE + " SET " +
                ILLNESS + " = '" + updated_T_Data.getIllness() + "'";
        if (updated_T_Data.getDescription() != null) query += ", " + TREATMENT_DESCRIPTION + " = '" + updated_T_Data.getDescription() + "'";
        if (updated_T_Data.getDateOfTreatment() != null) query += ", " + TREATMENT_DATE + " = '" + dateFormat.format(updated_T_Data.getDateOfTreatment()) + "'";
        if (updated_T_Data.getDateOfNextTreatment() != null) query += ", " + DATE_OF_NEXT_TREATMENT + " = '" + dateFormat.format(updated_T_Data.getDateOfNextTreatment()) + "'";
        if (updated_T_Data.getNote() != null) query += ", " + TREATMENT_NOTE + " = '" + updated_T_Data.getNote() + "'";
        if (updated_T_Data.getPhoto() != null) query += ", " + TREATMENT_STAMP_PHOTO + " = '" + updated_T_Data.getPhoto() + "'";
        query += ", " + DOG_ID + " = " + updated_T_Data.getDogId() + " " +
                "WHERE " +
                TREATMENT_ID + " = " + updated_T_Data.getId();
        return getCursor(query);    }

    @Override
    public boolean add(Treatment treatment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ILLNESS, treatment.getIllness());
        cv.put(TREATMENT_DESCRIPTION, treatment.getDescription());
        if (treatment.getDateOfTreatment() != null)
            cv.put(TREATMENT_DATE, dateFormat.format(treatment.getDateOfTreatment()));
        if (treatment.getDateOfNextTreatment() != null)
            cv.put(DATE_OF_NEXT_TREATMENT, dateFormat.format(treatment.getDateOfNextTreatment()));
        cv.put(TREATMENT_NOTE, treatment.getNote());
        cv.put(TREATMENT_STAMP_PHOTO, treatment.getPhoto());
        cv.put(DOG_ID, treatment.getDogId());
        long insert = db.insert(TREATMENT_TABLE, null, cv);
        return insert != -1;
    }

    @Override
    public List<Treatment> findAll() {
        String query = "SELECT * FROM " + TREATMENT_TABLE;
        return getListByQuery(query);    }

    @Override
    public Treatment findById(int id) {
        String query = "SELECT * FROM " + TREATMENT_TABLE + " WHERE " + TREATMENT_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Treatment treatment = null;
        if (cursor.moveToFirst()) {
            treatment = getTreatment(cursor);
        }
        cursor.close();
        db.close();
        return treatment;
    }

    @Override
    public boolean remove(Treatment treatment) {
        String query = "DELETE FROM " + TREATMENT_TABLE + " WHERE " + TREATMENT_ID + " = " + treatment.getId();
        return getCursor(query);
    }

    @Override
    public boolean removeAll() {
        String query = "DELETE FROM " + TREATMENT_TABLE;
        return getCursor(query);      }

    @Override
    public List<Treatment> getListByMasterId(int id) {
        String query = "SELECT * FROM " + TREATMENT_TABLE + " WHERE " + DOG_ID + " = " + id;
        return getListByQuery(query);    }

    @Override
    public boolean remove(int id) {
        String query = "DELETE FROM " + TREATMENT_TABLE + " WHERE " + TREATMENT_ID + " = " + id;
        return getCursor(query);
    }

    private Treatment getTreatment(Cursor cursor) {
        Treatment a = new Treatment();
        a.setId(cursor.getInt(0));
        a.setIllness(cursor.getString(1));
        a.setDescription(cursor.getString(2));
        try {
            a.setDateOfTreatment(dateFormat.parse(cursor.getString(3)));
        } catch (Exception e) {
            a.setDateOfTreatment(null);
        }
        try {
            a.setDateOfNextTreatment(dateFormat.parse(cursor.getString(4)));
        } catch (Exception e) {
            a.setDateOfNextTreatment(null);
        }
        a.setNote(cursor.getString(5));
        a.setPhoto(cursor.getString(6));
        a.setDogId(cursor.getInt(7));
        return a;
    }

    private List<Treatment> getListByQuery(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Treatment> list = new LinkedList<>();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Treatment treatment = getTreatment(cursor);
                list.add(treatment);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
}
