package com.example.calendar;

import androidx.annotation.ColorRes;
import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;

import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<EventDay> events = new ArrayList<>();
    com.applandeo.materialcalendarview.CalendarView mcalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        CalendarView calendarView= findViewById(R.id.calendarView);
//        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange ( CalendarView view, int year, int month, int dayOfMonth){
//                int mYear = year;
//                int mMonth = month;
//                int mDay = dayOfMonth;
//                String selectedDate = new StringBuilder().append(mDay).append("-").append(mMonth+1).append("-").append(mYear).append(" ").toString();
//                Toast.makeText(getApplicationContext(), selectedDate, Toast.LENGTH_LONG).show();
//            }
//
//        });
        mcalendarView = (com.applandeo.materialcalendarview.CalendarView) findViewById(R.id.calendarView);
        Calendar calendar = Calendar.getInstance();
        events.add(new EventDay(calendar, Color.parseColor("#228B22")));


        mcalendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(@NotNull EventDay eventDay) {
                Log.i("dayClick","Click");
               // mcalendarView.setEvents(events);
                Calendar clickedDayCalendar = eventDay . getCalendar ();
                Log.i("dayClick",eventDay+"");


            }
        });



    }

}