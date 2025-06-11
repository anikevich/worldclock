package com.example.worldclock

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.os.postDelayed
import java.util.*
/*
class CustomAnalogClock(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    //private var paint: Paint = Paint()
    //private var textPaint: Paint = Paint()
    //private var calendar: Calendar = Calendar.getInstance()
    //private var timeZone: TimeZone = TimeZone.getDefault() // Добавляем поле для хранения часового пояса
    private val paint = Paint().apply {
        isAntiAlias = true
        strokeWidth = 8f
        style = Paint.Style.STROKE
        color = Color.BLACK
    }

    private val textPaint = Paint().apply {
        isAntiAlias = true
        color = Color.BLACK
        textSize = 24f
        textAlign = Paint.Align.CENTER
    }

    private var calendar = Calendar.getInstance()
    private var timeZone: TimeZone = TimeZone.getDefault()
    private val handler = Handler(Looper.getMainLooper())
    private val updateRunnable = object : Runnable {
        override fun run() {
            updateTime()
            handler.postDelayed(this, 1000)
        }
    }

    init {
        startUpdates()
    }

    fun setTimeZone(timeZoneId: String) {
        timeZone = TimeZone.getTimeZone(timeZoneId)
        updateTime()
    }

    private fun startUpdates() {
        handler.post(updateRunnable)
    }

    private fun stopUpdates() {
        handler.removeCallbacks(updateRunnable)
    }

    private fun updateTime() {
        calendar.timeZone = timeZone
        calendar.timeInMillis = System.currentTimeMillis()
        invalidate()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startUpdates()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopUpdates()
    }

    // Метод для установки часового пояса
    //fun setTimeZone(timeZoneId: String) {
        //timeZone = TimeZone.getTimeZone(timeZoneId)
        //Log.d("Mylog", "Timezone: $timeZone")
        //invalidate() // Перерисовываем часы при изменении пояса
    //}

    /*
    init {
        paint.isAntiAlias = true
        paint.strokeWidth = 8f //толщина ободка
        paint.style = Paint.Style.STROKE
        paint.color = Color.BLACK

        textPaint.isAntiAlias = true
        textPaint.color = Color.BLACK
        textPaint.textSize = 24f//48f
        textPaint.textAlign = Paint.Align.CENTER
        //textPaint.setTypeface(Typeface.create("Roboto", Typeface.BOLD))
    }
    */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val hour = calendar.get(Calendar.HOUR)
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)

        val width = width.toFloat()
        val height = height.toFloat()
        val radius = Math.min(width, height) / 2 - 32

        // Устанавливаем часовой пояс для календаря
        calendar.timeZone = timeZone
        calendar.time = Date() // Обновляем время

        // Draw clock circle
        paint.style = Paint.Style.STROKE
        paint.color = Color.BLACK
        canvas.drawCircle(width / 2, height / 2, radius, paint)

        // Draw clock center
        paint.style = Paint.Style.FILL
        paint.color = Color.RED
        canvas.drawCircle(width / 2, height / 2, 12f, paint)

        // Draw clock numbers (hours)
        val numberRadius = radius - 30//60
        for (i in 1..12) {
            val angle = Math.toRadians((i * 30 - 90).toDouble())
            val x = (width / 2 + Math.cos(angle) * numberRadius).toFloat()
            val y = (height / 2 + Math.sin(angle) * numberRadius + textPaint.textSize / 3).toFloat()
            canvas.drawText(i.toString(), x, y, textPaint)
        }

        // Draw clock hands
        calendar = Calendar.getInstance()
        //val hour = calendar.get(Calendar.HOUR)
        //val minute = calendar.get(Calendar.MINUTE)
        //val second = calendar.get(Calendar.SECOND)

        // Hour hand
        paint.color = Color.BLUE
        paint.strokeWidth = 16f
        drawHand(canvas, width, height, (hour + minute / 60.0) * 5f, radius * 0.5f, paint)

        // Minute hand
        paint.color = Color.GREEN
        paint.strokeWidth = 12f
        drawHand(canvas, width, height, minute.toDouble(), radius * 0.7f, paint)

        // Second hand
        paint.color = Color.RED
        paint.strokeWidth = 4f
        drawHand(canvas, width, height, second.toDouble(), radius * 0.9f, paint)

        // Draw the clock ticks
        paint.strokeWidth = 4f
        paint.color = Color.DKGRAY
        for (i in 0..59) {
            val angle = Math.toRadians((i * 6).toDouble())
            val startX = (width / 2 + Math.cos(angle) * (radius - 10)).toFloat()
            val startY = (height / 2 + Math.sin(angle) * (radius - 10)).toFloat()
            val stopX = (width / 2 + Math.cos(angle) * radius).toFloat()
            val stopY = (height / 2 + Math.sin(angle) * radius).toFloat()
            canvas.drawLine(startX, startY, stopX, stopY, paint)
        }

        // Update every second
        postInvalidateDelayed(1000)
    }

    private fun drawHand(
        canvas: Canvas,
        width: Float,
        height: Float,
        loc: Double,
        handLength: Float,
        paint: Paint
    ) {
        val angle = Math.toRadians(loc * 6 - 90)
        val handX = (width / 2 + Math.cos(angle) * handLength).toFloat()
        val handY = (height / 2 + Math.sin(angle) * handLength).toFloat()
        canvas.drawLine(width / 2, height / 2, handX, handY, paint)
    }
}

 */
