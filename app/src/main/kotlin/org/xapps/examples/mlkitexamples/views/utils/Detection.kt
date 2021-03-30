package org.xapps.examples.mlkitexamples.views.utils


import android.graphics.Point
import android.graphics.Rect


data class Detection(
    val tag: String? = null,
    val boundingBox: Rect? = null,
    val cornerPoints: List<Point>? = null,
    val sourceWidth: Int,
    val sourceHeight: Int,
    val sourceRotation: Int,
    val data: Any? = null
)