package com.sharath070.wave.common.utils

import android.annotation.SuppressLint
import com.sharath070.wave.common.Constants.ALGORITHM
import com.sharath070.wave.common.Constants.KEY
import com.sharath070.wave.common.Constants.TRANSFORMATION
import java.nio.charset.StandardCharsets
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@SuppressLint("GetInstance")
@OptIn(ExperimentalEncodingApi::class)
fun decode(input: String): String {
    val key = KEY
    val cipher = Cipher.getInstance(TRANSFORMATION)
    val keySpec = SecretKeySpec(key.toByteArray(), ALGORITHM)
    cipher.init(Cipher.DECRYPT_MODE, keySpec)

    val encrypted = Base64.decode(input)
    val decrypted = cipher.doFinal(encrypted)
    return String(decrypted, StandardCharsets.UTF_8)
        .replace(Regex("\\.mp4.*"), ".mp4")
        .replace(Regex("\\.m4a.*"), ".m4a")
        .replace(Regex("\\.mp3.*"), ".mp3")
        .replace("http:", "https:")
}
