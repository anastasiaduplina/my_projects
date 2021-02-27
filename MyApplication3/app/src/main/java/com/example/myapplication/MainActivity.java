package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.google.android.material.internal.ViewOverlayImpl;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyView myView= findViewById(R.id.panel);
        RadioGroup shapes = findViewById(R.id.rg2);
        class Listener implements View.OnClickListener, RadioGroup.OnCheckedChangeListener{

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
        Listener listener = new Listener();

        Button undoBtn=findViewById(R.id.undo);
        undoBtn.setOnClickListener(listener);
        shapes.setOnCheckedChangeListener(listener);
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
}