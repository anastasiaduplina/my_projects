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
    int m500=0;
    int m200=0;
    int m700=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getApplicationContext();
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
        m500=0;
        m200=0;
        m700=0;
        CountMood(month,year);
        if(m500+m200+m700==0){
            text.setText("Нет данных");
        } else{
            text.setText("");
        }
        pieChart=findViewById(R.id.piechart);
        ArrayList<PieEntry> entries = new ArrayList<>();
        if(m500!=0){ entries.add(new PieEntry(m500, "1 mood"));}
        if(m200!=0){entries.add(new PieEntry(m200, "2 mood"));}
        if(m700!=0){entries.add(new PieEntry(m700, "3 mood"));}

        PieDataSet dataSet = new PieDataSet(entries, "Mood");
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(0xFF6200EE);
        colors.add(0xFFBB86FC);
        colors.add(0xFF3700B3);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        dataSet.setColors(colors);
        //dataSet.setGradientColor(0xff0000ff,0xffffff00);
        PieData data= new PieData(dataSet);
        data.setHighlightEnabled(true);
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
                        case"500": m500++; break;
                        case"700": m700++; break;
                        case"200": m200++; break;
                    }
                }

            }while(cursor.moveToNext());
        }
        cursor.close();
        dbOpenHelper.close();
    }


}
