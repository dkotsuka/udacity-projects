package com.example.android.habittracking.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.habittracking.data.HabitsContract.HabitsEntry;

/**
 * Created by dkots on 08/12/2017.
 */

public class HabitsDbHelper extends SQLiteOpenHelper {

    public final static String DATABASE_NAME = "habits.db";
    public final static int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public HabitsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_HABITS_TABLE = "CREATE TABLE " + HabitsEntry.TABLE_NAME + " ("
                + HabitsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HabitsEntry.COLUMN_REGISTRATION_DATE + " INTEGER NOT NULL, "
                + HabitsEntry.COLUMN_HAD_STUDIED + " INTEGER NOT NULL DEFAULT 0, "
                + HabitsEntry.COLUMN_STUDY_HOURS + " INTEGER,"
                + HabitsEntry.COLUMN_STUDY_SUBJECT + " TEXT);";
        db.execSQL(SQL_CREATE_HABITS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long insert(int date, int didStudy, int
                        studyHours, String subject){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HabitsEntry.COLUMN_REGISTRATION_DATE, date);
        values.put(HabitsEntry.COLUMN_HAD_STUDIED, didStudy);
        values.put(HabitsEntry.COLUMN_STUDY_HOURS, studyHours);
        values.put(HabitsEntry.COLUMN_STUDY_SUBJECT, subject);

        return db.insert(HabitsEntry.TABLE_NAME, null, values);
    }

    public Cursor query() {

        db = this.getReadableDatabase();

        String[] projection = {
                HabitsEntry._ID,
                HabitsEntry.COLUMN_REGISTRATION_DATE,
                HabitsEntry.COLUMN_HAD_STUDIED,
                HabitsEntry.COLUMN_STUDY_HOURS,
                HabitsEntry.COLUMN_STUDY_SUBJECT
        };
        return db.query(HabitsEntry.TABLE_NAME,projection,null,null,null,null,null);
    }
}
