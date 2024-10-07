package com.LibBib.spevn.presentation.SongListFragment

import com.LibBib.spevn.domain.Song
import com.LibBib.spevn.domain.SongType

sealed class SongListFragmentState {
    data object Progress : SongListFragmentState()
    class Content(
        val songList: List<Song>,
        val currentSongType: SongType
    ) : SongListFragmentState()
}