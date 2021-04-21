package com.example.calendar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class PPieCChart extends MainActivity {
    PieChart pieChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.piechart);
        Button calendar = findViewById(R.id.calendarbutton);
        Button piechart = findViewById(R.id.piechartbutton);
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
        pieChart=findViewById(R.id.piechart);
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(1f, 5f));
        entries.add(new PieEntry(2f, 2f));
        entries.add(new PieEntry(3f, 1f));
        entries.add(new PieEntry(4f, 3f));
        entries.add(new PieEntry(5f, 4f));
        entries.add(new PieEntry(6f, 1f));
        PieDataSet dataSet = new PieDataSet(entries, "Primer");
        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        PieData data= new PieData(dataSet);
        pieChart.setData(data);
        pieChart.invalidate();
    }


}
