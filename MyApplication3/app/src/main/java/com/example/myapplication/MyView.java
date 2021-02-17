package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class MyView extends View {
    private final static int maxPoints=10;
    int height;
    int width;
    int size=30;
    float density;
    PointF[] points = new PointF[maxPoints];
   int counterPoints;
   String typeShape="rect";

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        density=getResources().getDisplayMetrics().density;
        size *=density;
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        height=canvas.getHeight();
        width=canvas.getWidth();
        Paint paint= new Paint();
        paint.setStrokeWidth(3);
        paint.setColor(Color.BLACK);
        drawGrid(canvas);

        drawTriangles(canvas);
    }
    
    private void drawGrid(Canvas canvas) {
        Paint paint= new Paint();
        paint.setStrokeWidth(density);
        paint.setColor(Color.BLACK);
        int blockWidth=canvas.getWidth()/ size;
        int blockHeight=canvas.getHeight()/ size;
        for (int i=0;i<width;i+=size) {
            //int x=i *size;
            canvas.drawLine(i,0,i, height, paint);
        }
        for (int i=0;i<height;i+=size) {
            //int y= i* size;
            canvas.drawLine(0,i,width, i, paint);
        }

    }
    void drawPoints(Canvas canvas){
        Paint paint= new Paint();
        paint.setColor(Color.RED);
        //int maxPoints=5;
        int counter= Math.min(counterPoints,maxPoints);
        for (int i=0;i<counter;i++){
            PointF pointF= points[i];
            canvas.drawCircle(pointF.x,pointF.y,20,paint);
        }
    }
    void drawRects(Canvas canvas){
        Paint paint= new Paint();
        paint.setColor(Color.BLUE);
        Rect rect= new Rect();
        int counter= Math.min(counterPoints,maxPoints);
        for (int i=0;i<counter;i++){
            PointF pointF= points[i];
            canvas.drawRect(pointF.x-30, pointF.y+30,pointF.x+30,pointF.y-30,paint);
        }
    }
    void drawTriangles(Canvas canvas){
        Paint paint= new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(6);
        //int maxPoints=5;
        int counter= Math.min(counterPoints,maxPoints);
        for (int i=0;i<counter;i++){
            PointF pointF= points[i];
            canvas.drawLine(pointF.x+40,pointF.y+30,pointF.x,pointF.y-40,paint);
            canvas.drawLine( pointF.x,pointF.y-40,pointF.x-40,pointF.y+30,paint);
            canvas.drawLine(pointF.x-40,pointF.y+30,pointF.x+40,pointF.y+30,paint);
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction()==MotionEvent.ACTION_DOWN){

            float x= event.getX();
            float y= event.getY();
            if (counterPoints< maxPoints){
            points[counterPoints]= new PointF(x,y);
            counterPoints++;}
            this.invalidate();



        }
        return true;
    }
}
