package com.example.aroundtheworld.Game;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;

    public SceneManager manager;

    public GamePanel(Context context) {
        super(context);
        getHolder().addCallback(this);
        Constants.CURRENT_CONTEXT=context;

        thread =new MainThread(getHolder(),this);
        manager = new SceneManager();
        setFocusable(true);


    }




    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        thread = new MainThread(getHolder(),this);
        Constants.INIT_TIME=System.currentTimeMillis();
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        boolean retry = true;
        while(retry){
            try{
                thread.setRunning(false);
                thread.join();
            }catch (Exception e){e.printStackTrace();}
            retry=false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent  event){

        manager.receiveTouch(event);
        return true;
        //return super.onTouchEvent(event);
    }



    public void update(){
        manager.update();
    }

    @Override
    public void draw(Canvas canvas){

        if(canvas==null){
            getHolder().removeCallback(this);

        }
        else{
            super.draw(canvas);
            manager.draw(canvas);

        }



    }






}
