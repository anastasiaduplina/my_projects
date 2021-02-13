package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class MyView extends View {
    int height;
    int width;
    int size=30;
    float density;

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        density=getResources().getDisplayMetrics().density;
        size *=density;
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        //canvas.drawColor(Color.RED);
        height=canvas.getHeight();
        width=canvas.getWidth();
        Paint paint= new Paint();
        paint.setStrokeWidth(3);
        paint.setColor(Color.BLACK);
       // canvas.drawLine(0,0,100, 200, paint);
        drawGrid(canvas);
    }

    private void drawGrid(Canvas canvas) {
        Paint paint= new Paint();
        paint.setStrokeWidth(density);
        paint.setColor(Color.BLACK);
        int blockWidth=canvas.getWidth()/ size;
        int blockHeight=canvas.getHeight()/ size;
        for (int i=0;i<blockWidth;i++) {
            int x=i *size;
            canvas.drawLine(x,0,x, height, paint);
        }
        for (int i=0;i<blockHeight;i++) {
            int y= i* size;
            canvas.drawLine(0,y,width, y, paint);
        }

    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        // return super.OnTouchEvent(event);
        Log.i("touch",event.getAction()+ "");
        return true;
    }
}
