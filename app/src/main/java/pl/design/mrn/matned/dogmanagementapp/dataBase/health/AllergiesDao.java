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
import pl.design.mrn.matned.dogmanagementapp.dataBase.DaoInterface;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.Chip;

import static pl.design.mrn.matned.dogmanagementapp.Statics.ALLERGEN;
import static pl.design.mrn.matned.dogmanagementapp.Statics.ALLERGIES_TABLE;
import static pl.design.mrn.matned.dogmanagementapp.Statics.ALLERGY_DATE_OF_NEXT_TREATMENT;
import static pl.design.mrn.matned.dogmanagementapp.Statics.ALLERGY_DATE_OF_TREATMENT;
import static pl.design.mrn.matned.dogmanagementapp.Statics.ALLERGY_DESCRIPTION;
import static pl.design.mrn.matned.dogmanagementapp.Statics.ALLERGY_DETECTION_DATE;
import static pl.design.mrn.matned.dogmanagementapp.Statics.ALLERGY_ID;
import static pl.design.mrn.matned.dogmanagementapp.Statics.ALLERGY_NOTE;
import static pl.design.mrn.matned.dogmanagementapp.Statics.ALLERGY_STAMP_PHOTO;
import static pl.design.mrn.matned.dogmanagementapp.Statics.CHIP_DESCRIPTION;
import static pl.design.mrn.matned.dogmanagementapp.Statics.CHIP_EXP_DATE;
import static pl.design.mrn.matned.dogmanagementapp.Statics.CHIP_ID;
import static pl.design.mrn.matned.dogmanagementapp.Statics.CHIP_NUMBER;
import static pl.design.mrn.matned.dogmanagementapp.Statics.CHIP_PUT_DATE;
import static pl.design.mrn.matned.dogmanagementapp.Statics.CHIP_TABLE;
import static pl.design.mrn.matned.dogmanagementapp.Statics.DATABASE_NAME;
import static pl.design.mrn.matned.dogmanagementapp.Statics.DATE_FORMAT;
import static pl.design.mrn.matned.dogmanagementapp.Statics.DOG_ID;
import static pl.design.mrn.matned.dogmanagementapp.Statics.WAS_ALLERGY_TREATED;

public class AllergiesDao extends SQLiteOpenHelper implements DaoFragmentInterface<Allergies> {

    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    public AllergiesDao(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public boolean update(Allergies updated_T_Data) {
        return false;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public boolean add(Allergies allergies) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ALLERGEN, allergies.getAllergen());
        cv.put(ALLERGY_DESCRIPTION, allergies.getDescription());
        if (allergies.getDateOfDetection() != null)
            cv.put(ALLERGY_DETECTION_DATE, dateFormat.format(allergies.getDateOfDetection()));
        cv.put(WAS_ALLERGY_TREATED, allergies.isWasTreated()? 1 : 0);
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
//            allergies = getAllergy(cursor);
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
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        TODO
    }

    @Override
    public List<Allergies> getListByMasterId(int id) {
        return null;
    }

    @Override
    public boolean remove(int id) {
        return false;
    }

    private boolean getCursor(String query) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        boolean end = cursor.moveToFirst();
        cursor.close();
        return end;
    }

//    private Allergies getAllergy(Cursor cursor) {
//        Allergies all = new Chip(cursor.getInt(0));
//        all.setChipNumber(cursor.getString(1));
//        try {
//            all.setPutDate(dateFormat.parse(cursor.getString(2)));
//        } catch (Exception e) {
//            all.setPutDate(null);
//        }
//        try {
//            all.setExpDate(dateFormat.parse(cursor.getString(3)));
//        } catch (Exception e) {
//            all.setExpDate(null);
//        }
//        all.setChipDescription(cursor.getString(4));
//        all.setDogId(cursor.getInt(5));
//        return all;
//    }

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
