package com.codc.cats.data.common

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import timber.log.Timber

/**
 * Checks if a string is a an image file by looking for image extensions at the end of it.
 */
fun String.isImageFile(): Boolean {
    val imageFileExtensions = listOf(".png", ".jpg", ".jpeg", ".gif")
    return this.isNotEmpty() && imageFileExtensions.any {
        this.endsWith(it)
    }
}

/**
 * Applies a blur effect to an existing modifier and returns a new one. If SDK version is too
 * low then the original modifier is returned.
 */
fun Modifier.applyBlur(): Modifier {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        this.then(
            Modifier
                .graphicsLayer(
                    renderEffect = RenderEffect
                        .createBlurEffect(
                            25f,
                            25f,
                            Shader.TileMode.MIRROR
                        )
                        .asComposeRenderEffect()
                )
        )
    } else {
        Timber.w("Android SDK version too low.")
        this
    }
}

/**
 * Applies a grayscale effect to an existing modifier and returns a new one. If SDK version is too
 * low then the original modifier is returned.
 */
fun Modifier.applyGrayScale(): Modifier {
    val grayScaleMatrix = ColorMatrix(
        floatArrayOf(
            0.33f, 0.33f, 0.33f, 0f, 0f,
            0.33f, 0.33f, 0.33f, 0f, 0f,
            0.33f, 0.33f, 0.33f, 0f, 0f,
            0f, 0f, 0f, 1f, 0f
        )
    )

    val colorFilter = ColorMatrixColorFilter(grayScaleMatrix)

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        this.then(
            Modifier
                .graphicsLayer(
                    renderEffect = RenderEffect
                        .createColorFilterEffect(
                            colorFilter
                        )
                        .asComposeRenderEffect()
                )
        )
    } else {
        Timber.w("Android SDK version too low.")
        this
    }
}