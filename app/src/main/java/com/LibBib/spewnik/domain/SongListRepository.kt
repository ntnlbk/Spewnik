package com.LibBib.spewnik.domain

import kotlinx.coroutines.flow.Flow

interface SongListRepository {
    fun getSongList(songType: SongType): Flow<List<Song>>

    fun getSong(id: Int): Flow<Song>

}