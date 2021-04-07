package com.example.calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static com.example.calendar.CustomCalendarView.db;

public class DBmood extends SQLiteOpenHelper {
    private  static  final String DATABASE_NAME=DBStructure.DB_NAME;
    private static final int VERSION=DBStructure.DB_VERSION;
    static final String TABLE_NAME= DBStructure.MOOD_TABLE_NAME;
    public static final String COLUMN_ID=DBStructure.ID;
    public static final String COLUMN_MOOD= DBStructure.MOOD;
    public static final String COLUMN_DATE=DBStructure.DATE;
    public static final String COLUMN_MONTH= DBStructure.MONTH;
    public static final String COLUMN_YEAR = DBStructure.YEAR;

    public DBmood(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //super.onCreate(savedInstanceState);
        db.execSQL("CREATE TABLE " +TABLE_NAME +"("+ COLUMN_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_MOOD + " TEXT, "+COLUMN_DATE+ " TEXT, "+ COLUMN_MONTH+ " TEXT, "+ COLUMN_YEAR+ " TEXT)" );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public void SaveMood ( String mood,  String date, String month, String year, SQLiteDatabase database){
        ContentValues values= new ContentValues();
        values.put(COLUMN_MOOD, mood);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_MONTH, month);
        values.put(COLUMN_YEAR, year);
        database.insert(TABLE_NAME,null, values);
    }

}
