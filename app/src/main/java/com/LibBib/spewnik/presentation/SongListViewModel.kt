package com.LibBib.spewnik.presentation

import androidx.lifecycle.ViewModel
import com.LibBib.spewnik.domain.GetSongListUseCase
import com.LibBib.spewnik.domain.SongType
import javax.inject.Inject

class SongListViewModel @Inject constructor(
    getSongListUseCase: GetSongListUseCase
) : ViewModel() {

    val songList = getSongListUseCase(SongType.ALL)

}

