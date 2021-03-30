package org.xapps.examples.mlkitexamples.views.utils

import android.content.Context
import android.content.res.Configuration
import android.graphics.*
import android.util.AttributeSet
import android.view.View


class DetectionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1
) : View(context, attrs, defStyleAttr) {

    var color: Int = Color.WHITE
        set(value) {
            field = value
            paintConfig.color = value
            postInvalidate()
        }

    var lineWidth: Float = 2f
        set(value) {
            field = value
            paintConfig.strokeWidth = value
            postInvalidate()
        }

    var pointRadius: Float = 2f

    var detections: List<Detection> = listOf()
        set(value) {
            field = value
            postInvalidate()
        }

    private val paintConfig: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = this@DetectionView.color
            style = Paint.Style.STROKE
            strokeWidth = lineWidth
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.apply {
            detections.forEach { detection ->
                detection.boundingBox?.let {
                    val rect = translateRect(it, detection.sourceWidth, detection.sourceHeight)
                    drawRect(rect, paintConfig)
                }
            }
        }
    }

    private fun isPortraitMode(): Boolean {
        val orientation: Int = resources.configuration.orientation
        return orientation == Configuration.ORIENTATION_PORTRAIT
    }

    private fun calculateScale(sourceWidth: Int, sourceHeight: Int): Scale {
        val scaleFactorX: Float
        val scaleFactorY: Float

        if (isPortraitMode()) {
            scaleFactorY = height.toFloat() / sourceWidth
            scaleFactorX = width.toFloat() / sourceHeight
        } else {
            scaleFactorY = height.toFloat() / sourceHeight
            scaleFactorX = width.toFloat() / sourceWidth
        }
        return Scale(scaleFactorX, scaleFactorY)
    }

    private fun translateRect(rect: Rect, sourceWidth: Int, sourceHeight: Int): RectF {
        val scale = calculateScale(sourceWidth, sourceHeight)
        return RectF(
            rect.left.toFloat() * scale.x,
            rect.top.toFloat() * scale.y,
            rect.right.toFloat() * scale.x,
            rect.bottom.toFloat() * scale.y
        )
    }

}