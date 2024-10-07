package com.LibBib.spevn.presentation.SongFragment

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.LibBib.spevn.domain.GetSongUseCase
import com.LibBib.spevn.domain.Song
import com.LibBib.spevn.domain.TransposeSongUseCase
import com.LibBib.spevn.domain.options.GetOptionsUseCase
import com.LibBib.spevn.domain.options.Options
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
        _state.value = SongFragmentState.Progress
        val songName = song.name
        var songText = song.text
        if (options.isChordsVisible) {
            if(options.transposeInt != 0)
                songText = transposeSongUseCase(songText, options.transposeInt)
            val spannableSongText = SpannableString(songText)
            var counter = 0
            for (i in spannableSongText) {
                if (isCharPartOfChord(i)) {
                    spannableSongText.setSpan(
                        ForegroundColorSpan(options.chordsColor),
                        counter,
                        counter + 1,
                        Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                    )
                }
                counter++
            }
            _state.value = SongFragmentState.Content(songName, spannableSongText, options.textSize)
        } else {
            songText = songText.filterNot {
                isCharPartOfChord(it)
            }
            val spannableSongText = SpannableString(songText)
            _state.value = SongFragmentState.Content(songName, spannableSongText, options.textSize)
        }
    }

    private fun isCharPartOfChord(i: Char) =
            i >= UPPERCASE_A_DEC_CODE.toChar() &&
            i <= LOWERCASE_Z_DEC_CODE.toChar() ||
            i == NUMBER_SIGN_DEC_CODE.toChar() ||
            i == SEVEN_DEC_CODE.toChar()

    companion object {

        private const val UPPERCASE_A_DEC_CODE = 65
        private const val LOWERCASE_Z_DEC_CODE = 122
        private const val NUMBER_SIGN_DEC_CODE = 35
        private const val SEVEN_DEC_CODE = 55
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