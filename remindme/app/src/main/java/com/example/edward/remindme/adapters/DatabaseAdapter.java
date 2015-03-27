package com.example.edward.remindme.adapters;

/**
 * Created by Edward on 22/03/2015.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.edward.remindme.models.Alarm;
import com.example.edward.remindme.models.Reminder;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAdapter extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "remindDatabase";

    //table names
    private static final String TABLE_REMINDERS = "reminders";
    private static final String TABLE_ALARMS = "alarms";

    //remind column names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";

    //set_alarm column names
    private static final String KEY_ALARMID = "id";
    private static final String KEY_ALARMTITLE = "title";
    private static final String KEY_ALARMTIME = "time";
    private static final String KEY_ALARMINTENTID = "intentID";

    public DatabaseAdapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_REMINDS_TABLE = "CREATE TABLE " + TABLE_REMINDERS + "(" + KEY_ID
                + " INTEGER PRIMARY KEY, " + KEY_TITLE + " TEXT," + KEY_DESCRIPTION + " TEXT)";
        String CREATE_ALARMS_TABLE = "CREATE TABLE " + TABLE_ALARMS + "(" + KEY_ALARMID
                + " INTEGER PRIMARY KEY, " + KEY_ALARMTITLE + " TEXT," + KEY_ALARMTIME + " TEXT, "
                + KEY_ALARMINTENTID + " INTEGER)";
        db.execSQL(CREATE_REMINDS_TABLE);
        db.execSQL(CREATE_ALARMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALARMS);

        onCreate(db);
    }

    //Create, read, upgrade, delete ops for reminders
    //add new
    public void addReminder(Reminder reminder) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, reminder.getTitle());
        values.put(KEY_DESCRIPTION, reminder.getDescription());

        db.insert(TABLE_REMINDERS, null, values);
        db.close();
    }
    //get single
    public Reminder getReminder(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_REMINDERS, new String[] { KEY_ID,
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

        String selectQuery = "SELECT * FROM " + TABLE_REMINDERS + " ORDER BY " + KEY_TITLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Reminder reminder = new Reminder(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2));
                reminderList.add(reminder);
            } while (cursor.moveToNext());
        }

        return reminderList;
    }

    public int updateReminder(Reminder reminder){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, reminder.getTitle());
        values.put(KEY_DESCRIPTION, reminder.getDescription());

        return db.update(TABLE_REMINDERS, values, KEY_ID + " = ?",
                new String[] {String.valueOf(reminder.getId()) });
    }

    public void deleteReminder(Reminder reminder) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_REMINDERS, KEY_ID + " = ?",
                new String[] { String.valueOf(reminder.getId()) });
    }

    //create, read, update, delete alarms
    public void addAlarm(Alarm alarm){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ALARMTITLE, alarm.getTitle());
        values.put(KEY_ALARMTIME, alarm.getAlarmTime());
        values.put(KEY_ALARMINTENTID, alarm.getAlarmIntentID());

        db.insert(TABLE_ALARMS, null, values);
        db.close();
    }

    public Alarm getAlarm(int id) {
        SQLiteDatabase db= this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_ALARMS + " WHERE " + KEY_ALARMID + "= " + id;
        //select * from alarms where id = id
        Cursor cursor = db.rawQuery(selectQuery, null);
        /*Cursor cursor = db.query(TABLE_ALARMS, new String[] { KEY_ALARMID,
            KEY_ALARMTITLE, KEY_ALARMTIME, KEY_ALARMINTENTID }, KEY_ALARMID + "=?",
            new String[] {String.valueOf(id) }, null, null, null, null);*/
        if (cursor != null)
            cursor.moveToFirst();

        Alarm alarm = new Alarm(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getInt(3));
        return alarm;
    }

    public List<Alarm> getAllAlarms() {
        List<Alarm> alarmList = new ArrayList<Alarm>();

        String selectQuery = "SELECT * FROM " + TABLE_ALARMS + " ORDER BY " + KEY_ALARMTITLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Alarm alarm = new Alarm(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getInt(3));
                alarmList.add(alarm);
            } while (cursor.moveToNext());
        }

        return alarmList;
    }

    public void deleteAlarm(Alarm alarm) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ALARMS, KEY_ID + " = ?",
                new String[] { String.valueOf(alarm.getId()) });
    }
}
