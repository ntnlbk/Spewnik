package com.example.spewnik.presentation

import androidx.lifecycle.ViewModel
import com.example.spewnik.domain.GetSongListUseCase
import javax.inject.Inject

class SongListViewModel @Inject constructor(
    private val getSongListUseCase: GetSongListUseCase
) : ViewModel() {

    val songList = getSongListUseCase()

}

