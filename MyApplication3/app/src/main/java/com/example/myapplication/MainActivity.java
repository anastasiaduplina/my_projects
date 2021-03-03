package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.android.material.internal.ViewOverlayImpl;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener,AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyView myView= findViewById(R.id.panel);
        RadioGroup shapes = findViewById(R.id.rg2);
        Spinner color= findViewById(R.id.colours);
        class Listener implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

            @Override
            public void onClick(View v) {
                myView.undo();

            }

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.i( "changeChecked",checkedId + "");
                switch(checkedId){
                    case R.id.rect:myView.setTypeShape(MyView.TYPE_RECT); break;
                    case R.id.circle:myView.setTypeShape(MyView.TYPE_CIRCLE); break;
                    case R.id.triangle:myView.setTypeShape(MyView.TYPE_TRIANGLE); break;
                }
            }
        }
        //RadioGroup.OnCheckedChangeListener;

        String[] colors= getResources().getStringArray(R.array.coloursnum);
         class Spinner extends  Activity implements AdapterView.OnItemSelectedListener {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 myView.color=colors[position];
                 Log.i( "changeColor",colors[position] + "");

             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {

             }
         }
        Listener listener = new Listener();
       Spinner spinner= new Spinner();
        Button undoBtn=findViewById(R.id.undo);
        undoBtn.setOnClickListener(listener);
        shapes.setOnCheckedChangeListener(listener);
        color.setOnItemSelectedListener(spinner);
    }


    @Override
    public void onClick(View v) {
        Log.i("onClick","onClick");

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        Log.i( "changeChecked",checkedId + "");
        switch(checkedId){
            case R.id.rect:break;
            case R.id.circle:break;
            case R.id.triangle:break;
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}