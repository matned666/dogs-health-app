package pl.design.mrn.matned.dogmanagementapp.dataBase.app;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pl.design.mrn.matned.dogmanagementapp.dataBase.DaoBase;

import static pl.design.mrn.matned.dogmanagementapp.Statics.*;

public class ConfigurationMessageDao extends DaoBase implements ConfigureMessage<ConfigurationMessageModel> {

    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public ConfigurationMessageDao(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public ConfigurationMessageModel get(MessageSubject subject, Date date, int masterId) {
        String query = "" +
                "SELECT * FROM " + CONFIGURATION_MESSAGE_TABLE + " " +
                "WHERE " +
                CONFIGURATION_MESSAGE_SUBJECT + " = " + subject.name() + " " +
                "AND " +
                CONFIGURATION_MASTER_ID + " = " + masterId + " " +
                "AND " +
                CONFIGURATION_MESSAGE_DATE + " = " + dateFormat.format(date);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ConfigurationMessageModel configurationMessageModel = null;
        if (cursor.moveToFirst()) {
            configurationMessageModel = getConfiguration(cursor);
        }
        cursor.close();
        db.close();
        return configurationMessageModel;
    }

    @Override
    public List<ConfigurationMessageModel> findAll() {
        List<ConfigurationMessageModel> configurationMessageModels = new ArrayList<>();
        String query = "SELECT * FROM " + CONFIGURATION_MESSAGE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                ConfigurationMessageModel configurationMessageModel = getConfiguration(cursor);
                configurationMessageModels.add(configurationMessageModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return configurationMessageModels;
    }

    @Override
    public boolean removeAll() {
        String query = "DELETE FROM " + CONFIGURATION_MESSAGE_TABLE;
        return getCursor(query);      }

    @Override
    public boolean removeByDate(Date date) {
        String query = "" +
                "DELETE FROM " + CONFIGURATION_MESSAGE_TABLE + " " +
                "WHERE " + CONFIGURATION_MESSAGE_DATE + " = " + dateFormat.format(date);
        return getCursor(query);      }

    @Override
    public boolean create(ConfigurationMessageModel configurationMessageModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CONFIGURATION_MESSAGE_SUBJECT, configurationMessageModel.getSubject().name());
        cv.put(CONFIGURATION_MESSAGE_DATE, dateFormat.format(configurationMessageModel.getAutidDate()));
        cv.put(CONFIGURATION_MASTER_ID, configurationMessageModel.getMasterId());
        long insert = db.insert(CONFIGURATION_MESSAGE_TABLE, null, cv);
        return insert != -1;    }

    @Override
    public boolean update(ConfigurationMessageModel configurationMessageModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CONFIGURATION_MESSAGE_SUBJECT, configurationMessageModel.getSubject().name());
        cv.put(CONFIGURATION_MESSAGE_DATE, dateFormat.format(configurationMessageModel.getAutidDate()));
        cv.put(CONFIGURATION_MASTER_ID, configurationMessageModel.getMasterId());
        long update = db.update(CONFIGURATION_MESSAGE_TABLE, cv, (CONFIGURATION_MESSAGE_ID + " = " + configurationMessageModel.getId()),null);
        return update != -1;
    }

    private ConfigurationMessageModel getConfiguration(Cursor cursor) {
        ConfigurationMessageModel configurationMessageModel = new ConfigurationMessageModel();
        configurationMessageModel.setId(cursor.getInt(0));
        configurationMessageModel.setSubject(MessageSubject.valueOf(cursor.getString(1)));
        try {
            configurationMessageModel.setAutidDate(dateFormat.parse(cursor.getString(2)));
        } catch (ParseException ignored) {
        }
        configurationMessageModel.setMasterId(cursor.getInt(3));
        return configurationMessageModel;
    }
}
