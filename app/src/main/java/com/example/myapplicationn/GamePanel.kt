package com.example.myapplicationn

import android.content.Context
import android.graphics.Canvas
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView



class GamePanel(context: Context?) : SurfaceView(context),
    SurfaceHolder.Callback {
    private var thread: MainThread
    var manager: SceneManager
    override fun surfaceCreated(holder: SurfaceHolder) {
        thread = MainThread(getHolder(), this)
        Constants.INIT_TIME = System.currentTimeMillis()
        thread.setRunning(true)
        thread.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}
    override fun surfaceDestroyed(holder: SurfaceHolder) {
        var retry = true
        while (retry) {
            try {
                thread.setRunning(false)
                thread.join()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            retry = false
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        manager.receiveTouch(event)
        return true
        //return super.onTouchEvent(event);
    }

    fun update() {
        manager.update()
    }

    override fun draw(canvas: Canvas) {
        if (canvas == null) {
            holder.removeCallback(this)
        } else {
            super.draw(canvas)
            manager.draw(canvas)
        }
    }

    init {
        holder.addCallback(this)
        Constants.CURRENT_CONTEXT = context
        thread = MainThread(holder, this)
        manager = SceneManager
        isFocusable = true
    }
}