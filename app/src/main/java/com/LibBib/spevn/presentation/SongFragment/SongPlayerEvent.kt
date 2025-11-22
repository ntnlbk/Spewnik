package com.LibBib.spevn.presentation.SongFragment

sealed class SongPlayerEvent {
    class SongFileDownloadSuccessful() : SongPlayerEvent()
    class SongFileDownloadError(
        val message: String?
    ) : SongPlayerEvent()
}