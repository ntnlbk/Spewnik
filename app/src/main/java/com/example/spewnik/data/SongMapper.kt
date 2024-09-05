package com.example.spewnik.data

import com.example.spewnik.domain.Song
import com.example.spewnik.domain.SongType
import javax.inject.Inject

class SongMapper @Inject constructor() {

    private fun mapDbModelToEntity(dbModel: SongDbModel): Song{

        return Song(
            dbModel.name,
            dbModel.text,
            dbModel.author,
            setOf(SongType.SHORT, SongType.LONG),
            dbModel.id
        )
    }

    fun mapListDbModelToListEntity(list: List<SongDbModel>): List<Song>{
        return list.map {
            mapDbModelToEntity(it)
        }
    }
}