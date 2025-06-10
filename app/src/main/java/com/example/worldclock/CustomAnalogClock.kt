package com.example.worldclock

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import java.util.*

class CustomAnalogClock(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private var paint: Paint = Paint()
    private var textPaint: Paint = Paint()
    private var calendar: Calendar = Calendar.getInstance()

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

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val width = width.toFloat()
        val height = height.toFloat()
        val radius = Math.min(width, height) / 2 - 32

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
        val hour = calendar.get(Calendar.HOUR)
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)

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