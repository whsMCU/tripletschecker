package com.checker.tripletschecker.DBhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "database.db";
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

    String activity;


    public DbHelper(@Nullable Context context, String activity) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.activity = activity;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String Query_Table = " CREATE TABLE " + TABLE_NAME + "(" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_DATE + " TEXT, " +
                KEY_COUNT1 + " TEXT, " +
                KEY_COUNT2 + " TEXT, " +
                KEY_START_TIME + " TEXT, " +
                KEY_END_TIME1 + " TEXT, " +
                KEY_END_TIME2 + " TEXT, " +
                KEY_DURATION1 + " TEXT, " +
                KEY_DURATION2 + " TEXT)";

        db.execSQL(Query_Table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long insertData(String date, String count1, String count2, String start_time, String end_time1, String end_time2, String duration1, String duration2) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATE, date);
        values.put(KEY_COUNT1, count1);
        values.put(KEY_COUNT2, count2);
        values.put(KEY_START_TIME, start_time);
        values.put(KEY_END_TIME1, end_time1);
        values.put(KEY_END_TIME2, end_time2);
        values.put(KEY_DURATION1, duration1);
        values.put(KEY_DURATION2, duration2);
        return db.insert(TABLE_NAME, null, values);
    }

    public String[] getData(long l) {
        String[] data = new String[5];
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = new String[]{KEY_DATE, KEY_COUNT1, KEY_START_TIME, KEY_END_TIME1, KEY_DURATION1};
        Cursor cursor = db.query(TABLE_NAME, columns, KEY_ID+"="+l,null,null,null,null);
        if(columns !=null) {
            cursor.moveToFirst();
            data[0] = cursor.getString(1);
            data[1] = cursor.getString(2);
            data[2] = cursor.getString(3);
            data[3] = cursor.getString(4);
            data[4] = cursor.getString(5);
        }
        return data;
    }
}
