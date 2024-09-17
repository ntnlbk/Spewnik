package com.LibBib.spewnik.presentation.SongListFragment

import com.LibBib.spewnik.domain.Song
import com.LibBib.spewnik.domain.SongType

sealed class SongListFragmentState {
    data object Progress : SongListFragmentState()
    class Content(
        val songList: List<Song>,
        val currentSongType: SongType
    ) : SongListFragmentState()
}