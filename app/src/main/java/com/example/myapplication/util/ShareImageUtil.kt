package com.example.myapplication.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.provider.MediaStore
import android.widget.ImageView
import java.io.ByteArrayOutputStream

object ShareImageUtil {

    fun getBitmapFromView(view: ImageView): Bitmap? {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media
                .insertImage(inContext.contentResolver, inImage, "title", null)
        return Uri.parse(path)
    }

}