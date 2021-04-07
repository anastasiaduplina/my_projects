package com.example.calendar;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class CustomCalendarView extends LinearLayout {
    ImageButton NextButton, PreviousButton;
    Button DeleteButton;
    TextView CurrentDate;
    GridView gridView;
    private static final int MAX_CALENDAR_DAYS= 42;
    Calendar calendar=Calendar.getInstance(Locale.ENGLISH);
    Context context;
    SimpleDateFormat eventDateFormat= new SimpleDateFormat("MMMM dd yyyy", Locale.ENGLISH);
    SimpleDateFormat dateFormat= new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
    SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.ENGLISH);
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy",Locale.ENGLISH);

    MyGridAdapter myGridAdapter;
    AlertDialog alertDialog;
    List<Date> dates = new ArrayList<>();
    List <Events> eventsList= new ArrayList<>();
    public static SQLiteDatabase db;
    public static DBOpenHelper dbOpenHelper;
    public static  DBmood dbmood;

    public long kolvo(){
        dbOpenHelper= new DBOpenHelper(context);
        dbOpenHelper.onOpen(db);
        String query = "select count(*) from eventstable";
        db = dbOpenHelper.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        long  num= DatabaseUtils.longForQuery(db,"select count (*) from eventstable" ,null);
        c.close();
        Log.i("kolvo",num+"");
        return num;

    }
    public long Kolvo(){
        DBmood dbmood= new DBmood(context);
        dbmood.onOpen(db);
        //String query = "select count(*) from moodtable";
        db = dbmood.getReadableDatabase();
        Cursor c = db.rawQuery("select * from eventstable" , null);
        int numRows= c.getColumnCount();
        c.close();
        //long numRows = DatabaseUtils.longForQuery(database, "SELECT COUNT(*) FROM moodtable", null);
        Log.i("num", numRows+"");
        return numRows;
    }

    public CustomCalendarView(Context context) {
        super(context);
    }

    public CustomCalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context= context;
        IntializeLaoyut();
        SetUpCalendar();
        PreviousButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH, -1);
                SetUpCalendar();

            }
        });
        NextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH, 1);
                SetUpCalendar();

            }   });
        DeleteButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DBOpenHelper dbOpenHelper= new DBOpenHelper(context);
                SQLiteDatabase database= dbOpenHelper.getWritableDatabase();
                database.delete(DBStructure.EVENT_TABLE_NAME, null,null);
                SetUpCalendar();
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                                AlertDialog.Builder builder= new AlertDialog.Builder(context);
                                                builder.setCancelable(true);
                                                View addView = LayoutInflater.from(parent.getContext()).inflate(R.layout.choose_color,null);
                                                Button one= addView.findViewById(R.id.button1);
                                                Button two= addView.findViewById(R.id.button2);
                                                Button tree= addView.findViewById(R.id.button3);
                                                Button close= addView.findViewById(R.id.close);
                                                Button delete= addView.findViewById(R.id.deleteall);
                                                Button save= addView.findViewById(R.id.save);
                                                final String date= eventDateFormat.format(dates.get(position));
                                                final String month = monthFormat.format(dates.get(position));
                                                final String year = yearFormat.format(dates.get(position));
                                                final String[] color = new String[1];
                                                color[0]="green";
                                                one.setOnClickListener(new OnClickListener() {

                                                    @Override
                                                    public void onClick(View v) {
                                                        view.setBackgroundColor(getContext().getResources().getColor(R.color.purple_500));
                                                        color[0] = "500";
                                                    }
                                                });
                                                two.setOnClickListener(new OnClickListener() {

                                                    @Override
                                                    public void onClick(View v) {
                                                            view.setBackgroundColor(getContext().getResources().getColor(R.color.purple_200));
                                                        color[0] = "200";
                                                    }
                                                });
                                                tree.setOnClickListener(new OnClickListener() {

                                                    @Override
                                                    public void onClick(View v) {
                                                            view.setBackgroundColor(getContext().getResources().getColor(R.color.purple_700));
                                                        color[0] = "700";
                                                    }
                                                });
                                                close.setOnClickListener(new OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        alertDialog.dismiss();
                                                        SetUpCalendar();
                                                    }
                                                });
                                                delete.setOnClickListener(new OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        DeleteMood(Long.toString(kolvo()),color[0], date,month,year);
                                                        SetUpCalendar();
                                                    }
                                                });
                                                save.setOnClickListener(new OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        SaveMood(color[0], date,month,year);
                                                        long h =kolvo();
                                                        SetUpCalendar();
                                                    }
                                                });
                                                builder.setView(addView);
                                                alertDialog= builder.create();
                                                alertDialog.show();
                                            }
                                        });
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                AlertDialog.Builder builder= new AlertDialog.Builder(context);
//                builder.setCancelable(true);
//                View addView = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_newevent_layout,null);
//                EditText EventName = addView.findViewById(R.id.eventName);
//                TextView EventTime = addView.findViewById(R.id.eventtime);
//                ImageButton SetTime = addView.findViewById(R.id.seteventtime);
//                Button AddEvent = addView.findViewById(R.id.addevent);
//                SetTime.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Calendar calendar= Calendar.getInstance();
//                        int hours = calendar.get(Calendar.HOUR_OF_DAY);
//                        int minuts= calendar.get(Calendar.MINUTE);
//                        TimePickerDialog timePickerDialog= new TimePickerDialog(addView.getContext(), R.style.Theme_AppCompat_Dialog,
//                                new TimePickerDialog.OnTimeSetListener() {
//                                    @Override
//                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                                        Calendar c= Calendar.getInstance();
//                                        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
//                                        c.set(Calendar.MINUTE,minute);
//                                        c.setTimeZone(TimeZone.getDefault());
//                                        SimpleDateFormat hformate = new SimpleDateFormat("K:mm a",Locale.ENGLISH);
//                                        String event_Time= hformate.format(c.getTime());
//                                        EventTime.setText(event_Time);
//                                    }
//                                },hours, minuts, false);
//                        timePickerDialog.show();
//
//
//                    }
//                });
//                final String date= eventDateFormat.format(dates.get(position));
//                final String month = monthFormat.format(dates.get(position));
//                final String year = yearFormat.format(dates.get(position));
//                AddEvent.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        SaveEvent(EventName.getText().toString(), EventTime.getText().toString(),date,month,year);
//
//                        //Log.i("ddate", date+"");
//                        SetUpCalendar();
//                        alertDialog.dismiss();
//                    }
//                });
//                builder.setView(addView);
//                alertDialog= builder.create();
//                alertDialog.show();
//            }
//        });


    }

    public CustomCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }
