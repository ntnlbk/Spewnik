package com.example.spewnik.data

import com.example.spewnik.domain.Song
import com.example.spewnik.domain.SongListRepository
import com.example.spewnik.domain.SongType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SongListRepositoryImpl @Inject constructor(
    private val songListDao: SongListDao,
    private val mapper: SongMapper
) : SongListRepository {


    override fun getSongList(songType: SongType): Flow<List<Song>> {
        return mapper.mapListDbModelToListEntity(songListDao.getSongList())
    }

    override fun getSong(id: Int): Song {
        TODO("Not yet implemented")
    }


}