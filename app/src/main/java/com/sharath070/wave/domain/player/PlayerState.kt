package com.sharath070.wave.domain.player

enum class PlayerState {
    IDLE,
    PLAYING,
    PAUSED,
    STOPPED,
    BUFFERING
}

enum class RepeatModeState {
    REPEAT_OFF,
    REPEAT_ALL,
    REPEAT_ONE,
}