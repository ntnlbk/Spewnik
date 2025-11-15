package com.LibBib.spevn.presentation.SongFragment

import android.text.SpannableString
import java.io.File

sealed class SongFragmentState {
    data object Progress : SongFragmentState()
    class Content(
        val name: String,
        val text: SpannableString,
        val textSizeFromOptions: Int = 0
    ) : SongFragmentState()
    class SongFileDownloadSuccessful(
        val file: File
    ) : SongFragmentState()
    class SongFileDownloadError(
        val message: String
    ) : SongFragmentState()
}