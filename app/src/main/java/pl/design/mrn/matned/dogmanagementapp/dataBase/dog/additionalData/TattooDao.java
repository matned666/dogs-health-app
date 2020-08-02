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

import static pl.design.mrn.matned.dogmanagementapp.Statics.*;

public class TattooDao extends SQLiteOpenHelper implements DaoFragmentInterface<Tattoo> {



    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    public TattooDao(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

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
        Cursor cursor = db.rawQuery(query, null);
        boolean end = cursor.moveToFirst();
        cursor.close();
        db.close();
        return end;
    }

    @Override
    public boolean remove(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + DOGS_TATTOO_TABLE + " WHERE " + TATTOO_ID + " = " + id;
        Cursor cursor = db.rawQuery(query, null);
        boolean end = cursor.moveToFirst();
        cursor.close();
        db.close();
        return end;    }

    @Override
    public boolean removeAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + DOGS_TATTOO_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        boolean end = cursor.moveToFirst();
        cursor.close();
        db.close();
        return end;    }

    @Override
    public boolean update(Tattoo updated_T_Data) {
        SQLiteDatabase db = this.getReadableDatabase();
        String updateTattooQuery = "UPDATE " + DOGS_TATTOO_TABLE +" " +
                "SET " +
                TATTOO_DATE + " = '" + dateFormat.format(updated_T_Data.getTattooDate()) + "', " +
                TATTOO_DESCRIPTION + " = '" + updated_T_Data.getDescription() + "', " +
                DOG_ID + " = " + updated_T_Data.getDogId() + " " +
                "WHERE " +
                TATTOO_ID + " = " + updated_T_Data.getTattooId();
        Cursor cursor = db.rawQuery(updateTattooQuery, null);
        boolean end = cursor.moveToFirst();
        cursor.close();
        db.close();
        return end;
    }

    @Override
    public List<Tattoo> getListByMasterId(int id) {
        List<Tattoo> tattoos = new ArrayList<>();
        String query = "SELECT * FROM " + DOGS_TATTOO_TABLE + " WHERE " + DOG_ID + " = " + id;
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
