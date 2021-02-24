package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

public class Rect extends Shape {

    PointF cornerLT;
    PointF cornerRB;
    public Rect ( String color, PointF cornerLT, PointF cornerRB){
        super(color);
        this.color=color;
        this.cornerLT=cornerLT;
        this.cornerRB=cornerRB;
    }
    public void draw(Canvas canvas, Paint paint){
        paint.setColor(Color.parseColor("#" + this.color));
        canvas.drawRect(this.cornerLT.x,this.cornerLT.y, this.cornerRB.x, this.cornerRB.y,paint);

    }
}
