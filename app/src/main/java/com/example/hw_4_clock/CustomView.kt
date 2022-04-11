package com.example.hw_4_clock

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import java.lang.Math.PI
import java.util.*
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin


class CustomView(
    context: Context,
    attrs: AttributeSet? = null,
    defaultAttrs: Int = 0
) : View(context, attrs, defaultAttrs) {

    private var radius = 0
    private var centerX = 0f
    private var centerY = 0f
    private val paint = Paint()
    private val rect = Rect()
    private var minuteHand = 0
    private var hourHand = 0
    private var isInit = false
    private val fontSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
        25f, resources.displayMetrics).toInt()

    private val painter = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        isAntiAlias = true
        strokeWidth = 25f
    }

    override fun onDraw(canvas: Canvas) {
        canvas.apply {
            if (!isInit) {
                initClock()
            }
            drawCircle(canvas)
            drawCenter(canvas)
            drawHands(canvas)
            drawNumeral(canvas)
            invalidate()
        }
    }

    private fun initClock() {
        val numeralSpacing = 0     //цифровой интервал
        val padding = numeralSpacing + 100
        val min = min(height, width)
        radius = min / 2 - padding
        minuteHand = min / 15
        hourHand = min / 20
        isInit = true
    }

    private fun drawNumeral(canvas: Canvas) {
        paint.color = Color.BLACK
        paint.textSize = fontSize.toFloat()
        for (number in 1..12) {
            val tmp = number.toString()
            paint.getTextBounds(tmp, 0, tmp.length, rect)
            val angle = PI / 6 * (number - 3)
            val x = (width / 2 + cos(angle) * radius - rect.width() / 2).toInt()
            val y = (height / 2 + sin(angle) * radius + rect.height() / 2).toInt()
            canvas.drawText(tmp, x.toFloat(), y.toFloat(), paint)
        }
    }

    private fun drawCircle(canvas: Canvas) {
        canvas.apply {
            centerX = (width / 2).toFloat()
            centerY = (height / 2).toFloat()
            drawCircle(centerX, centerY, 500f, painter)
        }
    }

    private fun drawCenter(canvas: Canvas) {
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), 16f, painter)
    }

    private fun drawHands(canvas: Canvas) {
        val c: Calendar = Calendar.getInstance()
        var hour = c.get(Calendar.HOUR_OF_DAY)
        hour = if (hour > 12) hour - 12 else hour
        //часовая стрелка
        paint.strokeWidth = 20f
        drawHand(canvas, ((hour + c.get(Calendar.MINUTE) / 60.0) * 5f).toInt(), true)
        //минутная стрелка
        paint.color = Color.RED
        paint.strokeWidth = 20f
        drawHand(canvas, c.get(Calendar.MINUTE), false)
        //секундная стрелка
        paint.color = Color.BLUE
        paint.strokeWidth = 15f
        drawHand(canvas, c.get(Calendar.SECOND), false)
    }

    private fun drawHand(canvas: Canvas, loc: Int, isHour: Boolean) {
        val angle = PI * loc / 30 - PI / 2
        val handRadius =
            if (isHour) radius - minuteHand - hourHand else radius - minuteHand
        canvas.drawLine(
            (width / 2).toFloat(), (height / 2).toFloat(),
            (width / 2 + cos(angle) * handRadius).toFloat(),
            (height / 2 + sin(angle) * handRadius).toFloat(),
            paint
        )
    }
}