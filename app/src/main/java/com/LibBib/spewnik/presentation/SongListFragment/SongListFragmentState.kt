package com.LibBib.spewnik.presentation.SongListFragment

import com.LibBib.spewnik.domain.Song

sealed class SongListFragmentState {
    data object Progress : SongListFragmentState()
    class Content(
        val songList: List<Song>
    ) : SongListFragmentState()
}