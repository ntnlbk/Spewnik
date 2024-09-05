package com.example.spewnik.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.spewnik.domain.SongType

@Entity(tableName = "songs_table")
data class SongDbModel(
    val name: String,
    val text: String,
    val author: String,
    val types: String,
    @PrimaryKey
    var id: Int = UNDEFINED_ID
) {
    companion object {
        private const val UNDEFINED_ID = 0
    }
}
