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
    SimpleDateFormat eventDateFormat= new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
    SimpleDateFormat DateFormat= new SimpleDateFormat("dd", Locale.ENGLISH);
    SimpleDateFormat dateFormat= new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
    SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.ENGLISH);
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy",Locale.ENGLISH);

    MyGridAdapter myGridAdapter;
    AlertDialog alertDialog;
    List<Date> dates = new ArrayList<>();
    List <Events> eventsList= new ArrayList<>();
    public static SQLiteDatabase db;
    public static DBOpenHelper dbOpenHelper;




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
                //Button close= addView.findViewById(R.id.close);
                Button delete= addView.findViewById(R.id.deleteall);
                //Button save= addView.findViewById(R.id.save);
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
                        SaveMood(color[0], date,month,year);
                        alertDialog.dismiss();
                        SetUpCalendar();
                        CountMood(month);
                    }
                });
                two.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                            view.setBackgroundColor(getContext().getResources().getColor(R.color.purple_200));
                        color[0] = "200";
                        SaveMood(color[0], date,month,year);
                        alertDialog.dismiss();
                        SetUpCalendar();
                        CountMood(month);
                    }
                });
                tree.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                            view.setBackgroundColor(getContext().getResources().getColor(R.color.purple_700));
                        color[0] = "700";
                        SaveMood(color[0], date,month,year);
                        alertDialog.dismiss();
                        SetUpCalendar();
                        CountMood(month);
                    }
                });
//                close.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        alertDialog.dismiss();
//                        SetUpCalendar();
//                    }
//                });
                delete.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DeleteMood(date);
                        alertDialog.dismiss();
                        SetUpCalendar();
                        CountMood(month);

                    }
                });
//                save.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        SaveMood(color[0], date,month,year);
//                        SetUpCalendar();
//                    }
//                });
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

        Cursor cursor1= database.query(DBStructure.EVENT_TABLE_NAME,null,null,null,null,null,null);
        if (cursor1.moveToFirst()){
            int indexid = cursor1.getColumnIndex(DBStructure.ID);
            int indexmood = cursor1.getColumnIndex(DBStructure.EVENT);
            int indexdate = cursor1.getColumnIndex(DBStructure.DATE);
            int indexmonth = cursor1.getColumnIndex(DBStructure.MONTH);
            int indexyear = cursor1.getColumnIndex(DBStructure.YEAR);
            do {

                String date1=cursor1.getString(indexdate) ;
                String id1=cursor1.getString(indexid);
                Cursor cursor2= database.query(DBStructure.EVENT_TABLE_NAME,null,null,null,null,null,null);
                if (cursor2.moveToFirst()){
                    do{
                        String date2= cursor2.getString(indexdate);
                        String id2= cursor2.getString(indexid);
                        if (date1.equals(date2)&& !(id1.equals(id2))){
                            if (Integer.parseInt(id1)>Integer.parseInt(id2)){
                                long status= database.delete(DBStructure.EVENT_TABLE_NAME,DBStructure.ID + " = ?",new String[]{id2});
                                //Log.i("id1>id2","delete "+id2);
                            }
                            if (Integer.parseInt(id1)<Integer.parseInt(id2)){
                                long status= database.delete(DBStructure.EVENT_TABLE_NAME,DBStructure.ID + " = ?",new String[]{id1});
                                //Log.i("id1<id2","delete "+id1);
                            }
                        }
                    }while(cursor2.moveToNext());
                }
                Log.i("id:","id:" +cursor1.getString(indexid)+ " mood:"+cursor1.getString(indexmood)+ " date:" +cursor1.getString(indexdate)
                        +" month:"+cursor1.getString(indexmonth)+ " year:"+cursor1.getString(indexyear));
            }while(cursor1.moveToNext());
        }

        cursor1.close();
        dbOpenHelper.close();
    }
    private void DeleteMood(String date){
        dbOpenHelper= new DBOpenHelper(context);
        SQLiteDatabase database= dbOpenHelper.getWritableDatabase();
        database.delete(DBStructure.EVENT_TABLE_NAME,DBStructure.DATE + " = ?",new String[]{date});
        dbOpenHelper.close();

    }
     public  void CountMood(String Month){
        TextView countMood = findViewById(R.id.countmood);
         dbOpenHelper= new DBOpenHelper( context);
         SQLiteDatabase database= dbOpenHelper.getReadableDatabase();
         Cursor cursor= database.query(DBStructure.EVENT_TABLE_NAME,null,null,null,null,null,null);
         int m500=0;
         int m200=0;
         int m700=0;
         if (cursor.moveToFirst()){
             do {
                 String mood= cursor.getString(cursor.getColumnIndex(DBStructure.EVENT)) ;
                 String month= cursor.getString(cursor.getColumnIndex(DBStructure.MONTH)) ;
                 if (month.equals(Month)){
                     switch (mood){
                         case"500": m500++;break;
                         case"700": m700++;break;
                         case"200": m200++;break;
                     }
                 }

             }while(cursor.moveToNext());
         }
         String text = "Mood 1="+Integer.toString(m500)+"  "+"Mood 2="+Integer.toString(m200)+"  "+"Mood 3="+Integer.toString(m700);
         countMood.setText(text);
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
        //CollectEventsPerMonth(monthFormat.format(calendar.getTime()), yearFormat.format(calendar.getTime()));
        while(dates.size()<MAX_CALENDAR_DAYS){
            dates.add(monthCalendar.getTime());
            monthCalendar.add(Calendar.DAY_OF_MONTH,1);
        }
        myGridAdapter= new MyGridAdapter(context, dates, calendar, eventsList, Days());

         gridView.setAdapter(myGridAdapter);

    }
    public  List<String> Days(){
        List<String> days= new ArrayList<>();
        DBOpenHelper dbOpenHelper= new DBOpenHelper(context);
        SQLiteDatabase database= dbOpenHelper.getWritableDatabase();
        Cursor cursor= database.query(DBStructure.EVENT_TABLE_NAME,null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                String day= cursor.getString(cursor.getColumnIndex(DBStructure.DATE)) + " "+ cursor.getString(cursor.getColumnIndex(DBStructure.EVENT));
                days.add(day);

            }while(cursor.moveToNext());
        }
        return days;
    }
    private void CollectEventsPerMonth (String Month, String year){
        eventsList.clear();
        DBOpenHelper dbOpenHelper= new DBOpenHelper(context);
        SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
        Cursor cursor = dbOpenHelper.ReadEventsperMonth(Month, year, database);
        while( cursor.moveToNext()){
            String event = cursor.getString(cursor.getColumnIndex(DBStructure.EVENT));

            String date = cursor.getString(cursor.getColumnIndex(DBStructure.DATE));
            String month = cursor.getString(cursor.getColumnIndex(DBStructure.MONTH));
            String Year = cursor.getString(cursor.getColumnIndex(DBStructure.YEAR));
            Events events = new Events(event, date,month,Year);
            eventsList.add(events);
        }
        cursor.close();
        dbOpenHelper.close();
    }


}

