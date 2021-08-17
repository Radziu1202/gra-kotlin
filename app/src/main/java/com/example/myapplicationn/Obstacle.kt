package com.example.myapplicationn

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect

class Obstacle(rectHeight: Int, color: Int, startX : Int, startY: Int, playerGap: Int) : GameObject{
    private var rectangle2: Rect? = Rect(startX + playerGap, startY, Constants.SCREEN_WIDTH, startY + rectHeight)
    private var color = color
    private var rectangle: Rect? =  Rect(0, startY, startX, startY + rectHeight)


    fun getRectangle(): Rect? {
        return rectangle
    }

    fun incrementY(y: Float) {
        rectangle!!.top += y.toInt()
        rectangle!!.bottom += y.toInt()
        rectangle2!!.top += y.toInt()
        rectangle2!!.bottom += y.toInt()
    }

    fun playerCollide(player: RectPlayer): Boolean {
        return player.getRectangle()?.let { Rect.intersects(rectangle!!, it) } == true || player.getRectangle()?.let {
            Rect.intersects(rectangle2!!,
                it
            )
        } == true
    }


    override fun draw(canvas: Canvas?) {
        val paint = Paint()
        paint.color = color
        canvas!!.drawRect(rectangle!!, paint)
        canvas.drawRect(rectangle2!!, paint)
    }

    override fun update() {}
}