//    private void SaveEvent(String event, String time, String date, String month, String year){
//        DBOpenHelper dbOpenHelper= new DBOpenHelper(context);
//        SQLiteDatabase database= dbOpenHelper.getWritableDatabase();
//        dbOpenHelper.SaveEvent(event, time, date, month,year, database);
//        dbOpenHelper.close();
//        Toast.makeText(context,"Event Saved", Toast.LENGTH_SHORT).show();
//
//
//    }
    private void SaveMood(String mood, String date, String month, String year){
        dbOpenHelper= new DBOpenHelper( context);
        SQLiteDatabase database= dbOpenHelper.getWritableDatabase();
        dbOpenHelper.SaveMood(mood, date, month,year, database);
        dbOpenHelper.close();
        Toast.makeText(context,"Mood Saved", Toast.LENGTH_SHORT).show();


    }
    private void DeleteMood(String id, String mood,  String date, String month, String year){
        dbOpenHelper= new DBOpenHelper(context);
        SQLiteDatabase database= dbOpenHelper.getWritableDatabase();
        dbOpenHelper.DeleteMood(id,mood,date,month,year,database);
        dbOpenHelper.close();
      // database.delete(DBStructure.EVENT_TABLE_NAME, DBStructure.ID + "=" + id,null);
        //database.delete(DBStructure.EVENT_TABLE_NAME, "event = ?", new String[]{String.valueOf(mood)});
//        database.delete(DBStructure.EVENT_TABLE_NAME, "id = ?", new String[]{String.valueOf(id)});
//        database.delete(DBStructure.EVENT_TABLE_NAME, "date = ?", new String[]{String.valueOf(id)});
//        database.delete(DBStructure.EVENT_TABLE_NAME, "month = ?", new String[]{String.valueOf(id)});
//        database.delete(DBStructure.EVENT_TABLE_NAME, "year = ?", new String[]{String.valueOf(id)});
//
//       Cursor cursor = database.rawQuery("select * from "+ DBStructure.EVENT_TABLE_NAME ,null);
//       Log.i("cursor",   cursor.getColumnNames().toString() +"");
//       cursor.close();


    }

    private void IntializeLaoyut (){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.calendar_layout, this);
        NextButton = view.findViewById(R.id.nextBtn);
        PreviousButton = view.findViewById(R.id.previousBtn);
        CurrentDate= view.findViewById(R.id.current_Date);
        gridView = view.findViewById(R.id.gridview);
        DeleteButton = view.findViewById(R.id.deleteall);

    }
    private void SetUpCalendar () {
        String currentDate = dateFormat.format(calendar.getTime());
        //Log.i("date",currentDate+"");
        CurrentDate.setText(currentDate);
        dates.clear();
        Calendar monthCalendar= (Calendar)calendar.clone();
        monthCalendar.set(Calendar.DAY_OF_MONTH,1);
        int FirstDayOfMonth= monthCalendar.get(Calendar.DAY_OF_WEEK)-1;
        monthCalendar.add(Calendar.DAY_OF_MONTH,-FirstDayOfMonth);
        CollectEventsPerMonth(monthFormat.format(calendar.getTime()), yearFormat.format(calendar.getTime()));
        while(dates.size()<MAX_CALENDAR_DAYS){
            dates.add(monthCalendar.getTime());
            monthCalendar.add(Calendar.DAY_OF_MONTH,1);
        }
        myGridAdapter= new MyGridAdapter(context, dates, calendar, eventsList);

         gridView.setAdapter(myGridAdapter);

    }
    private void CollectEventsPerMonth (String Month, String year){
        eventsList.clear();
        DBOpenHelper dbOpenHelper= new DBOpenHelper(context);
        SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
        Cursor cursor = dbOpenHelper.ReadEventsperMonth(Month, year, database);
        while( cursor.moveToNext()){
            String event = cursor.getString(cursor.getColumnIndex(DBStructure.EVENT));
            String time = cursor.getString(cursor.getColumnIndex(DBStructure.TIME));
            String date = cursor.getString(cursor.getColumnIndex(DBStructure.DATE));
            String month = cursor.getString(cursor.getColumnIndex(DBStructure.MONTH));
            String Year = cursor.getString(cursor.getColumnIndex(DBStructure.YEAR));
            Events events = new Events(event,time, date,month,Year);
            eventsList.add(events);
        }
        cursor.close();
        dbOpenHelper.close();
    }


}

