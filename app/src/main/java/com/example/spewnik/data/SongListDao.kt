package com.example.spewnik.data

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SongListDao {

    @Query("SELECT * FROM songs_table")
    fun getSongList(): Flow<List<SongDbModel>>


}