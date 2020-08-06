package pl.design.mrn.matned.dogmanagementapp.dataBase.app;

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
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import pl.design.mrn.matned.dogmanagementapp.dataBase.DaoInterface;
import pl.design.mrn.matned.dogmanagementapp.dataBase.DaoMessageInterface;

import static pl.design.mrn.matned.dogmanagementapp.Statics.DATABASE_NAME;
import static pl.design.mrn.matned.dogmanagementapp.Statics.DATE_FORMAT_MESSAGE;
import static pl.design.mrn.matned.dogmanagementapp.Statics.MESSAGE;
import static pl.design.mrn.matned.dogmanagementapp.Statics.MESSAGE_DATE_TIME;
import static pl.design.mrn.matned.dogmanagementapp.Statics.MESSAGE_ID;
import static pl.design.mrn.matned.dogmanagementapp.Statics.MESSAGE_STATUS;
import static pl.design.mrn.matned.dogmanagementapp.Statics.MESSAGE_SUBJECT;
import static pl.design.mrn.matned.dogmanagementapp.Statics.MESSAGE_TABLE;

public class MessagesDao extends SQLiteOpenHelper implements DaoMessageInterface<Message> {

    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_MESSAGE);

    public MessagesDao(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    @Override
    public boolean add(Message message) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(MESSAGE, message.getMessage());
        cv.put(MESSAGE_SUBJECT, message.getSubject().name());
        cv.put(MESSAGE_DATE_TIME, dateFormat.format(message.getMessageDateTime()));
        cv.put(MESSAGE_STATUS, message.getStatus().name());
        long insert = db.insert(MESSAGE_TABLE, null, cv);
        return insert != -1;
    }

    @Override
    public List<Message> findAll() {
        String query = "SELECT * FROM " + MESSAGE_TABLE;
        return getChipListByQuery(query);    }

    @Override
    public Message findById(int id) {
        String query = "SELECT * FROM " + MESSAGE_TABLE + " WHERE " + MESSAGE_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Message message = null;
        if (cursor.moveToFirst()) {
            message = getMessage(cursor);
        }
        cursor.close();
        db.close();
        return message;    }

    @Override
    public boolean remove(Message message) {
        String query = "DELETE FROM " + MESSAGE_TABLE + " WHERE " + MESSAGE_ID + " = " + message.getId();
        return getCursor(query);
    }

    @Override
    public boolean removeAll() {
        String query = "DELETE FROM " + MESSAGE_TABLE;
        return getCursor(query);    }

    @Override
    public boolean update(Message updated_T_Data) {
        String query = "" +
                "UPDATE " + MESSAGE_TABLE + " SET " +
                MESSAGE_SUBJECT + " = '" + updated_T_Data.getSubject().name() + "', " +
                MESSAGE + " = '" + updated_T_Data.getMessage() + "', " +
                MESSAGE_STATUS + " = '" + updated_T_Data.getStatus().name() + "' " +
                "WHERE " +
                MESSAGE_ID + " = " + updated_T_Data.getId();
        return getCursor(query);
    }

    @Override
    public boolean isWelcomeMessageSent() {
            String query = "SELECT * FROM " + MESSAGE_TABLE;
            List temp = getChipListByQuery(query);
        return temp.size() > 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean isAnyUnreadMessage() {
        String query = "SELECT * FROM " + MESSAGE_TABLE;
        return getChipListByQuery(query).stream()
                .anyMatch(v -> v.getStatus() == MessageStatus.NOT_READ);
    }

    private List<Message> getChipListByQuery(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Message> list = new LinkedList<>();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Message message = getMessage(cursor);
                list.add(message);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    private boolean getCursor(String query) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        boolean end = cursor.moveToFirst();
        cursor.close();
        return end;
    }

    private Message getMessage(Cursor cursor) {
        Message message = new Message();
        message.setId(cursor.getInt(0));
        message.setSubject(MessageSubject.valueOf(cursor.getString(1)));
        message.setMessage(cursor.getString(2));
        try {
            message.setMessageDateTime(dateFormat.parse(cursor.getString(3)));
        } catch (Exception e) {
            message.setMessageDateTime(null);
        }
        message.setStatus(MessageStatus.valueOf(cursor.getString(4)));

        return message;
    }
}
