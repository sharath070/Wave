package com.sharath070.wave.presentation.feature.test

import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.sharath070.wave.common.utils.decode
import java.io.IOException

@Preview
@Composable
fun SongPlayer() {
    val mediaPlayer = remember { MediaPlayer() }
    val isPlaying = remember { mutableStateOf(false) }
    val encryptedUrl =
        "ID2ieOjCrwfgWvL5sXl4B1ImC5QfbsDy0GF7amDM+kR6MPgL6n1NCvHZAAa0+qM2vRmrw+iF1mtXa+r1rfsRTRw7tS9a8Gtq"


    Text(
        text = if (isPlaying.value) "Pause" else "Play",
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                try {

                    mediaPlayer.setDataSource(decode(encryptedUrl))
                    mediaPlayer.prepareAsync()

                    if (isPlaying.value) {
                        mediaPlayer.pause()
                    } else {
                        mediaPlayer.setOnPreparedListener {
                            mediaPlayer.start()
                        }
                    }
                    isPlaying.value = !isPlaying.value
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            },
        textAlign = TextAlign.Center
    )
}

fun main() {
    println(decode("ID2ieOjCrwdjlkMElYlzWCptgNdUpWD8byyD4iGMBe7eSezj3fAjQIGCcQTNOoLGtAnKlhcsr7N+ULHZj62twI92mytrdt3FDnQW0nglPS4="))
}