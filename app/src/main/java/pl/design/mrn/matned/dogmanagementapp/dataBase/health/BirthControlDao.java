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

public class BirthControlDao extends SQLiteOpenHelper implements DaoFragmentInterface<BirthControl> {

    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);


    public BirthControlDao(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public boolean update(BirthControl updated_T_Data) {
        String query = "" +
                "UPDATE " + BIRTH_CONTROL_TABLE + " SET " +
                BIRTH_CONTROL_NUMBER_OF_CHILDREN + " = '" + updated_T_Data.getNumberOfChildren() + "', " +
                BIRTH_CONTROL_DATE + " = " + (dateFormat.format(updated_T_Data.getDateOfBirth())) + "";
        if (updated_T_Data.getDescription() != null) query += ", " + BIRTH_CONTROL_DESCRIPTION + " = '" + updated_T_Data.getDescription() + "'";
        if (updated_T_Data.getNote() != null) query += ", " + BIRTH_CONTROL_NOTE + " = '" + updated_T_Data.getNote() + "'";
        if (updated_T_Data.getPhoto() != null) query += ", " + BIRTH_CONTROL_STAMP_PHOTO + " = '" + updated_T_Data.getPhoto() + "'";
        query += ", " + DOG_ID + " = " + updated_T_Data.getDogId() + " " +
                "WHERE " +
                BIRTH_CONTROL_ID + " = " + updated_T_Data.getId();
        return getCursor(query);    }

    @Override
    public boolean add(BirthControl birthControl) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(BIRTH_CONTROL_NUMBER_OF_CHILDREN, birthControl.getNumberOfChildren());
        cv.put(BIRTH_CONTROL_DESCRIPTION, birthControl.getDescription());
        if (birthControl.getDateOfBirth() != null)
            cv.put(BIRTH_CONTROL_DATE, dateFormat.format(birthControl.getDateOfBirth()));
        cv.put(BIRTH_CONTROL_NOTE, birthControl.getNote());
        cv.put(BIRTH_CONTROL_STAMP_PHOTO, birthControl.getPhoto());
        cv.put(DOG_ID, birthControl.getDogId());
        long insert = db.insert(BIRTH_CONTROL_TABLE, null, cv);
        return insert != -1;
    }

    @Override
    public List<BirthControl> findAll() {
        String query = "SELECT * FROM " + BIRTH_CONTROL_TABLE;
        return getListByQuery(query);    }

    @Override
    public BirthControl findById(int id) {
        String query = "SELECT * FROM " + BIRTH_CONTROL_TABLE + " WHERE " + BIRTH_CONTROL_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        BirthControl birthControl = null;
        if (cursor.moveToFirst()) {
            birthControl = getBirthControl(cursor);
        }
        cursor.close();
        db.close();
        return birthControl;
    }

    @Override
    public boolean remove(BirthControl birthControl) {
        String query = "DELETE FROM " + BIRTH_CONTROL_TABLE + " WHERE " + BIRTH_CONTROL_ID + " = " + birthControl.getId();
        return getCursor(query);
    }

    @Override
    public boolean removeAll() {
        String query = "DELETE FROM " + BIRTH_CONTROL_TABLE;
        return getCursor(query);      }

    @Override
    public List<BirthControl> getListByMasterId(int id) {
        String query = "SELECT * FROM " + BIRTH_CONTROL_TABLE + " WHERE " + DOG_ID + " = " + id;
        return getListByQuery(query);    }

    @Override
    public boolean remove(int id) {
        String query = "DELETE FROM " + BIRTH_CONTROL_TABLE + " WHERE " + BIRTH_CONTROL_ID + " = " + id;
        return getCursor(query);
    }

    private boolean getCursor(String query) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        boolean end = cursor.moveToFirst();
        cursor.close();
        return end;
    }

    private BirthControl getBirthControl(Cursor cursor) {
        BirthControl a = new BirthControl();
        a.setId(cursor.getInt(0));
        a.setNumberOfChildren(cursor.getInt(1));
        a.setDescription(cursor.getString(2));
        try {
            a.setDateOfBirth(dateFormat.parse(cursor.getString(3)));
        } catch (Exception e) {
            a.setDateOfBirth(null);
        }
        a.setNote(cursor.getString(4));
        a.setPhoto(cursor.getString(5));
        a.setDogId(cursor.getInt(6));
        return a;
    }

    private List<BirthControl> getListByQuery(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<BirthControl> list = new LinkedList<>();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                BirthControl birthControl = getBirthControl(cursor);
                list.add(birthControl);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
}
