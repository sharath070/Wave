package com.sharath070.wave.common.utils

import androidx.core.text.HtmlCompat

fun String.highImageQuality(): String {
    return if (this.contains("150x150.jpg")) {
        this.replace("150x150.jpg", "500x500.jpg")
    } else this
}

fun String.parse(): String {
    return HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY)
        .toString()
}