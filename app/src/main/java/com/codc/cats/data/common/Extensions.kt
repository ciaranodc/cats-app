package com.codc.cats.data.common

fun String.isImageFile(): Boolean {
    val imageFileExtensions = listOf(".png", ".jpg", ".jpeg", ".gif")
    return this.isNotEmpty() && imageFileExtensions.any {
        this.endsWith(it)
    }
}