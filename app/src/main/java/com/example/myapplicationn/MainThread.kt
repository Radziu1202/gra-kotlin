package com.example.myapplicationn

import android.graphics.Canvas
import android.view.SurfaceHolder
private lateinit var  canvas: Canvas

class MainThread(surfaceHolder: SurfaceHolder?, gamePanel: GamePanel?) : Thread() {

    val MAX_FPS = 30
    private var avarageFPS = 0.0
    private var surfaceHolder: SurfaceHolder? = surfaceHolder
    private var gamePanel: GamePanel? = gamePanel
    private var running = false


    fun setRunning(running: Boolean) {
        this.running = running
    }





    override fun run() {
        var startTime: Long
        var timeMillis = (1000 / MAX_FPS).toLong()
        var waitTime: Long
        var frameCount = 0
        var totalTime: Long = 0
        val targetTime = (1000 / MAX_FPS).toLong()
        while (running) {
            startTime = System.nanoTime()
            try {
                canvas = surfaceHolder!!.lockCanvas()
                synchronized(surfaceHolder!!) {
                    gamePanel!!.update()
                    gamePanel!!.draw(canvas)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder!!.unlockCanvasAndPost(canvas)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
            timeMillis = (System.nanoTime() - startTime) / 1000000
            waitTime = targetTime - timeMillis
            try {
                if (waitTime > 0) {
                    sleep(waitTime)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            totalTime += System.nanoTime() - startTime
            frameCount++
            if (frameCount == MAX_FPS) {
                avarageFPS = (1000 / (totalTime / frameCount / 1000000)).toDouble()
                frameCount = 0
                totalTime = 0
                println(avarageFPS)
            }
        }
    }
}