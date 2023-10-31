package com.codc.cats.data.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.codc.cats.data.common.OUTPUT_PATH
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/** Shares image with another app on the device e.g. Gmail or WhatsApp */
fun shareImage(context: Context, imageUrl: String) {
    Glide.with(context).load(imageUrl).into(object : CustomTarget<Drawable?>() {
        override fun onResourceReady(
            resource: Drawable, transition: Transition<in Drawable?>?
        ) {
            val imageExtension = imageUrl.substringAfterLast('.', "")
            val fileUri = saveStaticImageFile(context, resource, imageExtension)

            if (fileUri != null) {
                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    type = "image/*"
                    // temp permission for receiving app to read this file
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    putExtra(Intent.EXTRA_STREAM, fileUri)
                }

                startActivity(context, sendIntent, null)
            }
        }

        override fun onLoadCleared(placeholder: Drawable?) {}
    })
}

/** Saves a drawable object as an image file to the app's cache directory */
fun saveStaticImageFile(context: Context, drawable: Drawable, imageExtension: String): Uri? {
    val bitmap = drawable.toBitmap()

    // save bitmap to cache directory
    try {
        val cachePath = File(context.cacheDir, OUTPUT_PATH)
        Timber.d("Cache path set: ${cachePath.absolutePath}")
        cachePath.mkdirs()
        // image is overwritten every time
        val stream =
            FileOutputStream("$cachePath/image.$imageExtension")
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        stream.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }

    val imagePath = File(context.cacheDir, "images")
    val newFile = File(imagePath, "image.$imageExtension")

    return FileProvider.getUriForFile(
        context, "com.codc.cats.fileprovider", newFile
    )
}