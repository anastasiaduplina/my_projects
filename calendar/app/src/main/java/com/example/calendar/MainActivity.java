package com.example.calendar;

import androidx.annotation.ColorRes;
import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.github.mikephil.charting.charts.PieChart;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    CustomCalendarView customCalendarView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button calendar = findViewById(R.id.calendarbutton);
        //Button piechart = findViewById(R.id.piechartbutton);
        TextView motiv= findViewById(R.id.textmotivation);
        //String[] motivs= new String[]{"Ты все сможешь!","Удачного дня!","Ты можешь многое!","Какой у тебя сегодня был день?","Ты в порядке?","Ты солнышко!"};
        double ranmotiv=Math.random()*5;

        String[]motivs=getResources().getStringArray(R.array.motivs);
        String text=motivs[(int)ranmotiv];
        motiv.setText(text);
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customCalendarView= (CustomCalendarView)findViewById(R.id.custom_calendar_view);
                Intent intent = new Intent(MainActivity.this, CustomCalendar.class);
                startActivity(intent);
            }
        });

//        piechart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                    Intent intent = new Intent(MainActivity.this, PPieCChart.class);
//                    startActivity(intent);
//            }
//        });
    }

}