package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

public class Circle extends Shape {

    PointF centre;
    float radius;
    public Circle (String color,PointF centre,float radius){
        super(color);
        this.centre=centre;
        this.radius= radius;
    }
    @Override
    void draw(Canvas canvas,Paint paint){
        paint.setColor(Color.parseColor("#" + this.color));
        canvas.drawCircle(this.centre.x,this.centre.y, radius,paint);
    }

}
