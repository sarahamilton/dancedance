package com.example.dancedance

import android.app.Activity
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import com.example.dancedance.databinding.ActivityMainBinding
import android.R
import android.util.Log
import android.view.View

import android.widget.ImageView

import android.widget.TextView




class MainActivity : Activity(), SensorEventListener{

    private lateinit var binding: ActivityMainBinding
    private val mLastX:Float = 0f
    private  var mLastY:Float = 0f
    private  var mLastZ:Float = 0f
    var TAG : String = "dancedance"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var mInitialized : Boolean = false;

        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val accelerometer: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        //SensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    override fun onSensorChanged(event: SensorEvent) {
        // In this example, alpha is calculated as t / (t + dT),
        // where t is the low-pass filter's time-constant and
        // dT is the event delivery rate.

        val alpha: Float = 0.8f
        var gravity: Array<Float> = emptyArray()
        var linear_acceleration: Array<Float> = emptyArray()

        // Isolate the force of gravity with the low-pass filter.
        gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0]
        gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1]
        gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2]

        // Remove the gravity contribution with the high-pass filter.
        linear_acceleration[0] = event.values[0] - gravity[0]
        linear_acceleration[1] = event.values[1] - gravity[1]
        linear_acceleration[2] = event.values[2] - gravity[2]

        val x = linear_acceleration[0]
        val y = linear_acceleration[1]
        val z = linear_acceleration[2]

        binding.xAxisText.setText(x.toString())
        binding.yAxisText.setText(y.toString())
        binding.zAxisText.setText(z.toString())

        Log.d(TAG, "Setting axes. X: ${x.toString()}, Y: ${y.toString()}, Z: ${z.toString()}")

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

}