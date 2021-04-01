package com.checker.tripletschecker.DBhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "_count_data";

    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "date";
    private static final String KEY_COUNT1 = "count1";
    private static final String KEY_COUNT2 = "count2";
    private static final String KEY_COUNT3 = "count3";
    private static final String KEY_COUNT4 = "count4";
    private static final String KEY_START_TIME = "start_time";
    private static final String KEY_END_TIME1 = "end_time1";
    private static final String KEY_END_TIME2 = "end_time2";
    private static final String KEY_END_TIME3 = "end_time3";
    private static final String KEY_END_TIME4 = "end_time4";
    private static final String KEY_DURATION1 = "duration1";
    private static final String KEY_DURATION2 = "duration2";
    private static final String KEY_DURATION3 = "duration3";
    private static final String KEY_DURATION4 = "duration4";


    public DbHelper(@Nullable Context context, @Nullable String name) {
        super(context, name, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String Query_Table = " CREATE TABLE " + TABLE_NAME + "(" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_DATE + " TEXT, " +
                KEY_COUNT1 + "TEXT, " +
                KEY_START_TIME + "TEXT, " +
                KEY_END_TIME1 + "TEXT, " +
                KEY_DURATION1 + "TEXT);";
//        String Query_Table = " CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//                + KEY_DATE + " TEXT, " + KEY_COUNT1 + "TEXT, " + KEY_COUNT2 + "TEXT, " + KEY_COUNT3 + "TEXT, " + KEY_COUNT4 + "TEXT, "
//                + KEY_START_TIME + "TEXT, " + KEY_END_TIME1 + "TEXT, " + KEY_END_TIME2 + "TEXT, " + KEY_END_TIME3 + "TEXT, " + KEY_END_TIME4 + "TEXT, "
//                + KEY_DURATION1 + "TEXT, " + KEY_DURATION2 + "TEXT, " + KEY_DURATION3 + "TEXT, " + KEY_DURATION4 + "TEXT);";

        db.execSQL(Query_Table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long insertData(String date, String count1, String start_time, String end_time, String duration1) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATE, date);
        values.put(KEY_COUNT1, count1);
        values.put(KEY_START_TIME, start_time);
        values.put(KEY_END_TIME1, end_time);
        values.put(KEY_DURATION1, duration1);
        return db.insert(TABLE_NAME, null, values);
    }
}
