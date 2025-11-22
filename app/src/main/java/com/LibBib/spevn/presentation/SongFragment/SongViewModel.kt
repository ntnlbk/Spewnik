package com.LibBib.spevn.presentation.SongFragment

import android.app.Application
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.LibBib.spevn.domain.GetSongUseCase
import com.LibBib.spevn.domain.Song
import com.LibBib.spevn.domain.TransposeSongUseCase
import com.LibBib.spevn.domain.options.GetOptionsUseCase
import com.LibBib.spevn.domain.options.Options
import com.LibBib.spevn.domain.remoteDB.DownloadSongUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SongViewModel @AssistedInject constructor(
    @Assisted private val songId: Int,
    private val getSongUseCase: GetSongUseCase,
    private val getOptionsUseCase: GetOptionsUseCase,
    private val transposeSongUseCase: TransposeSongUseCase,
    private val downloadSongUseCase: DownloadSongUseCase,
    private val application: Application,
) : ViewModel() {

    private val _state = MutableStateFlow<SongFragmentState>(SongFragmentState.Progress)
    val state = _state.asStateFlow()

    private lateinit var options: Options
    private lateinit var song: Song
    private lateinit var songName: String
    private lateinit var songText: String
    private lateinit var spannableSongText: SpannableString

    private val player by lazy {
        ExoPlayer.Builder(application).build()
    }

    private val _playerUIState = MutableStateFlow(PlayerUIState())
    val playerUIState = _playerUIState.asStateFlow()

    private val _songPlayerEvent = MutableSharedFlow<SongPlayerEvent>()
    val songPlayerEvent = _songPlayerEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            while (true) {
                delay(200L)
                _playerUIState.value = PlayerUIState(
                    currentPosition = player.currentPosition,
                    duration = player.duration.takeIf { it > 0 } ?: 0L,
                    isPlaying = player.isPlaying,
                    songName = songName
                )
            }
        }
    }

    fun playButtonClicked() {
        player.play()
    }

    fun pauseButtonClicked() {
        player.pause()
    }

    fun updateScreen() {
        viewModelScope.launch {
            song = getSongUseCase(songId).first()
            options = getOptionsUseCase()
            parseSong(song)
        }
    }

    private fun parseSong(song: Song) {
        _state.value = SongFragmentState.Progress
        songName = song.name
        songText = song.text
        spannableSongText = SpannableString(parseSongText(songText))
        setContentState()

    }

    private fun setContentState() {
        _state.value = SongFragmentState.Content(songName, spannableSongText, options.textSize)
    }

    private fun parseSongText(songText: String): SpannableStringBuilder {
        val lines = songText.lines()
        val resultSpannable = SpannableStringBuilder()
        for (line in lines) {
            var spannableLine: SpannableString
            if (isChordLine(line)) {
                if (options.isChordsVisible) {
                    var chordLine = formatChordLine(line)
                    if (options.transposeInt != ZERO_TRANSPOSE)
                        chordLine = transposeSongUseCase(chordLine, options.transposeInt)
                    spannableLine = SpannableString(chordLine)
                    spannableLine.setSpan(
                        ForegroundColorSpan(options.chordsColor),
                        0,
                        chordLine.length - 1,
                        Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                    )
                } else {
                    spannableLine = SpannableString(EMPTY_STRING)
                }

            } else {
                spannableLine = SpannableString(line + NEW_LINE_CHAR)
            }

            resultSpannable.append(spannableLine)
        }

        return resultSpannable

    }

    private fun formatChordLine(line: String) =
        line.filterNot { it == CHORD_LINE_BEGIN } + NEW_LINE_CHAR

    private fun isChordLine(line: String) = line.isNotEmpty() && line[0] == CHORD_LINE_BEGIN

    fun listenButtonClicked() {
        _state.value = SongFragmentState.Progress
        loadSongFromFirebase()
    }

    private fun loadSongFromFirebase() {
        viewModelScope.launch {
            downloadSongUseCase(song.name).collect { result ->
                _songPlayerEvent.emit(
                    result.fold(
                        onSuccess = {
                            if (player.currentMediaItem == null) {
                                val mediaItem = MediaItem.fromUri(it.path)
                                player.setMediaItem(mediaItem)
                                player.prepare()
                            }
                            SongPlayerEvent.SongFileDownloadSuccessful()
                        },
                        onFailure = { SongPlayerEvent.SongFileDownloadError(it.message) }
                    )
                )
                setContentState()
            }
        }
    }


    companion object {

        private const val ZERO_TRANSPOSE = 0
        private const val EMPTY_STRING = ""
        const val CHORD_LINE_BEGIN = '|'
        private const val NEW_LINE_CHAR = '\n'

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