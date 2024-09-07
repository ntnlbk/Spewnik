package com.example.spewnik.presentation

import androidx.lifecycle.ViewModel
import com.example.spewnik.domain.GetSongListUseCase
import com.example.spewnik.domain.SongType
import javax.inject.Inject

class SongListViewModel @Inject constructor(
    getSongListUseCase: GetSongListUseCase
) : ViewModel() {

    val songList = getSongListUseCase(SongType.ALL)

}

