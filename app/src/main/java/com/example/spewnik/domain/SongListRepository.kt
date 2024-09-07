package com.example.spewnik.domain

import kotlinx.coroutines.flow.Flow

interface SongListRepository {
    fun getSongList(): Flow<List<Song>>

    fun getSong(id: Int): Song

    fun getSortedSongList(songType: SongType): List<Song>
}