package com.example.calendar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class CustomCalendar extends MainActivity {
    Context context;
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getBaseContext();
        setContentView(R.layout.custom_calendar);
        Button calendar = findViewById(R.id.calendarbutton);
        Button piechart = findViewById(R.id.piechartbutton);
        //ImageButton settings= findViewById(R.id.settingBtn);
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CustomCalendar.this, CustomCalendar.class);
                startActivity(intent);
            }
        });
        piechart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomCalendar.this, PPieCChart.class);
                startActivity(intent);
            }
        });
//        settings.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder= new AlertDialog.Builder(context);
//                builder.setCancelable(true);
//                View addView = LayoutInflater.from(context).inflate(R.layout.settings,null);
//
//                Button deleteall= addView.findViewById(R.id.deleteall);
//                deleteall.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        alertDialog.dismiss();
//                    }
//                });
//                builder.setView(addView);
//                alertDialog= builder.create();
//                alertDialog.show();
//            }
//        });

    }
}
