package pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.LinkedList;
import java.util.List;

import pl.design.mrn.matned.dogmanagementapp.dataBase.DaoBase;
import pl.design.mrn.matned.dogmanagementapp.dataBase.DaoFragmentInterface_FunctionalBreeding;

import static pl.design.mrn.matned.dogmanagementapp.Statics.*;

public class BreedingDao  extends DaoBase implements DaoFragmentInterface_FunctionalBreeding<Breeding> {


    public BreedingDao(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public boolean add(Breeding breeding) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(BREEDING_NAME, breeding.getName());
        if(breeding.getAddress() != null) cv.put(BREEDING_ADDRESS, breeding.getAddress());
        if(breeding.getPhone() != null) cv.put(BREEDING_PHONE, breeding.getPhone());
        if(breeding.getEmail() != null) cv.put(BREEDING_EMAIL, breeding.getEmail());
        if(breeding.getDescription() != null) cv.put(BREEDING_DESCRIPTION, breeding.getDescription());
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
    public boolean remove(int id) {
        String query = "DELETE FROM " + BREEDING_TABLE + " WHERE " + BREEDING_ID + " = " + id;
        return getCursor(query);    }

    @Override
    public boolean removeAll() {
        String query = "DELETE FROM " + BREEDING_TABLE;
        return getCursor(query);
    }

    @Override
    public boolean update(Breeding updated_T_Data) {
        String query = "UPDATE " + BREEDING_TABLE + " SET " +
                BREEDING_NAME + " = '" + updated_T_Data.getName() + "', " +
                BREEDING_ADDRESS + " = '" + updated_T_Data.getAddress() + "', " +
                BREEDING_PHONE + " = '" + updated_T_Data.getPhone() + "', " +
                BREEDING_EMAIL + " = '" + updated_T_Data.getEmail() + "', " +
                BREEDING_DESCRIPTION + " = '" + updated_T_Data.getDescription() + "', " +
                DOG_ID + " = " + updated_T_Data.getDogId() + " " +
                "WHERE " + BREEDING_ID + " = " + updated_T_Data.getBreedingId();
        return getCursor(query);

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
