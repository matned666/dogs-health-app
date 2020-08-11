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

public class TeethControlDao extends SQLiteOpenHelper implements DaoFragmentInterface<TeethControl> {

    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    public TeethControlDao(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    @Override
    public boolean update(TeethControl updated_T_Data) {
        String query = "" +
                "UPDATE " + TEETH_CONTROL_TABLE + " SET " +
                TEETH_C_DESCRIPTION + " = '" + updated_T_Data.getDescription() + "'";
        if (updated_T_Data.getDateOfControl() != null) query += ", " + TEETH_C_DATE + " = '" + dateFormat.format(updated_T_Data.getDateOfControl()) + "'";
        if (updated_T_Data.getDateOfNextControl() != null) query += ", " + TEETH_C_NEXT_DATE + " = '" + dateFormat.format(updated_T_Data.getDateOfNextControl()) + "'";
        if (updated_T_Data.getNote() != null) query += ", " + TEETH_C_NOTE + " = '" + updated_T_Data.getNote() + "'";
        if (updated_T_Data.getPhoto() != null) query += ", " + TEETH_C_STAMP_PHOTO + " = '" + updated_T_Data.getPhoto() + "'";
        query += ", " + DOG_ID + " = " + updated_T_Data.getDogId() + " " +
                "WHERE " +
                TEETH_C_ID + " = " + updated_T_Data.getId();
        return getCursor(query);    }

    @Override
    public boolean add(TeethControl teethControl) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TEETH_C_DESCRIPTION, teethControl.getDescription());
        if (teethControl.getDateOfControl() != null)
            cv.put(TEETH_C_DATE, dateFormat.format(teethControl.getDateOfControl()));
        if (teethControl.getDateOfNextControl() != null)
            cv.put(TEETH_C_NEXT_DATE, dateFormat.format(teethControl.getDateOfNextControl()));
        cv.put(TEETH_C_NOTE, teethControl.getNote());
        cv.put(TEETH_C_STAMP_PHOTO, teethControl.getPhoto());
        cv.put(DOG_ID, teethControl.getDogId());
        long insert = db.insert(TEETH_CONTROL_TABLE, null, cv);
        return insert != -1;
    }

    @Override
    public List<TeethControl> findAll() {
        String query = "SELECT * FROM " + TEETH_CONTROL_TABLE;
        return getListByQuery(query);    }

    @Override
    public TeethControl findById(int id) {
        String query = "SELECT * FROM " + TEETH_CONTROL_TABLE + " WHERE " + TEETH_C_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        TeethControl teethControl = null;
        if (cursor.moveToFirst()) {
            teethControl = getTeathControl(cursor);
        }
        cursor.close();
        db.close();
        return teethControl;
    }

    @Override
    public boolean remove(TeethControl teethControl) {
        String query = "DELETE FROM " + TEETH_CONTROL_TABLE + " WHERE " + TEETH_C_ID + " = " + teethControl.getId();
        return getCursor(query);
    }

    @Override
    public boolean removeAll() {
        String query = "DELETE FROM " + TEETH_CONTROL_TABLE;
        return getCursor(query);      }

    @Override
    public List<TeethControl> getListByMasterId(int id) {
        String query = "SELECT * FROM " + TEETH_CONTROL_TABLE + " WHERE " + DOG_ID + " = " + id;
        return getListByQuery(query);    }

    @Override
    public boolean remove(int id) {
        String query = "DELETE FROM " + TEETH_CONTROL_TABLE + " WHERE " + TEETH_C_ID + " = " + id;
        return getCursor(query);
    }

    private boolean getCursor(String query) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        boolean end = cursor.moveToFirst();
        cursor.close();
        return end;
    }

    private TeethControl getTeathControl(Cursor cursor) {
        TeethControl a = new TeethControl();
        a.setId(cursor.getInt(0));
        a.setDescription(cursor.getString(1));
        try {
            a.setDateOfControl(dateFormat.parse(cursor.getString(2)));
        } catch (Exception e) {
            a.setDateOfControl(null);
        }
        try {
            a.setDateOfNextControl(dateFormat.parse(cursor.getString(3)));
        } catch (Exception e) {
            a.setDateOfNextControl(null);
        }
        a.setNote(cursor.getString(4));
        a.setPhoto(cursor.getString(5));
        a.setDogId(cursor.getInt(6));
        return a;
    }

    private List<TeethControl> getListByQuery(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<TeethControl> list = new LinkedList<>();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                TeethControl teethControl = getTeathControl(cursor);
                list.add(teethControl);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
}
