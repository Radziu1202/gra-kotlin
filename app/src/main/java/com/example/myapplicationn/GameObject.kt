package com.example.myapplicationn

import android.graphics.Canvas

interface GameObject {
    fun draw(canvas: Canvas?)
    fun update()
}