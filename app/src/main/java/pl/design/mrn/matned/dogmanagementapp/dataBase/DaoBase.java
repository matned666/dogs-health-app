package pl.design.mrn.matned.dogmanagementapp.dataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static pl.design.mrn.matned.dogmanagementapp.Statics.DATABASE_NAME;

public abstract class DaoBase extends SQLiteOpenHelper {

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public DaoBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    protected boolean getCursor(String query) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        boolean end = cursor.moveToFirst();
        cursor.close();
        return end;
    }
}
