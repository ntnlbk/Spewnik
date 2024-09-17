package com.LibBib.spewnik.data

import android.util.Log
import com.LibBib.spewnik.domain.Song
import com.LibBib.spewnik.domain.SongListRepository
import com.LibBib.spewnik.domain.SongType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.map
import java.text.Collator
import java.util.Locale
import javax.inject.Inject

class SongListRepositoryImpl @Inject constructor(
    private val songListDao: SongListDao,
    private val mapper: SongMapper,
) : SongListRepository {


    override fun getSongList(): Flow<List<Song>> {

        return mapper.mapListDbModelToListEntity(
            songListDao.getSongList().map {
                it.sortedWith { a, b ->
                    Collator.getInstance(Locale("by", "BY")).compare(a.name, b.name)
                }
            }
        )

    }

    override fun getSong(id: Int): Flow<Song> {
        return songListDao.getSong(id).map { dbModel ->
            mapper.mapDbModelToEntity(dbModel)
        }
    }


}