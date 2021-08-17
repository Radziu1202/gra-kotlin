package com.example.myapplicationn

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.graphics.Rect

class RectPlayer(rect: Rect, rgb: Int) : GameObject{
    private var rectangle: Rect? = rect
    private var color = rgb

    fun getRectangle(): Rect? {
        return rectangle
    }






    override fun draw(canvas: Canvas?) {
        val paint = Paint()
        paint.color = color
        if (canvas != null) {
            canvas.drawRect(rectangle!!, paint)
        }
    }

    override fun update() {}

    fun update(point: Point) {
        rectangle!![point.x - rectangle!!.width() / 2, point.y - rectangle!!.height() / 2, point.x + rectangle!!.width() / 2] =
            point.y + rectangle!!.height() / 2
    }

}
