package com.example.calendar;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
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

    NoteAdd noteAdd=new NoteAdd();

    Bundle savedInstanceState;
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
    public List<Date> dates = new ArrayList<>();
    List <Events> eventsList= new ArrayList<>();
    //public static SQLiteDatabase db;
    public static DBOpenHelper dbOpenHelper;


//    public CustomCalendarView(Context context) {
//        super(context);
//    }

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
//        DeleteButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DBOpenHelper dbOpenHelper= new DBOpenHelper(context);
//                SQLiteDatabase database= dbOpenHelper.getWritableDatabase();
//                database.delete(DBStructure.EVENT_TABLE_NAME, null,null);
//                SetUpCalendar();
//            }
//        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder builder= new AlertDialog.Builder(context);
                builder.setCancelable(true);
                View addView = LayoutInflater.from(parent.getContext()).inflate(R.layout.choose_color,null);
                Button glad= addView.findViewById(R.id.glad);
                Button sad= addView.findViewById(R.id.sad);
                Button angry= addView.findViewById(R.id.angry);
                Button wonderful= addView.findViewById(R.id.wonderful);
                Button awful= addView.findViewById(R.id.awful);
                Button delete= addView.findViewById(R.id.deleteall);
                Button addnote= addView.findViewById(R.id.add_note);
                TextView textView= addView.findViewById(R.id.textView);
                final String date= eventDateFormat.format(dates.get(position));
                final String month = monthFormat.format(dates.get(position));
                final String year = yearFormat.format(dates.get(position));
                final String[] color = new String[1];
                color[0]="green";
                if (CheckNullNote(date)){
                    addnote.setText("Изменить заметку");
                    textView.setVisibility(VISIBLE);
                    textView.setText(GetNote(date));
                }else{
                    addnote.setText("Добавить заметку");
                    textView.setVisibility(INVISIBLE);
                }
                glad.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        view.setBackgroundColor(getContext().getResources().getColor(R.color.yellow));
                        color[0] = "glad";
                        SaveMood(color[0], date,month,year);
                        //alertDialog.dismiss();
                        SetUpCalendar();
                        CountMood(month);
                    }
                });
                wonderful.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        view.setBackgroundColor(getContext().getResources().getColor(R.color.yellow));
                        color[0] = "wonderful";
                        SaveMood(color[0], date,month,year);
                        //alertDialog.dismiss();
                        SetUpCalendar();
                        CountMood(month);
                    }
                });
                awful.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        view.setBackgroundColor(getContext().getResources().getColor(R.color.yellow));
                        color[0] = "awful";
                        SaveMood(color[0], date,month,year);
                        //alertDialog.dismiss();
                        SetUpCalendar();
                        CountMood(month);
                    }
                });
                sad.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                            view.setBackgroundColor(getContext().getResources().getColor(R.color.blue));
                        color[0] = "sad";
                        SaveMood(color[0], date,month,year);
                        //alertDialog.dismiss();
                        SetUpCalendar();
                        CountMood(month);
                    }
                });
                angry.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                            view.setBackgroundColor(getContext().getResources().getColor(R.color.red));
                        color[0] = "angry";
                        SaveMood(color[0], date,month,year);
                       // alertDialog.dismiss();
                        SetUpCalendar();
                        CountMood(month);
                    }
                });
                delete.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DeleteMood(date);
                        alertDialog.dismiss();
                        SetUpCalendar();
                        CountMood(month);

                    }
                });

                addnote.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder= new AlertDialog.Builder(context);
                        builder.setCancelable(true);
                        View addView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note,null);
                        EditText addNote=addView.findViewById(R.id.textnote);
                        Button saveNote= addView.findViewById(R.id.savenote);
                        ImageButton deletenote=addView.findViewById(R.id.deleteBtn);

                        if (CheckNullNote(date)){
                            addNote.setText(KnowNote(date));
                        }else{

                            addNote.setHint("Добавь заметку");
                        }
                        deletenote.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //Log.i("delete","yes");
                                DeleteNote(date);
                                addNote.setText(KnowNote(date));
                                addnote.setText("Добавить заметку");
                                textView.setText(GetNote(date));
                            }
                        });

                        saveNote.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                    color[0] = KnowColor(date);
                                    Savenote(color[0], date, month, year, addNote.getText().toString());

                                    //Log.i("note", addNote.getText().toString() + "");
                                    alertDialog.dismiss();
                                    addnote.setText("Изменить заметку");
                                    textView.setVisibility(VISIBLE);
                                    textView.setText(GetNote(date));

                            }
                        });
                        builder.setView(addView);
                        alertDialog= builder.create();
                        alertDialog.show();
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
        CheckRecords();
    }
    private boolean CheckNullNote(String date){
        boolean boolnote=false;
        dbOpenHelper= new DBOpenHelper( context);
        SQLiteDatabase database= dbOpenHelper.getWritableDatabase();
        Cursor cursor1= database.query(DBStructure.EVENT_TABLE_NAME,null,null,null,null,null,null);
        if (cursor1.moveToLast()) {
            int indexnote = cursor1.getColumnIndex(DBStructure.NOTE);
            int indexdate = cursor1.getColumnIndex(DBStructure.DATE);
            do{
                String date1=cursor1.getString(indexdate);
                String note= cursor1.getString(indexnote);
                if (date.equals(date1)){
                    if (note==null){
                        boolnote=false;
                    } else{
                        boolnote= true;
                    }
                    break;
                }
            }while(cursor1.moveToPrevious());
        }
        return boolnote;
    }
    private String GetNote(String date){
        String strnote="";
        dbOpenHelper= new DBOpenHelper( context);
        SQLiteDatabase database= dbOpenHelper.getWritableDatabase();
        Cursor cursor1= database.query(DBStructure.EVENT_TABLE_NAME,null,null,null,null,null,null);
        if (cursor1.moveToLast()) {
            int indexnote = cursor1.getColumnIndex(DBStructure.NOTE);
            int indexdate = cursor1.getColumnIndex(DBStructure.DATE);
            do{
                String date1=cursor1.getString(indexdate);
                String note= cursor1.getString(indexnote);
                if (date.equals(date1)){
                    strnote= note;
                    break;
                }
            }while(cursor1.moveToPrevious());
        }
        return strnote;
    }
    private void DeleteNote(String date){
        dbOpenHelper= new DBOpenHelper( context);
        SQLiteDatabase database= dbOpenHelper.getWritableDatabase();
        Cursor cursor1= database.query(DBStructure.EVENT_TABLE_NAME,null,null,null,null,null,null);
        ContentValues contentValues=new ContentValues();
        if (cursor1.moveToFirst()){
            int indexid = cursor1.getColumnIndex(DBStructure.ID);
            int indexmood = cursor1.getColumnIndex(DBStructure.EVENT);
            int indexdate = cursor1.getColumnIndex(DBStructure.DATE);
            int indexmonth = cursor1.getColumnIndex(DBStructure.MONTH);
            int indexyear = cursor1.getColumnIndex(DBStructure.YEAR);
            int indexnote = cursor1.getColumnIndex(DBStructure.NOTE);
            do {

                String date1=cursor1.getString(indexdate) ;
                String id1=cursor1.getString(indexid);
                if (date1.equals(date)){
                            contentValues.put(DBStructure.DATE,cursor1.getString(indexdate));
                            contentValues.put(DBStructure.MONTH,cursor1.getString(indexmonth));
                            contentValues.put(DBStructure.YEAR,cursor1.getString(indexyear));
                            contentValues.put(DBStructure.EVENT,cursor1.getString(indexmood));
                            long status = database.insert(DBStructure.EVENT_TABLE_NAME,null,contentValues);
                            long status1= database.delete(DBStructure.EVENT_TABLE_NAME,DBStructure.ID + " = ?",new String[]{id1});
                }
            }while(cursor1.moveToNext());
        }
        cursor1.close();
        dbOpenHelper.close();
    }
    private  void CheckRecords(){
        dbOpenHelper= new DBOpenHelper( context);
        SQLiteDatabase database= dbOpenHelper.getWritableDatabase();
        Cursor cursor1= database.query(DBStructure.EVENT_TABLE_NAME,null,null,null,null,null,null);
        ContentValues contentValues=new ContentValues();
        if (cursor1.moveToFirst()){
            int indexid = cursor1.getColumnIndex(DBStructure.ID);
            int indexmood = cursor1.getColumnIndex(DBStructure.EVENT);
            int indexdate = cursor1.getColumnIndex(DBStructure.DATE);
            int indexmonth = cursor1.getColumnIndex(DBStructure.MONTH);
            int indexyear = cursor1.getColumnIndex(DBStructure.YEAR);
            int indexnote = cursor1.getColumnIndex(DBStructure.NOTE);
            do {

                String date1=cursor1.getString(indexdate) ;
                String id1=cursor1.getString(indexid);
                String note1=cursor1.getString(indexnote);
                Cursor cursor2= database.query(DBStructure.EVENT_TABLE_NAME,null,null,null,null,null,null);
                if (cursor2.moveToFirst()){
                    do{
                        String date2= cursor2.getString(indexdate);
                        String id2= cursor2.getString(indexid);
                        String note2=cursor2.getString(indexnote);
                        ContentValues contentValues1= new ContentValues();
                        if (date1.equals(date2)&& !(id1.equals(id2))){
                            if (Integer.parseInt(id1)>Integer.parseInt(id2)){
                                if(note2!=null & note1==null){
                                    contentValues.put(DBStructure.NOTE,note2);
                                    contentValues.put(DBStructure.DATE,cursor1.getString(indexdate));
                                    contentValues.put(DBStructure.MONTH,cursor1.getString(indexmonth));
                                    contentValues.put(DBStructure.YEAR,cursor1.getString(indexyear));
                                    contentValues.put(DBStructure.EVENT,cursor1.getString(indexmood));
                                    long status = database.insert(DBStructure.EVENT_TABLE_NAME,null,contentValues);
                                    long status2= database.delete(DBStructure.EVENT_TABLE_NAME,DBStructure.ID + " = ?",new String[]{id2});
                                    long status1= database.delete(DBStructure.EVENT_TABLE_NAME,DBStructure.ID + " = ?",new String[]{id1});
                                }
                                long status= database.delete(DBStructure.EVENT_TABLE_NAME,DBStructure.ID + " = ?",new String[]{id2});
                                //Log.i("id1>id2","delete "+id2);
                            }
                            if (Integer.parseInt(id1)<Integer.parseInt(id2)){
                                if(note1!=null & note2==null ){
                                    contentValues.put(DBStructure.NOTE,note1);
                                    contentValues.put(DBStructure.DATE,cursor2.getString(indexdate));
                                    contentValues.put(DBStructure.MONTH,cursor2.getString(indexmonth));
                                    contentValues.put(DBStructure.YEAR,cursor2.getString(indexyear));
                                    contentValues.put(DBStructure.EVENT,cursor2.getString(indexmood));
                                    long status = database.insert(DBStructure.EVENT_TABLE_NAME,null,contentValues);
                                    long status2= database.delete(DBStructure.EVENT_TABLE_NAME,DBStructure.ID + " = ?",new String[]{id2});
                                    long status1= database.delete(DBStructure.EVENT_TABLE_NAME,DBStructure.ID + " = ?",new String[]{id1});
                                }
                                long status= database.delete(DBStructure.EVENT_TABLE_NAME,DBStructure.ID + " = ?",new String[]{id1});
                                //Log.i("id1<id2","delete "+id1);
                            }
                        }

                    }while(cursor2.moveToNext());
                }
                Log.i("id:","id:" +cursor1.getString(indexid)+ " mood:"+cursor1.getString(indexmood)+ " date:" +cursor1.getString(indexdate) +" month:"+cursor1.getString(indexmonth)+ " year:"+cursor1.getString(indexyear)+ " note:"+cursor1.getString(indexnote) );
            }while(cursor1.moveToNext());
        }

        cursor1.close();
        dbOpenHelper.close();
    }
    private String KnowColor (String date){
        dbOpenHelper= new DBOpenHelper( context);
        SQLiteDatabase database= dbOpenHelper.getWritableDatabase();
        Cursor cursor1= database.query(DBStructure.EVENT_TABLE_NAME,null,null,null,null,null,null);
        String color="green";
        if (cursor1.moveToLast()) {
            int indexmood = cursor1.getColumnIndex(DBStructure.EVENT);
            int indexdate = cursor1.getColumnIndex(DBStructure.DATE);
            do{
                String date1=cursor1.getString(indexdate);
                if (date.equals(date1)){
                    color=cursor1.getString(indexmood);
                    break;
                }
            }while(cursor1.moveToPrevious());
        }
        return color;
    }
    private String KnowNote(String date) {
        dbOpenHelper= new DBOpenHelper( context);
        SQLiteDatabase database= dbOpenHelper.getWritableDatabase();
        Cursor cursor1= database.query(DBStructure.EVENT_TABLE_NAME,null,null,null,null,null,null);
        String note="";
        if (cursor1.moveToLast()) {
            int indexnote = cursor1.getColumnIndex(DBStructure.NOTE);
            int indexdate = cursor1.getColumnIndex(DBStructure.DATE);
            do{
                String date1=cursor1.getString(indexdate);
                if (date.equals(date1)){
                    note=cursor1.getString(indexnote);
                    break;
                }
            }while(cursor1.moveToPrevious());
        }
        return note;
    }

    private void DeleteMood(String date){
        dbOpenHelper= new DBOpenHelper(context);
        SQLiteDatabase database= dbOpenHelper.getWritableDatabase();
        database.delete(DBStructure.EVENT_TABLE_NAME,DBStructure.DATE + " = ?",new String[]{date});
        dbOpenHelper.close();

    }
    private void Savenote(String mood, String date, String month, String year,String note){
        dbOpenHelper= new DBOpenHelper(context);
        SQLiteDatabase database= dbOpenHelper.getWritableDatabase();
        dbOpenHelper.SaveNote(mood,date,month,year,note,database);
        dbOpenHelper.close();
        CheckRecords();

    }
     public  void CountMood(String Month){
        TextView countMood = findViewById(R.id.countmood);
         dbOpenHelper= new DBOpenHelper( context);
         SQLiteDatabase database= dbOpenHelper.getReadableDatabase();
         Cursor cursor= database.query(DBStructure.EVENT_TABLE_NAME,null,null,null,null,null,null);
         int mglad=0;
         int msad=0;
         int mangry=0;
         int mawful=0;
         int mwonderful=0;
         if (cursor.moveToFirst()){
             do {
                 String mood= cursor.getString(cursor.getColumnIndex(DBStructure.EVENT)) ;
                 String month= cursor.getString(cursor.getColumnIndex(DBStructure.MONTH)) ;
                 if (month.equals(Month)){
                     switch (mood){
                         case"glad": mglad++;break;
                         case"angry": mangry++;break;
                         case"sad": msad++;break;
                         case"awful": mawful++;break;
                         case"wonderful": mwonderful++;break;
                     }
                 }

             }while(cursor.moveToNext());
         }
         //String text = "glad="+Integer.toString(mglad)+"  "+"sad="+Integer.toString(msad)+"  "+"angry="+Integer.toString(mangry)+"  "+"awful="+Integer.toString(mawful)+"  "+"wonderful="+Integer.toString(mwonderful);
         //countMood.setText(text);
         //Log.i("mood",text);
         cursor.close();
         dbOpenHelper.close();
     }

    private void IntializeLaoyut (){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.calendar_layout, this);
        NextButton = view.findViewById(R.id.nextBtn);
        PreviousButton = view.findViewById(R.id.previousBtn);
        CurrentDate= view.findViewById(R.id.current_Date);
        gridView = view.findViewById(R.id.gridview);
        //DeleteButton = view.findViewById(R.id.deleteall);

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
        cursor.close();
        dbOpenHelper.close();
        return days;
    }
    private void CollectEventsPerMonth (String Month, String year){
//        eventsList.clear();
//        DBOpenHelper dbOpenHelper= new DBOpenHelper(context);
//        SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
//        Cursor cursor = dbOpenHelper.ReadEventsperMonth(Month, year, database);
//        while( cursor.moveToNext()){
//            String event = cursor.getString(cursor.getColumnIndex(DBStructure.EVENT));
//
//            String date = cursor.getString(cursor.getColumnIndex(DBStructure.DATE));
//            String month = cursor.getString(cursor.getColumnIndex(DBStructure.MONTH));
//            String Year = cursor.getString(cursor.getColumnIndex(DBStructure.YEAR));
//            Events events = new Events(event, date,month,Year);
//            eventsList.add(events);
//        }
//        cursor.close();
//        dbOpenHelper.close();
    }


}

