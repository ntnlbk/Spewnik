package com.example.spewnik.domain

interface SongListRepository {
    fun getSongList(): List<Song>

    fun getSong(id: Int): Song

    fun getSortedSongList(songType: SongType): List<Song>
}