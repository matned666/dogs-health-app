package pl.design.mrn.matned.dogmanagementapp.dataBase.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import pl.design.mrn.matned.dogmanagementapp.Statics;
import pl.design.mrn.matned.dogmanagementapp.dataBase.DaoBase;

import static pl.design.mrn.matned.dogmanagementapp.Statics.*;

public class ConfigurationDao extends DaoBase implements ConfigurationImp<Configuration> {

    public ConfigurationDao(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        if (get() == null) {
            Configuration defaultConfiguration = Statics.defaultConfiguration();
            create(defaultConfiguration);
        }
    }

    @Override
    public boolean create(Configuration configuration) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CONFIGURATION_ALERT_VACCINE, (int) configuration.getIsVaccineNotificationOn());
        cv.put(CONFIGURATION_ALERT_BIRTHDAY, (int) configuration.getIsBirthdayNotificationOn());
        cv.put(CONFIGURATION_ALERT_DEWORMING, (int) configuration.getIsDewormingNotificationOn());
        cv.put(CONFIGURATION_ALERT_OTHER, (int) configuration.getIsOtherNotificationOn());
        cv.put(CONFIGURATION_IS_WELCOME_SENT, (int) configuration.getIsWelcomeMessageSent());
        long insert = db.insert(CONFIGURATION_TABLE, null, cv);
        return insert != -1;
    }

    @Override
    public Configuration get() {
        String query = "SELECT * FROM " + CONFIGURATION_TABLE + " WHERE " + CONFIGURATION_ID + " = 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Configuration configuration = null;
        if (cursor.moveToFirst()) {
            configuration = getConfiguration(cursor);
        }
        cursor.close();
        db.close();
        return configuration;    }

    @Override
    public boolean update(Configuration configuration) {
        String query = "" +
                "UPDATE " + CONFIGURATION_TABLE + " SET " +
                CONFIGURATION_ALERT_VACCINE + " = " + (int) configuration.getIsVaccineNotificationOn() + ", " +
                CONFIGURATION_ALERT_BIRTHDAY + " = " + (int) configuration.getIsBirthdayNotificationOn() + ", " +
                CONFIGURATION_ALERT_DEWORMING + " = " + (int) configuration.getIsDewormingNotificationOn() + ", " +
                CONFIGURATION_ALERT_OTHER + " = " + (int) configuration.getIsOtherNotificationOn() + ", " +
                CONFIGURATION_IS_WELCOME_SENT + " = " + (int) configuration.getIsWelcomeMessageSent() + " " +
                "WHERE " +
                CONFIGURATION_ID + " = 1";
        return getCursor(query);
    }

    private Configuration getConfiguration(Cursor cursor) {
        Configuration configuration = new Configuration();
        configuration.setId((byte) 1);
        configuration.setIsVaccineNotificationOn((byte) cursor.getInt(1));
        configuration.setIsBirthdayNotificationOn((byte) cursor.getInt(2));
        configuration.setIsDewormingNotificationOn((byte) cursor.getInt(3));
        configuration.setIsOtherNotificationOn((byte) cursor.getInt(4));
        configuration.setIsWelcomeMessageSent((byte) cursor.getInt(5));
        return configuration;
    }
}
