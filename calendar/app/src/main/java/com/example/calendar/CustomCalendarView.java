package com.example.calendar;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.provider.CalendarContract;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.Callable;


public class CustomCalendarView extends LinearLayout {
    ImageButton NextButton, PreviousButton;
    TextView CurrentDate;
    GridView gridView;
    private static final int MAX_CALENDAR_DAYS= 42;
    Calendar calendar=Calendar.getInstance(Locale.ENGLISH);
    Context context;
    SimpleDateFormat eventDateFormat= new SimpleDateFormat("yyyy MM dd", Locale.ENGLISH);
    SimpleDateFormat dateFormat= new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
    SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.ENGLISH);
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy",Locale.ENGLISH);

    MyGridAdapter myGridAdapter;
    AlertDialog alertDialog;
    List<Date> dates = new ArrayList<>();
    List <Events> eventsList= new ArrayList<>();
    public static SQLiteDatabase db;
    public static DBOpenHelper dbOpenHelper;

    public static int Kolvo(Context context){
        dbOpenHelper= new DBOpenHelper(context);
        dbOpenHelper.onOpen(db);
        String query = "select count(*) from eventstable";
        db = dbOpenHelper.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        int num= c.getColumnCount();
        c.close();
        Log.i("kolvo",num+"");
        return num;

    }
    public long kolvo(){
        dbOpenHelper= new DBOpenHelper(context);
        dbOpenHelper.onOpen(db);
        db = dbOpenHelper.getWritableDatabase();
        long numRows = DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM eventstable", null);
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
                                                Button delete= addView.findViewById(R.id.delete);
                                                Button save= addView.findViewById(R.id.save);
                                                final String date= eventDateFormat.format(dates.get(position));
                                                final String month = monthFormat.format(dates.get(position));
                                                final String year = yearFormat.format(dates.get(position));
                                                one.setOnClickListener(new OnClickListener() {
                                                    int a=0;
                                                    @Override
                                                    public void onClick(View v) {
                                                        a++;
                                                        long h = kolvo();
                                                        if (a % 2 !=0){
                                                        view.setBackgroundColor(getContext().getResources().getColor(R.color.purple_500));
                                                        }
                                                        else{view.setBackgroundColor(getContext().getResources().getColor(R.color.green));

                                                            }
                                                    }
                                                });
                                                two.setOnClickListener(new OnClickListener() {
                                                    int a=0;
                                                    @Override
                                                    public void onClick(View v) {
                                                        a++;
                                                        long h = kolvo();
                                                        if (a % 2 !=0){
                                                            view.setBackgroundColor(getContext().getResources().getColor(R.color.purple_200));
                                                            }
                                                        else{view.setBackgroundColor(getContext().getResources().getColor(R.color.green));
                                                        }
                                                    }
                                                });
                                                tree.setOnClickListener(new OnClickListener() {
                                                    int a=0;
                                                    @Override
                                                    public void onClick(View v) {
                                                        a++;
                                                        long h = kolvo();
                                                        if (a % 2 !=0){
                                                            view.setBackgroundColor(getContext().getResources().getColor(R.color.purple_700));
                                                            }
                                                        else{view.setBackgroundColor(getContext().getResources().getColor(R.color.green));
                                                        }
                                                    }
                                                });
                                                close.setOnClickListener(new OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        alertDialog.dismiss();
                                                    }
                                                });
                                                delete.setOnClickListener(new OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        DeleteMood(Long.toString(kolvo()),"1", date,month,year);
                                                    }
                                                });
                                                save.setOnClickListener(new OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        SaveMood(Long.toString(kolvo()),"1", date,month,year);
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
    private void SaveMood(String id,String mood, String date, String month, String year){
        DBOpenHelper dbOpenHelper= new DBOpenHelper(context);
        SQLiteDatabase database= dbOpenHelper.getWritableDatabase();
        dbOpenHelper.SaveMood(id,mood, date, month,year, database);
        dbOpenHelper.close();
        Toast.makeText(context,"Mood Saved", Toast.LENGTH_SHORT).show();


    }
    private void DeleteMood(String id, String mood,  String date, String month, String year){
        DBOpenHelper dbOpenHelper= new DBOpenHelper(context);
        SQLiteDatabase database= dbOpenHelper.getWritableDatabase();
        int a=10;

      // database.delete(DBStructure.EVENT_TABLE_NAME, DBStructure.ID + "=" + id,null);
        //database.delete(DBStructure.EVENT_TABLE_NAME, "event = ?", new String[]{String.valueOf(mood)});
        database.delete(DBStructure.EVENT_TABLE_NAME, "id = ?", new String[]{String.valueOf(id)});
        database.delete(DBStructure.EVENT_TABLE_NAME, "date = ?", new String[]{String.valueOf(id)});
        database.delete(DBStructure.EVENT_TABLE_NAME, "month = ?", new String[]{String.valueOf(id)});
        database.delete(DBStructure.EVENT_TABLE_NAME, "year = ?", new String[]{String.valueOf(id)});

       Cursor cursor = database.rawQuery("select * from "+ DBStructure.EVENT_TABLE_NAME ,null);
       Log.i("cursor",   cursor.getColumnNames().toString() +"");
       cursor.close();


    }

    private void IntializeLaoyut (){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.calendar_layout, this);
        NextButton = view.findViewById(R.id.nextBtn);
        PreviousButton = view.findViewById(R.id.previousBtn);
        CurrentDate= view.findViewById(R.id.current_Date);
        gridView = view.findViewById(R.id.gridview);

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

