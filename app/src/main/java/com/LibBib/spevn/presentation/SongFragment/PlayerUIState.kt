package com.LibBib.spevn.presentation.SongFragment

data class PlayerUIState(
    val songName: String = "",
    val duration: Long = 0L,
    val isPlaying: Boolean = false,
    val currentPosition: Long = 0L,
    )