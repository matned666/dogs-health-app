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

public class OwnerDao extends SQLiteOpenHelper implements DaoInterface<Owner> {

    private static final String DOGS_OWNER_TABLE = "DOGS_OWNER_TABLE";
    private static final String OWNER_ID = "OWNER_ID";
    private static final String OWNER_NAME = "OWNER_NAME";
    private static final String OWNER_SURNAME = "OWNER_SURNAME";
    private static final String OWNER_ADDRESS = "OWNER_ADDRESS";
    private static final String OWNER_PHONE = "OWNER_PHONE";
    private static final String OWNER_EMAIL = "OWNER_EMAIL";
    private static final String OWNER_DATE_FROM = "OWNER_DATE_FROM";
    private static final String OWNER_DATE_TO = "OWNER_DATE_TO";
    private static final String OWNER_DESCRIPTION = "OWNER_DESCRIPTION";
    private static final String DOG_ID = "DOG_ID";

    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    public OwnerDao(@Nullable Context context) {
        super(context, "dogs_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableIfNotExists = "CREATE TABLE " + DOGS_OWNER_TABLE + "(" +
                OWNER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                OWNER_NAME + " TEXT, " +
                OWNER_SURNAME + " TEXT, " +
                OWNER_ADDRESS + " TEXT, " +
                OWNER_PHONE + " TEXT, " +
                OWNER_EMAIL + " TEXT, " +
                OWNER_DATE_FROM + " TEXT, " +
                OWNER_DATE_TO + " TEXT, " +
                OWNER_DESCRIPTION + " TEXT, " +
                DOG_ID + " INTEGER )";
        db.execSQL(createTableIfNotExists);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public boolean add(Owner owner) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(OWNER_NAME, owner.getName());
        cv.put(OWNER_SURNAME, owner.getSurname());
        cv.put(OWNER_ADDRESS, owner.getAddress());
        cv.put(OWNER_PHONE, owner.getPhoneNumber());
        cv.put(OWNER_EMAIL, owner.getEmail());
        cv.put(OWNER_DATE_FROM, owner.getDateFrom().toString());
        cv.put(OWNER_DATE_TO, owner.getDateTo().toString());
        cv.put(OWNER_DESCRIPTION, owner.getDescription());
        cv.put(DOG_ID, owner.getDog_id());
        long insert = db.insert(DOGS_OWNER_TABLE, null, cv);
        return insert != -1;
    }

    @Override
    public List<Owner> findAll() {
        List<Owner> owners = new ArrayList<>();
        String query = "SELECT * FROM " + DOGS_OWNER_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            do {
                Owner owner = getOwnerFromDB(cursor);
                owners.add(owner);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return owners;
    }

    @Override
    public Owner findById(int id) {
        String query = "SELECT * FROM " + DOGS_OWNER_TABLE + " WHERE " + OWNER_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Owner owner;
        if(cursor.moveToFirst()) {
            owner = getOwnerFromDB(cursor);
        }else{
            owner = null;
        }
        cursor.close();
        db.close();
        return owner;
    }

    @Override
    public boolean remove(Owner owner) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + DOGS_OWNER_TABLE + " WHERE " + OWNER_ID + " = " + owner.getId();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        return cursor.moveToFirst();
    }

    @Override
    public boolean removeAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + DOGS_OWNER_TABLE;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        return cursor.moveToFirst();
    }

    @Override
    public boolean update(int id_toUpdate, Owner updated_T_Data) {
        SQLiteDatabase db = this.getReadableDatabase();
        String updateTattooQuery = "" +
                "UPDATE " + DOGS_OWNER_TABLE +" " +
                "SET " +
                OWNER_NAME + " = " + updated_T_Data.getName() + ", " +
                OWNER_SURNAME + " = " + updated_T_Data.getSurname() + ", " +
                OWNER_ADDRESS + " = " + updated_T_Data.getAddress() + ", " +
                OWNER_PHONE + " = " + updated_T_Data.getPhoneNumber() + ", " +
                OWNER_EMAIL + " = " + updated_T_Data.getEmail() + ", " +
                OWNER_DATE_FROM + " = " + dateFormat.format(updated_T_Data.getDateFrom()) + ", " +
                OWNER_DATE_TO + " = " + dateFormat.format(updated_T_Data.getDateTo()) + ", " +
                OWNER_DESCRIPTION + " = " + updated_T_Data.getDescription() + ", " +
                DOG_ID + " = " + updated_T_Data.getDog_id() + ", " +
                "WHERE " +
                OWNER_ID + " = " + id_toUpdate;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(updateTattooQuery, null);
        return cursor.moveToFirst();
    }

    private Owner getOwnerFromDB(Cursor cursor) {
        int id = cursor.getInt(0);
        String name = cursor.getString(1);
        String surname = cursor.getString(2);
        String address = cursor.getString(3);
        String phone = cursor.getString(4);
        String email = cursor.getString(5);
        Date fromDate;
        Date toDate;
        try{
            fromDate = dateFormat.parse(cursor.getString(6));
        }catch (ParseException e){
            fromDate = new Date();
        }
        try{
            toDate = dateFormat.parse(cursor.getString(7));
        }catch (ParseException e){
            toDate = new Date();
        }
        String description = cursor.getString(8);
        int dogId = cursor.getInt(9);
        Owner owner = new Owner(id);
        owner.setName(name);
        owner.setSurname(surname);
        owner.setAddress(address);
        owner.setPhoneNumber(phone);
        owner.setEmail(email);
        owner.setDateFrom(fromDate);
        owner.setDateTo(toDate);
        owner.setDescription(description);
        owner.setDog_id(dogId);
        return owner;
    }
}
