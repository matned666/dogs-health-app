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

import pl.design.mrn.matned.dogmanagementapp.dataBase.DaoInterface;

import static pl.design.mrn.matned.dogmanagementapp.Statics.DATE_FORMAT;

public class ChipDao extends SQLiteOpenHelper  implements DaoInterface<Chip> {

    private static final String CHIP_TABLE = "CHIP_TABLE";
    private static final String CHIP_ID = "CHIP_ID";
    private static final String CHIP_NUMBER = "CHIP_NUMBER";
    private static final String CHIP_PUT_DATE = "CHIP_PUT_DATE";
    private static final String CHIP_EXP_DATE = "CHIP_EXP_DATE";
    private static final String CHIP_DESCRIPTION = "CHIP_DESCRIPTION";
    private static final String DOG_ID = "DOG_ID";

    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);


    public ChipDao(@Nullable Context context) {
        super(context, "dogs_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableWhenNotExist = "CREATE TABLE " + CHIP_TABLE + "(" +
                CHIP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CHIP_NUMBER + " TEXT, " +
                CHIP_PUT_DATE + " TEXT, " +
                CHIP_EXP_DATE + " TEXT, " +
                CHIP_DESCRIPTION + " TEXT, " +
                DOG_ID + " INTEGER )";
        db.execSQL(createTableWhenNotExist);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public boolean add(Chip chip) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CHIP_NUMBER, chip.getChipNumber());
        cv.put(CHIP_PUT_DATE, chip.getPutDate().toString());
        cv.put(CHIP_EXP_DATE, chip.getExtDate().toString());
        cv.put(CHIP_DESCRIPTION, chip.getChipDescription());
        cv.put(DOG_ID, chip.getDogId());
        long insert = db.insert(CHIP_TABLE, null, cv);
        return insert != -1;
    }

    @Override
    public List<Chip> findAll() {
        String query = "SELECT * FROM " + CHIP_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        List<Chip> list = new LinkedList<>();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Chip chip = getChip(cursor);
                list.add(chip);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    @Override
    public boolean remove(Chip chip) {
        String query = "DELETE FROM " + CHIP_TABLE + " WHERE " + CHIP_ID + " = " + chip.getChipId();
        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        return cursor.moveToFirst();
    }

    @Override
    public boolean removeAll() {
        String query = "DELETE FROM " + CHIP_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        return cursor.moveToFirst();
    }

    @Override
    public Chip findById(int id) {
        String query = "SELECT * FROM " + CHIP_TABLE + " WHERE " + CHIP_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Chip chip = null;
        if (cursor.moveToFirst()) {
            chip = getChip(cursor);
        }
        cursor.close();
        db.close();
        return chip;
    }

    @Override
    public boolean update(int id_toUpdate, Chip updated_T_Data) {
        String query = "" +
                "UPDATE " + CHIP_TABLE + " SET " +
                CHIP_NUMBER + " = " + updated_T_Data.getChipNumber() + ", " +
                CHIP_PUT_DATE + " = " + dateFormat.format(updated_T_Data.getPutDate()) + ", " +
                CHIP_EXP_DATE + " = " + dateFormat.format(updated_T_Data.getExtDate()) + ", " +
                CHIP_DESCRIPTION + " = " + updated_T_Data.getChipDescription() + ", " +
                DOG_ID + " = " + updated_T_Data.getDogId() + " " +
                "WHERE " +
                CHIP_ID + " = " + updated_T_Data.getChipId();
        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        return cursor.moveToFirst();
    }

    private Chip getChip(Cursor cursor) {
        Chip chip = new Chip(cursor.getInt(0));
        chip.setChipNumber(cursor.getString(1));
        try {
            chip.setPutDate(dateFormat.parse(cursor.getString(2)));
        } catch (ParseException e) {
            chip.setPutDate(new Date());
        }
        try {
            chip.setExtDate(dateFormat.parse(cursor.getString(3)));
        } catch (ParseException e) {
            chip.setExtDate(new Date());
        }
        chip.setChipDescription(cursor.getString(4));
        chip.setDogId(cursor.getInt(5));
        return chip;
    }
}
