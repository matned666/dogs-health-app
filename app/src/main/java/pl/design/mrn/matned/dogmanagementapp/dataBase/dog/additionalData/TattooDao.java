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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pl.design.mrn.matned.dogmanagementapp.dataBase.DaoInterface;

import static pl.design.mrn.matned.dogmanagementapp.Statics.DATE_FORMAT;

public class TattooDao extends SQLiteOpenHelper implements DaoInterface<Tattoo> {

    private static final String DOGS_TATTOO_TABLE = "DOGS_TATTOO";
    private static final String TATTOO_ID = "TATTOO_ID";
    private static final String DOG_ID = "DOG_ID";
    private static final String TATTOO_DESCRIPTION = "TATTOO_DESCRIPTION";
    private static final String TATTOO_DATE = "TATTOO_DATE";

    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    public TattooDao(@Nullable Context context) {
        super(context, "dogs_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableIfNotExists = "CREATE TABLE " + DOGS_TATTOO_TABLE + "(" +
                TATTOO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TATTOO_DATE + " TEXT, " +
                TATTOO_DESCRIPTION + " TEXT, " +
                DOG_ID + " INTEGER )";
        db.execSQL(createTableIfNotExists);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public boolean add(Tattoo tattoo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TATTOO_DATE, tattoo.getTattooDate().toString());
        cv.put(TATTOO_DESCRIPTION, tattoo.getDescription());
        cv.put(DOG_ID, tattoo.getDogId());
        long insert = db.insert(DOGS_TATTOO_TABLE, null, cv);
        return insert != -1;
    }

    @Override
    public List<Tattoo> findAll() {
        List<Tattoo> tattoos = new ArrayList<>();
        String query = "SELECT * FROM " + DOGS_TATTOO_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            do {
                Tattoo tattoo = getTattooFromDB(cursor);
                tattoos.add(tattoo);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tattoos;
    }

    @Override
    public Tattoo findById(int id) {
        String query = "SELECT * FROM " + DOGS_TATTOO_TABLE + " WHERE " + TATTOO_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Tattoo tattoo;
        if(cursor.moveToFirst()) {
            tattoo = getTattooFromDB(cursor);
        }else{
            tattoo = null;
        }
        cursor.close();
        db.close();
        return tattoo;
    }

    @Override
    public boolean remove(Tattoo tattoo) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + DOGS_TATTOO_TABLE + " WHERE " + TATTOO_ID + " = " + tattoo.getTattooId();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        return cursor.moveToFirst();
    }

    @Override
    public void removeAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + DOGS_TATTOO_TABLE;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
    }

    @Override
    public boolean update(int id_toUpdate, Tattoo updated_T_Data) {
        SQLiteDatabase db = this.getReadableDatabase();
        String updateTattooQuery = "" +
                "UPDATE " + DOGS_TATTOO_TABLE +" " +
                "SET " +
                TATTOO_DATE + " = " + dateFormat.format(updated_T_Data.getTattooDate()) + ", " +
                TATTOO_DESCRIPTION + " = " + updated_T_Data.getDescription() + ", " +
                DOG_ID + " = " + updated_T_Data.getDogId() + " " +
                "WHERE " +
                TATTOO_ID + " = " + id_toUpdate;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(updateTattooQuery, null);
        return cursor.moveToFirst();
    }

    private Tattoo getTattooFromDB(Cursor cursor) {
        int tattooId = cursor.getInt(0);
        Date tattooDate;
        try{
            tattooDate = dateFormat.parse(cursor.getString(1));
        }catch (ParseException e){
            tattooDate = new Date();
        }
        String description = cursor.getString(2);
        int dogId = cursor.getInt(3);
        Tattoo tattoo = new Tattoo();
        tattoo.setTattooId(tattooId);
        tattoo.setTattooDate(tattooDate);
        tattoo.setDescription(description);
        tattoo.setDogId(dogId);
        return tattoo;
    }
}
