package com.example.myapplicationn

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class OrientationData: SensorEventListener {
    private var manager: SensorManager?  = Constants.CURRENT_CONTEXT!!.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private var accelerometer: Sensor? = manager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    private var magnometer: Sensor? = manager!!.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

    private var accelOutput: FloatArray? = null
    private var magOutput: FloatArray? = null

    private val orientation = FloatArray(3)
    fun getOrientation(): FloatArray? {
        return orientation
    }

    private var startOrientation: FloatArray? = null

    fun getStartOrientation(): FloatArray? {
        return startOrientation
    }

    fun newGame() {
        startOrientation = null
    }




    fun register() {
        manager!!.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)
        manager!!.registerListener(this, magnometer, SensorManager.SENSOR_DELAY_GAME)
    }

    fun pause() {
        manager!!.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event!!.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            accelOutput = event.values
        } else if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
            magOutput = event.values
        }
        if (accelOutput != null && magOutput != null) {
            val R = FloatArray(9)
            val I = FloatArray(9)
            val success = SensorManager.getRotationMatrix(R, I, accelOutput, magOutput)
            if (success) {
                SensorManager.getOrientation(R, orientation)
                if (startOrientation == null) {
                    startOrientation = FloatArray(orientation.size)
                    System.arraycopy(orientation, 0, startOrientation, 0, orientation.size)
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}