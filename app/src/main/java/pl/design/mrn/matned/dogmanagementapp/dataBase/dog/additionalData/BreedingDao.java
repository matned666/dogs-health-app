package pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

public class BreedingDao  extends SQLiteOpenHelper implements DaoFragmentInterface_FunctionalBreeding<Breeding> {

    private static final String BREEDING_TABLE = "BREEDING_TABLE";
    private static final String BREEDING_ID = "BREEDING_ID";
    private static final String BREEDING_NAME = "BREEDING_NAME";
    private static final String BREEDING_ADDRESS = "BREEDING_ADDRESS";
    private static final String BREEDING_PHONE = "BREEDING_PHONE";
    private static final String BREEDING_EMAIL = "BREEDING_EMAIL";
    private static final String BREEDING_DESCRIPTION = "BREEDING_DESCRIPTION";
    private static final String DOG_ID = "DOG_ID";

    public BreedingDao(@Nullable Context context) {
        super(context, "dogs_db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableIfNotExist = "CREATE TABLE " + BREEDING_TABLE + "(" +
                BREEDING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                BREEDING_NAME + " TEXT, " +
                BREEDING_ADDRESS + " TEXT, " +
                BREEDING_PHONE + " TEXT, " +
                BREEDING_EMAIL + " TEXT, " +
                BREEDING_DESCRIPTION + " TEXT, " +
                DOG_ID + " INTEGER )";
        db.execSQL(createTableIfNotExist);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch(oldVersion) {
            case 1:
                db.execSQL();
                // we want both updates, so no break statement here...
            case 2:
                db.execSQL(DATABASE_CREATE_someothertable);
        }
    }





    @Override
    public boolean add(Breeding breeding) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(BREEDING_NAME, breeding.getName());
        cv.put(BREEDING_ADDRESS, breeding.getAddress());
        cv.put(BREEDING_PHONE, breeding.getPhone());
        cv.put(BREEDING_EMAIL, breeding.getEmail());
        cv.put(BREEDING_DESCRIPTION, breeding.getDescription());
        cv.put(DOG_ID, breeding.getDogId());
        long insert = db.insert(BREEDING_TABLE, null, cv);
        return insert != -1;
    }

    @Override
    public List<Breeding> findAll() {
        String query = "SELECT * FROM " + BREEDING_TABLE;
        return getBreedingsListByQuery(query);
    }

    @Override
    public Breeding findById(int id) {
        String query = "SELECT * FROM " + BREEDING_TABLE + " WHERE " + BREEDING_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Breeding breeding;
        if(cursor.moveToFirst()) {
            breeding = getBreeding(cursor);
        }else breeding = null;
        cursor.close();
        db.close();
        return breeding;
    }

    @Override
    public boolean remove(Breeding breeding) {
        String query = "DELETE FROM " + BREEDING_TABLE + " WHERE " + BREEDING_ID + " = " + breeding.getBreedingId();
        return getCursor(query);
    }

    @Override
    public boolean removeAll() {
        String query = "DELETE FROM " + BREEDING_TABLE;
        return getCursor(query);
    }

    @Override
    public boolean update(int id_toUpdate, Breeding updated_T_Data) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + BREEDING_TABLE + " SET " +
                BREEDING_NAME + " = " + updated_T_Data.getName() + ", " +
                BREEDING_ADDRESS + " = " + updated_T_Data.getAddress() + ", " +
                BREEDING_PHONE + " = " + updated_T_Data.getPhone() + ", " +
                BREEDING_EMAIL + " = " + updated_T_Data.getEmail() + ", " +
                BREEDING_DESCRIPTION + " = " + updated_T_Data.getDescription() + ", " +
                DOG_ID + " = " + updated_T_Data.getDogId() + " " +
                "WHERE " + BREEDING_ID + " = " + id_toUpdate;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        return cursor.moveToFirst();

    }

    @Override
    public List<Breeding> getListByMasterId(int id) {
        String query = "SELECT * FROM " + BREEDING_TABLE + " WHERE " + DOG_ID + " = " + id;
        return getBreedingsListByQuery(query);    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Breeding findByDogId(int id) {
        return getListByMasterId(id).stream()
                .filter(v -> v.getDogId() == id)
                .findFirst()
                .orElse(null);
    }

    private Breeding getBreeding(Cursor cursor) {
        Breeding breeding = new Breeding(cursor.getInt(0));
        breeding.setName(cursor.getString(1));
        breeding.setAddress(cursor.getString(2));
        breeding.setPhone(cursor.getString(3));
        breeding.setEmail(cursor.getString(4));
        breeding.setDescription(cursor.getString(5));
        breeding.setDogId(cursor.getInt(6));
        return breeding;
    }

    private boolean getCursor(String query) {
        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        return cursor.moveToFirst();
    }

    private List<Breeding> getBreedingsListByQuery(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Breeding> list = new LinkedList<>();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            do{
                Breeding breeding = getBreeding(cursor);
                list.add(breeding);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
}
