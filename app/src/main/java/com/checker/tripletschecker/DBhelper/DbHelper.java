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

    private static final String TABLE_NAME_TWINS = "Twin_Data";
    private static final String TABLE_NAME_TRIPLETS = "Triplets_Data";

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
        if(this.activity == "twins") {
            String Query_Table = " CREATE TABLE " + TABLE_NAME_TWINS + "(" +
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

        } else if(this.activity == "triplets"){
            String Query_Table = " CREATE TABLE " + TABLE_NAME_TRIPLETS + "(" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_DATE + " TEXT, " +
                    KEY_COUNT1 + " TEXT, " +
                    KEY_COUNT2 + " TEXT, " +
                    KEY_COUNT3 + " TEXT, " +
                    KEY_START_TIME + " TEXT, " +
                    KEY_END_TIME1 + " TEXT, " +
                    KEY_END_TIME2 + " TEXT, " +
                    KEY_END_TIME3 + " TEXT, " +
                    KEY_DURATION1 + " TEXT, " +
                    KEY_DURATION2 + " TEXT, " +
                    KEY_DURATION3 + " TEXT)";
            db.execSQL(Query_Table);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(this.activity == "twins") {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TWINS);
            onCreate(db);
        } else if(this.activity == "triplets"){
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TRIPLETS);
            onCreate(db);
        }
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
        return db.insert(TABLE_NAME_TWINS, null, values);
    }

    public long insertData(String date, String count1, String count2, String count3, String start_time, String end_time1, String end_time2, String end_time3, String duration1, String duration2, String duration3) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATE, date);
        values.put(KEY_COUNT1, count1);
        values.put(KEY_COUNT2, count2);
        values.put(KEY_COUNT3, count3);
        values.put(KEY_START_TIME, start_time);
        values.put(KEY_END_TIME1, end_time1);
        values.put(KEY_END_TIME2, end_time2);
        values.put(KEY_END_TIME3, end_time3);
        values.put(KEY_DURATION1, duration1);
        values.put(KEY_DURATION2, duration2);
        values.put(KEY_DURATION3, duration3);
        return db.insert(TABLE_NAME_TRIPLETS, null, values);
    }

    public Cursor viewData() {
        SQLiteDatabase db = this.getReadableDatabase();
        if(this.activity == "twins"){
            String query = "Select * from " + TABLE_NAME_TWINS;
            Cursor cursor = db.rawQuery(query, null);
            return cursor;
        }else if(this.activity == "triplets"){
            String query = "Select * from " + TABLE_NAME_TRIPLETS;
            Cursor cursor = db.rawQuery(query, null);
            return cursor;
        }
        return null;
    }
}
