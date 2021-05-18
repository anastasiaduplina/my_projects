package com.example.calendar;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class PPieCChart extends MainActivity {
    CustomCalendarView customCalendarView;
    PieChart pieChart;
    public static DBOpenHelper dbOpenHelper;
    Context context;
    Calendar calendarday=Calendar.getInstance(Locale.ENGLISH);
    SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.ENGLISH);
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy",Locale.ENGLISH);
    int mglad=0;
    int msad=0;
    int mangry=0;
    int mawful=0;
    int mwonderful=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context= getApplicationContext();
        setContentView(R.layout.piechart);
        Button calendar = findViewById(R.id.calendarbutton);
        Button piechart = findViewById(R.id.piechartbutton);
        ImageButton nextbutton= findViewById(R.id.nextbutton);
        ImageButton previousbutton= findViewById(R.id.previousbutton);
        TextView text= findViewById(R.id.current_dDate);
        TextView textnochart= findViewById(R.id.textNoChar);
        final String[] month = {monthFormat.format(calendarday.getTime())};
        final String[] year = {yearFormat.format(calendarday.getTime())};
        text.setText(month[0]+ " "+ year[0]);
        SetPieChart(month[0],year[0],textnochart);
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //customCalendarView= (CustomCalendarView)findViewById(R.id.custom_calendar_view);
                Intent intent = new Intent(PPieCChart.this, CustomCalendar.class);
                startActivity(intent);
            }
        });
        piechart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PPieCChart.this, PPieCChart.class);
                startActivity(intent);
            }
        });
        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarday.add(Calendar.MONTH, 1);
                month[0] = monthFormat.format(calendarday.getTime());
                year[0] = yearFormat.format(calendarday.getTime());
                text.setText(month[0]+" "+year[0]);
                SetPieChart(month[0],year[0],textnochart);
            }
        });
        previousbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarday.add(Calendar.MONTH, -1);
                month[0] = monthFormat.format(calendarday.getTime());
                year[0] = yearFormat.format(calendarday.getTime());
                text.setText(month[0]+" "+year[0]);
                SetPieChart(month[0], year[0],textnochart);
            }
        });

    }
    public void SetPieChart (String month, String year,TextView text){
        mglad=0;
        msad=0;
        mangry=0;
        mawful=0;
        mwonderful=0;
        CountMood(month,year);
        if(mglad+msad+mangry==0){
            text.setText("Нет данных");
        } else{
            text.setText("");
        }
        pieChart=findViewById(R.id.piechart);
        ArrayList<PieEntry> entries = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();
        if(mglad!=0){ entries.add(new PieEntry(mglad, "Веселое"));colors.add(0xFFdbcea4);}
        if(msad!=0){entries.add(new PieEntry(msad, "Грустное"));colors.add(0xFF97a5c9);}
        if(mangry!=0){entries.add(new PieEntry(mangry, "Злое"));colors.add(0xFFc98383);}
        if(mawful!=0){entries.add(new PieEntry(mawful, "Ужасное"));colors.add(0xFF9fd4a9);}
        if(mwonderful!=0){entries.add(new PieEntry(mwonderful, "Прекрасное"));colors.add(0xFFffbadf);}

        PieDataSet dataSet = new PieDataSet(entries, "  Настроение");





        dataSet.setColors(colors);
        //dataSet.setGradientColor(0xff0000ff,0xffffff00);
        PieData data= new PieData(dataSet);
       // data.setHighlightEnabled(true);
        data.setValueTextSize(15f);
        data.setValueTextColor(Color.BLACK);
        pieChart.setData(data);

        pieChart.invalidate();
        pieChart.animate();
    }

    public  void CountMood(String Month,String Year){
        dbOpenHelper= new DBOpenHelper(context);
        SQLiteDatabase database= dbOpenHelper.getWritableDatabase();
        Cursor cursor= database.query(DBStructure.EVENT_TABLE_NAME,null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                String mood= cursor.getString(cursor.getColumnIndex(DBStructure.EVENT)) ;
                String month= cursor.getString(cursor.getColumnIndex(DBStructure.MONTH)) ;
                String year= cursor.getString(cursor.getColumnIndex(DBStructure.YEAR)) ;
                if (month.equals(Month) &&year.equals(Year)){
                    switch (mood){
                        case"glad": mglad++; break;
                        case"angry": mangry++; break;
                        case"sad": msad++; break;
                        case"awful": mawful++;break;
                        case"wonderful": mwonderful++;break;
                    }
                }

            }while(cursor.moveToNext());
        }
        cursor.close();
        dbOpenHelper.close();
    }


}
