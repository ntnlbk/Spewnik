package com.LibBib.spewnik.data

import com.LibBib.spewnik.domain.Song
import com.LibBib.spewnik.domain.SongListRepository
import com.LibBib.spewnik.domain.SongType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SongListRepositoryImpl @Inject constructor(
    private val songListDao: SongListDao,
    private val mapper: SongMapper
) : SongListRepository {


    override fun getSongList(songType: SongType): Flow<List<Song>> {
        return mapper.mapListDbModelToListEntity(songListDao.getSongList())
    }

    override fun getSong(id: Int): Flow<Song> {
        return songListDao.getSong(id).map { dbModel ->
            mapper.mapDbModelToEntity(dbModel)
        }
    }


}