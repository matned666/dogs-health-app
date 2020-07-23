package pl.design.mrn.matned.dogmanagementapp.dataBase.dog;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import pl.design.mrn.matned.dogmanagementapp.dataBase.Sex;

import static pl.design.mrn.matned.dogmanagementapp.Statics.DATE_FORMAT;

public class DogDao extends SQLiteOpenHelper implements DogDaoInterface<DogModel> {

    private static final String DOGS_TABLE = "DOGS";
    private static final String DOG_ID = "DOG_ID";
    private static final String DOG_NAME = "DOG_NAME";
    private static final String DOG_RACE = "DOG_RACE";
    private static final String DOG_BIRTH_DATE = "DOG_BIRTH_DATE";
    private static final String DOG_COLOR = "DOG_COLOR";
    private static final String DOG_SEX = "DOG_SEX";
    private static final String DOG_PHOTO = "DOG_PHOTO";

    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    public DogDao(@Nullable Context context) {
        super(context, "dogs_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableIfNotExists = "CREATE TABLE " + DOGS_TABLE + "(" +
                DOG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DOG_NAME + " TEXT, " +
                DOG_RACE + " TEXT, " +
                DOG_BIRTH_DATE + " TEXT, " +
                DOG_COLOR + " TEXT, " +
                DOG_PHOTO + " TEXT, " +
                DOG_SEX + " TEXT )";

        db.execSQL(createTableIfNotExists);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public boolean add(DogModel dog) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DOG_NAME, dog.getName());
        cv.put(DOG_RACE, dog.getRace());
        cv.put(DOG_BIRTH_DATE, dog.getBirthDate().toString());
        cv.put(DOG_COLOR, dog.getColor());
        cv.put(DOG_PHOTO, dog.getDogImage());
        cv.put(DOG_SEX, dog.getSex().name());
        long insert = db.insert(DOGS_TABLE, null, cv);
        return insert != -1;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public List<DogModel> findAll() {
        List<DogModel> dogs = new ArrayList<>();
        String query = "SELECT * FROM " + DOGS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                DogModel dogModel = getDogModel(cursor);
                dogs.add(dogModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return dogs;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public DogModel findById(int id_dogToFind) {
        DogModel dog;
        String query = "SELECT * FROM " + DOGS_TABLE + " WHERE " + DOG_ID + " = " + id_dogToFind;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            dog = getDogModel(cursor);
        } else {
            dog = null;
        }
        cursor.close();
        db.close();
        return dog;
    }

    @Override
    public int findFirstRecordId() {
        String query = "SELECT * FROM " + DOGS_TABLE + " ORDER BY " + DOG_ID + " ASC LIMIT 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int dogId;

        if (cursor.moveToFirst()) {
            dogId = cursor.getInt(0);
        } else {
            dogId = 0;
        }
        cursor.close();
        db.close();
        return dogId;
    }

    @Override
    public boolean remove(DogModel dog) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + DOGS_TABLE + " WHERE " + DOG_ID + " = " + dog.getId();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        return cursor.moveToFirst();
    }

    @Override
    public boolean removeAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + DOGS_TABLE;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        return cursor.moveToFirst();
    }

    @Override
    public boolean update(int id_dogToUpdate, DogModel updatedDogData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DOG_NAME, updatedDogData.getName());
        cv.put(DOG_RACE, updatedDogData.getRace());
        cv.put(DOG_BIRTH_DATE, updatedDogData.getBirthDate().toString());
        cv.put(DOG_COLOR, updatedDogData.getColor());
        cv.put(DOG_PHOTO, updatedDogData.getDogImage());
        cv.put(DOG_SEX, updatedDogData.getSex().name());
        long insert = db.update(DOGS_TABLE, cv, (DOG_ID + " = " + id_dogToUpdate), null);
        return insert != -1;
    }



    private DogModel getDogModel(Cursor cursor) {
        int dogId = cursor.getInt(0);
        String dogName = cursor.getString(1);
        String dogRace = cursor.getString(2);
        Date dogBirthDate;
        try {
            dogBirthDate = dateFormat.parse(cursor.getString(3));
        } catch (ParseException e) {
            dogBirthDate = new Date();
        }
        String dogColor = cursor.getString(4);
        String dogPhoto = cursor.getString(5);
        Sex dogSex = Sex.valueOf(cursor.getString(6));
        DogModel dogModel = new DogModel(dogId, dogName, dogRace, dogBirthDate, dogColor, dogSex);
        dogModel.setDogImage(dogPhoto);
        return dogModel;
    }

}

