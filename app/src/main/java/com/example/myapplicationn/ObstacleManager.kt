package com.example.myapplicationn

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import java.util.*

class ObstacleManager(playerGap: Int, obstacleGap: Int, obstacleHeight: Int, color: Int) {

    private var obstacles: ArrayList<Obstacle>? = null
    private var playerGap =  playerGap
    private var obstacleGap = obstacleGap
    private var obstacleHeight = obstacleHeight
    private var color =color

    private var startTime: Long = 0
    private var initTime = System.currentTimeMillis()
    private var score = 0

    init {
        obstacles = ArrayList<Obstacle>()
        startTime = initTime
        populateObstacles()
    }


    fun playerCollide(player: RectPlayer?): Boolean {
        for (ob in obstacles!!) {
            if (player?.let { ob.playerCollide(it) } == true) {
                return true
            }
        }
        return false
    }

    private fun populateObstacles() {
        var currY: Int = -5 * Constants.SCREEN_HEIGHT / 4
        while (currY < 0) {
            val xStart = (Math.random() * (Constants.SCREEN_WIDTH - playerGap)).toInt()
            obstacles!!.add(Obstacle(obstacleHeight, color, xStart, currY, playerGap))
            currY += obstacleHeight + obstacleGap
        }
    }

    fun update() {
        if (startTime < Constants.INIT_TIME) startTime = Constants.INIT_TIME
        val elapsedTime = (System.currentTimeMillis() - startTime).toInt()
        startTime = System.currentTimeMillis()
        val speed: Float = Math.sqrt(1 + (startTime - initTime) / 2000.0)
            .toFloat() * Constants.SCREEN_HEIGHT / 10000.0f
        for (ob in obstacles!!) {
            ob.incrementY(speed * elapsedTime)
        }
        if (obstacles!![obstacles!!.size - 1].getRectangle()?.top ?: 0  >= Constants.SCREEN_HEIGHT) {
            val xStart = (Math.random() * (Constants.SCREEN_WIDTH - playerGap)) as Int
            obstacles!!.add(
                0,
                Obstacle(
                    obstacleHeight,
                    color,
                    xStart,
                    (obstacles!![0].getRectangle()?.top ?: 0) - obstacleHeight - obstacleGap,
                    playerGap
                )
            )
            obstacles!!.removeAt(obstacles!!.size - 1)
            score++
        }
    }

    fun draw(canvas: Canvas) {
        for (ob in obstacles!!) {
            ob.draw(canvas)
        }
        val paint = Paint()
        paint.textSize = 100f
        paint.color = Color.MAGENTA
        canvas.drawText("" + score, 50f, 50 + paint.descent() - paint.ascent(), paint)
    }

    fun getScore(): Int {
        return score
    }
}
