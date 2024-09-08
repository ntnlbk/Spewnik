package com.LibBib.spewnik.presentation

import android.text.SpannableString

sealed class SongFragmentState {
    data object Progress : SongFragmentState()
    class Content(
        val name: String,
        val text: SpannableString
    ) : SongFragmentState()
}