package com.example.spewnik.data

import com.example.spewnik.domain.Song
import com.example.spewnik.domain.SongListRepository
import com.example.spewnik.domain.SongType

class SongListRepositoryImpl: SongListRepository {
    override fun getSongList(): List<Song> {
        TODO("Not yet implemented")
    }

    override fun getSong(id: Int): Song {
        TODO("Not yet implemented")
    }

    override fun getSortedSongList(songType: SongType): List<Song> {
        TODO("Not yet implemented")
    }

}