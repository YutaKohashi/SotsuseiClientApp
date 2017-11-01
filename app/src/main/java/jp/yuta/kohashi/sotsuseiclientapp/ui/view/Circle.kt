package jp.yuta.kohashi.sotsuseiclientapp.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation



class Circle(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paint: Paint
    private val rect: RectF

    var startAngle: Float = 0f

    init {
        val strokeWidth = 20

        paint = Paint()
        paint.isAntiAlias = true
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = strokeWidth.toFloat()
        //Circle color
        paint.color = Color.RED

        //size 200x200 example
        rect = RectF(strokeWidth.toFloat(), strokeWidth.toFloat(), (200 + strokeWidth).toFloat(), (200 + strokeWidth).toFloat())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawArc(rect, START_ANGLE_POINT.toFloat(), startAngle, false, paint)
    }

    companion object {
        private val START_ANGLE_POINT = 270
    }

    class CircleAngleAnimation(private val circle: Circle, endAngle: Int) : Animation() {

        private val oldAngle: Float = circle.startAngle
        private val newAngle: Float = endAngle.toFloat()

        override fun applyTransformation(interpolatedTime: Float, transformation: Transformation) {
            val angle = oldAngle + (newAngle - oldAngle) * interpolatedTime

            circle.startAngle = angle
            circle.requestLayout()
        }
    }
}