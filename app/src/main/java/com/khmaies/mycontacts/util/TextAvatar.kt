package com.khmaies.mycontacts.util

import android.graphics.*
import kotlin.random.Random

/**
 * Created by Khmaies Hassen on 09,March,2023
 */
object TextAvatar {
    private fun calculateMaxTextSize(
        text: String?,
        paint: Paint?,
        maxWidth: Int,
        maxHeight: Int
    ): Float {
        if (text == null || paint == null) return 0F
        val bound = Rect()
        var size = 1.0f
        val step = 1.0f
        while (true) {
            paint.getTextBounds(text, 0, text.length, bound)
            if (bound.width() < maxWidth && bound.height() < maxHeight) {
                size += step
                paint.textSize = size
            } else {
                return size - step
            }
        }
    }

    fun createAvatarBitmap(text: String): Bitmap {
        val bm = Bitmap.createBitmap(64, 64, Bitmap.Config.ARGB_8888)


        val color =
            Color.argb(255, Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
        val cv = Canvas(bm)
        val paint = Paint()
        paint.color = color
        paint.style = Paint.Style.FILL
        paint.textAlign = Paint.Align.CENTER;
        paint.textSize = calculateMaxTextSize(text.uppercase(), paint, 40, 40)

        cv.drawPaint(paint)
        val xPos = (cv.width / 2).toFloat()
        val yPos = (cv.height / 2 - (paint.descent() + paint.ascent()) / 2)

        paint.color = Color.WHITE
        cv.drawText(text.uppercase(), xPos, yPos, paint)
        return bm
    }
}