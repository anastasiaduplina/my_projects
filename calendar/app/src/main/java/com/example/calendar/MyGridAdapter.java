package com.example.calendar;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyGridAdapter extends ArrayAdapter {
    List<Date> dates;
    Calendar currentDate;
    List<Events> events;
    LayoutInflater inflater;
    List<String> days;

    public MyGridAdapter(@NonNull Context context, List<Date> dates,Calendar currentDate,List<Events> events,List<String> days) {
        super(context, R.layout.single_cell_layout);
        this.dates=dates;
        this.currentDate=currentDate;
        this.events= events;
        this.days= days;
        inflater= LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Date monthDate = dates.get(position);
        Calendar dateCalendar= Calendar.getInstance();
        dateCalendar.setTime(monthDate);//26:00
        int DayNo = dateCalendar.get(Calendar.DAY_OF_MONTH);
        int displayMonth = dateCalendar.get(Calendar.MONTH)+1;
        int displayYear = dateCalendar.get(Calendar.YEAR);
        int currentDay = currentDate.get(Calendar.DAY_OF_MONTH);
        int currentMonth = currentDate.get(Calendar.MONTH)+1;
        int currentYear = currentDate.get(Calendar.YEAR);
        int a=1;
        View view= convertView;


        if(view== null){
            view=inflater.inflate(R.layout.single_cell_layout,parent,false);
        }


        if (displayMonth == currentMonth && displayYear==currentYear){
            view.setBackgroundColor(getContext().getResources().getColor(R.color.green));
            //view.setBackground// https://github.com/roomorama/Caldroid
        }else{
            view.setBackgroundColor(Color.parseColor("#CCCCCC"));
        }
        TextView Day_Number = view.findViewById(R.id.calendar_day);
        TextView EventNumber = view.findViewById(R.id.events_id);
        Day_Number.setText(String.valueOf(DayNo));

//        Calendar eventCalendar = Calendar.getInstance();
//        ArrayList<String> arrayList = new ArrayList<>();
//        for (int i=0; i<events.size();i++){
//            eventCalendar.setTime(ConvertStringToDate(events.get(i).getDATE()));
//            if (DayNo==eventCalendar.get(Calendar.DAY_OF_MONTH)&& displayMonth== eventCalendar.get(Calendar.MONTH)+1
//            && displayYear==eventCalendar.get(Calendar.YEAR)){
//                arrayList.add(events.get(i).getEVENT());
//                //EventNumber.setText(arrayList.size()+" Events");
//                //EventNumber.setText(" mood");
//                view.setBackgroundColor(getContext().getResources().getColor(R.color.purple_500));
//            }
//        }
        for (String i:days){
            String[]dday=i.split(" ");
            switch (dday[1]){
                case "January":dday[1]="1"; break;
                case "February":dday[1]="2"; break;
                case "March":dday[1]="3"; break;
                case "April":dday[1]="4"; break;
                case "May":dday[1]="5"; break;
                case "June":dday[1]="6"; break;
                case "July":dday[1]="7"; break;
                case "August":dday[1]="8"; break;
                case "September":dday[1]="9"; break;
                case "October":dday[1]="10"; break;
                case "November":dday[1]="11"; break;
                case "December":dday[1]="12"; break;
            }
            if (DayNo==Integer.parseInt(dday[0]) && displayMonth==Integer.parseInt(dday[1]) && displayYear==Integer.parseInt(dday[2])){
                if (dday[3].equals("500")) {
                    view.setBackgroundColor(getContext().getResources().getColor(R.color.purple_500));
                }
                if (dday[3].equals("700")) {
                    view.setBackgroundColor(getContext().getResources().getColor(R.color.purple_700));
                }
                if (dday[3].equals("200")) {
                    view.setBackgroundColor(getContext().getResources().getColor(R.color.purple_200));
                }
            }
        }
         
        return view;
    }
    private Date ConvertStringToDate (String eventDate){
        SimpleDateFormat format = new SimpleDateFormat( "yyyy MM dd", Locale.ENGLISH);
        Date date= null;
        try{
            date= format.parse(eventDate);
        }catch(ParseException e ){
            e.printStackTrace();
        }
        return date;
    }

    @Override
    public int getCount() {
        return dates.size();
    }

    @Override
    public int getPosition(@Nullable Object item) {
        return dates.indexOf(item);
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return dates.get(position);
    }
}
