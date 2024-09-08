package com.LibBib.spewnik.data

import com.LibBib.spewnik.domain.Song
import com.LibBib.spewnik.domain.SongType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SongMapper @Inject constructor() {

     fun mapDbModelToEntity(dbModel: SongDbModel): Song {

        return Song(
            dbModel.name,
            dbModel.text,
            dbModel.author,
            setOf(SongType.SHORT, SongType.LONG),
            "Short, long",
            dbModel.id
        )
    }

    fun mapListDbModelToListEntity(list: Flow<List<SongDbModel>>): Flow<List<Song>> {
        return list.map { it.map { dbModel -> mapDbModelToEntity(dbModel) } }
    }
}
