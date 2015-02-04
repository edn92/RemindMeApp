package com.example.remindme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edward on 6/01/2015.
 */
public class DbAdapter extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "remindDatabase";

    private static final String TABLE_REMIND = "remind";

    //table column names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";

    public DbAdapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_REMIND + "(" + KEY_ID
                + " INTEGER PRIMARY KEY, " + KEY_TITLE + " TEXT," + KEY_DESCRIPTION + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMIND);

        onCreate(db);
    }

    //Create, read, upgrade, delete ops
    //add new
    public void addReminder(Reminder reminder) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, reminder.getTitle());
        values.put(KEY_DESCRIPTION, reminder.getDescription());

        db.insert(TABLE_REMIND, null, values);
        db.close();
    }
    //get single
    public Reminder getReminder(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_REMIND, new String[] { KEY_ID,
            KEY_TITLE, KEY_DESCRIPTION }, KEY_ID + "=?",
            new String[] {String.valueOf(id) },null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Reminder reminder = new Reminder(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));

        return reminder;
    }

    //getall
    public List<Reminder> getAllReminders() {
        List<Reminder> reminderList = new ArrayList<Reminder>();

        String selectQuery = "SELECT * FROM " + TABLE_REMIND + " ORDER BY " + KEY_TITLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Reminder reminder = new Reminder();
                reminder.setId(Integer.parseInt(cursor.getString(0)));
                reminder.setTitle(cursor.getString(1));
                reminder.setDescription(cursor.getString(2));

                reminderList.add(reminder);
            } while (cursor.moveToNext());
        }

        return reminderList;
    }

    public int getDatabaseSize() {
        List<Reminder> reminderList = new ArrayList<Reminder>();

        String selectQuery = "SELECT * FROM " + TABLE_REMIND + " ORDER BY " + KEY_TITLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Reminder reminder = new Reminder();
                reminder.setId(Integer.parseInt(cursor.getString(0)));
                reminder.setTitle(cursor.getString(1));
                reminder.setDescription(cursor.getString(2));

                reminderList.add(reminder);
            } while (cursor.moveToNext());
        }

        return reminderList.size();
    }

    public int updateReminder(Reminder reminder){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, reminder.getTitle());
        values.put(KEY_DESCRIPTION, reminder.getDescription());

        return db.update(TABLE_REMIND, values, KEY_ID + " = ?",
                new String[] {String.valueOf(reminder.getId()) });
    }

    public void deleteReminder(Reminder reminder) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_REMIND, KEY_ID + " = ?",
                new String[] { String.valueOf(reminder.getId()) });
    }
 }
