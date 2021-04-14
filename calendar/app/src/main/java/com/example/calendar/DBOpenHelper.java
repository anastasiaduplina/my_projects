package com.example.calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBOpenHelper extends SQLiteOpenHelper {
//    private static final String CREATE_EVENT_TABLE= "create table " + DBStructure.EVENT_TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, "
//            + DBStructure.EVENT + " TEXT, "+DBStructure.TIME+ " TEXT, "+DBStructure.DATE+ " TEXT, "+DBStructure.MONTH+ " TEXT, "+DBStructure.YEAR+ " TEXT)" ;
    private static final String CREATE_EVENT_TABLE= "CREATE TABLE IF NOT EXISTS " + DBStructure.EVENT_TABLE_NAME +" ("+ DBStructure.ID +" INTEGER PRIMARY KEY, "
            + DBStructure.EVENT + " TEXT, "+ DBStructure.DATE + " TEXT, " + DBStructure.MONTH + " TEXT, " + DBStructure.YEAR + " TEXT)" ;

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

    public void SaveMood ( String mood,  String date, String month, String year, SQLiteDatabase database){
        ContentValues contentValues= new ContentValues();
        contentValues.put(DBStructure.EVENT, mood);
        contentValues.put(DBStructure.DATE, date);
        contentValues.put(DBStructure.MONTH, month);
        contentValues.put(DBStructure.YEAR, year);
        long status =database.insert(DBStructure.EVENT_TABLE_NAME,null,contentValues);

    }

    public Cursor ReadEvents(String date, SQLiteDatabase database){
        String [] Projections = {DBStructure.EVENT, DBStructure.DATE, DBStructure.MONTH, DBStructure.YEAR};
        String Selection = DBStructure.DATE + "=?";
        String []SelectionArgs={date};
                return  database.query(DBStructure.EVENT_TABLE_NAME, Projections, Selection, SelectionArgs, null, null, null);
    }
    public Cursor ReadEventsperMonth(String month,String year, SQLiteDatabase database){
        String [] Projections = {DBStructure.EVENT, DBStructure.DATE, DBStructure.MONTH, DBStructure.YEAR};
        String Selection = DBStructure.MONTH + "=? and "+ DBStructure.YEAR +"=?";
        String []SelectionArgs={month,year};
        return  database.query(DBStructure.EVENT_TABLE_NAME, Projections, Selection, SelectionArgs, null, null, null);
    }
}