class CustomAnalogClock(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint().apply {
        isAntiAlias = true
        strokeWidth = 8f
        style = Paint.Style.STROKE
        color = Color.BLACK
    }

    private val textPaint = Paint().apply {
        isAntiAlias = true
        color = Color.BLACK
        textSize = 24f
        textAlign = Paint.Align.CENTER
    }

    private val calendar = Calendar.getInstance()
    private var timeZone: TimeZone = TimeZone.getDefault()
    private val handler = Handler(Looper.getMainLooper())
    private val updateRunnable = object : Runnable {
        override fun run() {
            updateTime()
            handler.postDelayed(this, 1000 - System.currentTimeMillis() % 1000)
        }
    }

    init {
        startUpdates()
    }

    fun setTimeZone(timeZoneId: String) {
        timeZone = TimeZone.getTimeZone(timeZoneId)
        calendar.timeZone = timeZone
        updateTime()
    }

    private fun startUpdates() {
        handler.removeCallbacks(updateRunnable) // Сначала очищаем старые вызовы
        handler.post(updateRunnable)
    }

    private fun stopUpdates() {
        handler.removeCallbacks(updateRunnable)
    }

    private fun updateTime() {
        calendar.timeZone = timeZone
        calendar.timeInMillis = System.currentTimeMillis()
        postInvalidateOnAnimation() // Более плавное обновление
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startUpdates()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopUpdates()
    }

    override fun onDraw(canvas: Canvas) {
        val width = width.toFloat()
        val height = height.toFloat()
        val radius = Math.min(width, height) / 2 - 32
        val centerX = width / 2
        val centerY = height / 2

        // Получаем время в 24-часовом формате
        val hour = calendar.get(Calendar.HOUR_OF_DAY) % 12
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)

        // Отрисовка циферблата (ваш существующий код)
        canvas.drawCircle(centerX, centerY, radius, paint)

        // Draw clock numbers (hours)
        val numberRadius = radius - 30   //_60
        for (i in 1..12) {
            val angle = Math.toRadians((i * 30 - 90).toDouble())
            val x = (width / 2 + Math.cos(angle) * numberRadius).toFloat()
            val y = (height / 2 + Math.sin(angle) * numberRadius + textPaint.textSize / 3).toFloat()
            canvas.drawText(i.toString(), x, y, textPaint)
        }

        // Часовая стрелка (учитываем минуты для плавности)
        paint.color = Color.BLUE
        paint.strokeWidth = 16f
        drawHand(canvas, centerX, centerY, (hour + minute / 60.0) * 5.0, radius * 0.5f, paint)

        // Минутная стрелка
        paint.color = Color.GREEN
        paint.strokeWidth = 12f
        drawHand(canvas, centerX, centerY, minute.toDouble(), radius * 0.7f, paint)

        // Секундная стрелка
        paint.color = Color.RED
        paint.strokeWidth = 4f
        drawHand(canvas, centerX, centerY, second.toDouble(), radius * 0.9f, paint)

        // Draw the clock ticks
        paint.strokeWidth = 4f
        paint.color = Color.DKGRAY
        for (i in 0..59) {
            val angle = Math.toRadians((i * 6).toDouble())
            val startX = (width / 2 + Math.cos(angle) * (radius - 10)).toFloat()
            val startY = (height / 2 + Math.sin(angle) * (radius - 10)).toFloat()
            val stopX = (width / 2 + Math.cos(angle) * radius).toFloat()
            val stopY = (height / 2 + Math.sin(angle) * radius).toFloat()
            canvas.drawLine(startX, startY, stopX, stopY, paint)
        }
    }

    private fun drawHand(
        canvas: Canvas,
        centerX: Float,
        centerY: Float,
        loc: Double,
        handLength: Float,
        paint: Paint
    ) {
        val angle = Math.toRadians(loc * 6 - 90)
        val handX = (centerX + Math.cos(angle) * handLength).toFloat()
        val handY = (centerY + Math.sin(angle) * handLength).toFloat()
        canvas.drawLine(centerX, centerY, handX, handY, paint)
    }
}