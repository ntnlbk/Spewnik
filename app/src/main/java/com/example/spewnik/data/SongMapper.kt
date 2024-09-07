package com.example.spewnik.data

import com.example.spewnik.domain.Song
import com.example.spewnik.domain.SongType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SongMapper @Inject constructor() {

    private fun mapDbModelToEntity(dbModel: SongDbModel): Song {

        return Song(
            dbModel.name,
            dbModel.text,
            dbModel.author,
            setOf(SongType.SHORT, SongType.LONG),
            dbModel.id
        )
    }

    fun mapListDbModelToListEntity(list: Flow<List<SongDbModel>>): Flow<List<Song>> {
        return list.map { it.map { dbModel -> mapDbModelToEntity(dbModel) } }
    }
}
