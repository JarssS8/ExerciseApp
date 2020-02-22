package com.jars.exerciseapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteManager extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Circuit.db";
    private static final String TABLE_NAME_CIRCUIT = "lastPick";

    private SQLiteDatabase sqLiteDatabase = getWritableDatabase();
    public SQLiteManager(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME_CIRCUIT + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, week NUMBER NOT NULL, day NUMBER NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertNewSave(int week,int day) {
        if (day==3 && week<16) {
            day=1;
            week++;
        }else if(week<16){
            day++;
        }
        ContentValues dataContentValues = toContentValues(week,day);
        if(findLastCircuit()==null) {
            sqLiteDatabase.insert(TABLE_NAME_CIRCUIT, null, dataContentValues);
        } else {
            sqLiteDatabase.update(TABLE_NAME_CIRCUIT,dataContentValues,"_id = 1",null);
        }

    }

    private ContentValues toContentValues(int week, int day) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("week",week);
        contentValues.put("day",day);
        return contentValues;
    }

    public ContentValues findLastCircuit(){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues= null;
        int week, day;

        Cursor cursor = db.query(TABLE_NAME_CIRCUIT, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            day = cursor.getInt(cursor.getColumnIndex("day"));
            week = cursor.getInt(cursor.getColumnIndex("week"));
            contentValues = new ContentValues();
            contentValues.put("week",week);
            contentValues.put("day",day);
        }
        return contentValues;
    }
}
