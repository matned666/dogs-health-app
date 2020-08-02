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

public class OwnerDao extends SQLiteOpenHelper implements DaoFragmentInterface<Owner> {



    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    public OwnerDao(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

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
        if(owner.getAddress() != null) cv.put(OWNER_ADDRESS, owner.getAddress());
        if(owner.getPhoneNumber() != null) cv.put(OWNER_PHONE, owner.getPhoneNumber());
        if(owner.getEmail() != null) cv.put(OWNER_EMAIL, owner.getEmail());
        if(owner.getDateFrom() != null) cv.put(OWNER_DATE_FROM, owner.getDateFrom().toString());
        if(owner.getDateTo() != null) cv.put(OWNER_DATE_TO, owner.getDateTo().toString());
        if(owner.getDescription() != null) cv.put(OWNER_DESCRIPTION, owner.getDescription());
        cv.put(DOG_ID, owner.getDog_id());
        long insert = db.insert(DOGS_OWNER_TABLE, null, cv);
        return insert != -1;
    }

    @Override
    public List<Owner> findAll() {
        String query = "SELECT * FROM " + DOGS_OWNER_TABLE;
        return getOwnersFromDB_byQuery(query);
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
        String query = "DELETE FROM " + DOGS_OWNER_TABLE + " WHERE " + OWNER_ID + " = " + owner.getId();
        Cursor cursor = this.getWritableDatabase().rawQuery(query, null);
        boolean end = cursor.moveToFirst();
        cursor.close();
        return end;
    }

    @Override
    public boolean remove(int id) {
        String query = "DELETE FROM " + DOGS_OWNER_TABLE + " WHERE " + OWNER_ID + " = " + id;
        Cursor cursor = this.getWritableDatabase().rawQuery(query, null);
        boolean end = cursor.moveToFirst();
        cursor.close();
        return end;
    }

    @Override
    public boolean removeAll() {
        String query = "DELETE FROM " + DOGS_OWNER_TABLE;
        Cursor cursor = this.getWritableDatabase().rawQuery(query, null);
        boolean end = cursor.moveToFirst();
        cursor.close();
        return end;
    }

    @Override
    public boolean update(Owner updated_T_Data) {
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
                OWNER_ID + " = " + updated_T_Data.getId();

        Cursor cursor = this.getReadableDatabase().rawQuery(updateTattooQuery, null);
        boolean end = cursor.moveToFirst();
        cursor.close();
        return end;
    }

    @Override
    public List<Owner> getListByMasterId(int id) {
        String query = "SELECT * FROM " + DOGS_OWNER_TABLE + " WHERE " + DOG_ID + " = " + id;
        return getOwnersFromDB_byQuery(query);
    }

    private List<Owner> getOwnersFromDB_byQuery(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Owner> owners = new ArrayList<>();
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

    private Owner getOwnerFromDB(Cursor cursor) {
        Owner owner = new Owner(cursor.getInt(0));
        owner.setName(cursor.getString(1));
        owner.setSurname(cursor.getString(2));
        owner.setAddress(cursor.getString(3));
        owner.setPhoneNumber(cursor.getString(4));
        owner.setEmail(cursor.getString(5));
        try{
            owner.setDateFrom(dateFormat.parse(cursor.getString(6)));
        }catch (ParseException e){
            owner.setDateFrom(new Date());
        }
        try{
            owner.setDateTo(dateFormat.parse(cursor.getString(7)));
        }catch (ParseException e){
            owner.setDateTo(new Date());
        }
        owner.setDescription(cursor.getString(8));
        owner.setDog_id(cursor.getInt(9));
        return owner;
    }

}
