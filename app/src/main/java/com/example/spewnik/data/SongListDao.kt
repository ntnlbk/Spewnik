package com.example.spewnik.data

import androidx.room.Dao
import androidx.room.Query

@Dao
interface SongListDao {

    @Query("SELECT * FROM songs_table")
    fun getSongList(): List<SongDbModel>

}