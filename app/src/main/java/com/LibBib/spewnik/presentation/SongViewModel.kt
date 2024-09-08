package com.LibBib.spewnik.presentation

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.LibBib.spewnik.domain.GetSongUseCase
import com.LibBib.spewnik.domain.Song
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SongViewModel @AssistedInject constructor(
    @Assisted private val songId: Int,
    private val getSongUseCase: GetSongUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<SongFragmentState>(SongFragmentState.Progress)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getSongUseCase(songId).collect {
                parseSong(it)
            }
        }
    }

    private fun parseSong(song: Song){
        val songName = song.name
        var songText = song.text
        songText = songText
            .replace("<br/><br/>", System.lineSeparator())
            .replace("<br/>", "")
            .replace("<![CDATA[", "")
            .replace("]]>", "")
            .replace("  ", "")
        val spannableSongText = SpannableString(songText)
        var counter = 0
        for (i in spannableSongText) {
            if (i >= 65.toChar() && i <= 122.toChar() || i == 35.toChar() || i == 55.toChar()) {
                spannableSongText.setSpan(
                    ForegroundColorSpan(Color.RED),
                    counter,
                    counter + 1,
                    Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                )
            }
            counter++
        }
        _state.value = SongFragmentState.Content(songName, spannableSongText)
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun factory(
            factory: Factory,
            songId: Int
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T =
                    factory.create(songId) as T
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(songId: Int): SongViewModel
    }
}