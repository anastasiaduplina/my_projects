package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
//import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class MyView extends View {
    private final static int maxPoints=10;
    public static final String TYPE_RECT="rect";
    public static final String TYPE_CIRCLE="circle";
    int height;
    int width;
    int size=30;
    float density;
    PointF[] points = new PointF[maxPoints];
   int counterPoints;
   String typeShape=TYPE_CIRCLE;
   String color= "000000";
   int counterRect;
   Rect[] rects= new Rect[100];
   int counterCircles;
   Circle[] circles= new Circle[100];

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
        drawPoints(canvas);
        drawCircles(canvas);
        drawRects(canvas);
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

        for (int i=0;i<counterRect;i++){
            Rect rect =rects[i];
            rect.draw(canvas,paint);

        }
    }
    void drawCircles(Canvas canvas){
        Paint paint= new Paint();
        for ( int i = 0 ; i<counterCircles;i++){
            Circle circle=circles[i];
            circle.draw(canvas,paint);

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
            counterPoints++;
            switch (this.typeShape){
                case TYPE_RECT: CheckPointsForCreateRect();break;
                case TYPE_CIRCLE:CheckPointsForCreateCircle();break;
            }
            this.invalidate();}



        }
        return true;
    }

    private void CheckPointsForCreateCircle() {
        if (counterPoints>=2){
            float a=Math.abs(points[0].x - points[1].x);
            float b=Math.abs(points[0].y - points[1].y);
            float radius=(float)Math.sqrt(Math.pow(a,2)+Math.pow(b,2));
            Circle circle= new Circle(this.color, points[0],radius);
            circles[counterCircles]=circle;
            counterCircles++;
            counterPoints=0;
            this.invalidate();
        }
    }

    private void CheckPointsForCreateRect() {
        if (counterPoints>=2){
            Rect rect = new Rect(this.color,points[0],points[1]);
            rects[counterRect]= rect;
            counterRect++;
            counterPoints=0;
            this.invalidate();
        }
    }

}
