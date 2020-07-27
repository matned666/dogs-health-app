package pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static pl.design.mrn.matned.dogmanagementapp.Statics.DATE_FORMAT;

public class SpecialSignDao extends SQLiteOpenHelper implements DaoFragmentInterface<SpecialSign> {

    private static final String SIGNS_TABLE = "SIGNS_TABLE";
    private static final String SIGN_ID = "SIGN_ID";
    private static final String SIGN_DESCRIPTION = "SIGN_DESCRIPTION";
    private static final String SIGN_DATE = "SIGN_DATE";
    private static final String DOG_ID = "DOG_ID";

    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    public SpecialSignDao(@Nullable Context context) {
        super(context, "dogs_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableWhenNotExist = "CREATE TABLE " + SIGNS_TABLE + "(" +
                SIGN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SIGN_DESCRIPTION + " TEXT, " +
                SIGN_DATE + " TEXT, " +
                DOG_ID + " INTEGER )";
        db.execSQL(createTableWhenNotExist);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public boolean add(SpecialSign specialSign) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SIGN_DESCRIPTION, specialSign.getDescription());
        cv.put(SIGN_DATE, dateFormat.format(new Date()));
        cv.put(DOG_ID, specialSign.getDogId());
        long insert = db.insert(SIGNS_TABLE, null, cv);
        return insert != -1;
    }

    @Override
    public List<SpecialSign> findAll() {
        String query = "SELECT * FROM " + SIGNS_TABLE;
        return getSignsFromDB_ByQuery(query);
    }

    @Override
    public boolean remove(SpecialSign specialSign) {
        String query = "DELETE FROM " + SIGNS_TABLE + " WHERE " + SIGN_ID + " = " + specialSign.getSignId();
        @SuppressLint("Recycle") Cursor cursor = getCursor(query);
        return cursor.moveToFirst();
    }

    @Override
    public boolean removeAll() {
        String query = "DELETE FROM " + SIGNS_TABLE;
        @SuppressLint("Recycle") Cursor cursor = getCursor(query);
        return cursor.moveToFirst();
    }

    @Override
    public SpecialSign findById(int id) {
        String query = "SELECT * FROM " + SIGNS_TABLE + " WHERE " + SIGN_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        SpecialSign specialSign = null;
        if (cursor.moveToFirst()) {
            specialSign = getSign(cursor);
        }
        cursor.close();
        db.close();
        return specialSign;
    }

    @Override
    public boolean update(int id_toUpdate, SpecialSign updated_T_Data) {
        String query = "" +
                "UPDATE " + SIGNS_TABLE + " SET " +
                SIGN_DESCRIPTION + " = " + updated_T_Data.getDescription() + ", " +
                DOG_ID + " = " + updated_T_Data.getDogId() + " " +
                "WHERE " +
                SIGN_ID + " = " + updated_T_Data.getSignId();
        @SuppressLint("Recycle") Cursor cursor = getCursor(query);
        return cursor.moveToFirst();
    }

    @Override
    public List<SpecialSign> getListByMasterId(int id) {
        String query = "SELECT * FROM " + SIGNS_TABLE + " WHERE " + DOG_ID + " = " + id;
        return getSignsFromDB_ByQuery(query);
    }

    private SpecialSign getSign(Cursor cursor) {
        SpecialSign specialSign = new SpecialSign(cursor.getInt(0));
        specialSign.setDescription(cursor.getString(1));
        try {
            specialSign.setDate(dateFormat.parse(cursor.getString(2)));
        } catch (ParseException e) {
            specialSign.setDate(new Date());
        }
        specialSign.setDogId(cursor.getInt(3));
        return specialSign;
    }

    private List<SpecialSign> getSignsFromDB_ByQuery(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<SpecialSign> list = new LinkedList<>();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do{
                SpecialSign specialSign = getSign(cursor);
                list.add(specialSign);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    private Cursor getCursor(String query) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(query, null);
    }
}
