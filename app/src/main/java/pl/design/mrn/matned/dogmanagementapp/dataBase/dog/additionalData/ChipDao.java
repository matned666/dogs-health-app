package pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import pl.design.mrn.matned.dogmanagementapp.dataBase.DaoBase;
import pl.design.mrn.matned.dogmanagementapp.dataBase.DaoFragmentInterface;

import static pl.design.mrn.matned.dogmanagementapp.Statics.*;

public class ChipDao extends DaoBase implements DaoFragmentInterface<Chip> {


    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);


    public ChipDao(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public boolean add(Chip chip) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CHIP_NUMBER, chip.getChipNumber());
        if (chip.getPutDate() != null) cv.put(CHIP_PUT_DATE, dateFormat.format(chip.getPutDate()));
        if (chip.getExpDate() != null) cv.put(CHIP_EXP_DATE, dateFormat.format(chip.getExpDate()));
        if (chip.getChipDescription() != null) cv.put(CHIP_DESCRIPTION, chip.getChipDescription());
        cv.put(DOG_ID, chip.getDogId());
        long insert = db.insert(CHIP_TABLE, null, cv);
        return insert != -1;
    }

    @Override
    public List<Chip> findAll() {
        String query = "SELECT * FROM " + CHIP_TABLE;
        return getChipListByQuery(query);
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
    public boolean remove(Chip chip) {
        String query = "DELETE FROM " + CHIP_TABLE + " WHERE " + CHIP_ID + " = " + chip.getChipId();
        return getCursor(query);
    }

    @Override
    public boolean remove(int id) {
        String query = "DELETE FROM " + CHIP_TABLE + " WHERE " + CHIP_ID + " = " + id;
        return getCursor(query);
    }

    @Override
    public boolean removeAll() {
        String query = "DELETE FROM " + CHIP_TABLE;
        return getCursor(query);
    }

    @Override
    public boolean update(Chip updated_T_Data) {
        String query = "" +
                "UPDATE " + CHIP_TABLE + " SET " +
                CHIP_NUMBER + " = '" + updated_T_Data.getChipNumber() + "'";
        if (updated_T_Data.getPutDate() != null) query += ", " + CHIP_PUT_DATE + " = '" + dateFormat.format(updated_T_Data.getPutDate()) + "'";
        if (updated_T_Data.getExpDate() != null) query += ", " + CHIP_EXP_DATE + " = '" + dateFormat.format(updated_T_Data.getExpDate()) + "'";
        if (updated_T_Data.getChipDescription() != null) query += ", " + CHIP_DESCRIPTION + " = '" + updated_T_Data.getChipDescription() + "'";
        query += ", " + DOG_ID + " = " + updated_T_Data.getDogId() + " " +
                "WHERE " +
                CHIP_ID + " = " + updated_T_Data.getChipId();
        return getCursor(query);
    }

    @Override
    public List<Chip> getListByMasterId(int id) {
        String query = "SELECT * FROM " + CHIP_TABLE + " WHERE " + DOG_ID + " = " + id;
        return getChipListByQuery(query);
    }


    private Chip getChip(Cursor cursor) {
        Chip chip = new Chip(cursor.getInt(0));
        chip.setChipNumber(cursor.getString(1));
        try {
            chip.setPutDate(dateFormat.parse(cursor.getString(2)));
        } catch (Exception e) {
            chip.setPutDate(null);
        }
        try {
            chip.setExpDate(dateFormat.parse(cursor.getString(3)));
        } catch (Exception e) {
            chip.setExpDate(null);
        }
        chip.setChipDescription(cursor.getString(4));
        chip.setDogId(cursor.getInt(5));
        return chip;
    }

    private List<Chip> getChipListByQuery(String query) {
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
}
