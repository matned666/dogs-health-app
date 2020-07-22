package pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.LinkedList;
import java.util.List;

import pl.design.mrn.matned.dogmanagementapp.dataBase.DaoInterface;

public class NoteDao extends SQLiteOpenHelper implements DaoInterface<Note> {

    private static final String NOTES_TABLE = "NOTES_TABLE" ;
    private static final String NOTE_ID = "NOTE_ID" ;
    private static final String NOTE = "NOTE" ;
    private static final String DOG_ID = "DOG_ID" ;

    public NoteDao(@Nullable Context context) {
        super(context, "dogs_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableWhenNotExist = "CREATE TABLE " + NOTES_TABLE + "(" +
                NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NOTE + " TEXT, " +
                DOG_ID + " INTEGER )";
        db.execSQL(createTableWhenNotExist);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public boolean add(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NOTE, note.getNote());
        cv.put(DOG_ID, note.getDogId());
        long insert = db.insert(NOTES_TABLE, null, cv);
        return insert != -1;
    }

    @Override
    public List<Note> findAll() {
        String query = "SELECT * FROM " + NOTES_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        List<Note> list = new LinkedList<>();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do{
                Note note = getNote(cursor);
                list.add(note);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    @Override
    public boolean remove(Note note) {
        String query = "DELETE FROM " + NOTES_TABLE + " WHERE " + NOTE_ID + " = " + note.getNoteId();
        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        return cursor.moveToFirst();
    }

    @Override
    public boolean removeAll() {
        String query = "DELETE FROM " + NOTES_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        return cursor.moveToFirst();
    }

    @Override
    public Note findById(int id) {
        String query = "SELECT * FROM " + NOTES_TABLE + " WHERE " + NOTE_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Note note = null;
        if (cursor.moveToFirst()) {
            note = getNote(cursor);
        }
        cursor.close();
        db.close();
        return note;
    }

    @Override
    public boolean update(int id_toUpdate, Note updated_T_Data) {
        String query = "" +
                "UPDATE " + NOTES_TABLE + " SET " +
                NOTE + " = " + updated_T_Data.getNote() + ", " +
                DOG_ID + " = " + updated_T_Data.getDogId() + " " +
                "WHERE " +
                NOTE_ID + " = " + updated_T_Data.getNoteId();
        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        return cursor.moveToFirst();
    }

    private Note getNote(Cursor cursor) {
        Note note = new Note(cursor.getInt(0));
        note.setNote(cursor.getString(1));
        note.setDogId(cursor.getInt(2));
        return note;
    }
}
