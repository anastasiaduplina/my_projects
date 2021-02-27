package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

public class Triangle extends Shape {

    PointF centre;
    PointF storona;
    public Triangle (String color,PointF centre,PointF storona){
        super(color);
        this.centre=centre;
        this.storona= storona;
    }
    public void draw(Canvas canvas, Paint paint){
        paint.setColor(Color.parseColor("#" + this.color));
        paint.setStrokeWidth(6);
        float x=Math.abs(centre.x-storona.x);
        float y=Math.abs(centre.y-storona.y);
        canvas.drawLine(centre.x+ x,centre.y+y,centre.x,centre.y-y,paint);
        canvas.drawLine( centre.x,centre.y-y,centre.x-x,centre.y+y,paint);
        canvas.drawLine(centre.x-x,centre.y+y,centre.x+x,centre.y+y,paint);
    }
}
