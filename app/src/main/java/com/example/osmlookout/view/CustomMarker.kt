package com.example.osmlookout.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

//TODO maybe refactor to a fabric
fun createCustomMarker(ctx:Context): Drawable {
    val tracker =  ContextCompat.getDrawable(
        ctx, com.example.osmlookout.R.mipmap.ic_tracker_75dp
    )
    val trackerBmp = (tracker as BitmapDrawable).bitmap

    val photo =  ContextCompat.getDrawable(ctx, com.example.osmlookout.R.drawable.photo)
    var photoBmp = (photo as BitmapDrawable).bitmap
    // Apply SCALE of marker
    photoBmp = Bitmap.createScaledBitmap(
        photoBmp,
        (trackerBmp.width * 0.6666).toInt(),
        (trackerBmp.width * 0.6666).toInt(),
        true
    )
    //Apply CORNER ROUNDING to make it circle shape
    photoBmp = getRoundedCornerBitmap(photoBmp, photoBmp.width.toFloat())

    val resultBitmap = Bitmap.createBitmap(trackerBmp.width, trackerBmp.height, trackerBmp.config)
    val canvas = Canvas(resultBitmap)
    canvas.drawBitmap(trackerBmp, Matrix(), null)
    val photoOffsetX = - canvas.width * 0.005
    val photoOffsetY = - canvas.height * 0.0833
    canvas.drawBitmap(
        photoBmp,
        (canvas.width / 2 - photoBmp.width / 2 + photoOffsetX).toFloat(), //offset
        (canvas.height / 2 - photoBmp.height / 2 + photoOffsetY).toFloat(),
        null
    )

    return BitmapDrawable(ctx.resources, resultBitmap)
}

private fun getRoundedCornerBitmap(bitmap: Bitmap, roundPx:Float): Bitmap {
    val output = Bitmap.createBitmap(
        bitmap.width,
        bitmap.height, Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(output)
    val paint = Paint()

    val rect = Rect(0, 0, bitmap.width, bitmap.height)
    val rectF = RectF(rect)

    paint.isAntiAlias = true
    canvas.drawARGB(0, 0, 0, 0)
    paint.color = -0xbdbdbe
    canvas.drawRoundRect(rectF, roundPx, roundPx, paint)
    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    canvas.drawBitmap(bitmap, rect, rect, paint)
    return output
}