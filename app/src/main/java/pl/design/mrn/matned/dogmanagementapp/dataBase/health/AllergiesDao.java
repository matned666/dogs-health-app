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

import static pl.design.mrn.matned.dogmanagementapp.Statics.ALLERGEN;
import static pl.design.mrn.matned.dogmanagementapp.Statics.ALLERGIES_TABLE;
import static pl.design.mrn.matned.dogmanagementapp.Statics.ALLERGY_DATE_OF_NEXT_TREATMENT;
import static pl.design.mrn.matned.dogmanagementapp.Statics.ALLERGY_DATE_OF_TREATMENT;
import static pl.design.mrn.matned.dogmanagementapp.Statics.ALLERGY_DESCRIPTION;
import static pl.design.mrn.matned.dogmanagementapp.Statics.ALLERGY_DETECTION_DATE;
import static pl.design.mrn.matned.dogmanagementapp.Statics.ALLERGY_ID;
import static pl.design.mrn.matned.dogmanagementapp.Statics.ALLERGY_NOTE;
import static pl.design.mrn.matned.dogmanagementapp.Statics.ALLERGY_STAMP_PHOTO;
import static pl.design.mrn.matned.dogmanagementapp.Statics.DATABASE_NAME;
import static pl.design.mrn.matned.dogmanagementapp.Statics.DATE_FORMAT;
import static pl.design.mrn.matned.dogmanagementapp.Statics.DOG_ID;
import static pl.design.mrn.matned.dogmanagementapp.Statics.WAS_ALLERGY_TREATED;

public class AllergiesDao extends SQLiteOpenHelper implements DaoFragmentInterface<Allergies> {

    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    public AllergiesDao(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


    @Override
    public boolean update(Allergies updated_T_Data) {
        String query = "" +
                "UPDATE " + ALLERGIES_TABLE + " SET " +
                ALLERGEN + " = '" + updated_T_Data.getAllergen() + "'";
        if (updated_T_Data.getDescription() != null) query += ", " + ALLERGY_DESCRIPTION + " = '" + updated_T_Data.getDescription() + "'";
        if (updated_T_Data.getDateOfDetection() != null) query += ", " + ALLERGY_DETECTION_DATE + " = '" + dateFormat.format(updated_T_Data.getDateOfDetection()) + "'";
        if (updated_T_Data.getDateOfTreatment() != null) query += ", " + ALLERGY_DATE_OF_TREATMENT + " = '" + dateFormat.format(updated_T_Data.getDateOfTreatment()) + "'";
        if (updated_T_Data.getDateOfNextTreatment() != null) query += ", " + ALLERGY_DATE_OF_NEXT_TREATMENT + " = '" + dateFormat.format(updated_T_Data.getDateOfNextTreatment()) + "'";
        if (updated_T_Data.getNote() != null) query += ", " + ALLERGY_NOTE + " = '" + updated_T_Data.getNote() + "'";
        if (updated_T_Data.getPhoto() != null) query += ", " + ALLERGY_STAMP_PHOTO + " = '" + updated_T_Data.getPhoto() + "'";
        query += ", " + DOG_ID + " = " + updated_T_Data.getDogId() + " " +
                "WHERE " +
                ALLERGY_ID + " = " + updated_T_Data.getId();
        return getCursor(query);    }

    @Override
    public boolean add(Allergies allergies) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ALLERGEN, allergies.getAllergen());
        cv.put(ALLERGY_DESCRIPTION, allergies.getDescription());
        if (allergies.getDateOfDetection() != null)
            cv.put(ALLERGY_DETECTION_DATE, dateFormat.format(allergies.getDateOfDetection()));
        if (allergies.getDateOfTreatment() != null)
            cv.put(ALLERGY_DATE_OF_TREATMENT, dateFormat.format(allergies.getDateOfTreatment()));
        if (allergies.getDateOfNextTreatment() != null)
            cv.put(ALLERGY_DATE_OF_NEXT_TREATMENT, dateFormat.format(allergies.getDateOfNextTreatment()));
        cv.put(ALLERGY_NOTE, allergies.getNote());
        cv.put(ALLERGY_STAMP_PHOTO, allergies.getPhoto());
        cv.put(DOG_ID, allergies.getDogId());
        long insert = db.insert(ALLERGIES_TABLE, null, cv);
        return insert != -1;
    }

    @Override
    public List<Allergies> findAll() {
        String query = "SELECT * FROM " + ALLERGIES_TABLE;
        return getListByQuery(query);    }

    @Override
    public Allergies findById(int id) {
        String query = "SELECT * FROM " + ALLERGIES_TABLE + " WHERE " + ALLERGY_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Allergies allergies = null;
        if (cursor.moveToFirst()) {
            allergies = getAllergy(cursor);
        }
        cursor.close();
        db.close();
        return allergies;
    }

    @Override
    public boolean remove(Allergies allergies) {
        String query = "DELETE FROM " + ALLERGIES_TABLE + " WHERE " + ALLERGY_ID + " = " + allergies.getId();
        return getCursor(query);
    }

    @Override
    public boolean removeAll() {
        String query = "DELETE FROM " + ALLERGIES_TABLE;
        return getCursor(query);      }

    @Override
    public List<Allergies> getListByMasterId(int id) {
        String query = "SELECT * FROM " + ALLERGIES_TABLE + " WHERE " + DOG_ID + " = " + id;
        return getListByQuery(query);    }

    @Override
    public boolean remove(int id) {
        String query = "DELETE FROM " + ALLERGIES_TABLE + " WHERE " + ALLERGY_ID + " = " + id;
        return getCursor(query);
    }

    private boolean getCursor(String query) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        boolean end = cursor.moveToFirst();
        cursor.close();
        return end;
    }

    private Allergies getAllergy(Cursor cursor) {
        Allergies a = new Allergies();
        a.setId(cursor.getInt(0));
        a.setAllergen(cursor.getString(1));
        a.setDescription(cursor.getString(2));
        try {
            a.setDateOfDetection(dateFormat.parse(cursor.getString(3)));
        } catch (Exception e) {
            a.setDateOfDetection(null);
        }
        try {
            a.setDateOfTreatment(dateFormat.parse(cursor.getString(5)));
        } catch (Exception e) {
            a.setDateOfTreatment(null);
        }
        try {
            a.setDateOfNextTreatment(dateFormat.parse(cursor.getString(6)));
        } catch (Exception e) {
            a.setDateOfNextTreatment(null);
        }
        a.setNote(cursor.getString(7));
        a.setPhoto(cursor.getString(8));
        a.setDogId(cursor.getInt(9));
        return a;
    }

    private List<Allergies> getListByQuery(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Allergies> list = new LinkedList<>();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Allergies allergies = getAllergy(cursor);
                list.add(allergies);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
}
