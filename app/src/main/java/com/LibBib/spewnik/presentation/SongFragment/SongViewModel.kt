package com.LibBib.spewnik.presentation.SongFragment

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.LibBib.spewnik.domain.GetSongUseCase
import com.LibBib.spewnik.domain.Song
import com.LibBib.spewnik.domain.TransposeSongUseCase
import com.LibBib.spewnik.domain.options.GetOptionsUseCase
import com.LibBib.spewnik.domain.options.Options
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SongViewModel @AssistedInject constructor(
    @Assisted private val songId: Int,
    private val getSongUseCase: GetSongUseCase,
    private val getOptionsUseCase: GetOptionsUseCase,
    private val transposeSongUseCase: TransposeSongUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<SongFragmentState>(SongFragmentState.Progress)
    val state = _state.asStateFlow()

    private lateinit var options: Options
    private lateinit var song: Song

    fun updateScreen() {
        viewModelScope.launch {
            song = getSongUseCase.invoke(songId).first()
            options = getOptionsUseCase()
            parseSong(song)
        }
    }

    private fun parseSong(song: Song) {
        val songName = song.name
        var songText = song.text
        if (options.isChordsVisible) {
            if(options.transposeInt != 0)
                songText = transposeSongUseCase(songText, options.transposeInt)
            val spannableSongText = SpannableString(songText)
            var counter = 0
            for (i in spannableSongText) {
                if (i >= 65.toChar() && i <= 122.toChar() || i == 35.toChar() || i == 55.toChar()) {
                    spannableSongText.setSpan(
                        ForegroundColorSpan(options.chordsColor),
                        counter,
                        counter + 1,
                        Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                    )
                }
                counter++
            }
            _state.value = SongFragmentState.Content(songName, spannableSongText)
        } else {
            songText = songText.filterNot {
                it >= 65.toChar() && it <= 122.toChar() || it == 35.toChar() || it == 55.toChar()
            }
            val spannableSongText = SpannableString(songText)
            _state.value = SongFragmentState.Content(songName, spannableSongText)
        }
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun factory(
            factory: Factory,
            songId: Int,
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