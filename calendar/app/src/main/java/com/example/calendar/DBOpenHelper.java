package com.example.calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBOpenHelper extends SQLiteOpenHelper {
//    private static final String CREATE_EVENT_TABLE= "create table " + DBStructure.EVENT_TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, "
//            + DBStructure.EVENT + " TEXT, "+DBStructure.TIME+ " TEXT, "+DBStructure.DATE+ " TEXT, "+DBStructure.MONTH+ " TEXT, "+DBStructure.YEAR+ " TEXT)" ;
    private static final String CREATE_EVENT_TABLE= "create table " + DBStructure.EVENT_TABLE_NAME + DBStructure.ID +"(ID INTEGER PRIMARY KEY , "
            + DBStructure.EVENT + " TEXT, "+DBStructure.DATE+ " TEXT, "+DBStructure.MONTH+ " TEXT, "+DBStructure.YEAR+ " TEXT)" ;

    private static final String DROP_EVENTS_TABLE="DROP TABLE IF EXISTS "+ DBStructure.EVENT_TABLE_NAME;

    public DBOpenHelper(@Nullable Context context) {
        super(context,DBStructure.DB_NAME, null, DBStructure.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_EVENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_EVENTS_TABLE);
        onCreate(db);
    }
//    public void SaveEvent (String event, String time, String date, String month, String year, SQLiteDatabase database){
//        ContentValues contentValues= new ContentValues();
//        contentValues.put(DBStructure.EVENT, event);
//        contentValues.put(DBStructure.TIME, time);
//        contentValues.put(DBStructure.DATE, date);
//        contentValues.put(DBStructure.MONTH, month);
//        contentValues.put(DBStructure.YEAR, year);
//       database.insert(DBStructure.EVENT_TABLE_NAME, null, contentValues);
//    }

    public void SaveMood ( String mood,  String date, String month, String year, SQLiteDatabase database){
        ContentValues contentValues= new ContentValues();

        contentValues.put(DBStructure.EVENT, mood);
        contentValues.put(DBStructure.DATE, date);
        contentValues.put(DBStructure.MONTH, month);
        contentValues.put(DBStructure.YEAR, year);
        database.insert(DBStructure.EVENT_TABLE_NAME, null, contentValues);
    }
    public void DeleteMood (String id,String mood,  String date, String month, String year, SQLiteDatabase database){
        database.delete(DBStructure.EVENT_TABLE_NAME, DBStructure.ID +" = " + id, null);
        database.delete(DBStructure.EVENT_TABLE_NAME, DBStructure.EVENT +" = " + mood, null);
        database.delete(DBStructure.EVENT_TABLE_NAME, DBStructure.MONTH +" = " + month, null);
        database.delete(DBStructure.EVENT_TABLE_NAME, DBStructure.YEAR +" = " + year, null);
        database.delete(DBStructure.EVENT_TABLE_NAME, DBStructure.DATE +" = " + date, null);
    }

    public Cursor ReadEvents(String date, SQLiteDatabase database){
        String [] Projections = {DBStructure.EVENT, DBStructure.TIME,DBStructure.DATE, DBStructure.MONTH, DBStructure.YEAR};
        String Selection = DBStructure.DATE + "=?";
        String []SelectionArgs={date};
                return  database.query(DBStructure.EVENT_TABLE_NAME, Projections, Selection, SelectionArgs, null, null, null);
    }
    public Cursor ReadEventsperMonth(String month,String year, SQLiteDatabase database){
        String [] Projections = {DBStructure.EVENT, DBStructure.TIME,DBStructure.DATE, DBStructure.MONTH, DBStructure.YEAR};
        String Selection = DBStructure.MONTH + "=? and "+ DBStructure.YEAR +"=?";
        String []SelectionArgs={month,year};
        return  database.query(DBStructure.EVENT_TABLE_NAME, Projections, Selection, SelectionArgs, null, null, null);
    }
}
